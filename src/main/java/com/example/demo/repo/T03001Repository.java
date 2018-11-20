package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.T03001;

@Repository
public interface T03001Repository extends CrudRepository<T03001, String>{
	T03001 findByFirstNameNativeAndFamilyNameNative(String firstName, String lastName);
	List<T03001> findByFirstNameNativeContaining(String firstName);
}
