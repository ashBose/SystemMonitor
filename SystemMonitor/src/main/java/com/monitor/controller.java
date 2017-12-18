package com.monitor;

import java.io.IOException;

public class controller {

	/**
	 * @param args
	 * @throws IOException
	 *
	 */

	public static void main(String[] args) throws IOException {

		// TODO Auto-generated method stub

		if (args.length == 0)
		{
			System.out.println("There were no commandline arguments passed!");
			return;
		}
		String logtype= args[0];
		int timetosleep = Integer.parseInt(args[1]);

		logProducer lp = new logProducer(logtype,timetosleep);
		Thread thread = new Thread(lp);
		//thread.start();

		logConsumer lc = new logConsumer();
		Thread thread1 = new Thread(lc);
		thread1.start();
		
		//livechart graph = new livechart(metrics);
		//Thread thr = new Thread(graph);
		//thr.start();


	}

}
