package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.T01199;

@Repository
public interface T01199Repository extends CrudRepository<T01199, String>{
//	T01199 findByFormCode(String formCode);
	List<T01199> findByRoleCodeAndLinkSeperation(String roleCode, Integer link);
}
