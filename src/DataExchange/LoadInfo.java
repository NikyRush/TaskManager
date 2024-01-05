package DataExchange;

import Characteristic.Processes;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class LoadInfo {
    String loadCPU, loadRAM, loadDisk;
    ArrayList<Processes.ProcessInfo> processes;

    public LoadInfo(String loadCPU, String loadRAM, String loadDisk, ArrayList<Processes.ProcessInfo> processes) {
        this.loadCPU = loadCPU;
        this.loadRAM = loadRAM;
        this.loadDisk = loadDisk;
        this.processes = processes;
    }

    public String getLoadCPU() {
        return loadCPU;
    }

    public String getLoadRAM() {
        return loadRAM;
    }

    public String getLoadDisk() {
        return loadDisk;
    }

    public ArrayList<Processes.ProcessInfo> getProcesses() {
        return processes;
    }
    
}
