package com.whhcxw.hmtpritnt.dingdan;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.google.gson.Gson;
import com.whhcxw.hmtapi.AddIP;
import com.whhcxw.hmtpritnt.dingdan.exact.ChuanCaiDan;
import com.whhcxw.hmtpritnt.dingdan.exact.Fendan;
import com.whhcxw.hmtpritnt.dingdan.exact.Zongdan;
//import com.whhcxw.hmtsetting.DepartMentMap;
//import com.whhcxw.hmtsetting.Settings;
import com.whhcxw.object.ResultObject;
import com.whhcxw.object.TaskObject;

public class TangshiPrint {
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
	private String IP1="192.168.1.193";//前台
	private String IP2="192.168.1.194";//传菜
	private String IP3;//厨房
	private String PORT="9100";
	//private String IP,Port;
	//private int PortInt;
	
	private String line="------------------------------------------------";

	//private final String SUCCESS="打印成功";
	//private final String FAIL="打印失败";
	
public TangshiPrint(TaskObject task){
	taskObject=task;
//	settings=new Settings();
//	IP1=settings.IP1;
//	IP2=settings.IP2;
//	IP3=settings.IP3;
//	PORT=settings.PORT;
	
//	DepartMentMap.putDepartment("IP1", "192.168.1.193");
//	DepartMentMap.putDepartment("IP2", "192.168.1.194");
//	DepartMentMap.putDepartment("IP1", "192.168.1.196");
	//DepartMentMap.putDepartment("PORT", "9100");
	//IP1=DepartMentMap.getIP("前台");//前台
	//IP2=DepartMentMap.getIP("传菜");//传菜
	//IP3=DepartMentMap.getIP("IP3");
	//PORT=DepartMentMap.getIP("PORT");
	String filePath=TangshiPrint.class.getClassLoader().getResource("Settings.properties").getPath();
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

public String printTangshi(){
	String result="";
	ArrayList<ResultObject> arrayListResult=new ArrayList<ResultObject>();
	ResultObject z_d_result=null;
	ResultObject c_c_result=null;
	ArrayList<ResultObject> f_d_result=null;
	Zongdan zongdan=new Zongdan(taskObject,IP1,PORT);
	z_d_result=zongdan.printZongdan();
	ChuanCaiDan chuancaidan=new ChuanCaiDan(taskObject,IP2,PORT,"堂食传菜单");
	c_c_result=chuancaidan.printChuanCaiDan();
	Fendan fendan=new Fendan(taskObject,"堂食分单");
	f_d_result=fendan.printFendan();
	arrayListResult=f_d_result;
	arrayListResult.add(z_d_result);
	arrayListResult.add(c_c_result);
	Gson gson=new Gson();
	result=gson.toJson(arrayListResult);
	//result="总单："+z_d_result+"传菜单："+c_c_result+"分单："+f_d_result;
	return result;
}

}
