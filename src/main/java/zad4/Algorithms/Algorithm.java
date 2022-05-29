package zad4.Algorithms;


import zad4.Frame;

import java.util.ArrayDeque;
import java.util.List;

public abstract class Algorithm{

    private Frame[] framesTab;
    private int numberOfPageErrors;
    private ArrayDeque<Integer>[] processesTable;
    private ArrayDeque<Integer> generatedSequenceOfProcesses;

    public Algorithm(int numberOfFrames, ArrayDeque<Integer>[] processesTable, ArrayDeque<Integer> generatedSequenceOfProcesses) {

        framesTab = new Frame[numberOfFrames];
        for(int x = 0; x<numberOfFrames; x++){
            framesTab[x] = new Frame();
        }
        numberOfPageErrors = 0;
        this.processesTable = processesTable;
        this.generatedSequenceOfProcesses = generatedSequenceOfProcesses;

    }

    public int getNumberOfFrames(){
        return framesTab.length;
    }
    public int getNumberOfProcesses(){
        return processesTable.length;
    }

    public Frame[] getFramesTab() {
        return framesTab;
    }

    protected void setFramesTab(Frame[] framesTab) {
        this.framesTab = framesTab;
    }


    public int getNumberOfPageErrors() {
        return numberOfPageErrors;
    }

    protected void setNumberOfPageErrors(int numberOfPageErrors) {
        this.numberOfPageErrors = numberOfPageErrors;
    }

    public ArrayDeque<Integer>[] getProcessesTable() {
        return processesTable;
    }

    public void setProcessesTable(ArrayDeque<Integer>[] processesTable) {
        this.processesTable = processesTable;
    }

    public ArrayDeque<Integer> getGeneratedSequenceOfProcesses() {
        return generatedSequenceOfProcesses;
    }

    public void setGeneratedSequenceOfProcesses(ArrayDeque<Integer> generatedSequenceOfProcesses) {
        this.generatedSequenceOfProcesses = generatedSequenceOfProcesses;
    }

    protected void incrementNumberOfPageErrors() {
        setNumberOfPageErrors(getNumberOfPageErrors()+1);
    }

    protected boolean appealIsInMemory(int appeal) {
        for(Frame f : getFramesTab()){
            if(f.getCurrentAppeal() == appeal)
                return true;
        }
        return false;
    }

    protected boolean atLeastOnePageIsEmpty(){
        for(int x = 0; x < framesTab.length; x++){
            if(framesTab[x].getCurrentAppeal() == -1){
                return true;
            }
        }
        return false;
    }

    protected int getHowManyFramesDoesAProcessHave(int processNumber){
        int numberOfFrames = 0;
        for(Frame f : getFramesTab()){
            if(f.getCurrentProcess() == processNumber){
                numberOfFrames++;
            }
        }
        return numberOfFrames;
    }

    protected int getFrameIndexWithPageThatHasTheLongestTimeInMemory(int currentProcess){
        int longestTimeInMemory = 0;
        int frameIndex = -1;
        for (int x = 0; x < getFramesTab().length; x++) {
            if(getFramesTab()[x].getCurrentProcess() == currentProcess && getFramesTab()[x].getCurrentAppeal() != -1 && getFramesTab()[x].getCounter() > longestTimeInMemory){
                longestTimeInMemory = getFramesTab()[x].getCounter();
                frameIndex = x;
            }
        }
        return frameIndex;
    }
}
