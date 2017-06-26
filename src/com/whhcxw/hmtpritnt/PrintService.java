package com.whhcxw.hmtpritnt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.gson.Gson;
import com.whhcxw.hmtpritnt.dingdan.DingweiPrint;
import com.whhcxw.hmtpritnt.dingdan.JiezhanPrint;
import com.whhcxw.hmtpritnt.dingdan.ShizaPrint;
import com.whhcxw.hmtpritnt.dingdan.TangshiPrint;
import com.whhcxw.hmtpritnt.dingdan.WaimaiPrint;
import com.whhcxw.hmtsetting.DepartMentMap;
import com.whhcxw.object.RequestObject;
import com.whhcxw.object.TaskObject;

public class PrintService {
	private  String type,task;
	private TaskObject taskObject;
	private int typeInt;
	private final int Jiezhan=-1;//���˵�
	private final int Dingwei=4;//��λ
	private final int Tangshi=1;//��ʳ
	private final int Waimai=2;//����
	private final int Shiza=3;//ʳ��
	//private Settings settings;
	private String IP1="192.168.1.193",IP2="192.168.1.194",IP3;
	private String PORT="9100";
public  PrintService(RequestObject requestObject){
	type=requestObject.getType();
	typeInt=Integer.parseInt(type);
	task=requestObject.getTask();
	
	
	//settings=new Settings();
//	IP1=settings.IP1;
//	IP2=settings.IP2;
//	IP3=settings.IP3;
//	PORT=settings.PORT;
//	DepartMentMap.putDepartment("IP1", "192.168.1.193");
//	DepartMentMap.putDepartment("IP2", "192.168.1.194");
//	DepartMentMap.putDepartment("IP1", "192.168.1.196");
//	DepartMentMap.initMap();
//	DepartMentMap.putDepartment("PORT", "9100");
//	IP1=DepartMentMap.getIP("ǰ̨");//ǰ̨
//	IP2=DepartMentMap.getIP("����");//����
//	//IP3=DepartMentMap.getIP("IP3");
//	System.out.println("ǰ̨"+IP1);
//	System.out.println("����"+IP2);
//	PORT=DepartMentMap.getIP("PORT");
	
	String filePath=PrintService.class.getClassLoader().getResource("Settings.properties").getPath();
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
}
public String doPrint(){
	String result="";
	Gson gson=new Gson();
	try{
		taskObject=gson.fromJson(task, TaskObject.class);
		System.out.println("taskObject:"+taskObject);
	}catch(Exception e){
		result="jsonerror";
	}
switch(typeInt){
case Jiezhan:
	JiezhanPrint jiezhanPrt=new JiezhanPrint(taskObject,IP1,PORT);
	result=jiezhanPrt.printJieZhan();
	break;
case Dingwei:
	DingweiPrint dingweiPrt=new DingweiPrint(taskObject,IP1,PORT);
	result=dingweiPrt.printDingwei();
	break;
case Tangshi:
	TangshiPrint tangshiPrt=new TangshiPrint(taskObject);
	result=tangshiPrt.printTangshi();
	break;
case Waimai:
	WaimaiPrint waimaiPrt=new WaimaiPrint(taskObject);
	result=waimaiPrt.printWaimai("����");
	break;
case Shiza:
	//WaimaiPrint peisongPrt=new WaimaiPrint(taskObject);
	//result=peisongPrt.printWaimai("ʳ��");
	ShizaPrint shizaPrt=new ShizaPrint(taskObject);
	result=shizaPrt.printShiza();
	break;
default:break;

}
	return result;
}
}
