package com.smtb.mqmessageprocessor.dao;

import com.smtb.mqmessageprocessor.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends JpaRepository<Department, Integer> {

}
