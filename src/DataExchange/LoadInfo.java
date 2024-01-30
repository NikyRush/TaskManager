package DataExchange;

import Characteristic.CONST;
import Characteristic.ProcessInfo;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class LoadInfo implements Serializable{
    double loadRAM, loadDisk;
    ArrayList<ProcessInfo> listProcessInfo;

    public double getLoadCPU() {
        Double load = 0.0;
        for(ProcessInfo process: listProcessInfo)
            load += process.getCpuLoadPercent();
        return CONST.Round(load);
    }

    public double getLoadRAM() {
        return loadRAM;
    }

    public double getLoadDisk() {
        return loadDisk;
    }

    public ArrayList<ProcessInfo> getListProcessInfo() {
        return listProcessInfo;
    }

    public void setLoadRAM(double loadRAM) {
        this.loadRAM = CONST.Round(loadRAM);
    }

    public void setLoadDisk(double loadDisk) {
        this.loadDisk = CONST.Round(loadDisk);
    }

    public void setListProcessInfo(ArrayList<ProcessInfo> listProcessInfo) {
        this.listProcessInfo = listProcessInfo;
    }
}
