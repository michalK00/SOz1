package zad5;

import java.util.ArrayList;

public class CPU {
    private int currentLoad;
    private int generateInterval;
    private ArrayList<Process> cpuProcessList;
    private ArrayList<Process> cpuProcessListInService;
    private ArrayList<Process> cpuProcessListCopy;

    public CPU(int generateInterval) {
        this.generateInterval = generateInterval;
        currentLoad = 0;
        cpuProcessList = new ArrayList<>();
        cpuProcessListInService = new ArrayList<>();
        cpuProcessListCopy = new ArrayList<>();
    }

    public ArrayList<Process> getCpuProcessListInService() {
        return cpuProcessListInService;
    }

    public void setCpuProcessListInService(ArrayList<Process> cpuProcessListInService) {
        this.cpuProcessListInService = cpuProcessListInService;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }

    public int getGenerateInterval() {
        return generateInterval;
    }

    public void setGenerateInterval(int generateInterval) {
        this.generateInterval = generateInterval;
    }

    public ArrayList<Process> getCpuProcessList() {
        return cpuProcessList;
    }

    public void setCpuProcessList(ArrayList<Process> cpuProcessList) {
        this.cpuProcessList = cpuProcessList;
    }

    public ArrayList<Process> getCpuProcessListCopy() {
        return cpuProcessListCopy;
    }

    public void setCpuProcessListCopy(ArrayList<Process> cpuProcessListCopy) {
        this.cpuProcessListCopy = cpuProcessListCopy;
    }
}
