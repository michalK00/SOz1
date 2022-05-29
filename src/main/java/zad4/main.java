package zad4;

import zad4.Algorithms.AlgorithmInterface;
import zad4.Algorithms.EqualAllocation;
import zad4.Algorithms.ProportionalAllocation;

import java.util.ArrayDeque;


public class main {
    public static void main(String[] args) throws Exception {

        Utils utils = new Utils();

        //#####-All-Processes-Parameters-#####//
        int numberOfProcesses = 2;
        int offset = 11;
        ArrayDeque<Integer>[] processesTable;
        ArrayDeque<Integer> generatedSequenceOfProcesses;

        //#####-Single-Process-AppealList-Parameters-#####//
        int numberOfAppeals = 6;
        int maxValue = 10;
        int minValue = 0;
        int numberOfIntervals = 5;
        double secretParameter = 0.5;

        //#####-Algorithm-Parameters-#####//
        int numberOfFrames = 4;


        //Generating table of processes with appeals and storing it to processesTable
        processesTable = utils.generateTableOfProcessesWithAppeals(numberOfProcesses, numberOfAppeals, maxValue, minValue, offset, numberOfIntervals, secretParameter);



        generatedSequenceOfProcesses = utils.generateSequenceOfProcesses(numberOfProcesses, numberOfAppeals);

        //Equal Allocation

        ArrayDeque<Integer>[] processesTableAlgorithmOne = utils.copyArrayDeque(processesTable);
        ArrayDeque<Integer> generatedSequenceOfProcessesAlgorithmOne = generatedSequenceOfProcesses.clone();

        AlgorithmInterface equalAllocation = new EqualAllocation(numberOfFrames, processesTableAlgorithmOne, generatedSequenceOfProcessesAlgorithmOne);

        equalAllocation.simulate();

        //Proportional Allocation

        ArrayDeque<Integer>[] processesTableAlgorithmTwo = utils.copyArrayDeque(processesTable);
        ArrayDeque<Integer> generatedSequenceOfProcessesAlgorithmTwo = generatedSequenceOfProcesses.clone();

        AlgorithmInterface proportionalAllocation = new ProportionalAllocation(numberOfFrames, processesTableAlgorithmTwo, generatedSequenceOfProcessesAlgorithmTwo);

        proportionalAllocation.simulate();






    }
}
