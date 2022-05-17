package zad3.Algorithms;

import zad3.Frame;

public class LRU extends Algorithm{

    public LRU(int numberOfFrames, int[] sequenceOfAppeals) {

        super(numberOfFrames, sequenceOfAppeals);

    }

    @Override
    public void simulate() {

        int time = 0;

        while(time < getSequenceOfAppeals().length){

            int nextAppeal = getSequenceOfAppeals()[time];
            if(atLeastOnePageIsEmpty() && !appealIsInMemory(nextAppeal)){
                for (Frame f: getFramesTab()) {

                    if(f.getCurrentAppeal() == -1){
                        incrementNumberOfPageErrors();
                        f.setCurrentAppeal(nextAppeal);
                        f.setCounter(0);
                        break;
                    }
                }
            } else if(appealIsInMemory(nextAppeal)){

                for (Frame f: getFramesTab()) {

                    if(f.getCurrentAppeal() == nextAppeal){
                        f.setCounter(0);
                        break;
                    }
                }

            } else {

                int longestTimeInMemory = 0;
                int pageIndex = -1;
                for (int x = 0; x < getFramesTab().length; x++) {
                    if(getFramesTab()[x].getCurrentAppeal() != -1 && getFramesTab()[x].getCounter() > longestTimeInMemory){
                        longestTimeInMemory = getFramesTab()[x].getCounter();
                        pageIndex = x;
                    }
                }
                getFramesTab()[pageIndex].setCurrentAppeal(nextAppeal);
                getFramesTab()[pageIndex].setCounter(0);
                incrementNumberOfPageErrors();

            }
            time++;
            for (Frame f: getFramesTab()) {
                if(f.getCurrentAppeal() != -1){
                    f.setCounter(f.getCounter()+1);
                }
            }
        }




    }


}
