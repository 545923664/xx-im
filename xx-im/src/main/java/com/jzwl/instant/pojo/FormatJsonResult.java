package com.jzwl.instant.pojo;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 返回数据
 * 
 */
public class FormatJsonResult {

	// 判断请求是否成功
	//1代表成功
	private int flag = 1;

	private String message = "";

	private List<Object> list;

	private Map<String, String> map;

	// ------客户端对应处理----
	// t=toast | a=alert
	private String ctrl = "t";

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public String getCtrl() {
		return ctrl;
	}

	public void setCtrl(String ctrl) {
		this.ctrl = ctrl;
	}

}
