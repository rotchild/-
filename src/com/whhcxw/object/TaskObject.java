package com.whhcxw.object;

import java.util.ArrayList;

public class TaskObject {
public String isprint;//打印次数
public String taskid;//订单号
public String ordertime;//下单时间
//订位
public String environment;//环境
public String note;//备注
public String ordertabletime;//订位时间
public String diningtime;//就餐时间,配送时间
//堂食
public String tableid;//桌位/座位号
//外卖
public String address;//送餐地址
public String sendtime;//送达时间

//堂食、外卖
public String peoplecount;//就餐人数
public String mobile;//电话
public String realName;//联系人姓名
//结账单
public String paymethod;//支付方式
public String totalmoney;//总计
public String totalcount;//总数量

//二次打印
public boolean allBills;//是否打印总单
public boolean handBill;//是否打印结账单
//菜单
public ArrayList<Dishids> dishids;//菜品
//public ArrayList<DishidsJ> dishArrIn;//结账单dishln;
public ArrayList<ArrayList<DishidsJ>> dishArr;
public ArrayList<Service> allservice;//Service对象数组
public class Dishids{
	public ArrayList<Data> data;//Data对象数组
	
	public String depart_fk;//部门
	public class Data{
		public String menuid;//菜品id
		public int menucount;//菜品数量
		public String dishname;//菜品名称
		public String presentprice;//单价
		public float price;//小计(无用)
		public String depart_fk;//???
		public String dishurl;//???
	}
	
}
public class DishidsJ{
	public String menuid;//菜品id
	public int menucount;//菜品数量
	public String dishname;//菜品名称
	public String presentprice;//单价
	public float price;//小计
	public String depart_fk;//???
	public String dishurl;//???
}
public class Service{
	public int count;//数量
	public int id;//服务id
	public String servicename;//服务名称
	public float price;//服务费
}

}
