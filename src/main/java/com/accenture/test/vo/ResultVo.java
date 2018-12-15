package com.accenture.test.vo;

import java.util.ArrayList;
import java.util.List;

public class ResultVo<T> {
	private List<String> message;
	private boolean isSuccess;
	private T ojb;
	
	public ResultVo() {
		this.message = new ArrayList<>();
        this.isSuccess = true;
	}
	
	public List<String> getMessage() {
		return message;
	}
	
	public void addMessage(String msg) {
		this.message.add(msg);
	}
	
	public void setMessage(List<String> message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public T getOjb() {
		return ojb;
	}
	public void setOjb(T ojb) {
		this.ojb = ojb;
	}
}
