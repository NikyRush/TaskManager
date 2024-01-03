package Characteristic;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.util.FormatUtil;

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
    
    public String getTotalSpace()
    {
        return FormatUtil.formatBytes(diskStore.getSize());
    }
    
    public String getUsedSpace()
    {
        long used = 0;
        try {
            for (FileStore store : FileSystems.getDefault().getFileStores())
                used += store.getTotalSpace() - store.getUsableSpace();
        } catch (IOException e) {
            used = 0;
        }
        return FormatUtil.formatBytes(used);
    }
}
