package zad3;

import zad3.Algorithms.*;

public class main {

    public static void main(String[] args) {

        Utils utils = new Utils();
        Generator generator = new Generator();
        int numberOfFrames = 4;
        int[] tab = generator.generateSequenceOfAppeals(100, 100, 10);
        //int[] tab = {1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
        //int[] tab = {1, 2, 3, 4, 1, 2, 5, 3, 2, 1, 4, 5};

        for(int x = 0; x<tab.length; x++){
            System.out.print(tab[x] + " ");
        }
        System.out.println();

        Algorithm fifo = new FIFO(numberOfFrames, tab);
        fifo.simulate();
        utils.printStats(fifo);

        Algorithm opt = new OPT(numberOfFrames, tab);
        opt.simulate();
        utils.printStats(opt);

        Algorithm lru = new LRU(numberOfFrames, tab);
        lru.simulate();
        utils.printStats(lru);

        Algorithm alru = new ALRU(numberOfFrames, tab);
        alru.simulate();
        utils.printStats(alru);

        Algorithm rand = new RAND(numberOfFrames, tab);
        rand.simulate();
        utils.printStats(rand);


    }

}
