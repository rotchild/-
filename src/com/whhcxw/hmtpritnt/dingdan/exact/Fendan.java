package com.whhcxw.hmtpritnt.dingdan.exact;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;

import com.whhcxw.epscommand.EPSCommand;
import com.whhcxw.hmtpritnt.dingdan.TangshiPrint;
//import com.whhcxw.hmtsetting.DepartMentMap;
import com.whhcxw.object.ResultObject;
import com.whhcxw.object.TaskObject;

public class Fendan {//堂食、外卖、食杂
	private String isprint;//打印次数
	private String taskId;//订单号
	private String orderTime;//下单时间
	//private String tableId;//桌位编号
	//private String peopleCount;//人数
	//private String realName;//就餐联系人
	//private String mobile;//联系电话
	//private String total;//总计
	//private String payMethod;//支付方式
	private String typeStr;
	
	private TaskObject taskObject;
//	private String IP,Port;
//	private int PortInt;

	private String line="------------------------------------------------";

	private final String SUCCESS="打印成功";
	private final String FAIL="打印失败";
	
	//private Settings settings;
	private String IP1,IP2;
	private String PORT;
	private int PortInt;
	public Fendan(TaskObject task,String typeStr){
		this.typeStr=typeStr;
		taskObject=task;
		taskId=task.taskid;
		orderTime=task.ordertime;
		isprint=task.isprint;
		
		
		//tableId=task.tableid;
		//peopleCount=task.peoplecount;
		//realName=task.realName;
		//mobile=task.mobile;
		
		
		//this.IP=IP;
		//this.Port=Port;
		//PortInt=Integer.parseInt(Port);
		
//		settings=new Settings();
//		IP1=settings.IP1;
//		IP2=settings.IP2;
//		IP3=settings.IP3;
//		PORT=settings.PORT;
//		PortInt=Integer.parseInt(PORT);
		
//		DepartMentMap.putDepartment("IP1", "192.168.1.193");
//		DepartMentMap.putDepartment("IP2", "192.168.1.194");
//		DepartMentMap.putDepartment("IP1", "192.168.1.196");
		String filePath=Fendan.class.getClassLoader().getResource("Settings.properties").getPath();
		InputStream fis;
		try {
			fis = new FileInputStream(filePath);
			Properties properties=new Properties();
			properties.load(fis);
			IP1=properties.getProperty("qiantai");
			IP2=properties.getProperty("chuancai");
			PORT=properties.getProperty("PORT");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//DepartMentMap.putDepartment("PORT", "9100");
		//IP1=DepartMentMap.getIP("前台");//前台
		//IP2=DepartMentMap.getIP("传菜");//传菜
		//IP3=DepartMentMap.getIP("IP3");
		//PORT=DepartMentMap.getIP("PORT");
		PortInt=Integer.parseInt(PORT);
		
	}
	public ArrayList<ResultObject> printFendan(){
		//String result="";
		ArrayList<ResultObject> resultObjectArrayList=new ArrayList<ResultObject>();
		//String titlePrt="花满棠.华侨城";
		String titlePrt="";
		String taskidPrt="单号："+taskId;
		String orderTimePrt="时间："+orderTime;
		if(isprint.equals("1")||isprint==null){
			//titlePrt="花满棠.华侨城";
			titlePrt=taskidPrt;
		}else{
			//titlePrt="花满棠.华侨城(再次打印)";
			titlePrt=taskidPrt+"(再次打印)";
		}
		//String tableIdPrt="桌位号："+tableId;
		//String peopleCountPrt="人数："+peopleCount;
		//String realNamePrt="客户："+realName;
		//String mobilePrt="电话："+mobile;
	//version1 每道菜都单独打一张单子	---------------------------------------------------
		for(int i=0;i<taskObject.dishids.size();i++){
			//String depart_result="";
			ResultObject depart_resultObject=new ResultObject();
			TaskObject.Dishids dishids=taskObject.dishids.get(i);
			String depart_fk="";
			depart_fk=dishids.depart_fk;//部门ip
			if(depart_fk==null||depart_fk.length()<=0){
				depart_resultObject.setIP(depart_fk);
				depart_resultObject.setSuccess(false);
			}

			else{
				Socket socket;
				try {
					//*检查depart_fk是否存在
					socket = new Socket(depart_fk,PortInt);
					OutputStream out=socket.getOutputStream();
					
					
					
					for(int j=0;j<dishids.data.size();j++){
						TaskObject.Dishids.Data dishdata=dishids.data.get(j);
						int nameLenth,menucountLenth,presentpriceLenth,priceLenth;
						    String dishName=dishdata.dishname;
						    nameLenth=(dishName!=null)?dishName.length()*2:0;
						    String menucount=Integer.toString(dishdata.menucount);
						    menucountLenth=(menucount!=null)?menucount.length():0;

					   int lenLeft=48-nameLenth-menucountLenth;
					   
					   out.write(EPSCommand.init_printer());
						out.write(EPSCommand.fontSizeSetBig(2));
						out.write(EPSCommand.alignCenter());
						out.write(titlePrt.getBytes("gb2312"));
						out.write(EPSCommand.nextLine(2));
						out.write(typeStr.getBytes("gb2312"));
						out.write(EPSCommand.fontSizeSetBig(1));
						out.write(EPSCommand.nextLine(1));
						out.write(EPSCommand.alignLeft());
						//out.write(taskidPrt.getBytes("gb2312"));
						out.write(EPSCommand.nextLine(2));
						out.write(orderTimePrt.getBytes("gb2312"));
						out.write(EPSCommand.nextLine(1));
						out.write(line.getBytes("gb2312"));//画线
						out.write(EPSCommand.nextLine(2));
					   
					   out.write(new String("菜品名称："+spaceNumStr(33)+"数量").getBytes("gb2312"));
					   out.write(EPSCommand.nextLine(1));
					   out.write(line.getBytes("gb2312"));
					   out.write(EPSCommand.nextLine(1));
					   if(lenLeft>0){
					   out.write(new String(dishName+spaceNumStr(lenLeft)+menucount).getBytes("gb2312"));
					   }else{
						   out.write(new String(dishName+" "+menucount).getBytes("gb2312"));  
					   }
					   out.write(EPSCommand.nextLine(3));
					   out.write(EPSCommand.feedPaperCutAll());
					   out.write(EPSCommand.nextLine(3));
					}
					
					out.flush();
					out.close();
		            socket.close();
					depart_resultObject.setIP(depart_fk);
					depart_resultObject.setSuccess(true);
				} catch (UnknownHostException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					depart_resultObject.setIP(depart_fk);
					depart_resultObject.setSuccess(false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					depart_resultObject.setIP(depart_fk);
					depart_resultObject.setSuccess(false);
				}	
			}

			 resultObjectArrayList.add(depart_resultObject);
			
		}
		//----------------------------------------------------------------------------
		//version2同一出品部的菜打在一张单子上-----------------------------------------
//		for(int i=0;i<taskObject.dishids.size();i++){
//			ResultObject depart_resultObject=new ResultObject();
//			TaskObject.Dishids dishids=taskObject.dishids.get(i);
//			String depart_fk="";
//			depart_fk=dishids.depart_fk;//部门ip
//			if(depart_fk==null||depart_fk.length()<=0){
//				depart_resultObject.setIP(depart_fk);
//				depart_resultObject.setSuccess(false);
//			}
//
//			else{
//				Socket socket;
//				try {
//					//*检查depart_fk是否存在
//					socket = new Socket(depart_fk,PortInt);
//					OutputStream out=socket.getOutputStream();
//
//					   out.write(EPSCommand.init_printer());
//						out.write(EPSCommand.fontSizeSetBig(2));
//						out.write(EPSCommand.alignCenter());
//						out.write(titlePrt.getBytes("gb2312"));
//						out.write(EPSCommand.nextLine(2));
//						out.write(typeStr.getBytes("gb2312"));
//						out.write(EPSCommand.fontSizeSetBig(1));
//						out.write(EPSCommand.nextLine(1));
//						out.write(EPSCommand.alignLeft());
//						//out.write(taskidPrt.getBytes("gb2312"));
//						out.write(EPSCommand.nextLine(2));
//						out.write(orderTimePrt.getBytes("gb2312"));
//						out.write(EPSCommand.nextLine(1));
//						out.write(line.getBytes("gb2312"));//画线
//						out.write(EPSCommand.nextLine(2));
//					   
//					   out.write(new String("菜品名称："+spaceNumStr(33)+"数量").getBytes("gb2312"));
//					   out.write(EPSCommand.nextLine(1));
//					   out.write(line.getBytes("gb2312"));
//					   out.write(EPSCommand.nextLine(1));
//					
//					
//					for(int j=0;j<dishids.data.size();j++){
//						TaskObject.Dishids.Data dishdata=dishids.data.get(j);
//						int nameLenth,menucountLenth,presentpriceLenth,priceLenth;
//						    String dishName=dishdata.dishname;
//						    nameLenth=(dishName!=null)?dishName.length()*2:0;
//						    String menucount=Integer.toString(dishdata.menucount);
//						    menucountLenth=(menucount!=null)?menucount.length():0;
//
//					   int lenLeft=48-nameLenth-menucountLenth;
//					   if(lenLeft>0){
//					   out.write(new String(dishName+spaceNumStr(lenLeft)+menucount).getBytes("gb2312"));
//					   }else{
//						   out.write(new String(dishName+" "+menucount).getBytes("gb2312"));  
//					   }
//					  // out.write(EPSCommand.nextLine(3));
//					   //out.write(EPSCommand.feedPaperCutAll());
//					   out.write(EPSCommand.nextLine(1));
//					}
//					out.write(EPSCommand.nextLine(3));
//					out.write(EPSCommand.feedPaperCutAll());
//					out.flush();
//					out.close();
//					socket.close();
//					depart_resultObject.setIP(depart_fk);
//					depart_resultObject.setSuccess(true);
//					
//				} catch (UnknownHostException e) {
//					
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					depart_resultObject.setIP(depart_fk);
//					depart_resultObject.setSuccess(false);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					depart_resultObject.setIP(depart_fk);
//					depart_resultObject.setSuccess(false);
//				}
//			
//			}
//
//			 resultObjectArrayList.add(depart_resultObject);
//			
//		}
		//---------------------------------------------------------------------------
     return resultObjectArrayList;
	}
	public String spaceNumStr(int num){
		String space=new String();
		for(int i=0;i<num;i++){
			space=space+" ";
		}
		return space;
	}
}
