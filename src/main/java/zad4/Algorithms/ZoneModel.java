package zad4.Algorithms;

import zad4.Frame;
import zad4.PageInTimeWindow;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ZoneModel extends Algorithm implements AlgorithmInterface{

    private int numberOfHaltedProcesses;
    private int timeWindow;
    private int c;
    List<PageInTimeWindow>[] numberOfPagesForEachProcessInTimeWindow;

    public ZoneModel(int numberOfFrames, ArrayDeque<Integer>[] processesTable, ArrayDeque<Integer> generatedSequenceOfProcesses, int timeWindow, int c) {
        super(numberOfFrames, processesTable, generatedSequenceOfProcesses);
        this.timeWindow = timeWindow;
        this.c = c;
        numberOfHaltedProcesses = 0;
    }

    @Override
    public void simulate() {
        int counter = 0;

        int[] numberOfFramesPerProcess = calculateNumberOfFramesPerProcess();
        int[] startingFramesPerProcess = calculateNumberOfFramesPerProcess();
        boolean[] processHalted = new boolean[getProcessesTable().length];
        boolean timeWindowReached = false;
        int time = 0;

        numberOfPagesForEachProcessInTimeWindow =  new List[getProcessesTable().length];
        for(int x = 0; x < numberOfPagesForEachProcessInTimeWindow.length; x++){
            numberOfPagesForEachProcessInTimeWindow[x] = new ArrayList<>();
        }

        while(!getGeneratedSequenceOfProcesses().isEmpty()) {

            boolean dequeuedProcessIsHalted = true;
            int nextProcess = -1;

            while (dequeuedProcessIsHalted) {
                nextProcess = getGeneratedSequenceOfProcesses().poll();
                if (processHalted[nextProcess]) {
                    getGeneratedSequenceOfProcesses().add(nextProcess);
                } else {
                    dequeuedProcessIsHalted = false;
                }
            }

            int nextAppeal = getProcessesTable()[nextProcess].poll();


            if (getHowManyFramesDoesAProcessHave(nextProcess) == numberOfFramesPerProcess[nextProcess]) {

                if (!appealIsInMemory(nextAppeal)) {

                    int frameIndex = getFrameIndexWithPageThatHasTheLongestTimeInMemory(nextProcess);
                    getFramesTab()[frameIndex].setCurrentAppeal(nextAppeal);
                    getFramesTab()[frameIndex].setCurrentProcess(nextProcess);
                    getFramesTab()[frameIndex].setCounter(0);
                    incrementNumberOfPageErrors();
                    numberOfPagesForEachProcessInTimeWindow[nextProcess].add(new PageInTimeWindow(time, nextAppeal));
                    numberOfPageErrorsForEachProcess[nextProcess]++;

                } else {
                    for (Frame f : getFramesTab()) {

                        if ((f.getCurrentAppeal() == nextAppeal) && (f.getCurrentProcess() == nextProcess)) {
                            f.setCounter(0);
                            break;
                        }
                    }
                }

            } else if (getHowManyFramesDoesAProcessHave(nextProcess) < numberOfFramesPerProcess[nextProcess]) {

                if (!appealIsInMemory(nextAppeal)) {
                    for (Frame f : getFramesTab()) {

                        if (f.getCurrentProcess() == -1 && f.getCurrentAppeal() == -1) {
                            incrementNumberOfPageErrors();
                            numberOfPagesForEachProcessInTimeWindow[nextProcess].add(new PageInTimeWindow(time, nextAppeal));
                            numberOfPageErrorsForEachProcess[nextProcess]++;
                            f.setCurrentAppeal(nextAppeal);
                            f.setCurrentProcess(nextProcess);
                            f.setCounter(0);
                            break;
                        }
                    }
                } else {
                    for (Frame f : getFramesTab()) {
                        if ((f.getCurrentAppeal() == nextAppeal) && (f.getCurrentProcess() == nextProcess)) {
                            f.setCounter(0);
                            break;
                        }
                    }
                }
            }

            for (Frame f : getFramesTab()) {
                if (f.getCurrentAppeal() != -1) {
                    f.setCounter(f.getCounter() + 1);
                }
            }

            //usuwanie stron które są poza oknem czasowym
            if(timeWindowReached && counter == c){
                for(int x = 0; x < numberOfPagesForEachProcessInTimeWindow.length; x++){
                    Iterator iterator = numberOfPagesForEachProcessInTimeWindow[x].iterator();
                    while(iterator.hasNext()){
                        PageInTimeWindow i = (PageInTimeWindow) iterator.next();
                        if(i.getTimestamp() < time - timeWindow){
                            iterator.remove();
                        }
                    }
                }
            }
            //przydział ramek
            if(timeWindowReached && counter == c){
                int d = 0;
                List<Integer> wss = new ArrayList<>();
                for(int x = 0; x < getProcessesTable().length; x++){
                    if(!processHalted[x]){
                        wss.add(Math.max(1, calculateUniquePagesGeneratedByProcessInTimeWindow(x)));
                        d += Math.max(1, calculateUniquePagesGeneratedByProcessInTimeWindow(x));
                    } else{
                        wss.add(0);
                    }

                }
                if(d>getNumberOfFrames()){
                    while(d>getNumberOfFrames()){
                        int processWithTheLeastAmountOfFrames = -1;
                        int leastAmountOfFrames = Integer.MAX_VALUE;
                        for(int x = 0; x < wss.size(); x++){
                            if(wss.get(x) != 0){
                                if(wss.get(x) < leastAmountOfFrames){
                                    leastAmountOfFrames = wss.get(x);
                                    processWithTheLeastAmountOfFrames = x;
                                }
                            }
                        }
                        processHalted[processWithTheLeastAmountOfFrames] = true;
                        numberOfHaltedProcesses++;
                        d = 0;
                        wss.clear();
                        for(int x = 0; x < getProcessesTable().length; x++){
                            if(!processHalted[x]){
                                wss.add(Math.max(1, calculateUniquePagesGeneratedByProcessInTimeWindow(x)));
                                d += Math.max(1, calculateUniquePagesGeneratedByProcessInTimeWindow(x));
                            } else{
                                wss.add(0);
                            }

                        }
                    }
                }
                for(int x = 0; x < getNumberOfProcesses(); x++){
                    numberOfFramesPerProcess[x] = wss.get(x);
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
                        if(countFreeFrames() < startingFramesPerProcess[x]){
                            numberOfFramesPerProcess[x] = countFreeFrames();
                        } else{
                            numberOfFramesPerProcess[x] = startingFramesPerProcess[x];
                        }
                        break;
                    }
                }
            }


            time++;
            if(time >= timeWindow){
                timeWindowReached = true;
            }

            if(counter == c){
                counter = 0;
            } else {
                counter++;
            }



        }




    }

    private int calculateUniquePagesGeneratedByProcessInTimeWindow(int process) {

        List<Integer> uniquePages = new ArrayList<>();

        for(int a = 0 ; a < numberOfPagesForEachProcessInTimeWindow[process].size(); a++){
            if(!uniquePages.contains(numberOfPagesForEachProcessInTimeWindow[process].get(a)))
                uniquePages.add(numberOfPagesForEachProcessInTimeWindow[process].get(a).getPageNumber());
        }

        return uniquePages.size();
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
            framesPerProcess[x] = (uniquePagesTable[x].size()* getNumberOfFrames()) / totalNumberOfPages ;
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
        System.out.println("Zone Model: ");
        printStats();
        System.out.println("Halted processes: " + numberOfHaltedProcesses);
    }
}
