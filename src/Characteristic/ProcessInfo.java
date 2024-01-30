package Characteristic;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class ProcessInfo implements Serializable{
    private int pid;
    private String name;
    private double cpuLoadPercent;
    private double ramUsed;

    //Default constructor
    public ProcessInfo(){}

    //Only for Processes
    public ProcessInfo(int pid, String name, double cpuLoadPercent, long ramUsed) {
        this.pid = pid;
        this.name = name;
        this.cpuLoadPercent = CONST.Round(cpuLoadPercent);
        this.ramUsed = CONST.BytesToMegabytes(ramUsed);
    }
    
    public int getPID() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public double getCpuLoadPercent() {
        return cpuLoadPercent;
    }

    public double getRamUsed() {
        return ramUsed;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCpuLoadPercent(double cpuLoadPercent) {
        this.cpuLoadPercent = cpuLoadPercent;
    }

    public void setRamUsed(double ramUsed) {
        this.ramUsed = ramUsed;
    }
    
}
