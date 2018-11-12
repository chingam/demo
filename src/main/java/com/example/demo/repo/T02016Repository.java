package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.T02016;

@Repository
public interface T02016Repository extends CrudRepository<T02016, String>{
	T02016 findByTitleNo(String titleNo);
	List<T02016> findByTitleNoContaining(String titleNo);
}
