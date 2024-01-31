package Server;

import PostgreDB.ManagerDB;
import java.awt.Color;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class ServerManagerTab {
    MainWindow main;
    ManagerDB manager;
    Controller controller;
    
    public ServerManagerTab (MainWindow main)
    {
        this.main = main;
        manager = main.getManagerDB();
        controller = new Controller(main);
    }
    
    public boolean setConnectionDB(String userName, String password, String dbName)
    {
        if(dbName.isEmpty())
        {
            main.setDBMessage("DB Name isEmpty!", "Error", Color.RED);
            return false;
        }
        
        try {
            manager.getConnection(userName, password, dbName);
            controller.getConnectionDB(userName, password, dbName);
            main.setDBMessage("Successful connection", "Connected", Color.MAGENTA);
            
            return true;

        } catch (SQLException ex) {
            main.setDBMessage(ex.getLocalizedMessage(), "Error", Color.RED);
            return false;
        }
    }
    
    public boolean ServerStart(int countClients, String timeMinutes)
    {
        if(!manager.isConnected())
        {
            main.setServerMessage("Set connection to DB!", "Error", Color.red);
            return false;
        }
        
        if(countClients == 0)
        {
            main.setServerMessage("Список клиентов пуст. Добавьте клиентов.", "Error", Color.red);
            return false;
        }

        try
        {
            int minutes = Integer.parseInt(timeMinutes);

            if(minutes > 0 && minutes < 999)
            {
                controller.ServerStart(minutes);
                main.setEditableTime(false);

                main.setServerMessage("Сервер успешно запущен!", "Started", Color.MAGENTA);
                return true;
            }
            else
            {
                main.setServerMessage("Неверный формат времени: (0;999)", "Error", Color.red);
                return false;
            }
        }catch(Exception ex)
        {
            main.setServerMessage("Неверный формат времени: (0;999)", "Error", Color.red);
            return false;
        }
    }
    
    public void ServerStop()
    {
        controller.ServerStop();
        main.setServerMessage("Сервер остановлен", "Stoped", Color.orange);
    }
}
