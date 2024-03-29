package Characteristic;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;

/**
 *
 * @author Admin
 */
public class Disk {
    private final HWDiskStore diskStore;
    
    public Disk(SystemInfo si)
    {
        diskStore = si.getHardware().getDiskStores().getFirst();
    }

    public String getInfo()
    {
        return diskStore.getModel();
    }
    
    public double getTotalSpace()
    {
        return CONST.BytesToGigabytes(diskStore.getSize());
    }
    
    public double getUsedSpace()
    {
        long used = 0;
        try {
            for (FileStore store : FileSystems.getDefault().getFileStores())
                used += store.getTotalSpace() - store.getUsableSpace();
        } catch (IOException e) {
            used = 0;
        }
        return CONST.BytesToGigabytes(used);
    }
}
