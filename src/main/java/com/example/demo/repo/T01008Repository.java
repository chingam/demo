package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.T01008;

@Repository
public interface T01008Repository extends CrudRepository<T01008, String>{
	T01008 findByRoleCode(String roleCode);
	List<T01008> findByRoleCodeAndFormCodeContaining(String roleCode, String formCode);
}
