package main.java.com.monitor;

import java.util.*;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;


public class SystemMonitor {
	
	private static Sigar sigar = new Sigar();
	
	public HashMap getSystemStatistics(){
	    Mem mem = null;
	    CpuPerc cpuperc = null;
	    FileSystemUsage filesystemusage = null;
	    HashMap<String, Double> data = new HashMap<String, Double>();
	    try {
	        mem = sigar.getMem();
	        cpuperc = sigar.getCpuPerc();
	        data.put("CPU", cpuperc.getCombined()*100);
	        data.put("Memory", mem.getUsedPercent());        
	    } catch (SigarException se) {
	        se.printStackTrace();
	    }
	    return data;
	}
}
