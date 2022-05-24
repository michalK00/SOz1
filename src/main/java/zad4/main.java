package zad4;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) throws Exception {

        Utils utils = new Utils();

        //#####-All-Processes-Parameters-#####//
        int numberOfProcesses = 10;
        int offset = 50;
        ArrayDeque<Integer>[] processesTable;

        //#####-Single-Process-AppealList-Parameters-#####//
        int numberOfAppeals = 50;
        int maxValue = 50;
        int minValue = 0;
        int numberOfIntervals = 5;
        double secretParameter = 0.5;

        //Generating table of processes with appeals and storing it to processesTable
        processesTable = utils.generateTableOfProcessesWithAppeals(numberOfProcesses, numberOfAppeals, maxValue, minValue, offset, numberOfIntervals, secretParameter);

        ArrayDeque<Integer>[] processesTableAlgorithmOne = processesTable.clone();

        List<Integer> generatedSequenceOfProcesses= new ArrayList<Integer>();
        generatedSequenceOfProcesses = utils.generateSequenceOfProcesses(numberOfProcesses, numberOfAppeals);







    }
}
