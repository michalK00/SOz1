package zad5;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;


public class Utils {


    public void reloadList(ArrayList<CPU> cpuList){
        for(int x = 0; x < cpuList.size(); x++){
            cpuList.get(x).setCpuProcessList((ArrayList<Process>) cpuList.get(x).getCpuProcessListCopy().clone());
        }
    }

    public void createOutputChart(ArrayList[] cpuStats, String strategyNumber){

        XYChart chart = new XYChartBuilder().width(800).height(600).title("Strategy number " + strategyNumber).xAxisTitle("X").yAxisTitle("Load").build();

        for(int x = 0;x<cpuStats.length;x++){
            int[] xData = new int[cpuStats[x].size()];
            int[] yData = new int[cpuStats[x].size()];
            for(int y = 0; y < cpuStats[x].size(); y++){
                yData[y] = (int) cpuStats[x].get(y);
                xData[y] = y;
            }
            chart.addSeries("CPU " + x, xData, yData);
        }


        chart.getStyler().setZoomEnabled(true);
        chart.getStyler().setZoomResetByDoubleClick(true);
        chart.getStyler().setLegendVisible(true);


        new SwingWrapper(chart).displayChart();
    }
}
