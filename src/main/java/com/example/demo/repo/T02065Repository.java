package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.T02065;

@Repository
public interface T02065Repository extends CrudRepository<T02065, String>{
	T02065 findBySiteCode(String siteCode);
	List<T02065> findBySiteCodeContaining(String zoneCode);
}
