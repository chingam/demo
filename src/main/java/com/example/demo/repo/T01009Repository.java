package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.T01009;

@Repository
public interface T01009Repository extends CrudRepository<T01009, String>{
	T01009 findByEmployeeCode(String employeeCode);
	List<T01009> findByEmployeeCodeContaining(String employeeCode);
}
