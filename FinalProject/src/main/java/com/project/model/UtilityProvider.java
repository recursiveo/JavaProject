package com.project.model;

public class UtilityProvider {
	
	private String providerName;
	private String type;
	private String username;
	private String utilityAcc;
	public UtilityProvider(String providerName, String type, String username, String utilityAcc) {
		super();
		this.providerName = providerName;
		this.type = type;
		this.username = username;
		this.utilityAcc = utilityAcc;
	}
	public UtilityProvider() {
		super();
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUtilityAcc() {
		return utilityAcc;
	}
	public void setUtilityAcc(String utilityAcc) {
		this.utilityAcc = utilityAcc;
	}
	@Override
	public String toString() {
		return "UtilityProvider [providerName=" + providerName + ", type=" + type + ", username=" + username
				+ ", utilityAcc=" + utilityAcc + "]";
	}
	
	
	 

}
