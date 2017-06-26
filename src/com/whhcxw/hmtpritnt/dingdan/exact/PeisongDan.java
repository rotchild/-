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
	//��ʳ
	private String isprint;//��ӡ����
	private String taskId;//������
	private String orderTime;//�µ�ʱ��
	//private String tableId;//��λ���
	private String peopleCount;//����
	private String realName;//�Ͳ���ϵ��
	private String mobile;//��ϵ�绰
	private String total;//�ܼ�
	private String payMethod;//֧����ʽ
	public String address;//��ַ
	//public String sendtime;//�ʹ�ʱ��
	public String diningtime;//����ʱ��
	private TaskObject taskObject;
	private String IP,Port;
	private int PortInt;

	private String line="------------------------------------------------";

	private final String SUCCESS="��ӡ�ɹ�";
	private final String FAIL="��ӡʧ��";
	private String mType;//������ʳ��
	public ResultObject resultObject=new ResultObject();
	public PeisongDan(TaskObject task,String IP,String Port,String mType){//mTypeΪ������ʳ��
		
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
		String taskidPrt="���ţ�"+taskId;
		if(isprint.equals("1")||isprint==null){
			titlePrt=taskidPrt;
		}else{
			titlePrt=taskidPrt+"(�ٴδ�ӡ)";
		}
		
		String orderTimePrt="�µ�ʱ�䣺"+orderTime;
		String sendtimePrt="����ʱ�䣺"+diningtime;
		String addressPrt="���͵�ַ��"+address;
		//String tableIdPrt="��λ�ţ�"+tableId;
		String peopleCountPrt="������"+peopleCount;
		String realNamePrt="�ͻ���"+realName;
		String mobilePrt="�绰��"+mobile;
		
		String subtitlePrt="�������͵�";
		if(mType.equals("ʳ��")){
			subtitlePrt="ʳ�����͵�";
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
			out.write(line.getBytes("gb2312"));//����
			out.write(EPSCommand.nextLine(1));
			out.write(new String(realNamePrt+spaceNumStr(18)+mobilePrt).getBytes("gb2312"));
			out.write(EPSCommand.nextLine(2));
			out.write(addressPrt.getBytes("gb2312"));
			out.write(EPSCommand.nextLine(1));
			out.write(peopleCountPrt.getBytes("gb2312"));
			
			//out.write(new String(tableIdPrt+spaceNumStr(18)+peopleCountPrt).getBytes("gb2312"));
			out.write(EPSCommand.nextLine(2));
			out.write(new String("��Ʒ���ƣ�"+spaceNumStr(4)+"����"+spaceNumStr(11)+"����"+spaceNumStr(10)+"С��").getBytes("gb2312"));
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
					   String price=String.valueOf(menucountInt*presentpriceFloat);//С��
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
