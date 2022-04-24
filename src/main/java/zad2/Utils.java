package zad2;

import java.util.ArrayList;

public class Utils {

    public void printStats(ArrayList<Request> done){
        double averageWaitingTime = 0;
        for (Request p :done){
            averageWaitingTime += p.getWaitingTime();
        }
        int longestWaitingTime = 0;
        for (Request p : done){
            if(p.getWaitingTime()>longestWaitingTime){
                longestWaitingTime=p.getWaitingTime();
            }
        }
        System.out.println("Longest waiting time: " + longestWaitingTime);
        averageWaitingTime= averageWaitingTime/done.size();
        System.out.println("Average waiting time: " + averageWaitingTime);
        double averageCompletionTime = 0;
        for (Request p :done){
            averageCompletionTime += p.getCompletionTime()-p.getArrivalTime();
        }
        averageCompletionTime= averageCompletionTime/done.size();
        System.out.println("Average completion time (from arrival to fully completed): " + averageCompletionTime);
    }
    public void printWholeQueue(ArrayList<Request> done){
        for (int x =0; x<done.size(); x++) {
            System.out.println(done.get(x));
        }
    }

}
