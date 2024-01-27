package DataExchange;

import Characteristic.ProcessInfo;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class LoadInfo implements Serializable{
    double loadRAM, loadDisk;
    ArrayList<ProcessInfo> processes;

    public LoadInfo(double loadRAM, double loadDisk, ArrayList<ProcessInfo> processes) {
        this.loadRAM = loadRAM;
        this.loadDisk = loadDisk;
        this.processes = processes;
    }

    public double getLoadCPU() {
        Double load = 0.0;
        for(ProcessInfo process: processes)
            load += process.getCpuLoadPercent();
        return load;
    }

    public double getLoadRAM() {
        return loadRAM;
    }

    public double getLoadDisk() {
        return loadDisk;
    }

    public ArrayList<ProcessInfo> getProcesses() {
        return processes;
    }
    
}
