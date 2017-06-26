package com.whhcxw.object;

public class ResultObject {
private String IP="";
private boolean success=false;
public void setIP(String IP){
	this.IP=IP;
}
public void setSuccess(boolean success){
	this.success=success;
}
public String getIP(){
	return IP;
}
public boolean getSuccess(){
	return success;
}
}
