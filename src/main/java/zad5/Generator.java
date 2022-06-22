package zad5;

import java.util.ArrayList;
import java.util.Random;

public class Generator {


    public ArrayList<Process> generateSequence(CPU cpu, int numberOfProcesses, int maxLoad, int maxDuration){

        Random random = new Random();
        ArrayList<Process> list = new ArrayList<>();
        int interval = cpu.getGenerateInterval();
        int load;
        int duration;
        int arrivalTime;
        int prevArrivalTime = 0;
        for(int x = 0; x < numberOfProcesses; x++){
            load =  random.nextInt(maxLoad-1)+1;
            duration = random.nextInt(maxDuration-1)+1;
            arrivalTime = prevArrivalTime + interval;
            prevArrivalTime = arrivalTime;
            list.add(new Process(arrivalTime, duration, load));
            cpu.getCpuProcessListCopy().add(new Process(arrivalTime, duration, load));
        }


        return list;
    }
}
