package com.whhcxw.hmtapi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.whhcxw.hmtpritnt.PrintService;
import com.whhcxw.hmtsetting.DepartMentMap;
import com.whhcxw.object.*;
public class AddIP extends HttpServlet{
Queue<RequestObject> queue;//队列
String task,taskUtf8;//AddDepartmentRequest
AddDartmentObjRequest addDartmentObjRequest;
AddDartmentObjResult addDartmentObjResult;
//String departmentName;
//String IP;
//String departmentUtf8;
//String IPUtf8;
//Settings settings;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	String departmentName;
	String IP;
	String departmentUtf8;
	String IPUtf8;
	System.out.println("This is doGet");//test
    String result="";
    response.setContentType("text/plain; charset=utf-8");
    
	request.setCharacterEncoding("utf-8");
	String task=request.getParameter("task");
//	String departmentName=request.getParameter("DepartmentName");
//	String IP=request.getParameter("IP");
//	System.out.println("department:"+departmentName+" "+"IP:"+IP);
	try{
		taskUtf8=task;
		Gson gson=new Gson();
		addDartmentObjRequest=gson.fromJson(taskUtf8, AddDartmentObjRequest.class);
		departmentName=addDartmentObjRequest.getDepartmentName();
		IP=addDartmentObjRequest.getIP();
		if((departmentName!=null)&&(IP!=null)){
			departmentUtf8=new String(departmentName.getBytes("iso8859-1"),"utf-8");
			 IPUtf8=new String(IP.getBytes("iso8859-1"),"utf-8");
			 String filePath=AddIP.class.getClassLoader().getResource("Settings.properties").getPath();
			 Properties prop=new Properties();
			 InputStream fis=new FileInputStream(filePath);
			 prop.load(fis);
			 OutputStream fos=new FileOutputStream(filePath);
			 prop.setProperty(departmentUtf8, IPUtf8);
			 prop.store(fos, "Update");
			 System.out.println(prop.get(departmentUtf8));
			 //DepartMentMap.putDepartment(departmentUtf8, IPUtf8);
			// System.out.print(departmentUtf8+DepartMentMap.getIP(departmentUtf8));
			 fis.close();
			 fos.close();
			 addDartmentObjResult.setSuccess(true);
			 PrintWriter out=response.getWriter();
			 out.print(gson.toJson(addDartmentObjResult));
			 out.flush();
			 out.close();
		}else{
			 addDartmentObjResult.setSuccess(false);
			 PrintWriter out=response.getWriter();
			 out.print(gson.toJson(addDartmentObjResult));
			 out.flush();
			 out.close();
		}
	
		 //settings.writeProperties(departmentUtf8, IPUtf8);
		 //String filePath=Settings.class.getClassLoader().getResource("Settings.properties").getPath();
		 //System.out.println(filePath);
		 //Properties properties=new Properties();
		 //properties.load(new FileInputStream(filePath));
		// OutputStream fos=new FileOutputStream(filePath);
		 //properties.setProperty(departmentUtf8, IPUtf8);
		// properties.store(fos, "Update"+departmentUtf8+"value");
		// fos.flush();
		 //fos.close();
	}catch(Exception e){
		System.out.println("utf8-error");	
		
	}
	
	
//	RequestObject requestObject=new RequestObject();
//	requestObject.setType(typeUtf8);
//	requestObject.setTask(taskUtf8);
//	queue.offer(requestObject);
//	while(queue.size()!=0){
//		RequestObject requestObjectGet=queue.poll();
//		PrintService printService=new PrintService(requestObjectGet);
//		result=printService.doPrint();
//		PrintWriter out = response.getWriter();
//		out.print(result);
//		out.flush();
//		out.close();
//	}
	
	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(request, response);
		String departmentName;
		String IP;
		String departmentUtf8;
		String IPUtf8;
		
		System.out.println("This is doPost");//test
		String result="";
	    response.setContentType("text/plain; charset=utf-8");
		request.setCharacterEncoding("utf-8");
//		String type=request.getParameter("type");
//		String task=request.getParameter("task");
//		System.out.println("type:"+type);
//		System.out.println("task:"+task);
		String task=request.getParameter("task");
		taskUtf8=task;
		//taskUtf8=new String(task.getBytes("iso8859-1"),"utf-8");
		Gson gson=new Gson();
		addDartmentObjRequest=gson.fromJson(taskUtf8, AddDartmentObjRequest.class);
		departmentName=addDartmentObjRequest.getDepartmentName();
		IP=addDartmentObjRequest.getIP();
//		String departmentName=request.getParameter("DepartmentName");
//		String IP=request.getParameter("IP");
//		String typeUtf8=type;//doPost不设置转型
//		String taskUtf8=task;//doPost不设置转型
		if((departmentName!=null)&&(IP!=null)){
			 departmentUtf8=departmentName;
			 IPUtf8=IP;
			//settings.writeProperties(departmentUtf8, IPUtf8);
			DepartMentMap.putDepartment(departmentUtf8, IPUtf8);
			System.out.print(departmentUtf8+DepartMentMap.getIP(departmentUtf8));
//			System.out.println("departmentUtf8:"+departmentUtf8);
//			System.out.println("IPUtf8:"+IPUtf8);
			addDartmentObjResult.setSuccess(true);
			PrintWriter out=response.getWriter();
			out.print(gson.toJson(addDartmentObjResult));
			out.flush();
			out.close();
		}else{
			addDartmentObjResult.setSuccess(false);
			PrintWriter out=response.getWriter();
			out.print(gson.toJson(addDartmentObjResult));
			out.flush();
			out.close();
		}
		
		
		
//		RequestObject requestObject=new RequestObject();
//		requestObject.setType(typeUtf8);
//		requestObject.setTask(taskUtf8);
//		queue.offer(requestObject);
//		while(queue.size()!=0){
//			RequestObject requestObjectGet=queue.poll();
//			PrintService printService=new PrintService(requestObjectGet);
//			result=printService.doPrint();
//			PrintWriter out = response.getWriter();
//			out.print(result);
//			out.flush();
//			out.close();
//		}
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		queue=new LinkedList<RequestObject>();
		addDartmentObjResult=new AddDartmentObjResult();
		//settings=new Settings();
		super.init();
	}

}
