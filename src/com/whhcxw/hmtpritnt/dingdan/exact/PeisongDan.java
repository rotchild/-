package com.whhcxw.hmtpritnt.dingdan.exact;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.whhcxw.epscommand.EPSCommand;
import com.whhcxw.object.RequestObject;
import com.whhcxw.object.ResultObject;
import com.whhcxw.object.TaskObject;
import com.whhcxw.object.TaskObject.Dishids.Data;

public class PeisongDan {
	//堂食
	private String isprint;//打印次数
	private String taskId;//订单号
	private String orderTime;//下单时间
	//private String tableId;//桌位编号
	private String peopleCount;//人数
	private String realName;//就餐联系人
	private String mobile;//联系电话
	private String total;//总计
	private String payMethod;//支付方式
	public String address;//地址
	//public String sendtime;//送达时间
	public String diningtime;//配送时间
	private TaskObject taskObject;
	private String IP,Port;
	private int PortInt;

	private String line="------------------------------------------------";

	private final String SUCCESS="打印成功";
	private final String FAIL="打印失败";
	private String mType;//外卖、食杂
	public ResultObject resultObject=new ResultObject();
	public PeisongDan(TaskObject task,String IP,String Port,String mType){//mType为外卖、食杂
		
		taskObject=task;
		isprint=task.isprint;
		taskId=task.taskid;
		orderTime=task.ordertime;
		//sendtime=task.sendtime;
		diningtime=task.diningtime;
		address=task.address;
		//tableId=task.tableid;
		peopleCount=task.peoplecount;
		realName=task.realName;
		mobile=task.mobile;
		this.mType=mType;
		
		this.IP=IP;
		this.Port=Port;
		PortInt=Integer.parseInt(Port);
		
		ResultObject resultObject=new ResultObject();
	}
	public ResultObject printPeisongDan(){
		
		String titlePrt="";
		String taskidPrt="单号："+taskId;
		if(isprint.equals("1")||isprint==null){
			titlePrt=taskidPrt;
		}else{
			titlePrt=taskidPrt+"(再次打印)";
		}
		
		String orderTimePrt="下单时间："+orderTime;
		String sendtimePrt="配送时间："+diningtime;
		String addressPrt="配送地址："+address;
		//String tableIdPrt="桌位号："+tableId;
		String peopleCountPrt="人数："+peopleCount;
		String realNamePrt="客户："+realName;
		String mobilePrt="电话："+mobile;
		
		String subtitlePrt="外卖配送单";
		if(mType.equals("食杂")){
			subtitlePrt="食杂配送单";
		}
		Socket socket;
		try {
			socket = new Socket(IP,PortInt);
			OutputStream out=socket.getOutputStream();
			
			out.write(EPSCommand.init_printer());
			out.write(EPSCommand.fontSizeSetBig(2));
			out.write(EPSCommand.alignCenter());
			out.write(titlePrt.getBytes("gb2312"));
			out.write(EPSCommand.nextLine(2));
			out.write(subtitlePrt.getBytes("gb2312"));
			out.write(EPSCommand.nextLine(2));
			out.write(EPSCommand.fontSizeSetBig(1));
			out.write(EPSCommand.alignLeft());
			//out.write(taskidPrt.getBytes("gb2312"));
			out.write(EPSCommand.nextLine(2));
			out.write(orderTimePrt.getBytes("gb2312"));
			out.write(EPSCommand.nextLine(1));
			out.write(sendtimePrt.getBytes("gb2312"));
			out.write(EPSCommand.nextLine(1));
			out.write(line.getBytes("gb2312"));//画线
			out.write(EPSCommand.nextLine(1));
			out.write(new String(realNamePrt+spaceNumStr(18)+mobilePrt).getBytes("gb2312"));
			out.write(EPSCommand.nextLine(2));
			out.write(addressPrt.getBytes("gb2312"));
			out.write(EPSCommand.nextLine(1));
			out.write(peopleCountPrt.getBytes("gb2312"));
			
			//out.write(new String(tableIdPrt+spaceNumStr(18)+peopleCountPrt).getBytes("gb2312"));
			out.write(EPSCommand.nextLine(2));
			out.write(new String("菜品名称："+spaceNumStr(4)+"数量"+spaceNumStr(11)+"单价"+spaceNumStr(10)+"小计").getBytes("gb2312"));
			out.write(EPSCommand.nextLine(1));
			out.write(line.getBytes("gb2312"));
			out.write(EPSCommand.nextLine(1));
			for(int i=0;i<taskObject.dishids.size();i++){
				TaskObject.Dishids dishids=taskObject.dishids.get(i);
				for(int j=0;j<dishids.data.size();j++){
					TaskObject.Dishids.Data dishdata=dishids.data.get(j);
					int nameLenth,menucountLenth,presentpriceLenth,priceLenth;
					   String dishName=dishdata.dishname;
					   nameLenth=(dishName!=null)?dishName.length()*2:0;
					   String menucount=String.valueOf(dishdata.menucount);
					   int menucountInt=Integer.parseInt(menucount);
					   menucountLenth=(menucount!=null)?menucount.length():0;
					   String presentprice=dishdata.presentprice;
					   float presentpriceFloat=Float.parseFloat(presentprice);
					   presentpriceLenth=(presentprice!=null)?presentprice.length():0;
					   String price=String.valueOf(menucountInt*presentpriceFloat);//小计
					   priceLenth=(price!=null)?price.length():0;
					

					   int lenLeft1=18-nameLenth-menucountLenth;
					   int lenLeft2=16-presentpriceLenth;
					   int lenLeft3=14-priceLenth;

					   if((lenLeft1>0)&&(lenLeft2>0)&&(lenLeft3>0)){
					   out.write(new String(dishName+spaceNumStr(lenLeft1)+menucount+spaceNumStr(lenLeft2)+presentprice+spaceNumStr(lenLeft3)+price).getBytes("gb2312"));
					   }else{
						   out.write(new String(dishName+" "+menucount+" "+presentprice+" "+price).getBytes("gb2312"));  
					   }
					   out.write(EPSCommand.nextLine(1));	
				}

			}
			out.write(line.getBytes("gb2312"));
			
			out.write(EPSCommand.nextLine(3));
			out.write(EPSCommand.feedPaperCutAll());
			out.write(EPSCommand.nextLine(3));
			out.flush();
			out.close();
			socket.close();
			resultObject.setIP(IP);
			resultObject.setSuccess(true);
			return resultObject;
		} catch (UnknownHostException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultObject.setIP(IP);
			resultObject.setSuccess(false);
			return resultObject;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultObject.setIP(IP);
			resultObject.setSuccess(false);
			return resultObject;
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
