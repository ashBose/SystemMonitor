package com.monitor;

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
import java.util.Random;


public class logConsumer implements Runnable {
	ObjectMapper mapper = new ObjectMapper();
    Histogram stats = new Histogram(1, 10000000, 2);
    Histogram global = new Histogram(1, 10000000, 2);
    int timeouts = 0;
    KafkaConsumer<String, byte[]> consumer;
    clientMonitor cm = null;

    logConsumer() throws IOException {
            InputStream props = Resources.getResource("consumer.props").openStream(); 
            Properties properties = new Properties();
            properties.load(props);
            if (properties.getProperty("group.id") == null) {
                properties.setProperty("group.id", "group-" + new Random().nextInt(100000));
            }
            consumer = new KafkaConsumer<String, byte[]>(properties);
        consumer.subscribe(Arrays.asList("fast-mesages"));
        cm = new clientMonitor();
        cm.setUp();
    }

	public void run() {
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
                HashMap<String, Object> tmp = util.byteToMap(msg);
                cm.insertData(tmp, tmp.get("type").toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
          catch(ClassNotFoundException e) {
              e.printStackTrace();  
	    }
	}
   }   
}
