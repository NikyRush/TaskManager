package Server;

import DataExchange.LoadInfo;
import PostgreDB.ManagerDB;
import java.awt.Color;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class InformationTab {
    MainWindow main;
    ManagerDB manager;
    
    public InformationTab (MainWindow main)
    {
        this.main = main;
        manager = main.getManagerDB();
    }
    
    public void getClientInfo(Object IP)
    {
        if(isNullIP(IP))
            return;
        
        String ip = IP.toString();
        
        try {
            main.setComboQuery(manager.getListQuery(ip));
            main.setHardarwareInfo(manager.getClientHardWareInfo(ip));
            
            main.setDBMessage("", "Connected", Color.MAGENTA);
        } catch (SQLException ex) {
            main.setDBMessage(ex.getLocalizedMessage(), "Error", Color.RED);
        }
    }
    
    public void getClientWorkLoad(Object objIP, Object objQueryTime)
    {
        if(isNullIP(objIP))
            return;
        
        if(isNullQueryTime(objQueryTime))
            return;
        
        String IP = objIP.toString(), queryTime = objQueryTime.toString();
        
        try {
            LoadInfo loadInfo = manager.getClientWorkLoad(IP, queryTime);
            loadInfo.setListProcessInfo(manager.getClientListProcess(IP, queryTime));
            
            main.setLoadInfo(loadInfo);
            main.setDBMessage("", "Connected", Color.MAGENTA);

        } catch (SQLException ex) {
            main.setDBMessage(ex.getLocalizedMessage(), "Error", Color.RED);
        }
    }
    
    private boolean isNullIP(Object IP)
    {
        if(IP == null)
        {
            main.ClearClientInfo();
            main.ClearClientWorkLoad();
            return true;
        }
        
        return false;
    }
    
    private boolean isNullQueryTime(Object queryTime)
    {
        if(queryTime == null)
        {
            main.ClearClientWorkLoad();
            return true;
        }
        
        return false;
    }
}
