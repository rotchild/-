package com.whhcxw.hmtpritnt.dingdan;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.whhcxw.epscommand.EPSCommand;
import com.whhcxw.object.ResultObject;
import com.whhcxw.object.TaskObject;

public class JiezhanPrint {
private String isprint;//打印次数
private String taskId;//订单号
private String orderTime;//下单时间
private String tableId;//桌位编号
private String peopleCount;//人数
private String realName;//就餐联系人
private String mobile;//联系电话
private String total;//总计
private String totalCount;//总数量
private String payMethod;//支付方式
private TaskObject taskObject;
private String IP,Port;
private int PortInt;

private String line="------------------------------------------------";

//private final String SUCCESS="打印成功";
//private final String FAIL="打印失败";
public ResultObject resultObject=null;
public ArrayList<ResultObject> arrayListResult;
public JiezhanPrint(TaskObject task,String IP,String Port){
	taskObject=task;
	taskId=task.taskid;
	isprint=task.isprint;
	orderTime=task.ordertime;
	tableId=task.tableid;
	peopleCount=task.peoplecount;
	realName=task.realName;
	mobile=task.mobile;
	total=task.totalmoney;
	totalCount=task.totalcount;
	payMethod=task.paymethod;
	
	this.IP=IP;
	this.Port=Port;
	PortInt=Integer.parseInt(Port);
	
	resultObject=new ResultObject();
	arrayListResult=new ArrayList<ResultObject>();
}
public String printJieZhan(){
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
	String totalPrt="消费："+total;
	String totalCountPrt="总数量："+totalCount;
	String payMethodPrt="支付方式："+payMethod;
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
		out.write("堂食消费清单".getBytes("gb2312"));
		out.write(EPSCommand.nextLine(1));
		out.write(EPSCommand.alignLeft());
		out.write(taskidPrt.getBytes("gb2312"));
		out.write(EPSCommand.nextLine(2));
		out.write(orderTimePrt.getBytes("gb2312"));
		out.write(EPSCommand.nextLine(1));
		out.write(line.getBytes("gb2312"));//画线
		out.write(EPSCommand.nextLine(1));
		if(mobile.length()>0){//电话不为null
		out.write(new String(realNamePrt+spaceNumStr(18)+mobilePrt).getBytes("gb2312"));
		}else{
		out.write(new String(realNamePrt+spaceNumStr(18)).getBytes("gb2312"));
		}
		out.write(EPSCommand.nextLine(2));
		out.write(new String(tableIdPrt+spaceNumStr(18)+peopleCountPrt).getBytes("gb2312"));
		out.write(EPSCommand.nextLine(2));
		out.write(new String("菜品名称："+spaceNumStr(4)+"数量"+spaceNumStr(10)+"单价"+spaceNumStr(10)+"小计").getBytes("gb2312"));
		out.write(EPSCommand.nextLine(1));
		out.write(line.getBytes("gb2312"));
		out.write(EPSCommand.nextLine(1));
		for(int i=0;i<taskObject.dishArr.size();i++){
			ArrayList<TaskObject.DishidsJ> arrayIn=taskObject.dishArr.get(i);
			for(int j=0;j<arrayIn.size();j++){
				TaskObject.DishidsJ dishids=arrayIn.get(j);
				int nameLenth,menucountLenth,presentpriceLenth,priceLenth;
				   String dishName=dishids.dishname;
				   nameLenth=(dishName!=null)?dishName.length()*2:0;
				   String menucount=String.valueOf(dishids.menucount);
				   int menucountInt=Integer.parseInt(menucount);
				   menucountLenth=(menucount!=null)?menucount.length():0;
				   String presentprice=dishids.presentprice;
				   float presentpriceFloat=Float.parseFloat(presentprice);
				    presentprice=String.valueOf(presentpriceFloat);//单价转为float的显示形式
				   presentpriceLenth=(presentprice!=null)?presentprice.length():0;
				   String price=String.valueOf(menucountInt*presentpriceFloat);//小计
				  // String price=String.valueOf(dishids.price);//小计
				   priceLenth=(price!=null)?price.length():0;
				   int lenLeft1=18-nameLenth-menucountLenth;
				   int lenLeft2=16-presentpriceLenth;
				   int lenLeft3=14-priceLenth;
                
				   if((lenLeft1>0)&&(lenLeft2>0)){
				   out.write(new String(dishName+spaceNumStr(lenLeft1)+menucount+spaceNumStr(lenLeft2)+presentprice+spaceNumStr(lenLeft3)+price).getBytes("gb2312"));
				   }else{
					   out.write(new String(dishName+" "+menucount+" "+presentprice+" ").getBytes("gb2312"));  
				   }
				   out.write(EPSCommand.nextLine(1));
			}
				//TaskObject.Dishids.Data dishdata=dishids.data.get(j);
		}
		out.write(line.getBytes("gb2312"));
		out.write(new String("服务名称："+spaceNumStr(4)+"数量"+spaceNumStr(10)+"单价"+spaceNumStr(10)+"小计").getBytes("gb2312"));
		out.write(EPSCommand.nextLine(1));
		out.write(line.getBytes("gb2312"));
		out.write(EPSCommand.nextLine(1));
		for(int j=0;j<taskObject.allservice.size();j++){
			TaskObject.Service service=taskObject.allservice.get(j);
			int serviceCount;
			int nameLength,countLength,priceLength,fuwuXiaojiLength;
			String serviceName,serviceCountStr,servicePriceStr;
			float servicePrice;
			serviceCount=service.count;
			serviceCountStr=String.valueOf(serviceCount);
			countLength=(serviceCountStr!=null)?serviceCountStr.length():0;
			
			serviceName=service.servicename;
			nameLength=(serviceName!=null)?serviceName.length()*2:0;
			servicePrice=service.price;
			servicePriceStr=String.valueOf(servicePrice);
			priceLength=(servicePriceStr!=null)?servicePriceStr.length():0;
			String fuwuXiaojiStr=String.valueOf(serviceCount*servicePrice);
			fuwuXiaojiLength=(fuwuXiaojiStr!=null)?fuwuXiaojiStr.length():0;
			int lenLeft1=18-nameLength-countLength;
			int lenLeft2=16-priceLength;
			int lenLeft3=14-fuwuXiaojiLength;//小计
			 if((lenLeft1>0)&&(lenLeft2>0)&&(lenLeft3>0)){
				   out.write(new String(serviceName+spaceNumStr(lenLeft1)+serviceCountStr+spaceNumStr(lenLeft2)+servicePriceStr+spaceNumStr(lenLeft3)+fuwuXiaojiStr).getBytes("gb2312"));
				   }else{
					   out.write(new String(serviceName+" "+serviceCountStr+" "+servicePriceStr+" ").getBytes("gb2312"));  
				   }
				   out.write(EPSCommand.nextLine(1));
			
			
		}
		out.write(EPSCommand.nextLine(1));
		out.write(totalCountPrt.getBytes("gb2312"));
		out.write(EPSCommand.nextLine(1));
		out.write(totalPrt.getBytes("gb2312"));
		out.write(EPSCommand.nextLine(1));
		//out.write(payMethodPrt.getBytes("gb2312"));
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
