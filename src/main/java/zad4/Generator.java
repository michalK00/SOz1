package zad4;

import java.util.ArrayDeque;
import java.util.Random;

public class Generator {

    Random random = new Random();

    public ArrayDeque<Integer> generateSequenceOfAppeals(int n, int maxIndex, int minIndex, int numberOfIntervals, double parameterForMaximalLocalSequenceLengthComparedToFullLength) throws Exception {


        if(maxIndex<minIndex){
            throw new Exception();
        }
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        int generatedValues = 0;
        boolean generatingLocalAppeals = false;

        while(generatedValues<=n){
            if(generatingLocalAppeals){

                int index = random.nextInt(maxIndex - minIndex - 1) + 1 + minIndex;
                int lowEnd = Math.max(index - maxIndex-minIndex/numberOfIntervals, minIndex);
                int highEnd = Math.min(index + maxIndex-minIndex/numberOfIntervals, maxIndex);

                int sequenceLength = random.nextInt((int) (parameterForMaximalLocalSequenceLengthComparedToFullLength*n));
                for(int x = 0; x<sequenceLength; x++){
                    if(generatedValues >= n){
                        queue.push(-1);
                        return queue;
                    }
                    int generatedValue = random.nextInt(highEnd - lowEnd) + lowEnd;
                    queue.push(generatedValue);
                    generatedValues++;
                }

                generatingLocalAppeals = false;

            } else {
                if(generatedValues >= n){
                    queue.push(-1);
                    return queue;
                }
                int generatedValue = random.nextInt(maxIndex - minIndex - 1) + 1 + minIndex;
                queue.push(generatedValue);
                generatedValues++;
                if(random.nextInt(100)<5){
                    generatingLocalAppeals = true;
                }
            }





        }
        queue.push(-1);
        return queue;
    }

}
