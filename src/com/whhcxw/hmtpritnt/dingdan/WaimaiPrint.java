package com.whhcxw.hmtpritnt.dingdan;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.google.gson.Gson;
import com.whhcxw.hmtpritnt.dingdan.exact.ChuanCaiDan;
import com.whhcxw.hmtpritnt.dingdan.exact.Fendan;
import com.whhcxw.hmtpritnt.dingdan.exact.PeisongDan;
import com.whhcxw.hmtpritnt.dingdan.exact.Zongdan;
//import com.whhcxw.hmtsetting.DepartMentMap;
//import com.whhcxw.hmtsetting.Settings;
import com.whhcxw.object.ResultObject;
import com.whhcxw.object.TaskObject;

public class WaimaiPrint {
	private String taskId;//订单号
	private String orderTime;//下单时间
	private String tableId;//桌位编号
	private String peopleCount;//人数
	private String realName;//就餐联系人
	private String mobile;//联系电话
	private String total;//总计
	private String payMethod;//支付方式
	private TaskObject taskObject;
	//private Settings settings;
	private String IP1;//前台
	private String IP2;//传菜
	private String IP3;//厨房
	private String PORT;
	//private String IP,Port;
	//private int PortInt;
	
	private String line="------------------------------------------------";

	private final String SUCCESS="打印成功";
	private final String FAIL="打印失败";
	
public WaimaiPrint(TaskObject task){
	taskObject=task;
//	settings=new Settings();
//	IP1=settings.IP1;
//	IP2=settings.IP2;
//	IP3=settings.IP3;
//	PORT=settings.PORT;
	
//	DepartMentMap.putDepartment("IP1", "192.168.1.193");
//	DepartMentMap.putDepartment("IP2", "192.168.1.194");
//	DepartMentMap.putDepartment("IP1", "192.168.1.196");
	//DepartMentMap.initMap();
	//DepartMentMap.putDepartment("PORT", "9100");
	//IP1=DepartMentMap.getIP("前台");//前台
	//IP2=DepartMentMap.getIP("传菜");//传菜
	//IP3=DepartMentMap.getIP("IP3");
	//PORT=DepartMentMap.getIP("PORT");
	String filePath=WaimaiPrint.class.getClassLoader().getResource("Settings.properties").getPath();
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
	//taskId=task.taskid;
	//orderTime=task.ordertime;
	//tableId=task.tableid;
	//peopleCount=task.peoplecount;
	//realName=task.realName;
	//mobile=task.mobile;
	//total=task.total;
	//payMethod=task.paymethod;
	
	
	
	 
	//this.IP=IP;
	//this.Port=Port;
	//PortInt=Integer.parseInt(Port);
}

public String printWaimai(String mType){//mType用来区分外卖和食杂
	String result="";
	ArrayList<ResultObject> resultObject_ArrayList=new ArrayList<ResultObject>();
	ResultObject p_s_result=null;
	ResultObject c_c_result=null;
	ArrayList<ResultObject> f_d_result=null;
	
	PeisongDan peisongdan=new PeisongDan(taskObject,IP1,PORT,"外卖");
	if(mType.equals("食杂")){
		peisongdan=new PeisongDan(taskObject,IP1,PORT,"食杂");
	}
	p_s_result=peisongdan.printPeisongDan();
	ChuanCaiDan chuancaidan=new ChuanCaiDan(taskObject,IP2,PORT,"外卖传菜单");
	if(mType.equals("食杂")){
		chuancaidan=new ChuanCaiDan(taskObject,IP2,PORT,"食杂传菜单");
	}
	c_c_result=chuancaidan.printChuanCaiDan();
	Fendan fendan=new Fendan(taskObject,"外卖分单");
	if(mType.equals("食杂")){
		fendan=new Fendan(taskObject,"食杂分单");
	}
	f_d_result=fendan.printFendan();
	resultObject_ArrayList=f_d_result;
	resultObject_ArrayList.add(p_s_result);
	resultObject_ArrayList.add(c_c_result);
	Gson gson=new Gson();
	result=gson.toJson(resultObject_ArrayList);
	//result="总单："+p_s_result+"传菜单："+c_c_result+"分单："+f_d_result;
	return result;
}

}
