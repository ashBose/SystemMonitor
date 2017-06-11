
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;

import org.apache.kafka.clients.producer.ProducerRecord;
import com.google.common.io.Resources;
import java.io.IOException;

public class logProducer  implements Runnable{
	ProducerConfig config;
	KafkaProducer<String, byte[]> producer;
    Random rnd = new Random();
    SystemMonitor tracker = null;
	
	public logProducer() throws IOException {
		try (InputStream props = Resources.getResource("producer.props").openStream()) {
            Properties properties = new Properties();
            properties.load(props);
            producer = new KafkaProducer<>(properties);
        }
		tracker = new SystemMonitor();
	}

	public void run() {
		try {
            for (int i = 0; i < 1000; i++) {
                // send lots of messages
            	HashMap<String, Double> val = tracker.getSystemStatistics();
                producer.send(new ProducerRecord<String, byte[]>(
                        "fast-messages",
                        util.mapToByte(val)));
                
            	/*
            	System.out.println(val);
            	System.out.println(" now string");
            	System.out.println(util.mapToByte(val));
            	*/
               Thread.sleep(1000);
               
            }
        } catch (Throwable throwable) {
            System.out.printf("%s", throwable.getStackTrace());
        } finally {
            producer.close();
        }
	}
}
