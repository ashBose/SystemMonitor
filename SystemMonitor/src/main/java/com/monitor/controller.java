package com.monitor;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


public class controller {

	/**
	 * @param args
	 * @throws IOException
	 *
	 */

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		logProducer lp = new logProducer("CPU",1000);
		Thread thread = new Thread(lp);
		thread.start();

		logConsumer lc = new logConsumer();
		Thread thread1 = new Thread(lc);
		thread1.start();
		
		//livechart graph = new livechart(metrics);
		//Thread thr = new Thread(graph);
		//thr.start();


	}

}
