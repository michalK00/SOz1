package zad4.Algorithms;

import zad4.Frame;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PPF extends Algorithm implements AlgorithmInterface{
    private double upperBound;
    private double lowerBound;
    private int timeWindow;
    int numberOfHaltedProcesses = 0;

    public PPF(int numberOfFrames, ArrayDeque<Integer>[] processesTable, ArrayDeque<Integer> generatedSequenceOfProcesses, double upperBound, double lowerBound, int timeWindow) {
        super(numberOfFrames, processesTable, generatedSequenceOfProcesses);
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
        this.timeWindow = timeWindow;
    }

    @Override
    public void simulate() {
        double ppf = 0;

        boolean[] processHalted = new boolean[getProcessesTable().length];
        boolean timeWindowReached = false;
        int time = 0;


        List<Integer>[] numberOfPageErrorsForEachProcessInTimeWindow =  new List[getProcessesTable().length];
        for(int x = 0; x < numberOfPageErrorsForEachProcessInTimeWindow.length; x++){
            numberOfPageErrorsForEachProcessInTimeWindow[x] = new ArrayList<>();
        }

        int[] numberOfFramesPerProcess = calculateNumberOfFramesPerProcess();

        while(!getGeneratedSequenceOfProcesses().isEmpty()){

            boolean dequeuedProcessIsHalted = true;
            int nextProcess = -1;

            while(dequeuedProcessIsHalted){
                nextProcess = getGeneratedSequenceOfProcesses().poll();
                if(processHalted[nextProcess]){
                    getGeneratedSequenceOfProcesses().add(nextProcess);
                } else {
                    dequeuedProcessIsHalted = false;
                }
            }

            int nextAppeal = getProcessesTable()[nextProcess].poll();


            if(getHowManyFramesDoesAProcessHave(nextProcess) == numberOfFramesPerProcess[nextProcess]){

                if(!appealIsInMemory(nextAppeal)){

                    int frameIndex = getFrameIndexWithPageThatHasTheLongestTimeInMemory(nextProcess);
                    getFramesTab()[frameIndex].setCurrentAppeal(nextAppeal);
                    getFramesTab()[frameIndex].setCurrentProcess(nextProcess);
                    getFramesTab()[frameIndex].setCounter(0);
                    incrementNumberOfPageErrors();
                    numberOfPageErrorsForEachProcessInTimeWindow[nextProcess].add(time);
                    numberOfPageErrorsForEachProcess[nextProcess]++;

                }else {
                    for (Frame f: getFramesTab()) {

                        if((f.getCurrentAppeal() == nextAppeal) && (f.getCurrentProcess() == nextProcess)){
                            f.setCounter(0);
                            break;
                        }
                    }
                }

            } else if (getHowManyFramesDoesAProcessHave(nextProcess) < numberOfFramesPerProcess[nextProcess]){

                if(!appealIsInMemory(nextAppeal)){
                    for (Frame f: getFramesTab()) {

                        if(f.getCurrentProcess() == -1 && f.getCurrentAppeal() == -1){
                            incrementNumberOfPageErrors();
                            numberOfPageErrorsForEachProcessInTimeWindow[nextProcess].add(time);
                            numberOfPageErrorsForEachProcess[nextProcess]++;
                            f.setCurrentAppeal(nextAppeal);
                            f.setCurrentProcess(nextProcess);
                            f.setCounter(0);
                            break;
                        }
                    }
                }else {
                    for (Frame f: getFramesTab()) {
                        if((f.getCurrentAppeal() == nextAppeal) && (f.getCurrentProcess() == nextProcess)){
                            f.setCounter(0);
                            break;
                        }
                    }
                }
            }

            for (Frame f: getFramesTab()) {
                if(f.getCurrentAppeal() != -1){
                    f.setCounter(f.getCounter()+1);
                }
            }



            //usuwanie błędów strony które są poza oknem czasowym
            if(timeWindowReached){
                for(int x = 0; x < numberOfPageErrorsForEachProcessInTimeWindow.length; x++){
                    Iterator iterator = numberOfPageErrorsForEachProcessInTimeWindow[x].iterator();
                    while(iterator.hasNext()){
                        int i = (int) iterator.next();
                        if(i < time - timeWindow){
                            iterator.remove();
                        }
                    }
                }
            }

            if(timeWindowReached){
                for(int x = 0; x < getProcessesTable().length; x++){
                    ppf = (double) numberOfPageErrorsForEachProcessInTimeWindow[x].size()/timeWindow;
                    if(ppf > upperBound){

                        if(atLeastOnePageIsEmpty()){

                            for(int y = 0; y < getFramesTab().length; y++){
                                if(getFramesTab()[y].getCurrentProcess() == -1){
                                    getFramesTab()[y].setCurrentProcess(x);
                                    numberOfFramesPerProcess[x]++;
                                    break;
                                }
                            }
                        } else {
                            processHalted[x] = true;
                            numberOfHaltedProcesses++;
                            for(int y = 0; y < getFramesTab().length; y++){
                                if(getFramesTab()[y].getCurrentProcess() == x){
                                    getFramesTab()[y].setCurrentProcess(-1);
                                    getFramesTab()[y].setCurrentAppeal(-1);
                                    getFramesTab()[y].setCounter(0);
                                }
                            }
                        }

                    } else if (ppf < lowerBound){
                        if(numberOfFramesPerProcess[x] > 1){
                            numberOfFramesPerProcess[x]--;
                            for(int y = 0; y < getFramesTab().length; y++){
                                if(getFramesTab()[y].getCurrentProcess() == x){
                                    getFramesTab()[y].setCurrentProcess(-1);
                                    getFramesTab()[y].setCurrentAppeal(-1);
                                    getFramesTab()[y].setCounter(0);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            //jeśli proces się zakończył zwalniamy jego ramki i wznawiamy jeden wstrzymany proces
            if(getProcessesTable()[nextProcess].isEmpty()){
                for(int x = 0; x < getFramesTab().length; x++){
                    if(getFramesTab()[x].getCurrentProcess() == nextProcess){
                        getFramesTab()[x].setCurrentProcess(-1);
                        getFramesTab()[x].setCurrentAppeal(-1);
                        getFramesTab()[x].setCounter(0);
                    }
                }
                for(int x = 0; x < processHalted.length; x++){
                    if(processHalted[x]){
                        processHalted[x] = false;
                        if(countFreeFrames() < numberOfFramesPerProcess[x]){
                            numberOfFramesPerProcess[x] = countFreeFrames();
                        }
                        break;
                    }
                }
            }

            if(time >= timeWindow){
                timeWindowReached = true;
            }
            time++;

        }
    }

    private int[] calculateNumberOfFramesPerProcess(){

        int[] framesPerProcess = new int[getProcessesTable().length];

        ArrayDeque<Integer>[] uniquePagesTable = new ArrayDeque[getProcessesTable().length];
        for(int x = 0; x < getProcessesTable().length; x++){
            uniquePagesTable[x] = new ArrayDeque<>();
        }


        for(int x = 0; x < getProcessesTable().length; x++){
            Iterator iterator = getProcessesTable()[x].iterator();
            while(iterator.hasNext()){
                int next = (Integer) iterator.next();
                if(!uniquePagesTable[x].contains(next)){
                    uniquePagesTable[x].add(next);
                }
            }
        }
        int totalNumberOfPages = 0;
        for(int x = 0; x < uniquePagesTable.length; x++){
            totalNumberOfPages += uniquePagesTable[x].size();
        }
        for(int x = 0; x < uniquePagesTable.length; x++){
            if(totalNumberOfPages != 0){
                framesPerProcess[x] = (uniquePagesTable[x].size()* getNumberOfFrames()) / totalNumberOfPages ;
            } else {
                framesPerProcess[x] = 0;
            }
        }


        return framesPerProcess;
    }

    private int countFreeFrames(){
        int i = 0;
        for(int x = 0; x < getFramesTab().length; x++){
            if(getFramesTab()[x].getCurrentProcess() == -1){
                i++;
            }
        }

        return i;
    }


    @Override
    public void stats() {
        System.out.println("PPF: ");
        printStats();
        System.out.println("Halted processes: " + numberOfHaltedProcesses);
    }

}
