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
	private String taskId;//������
	private String orderTime;//�µ�ʱ��
	private String tableId;//��λ���
	private String peopleCount;//����
	private String realName;//�Ͳ���ϵ��
	private String mobile;//��ϵ�绰
	private String total;//�ܼ�
	private String payMethod;//֧����ʽ
	private TaskObject taskObject;
	//private Settings settings;
	private String IP1;//ǰ̨
	private String IP2;//����
	private String IP3;//����
	private String PORT;
	//private String IP,Port;
	//private int PortInt;
	
	private String line="------------------------------------------------";

	private final String SUCCESS="��ӡ�ɹ�";
	private final String FAIL="��ӡʧ��";
	
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
		
		PeisongDan peisongdan=new PeisongDan(taskObject,IP1,PORT,"ʳ��");
		p_s_result=peisongdan.printPeisongDan();
		resultObject_ArrayList=f_d_result;
		resultObject_ArrayList.add(p_s_result);
		resultObject_ArrayList.add(c_c_result);
		Gson gson=new Gson();
		result=gson.toJson(resultObject_ArrayList);
		return result;
	}
	
}
