package com.monitor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
public class util {

	public static byte[]  mapToByte(HashMap<String, Object> val) throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	    ObjectOutputStream out = new ObjectOutputStream(byteOut);
	    out.writeObject(val);
	    return byteOut.toByteArray();
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> byteToMap(byte[] val) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis  = new ByteArrayInputStream(val);
		ObjectInputStream ois = new ObjectInputStream(bis);
		return (HashMap<String,Object>) ois.readObject();
	}
	
}
