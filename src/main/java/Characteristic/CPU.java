package Characteristic;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;

/**
 *
 * @author Admin
 */
public class CPU {
    private final CentralProcessor processor;
    private long[] oldTicks;
    
    public CPU(SystemInfo si)
    {
        this.processor = si.getHardware().getProcessor();
        oldTicks = new long[TickType.values().length];
    }
    
    public String getInfo()
    {
        return processor.getProcessorIdentifier().getName();
    }
    
     public String getPercentUsageCPU()
     {
         double usage = processor.getSystemCpuLoadBetweenTicks(oldTicks) * 100d;
         oldTicks = processor.getSystemCpuLoadTicks();
         return String.format("%.2f%%", usage);
     }
    
}
