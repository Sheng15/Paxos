package server;


import java.io.IOException;
import java.util.ArrayList;

public class State {

    public int processCount;
    private ArrayList<ProcessModel> connectedProcesses;

    public State() {
        this.processCount = 0;
        this.connectedProcesses = new ArrayList<>();
    }

    public ArrayList<ProcessModel> getConnectedProcesses() {
        return connectedProcesses;
    }

    public void addProcess(ProcessModel process) {
        connectedProcesses.add(process);
    }


    public void removeProcess(ProcessModel process) throws IOException {
        connectedProcesses.remove(process);
        process.getIs().close();
        process.getOs().close();
    }

    public void removeProcess(String processName) {
        connectedProcesses.stream()
                .filter(processModel -> processModel.getProcessName().equals(processName))
                .findFirst()
                .ifPresent(processModel -> {
                    try {
                        removeProcess(processModel);
                        System.out.println("Removing: " + processModel.getProcessName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
