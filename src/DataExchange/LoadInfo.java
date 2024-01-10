package DataExchange;

import Characteristic.Processes;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class LoadInfo implements Serializable{
    double loadCPU, loadRAM, loadDisk;
    ArrayList<Processes.ProcessInfo> processes;

    public LoadInfo(double loadCPU, double loadRAM, double loadDisk, ArrayList<Processes.ProcessInfo> processes) {
        this.loadCPU = loadCPU;
        this.loadRAM = loadRAM;
        this.loadDisk = loadDisk;
        this.processes = processes;
    }

    public double getLoadCPU() {
        return loadCPU;
    }

    public double getLoadRAM() {
        return loadRAM;
    }

    public double getLoadDisk() {
        return loadDisk;
    }

    public ArrayList<Processes.ProcessInfo> getProcesses() {
        return processes;
    }
    
}
