package DataExchange;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class HardwareInfo implements Serializable{
    String infoCPU, infoDisk, infoRAM, infoGPU;

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

    public void setInfoCPU(String infoCPU) {
        this.infoCPU = infoCPU;
    }

    public void setInfoDisk(String infoDisk) {
        this.infoDisk = infoDisk;
    }

    public void setInfoRAM(String infoRAM) {
        this.infoRAM = infoRAM;
    }

    public void setInfoGPU(String infoGPU) {
        this.infoGPU = infoGPU;
    }
       
}
