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
private String isprint;//打印次数
private String taskId;//订单号
private String orderTime;//下单时间
private String tableId;//桌位编号?环境
private String peopleCount;//人数
private String realName;//就餐联系人
private String mobile;//联系电话
//private String total;//总计
//private String payMethod;//支付方式
private TaskObject taskObject;
private String IP,Port;
private int PortInt;

private String line="------------------------------------------------";

//private final String SUCCESS="打印成功";
//private final String FAIL="打印失败";

public ResultObject resultObject=null;
public ArrayList<ResultObject> arrayListResult;
public DingweiPrint(TaskObject task,String IP,String Port){
	taskObject=task;
	isprint=task.isprint;
	taskId=task.taskid;
	orderTime=task.ordertime;
	tableId=task.tableid;//环境？桌位号
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
	String titlePrt="花满棠.华侨城";
	if(isprint.equals("1")||isprint==null){
		titlePrt="花满棠.华侨城";
	}else{
		titlePrt="花满棠.华侨城(再次打印)";
	}
	String taskidPrt="单号："+taskId;
	String orderTimePrt="时间："+orderTime;
	String tableIdPrt="桌位号："+tableId;
	String peopleCountPrt="人数："+peopleCount;
	String realNamePrt="客户："+realName;
	String mobilePrt="电话："+mobile;
	//String totalPrt="消费："+total;
	//String payMethodPrt="支付方式："+payMethod;
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
		out.write("(订位单)".getBytes("gb2312"));
		out.write(EPSCommand.nextLine(1));
		out.write(EPSCommand.alignLeft());
		out.write(taskidPrt.getBytes("gb2312"));
		out.write(EPSCommand.nextLine(2));
		out.write(orderTimePrt.getBytes("gb2312"));
		out.write(EPSCommand.nextLine(1));
		out.write(line.getBytes("gb2312"));//画线
		out.write(EPSCommand.nextLine(1));
		if(realName!=null||mobile!=null){//联系人或电话不为null
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
