package zad3.Algorithms;


import zad3.Frame;

public abstract class Algorithm{

    private Frame[] framesTab;
    private int numberOfPageErrors;
    private int[] sequenceOfAppeals;

    public Algorithm(int numberOfFrames, int[] sequenceOfAppeals) {

        framesTab = new Frame[numberOfFrames];
        for(int x = 0; x<numberOfFrames; x++){
            framesTab[x] = new Frame();
        }
        numberOfPageErrors = 0;
        this.sequenceOfAppeals = sequenceOfAppeals;

    }

    public void simulate(){};


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

    public int[] getSequenceOfAppeals() {
        return sequenceOfAppeals;
    }

    protected void setSequenceOfAppeals(int[] sequenceOfAppeals) {
        this.sequenceOfAppeals = sequenceOfAppeals;
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
}
