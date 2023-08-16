package com.smtb.mqmessageprocessor.entities;

import jakarta.persistence.*;

@Entity
public class Employee {
    @Id
    @Column(name = "emp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int empId;

    @Column(name="emp_first_name")
    private String empFirstName;
    @Column(name="emp_last_name")
    private String empLastName;

    // many employee can have same department id
    @ManyToOne
    @JoinColumn(name="dept_id", referencedColumnName = "dept_id")
    private Department department;

    public Employee(){}
    public Employee(int empId, String empFirstName, String empLastName, Department department) {
        this.empId = empId;
        this.empFirstName = empFirstName;
        this.empLastName = empLastName;
        this.department = department;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
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
        return "Employee{" +
                "empId=" + empId +
                ", empFirstName='" + empFirstName + '\'' +
                ", empLastName='" + empLastName + '\'' +
                ", department=" + department +
                '}';
    }
}
