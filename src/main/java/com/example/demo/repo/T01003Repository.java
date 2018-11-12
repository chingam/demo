package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.T01003;

@Repository
public interface T01003Repository extends CrudRepository<T01003, String>{
	T01003 findByFormCode(String formCode);
	List<T01003> findByFormCodeContaining(String formCode);
}
