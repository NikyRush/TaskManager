package Server;

import PostgreDB.ManagerDB;
import java.awt.Color;
import java.sql.SQLException;


/**
 *
 * @author Admin
 */
public class ClientsTab {
    MainWindow main;
    ManagerDB manager;
    
    public ClientsTab (MainWindow main)
    {
        this.main = main;
        manager = main.getManagerDB();
    }
    
    public void getClients()
    {       
        try {
            main.setTableClients(manager.getListClient());
        } catch (SQLException ex) {
            main.setDBMessage(ex.getLocalizedMessage(), "Error", Color.RED);
        }
    }
    
    public void InsertClient(String name, String IP, String port)
    {
        if(notCorrect(IP, name, port))
            return;
        
        try{
            manager.insertClient(name, IP, port);
            getClients();
            main.setDBMessage("", "Connected", Color.MAGENTA);
            
        }catch(SQLException ex){
            main.setDBMessage(ex.getLocalizedMessage(), "Error", Color.RED);
        }
    }
    
    public void UpdateClient(String oldIP, String name, String IP, String port)
    {
        if(notCorrect(IP, name, port))
            return;

        try{
            manager.updateClient(oldIP, IP, port, name);
            getClients();
            
            main.setDBMessage("", "Connected", Color.MAGENTA);
                    
        }catch(SQLException ex){
            main.setDBMessage(ex.getLocalizedMessage(), "Error", Color.RED);
        }
    }
    
    public void DeleteClient(String IP)
    {
        try{
            manager.deleteClient(IP);
            
            getClients();
            main.setDBMessage("", "Connected", Color.MAGENTA);

        }catch(SQLException ex){
            main.setDBMessage(ex.getLocalizedMessage(), "Error", Color.RED);
        }
    }
    
    private boolean notCorrect(String IP, String name, String port)
    {
        return !manager.isConnected() || IP.isEmpty() || port.isEmpty() || name.isEmpty();
    }
}
