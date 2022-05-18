package zad3;

import java.util.Random;

public class Generator {

    Random random = new Random();

    public int[] generateSequenceOfAppeals(int n, int maxIndex, int numberOfIntervals, double parameterForMaximalLocalSequenceLengthComparedToFullLength){

        int[] tab = new int[n];
        int generatedValues = 0;
        boolean generatingLocalAppeals = false;

        while(generatedValues<=n){
            if(generatingLocalAppeals){
                int index = random.nextInt(maxIndex-1)+1;
                int lowEnd = Math.max(index - maxIndex/numberOfIntervals, 0);
                int highEnd = Math.min(index + maxIndex/numberOfIntervals, maxIndex);

                int sequenceLength = random.nextInt((int) (parameterForMaximalLocalSequenceLengthComparedToFullLength*n));
                for(int x = 0; x<sequenceLength; x++){
                    if(generatedValues >= n){
                        return tab;
                    }
                    int generatedValue = random.nextInt(highEnd - lowEnd) + lowEnd;
                    tab[generatedValues] = generatedValue;
                    generatedValues++;
                }

                generatingLocalAppeals = false;

            } else {
                if(generatedValues >= n){
                    return tab;
                }
                int generatedValue = random.nextInt(maxIndex-1)+1;
                tab[generatedValues] = generatedValue;
                generatedValues++;
                if(random.nextInt(100)<5){
                    generatingLocalAppeals = true;
                }
            }





        }

        return tab;
    }

}
