package zad5.Algorithms;

import zad5.CPU;
import zad5.Process;
import zad5.Utils;

import java.util.ArrayList;
import java.util.ListIterator;

public class FirstStrategy extends Algorithm implements AlgorithmInterface{


    private int cpuLoadBound;

    public FirstStrategy(ArrayList<CPU> cpuList, int cpuLoadBound) {
        super(cpuList);
        this.cpuLoadBound = cpuLoadBound;
    }

    @Override
    public void simulate() {
        int time = 0;

        while(!allCpuListsAreEmpty()){
            for(int x = 0; x < cpuList.size(); x++){

                if(!cpuList.get(x).getCpuProcessList().isEmpty() && cpuList.get(x).getCpuProcessList().get(0).getArrivalTime() == time){
                    Process processToService = cpuList.get(x).getCpuProcessList().get(0);
                    cpuList.get(x).getCpuProcessList().remove(0);
                    int cpuIndex = findCpuToTakeTheTask(x);

                    processToService.setDuringService(true);
                    cpuList.get(cpuIndex).getCpuProcessListInService().add(processToService);
                    if(cpuList.get(cpuIndex).getCurrentLoad() < 100 && cpuList.get(cpuIndex).getCurrentLoad() + processToService.getCpuLoad() > 100){
                        numberOfTimesThatProcessorsWereOverloaded++;
                    }
                    cpuList.get(cpuIndex).setCurrentLoad(cpuList.get(cpuIndex).getCurrentLoad() + processToService.getCpuLoad());
                }

            }
            for(int x = 0; x < cpuList.size(); x++){
                for(int process = 0; process < cpuList.get(x).getCpuProcessListInService().size(); process++){
                    int processTimeLeft = cpuList.get(x).getCpuProcessListInService().get(process).getTimeLeft();
                    cpuList.get(x).getCpuProcessListInService().get(process).setTimeLeft(processTimeLeft-1);

                }
                ListIterator<Process> iterator = cpuList.get(x).getCpuProcessListInService().listIterator();
                while(iterator.hasNext()){
                    Process nextProcess = iterator.next();
                    if(nextProcess.getTimeLeft() == 0){
                        nextProcess.setTimeLeft(nextProcess.getDurationTime());

                        cpuList.get(x).setCurrentLoad(cpuList.get(x).getCurrentLoad() - nextProcess.getCpuLoad());
                        iterator.remove();
                    }
                }
                cpuStats[x].add(cpuList.get(x).getCurrentLoad());
            }
            time++;
        }
        printAllStats();
        Utils utils = new Utils();
        utils.createOutputChart(cpuStats, "one");
    }
    private int findCpuToTakeTheTask(int askingProcessorNumber){

        for(int x = 0; x < cpuList.size(); x++){
            if(x != askingProcessorNumber){
                numberOfInquiries++;
                if(cpuList.get(x).getCurrentLoad() < cpuLoadBound){
                    numberOfPassedProcesses++;
                    return x;
                }
            }
        }
        return askingProcessorNumber;

    }
}
