package com.smtb.mqmessageprocessor.dao;

import com.smtb.mqmessageprocessor.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {

}
