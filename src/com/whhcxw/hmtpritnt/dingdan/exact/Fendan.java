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

public class Fendan {//��ʳ��������ʳ��
	private String isprint;//��ӡ����
	private String taskId;//������
	private String orderTime;//�µ�ʱ��
	//private String tableId;//��λ���
	//private String peopleCount;//����
	//private String realName;//�Ͳ���ϵ��
	//private String mobile;//��ϵ�绰
	//private String total;//�ܼ�
	//private String payMethod;//֧����ʽ
	private String typeStr;
	
	private TaskObject taskObject;
//	private String IP,Port;
//	private int PortInt;

	private String line="------------------------------------------------";

	private final String SUCCESS="��ӡ�ɹ�";
	private final String FAIL="��ӡʧ��";
	
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
		//IP1=DepartMentMap.getIP("ǰ̨");//ǰ̨
		//IP2=DepartMentMap.getIP("����");//����
		//IP3=DepartMentMap.getIP("IP3");
		//PORT=DepartMentMap.getIP("PORT");
		PortInt=Integer.parseInt(PORT);
		
	}
	public ArrayList<ResultObject> printFendan(){
		//String result="";
		ArrayList<ResultObject> resultObjectArrayList=new ArrayList<ResultObject>();
		//String titlePrt="������.���ȳ�";
		String titlePrt="";
		String taskidPrt="���ţ�"+taskId;
		String orderTimePrt="ʱ�䣺"+orderTime;
		if(isprint.equals("1")||isprint==null){
			//titlePrt="������.���ȳ�";
			titlePrt=taskidPrt;
		}else{
			//titlePrt="������.���ȳ�(�ٴδ�ӡ)";
			titlePrt=taskidPrt+"(�ٴδ�ӡ)";
		}
		//String tableIdPrt="��λ�ţ�"+tableId;
		//String peopleCountPrt="������"+peopleCount;
		//String realNamePrt="�ͻ���"+realName;
		//String mobilePrt="�绰��"+mobile;
	//version1 ÿ���˶�������һ�ŵ���	---------------------------------------------------
		for(int i=0;i<taskObject.dishids.size();i++){
			//String depart_result="";
			ResultObject depart_resultObject=new ResultObject();
			TaskObject.Dishids dishids=taskObject.dishids.get(i);
			String depart_fk="";
			depart_fk=dishids.depart_fk;//����ip
			if(depart_fk==null||depart_fk.length()<=0){
				depart_resultObject.setIP(depart_fk);
				depart_resultObject.setSuccess(false);
			}

			else{
				Socket socket;
				try {
					//*���depart_fk�Ƿ����
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
						out.write(line.getBytes("gb2312"));//����
						out.write(EPSCommand.nextLine(2));
					   
					   out.write(new String("��Ʒ���ƣ�"+spaceNumStr(33)+"����").getBytes("gb2312"));
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
		//version2ͬһ��Ʒ���Ĳ˴���һ�ŵ�����-----------------------------------------
//		for(int i=0;i<taskObject.dishids.size();i++){
//			ResultObject depart_resultObject=new ResultObject();
//			TaskObject.Dishids dishids=taskObject.dishids.get(i);
//			String depart_fk="";
//			depart_fk=dishids.depart_fk;//����ip
//			if(depart_fk==null||depart_fk.length()<=0){
//				depart_resultObject.setIP(depart_fk);
//				depart_resultObject.setSuccess(false);
//			}
//
//			else{
//				Socket socket;
//				try {
//					//*���depart_fk�Ƿ����
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
//						out.write(line.getBytes("gb2312"));//����
//						out.write(EPSCommand.nextLine(2));
//					   
//					   out.write(new String("��Ʒ���ƣ�"+spaceNumStr(33)+"����").getBytes("gb2312"));
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
