package com.monitor;

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
    String logType;
    int timeToSleep;
	
	public logProducer(String logType,int timeToSleep) throws IOException {
	        this.logType = logType;
	        this.timeToSleep = timeToSleep;
            InputStream props = Resources.getResource("producer.props").openStream();
            Properties properties = new Properties();
            properties.load(props);
            producer = new KafkaProducer<String, byte[]>(properties);
        
		tracker = new SystemMonitor();
	}

	public void run() {
		try {
            while(true) {
                // send lots of messages
            	HashMap<String, Object> val = tracker.getSystemStatistics(logType);
                producer.send(new ProducerRecord<String, byte[]>(
                        "fast-mesages",
                        util.mapToByte(val)));
                
            	/*
            	System.out.println(val);
            	System.out.println(" now string");
            	System.out.println(util.mapToByte(val));
            	*/

               Thread.sleep(timeToSleep);
               
            }
        } catch (Throwable throwable) {
            System.out.printf("%s", throwable.getStackTrace());
        } finally {
            producer.close();
        }
	}
}
