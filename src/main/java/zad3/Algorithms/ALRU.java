package zad3.Algorithms;

import zad3.DataStructures.AppealQueue;
import zad3.Frame;

public class ALRU extends Algorithm{

    public ALRU(int numberOfFrames, int[] sequenceOfAppeals) {
        super(numberOfFrames, sequenceOfAppeals);
    }

    @Override
    public void simulate() {
        AppealQueue appealQueue = new AppealQueue(getFramesTab().length);
        int time = 0;

        while(time < getSequenceOfAppeals().length){

            int nextAppeal = getSequenceOfAppeals()[time];
            if(atLeastOnePageIsEmpty() && !appealIsInMemory(nextAppeal)){
                for (Frame f: getFramesTab()) {

                    if(f.getCurrentAppeal() == -1){
                        incrementNumberOfPageErrors();
                        f.setCurrentAppeal(nextAppeal);
                        appealQueue.add(nextAppeal);
                        break;
                    }
                }
            } else if(!appealIsInMemory(nextAppeal)){
                boolean addedNewApealToQueue = false;
                int prevAppealValue = -1;

                while(!addedNewApealToQueue){
                    if(appealQueue.getBit(0)){
                        AppealQueue.Appeal a = appealQueue.getAppeal(0);
                        appealQueue.dequeue();
                        appealQueue.add(a);
                        appealQueue.setBit(appealQueue.size()-1, false);
                    } else{
                        prevAppealValue = appealQueue.getValue(0);
                        appealQueue.dequeue();
                        appealQueue.add(nextAppeal);
                        addedNewApealToQueue = true;
                    }
                }

                for (Frame f: getFramesTab()) {
                    if(f.getCurrentAppeal() == prevAppealValue){
                        f.setCurrentAppeal(nextAppeal);
                        incrementNumberOfPageErrors();
                    }

                }

            } else if(appealIsInMemory(nextAppeal)) {
                int index = appealQueue.indexOf(nextAppeal);
                appealQueue.setBit(index, true);
            }

            time++;
        }
    }

}
