package com.smtb.mqmessageprocessor.dao;

import com.smtb.mqmessageprocessor.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {
    @Query(value = ":insertQuery", nativeQuery = true)
    public void insertRecord(@Param("insertQuery") String insertQuery);
}
