
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
    
    public static final String SQL_CREATE_CLIENT = String.format(
            "CREATE TABLE IF NOT EXISTS public.%s(" +
            "    %s bigserial NOT NULL,\n" +
            "    %s cidr NOT NULL,\n" +
            "    %s integer NOT NULL DEFAULT 1099,\n" +
            "    %s text COLLATE pg_catalog.\"default\" NOT NULL DEFAULT 'cpu'::text,\n" +
            "    %s text COLLATE pg_catalog.\"default\" NOT NULL DEFAULT 'ram'::text,\n" +
            "    %s text COLLATE pg_catalog.\"default\" NOT NULL DEFAULT 'disk'::text,\n" +
            "    %s text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
            "    %s text COLLATE pg_catalog.\"default\" NOT NULL DEFAULT 'gpu'::text,\n" +
            "    CONSTRAINT client_pkey PRIMARY KEY (%s),\n" +
            "    CONSTRAINT unique_ip_address UNIQUE (%s)\n" +
            ")",
            TableClient.TABLE_NAME,
            TableClient.COLUMN_ID,
            TableClient.COLUMN_IP,
            TableClient.COLUMN_PORT,
            TableClient.COLUMN_CPU_INFO,
            TableClient.COLUMN_RAM_INFO,
            TableClient.COLUMN_DISK_INFO,
            TableClient.COLUMN_NAME,
            TableClient.COLUMN_GPU_INFO,
            TableClient.COLUMN_ID,
            TableClient.COLUMN_IP);
    
    public static final String SQL_CREATE_QUERY = String.format(
        "CREATE TABLE IF NOT EXISTS public.%s(" +
        "    %s bigserial NOT NULL,\n" +
        "    %s integer NOT NULL,\n" +
        "    %s timestamp with time zone NOT NULL DEFAULT (now())::timestamp(0) without time zone,\n" +
        "    %s real,\n" +
        "    %s real,\n" +
        "    %s real,\n" +
        "    CONSTRAINT query_pkey PRIMARY KEY (%s),\n" +
        "    CONSTRAINT fk_client_id FOREIGN KEY (%s)\n" +
        "        REFERENCES public.%s (%s) MATCH SIMPLE\n" +
        "        ON UPDATE NO ACTION\n" +
        "        ON DELETE NO ACTION\n" +
        ")"
        ,
        TableQuery.TABLE_NAME,
        TableQuery.COLUMN_ID,
        TableQuery.COLUMN_CLIENT_ID,
        TableQuery.COLUMN_DATE_TIME,
        TableQuery.COLUMN_CPU_LOAD_PERCENT,
        TableQuery.COLUMN_RAM_LOAD_GB,
        TableQuery.COLUMN_DISK_LOAD_GB,
        TableQuery.COLUMN_ID,
        TableQuery.COLUMN_CLIENT_ID,
        TableClient.TABLE_NAME,
        TableClient.COLUMN_ID);
    
    public static final String SQL_CREATE_PROCESS = String.format(
        "CREATE TABLE IF NOT EXISTS public.%s(" +
        "    %s bigserial NOT NULL,\n" +
        "    %s text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
        "    %s real NOT NULL DEFAULT 0,\n" +
        "    %s real NOT NULL DEFAULT 0,\n" +
        "    %s integer NOT NULL,\n" +
        "    %s integer NOT NULL DEFAULT 0,\n" +
        "    CONSTRAINT process_pkey PRIMARY KEY (%s),\n" +
        "    CONSTRAINT fk_query_id FOREIGN KEY (%s)\n" +
        "        REFERENCES public.%s (%s) MATCH SIMPLE\n" +
        "        ON UPDATE NO ACTION\n" +
        "        ON DELETE NO ACTION\n" +
        ")",
        TableProcess.TABLE_NAME,
        TableProcess.COLUMN_ID,
        TableProcess.COLUMN_NAME,
        TableProcess.COLUMN_CPU_LOAD_PERCENT,
        TableProcess.COLUMN_RAM_LOAD_MB,
        TableProcess.COLUMN_QUERY_ID,
        TableProcess.COLUMN_PID,
        TableProcess.COLUMN_ID,
        TableProcess.COLUMN_QUERY_ID,
        TableQuery.TABLE_NAME,
        TableQuery.COLUMN_ID
    );
    
    public static final String SQL_CREATE_ERROR = String.format(
        "CREATE TABLE IF NOT EXISTS public.%s(" +
        "    %s bigserial NOT NULL,\n" +
        "    %s text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
        "    %s integer NOT NULL,\n" +
        "    %s timestamp with time zone NOT NULL DEFAULT (now())::timestamp(0) without time zone,\n" +
        "    CONSTRAINT error_pkey PRIMARY KEY (%s),\n" +
        "    CONSTRAINT fk_client_id FOREIGN KEY (%s)\n" +
        "        REFERENCES public.%s (%s) MATCH SIMPLE\n" +
        "        ON UPDATE NO ACTION\n" +
        "        ON DELETE NO ACTION\n" +
        ")",
        TableError.TABLE_NAME,
        TableError.COLUMN_ID,
        TableError.COLUMN_NAME,
        TableError.COLUMN_CLIENT_ID,
        TableError.COLUMN_DATE_TIME,
        TableError.COLUMN_ID,
        TableError.COLUMN_CLIENT_ID,
        TableClient.TABLE_NAME,
        TableClient.COLUMN_ID
    );
}
