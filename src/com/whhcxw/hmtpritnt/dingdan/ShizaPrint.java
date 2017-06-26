package com.whhcxw.hmtpritnt.dingdan;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.google.gson.Gson;
import com.whhcxw.hmtpritnt.dingdan.exact.PeisongDan;
import com.whhcxw.object.ResultObject;
import com.whhcxw.object.TaskObject;

public class ShizaPrint {
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
	
	public ShizaPrint(TaskObject task){
		taskObject=task;
		String filePath=ShizaPrint.class.getClassLoader().getResource("Settings.properties").getPath();
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
	public String printShiza(){
		String result="";
		ArrayList<ResultObject> resultObject_ArrayList=new ArrayList<ResultObject>();
		ResultObject p_s_result=null;
		ResultObject c_c_result=null;
		ArrayList<ResultObject> f_d_result=new ArrayList<ResultObject>();
		ResultObject fd_resultObject=new ResultObject();
		fd_resultObject.setIP("");
		fd_resultObject.setSuccess(true);	
		f_d_result.add(fd_resultObject);
		
		c_c_result=new ResultObject();
		c_c_result.setIP("");
		c_c_result.setSuccess(true);
		
		PeisongDan peisongdan=new PeisongDan(taskObject,IP1,PORT,"食杂");
		p_s_result=peisongdan.printPeisongDan();
		resultObject_ArrayList=f_d_result;
		resultObject_ArrayList.add(p_s_result);
		resultObject_ArrayList.add(c_c_result);
		Gson gson=new Gson();
		result=gson.toJson(resultObject_ArrayList);
		return result;
	}
	
}
