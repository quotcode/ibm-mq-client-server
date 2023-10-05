package com.smtb.mq.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;

@Entity
@JsonPropertyOrder({ "emp-id", "emp-first-name", "emp-last-name", "dept-id" })
public class Employee {
	@Id
	@Column(name = "emp_id")
	@JsonProperty("emp-id")
	private String empId;
	
	@JsonProperty("emp-first-name")
	@Column(name = "emp_first_name")
	private String empFirstName;
	
	@JsonProperty("emp-last-name")
	@Column(name = "emp_last_name")
	private String empLastName;

	// many employee can have same department id
	@ManyToOne
	@JoinColumn(name = "dept_id", referencedColumnName = "dept_id")
	@JsonProperty("dept-id")
	private Department department;

	public Employee() {
	}

	public Employee(String empId, String empFirstName, String empLastName, Department department) {
		this.empId = empId;
		this.empFirstName = empFirstName;
		this.empLastName = empLastName;
		this.department = department;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpFirstName() {
		return empFirstName;
	}

	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}

	public String getEmpLastName() {
		return empLastName;
	}

	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee{" + "empId=" + empId + ", empFirstName='" + empFirstName + '\'' + ", empLastName='"
				+ empLastName + '\'' + ", department=" + department + '}';
	}
}
