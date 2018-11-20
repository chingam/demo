package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Code;

@Repository
public interface CodeRepository extends CrudRepository<Code, String>{
	Code findByCode(String code);
	List<Code> findByCodeContaining(String code);
}
