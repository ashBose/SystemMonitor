package com.monitor;

import java.util.Queue;
import java.util.Random;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class livechart  implements Runnable {
    static TimeSeries ts = new TimeSeries("data", Millisecond.class);
    Queue<Double> sharedQueue;
    
   public livechart(Queue<Double> metrics) {

        TimeSeriesCollection dataset = new TimeSeriesCollection(ts);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "GraphTest",
            "Time",
            "CPU Value",
            dataset,
            true,
            true,
            false
        );
        final XYPlot plot = chart.getXYPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);

        JFrame frame = new JFrame("GraphTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChartPanel label = new ChartPanel(chart);
        frame.getContentPane().add(label);
        //Suppose I add combo boxes and buttons here later

        frame.pack();
        frame.setVisible(true);
        sharedQueue = metrics;
        
    }

        public void run() {
        	double old= 0, newval;
            while(true) {
                if (sharedQueue.isEmpty())
                	newval = old;
                else {
                	newval = sharedQueue.poll();
                	old = newval;
                }
                ts.addOrUpdate(new Millisecond(), newval);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
            }
        
    }
}
