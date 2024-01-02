package Characteristic;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

/**
 *
 * @author Admin
 */
public class CPU {
    private String name;
    private CentralProcessor processor;
    
    public CPU(SystemInfo si)
    {
        this.processor = si.getHardware().getProcessor();
        this.name = processor.getProcessorIdentifier().getName();
    }
    
    public String getName()
    {
        return name;
    }
    
    public CentralProcessor getProcessor()
    {
        return processor;
    }
}
