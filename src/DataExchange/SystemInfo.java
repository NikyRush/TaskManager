package DataExchange;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class SystemInfo implements Serializable{
    String infoCPU, infoDisk, infoRAM, infoGPU;

    public SystemInfo(String infoCPU, String infoDisk, String infoRAM, String infoGPU) {
        this.infoCPU = infoCPU;
        this.infoDisk = infoDisk;
        this.infoRAM = infoRAM;
        this.infoGPU = infoGPU;
    }

    public String getInfoCPU() {
        return infoCPU;
    }

    public String getInfoDisk() {
        return infoDisk;
    }

    public String getInfoRAM() {
        return infoRAM;
    }

    public String getInfoGPU() {
        return infoGPU;
    }
    
    
}
