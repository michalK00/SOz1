package zad2.Algorithms.RealTime;

import zad2.Request;

import java.util.ArrayList;
import java.util.Comparator;

public class EDFandSCAN implements RealTimeAlgorithmInterface{


    ArrayList<Request> normalDone = new ArrayList<>();
    ArrayList<Request> realTimeDone = new ArrayList<>();
    int currentPosition = 1;
    int time=0;
    int addedTime=0;
    boolean goingRight = true;
    int totalNumberOfHeadPasses = 0;


    @Override
    public ArrayList<Request> simulate(ArrayList<Request> normalRequestList, ArrayList<Request> realTimeRequestList) {

        ArrayList<Request> normalQueue = new ArrayList<>();
        ArrayList<Request> realTimeQueue = new ArrayList<>();
        //dopóki done będzie wielkości list to pętla działa
        while(!(normalDone.size() == normalRequestList.size()) || !(realTimeDone.size() == realTimeRequestList.size())) {


            if (!normalQueue.isEmpty() && realTimeQueue.isEmpty()) {
                standardSCAN(normalQueue);
            } else if (!realTimeQueue.isEmpty()){
                edf(realTimeQueue);
            } else
                addedTime = 1;




            //dodawanie nowych elementów do listy normalQueue
            for (int x = 0; x < normalRequestList.size(); x++) {
                if (normalRequestList.get(x).getArrivalTime() <= time + addedTime && normalRequestList.get(x).getArrivalTime() > time) {
                    normalQueue.add(normalRequestList.get(x));
                }
            }
            //dodawanie nowych elementów do listy ueue
            for (int x = 0; x < realTimeRequestList.size(); x++) {
                if (realTimeRequestList.get(x).getArrivalTime() <= time + addedTime && realTimeRequestList.get(x).getArrivalTime() > time) {
                    realTimeQueue.add(realTimeRequestList.get(x));
                }
            }
            //aktualizacja czasu oczekiwania normalQueue
            for (int x = 0; x < normalQueue.size(); x++) {
                normalQueue.get(x).setWaitingTime(normalQueue.get(x).getWaitingTime() + addedTime);
            }
            //aktualizacja czasu oczekiwania realTimeQueue
            //aktualizacja deadline'ów realTimeQueue
            for (int x = 0; x < realTimeQueue.size(); x++) {
                realTimeQueue.get(x).setWaitingTime(realTimeQueue.get(x).getWaitingTime() + addedTime);
                realTimeQueue.get(x).setDeadline(realTimeQueue.get(x).getDeadline() - addedTime);
                if(realTimeQueue.get(x).getDeadline()<=0){
                    realTimeQueue.get(x).setFailed(true);
                    realTimeDone.add(realTimeQueue.get(x));
                    realTimeQueue.remove(x);
                }
            }

            int finalCurrentPosition = currentPosition;
            normalQueue.sort(new Comparator<Request>() {

                @Override
                public int compare(Request o1, Request o2) {
                    int left = Math.abs(o1.getAddress()-finalCurrentPosition);
                    int right = Math.abs(o2.getAddress()-finalCurrentPosition);
                    if(left>right){
                        return 1;
                    } else  if(left<right){
                        return -1;
                    }else
                        return 0;
                }
            });
            realTimeQueue.sort(new Comparator<Request>() {

                @Override
                public int compare(Request o1, Request o2) {
                    int left = Math.abs(o1.getDeadline());
                    int right = Math.abs(o2.getDeadline());
                    if(left>right){
                        return 1;
                    } else  if(left<right){
                        return -1;
                    }else
                        return 0;
                }
            });
            time += addedTime;
        }
        normalDone.addAll(realTimeDone);
        return normalDone;
    }

    private void edf(ArrayList<Request> realTimeQueue) {

        if(realTimeQueue.get(0).getDeadline() >= Math.abs(realTimeQueue.get(0).getAddress()-currentPosition)){
            //bierzemy pierwszy proces i go kończymy
            realTimeQueue.get(0).setServed(true);
            addedTime = Math.abs(realTimeQueue.get(0).getAddress() - currentPosition);
            currentPosition = realTimeQueue.get(0).getAddress();
            realTimeQueue.get(0).setCompletionTime(time + addedTime);
            realTimeQueue.get(0).setWaitingTime(realTimeQueue.get(0).getWaitingTime() + addedTime);
            //dodajemy do done i usuwamy z queue
            realTimeDone.add(realTimeQueue.get(0));
            realTimeQueue.remove(0);
        } else{
            addedTime = realTimeQueue.get(0).getDeadline();
            if(currentPosition < realTimeQueue.get(0).getAddress())
                currentPosition +=addedTime;
            else if(currentPosition > realTimeQueue.get(0).getAddress()){
                currentPosition-=addedTime;
            }

            realTimeQueue.get(0).setWaitingTime(realTimeQueue.get(0).getWaitingTime() + addedTime);
            realTimeQueue.get(0).setFailed(true);
            //dodajemy do done i usuwamy z queue
            realTimeDone.add(realTimeQueue.get(0));
            realTimeQueue.remove(0);
        }
        totalNumberOfHeadPasses += addedTime;

    }

    public void standardSCAN(ArrayList<Request> normalQueue){
        int temp = -1;
        for(int x = 0; x< normalQueue.size(); x++){
            if(goingRight){
                if(normalQueue.get(x).getAddress()>=currentPosition){
                    temp = x;
                    break;
                }

            } else if(!goingRight){
                if(normalQueue.get(x).getAddress()<=currentPosition){
                    temp = x;
                    break;
                }
            }
        }
        if(temp==-1){
            if(goingRight){
                goingRight = false;
            } else
                goingRight = true;

            addedTime = 0;
        } else {
            //bierzemy pierwszy proces i go kończymy
            normalQueue.get(temp).setServed(true);
            addedTime = Math.abs(normalQueue.get(temp).getAddress()-currentPosition);
            currentPosition = normalQueue.get(temp).getAddress();
            normalQueue.get(temp).setCompletionTime(time+addedTime);
            normalQueue.get(temp).setWaitingTime(normalQueue.get(temp).getWaitingTime()+addedTime);
            //dodajemy do done i usuwamy z queue
            normalDone.add(normalQueue.get(temp));
            normalQueue.remove(temp);
            totalNumberOfHeadPasses += addedTime;
        }

    }

    @Override
    public int getTotalNumberOfHeadPasses() {
        return totalNumberOfHeadPasses;
    }
}
