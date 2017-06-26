package com.whhcxw.hmtpritnt;

public class EPSCommand {
public static final byte ESC=27;//换码
public static final byte FS=28;//文本分割
public static final byte GS=29;//组分割符
public static final byte DLE=16;//数据连接换码
public static final byte EOT=4;//传输结束
public static final byte ENQ=5;//询问字符
public static final byte SP=32;//空格
public static final byte HT=9;//横向列表
public static final byte LF=10;//打印并换行
public static final byte CR=13;//归位键
public static final byte FF=12;//走纸控制
public static final byte CAN=24;//作废
//------------------------打印机初始化-----------------------
public static byte[] init_printer(){
	byte[] result=new byte[2];
	result[0]=ESC;
	result[1]=64;
	return result;
}
//------------------------换行-----------------------
public static byte[] nextLine(int lineNum){
	byte[] result=new byte[lineNum];
	for(int i=0;i<lineNum;i++){
		result[i]=LF;
	}
	return result;
}

public static byte[] nextLine2(byte lineNum){
	byte[] result=new byte[3];
	result[0]=ESC;
	result[1]=100;
	result[2]=lineNum;
	return result;
}
//------------------------下划线---------------------------
//dotWidth只能为0或1或2?
public static byte[] underline(byte dotWidth){
	byte[] result=new byte[3];
	result[0]=ESC;
	result[1]=45;
	result[2]=dotWidth;
	return result;
}

//-----------------------加粗------------------------------
public static byte[] boldOn(){
	byte[] result=new byte[3];
	result[0]=ESC;
	result[1]=69;
	result[2]=0xF;
	return result;
}
//---------------------取消加粗------------------------------
public static byte[] boldOff(){
	byte[] result=new byte[3]; 
	result[0]=ESC;
	result[1]=69;
	result[2]=0;
	return result;
}
//-----------------对齐方式---------------------------------
public static byte[] alignLeft(){
	byte[] result=new byte[3];
	result[0]=ESC;
	result[1]=97;
	result[2]=0;
	return result;
}
public static byte[] alignCenter(){
	byte[] result=new byte[3];
	result[0]=ESC;
	result[1]=97;
	result[2]=1;
	return result;
}

public static byte[] alignRight(){
	byte[] result=new byte[3];
	result[0]=ESC;
	result[1]=97;
	result[2]=2;
	return result;
	
}
//---------------水平方向向右移动多少列---------------------------
public static byte[] set_HT_Postion(byte col){
	byte[] result=new byte[4];
	result[0]=ESC;
	result[1]=68;
	result[2]=col;
	result[3]=0;
	return result;
  
}
//-----------------字体变大---------------------------------
//字体变大为标准的n倍
public static byte[] fontSizeSetBig(int num){
	byte[] result=new byte[3];
	byte realSize=0;
	result[0]=29;
	result[1]=33;
	if(num<9){
		result[2]= (byte)((num-1)*17);
	}else{
		result[2]=0;
		System.out.println("字体放大错误");
	}
	return result;
}
//----------------字体变小------------------------------???????
public static byte[] fontSizeSetSmall(int num){
	byte[] result=new byte[3];
	result[0]=ESC;
	result[1]=33;
	return result;
}
//---------------切纸-----------------------------------
public static byte[] feedPaperCutAll(){
	byte[] result=new byte[4];
	result[0]=GS;
	result[1]=86;
	result[2]=65;
	result[3]=0;
	return result;
}
public static byte[] feedPaperCutPartial(){
	byte[] result=new byte[4];
	result[0]=GS;
	result[1]=86;
	result[2]=66;
	result[3]=0;
	return result;
}
//---------------走纸------------------------
public static byte[] feedPaper(byte n){
	byte[] result=new byte[3];
	result[0]=ESC;
	result[1]=74;
	result[2]=n;
	return result;
}

public static byte[] testPrint(int paper, int pattern) {  
    paper = (paper == 0 || paper == 1 || paper == 2 || paper == 48  
                || paper == 49 || paper == 50) ? paper : 0;  
    pattern = (pattern == 1 || pattern == 2 || pattern == 3  
                || pattern == 49 || pattern == 50 || pattern == 51)? pattern: 1;  
    return new byte[] { ESC, '(', 'A', 0x02, 0x00, (byte) paper,  
            (byte) pattern };  
}  

}
