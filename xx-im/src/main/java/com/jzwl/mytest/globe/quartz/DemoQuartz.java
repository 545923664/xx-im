package com.jzwl.mytest.globe.quartz;

/**
 * demo定时任务
 * */
public class DemoQuartz {

	private int isOpen = 0;// 监测开关，默认关闭

	//定时器轮询方法
	public void executeInternal() throws Exception {
		if (isOpen == 1) {//定时器开关打开了
			doBusiness();
		}
	}

	/**
	 * 定时器业务逻辑
	 * */
	private void doBusiness(){
		System.out.println("demo quartz start !");
	}

	public int getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}

}
