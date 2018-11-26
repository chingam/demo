package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Association;

@Repository
public interface AssociationRepository extends CrudRepository<Association, String>{
	Association findByPatientNo(String patientNo);
	List<Association> findAllByPatientNo(String patientNo);
	List<Association> findByPatientNoContaining(String patientNo);
}
