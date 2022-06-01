package zad4;

import zad4.Algorithms.*;

import java.util.ArrayDeque;


public class main {
    public static void main(String[] args) throws Exception {

        Utils utils = new Utils();

        //#####-All-Processes-Parameters-#####//
        int numberOfProcesses = 10;
        int offset = 51;
        ArrayDeque<Integer>[] processesTable;
        ArrayDeque<Integer> generatedSequenceOfProcesses;

        //#####-Single-Process-AppealList-Parameters-#####//
        int numberOfAppeals = 50;
        int maxValue = 25;
        int minValue = 0;
        int numberOfIntervals = 5;
        double secretParameter = 0.6;

        //#####-Algorithm-Parameters-#####//
        int numberOfFrames = 40;


        //Generating table of processes with appeals and storing it to processesTable
        processesTable = utils.generateTableOfProcessesWithAppeals(numberOfProcesses, numberOfAppeals, maxValue, minValue, offset, numberOfIntervals, secretParameter);



        generatedSequenceOfProcesses = utils.generateSequenceOfProcesses(numberOfProcesses, numberOfAppeals);

        //Equal Allocation

        ArrayDeque<Integer>[] processesTableAlgorithmOne = utils.copyArrayDeque(processesTable);
        ArrayDeque<Integer> generatedSequenceOfProcessesAlgorithmOne = generatedSequenceOfProcesses.clone();

        AlgorithmInterface equalAllocation = new EqualAllocation(numberOfFrames, processesTableAlgorithmOne, generatedSequenceOfProcessesAlgorithmOne);

        equalAllocation.simulate();
        equalAllocation.stats();

        //Proportional Allocation

        ArrayDeque<Integer>[] processesTableAlgorithmTwo = utils.copyArrayDeque(processesTable);
        ArrayDeque<Integer> generatedSequenceOfProcessesAlgorithmTwo = generatedSequenceOfProcesses.clone();

        AlgorithmInterface proportionalAllocation = new ProportionalAllocation(numberOfFrames, processesTableAlgorithmTwo, generatedSequenceOfProcessesAlgorithmTwo);

        proportionalAllocation.simulate();
        proportionalAllocation.stats();


        //PPF
        double upperBound = 0.4;
        double lowerBound = 0.01;
        int timeWindow = 20;


        ArrayDeque<Integer>[] processesTableAlgorithmThree = utils.copyArrayDeque(processesTable);
        ArrayDeque<Integer> generatedSequenceOfProcessesAlgorithmThree = generatedSequenceOfProcesses.clone();

        AlgorithmInterface ppf = new PPF(numberOfFrames, processesTableAlgorithmThree, generatedSequenceOfProcessesAlgorithmThree, upperBound, lowerBound, timeWindow);

        ppf.simulate();
        ppf.stats();

        //Zone Model
        int c = 25;
        timeWindow = 50;


        ArrayDeque<Integer>[] processesTableAlgorithmFour = utils.copyArrayDeque(processesTable);
        ArrayDeque<Integer> generatedSequenceOfProcessesAlgorithmFour = generatedSequenceOfProcesses.clone();

        AlgorithmInterface zoneModel = new ZoneModel(numberOfFrames, processesTableAlgorithmFour, generatedSequenceOfProcessesAlgorithmFour, timeWindow, c);

        zoneModel.simulate();
        zoneModel.stats();






    }
}
