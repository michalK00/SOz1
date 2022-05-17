package zad3;

import zad3.Algorithms.*;

import java.util.AbstractList;

public class Utils {


    public void printStats(Algorithm algorithm){
        if(algorithm instanceof FIFO){
            System.out.println("-----FIFO-----");
        } else if(algorithm instanceof OPT){
            System.out.println("-----OPT-----");
        } else if(algorithm instanceof LRU){
            System.out.println("-----LRU-----");
        } else if(algorithm instanceof ALRU){
            System.out.println("-----ALRU-----");
        } else if(algorithm instanceof RAND){
            System.out.println("-----RAND-----");
        }
        System.out.println("Number of page errors:");
        System.out.println(algorithm.getNumberOfPageErrors());
    }
}
