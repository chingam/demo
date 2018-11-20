package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.HealthCare;

@Repository
public interface HealthCareRepository extends CrudRepository<HealthCare, String>{
	HealthCare findByHealthCareCodeAndPatientNo(String healthCareCode, String patientNo);
	List<HealthCare> findByHealthCareCodeContaining(String healthCareCode);
}
