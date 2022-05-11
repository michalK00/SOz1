package zad2;

import zad2.Algorithms.*;
import zad2.Algorithms.RealTime.EDFandSCAN;
import zad2.Algorithms.RealTime.FDSCANandSCAN;
import zad2.Algorithms.RealTime.RealTimeAlgorithmInterface;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {

        int discSize;
        int maxArrivalTime;
        int numberOfRequests;
        int alpha;
        int beta;
        int maxDeadlineTime;

        //fd SCAN fails less
//        discSize = 1000;
//        maxArrivalTime = 2000;
//        numberOfRequests = 1000;
//        alpha = 2;
//        beta = 2;
//        maxDeadlineTime = 1000;

        //sstf becomes fcfs
//        discSize = 100;
//        maxArrivalTime = 20000;
//        numberOfRequests = 100;
//        alpha = 2;
//        beta = 2;
//        maxDeadlineTime = 1000;

        //all real time requests failed
//        discSize = 1000;
//        maxArrivalTime = 200;
//        numberOfRequests = 1000;
//        alpha = 2;
//        beta = 2;
//        maxDeadlineTime = 50;

        //all real time requests succeeded
        discSize = 500;
        maxArrivalTime = 2000;
        numberOfRequests = 400;
        alpha = 2;
        beta = 2;
        maxDeadlineTime = 10000;

        Disc discNormalRequests = new Disc(discSize, maxArrivalTime, numberOfRequests, alpha,beta);
        Disc discRealTimeRequests = new Disc(discSize, maxArrivalTime, numberOfRequests/4, alpha,beta, maxDeadlineTime);
        //discNormalRequests.printWholeQueue();
        //discRealTimeRequests.printWholeQueue();
        AlgorithmInterface fcfs = new FCFS();
        AlgorithmInterface sstf = new SSTF();
        AlgorithmInterface scan = new SCAN();
        AlgorithmInterface cscan = new CSCAN();


        RealTimeAlgorithmInterface edfandscan = new EDFandSCAN();
        RealTimeAlgorithmInterface fdscanfandscan = new FDSCANandSCAN();
        Utils utils = new Utils();


        ArrayList<Request> done = fcfs.simulate(discNormalRequests.getQueue());
        System.out.println("-----------FCFS:-----------");
        utils.printStats(done, fcfs);
        System.out.println();
        //utils.printWholeQueue(done);

        discNormalRequests.resetList();
        done = sstf.simulate(discNormalRequests.getQueue());
        System.out.println("-----------SSTF:-----------");
        utils.printStats(done, sstf);
        System.out.println();
        //utils.printWholeQueue(done);

        discNormalRequests.resetList();
        done = scan.simulate(discNormalRequests.getQueue());
        System.out.println("-----------SCAN:-----------");
        utils.printStats(done, scan);
        System.out.println();
        //utils.printWholeQueue(done);

        discNormalRequests.resetList();
        done = cscan.simulate(discNormalRequests.getQueue());
        System.out.println("-----------CSCAN:-----------");
        utils.printStats(done, cscan);
        System.out.println();
        //utils.printWholeQueue(done);

        discNormalRequests.resetList();
        done = edfandscan.simulate(discNormalRequests.getQueue(), discRealTimeRequests.getQueue());
        System.out.println("-----------EDF and SCAN:-----------");
        utils.printRealTimeStats(done, edfandscan);
        System.out.println();
        //utils.printWholeQueue(done);

        discNormalRequests.resetList();
        discRealTimeRequests.resetList();
        done = fdscanfandscan.simulate(discNormalRequests.getQueue(), discRealTimeRequests.getQueue());
        System.out.println("-----------FD-SCAN and SCAN:-----------");
        utils.printRealTimeStats(done, fdscanfandscan);
        System.out.println();
        //utils.printWholeQueue(done);

    }
}
