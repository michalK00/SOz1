package zad2;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.random.AbstractRandomGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Disc {
    private int size;
    private int maxArrivalTime;
    private int numberOfRequests;
    private int maxDeadline;
    private ArrayList<Request> queue = new ArrayList<>();


    public Disc(int size, int maxArrivalTime, String typeOfRequestArrangement, int numberOfRequests) {
        this.size = size;
        this.maxArrivalTime = maxArrivalTime;
        this.numberOfRequests = numberOfRequests;
        maxDeadline = -1;
        initialize(typeOfRequestArrangement);
    }
    public Disc(int size, int maxArrivalTime, int numberOfRequests, int alpha, int beta) {
        this.size = size;
        this.maxArrivalTime = maxArrivalTime;
        this.numberOfRequests = numberOfRequests;
        maxDeadline = -1;
        initialize(alpha, beta);
    }
    public Disc(int size, int maxArrivalTime, int numberOfRequests, int alpha, int beta, int maxDeadline) {
        this.size = size;
        this.maxArrivalTime = maxArrivalTime;
        this.numberOfRequests = numberOfRequests;
        this.maxDeadline = maxDeadline;
        initialize(alpha, beta);
    }




    private void initialize(String typeOfRequestArrangement){

        if(typeOfRequestArrangement.equals("clusters"))
            generateClusterTypeQueue();
        else
            generateBetaDistribution(1, 1);
    }
    private void initialize(int alpha, int beta){
        generateBetaDistribution(alpha,beta);
    }



    private void generateClusterTypeQueue(){

    }
    private void generateBetaDistribution(int alpha, int beta){

        Random rand = new Random();

        for (int x = 0; x<numberOfRequests;x++){

            int address = (int) (Math.round(betaDistribution(alpha, beta)*(size-1))+1);
            int arrivalTime = rand.nextInt(maxArrivalTime-1)+1;
            if(maxDeadline != -1){
                int deadline = (int) (Math.round(betaDistribution(alpha, beta)*(maxDeadline-1))+1);
                queue.add(new Request(arrivalTime, address, deadline));
            } else
                queue.add(new Request(arrivalTime, address));
        }
        sortQueue();
    }

    public void sortQueue(){
        queue.sort(new Comparator<Request>() {
            @Override
            public int compare(Request o1, Request o2) {
                if(o1.getArrivalTime() < o2.getArrivalTime()){
                    return -1;
                } else if(o1.getArrivalTime() > o2.getArrivalTime()){
                    return 1;
                } else
                    return 0;
            }
        });
    }

    public void printWholeQueue(){
        for (int x =0; x<queue.size(); x++) {
            System.out.println(queue.get(x));
        }
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Request> getQueue() {
        return queue;
    }



    public void resetList(){
        for (Request r: queue) {
            r.setServed(false);
            r.setWaitingTime(0);
            r.setCompletionTime(0);
            if(r.isRealTimeRequest()){
                r.setDeadline(r.getInitialDeadline());
                r.setFailed(false);
            }
        }
    }
    private double betaDistribution(int alpha, int beta){
        BetaDistribution betaDistribution = new BetaDistribution(new AbstractRandomGenerator() {
            Random r = new Random();
            @Override
            public void setSeed(long l) {
                r.setSeed(l);
            }

            @Override
            public double nextDouble() {
                return r.nextDouble();
            }
        },alpha,beta );
        return betaDistribution.sample();
    }




}
