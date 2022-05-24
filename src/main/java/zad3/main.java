package zad3;

import zad3.Algorithms.*;

public class main {

    public static void main(String[] args) {

        Utils utils = new Utils();
        Generator generator = new Generator();
        int numberOfFrames = 15;
        int[] tab = generator.generateSequenceOfAppeals(1000, 100, 10, 0.6);
        //int[] tab = {1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
        //int[] tab = {1, 2, 3, 4, 1, 2, 5, 3, 2, 1, 4, 5};

//        for(int x = 0; x<tab.length; x++){
//            System.out.print(tab[x] + " ");
//        }
//        System.out.println();

        Algorithm fifo = new FIFO(numberOfFrames, tab);
        ((FIFO) fifo).simulate();
        System.out.println("-----FIFO-----");
        utils.printStats(fifo);

        Algorithm opt = new OPT(numberOfFrames, tab);
        ((OPT) opt).simulate();
        System.out.println("-----OPT-----");
        utils.printStats(opt);

        Algorithm lru = new LRU(numberOfFrames, tab);
        ((LRU) lru).simulate();
        System.out.println("-----LRU-----");
        utils.printStats(lru);

        Algorithm alru = new ALRU(numberOfFrames, tab);
        ((ALRU) alru).simulate();
        System.out.println("-----ALRU-----");
        utils.printStats(alru);

        Algorithm rand = new RAND(numberOfFrames, tab);
        ((RAND) rand).simulate();
        System.out.println("-----RAND-----");
        utils.printStats(rand);


    }

}
