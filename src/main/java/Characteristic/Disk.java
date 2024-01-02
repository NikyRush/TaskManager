package Characteristic;

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
        long used = (diskStore.getWriteBytes() + diskStore.getReadBytes()) / 2;
        return FormatUtil.formatBytes(used);
    }
}
