package zad3.Algorithms;

import zad3.Frame;

public class OPT extends Algorithm{

    public OPT(int numberOfFrames, int[] sequenceOfAppeals) {

        super(numberOfFrames, sequenceOfAppeals);

    }

    @Override
    public void simulate(){
        int time = 0;

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
            } else if(!appealIsInMemory(nextAppeal)){
                int frameNumberWithTheFurthestAwayAppeal = findTheFurthestAway(time);
                getFramesTab()[frameNumberWithTheFurthestAwayAppeal].setCurrentAppeal(nextAppeal);
                incrementNumberOfPageErrors();

            }

            time++;
        }
    }
    private int findTheFurthestAway(int time){
        int maxDistanceForOneAppeal = -1;
        int howFarIsTheSameAppeal;
        int currentAppeal;
        int frameNumber = -1;

        for (int a = 0; a<getFramesTab().length; a++) {
            howFarIsTheSameAppeal = -1;
            currentAppeal = getFramesTab()[a].getCurrentAppeal();
            for(int x = time+1; x < getSequenceOfAppeals().length; x++){
                if(currentAppeal == getSequenceOfAppeals()[x]){
                    howFarIsTheSameAppeal = x - time;
                    break;
                }
            }
            if(howFarIsTheSameAppeal == -1){
                return a;
            } else if(maxDistanceForOneAppeal < howFarIsTheSameAppeal){
                maxDistanceForOneAppeal = howFarIsTheSameAppeal;
                frameNumber = a;
            }
        }
        return frameNumber;
    }

}
