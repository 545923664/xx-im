package com.jzwl.base.pojo;

import java.util.Date;

/**
 * Pojo接口基类，所有mongodb的POJO都必须集成该类，并给与下列所有的必须项的数据，便于统计，切片管理
 * @author zhang guo yu
 * @version 1.0.0
 * @since 2015-02-01
 * */
public class BasePojo implements java.io.Serializable {

	public long id;//唯一标识
	public String operationType;//操作类型（在MongodbConstant类中进行查找使用或者添加）
	public String businessName;//属于哪个模块（在BusinessConstant类中进行查找使用或者添加）
	public Date createTime;//创建时间
	public String createUserName;//创建人用户名
	public Date lastModifyTime;//最后一次修改时间（添加的时候跟创建时间相同）
	public String updateUserName;//修改人用户名
	public int deleteFlag;//数据状态：1，正常；0，已删除
	/*******************************以上几条，是每个POJO都要有的；以下的，是各个业务需要的******************************************/
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
}
