import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


public class controller {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		logProducer lp = new logProducer();
		Thread thread = new Thread(lp);
		thread.start();
		
		Queue<Double> metrics = new LinkedList();
		
		logConsumer lc = new logConsumer(metrics);
		Thread thread1 = new Thread(lc);
		thread1.start();
		
		livechart graph = new livechart(metrics);
		Thread thr = new Thread(graph);
		thr.start();
	}

}
