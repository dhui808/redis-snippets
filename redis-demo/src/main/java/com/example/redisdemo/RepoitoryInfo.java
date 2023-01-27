package com.example.redisdemo;

public class RepoitoryInfo {

	private String gitName;
	private String gitData;
	private boolean cached;
	private String responseTime;
	
	public String getGitName() {
		return gitName;
	}
	public void setGitName(String gitName) {
		this.gitName = gitName;
	}
	public String getGitData() {
		return gitData;
	}
	public void setGitData(String gitData) {
		this.gitData = gitData;
	}
	public boolean isCached() {
		return cached;
	}
	public void setCached(boolean cached) {
		this.cached = cached;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
}