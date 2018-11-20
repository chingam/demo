package com.example.demo.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.DocumentSet;

@Repository
public interface DocumentSetRepository extends CrudRepository<DocumentSet, String>{
	
}
