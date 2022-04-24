package zad2.Algorithms;

import zad1.Process;
import zad2.Request;

import java.util.ArrayList;

public class FCFS implements AlgorithmInterface{

    @Override
    public ArrayList<Request> simulate(ArrayList<Request> list) {

        ArrayList<Request> queue = new ArrayList<>();
        ArrayList<Request> done = new ArrayList<>();
        int currentPosition = 1;
        int time=0;
        int addedTime;

        //dopóki done będzie wielkości list to pętla działa
        while(!(done.size() == list.size())){


            if(!queue.isEmpty()){
                //bierzemy pierwszy proces i go kończymy
                queue.get(0).setServed(true);
                addedTime = Math.abs(queue.get(0).getAddress()-currentPosition);
                currentPosition = queue.get(0).getAddress();
                queue.get(0).setCompletionTime(time+addedTime);
                queue.get(0).setWaitingTime(queue.get(0).getWaitingTime()+addedTime);
                //dodajemy do done i usuwamy z queue
                done.add(queue.get(0));
                queue.remove(0);

            }else{
                addedTime = 1;
            }

            //dodawanie nowych elementów do listy queue
            for(int x = 0;x< list.size();x++){
                if(list.get(x).getArrivalTime()<=time+addedTime && list.get(x).getArrivalTime()>time){
                    queue.add(list.get(x));
                }
            }
            //aktualizacja czasu oczekiwania
            for(int x =0; x<queue.size();x++){
                queue.get(x).setWaitingTime( queue.get(x).getWaitingTime()+addedTime);
            }

            time+=addedTime;
        }
        return done;
    }


}
