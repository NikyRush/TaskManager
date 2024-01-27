
package PostgreDB;

/**
 *
 * @author Admin
 */
public final class StructureDB {
    private StructureDB(){}
    
    public static class TableClient
    {
        public static final String TABLE_NAME = "client";
        public static final String COLUMN_ID = "id_client";
        public static final String COLUMN_IP = "ip_address";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PORT = "port";
        public static final String COLUMN_CPU_INFO = "cpu_info";
        public static final String COLUMN_RAM_INFO = "ram_info";
        public static final String COLUMN_DISK_INFO = "disk_info";
        public static final String COLUMN_GPU_INFO = "gpu_info";
    }
    
    public static class TableProcess
    {
        public static final String TABLE_NAME = "process";
        public static final String COLUMN_ID = "id_process";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CPU_LOAD_PERCENT = "cpu_load_percent";
        public static final String COLUMN_RAM_LOAD_MB = "ram_load_mb";
        public static final String COLUMN_QUERY_ID = "query_id";
        public static final String COLUMN_PID = "pid_process";
    }
    
    public static class TableQuery
    {
        public static final String TABLE_NAME = "query";
        public static final String COLUMN_ID = "id_query";
        public static final String COLUMN_CLIENT_ID = "client_id";
        public static final String COLUMN_DATE_TIME = "date_time";
        public static final String COLUMN_CPU_LOAD_PERCENT = "cpu_load_percent";
        public static final String COLUMN_RAM_LOAD_GB = "ram_load_gb";
        public static final String COLUMN_DISK_LOAD_GB = "disk_load_gb";
    }
    
    public static class TableError
    {
        public static final String TABLE_NAME = "error";
        public static final String COLUMN_ID = "id_error";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE_TIME = "date_time";
        public static final String COLUMN_CLIENT_ID = "client_id";
    }
}
