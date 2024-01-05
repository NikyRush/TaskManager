package Characteristic;

import oshi.SystemInfo;
import oshi.hardware.GraphicsCard;
import oshi.util.FormatUtil;

/**
 *
 * @author Admin
 */
public class GPU {
    private final String name;
    private final long memory;
    
    public GPU(SystemInfo si)
    {
        GraphicsCard card = si.getHardware().getGraphicsCards().getFirst();
        this.name = card.getName();
        this.memory = card.getVRam();
    }

    public String getName() {
        return name;
    }

    public long getMemory() {
        return memory;
    }
    
    /**
     * @return - Возвращает информацию о видеокарте в виде строки
     */
    public String getInfo()
    {
        StringBuilder info = new StringBuilder();
        info.append(name);
        info.append(" ");
        info.append(FormatUtil.formatBytes(memory));
        return info.toString();
    }
    
}
