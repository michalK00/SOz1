package zad2;

import zad2.Algorithms.*;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        Disc disc = new Disc(200, 50, 20, 2,2);
        disc.printWholeQueue();
        AlgorithmInterface fcfs = new FCFS();
        AlgorithmInterface sstf = new SSTF();
        AlgorithmInterface scan = new SCAN();
        AlgorithmInterface cscan = new CSCAN();
        Utils utils = new Utils();


        ArrayList<Request> done = fcfs.simulate(disc.getQueue());
        System.out.println("FCFS:");
        utils.printStats(done);
        utils.printWholeQueue(done);

        disc.resetList();
        done = sstf.simulate(disc.getQueue());
        System.out.println("SSTF:");
        utils.printStats(done);
        utils.printWholeQueue(done);

        disc.resetList();
        done = scan.simulate(disc.getQueue());
        System.out.println("SCAN:");
        utils.printStats(done);
        utils.printWholeQueue(done);

        disc.resetList();
        done = cscan.simulate(disc.getQueue());
        System.out.println("CSCAN:");
        utils.printStats(done);
        utils.printWholeQueue(done);

    }
}
