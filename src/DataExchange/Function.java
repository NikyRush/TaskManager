package DataExchange;

import Client.Controller;

/**
 *
 * @author Admin
 */
public class Function implements FunctionMBean{
    private final Controller controller;

    public Function(Controller controller) {
        this.controller = controller;
    }
    
    @Override
    public HardwareInfo returnHardwareInfo() {
        HardwareInfo hardwareInfo = new HardwareInfo();
        
        hardwareInfo.setInfoCPU(controller.getCPU().getInfo());
        hardwareInfo.setInfoGPU(controller.getGPU().getInfo());
        hardwareInfo.setInfoRAM(controller.getRAM().getInfo());
        hardwareInfo.setInfoDisk(controller.getDisk().getInfo());

        return hardwareInfo;
    }

    @Override
    public LoadInfo returnLoadInfo() {
        LoadInfo loadInfo =  new LoadInfo();
        
        loadInfo.setLoadRAM(controller.getRAM().getUsedSpace());
        loadInfo.setLoadDisk(controller.getDisk().getUsedSpace());
        loadInfo.setListProcessInfo(controller.getProcesses());
        
        return loadInfo;
    }
    
}
