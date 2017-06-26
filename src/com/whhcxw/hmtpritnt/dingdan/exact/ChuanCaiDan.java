package com.whhcxw.hmtpritnt.dingdan.exact;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.whhcxw.epscommand.EPSCommand;
import com.whhcxw.object.ResultObject;
import com.whhcxw.object.TaskObject;

public class ChuanCaiDan {//堂食、外卖、食杂
	private String isprint;//打印次数：1为首次打印 ,其余为二次打印
	private boolean allBills;//是否打印总单
	private boolean handBill;//是否打印结账单
	private String taskId;//订单号
	private String orderTime;//下单时间
	private String tableId;//桌位编号
	//private String peopleCount;//人数
	//private String realName;//就餐联系人
	//private String mobile;//联系电话
	//private String total;//总计
	//private String payMethod;//支付方式
	private String typeStr;//类型：堂食、外卖、食杂
	private TaskObject taskObject;
	private String IP,Port;
	private int PortInt;

	private String line="------------------------------------------------";
    public ResultObject resultObject;
	//private final String SUCCESS="打印成功";
	//private final String FAIL="打印失败";
	
	public ChuanCaiDan(TaskObject task,String IP,String Port,String typeStr){
		resultObject=new ResultObject();
		this.typeStr=typeStr;
		taskObject=task;
		taskId=task.taskid;
		orderTime=task.ordertime;
		tableId=task.tableid;
		isprint=task.isprint;
		//allBills=task.allBills;
		handBill=task.handBill;
		//peopleCount=task.peopleCount;
		//realName=task.realName;
		//mobile=task.mobile;
		
		this.IP=IP;
		this.Port=Port;
		PortInt=Integer.parseInt(Port);
	}
	public ResultObject printChuanCaiDan(){
		//String titlePrt="花满棠.华侨城";
		String titlePrt="单号："+taskId;
		
		boolean shouldPrint=true;
		if(isprint.equals("1")||isprint==null){
			//titlePrt="花满棠.华侨城";
			titlePrt="单号："+taskId;
		}else {
			//titlePrt="花满棠.华侨城(二次打印)";
			titlePrt="单号："+taskId+"(二次打印)";
			if(handBill==false){
				shouldPrint=false;
			}
			
			
		}
		if(shouldPrint==true){
			String taskidPrt="单号："+taskId;
			String orderTimePrt="时间："+orderTime;
			String tableIdPrt="桌位号："+tableId;
			//String peopleCountPrt="人数："+peopleCount;
			//String realNamePrt="客户："+realName;
			//String mobilePrt="电话："+mobile;
			
			Socket socket;
			try {
				socket = new Socket(IP,PortInt);
				OutputStream out=socket.getOutputStream();
				
				out.write(EPSCommand.init_printer());
				out.write(EPSCommand.fontSizeSetBig(2));
				out.write(EPSCommand.alignCenter());
				out.write(titlePrt.getBytes("gb2312"));
				out.write(EPSCommand.nextLine(2));
				out.write(typeStr.getBytes("gb2312"));
				out.write(EPSCommand.nextLine(2));
				out.write(EPSCommand.fontSizeSetBig(1));
				out.write(EPSCommand.alignLeft());
				//out.write(taskidPrt.getBytes("gb2312"));
				//out.write(EPSCommand.nextLine(2));
				out.write(orderTimePrt.getBytes("gb2312"));
				out.write(EPSCommand.nextLine(1));
				out.write(line.getBytes("gb2312"));//画线
				//out.write(new String(tableIdPrt+spaceNumStr(18)+peopleCountPrt).getBytes("gb2312"));
				if(typeStr.equals("堂食传菜单")){
			    out.write(EPSCommand.nextLine(1));
				out.write(tableIdPrt.getBytes("gb2312"));
				}
				out.write(EPSCommand.nextLine(2));
				out.write(new String("菜品名称："+spaceNumStr(33)+"数量").getBytes("gb2312"));
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
					   menucountLenth=(menucount!=null)?menucount.length():0;

					   int lenLeft=48-nameLenth-menucountLenth;
					   if(lenLeft>0){
					   out.write(new String(dishName+spaceNumStr(lenLeft)+menucount).getBytes("gb2312"));
					   }else{
						   out.write(new String(dishName+" "+menucount).getBytes("gb2312"));  
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
		}else{
			resultObject.setIP(IP);
			resultObject.setSuccess(true);
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
