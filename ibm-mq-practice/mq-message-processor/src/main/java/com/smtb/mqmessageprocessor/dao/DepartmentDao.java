package com.smtb.mqmessageprocessor.dao;

import com.smtb.mqmessageprocessor.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentDao extends JpaRepository<Department, Integer> {
    @Query(value = ":insertQuery", nativeQuery = true)
    public void insertRecord(@Param("insertQuery") String insertQuery);
}
