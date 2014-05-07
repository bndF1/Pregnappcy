package com.pregnappcy.app.business;

public class Messages {

	private String msg;
	private int result;

	public Messages(String msg) {
		this.msg = msg;
	}

	public Messages(String msg, int result){
		this.msg=msg;
		this.result = result;
	}
	public String getMsg() {
		return this.msg;
	}
	public void setMsg(String msg){
		this.msg=msg;
	}
	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

}
