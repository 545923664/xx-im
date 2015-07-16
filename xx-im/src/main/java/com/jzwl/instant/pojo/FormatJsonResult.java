package com.jzwl.instant.pojo;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 返回数据
 * 
 */
public class FormatJsonResult {

	public FormatJsonResult() {

	}

	public FormatJsonResult(int flag, String message, String ctrl,
			List<Object> list, Map<String, Object> map) {
		
		this.flag = flag;
		this.message=message;
		this.ctrl = ctrl;
		this.list = list;
		this.map = map;

	}

	// 判断请求是否成功
	// 1代表成功
	private int flag = 1;

	private String message = "";

	private List<Object> list;

	private Map<String, Object> map;

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

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public String getCtrl() {
		return ctrl;
	}

	public void setCtrl(String ctrl) {
		this.ctrl = ctrl;
	}

}
