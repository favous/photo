package com.cloudsea.common.entity;

import java.util.Date;

import com.cloudsea.photo.entity.BaseEntity;

public class User extends BaseEntity{
	
	private static final long serialVersionUID = -5976474144608010507L;

	private String userNo; //
	
	private String loginName;//
	
	private String password;//
	
	private String userName;//

	private Date lastModifyTime;//	
	
	private Date expireTime;//	
	
	
	@Override  
	public int hashCode() {  
	    return loginName.hashCode();  
	}  
	@Override  
	public boolean equals(Object obj) {  
	    User user = (User)obj;  
	    return this.loginName.equals(user.getLoginName());  
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	} 
	
	
}
