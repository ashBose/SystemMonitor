package com.monitor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;


public class SystemMonitor {
	
	private static Sigar sigar = new Sigar();
	public HashMap<String, Object> getSystemStatistics(String logType) throws UnknownHostException
	{
		if(logType.equals("CPU"))
		{
			return getSystemCPUStatistics();
		}
		else if (logType.equals("Mem"))
		{
			return getSystemMemStatistics();
		}
		else
		{
			return getSystemLog();
		}
	}
	
	public HashMap getSystemCPUStatistics() throws UnknownHostException{
	    CpuPerc cpuperc = null;
	    HashMap<String, Object> data = new HashMap<String, Object>();
	    try {
	        cpuperc = sigar.getCpuPerc();
	        data.put("CPU", cpuperc.getCombined()*100);
			data.put("host",  InetAddress.getLocalHost());
			data.put("type", "cpu");
			data.put("timestamp", util.getCurrentTime());
	    } catch (SigarException se) {
	        se.printStackTrace();
	    }
	    return data;
	}

	public HashMap getSystemMemStatistics() throws UnknownHostException{
		Mem mem = null;
		HashMap<String, Object> data = new HashMap<String, Object>();
		try {
			mem = sigar.getMem();
			data.put("Memory", mem.getUsedPercent());
			data.put("host", InetAddress.getLocalHost());
			data.put("type", "memory");
			data.put("timestamp", util.getCurrentTime());
		} catch (SigarException se) {
			se.printStackTrace();
		}
		return data;
	}

	private String executeCommand(String command) {

		StringBuffer output = new StringBuffer();
		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return output.toString();
	}

	public HashMap getSystemLog() throws UnknownHostException {
		// get last two lines of /var/log/syslog using java system programming
		// read it
		// add to hashmap
		HashMap<String,Object> log = new HashMap<String,Object>();
		try {

			String value = executeCommand("tail -2 /var/log/syslog");
			log.put("LOG", value);
			log.put("host",  InetAddress.getLocalHost());
			log.put("type", "log");
			log.put("timestamp", util.getCurrentTime());
		} catch (Exception se) {
			se.printStackTrace();
		}
		return log;
	}
}