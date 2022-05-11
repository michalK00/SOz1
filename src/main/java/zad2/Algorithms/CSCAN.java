package zad2.Algorithms;

import zad1.Process;
import zad2.Request;

import java.util.ArrayList;
import java.util.Comparator;

public class CSCAN implements AlgorithmInterface{

    int totalNumberOfHeadPasses = 0;

    @Override
    public ArrayList<Request> simulate(ArrayList<Request> list) {
        ArrayList<Request> queue = new ArrayList<>();
        ArrayList<Request> done = new ArrayList<>();
        int currentPosition = 1;
        int time=0;
        int addedTime;
        int numberOfPositionResets = 0;

        //dopóki done będzie wielkości list to pętla działa
        while(!(done.size() == list.size())) {


            if (!queue.isEmpty()) {
                int temp = -1;
                for(int x = 0; x< queue.size(); x++){

                    if(queue.get(x).getAddress()>=currentPosition){
                        temp = x;
                        break;
                    }

                }
                if(temp==-1){
                    addedTime = 0;
                    currentPosition = 1;
                    numberOfPositionResets++;
                } else {
                    //bierzemy pierwszy proces i go kończymy
                    queue.get(temp).setServed(true);
                    addedTime = Math.abs(queue.get(temp).getAddress()-currentPosition);
                    currentPosition = queue.get(temp).getAddress();
                    queue.get(temp).setCompletionTime(time+addedTime);
                    queue.get(temp).setWaitingTime(queue.get(temp).getWaitingTime()+addedTime);
                    //dodajemy do done i usuwamy z queue
                    done.add(queue.get(temp));
                    queue.remove(temp);
                    totalNumberOfHeadPasses += addedTime;
                }



            } else {
                addedTime = 1;
            }

            //dodawanie nowych elementów do listy queue
            for (int x = 0; x < list.size(); x++) {
                if (list.get(x).getArrivalTime() <= time + addedTime && list.get(x).getArrivalTime() > time) {
                    queue.add(list.get(x));
                }
            }
            //aktualizacja czasu oczekiwania
            for (int x = 0; x < queue.size(); x++) {
                queue.get(x).setWaitingTime(queue.get(x).getWaitingTime() + addedTime);
            }

            int finalCurrentPosition = currentPosition;
            queue.sort(new Comparator<Request>() {

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



            time += addedTime;
        }

        return done;
    }

    @Override
    public int getTotalNumberOfHeadPasses() {
        return totalNumberOfHeadPasses;
    }
}
