package zad4.Algorithms;

import zad4.Frame;

import java.util.ArrayDeque;

public class EqualAllocation extends Algorithm implements AlgorithmInterface {

    public EqualAllocation(int numberOfFrames, ArrayDeque<Integer>[] processesTable, ArrayDeque<Integer> generatedSequenceOfProcesses) {

        super(numberOfFrames, processesTable, generatedSequenceOfProcesses);

    }

    public void simulate() {

        int numberOfFramesPerProcess = getNumberOfFrames()/getNumberOfProcesses();


        while(!getGeneratedSequenceOfProcesses().isEmpty()){

            int nextProcess = getGeneratedSequenceOfProcesses().poll();
            int nextAppeal = getProcessesTable()[nextProcess].poll();

            if(getHowManyFramesDoesAProcessHave(nextProcess) == numberOfFramesPerProcess){

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

            } else if (getHowManyFramesDoesAProcessHave(nextProcess) < numberOfFramesPerProcess){

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




}
