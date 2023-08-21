package com.smtb.mqmessageprocessor.entities;

import jakarta.persistence.*;

@Entity
@Table(name="department")
public class Department {
    @Id
    @Column(name = "dept_id")
    private String deptId;

    @Column(name="dept_name")
    private String departmentName;

    public Department(){}
    public Department(String deptId, String departmentName) {
        this.deptId = deptId;
        this.departmentName = departmentName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
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
