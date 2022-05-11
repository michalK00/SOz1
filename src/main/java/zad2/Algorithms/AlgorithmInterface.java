package zad2.Algorithms;

import zad2.Request;

import java.util.ArrayList;

public interface AlgorithmInterface {

    int totalNumberOfHeadPasses = 0;

    public ArrayList<Request> simulate(ArrayList<Request> list);

    public int getTotalNumberOfHeadPasses();
}
