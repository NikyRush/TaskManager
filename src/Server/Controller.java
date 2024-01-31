package Server;

import DataExchange.FunctionMBean;
import DataExchange.HardwareInfo;
import DataExchange.LoadInfo;
import PostgreDB.ManagerDB;
import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
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
    private final ManagerDB managerDB;
    MainWindow main;
    
    Timer timer;
    boolean isActiveServer;
    
    int SEC_IN_MIN = 60;
    int MS_IN_SEC = 1000;
    
    public Controller(MainWindow main)
    {
        managerDB = new ManagerDB();
        this.main = main;
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
        DefaultTableModel modelClients = main.getModelClients();
        
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
                main.setServerMessage(ex.getLocalizedMessage(), "Error", Color.red);
            } catch (IOException ex) {
                main.AddError(new Object[] {new Date(), ipAddress, ex.getLocalizedMessage()});
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
