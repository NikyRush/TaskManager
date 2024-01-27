package Characteristic;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class ProcessInfo implements Serializable{
    private final int pid;
    private final String name;
    private final double cpuLoadPercent;
    private final double ramUsed;

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

    public ProcessInfo(int pid, String name, double cpuLoadPercent, long ramUsed) {
        this.pid = pid;
        this.name = name;
        this.cpuLoadPercent = CONST.Round(cpuLoadPercent);
        this.ramUsed = CONST.BytesToMegabytes(ramUsed);
    }
}
