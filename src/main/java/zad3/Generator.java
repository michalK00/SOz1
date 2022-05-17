package zad3;

import java.util.Random;

public class Generator {

    Random random = new Random();

    public int[] generateSequenceOfAppeals(int n, int maxIndex, int numberOfFrames, int minLengthForSingleSequence,int maxLengthForSingleSequence){

        int[] tab = new int[n];
        int generatedValues = 0;

        while(generatedValues<=n){

            int index = random.nextInt(maxIndex+1)-1;
            int lowEnd = Math.max(index - maxIndex/numberOfFrames, 0);
            int highEnd = Math.min(index + maxIndex/numberOfFrames, maxIndex);

            int howManyValues = random.nextInt(maxLengthForSingleSequence+minLengthForSingleSequence)-minLengthForSingleSequence;

            for(int x = 0; x < howManyValues; x++){
                if(generatedValues >= n){
                    return tab;
                }
                int generatedValue = random.nextInt(highEnd - lowEnd) + lowEnd;
                tab[generatedValues] = generatedValue;
                generatedValues++;
            }



        }

        return tab;
    }

}
