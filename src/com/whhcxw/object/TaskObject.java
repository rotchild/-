package com.whhcxw.object;

import java.util.ArrayList;

public class TaskObject {
public String isprint;//��ӡ����
public String taskid;//������
public String ordertime;//�µ�ʱ��
//��λ
public String environment;//����
public String note;//��ע
public String ordertabletime;//��λʱ��
public String diningtime;//�Ͳ�ʱ��,����ʱ��
//��ʳ
public String tableid;//��λ/��λ��
//����
public String address;//�Ͳ͵�ַ
public String sendtime;//�ʹ�ʱ��

//��ʳ������
public String peoplecount;//�Ͳ�����
public String mobile;//�绰
public String realName;//��ϵ������
//���˵�
public String paymethod;//֧����ʽ
public String totalmoney;//�ܼ�
public String totalcount;//������

//���δ�ӡ
public boolean allBills;//�Ƿ��ӡ�ܵ�
public boolean handBill;//�Ƿ��ӡ���˵�
//�˵�
public ArrayList<Dishids> dishids;//��Ʒ
//public ArrayList<DishidsJ> dishArrIn;//���˵�dishln;
public ArrayList<ArrayList<DishidsJ>> dishArr;
public ArrayList<Service> allservice;//Service��������
public class Dishids{
	public ArrayList<Data> data;//Data��������
	
	public String depart_fk;//����
	public class Data{
		public String menuid;//��Ʒid
		public int menucount;//��Ʒ����
		public String dishname;//��Ʒ����
		public String presentprice;//����
		public float price;//С��(����)
		public String depart_fk;//???
		public String dishurl;//???
	}
	
}
public class DishidsJ{
	public String menuid;//��Ʒid
	public int menucount;//��Ʒ����
	public String dishname;//��Ʒ����
	public String presentprice;//����
	public float price;//С��
	public String depart_fk;//???
	public String dishurl;//???
}
public class Service{
	public int count;//����
	public int id;//����id
	public String servicename;//��������
	public float price;//�����
}

}
