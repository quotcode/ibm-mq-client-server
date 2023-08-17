package com.smtb.mqmessageprocessor.entities;

import jakarta.persistence.*;

@Entity
@Table(name="department")
public class Department {
    @Id
    @Column(name = "dept_id")
    private int deptId;

    @Column(name="dept_name")
    private String departmentName;

    public Department(){}
    public Department(int deptId, String departmentName) {
        this.deptId = deptId;
        this.departmentName = departmentName;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "Department{" +
                "deptId=" + deptId +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
