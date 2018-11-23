package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.PatientDocument;

@Repository
public interface PatientDocumentRepository extends CrudRepository<PatientDocument, String>{
	PatientDocument findByPatientNo(String patientNo);
	List<PatientDocument> findAllByPatientNo(String patientNo);
	List<PatientDocument> findByPatientNoContaining(String patientNo);
	List<PatientDocument> findAllByEntryUuid(String entryUuid);// user entry id
	
}
