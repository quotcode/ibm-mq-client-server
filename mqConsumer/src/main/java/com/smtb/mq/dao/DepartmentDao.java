package com.smtb.mq.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smtb.mq.entities.Department;

@Repository
public interface DepartmentDao extends JpaRepository<Department, String>{

}
