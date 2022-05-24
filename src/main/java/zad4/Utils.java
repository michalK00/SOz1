package zad4;

import java.util.*;

public class Utils {

    public void printArrayDequeue(ArrayDeque<Integer> queue){
        Iterator<Integer> iterator = queue.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
    public ArrayDeque<Integer>[] generateTableOfProcessesWithAppeals(int numberOfProcesses, int numberOfAppeals, int maxValue, int minValue, int offset, int numberOfIntervals, double secretParameter) throws Exception {

        Generator generator = new Generator();
        ArrayDeque<Integer>[] processesTable = new ArrayDeque[numberOfProcesses];
        for(int x = 0; x<processesTable.length; x++){
            ArrayDeque<Integer> sequence = generator.generateSequenceOfAppeals(numberOfAppeals, maxValue + x*offset, minValue + x*offset, numberOfIntervals, secretParameter);
            processesTable[x] = sequence;
        }
        return processesTable;
    }

    public List<Integer> generateSequenceOfProcesses(int numberOfProcesses, int numberOfAppeals){

        Random random = new Random();

        int[] processCounter = new int[numberOfProcesses];
        List<Integer> generatedSequenceOfProcesses = new ArrayList<>();
        List<Integer> possibleProcessList = new ArrayList<>();

        //tables and lists initialization
        for(int x = 0; x<numberOfProcesses; x++){
            possibleProcessList.add(x);
            processCounter[x] = 0;
        }

        while(!possibleProcessList.isEmpty()){
            int generatedProcess = random.nextInt(possibleProcessList.size());

            generatedSequenceOfProcesses.add(possibleProcessList.get(generatedProcess));

            processCounter[possibleProcessList.get(generatedProcess)]++;

            if(processCounter[possibleProcessList.get(generatedProcess)] == numberOfAppeals){
                possibleProcessList.remove(generatedProcess);
            }
        }


        return generatedSequenceOfProcesses;
    }
}
