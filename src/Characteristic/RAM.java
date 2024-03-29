
package Characteristic;

import cn.hutool.core.io.unit.DataSizeUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.PhysicalMemory;

/**
 *
 * @author Admin
 */
public class RAM {
    private final GlobalMemory memory;
    
    public RAM(SystemInfo si)
    {
        this.memory = si.getHardware().getMemory();
    }
    
    public double getTotalSpace()
    {
        return CONST.BytesToGigabytes(memory.getTotal());
    }

    public double getUsedSpace() {
        return CONST.BytesToGigabytes(memory.getTotal() - memory.getAvailable()); 
    }
    
    /**
     * @return - Информация в виде строки 
     * для каждой плашки памяти отдельно 
     * (Производитель + Тип памяти + Частота + Объём)
     */
    public String getInfo()
    {
        ArrayList<String> detailList = new ArrayList<>();

        StringBuilder detailBuilder;
        for (PhysicalMemory physicalMemory : memory.getPhysicalMemory()) {
            detailBuilder = new StringBuilder();
            detailBuilder.append(physicalMemory.getManufacturer()); 
            detailBuilder.append(" ").append(physicalMemory.getMemoryType()); 
            detailBuilder.append(" ").append(new BigDecimal(physicalMemory.getClockSpeed()).divide(new BigDecimal(1000000), 0, RoundingMode.HALF_UP)).append("MHz");
            detailBuilder.append(" ").append(DataSizeUtil.format(physicalMemory.getCapacity()));
            detailList.add(detailBuilder.toString());
        }
        return String.join(" + ", detailList);
    }
}
