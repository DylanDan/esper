package com.dylan.esper.bo;

public class UserSession {
	
	@Override
	public String toString() {
		return "UserSession [userId=" + userId + ", loginStatus=" + loginStatus
				+ "]";
	}

	private String userId;
	
	private String loginStatus;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

}
