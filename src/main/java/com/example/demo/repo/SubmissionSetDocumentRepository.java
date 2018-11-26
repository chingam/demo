package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.SubmissionSetDocument;

@Repository
public interface SubmissionSetDocumentRepository extends CrudRepository<SubmissionSetDocument, String>{
	SubmissionSetDocument findByPatientNo(String patientNo);
	List<SubmissionSetDocument> findByPatientNoContaining(String patientNo);
	List<SubmissionSetDocument> findAllByPatientNo(String patientNo);
}
