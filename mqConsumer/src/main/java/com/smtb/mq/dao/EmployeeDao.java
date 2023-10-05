package com.smtb.mq.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smtb.mq.entities.Employee;

public interface EmployeeDao extends JpaRepository<Employee, String>{

}
