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
    public SystemInfo returnSystemInfo() {
        return new SystemInfo(
        controller.getCPU().getInfo(),
        controller.getDisk().getInfo(),
        controller.getRAM().getInfo(),
        controller.getGPU().getInfo()
        );
    }

    @Override
    public LoadInfo returnLoadInfo() {
        return new LoadInfo(
            controller.getCPU().getPercentUsageCPU(),
            controller.getRAM().getUsedSpace(),
            controller.getDisk().getUsedSpace(),
            controller.getProcesses()
        );
    }
    
}
