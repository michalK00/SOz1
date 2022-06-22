package zad5.Algorithms;

import zad5.CPU;

import java.util.ArrayList;

public abstract class Algorithm {


    protected ArrayList[] cpuStats;
    protected int numberOfInquiries = 0;
    protected int numberOfPassedProcesses = 0;
    protected int numberOfTimesThatProcessorsWereOverloaded = 0;

    protected ArrayList<CPU> cpuList;

    public Algorithm(ArrayList<CPU> cpuList) {
        this.cpuList = cpuList;
        cpuStats = new ArrayList[cpuList.size()];
        for(int x = 0; x<cpuStats.length; x++){
            cpuStats[x] = new ArrayList<>();
        }
    }

    protected boolean allCpuListsAreEmpty(){

        for (CPU cpu: cpuList) {
            if(!cpu.getCpuProcessListInService().isEmpty() || !cpu.getCpuProcessList().isEmpty()){
                return false;
            }
        }
        return true;
    }


    protected void printAllStats(){
        System.out.println("---Stats---");
        System.out.println("Number of inquiries: " + numberOfInquiries);
        System.out.println("Number of processes that were passed to another processor: " + numberOfPassedProcesses);
        System.out.println("Number of times that processors became overloaded: " + numberOfTimesThatProcessorsWereOverloaded);
        double averageProcLoad = calculateAverageProcessorLoad();
        System.out.println("Average processor load: " + averageProcLoad);
        System.out.println("Standard deviation from average processor load: " + calculateStandardDev(averageProcLoad));
    }

    private double calculateAverageProcessorLoad(){
        int i = 0;
        double sum = 0;
        for(int processor = 0; processor < cpuStats.length; processor++){
            for(int x = 0; x < cpuStats[processor].size(); x++){
                i++;
                sum = sum + (Integer) cpuStats[processor].get(x);
            }
        }
        return Math.round((sum/i)*100)/100.0;
    }
    private double calculateStandardDev(double averageProcLoad){
        int standardDeviation = 0;
        int i = 0;
        for(int processor = 0; processor < cpuStats.length; processor++){
            for(int x = 0; x < cpuStats[processor].size(); x++){
                i++;
                standardDeviation += Math.pow((Integer) cpuStats[processor].get(x) - averageProcLoad, 2);
            }
        }
        return Math.round(Math.sqrt(standardDeviation/i)*100)/100.0;
    }

}
