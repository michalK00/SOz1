package zad2;

import zad2.Algorithms.AlgorithmInterface;
import zad2.Algorithms.RealTime.FDSCANandSCAN;
import zad2.Algorithms.RealTime.RealTimeAlgorithmInterface;

import java.util.ArrayList;

public class Utils {

    public void printStats(ArrayList<Request> done, AlgorithmInterface algorithm){
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
        System.out.println("Average waiting time: " + Math.round(averageWaitingTime));
        System.out.println("Complete displacement of the head: " +  algorithm.getTotalNumberOfHeadPasses());
    }
    public void printRealTimeStats(ArrayList<Request> done, RealTimeAlgorithmInterface algorithm){
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
        int failedRequests = 0;
        for (Request p : done){
            if(p.isFailed()){
                failedRequests++;
            }
        }
        System.out.println("Longest waiting time: " + longestWaitingTime);
        averageWaitingTime= averageWaitingTime/done.size();
        System.out.println("Average waiting time: " + Math.round(averageWaitingTime));
        System.out.println("Complete displacement of the head: " +  algorithm.getTotalNumberOfHeadPasses());
        System.out.println("Total number of failed requests: " +  failedRequests);
        if(algorithm instanceof FDSCANandSCAN){
            System.out.println("Total number of requests done on the way: " +  ((FDSCANandSCAN) algorithm).numberOfRequestsDoneOnTheWay);
        }
    }
    public void printWholeQueue(ArrayList<Request> done){
        for (int x =0; x<done.size(); x++) {
            System.out.println(done.get(x));
        }
    }

}
