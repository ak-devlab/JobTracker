package com.example.jobtracker.model;

public class ApplicationEntry {
	public long id;
	public String company;
	public String role;
	public String status;
	public Long nextActionAt;
	public String nextAction;
	public String jobUrl;
	public String note;
	
	public String group;
	
	public ApplicationEntry() {}
	
	public ApplicationEntry(long id, String company, String role, 
			String status, long nextActionAt, String nextAction, String jobUrl, String note, String group) {
		this.id = id;
		this.company = company;
		this.role = role;
		this.status = status;
		this.nextActionAt = nextActionAt;
		this.nextAction = nextAction;
		this.jobUrl = jobUrl;
		this.note = note;
		this.group = group;
	}
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}

}
