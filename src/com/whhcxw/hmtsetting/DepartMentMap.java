package com.whhcxw.hmtsetting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DepartMentMap {
static Map<String,String> map=new HashMap<String,String>();
public static void putDepartment(String departmentName,String departmentIP){
map.put(departmentName, departmentIP);	
}
public static String getIP(String departmentName){
	return map.get(departmentName);
}
public static boolean containIP(String IP){
	return map.containsValue(IP);
}
public static void initMap(){
	String IP1,IP2,PORT;
	int portInt;
	FileInputStream fis;
	Properties properties=new Properties();
	String filePath=DepartMentMap.class.getClassLoader().getResource("Settings.properties").getPath();
	
	try {
		
		fis=new FileInputStream(filePath);
		properties.load(fis);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	IP1="192.168.1.193";
	IP2="192.168.1.194";
	IP1=properties.getProperty("qiantai");
	IP2=properties.getProperty("chuancai");
	
	map.put("Ç°Ì¨", IP1);
	map.put("´«²Ë", IP2);
	
}
}
