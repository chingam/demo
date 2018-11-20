package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.PracticeSetting;

@Repository
public interface PracticeSettingRepository extends CrudRepository<PracticeSetting, String>{
	PracticeSetting findByPracticeCodeAndPatientNo(String practiceCode, String patientNo);
	List<PracticeSetting> findByPracticeCodeContaining(String practiceCode);
}
