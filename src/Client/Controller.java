package Client;

import Characteristic.CONST;
import Characteristic.CPU;
import Characteristic.Disk;
import Characteristic.GPU;
import Characteristic.Processes;
import Characteristic.RAM;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import oshi.SystemInfo;
import oshi.util.FormatUtil;

/**
 *
 * @author Admin
 */
public class Controller {
    private final CPU cpu;
    private final RAM ram;
    private final Disk disk;
    private final GPU gpu;
    private final Processes processes;
    
    private static Controller instance;
    
    private Controller(SystemInfo si)
    {
        cpu = new CPU(si);
        ram = new RAM(si);
        disk = new Disk(si);
        gpu = new GPU(si);
        processes = new Processes(si.getOperatingSystem(), cpu.getCountLogicalCPU());
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

    public ArrayList<Processes.ProcessInfo> getProcesses() {
        return processes.getProcesses();
    }

    public void UpdateTableProcesses(DefaultTableModel model)
    {
        model.setRowCount(0);

        Object data[] = new Object[4];
        
        for(var process: processes.getProcesses())
        {
            data[0] = process.getPID();
            data[1] = process.getName();
            data[2] = CONST.Round(process.getCpuLoadPercent());
            data[3] = CONST.BytesToMegabytes(process.getRamUsed());
            model.addRow(data);
        }
    }
    
    public void startListeningPort() throws Exception
    {
        new ClientThread("Client", instance).start();
    }
}
