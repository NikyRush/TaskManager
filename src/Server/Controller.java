package Server;

import DataExchange.FunctionMBean;
import DataExchange.HardwareInfo;
import DataExchange.LoadInfo;
import PostgreDB.ManagerDB;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class Controller {
    private static Controller instance;
    private final ManagerDB managerDB;
    Timer timer;
    DefaultTableModel modelClients, modelErrors;
    boolean isActiveServer;
    
    int SEC_IN_MIN = 60;
    int MS_IN_SEC = 1000;
    
     /**
     * Singltone
     * @return object Controller (instance)
     */
    private Controller(DefaultTableModel modelClients, DefaultTableModel modelErrors)
    {
        managerDB = new ManagerDB();
        this.modelClients = modelClients;
        this.modelErrors = modelErrors;
    }
    
    public static Controller getInstance(DefaultTableModel modelClients, DefaultTableModel modelErrors)
    {
        if(instance == null)
            instance = new Controller(modelClients, modelErrors);
        return instance;
    }
    
    public void getConnectionDB(String user, String password, String DBname) throws SQLException
    {
        managerDB.getConnection(user, password, DBname);
    }
    
    public void ServerStart(int minutes)
    {       
        if(timer != null)
            return;
        
        isActiveServer = true;
        System.out.println("The server is running!");
        
        new Thread(() -> {
            TimerFunction();
        }).start(); //Forced initial information collection call
        
        timer = new Timer(minutes * MS_IN_SEC * SEC_IN_MIN , e -> {
            TimerFunction();
        });
        timer.start();
    }
    
    private void TimerFunction()
    {
        String ipAddress, port;
                
        for(int index = 0; index < modelClients.getRowCount(); index++)
        {   
            if(!isActiveServer)
                break;

            ipAddress = modelClients.getValueAt(index, 1).toString();
            port = modelClients.getValueAt(index, 2).toString();

            try {
                managerDB.updateHardwareInfo(getHardwareInfo(ipAddress, port), ipAddress);
                managerDB.updateLoadInfo(getLoadInfo(ipAddress, port), ipAddress);

            } catch (SQLException | MalformedObjectNameException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                modelErrors.addRow(new Object[] {new Date(), ipAddress, ex.getLocalizedMessage()});
            }
        }
    }
    
    public void ServerStop()
    {
        if(timer == null)
            return;
        isActiveServer = false;
        timer.stop();
        timer = null;
        System.out.println("The server is stopped!");
    }
    
    private HardwareInfo getHardwareInfo(String ipAddress, String port) throws IOException, MalformedObjectNameException
    {
        String conURL = String.format("service:jmx:rmi:///jndi/rmi://%s:%s/jmxrmi", ipAddress.substring(0, ipAddress.length() - 3), port);
        JMXServiceURL url = new JMXServiceURL(conURL);
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        
        ObjectName name = new ObjectName("DataExchange:type=Function");
        FunctionMBean mbProxy = JMX.newMBeanProxy(mbsc, name, FunctionMBean.class, false);
        HardwareInfo hardwareInfo = mbProxy.returnHardwareInfo();
        jmxc.close();
        return hardwareInfo;
    }
    
    private LoadInfo getLoadInfo (String ipAddress, String port) throws IOException, MalformedObjectNameException
    {
        String conURL = String.format("service:jmx:rmi:///jndi/rmi://%s:%s/jmxrmi", ipAddress.substring(0, ipAddress.length() - 3), port);
        JMXServiceURL url = new JMXServiceURL(conURL);
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
        
        ObjectName name = new ObjectName("DataExchange:type=Function");
        FunctionMBean mbProxy = JMX.newMBeanProxy(mbsc, name, FunctionMBean.class, false);
        LoadInfo loadInfo = mbProxy.returnLoadInfo();
        jmxc.close();
        return loadInfo;
    }
}
