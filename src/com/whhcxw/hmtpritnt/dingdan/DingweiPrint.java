package com.whhcxw.hmtpritnt.dingdan;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;
import com.whhcxw.epscommand.EPSCommand;
import com.whhcxw.object.ResultObject;
import com.whhcxw.object.TaskObject;

public class DingweiPrint {
private String isprint;//��ӡ����
private String taskId;//������
private String orderTime;//�µ�ʱ��
private String tableId;//��λ���?����
private String peopleCount;//����
private String realName;//�Ͳ���ϵ��
private String mobile;//��ϵ�绰
//private String total;//�ܼ�
//private String payMethod;//֧����ʽ
private TaskObject taskObject;
private String IP,Port;
private int PortInt;

private String line="------------------------------------------------";

//private final String SUCCESS="��ӡ�ɹ�";
//private final String FAIL="��ӡʧ��";

public ResultObject resultObject=null;
public ArrayList<ResultObject> arrayListResult;
public DingweiPrint(TaskObject task,String IP,String Port){
	taskObject=task;
	isprint=task.isprint;
	taskId=task.taskid;
	orderTime=task.ordertime;
	tableId=task.tableid;//��������λ��
	peopleCount=task.peoplecount;
	realName=task.realName;
	mobile=task.mobile;
	//total=task.totel;
	//payMethod=task.paymethod;
	
	this.IP=IP;
	this.Port=Port;
	PortInt=Integer.parseInt(Port);
	
	resultObject=new ResultObject();
	arrayListResult=new ArrayList<ResultObject>();
}
public String printDingwei(){
	Gson gson=new Gson();
	String titlePrt="������.���ȳ�";
	if(isprint.equals("1")||isprint==null){
		titlePrt="������.���ȳ�";
	}else{
		titlePrt="������.���ȳ�(�ٴδ�ӡ)";
	}
	String taskidPrt="���ţ�"+taskId;
	String orderTimePrt="ʱ�䣺"+orderTime;
	String tableIdPrt="��λ�ţ�"+tableId;
	String peopleCountPrt="������"+peopleCount;
	String realNamePrt="�ͻ���"+realName;
	String mobilePrt="�绰��"+mobile;
	//String totalPrt="���ѣ�"+total;
	//String payMethodPrt="֧����ʽ��"+payMethod;
	Socket socket;
	try {
		socket = new Socket(IP,PortInt);
		OutputStream out=socket.getOutputStream();
		
		out.write(EPSCommand.init_printer());
		out.write(EPSCommand.fontSizeSetBig(2));
		out.write(EPSCommand.alignCenter());
		out.write(titlePrt.getBytes("gb2312"));
		out.write(EPSCommand.nextLine(1));
		out.write(EPSCommand.fontSizeSetBig(1));
		out.write("(��λ��)".getBytes("gb2312"));
		out.write(EPSCommand.nextLine(1));
		out.write(EPSCommand.alignLeft());
		out.write(taskidPrt.getBytes("gb2312"));
		out.write(EPSCommand.nextLine(2));
		out.write(orderTimePrt.getBytes("gb2312"));
		out.write(EPSCommand.nextLine(1));
		out.write(line.getBytes("gb2312"));//����
		out.write(EPSCommand.nextLine(1));
		if(realName!=null||mobile!=null){//��ϵ�˻�绰��Ϊnull
		out.write(new String(realNamePrt+spaceNumStr(18)+mobilePrt).getBytes("gb2312"));
		}
		out.write(EPSCommand.nextLine(2));
		out.write(new String(tableIdPrt+spaceNumStr(18)+peopleCountPrt).getBytes("gb2312"));
		out.write(EPSCommand.nextLine(3));
		out.write(EPSCommand.feedPaperCutAll());
		out.write(EPSCommand.nextLine(3));
		out.flush();
		out.close();
		socket.close();
		resultObject.setIP(IP);
		resultObject.setSuccess(true);
		arrayListResult.add(resultObject);
		return gson.toJson(arrayListResult);
	} catch (UnknownHostException e) {
		
		// TODO Auto-generated catch block
		e.printStackTrace();
		resultObject.setIP(IP);
		resultObject.setSuccess(false);
		arrayListResult.add(resultObject);
		return gson.toJson(arrayListResult);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		resultObject.setIP(IP);
		resultObject.setSuccess(false);
		arrayListResult.add(resultObject);
		return gson.toJson(arrayListResult);
	}

}
public String spaceNumStr(int num){
	String space=new String();
	for(int i=0;i<num;i++){
		space=space+" ";
	}
	return space;
}
}
