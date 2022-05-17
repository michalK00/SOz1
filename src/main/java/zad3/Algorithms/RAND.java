package zad3.Algorithms;

import zad3.Frame;

import java.util.Random;

public class RAND extends Algorithm {

    public RAND(int numberOfFrames, int[] sequenceOfAppeals) {

        super(numberOfFrames, sequenceOfAppeals);

    }

    @Override
    public void simulate() {
        int time = 0;
        Random random = new Random();
        while(time < getSequenceOfAppeals().length){

            int nextAppeal = getSequenceOfAppeals()[time];
            if(atLeastOnePageIsEmpty() && !appealIsInMemory(nextAppeal)){
                for (Frame f: getFramesTab()) {
                    if(f.getCurrentAppeal() == -1){
                        incrementNumberOfPageErrors();
                        f.setCurrentAppeal(nextAppeal);
                        break;
                    }
                }
            } else {
                int index = random.nextInt(getFramesTab().length);
                if(getFramesTab()[index].getCurrentAppeal() != nextAppeal){
                    getFramesTab()[index].setCurrentAppeal(nextAppeal);
                    incrementNumberOfPageErrors();
                }
            }
            time++;
        }
    }
}
