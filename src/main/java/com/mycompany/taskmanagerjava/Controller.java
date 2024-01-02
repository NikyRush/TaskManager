package com.mycompany.taskmanagerjava;

import Characteristic.CPU;
import Characteristic.Disk;
import Characteristic.GPU;
import Characteristic.RAM;
import oshi.SystemInfo;

/**
 *
 * @author Admin
 */
public class Controller {
    private final CPU cpu;
    private final RAM ram;
    private final Disk disk;
    private final GPU gpu;
    
    private static Controller instance;
    
    private Controller(SystemInfo si)
    {
        cpu = new CPU(si);
        ram = new RAM(si);
        disk = new Disk(si);
        gpu = new GPU(si);
    }
    
    /**
     * Singltone
     * @param si - oshi.SystemInfo
     * @return object Controller (instance)
     */
    public static Controller getInstance(SystemInfo si)
    {
        if(instance == null)
            instance = new Controller(si);
        return instance;
    }

    public CPU getCPU() { return cpu; }

    public RAM getRAM() { return ram; }

    public Disk getDisk() { return disk; }

    public GPU getGPU() { return gpu; }
    
}
