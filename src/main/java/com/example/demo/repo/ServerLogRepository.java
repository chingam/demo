package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ServerLog;

@Repository
public interface ServerLogRepository extends CrudRepository<ServerLog, String>{
	List<ServerLog> findTop100ByOrderByDateDesc();
}
