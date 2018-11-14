package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.T130961;

@Repository
public interface T130961Repository extends CrudRepository<T130961, String>{

	T130961 findByPatnerName(String patnerName);
	List<T130961> findByPatnerNameContaining(String patnerName);
}
