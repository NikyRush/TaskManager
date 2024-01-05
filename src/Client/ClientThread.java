package Client;

import DataExchange.Function;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

/**
 *
 * @author Admin
 */
public class ClientThread extends Thread{
    Controller controller;
    
    public ClientThread(String name, Controller controller)
    {
        super(name);
        this.controller = controller;
    }
    
    @Override
    public void run()
    {
        try {
                MBeanServer server = ManagementFactory.getPlatformMBeanServer();
                ObjectName name = new ObjectName("DataExchange:type=Function");
                Function mbean = new Function(controller);
                server.registerMBean(mbean, name);
                
                System.out.println("Waiting forever...");
                Thread.sleep(Long.MAX_VALUE);
                
            } catch (MalformedObjectNameException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstanceAlreadyExistsException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MBeanRegistrationException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NotCompliantMBeanException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
