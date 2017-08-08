package main.java.com.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import org.HdrHistogram.Histogram;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Queue;
import java.util.Random;


public class logConsumer implements Runnable {
	ObjectMapper mapper = new ObjectMapper();
    Histogram stats = new Histogram(1, 10000000, 2);
    Histogram global = new Histogram(1, 10000000, 2);
    int timeouts = 0;
    KafkaConsumer<String, byte[]> consumer;
    Queue<Double> sharedQueue; 
    
    logConsumer(Queue<Double> metrics) throws IOException {
            InputStream props = Resources.getResource("consumer.props").openStream(); 
            Properties properties = new Properties();
            properties.load(props);
            if (properties.getProperty("group.id") == null) {
                properties.setProperty("group.id", "group-" + new Random().nextInt(100000));
            }
            consumer = new KafkaConsumer<String, byte[]>(properties);
        consumer.subscribe(Arrays.asList("fast-messages"));
        sharedQueue = metrics;
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
            // read records with a short timeout. If we time out, we don't really care.
            ConsumerRecords<String, byte[]> records = consumer.poll(2000);
            if (records.count() == 0) {
                timeouts++;
            } else {
                System.out.printf("Got %d records after %d timeouts\n", records.count(), timeouts);
                timeouts = 0;
            }
            try {
            for (ConsumerRecord<String, byte[]> record : records) {
					byte[] msg = null;
			msg=record.value();
			HashMap<String, Double> tmp = util.byteToMap(msg);
			sharedQueue.add(tmp.get("CPU"));
					
			}
             } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}           
          catch(ClassNotFoundException e) {
              e.printStackTrace();  
	  }
	}
   }   
}
