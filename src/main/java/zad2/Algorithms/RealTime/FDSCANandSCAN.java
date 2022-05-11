package zad2.Algorithms.RealTime;

import zad2.Request;

import java.util.ArrayList;
import java.util.Comparator;

public class FDSCANandSCAN implements RealTimeAlgorithmInterface{

    ArrayList<Request> normalDone = new ArrayList<>();
    ArrayList<Request> realTimeDone = new ArrayList<>();
    int currentPosition = 1;
    int time=0;
    int addedTime=0;
    boolean goingRight = true;
    int totalNumberOfHeadPasses = 0;
    public int numberOfRequestsDoneOnTheWay = 0;


    @Override
    public ArrayList<Request> simulate(ArrayList<Request> normalRequestList, ArrayList<Request> realTimeRequestList) {

        ArrayList<Request> normalQueue = new ArrayList<>();
        ArrayList<Request> realTimeQueue = new ArrayList<>();
        //dopóki done będzie wielkości list to pętla działa
        while(!(normalDone.size() == normalRequestList.size()) || !(realTimeDone.size() == realTimeRequestList.size())) {


            if (!normalQueue.isEmpty() && realTimeQueue.isEmpty()) {
                standardSCAN(normalQueue);
            } else if (!realTimeQueue.isEmpty()){
                fdscan(realTimeQueue, normalQueue);
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
            //sort by closest to currentPos
            realTimeQueue.sort(new Comparator<Request>() {

                @Override
                public int compare(Request o1, Request o2) {
                    int left = Math.abs(o1.getAddress()-finalCurrentPosition);
                    int right = Math.abs(o2.getAddress()-finalCurrentPosition);
                    if(left>right){
                        return -1;
                    } else  if(left<right){
                        return 1;
                    }else
                        return 0;
                }
            });
            time += addedTime;
        }
        normalDone.addAll(realTimeDone);
        return normalDone;
    }

    private void fdscan(ArrayList<Request> realTimeQueue, ArrayList<Request> normalQueue) {

        int najdalszyMozliwy = -1;
        for (int x = 0; x<realTimeQueue.size(); x++) {
            if(realTimeQueue.get(x).getDeadline()-Math.abs(realTimeQueue.get(x).getAddress()-currentPosition) >= 0){
                //wykonuj
                najdalszyMozliwy=x;
                break;
            }
        }
        if(najdalszyMozliwy != -1){
            ArrayList<Integer> indexList = new ArrayList();
            for (int x = 0; x<najdalszyMozliwy; x++) {
                realTimeQueue.get(x).setFailed(true);
                //dodajemy do done i usuwamy z queue
                realTimeDone.add(realTimeQueue.get(x));
                indexList.add(x);
            }
            int k = 0;
            for(int x = 0; x < indexList.size(); x++){
                realTimeQueue.remove(indexList.get(x)-k);
                k++;
            }
            int prevPosition = currentPosition;
            realTimeQueue.get(0).setServed(true);
            addedTime = Math.abs(realTimeQueue.get(0).getAddress() - currentPosition);
            currentPosition = realTimeQueue.get(0).getAddress();
            realTimeQueue.get(0).setCompletionTime(time + addedTime);
            realTimeQueue.get(0).setWaitingTime(realTimeQueue.get(0).getWaitingTime() + addedTime);
            //dodajemy do done i usuwamy z queue
            realTimeDone.add(realTimeQueue.get(0));
            realTimeQueue.remove(0);

            indexList.clear();
            //wykonywanie wszysktich requestów po drodze:
            for(int x = 0; x < realTimeQueue.size(); x++){
                if(currentPosition>prevPosition && (realTimeQueue.get(x).getAddress() < currentPosition && realTimeQueue.get(x).getAddress() > prevPosition)){
                    realTimeQueue.get(x).setCompletionTime(time + Math.abs(realTimeQueue.get(x).getAddress()-prevPosition));
                    realTimeQueue.get(x).setWaitingTime(realTimeQueue.get(x).getWaitingTime() + Math.abs(realTimeQueue.get(x).getAddress()-prevPosition));
                    //dodajemy do done i usuwamy z queue
                    realTimeDone.add(realTimeQueue.get(x));
                    indexList.add(x);
                    numberOfRequestsDoneOnTheWay++;

                } else if(currentPosition<prevPosition && (realTimeQueue.get(x).getAddress() > currentPosition && realTimeQueue.get(x).getAddress() < prevPosition)){
                    realTimeQueue.get(x).setCompletionTime(time + Math.abs(realTimeQueue.get(x).getAddress()-prevPosition));
                    realTimeQueue.get(x).setWaitingTime(realTimeQueue.get(x).getWaitingTime() + Math.abs(realTimeQueue.get(x).getAddress()-prevPosition));
                    //dodajemy do done i usuwamy z queue
                    realTimeDone.add(realTimeQueue.get(x));
                    indexList.add(x);
                    numberOfRequestsDoneOnTheWay++;
                }
            }
            k = 0;
            for(int x = 0; x < indexList.size(); x++){
                realTimeQueue.remove(indexList.get(x)-k);
                k++;
            }
            indexList.clear();
            for(int x = 0; x < normalQueue.size(); x++){
                if(currentPosition>prevPosition && (normalQueue.get(x).getAddress() < currentPosition && normalQueue.get(x).getAddress() > prevPosition)){
                    normalQueue.get(x).setCompletionTime(time + Math.abs(normalQueue.get(x).getAddress()-prevPosition));
                    normalQueue.get(x).setWaitingTime(normalQueue.get(x).getWaitingTime() + Math.abs(normalQueue.get(x).getAddress()-prevPosition));
                    //dodajemy do done i usuwamy z queue
                    normalDone.add(normalQueue.get(x));
                    indexList.add(x);
                    numberOfRequestsDoneOnTheWay++;

                } else if(currentPosition<prevPosition && (normalQueue.get(x).getAddress() > currentPosition && normalQueue.get(x).getAddress() < prevPosition)){
                    normalQueue.get(x).setCompletionTime(time + Math.abs(normalQueue.get(x).getAddress()-prevPosition));
                    normalQueue.get(x).setWaitingTime(normalQueue.get(x).getWaitingTime() + Math.abs(normalQueue.get(x).getAddress()-prevPosition));
                    //dodajemy do done i usuwamy z queue
                    normalDone.add(normalQueue.get(x));
                    indexList.add(x);
                    numberOfRequestsDoneOnTheWay++;
                }
            }
            k = 0;
            for(int x = 0; x < indexList.size(); x++){
                normalQueue.remove(indexList.get(x)-k);
                k++;
            }
            totalNumberOfHeadPasses += addedTime;
        } else
            addedTime = 1;




    }

    public void standardSCAN(ArrayList<Request> normalQueue){
        int temp = -1;
        for(int x = 0; x< normalQueue.size(); x++){
            if(goingRight){
                if(normalQueue.get(x).getAddress()>=currentPosition){
                    temp = x;
                    break;
                }

            } else{
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
