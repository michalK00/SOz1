package zad5.Algorithms;

import zad5.CPU;
import zad5.Process;
import zad5.Utils;

import java.util.ArrayList;

import java.util.ListIterator;
import java.util.Random;

public class ThirdStrategy extends Algorithm implements AlgorithmInterface{


    private int cpuLoadBound;
    private int numberOfTimesProcessorHelped;

    public ThirdStrategy(ArrayList<CPU> cpuList, int cpuLoadBound) {
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
                if(cpuList.get(x).getCurrentLoad() < cpuLoadBound){
                    int cpuToHelpIndex = findCpuToHelp(x);
                    if(cpuToHelpIndex != -1){
                        takeTaskFromOverloadedProcessor(x, cpuToHelpIndex);
                    }
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
        utils.createOutputChart(cpuStats, "three");
    }
    private int findCpuToTakeTheTask(int askingProcessorNumber){

        if(cpuList.get(askingProcessorNumber).getCurrentLoad() < cpuLoadBound){
            return askingProcessorNumber;
        } else{
            for(int x = 0; x < cpuList.size(); x++){
                numberOfInquiries++;
                if(cpuList.get(x).getCurrentLoad() < cpuLoadBound){
                    numberOfPassedProcesses++;
                    return x;
                }
            }
        }
        return askingProcessorNumber;


    }
    private int findCpuToHelp(int askingProcessorNumber){
        Random random = new Random();

        int a = 0;
        while(a < cpuList.size()){
            int x = random.nextInt(cpuList.size());
            if(x != askingProcessorNumber){
                numberOfInquiries++;
                if(cpuList.get(x).getCurrentLoad() > cpuLoadBound){
                    return x;
                }
            }
            a++;
        }
        return -1;
    }
    private void takeTaskFromOverloadedProcessor(int helpingProcIndex, int overloadedProcIndex){
        Process processToTake = findBiggestProcess(overloadedProcIndex);
        boolean wasOverloaded = false;
        if(cpuList.get(helpingProcIndex).getCurrentLoad() > 100){
            wasOverloaded = true;
        }
        cpuList.get(overloadedProcIndex).getCpuProcessListInService().remove(processToTake);
        cpuList.get(helpingProcIndex).getCpuProcessListInService().add(processToTake);
        cpuList.get(overloadedProcIndex).setCurrentLoad(cpuList.get(overloadedProcIndex).getCurrentLoad() - processToTake.getCpuLoad());
        cpuList.get(helpingProcIndex).setCurrentLoad(cpuList.get(helpingProcIndex).getCurrentLoad() + processToTake.getCpuLoad());
        if(cpuList.get(helpingProcIndex).getCurrentLoad() > 100 && !wasOverloaded){
            numberOfTimesThatProcessorsWereOverloaded++;
        }
        numberOfPassedProcesses++;
        numberOfTimesProcessorHelped++;
    }
    private Process findBiggestProcess(int procIndex){
        Process returnedProcess = null;
        int biggestLoad = 0;
        for(int x = 0; x < cpuList.get(procIndex).getCpuProcessListInService().size(); x++){
            if(biggestLoad < cpuList.get(procIndex).getCpuProcessListInService().get(x).getCpuLoad()){
                biggestLoad = cpuList.get(procIndex).getCpuProcessListInService().get(x).getCpuLoad();
                returnedProcess = cpuList.get(procIndex).getCpuProcessListInService().get(x);
            }
        }
        return returnedProcess;
    }
    @Override
    protected void printAllStats(){
        super.printAllStats();
        System.out.println("Number of times that processors helped another processor: " + numberOfTimesProcessorHelped);
    }
}
