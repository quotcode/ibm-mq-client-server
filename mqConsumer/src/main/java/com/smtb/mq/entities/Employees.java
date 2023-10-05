package com.smtb.mq.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employees {
	@JsonProperty("employee")
	Employee employee;

	public Employees(Employee employee) {
		super();
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	
	
}
