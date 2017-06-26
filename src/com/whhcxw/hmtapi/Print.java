package com.whhcxw.hmtapi;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.whhcxw.hmtpritnt.PrintService;
import com.whhcxw.object.*;
public class Print extends HttpServlet{
Queue<RequestObject> queue;//队列
String typeUtf8="";
String taskUtf8="";
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	System.out.println("This is doGet");//test
    String result="";
    response.setContentType("text/plain; charset=utf-8");
	request.setCharacterEncoding("utf-8");
	String type=request.getParameter("type");
	String task=request.getParameter("task");
	System.out.println(task);
	try{
		 typeUtf8=new String(type.getBytes("iso8859-1"),"utf-8");
		 taskUtf8=new String(task.getBytes("iso8859-1"),"utf-8");
	}catch(Exception e){
		System.out.println("utf8-error");	
	}
	
	System.out.println("taskUtf8:"+taskUtf8);
	RequestObject requestObject=new RequestObject();
	requestObject.setType(typeUtf8);
	requestObject.setTask(taskUtf8);
	queue.offer(requestObject);
	while(queue.size()!=0){
		RequestObject requestObjectGet=queue.poll();
		PrintService printService=new PrintService(requestObjectGet);
		result=printService.doPrint();
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}
	
	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doPost(request, response);
		System.out.println("This is doPost");//test
		//System.out.println(request.getRequestURI()+" "+request.getQueryString());
		String result="";
	    response.setContentType("text/plain; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		String type=request.getParameter("type");
		String task=request.getParameter("task");
		System.out.println("type:"+type);
		System.out.println("task:"+task);
		String typeUtf8=type;//doPost不设置转型
		String taskUtf8=task;//doPost不设置转型
		RequestObject requestObject=new RequestObject();
		requestObject.setType(typeUtf8);
		requestObject.setTask(taskUtf8);
		queue.offer(requestObject);
		while(queue.size()!=0){
			RequestObject requestObjectGet=queue.poll();
			PrintService printService=new PrintService(requestObjectGet);
			result=printService.doPrint();
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		}
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		queue=new LinkedList<RequestObject>();
		super.init();
	}

}
