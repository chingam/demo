package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.T01007;

@Repository
public interface T01007Repository extends CrudRepository<T01007, String>{
	T01007 findByRoleCode(String roleCode);
	List<T01007> findByRoleCodeContaining(String roleCode);
}
