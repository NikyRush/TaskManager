package Characteristic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import oshi.PlatformEnum;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

/**
 *
 * @author Admin
 */
public class Processes{
    private final ArrayList<ProcessInfo> actualProcesses;
    private final Map<Integer, OSProcess> oldProcesses;
    private final int cpuLogicalCount;
    private final OperatingSystem os;
    
    public Processes(OperatingSystem os, int cpuLogicalCount)
    {
        actualProcesses = new ArrayList<>();
        oldProcesses = new HashMap<>();
        this.os = os;
        this.cpuLogicalCount = cpuLogicalCount;
    }
    
    public ArrayList<ProcessInfo> getProcesses() {
        actualProcesses.clear();
        
        int pid;
        List<OSProcess> osProcesses = os.getProcesses(null, null, 0); 
        for (OSProcess p : osProcesses) {
            pid = p.getProcessID();
            // Ignore the Idle process on Windows
            if (pid > 0 || !SystemInfo.getCurrentPlatform().equals(PlatformEnum.WINDOWS)) {
                ProcessInfo processInfo = new ProcessInfo(
                    p.getProcessID(),
                    p.getName(),
                    p.getProcessCpuLoadBetweenTicks(oldProcesses.get(pid)) / cpuLogicalCount * 100d,
                    p.getResidentSetSize());
                if(processInfo.getCpuLoadPercent() > 0 || processInfo.getRamUsed() > 100)
                    actualProcesses.add(processInfo);
            }
        }
        
        oldProcesses.clear();
        for (OSProcess p : osProcesses) {
            oldProcesses.put(p.getProcessID(), p);
        }
        
        return actualProcesses;
    }
}
