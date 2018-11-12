package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.T02064;

@Repository
public interface T02064Repository extends CrudRepository<T02064, String>{
	T02064 findByZoneCode(String zoneCode);
	List<T02064> findByzoneCodeContaining(String zoneCode);
}
