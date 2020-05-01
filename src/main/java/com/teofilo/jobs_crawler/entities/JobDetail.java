package com.teofilo.jobs_crawler.entities;

public class JobDetail {
	private String city;
	private float salary;
	private String role;
	
	
	public JobDetail() {}
	
	public JobDetail(String city, float salary, String role) {
		super();
		this.city = city;
		this.salary = salary;
		this.role = role;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public float getSalary() {
		return salary;
	}
	public void setSalary(float salary) {
		this.salary = salary;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
}
