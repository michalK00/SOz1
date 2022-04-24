package zad1;

import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.random.AbstractRandomGenerator;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.ArrayList;
import java.util.Random;

public class utilities {

    public static ArrayList<Process> generateProcesses(int n, int upperBound){

        ArrayList<Process> list = new ArrayList<>();
        Random r = new Random();
        for(int x = 0;x<n;x++){
            int processingTime = (int) Math.round(r.nextGaussian(20,3));
            //int processingTime = r.nextInt(2,20);
            int arrivalTime = r.nextInt(1,upperBound);
            list.add(new Process(arrivalTime,processingTime));
        }

        return list;
    }

    public static ArrayList<Integer> betaDistribution(int elementsCount, int maxValueForOneSample,double alpha, double beta){

        BetaDistribution betaDistribution = new BetaDistribution(new AbstractRandomGenerator() {
            Random r = new Random();
            @Override
            public void setSeed(long l) {
                r.setSeed(l);
            }

            @Override
            public double nextDouble() {
                return r.nextDouble();
            }
        },alpha,beta );


        ArrayList<Integer> list = new ArrayList<>();
        for (int x = 0; x<elementsCount;x++){
            list.add((int) Math.round(betaDistribution.sample()*(maxValueForOneSample-1))+1);
        }
        return list;
    }
    public static ArrayList<Process> combineTwoLists(ArrayList<Integer> processingTimeList, ArrayList<Integer> arrivalTimeList){
        ArrayList<Process> output = new ArrayList<>();
        for (int x = 0;x<processingTimeList.size();x++) {
            output.add(new Process(arrivalTimeList.get(x), processingTimeList.get(x)));
        }
        return output;
    }
    public static void createOutputChart(ArrayList<Integer> list, String title, String xAxisTitle, String yAxisTitle, String dataSetName){
        double[] xData = new double[list.size()];
        double[] yData = new double[list.size()];
        for(int x = 0;x<list.size();x++){
            yData[x] = list.get(x);
            xData[x] = x+1;
        }

        XYChart chart = QuickChart.getChart(title, xAxisTitle, yAxisTitle, dataSetName, xData, yData);
        chart.getStyler().setZoomEnabled(true);
        chart.getStyler().setZoomResetByDoubleClick(true);

        //
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);


        new SwingWrapper(chart).displayChart();
    }
    public static void createHistogram(ArrayList<Integer> list, String title, int maxCapForData, int binNumber, String dataSetName){

        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title(title).xAxisTitle("Mean").yAxisTitle("Count").build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setAvailableSpaceFill(.96);
        chart.getStyler().setOverlapped(true);

        Histogram histogram1 = new Histogram(list, binNumber, 0, maxCapForData);

        chart.addSeries(dataSetName, histogram1.getxAxisData(), histogram1.getyAxisData());


        new SwingWrapper(chart).displayChart();

    }
    public static void sortByArrivalTime(ArrayList<Process> list){
        list.sort(Process::arrivalTimeComparator);
    }
    public static void sortByProcessingTime(ArrayList<Process> list){
        list.sort(Process::processingTimeComparator);
    }

    public static void resetList(ArrayList<Process> list){
        for (Process p: list) {
            p.setProgress(0);
            p.setFinished(false);
            p.setWaitingTime(0);
        }
    }

    public static void printingStats(ArrayList<Process> done){
        double averageWaitingTime = 0;
        for (Process p :done){
            averageWaitingTime += p.getWaitingTime();
        }
        int longestWaitingTime = 0;
        for (Process p : done){
            if(p.getWaitingTime()>longestWaitingTime){
                longestWaitingTime=p.getWaitingTime();
            }
        }
        System.out.println("Longest waiting time: " + longestWaitingTime);
        averageWaitingTime= averageWaitingTime/done.size();
        System.out.println("Average waiting time: " + averageWaitingTime);
        double averageCompletionTime = 0;
        for (Process p :done){
            averageCompletionTime += p.getCompletionTime()-p.getArrivalTime();
        }
        averageCompletionTime= averageCompletionTime/done.size();
        System.out.println("Average completion time (from arrival to fully completed): " + averageCompletionTime);
    }
}
