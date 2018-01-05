package com.monitor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class util {

	public static byte[]  mapToByte(HashMap<String, Object> val) throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	    ObjectOutputStream out = new ObjectOutputStream(byteOut);
	    out.writeObject(val);
	    return byteOut.toByteArray();
	}

	public static HashMap<String, Object> byteToMap(byte[] val) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis  = new ByteArrayInputStream(val);
		ObjectInputStream ois = new ObjectInputStream(bis);
		return (HashMap<String,Object>) ois.readObject();
	}

	public static String getCurrentTime() {
		SimpleDateFormat formatUTC = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ssZ");
		formatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
		return formatUTC.format(new Date());
	}
}
