package com.whhcxw.hmtpritnt.dingdan.exact;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.whhcxw.epscommand.EPSCommand;
import com.whhcxw.object.ResultObject;
import com.whhcxw.object.TaskObject;

public class Zongdan {//��ʳ
	private String isprint;//��ӡ����
	private String taskId;//������
	private String orderTime;//�µ�ʱ��
	private String tableId;//��λ���
	private String peopleCount;//����
	private String realName;//�Ͳ���ϵ��
	private String mobile;//��ϵ�绰
	//private String total;//�ܼ�
	//private String payMethod;//֧����ʽ
	private TaskObject taskObject;
	private boolean allBills;
	private String IP,Port;
	private int PortInt;

	private String line="------------------------------------------------";
    public ResultObject resultObject;
	//private final String SUCCESS="��ӡ�ɹ�";
	//private final String FAIL="��ӡʧ��";
	public Zongdan(TaskObject task,String IP,String Port){
		taskObject=task;
		//isprint=task.isprint;
		taskId=task.taskid;
		orderTime=task.ordertime;
		tableId=task.tableid;
		peopleCount=task.peoplecount;
		realName=task.realName;
		mobile=task.mobile;
	    isprint=task.isprint;
		allBills=task.allBills;
		
		this.IP=IP;
		this.Port=Port;
		PortInt=Integer.parseInt(Port);
		
		resultObject=new ResultObject();
	}
	public ResultObject printZongdan(){
		//String titlePrt="������.���ȳ�";
		String titlePrt="���ţ�"+taskId;
		boolean shouldPrint=true;
		if(isprint.equals("1")||isprint==null){
			//titlePrt="������.���ȳ�";
			titlePrt="���ţ�"+taskId;
		}else{
			//titlePrt="������.���ȳ�(�ٴδ�ӡ)";
			titlePrt="���ţ�"+taskId+"(�ٴδ�ӡ)";
			if(allBills==false){
				shouldPrint=false;
			}
		}
		if(shouldPrint==true){
			String taskidPrt="���ţ�"+taskId;
			String orderTimePrt="ʱ�䣺"+orderTime;
			String tableIdPrt="��λ�ţ�"+tableId;
			String peopleCountPrt="������"+peopleCount;
			String realNamePrt="�ͻ���"+realName;
			String mobilePrt="�绰��"+mobile;
			
			Socket socket;
			try {
				socket = new Socket(IP,PortInt);
				OutputStream out=socket.getOutputStream();
				
				out.write(EPSCommand.init_printer());
				out.write(EPSCommand.fontSizeSetBig(2));
				out.write(EPSCommand.alignCenter());
				out.write(titlePrt.getBytes("gb2312"));
				out.write(EPSCommand.nextLine(2));
				out.write("��ʳ�ܵ�".getBytes("gb2312"));
				out.write(EPSCommand.nextLine(1));
				out.write(EPSCommand.fontSizeSetBig(1));
				out.write(EPSCommand.alignLeft());
				//out.write(taskidPrt.getBytes("gb2312"));
				out.write(EPSCommand.nextLine(2));
				out.write(orderTimePrt.getBytes("gb2312"));
				out.write(EPSCommand.nextLine(1));
				out.write(line.getBytes("gb2312"));//����
				out.write(EPSCommand.nextLine(1));
				if(realName!=null||mobile!=null){//��ϵ�˻�绰��Ϊnull
				out.write(new String(realNamePrt+spaceNumStr(20)+mobilePrt).getBytes("gb2312"));
				}
				out.write(EPSCommand.nextLine(2));
				out.write(new String(tableIdPrt+spaceNumStr(16)+peopleCountPrt).getBytes("gb2312"));
				out.write(EPSCommand.nextLine(2));
				out.write(new String("��Ʒ����"+spaceNumStr(4)+"����"+spaceNumStr(10)+"����"+spaceNumStr(10)+"С��").getBytes("gb2312"));
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
						   String menucount=Integer.toString(dishdata.menucount);
						   int menucountInt=Integer.parseInt(menucount);
						   menucountLenth=(menucount!=null)?menucount.length():0;
						   String presentprice=dishdata.presentprice;
						   Float presentpriceFloat=Float.parseFloat(presentprice);
						   presentpriceLenth=(presentprice!=null)?presentprice.length():0;
						   String price=String.valueOf(menucountInt*presentpriceFloat);
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
