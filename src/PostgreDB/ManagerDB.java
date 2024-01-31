
package PostgreDB;

import Characteristic.ProcessInfo;
import DataExchange.HardwareInfo;
import DataExchange.LoadInfo;
import static PostgreDB.StructureDB.*;
import Server.ClientInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ManagerDB {
    Connection con;
    Statement statement;
    
    public void getConnection(String user, String password, String DBName) throws SQLException
    {
        if(con == null)
        {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + DBName, user, password);
            CreateTables();
        }
    }
    
    public boolean isConnected()
    {
        return (con != null);
    }
    
    private void CreateTables() throws SQLException
    {
        CreateTable(SQL_CREATE_CLIENT);
        CreateTable(SQL_CREATE_QUERY);
        CreateTable(SQL_CREATE_PROCESS);
        CreateTable(SQL_CREATE_ERROR);
    }
    
    private void CreateTable(String SQL_CREATE_TABLE) throws SQLException {
        statement = con.createStatement();
        statement.executeUpdate(SQL_CREATE_TABLE);
        statement.close();
    }
    
    public ArrayList<ClientInfo> getListClient() throws SQLException
    {   
        String query = String.format("select %s,%s,%s from %s",
                                    TableClient.COLUMN_IP, 
                                    TableClient.COLUMN_PORT,
                                    TableClient.COLUMN_NAME,
                                    TableClient.TABLE_NAME);
        
        statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        ArrayList<ClientInfo> listClient = new ArrayList<>();
        ClientInfo client;
        
        while(rs.next())
        {
            client = new ClientInfo();
            client.setName(rs.getString(TableClient.COLUMN_NAME));
            client.setIp(rs.getString(TableClient.COLUMN_IP));
            client.setPort(rs.getInt(TableClient.COLUMN_PORT));
            listClient.add(client);
        }
        statement.close();
        
        return listClient;
    }

    public ArrayList<String> getListQuery(String ipClient) throws SQLException {     
        String query = String.format("select %s from %s where %s = "
                    + "(select %s from %s where %s = '%s')",
                    TableQuery.COLUMN_DATE_TIME,
                    TableQuery.TABLE_NAME,
                    TableQuery.COLUMN_CLIENT_ID,
                    TableClient.COLUMN_ID,
                    TableClient.TABLE_NAME,
                    TableClient.COLUMN_IP,
                    ipClient);
        
        statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        ArrayList<String> listQuery = new ArrayList<>();
        
        while(rs.next())
            listQuery.add(rs.getString(TableQuery.COLUMN_DATE_TIME));
        
        statement.close();
        
        return listQuery;
    }

    public HardwareInfo getClientHardWareInfo(String ipClient) throws SQLException {
        String query = String.format("select %s,%s,%s,%s from %s where %s = '%s'",
                    TableClient.COLUMN_CPU_INFO,
                    TableClient.COLUMN_RAM_INFO,
                    TableClient.COLUMN_GPU_INFO,
                    TableClient.COLUMN_DISK_INFO,
                    TableClient.TABLE_NAME,
                    TableClient.COLUMN_IP,
                    ipClient);
        
        statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        
        HardwareInfo hardwareInfo = new HardwareInfo();
        while(rs.next())
        {
            hardwareInfo.setInfoCPU(rs.getString(TableClient.COLUMN_CPU_INFO));
            hardwareInfo.setInfoGPU(rs.getString(TableClient.COLUMN_GPU_INFO));
            hardwareInfo.setInfoRAM(rs.getString(TableClient.COLUMN_RAM_INFO));
            hardwareInfo.setInfoDisk(rs.getString(TableClient.COLUMN_DISK_INFO));
        }
        
        statement.close();
        return hardwareInfo;
    }

    public LoadInfo getClientWorkLoad(String ipClient, String timeQuery) throws SQLException{
        String query = String.format("select %s,%s from %s where %s = "
                + "(select %s from %s where %s = '%s') and %s = '%s'",
                    TableQuery.COLUMN_RAM_LOAD_GB,
                    TableQuery.COLUMN_DISK_LOAD_GB,
                    TableQuery.TABLE_NAME,
                    TableQuery.COLUMN_CLIENT_ID,
                    TableClient.COLUMN_ID,
                    TableClient.TABLE_NAME,
                    TableClient.COLUMN_IP,
                    ipClient,
                    TableQuery.COLUMN_DATE_TIME,
                    timeQuery);
        
        statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        LoadInfo loadInfo = new LoadInfo();
        
        while(rs.next())
        {
            loadInfo.setLoadRAM(rs.getDouble(TableQuery.COLUMN_RAM_LOAD_GB));
            loadInfo.setLoadDisk(rs.getDouble(TableQuery.COLUMN_DISK_LOAD_GB));
        }
        statement.close();
        return loadInfo;
    }

    public ArrayList<ProcessInfo> getClientListProcess(String ipClient, String timeQuery) throws SQLException{

        String query = String.format("select * from %s where %s = "
                + "(select %s from %s where %s = '%s' and %s = "
                + "(select %s from %s where %s = '%s'))", 
                                    TableProcess.TABLE_NAME, 
                                    TableProcess.COLUMN_QUERY_ID,
                                    TableQuery.COLUMN_ID,
                                    TableQuery.TABLE_NAME,
                                    TableQuery.COLUMN_DATE_TIME,
                                    timeQuery,
                                    TableQuery.COLUMN_CLIENT_ID,
                                    TableClient.COLUMN_ID,
                                    TableClient.TABLE_NAME,
                                    TableClient.COLUMN_IP,
                                    ipClient);
        
        statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        
        ArrayList<ProcessInfo> listProcessInfo = new ArrayList<>();
        ProcessInfo processInfo;
        while(rs.next())
        {
            processInfo = new ProcessInfo();
            processInfo.setPid(rs.getInt(TableProcess.COLUMN_PID));
            processInfo.setName(rs.getString(TableProcess.COLUMN_NAME));
            processInfo.setCpuLoadPercent(rs.getDouble(TableProcess.COLUMN_CPU_LOAD_PERCENT));
            processInfo.setRamUsed(rs.getDouble(TableProcess.COLUMN_RAM_LOAD_MB));

            listProcessInfo.add(processInfo);
        }
        statement.close();
        return listProcessInfo;
    }

    public void insertClient(String name, String ipAddress, String port) throws SQLException {
        String query = String.format("insert into %s (%s,%s,%s) values ('%s','%s','%s')", 
                TableClient.TABLE_NAME,
                TableClient.COLUMN_IP,
                TableClient.COLUMN_PORT,
                TableClient.COLUMN_NAME,
                ipAddress,
                port,
                name);
        statement = con.createStatement();
        statement.executeUpdate(query);
        
        statement.close();
    }

    public void updateClient(String oldIpAddress, String ipAddress, String port, String name) throws SQLException {
        String query = String.format("update %s set %s = '%s', %s = '%s', %s = '%s' where %s = '%s'", 
                TableClient.TABLE_NAME,
                TableClient.COLUMN_IP, ipAddress,
                TableClient.COLUMN_PORT, port,
                TableClient.COLUMN_NAME, name,
                TableClient.COLUMN_IP, oldIpAddress
                );
        statement = con.createStatement();
        statement.executeUpdate(query);
        
        statement.close();
    }

    public void deleteClient(String oldIpAddress) throws SQLException{
        deleteProcess(oldIpAddress);
        deleteQuery(oldIpAddress);
        deleteError(oldIpAddress);
        
        String query = String.format("delete from %s where %s = '%s'",
                TableClient.TABLE_NAME,
                TableClient.COLUMN_IP,
                oldIpAddress
                );
        statement = con.createStatement();
        statement.executeUpdate(query);
        
        statement.close();
    }

    private void deleteProcess(String oldIpAddress) throws SQLException{
        String query = String.format("delete from %s where %s in "
                + "(select %s from %s where %s = "
                + "(select %s from %s where %s = '%s'))",
                TableProcess.TABLE_NAME,
                TableProcess.COLUMN_QUERY_ID,
                TableQuery.COLUMN_ID,
                TableQuery.TABLE_NAME,
                TableQuery.COLUMN_CLIENT_ID,
                TableClient.COLUMN_ID,
                TableClient.TABLE_NAME,
                TableClient.COLUMN_IP,
                oldIpAddress
                );
        statement = con.createStatement();
        statement.executeUpdate(query);
        
        statement.close();
    }

    private void deleteQuery(String oldIpAddress) throws SQLException{
        String query = String.format("delete from %s where %s = (select %s from %s where %s = '%s')",
                TableQuery.TABLE_NAME,
                TableQuery.COLUMN_CLIENT_ID,
                TableClient.COLUMN_ID,
                TableClient.TABLE_NAME,
                TableClient.COLUMN_IP,
                oldIpAddress
                );
        statement = con.createStatement();
        statement.executeUpdate(query);
        
        statement.close();
    }

    private void deleteError(String oldIpAddress) throws SQLException{
        String query = String.format("delete from %s where %s = (select %s from %s where %s = '%s')",
                TableError.TABLE_NAME,
                TableError.COLUMN_CLIENT_ID,
                TableClient.COLUMN_ID,
                TableClient.TABLE_NAME,
                TableClient.COLUMN_IP,
                oldIpAddress
                );
        statement = con.createStatement();
        statement.executeUpdate(query);
        
        statement.close();
    }

    public void updateHardwareInfo(HardwareInfo hardwareInfo, String ipAddress) throws SQLException {
        String query = String.format("update %s set %s = '%s', %s = '%s', %s = '%s', %s = '%s' where %s = '%s'", 
                TableClient.TABLE_NAME,
                TableClient.COLUMN_CPU_INFO, hardwareInfo.getInfoCPU(),
                TableClient.COLUMN_RAM_INFO, hardwareInfo.getInfoRAM(),
                TableClient.COLUMN_GPU_INFO, hardwareInfo.getInfoGPU(),
                TableClient.COLUMN_DISK_INFO, hardwareInfo.getInfoDisk(),
                TableClient.COLUMN_IP, ipAddress
                );
        statement = con.createStatement();
        statement.executeUpdate(query);
        
        statement.close();
    }

    public void updateLoadInfo(LoadInfo loadInfo, String ipAddress) throws SQLException {        
        String query = String.format("insert into %s (%s,%s,%s,%s) values ("
                + "(select %s from %s where %s = '%s'), '%s','%s', %s) RETURNING %s", 
                TableQuery.TABLE_NAME,
                TableQuery.COLUMN_CLIENT_ID,
                TableQuery.COLUMN_CPU_LOAD_PERCENT,
                TableQuery.COLUMN_RAM_LOAD_GB,
                TableQuery.COLUMN_DISK_LOAD_GB,
                TableClient.COLUMN_ID,
                TableClient.TABLE_NAME,
                TableClient.COLUMN_IP,
                ipAddress,
                loadInfo.getLoadCPU(),
                loadInfo.getLoadRAM(),
                loadInfo.getLoadDisk(),
                TableQuery.COLUMN_ID);
        statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);
        String idQuery = "0";
        while(rs.next())
        {
            idQuery = rs.getString(TableQuery.COLUMN_ID);
        }

        statement.close();
        
        for(ProcessInfo process: loadInfo.getListProcessInfo())
        {
            query = String.format("insert into %s (%s,%s,%s,%s,%s) values ('%s','%s','%s','%s','%s')",
                TableProcess.TABLE_NAME,
                TableProcess.COLUMN_NAME,
                TableProcess.COLUMN_CPU_LOAD_PERCENT,
                TableProcess.COLUMN_RAM_LOAD_MB,
                TableProcess.COLUMN_QUERY_ID,
                TableProcess.COLUMN_PID,
                process.getName(),
                process.getCpuLoadPercent(),
                process.getRamUsed(),
                idQuery,
                process.getPID());
            statement = con.createStatement();
            statement.executeUpdate(query);

            statement.close();
        }
    }
}
