package zad2.Algorithms.RealTime;

import zad2.Request;

import java.util.ArrayList;

public interface RealTimeAlgorithmInterface {

    int totalNumberOfHeadPasses = 0;

    public ArrayList<Request> simulate(ArrayList<Request> normalRequestList, ArrayList<Request> realTimeRequestList);


    public int getTotalNumberOfHeadPasses();
}
