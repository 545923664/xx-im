package com.jzwl.instant.pojo;

import java.util.HashSet;
import java.util.Set;

public class GroupInfo {

	/**
	 * 群id
	 */
	private String gid;

	private String mid;

	private int maxCount;

	private String desc;

	private String createDate;

	private Set<String> member = new HashSet<String>();

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Set<String> getMember() {
		return member;
	}

	public void setMember(Set<String> member) {
		this.member = member;
	}

}
