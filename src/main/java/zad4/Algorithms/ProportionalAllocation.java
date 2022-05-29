package zad4.Algorithms;

import zad4.Frame;

import java.util.ArrayDeque;
import java.util.Iterator;

public class ProportionalAllocation extends Algorithm implements AlgorithmInterface{

    public ProportionalAllocation(int numberOfFrames, ArrayDeque<Integer>[] processesTable, ArrayDeque<Integer> generatedSequenceOfProcesses) {
        super(numberOfFrames, processesTable, generatedSequenceOfProcesses);
    }

    @Override
    public void simulate() {

        int[] numberOfFramesPerProcess = calculateNumberOfFramesPerProcess();

        while(!getGeneratedSequenceOfProcesses().isEmpty()){

            int nextProcess = getGeneratedSequenceOfProcesses().poll();
            int nextAppeal = getProcessesTable()[nextProcess].poll();

            if(getHowManyFramesDoesAProcessHave(nextProcess) == numberOfFramesPerProcess[nextProcess]){

                if(!appealIsInMemory(nextAppeal)){

                    int frameIndex = getFrameIndexWithPageThatHasTheLongestTimeInMemory(nextProcess);
                    getFramesTab()[frameIndex].setCurrentAppeal(nextAppeal);
                    getFramesTab()[frameIndex].setCurrentProcess(nextProcess);
                    getFramesTab()[frameIndex].setCounter(0);
                    incrementNumberOfPageErrors();

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
            framesPerProcess[x] = (uniquePagesTable[x].size()* getNumberOfFrames()) / totalNumberOfPages ;
        }


        return framesPerProcess;
    }
}
