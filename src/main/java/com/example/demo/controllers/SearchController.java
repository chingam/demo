package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.T03001;
import com.example.demo.repo.T03001Repository;
import com.example.demo.view.MultiColumnSearch;
import com.example.demo.view.PatientSearchReponse;

@Controller
@RequestMapping("/search")
public class SearchController {
	@Autowired private T03001Repository patientRepository;

	@GetMapping(value="/patient/{hint}")
	public @ResponseBody ResponseEntity<Object> search(@PathVariable String hint, @RequestParam(required=false, defaultValue="false") Boolean exactMatch) {
		if(exactMatch) {
			return ResponseEntity.ok(patientRepository.findById(hint).orElseThrow(() -> new BadRequestException("Not found")));
		}
		PatientSearchReponse response = new PatientSearchReponse();
		response.setValues(populatePatient(hint));
		return ResponseEntity.ok(response);
	}

	private List<MultiColumnSearch> populatePatient(String hint) {
		List<MultiColumnSearch> suggestions = new ArrayList<>();
		for (T03001 list : patientRepository.findByFirstNameNativeContaining(hint)) {
			MultiColumnSearch multiColumn = new MultiColumnSearch();
			String[] columns = new String[6]; // for job search list
			String[] lines = new String[6];
			
			columns[0] = StringUtils.isEmpty(list.getFirstNameNative()) ? "" : list.getFirstNameNative();
			columns[1] = StringUtils.isEmpty(list.getFamilyNameNative()) ? "" : list.getFamilyNameNative();
			columns[2] = StringUtils.isEmpty(list.getAddressLineOne()) ? "" : list.getAddressLineOne();
			columns[3] = StringUtils.isEmpty(list.getPostalCode()) ? "" : list.getPostalCode();
			columns[4] = StringUtils.isEmpty(list.getCity()) ? "" : list.getCity();
			columns[5] = StringUtils.isEmpty(list.getCountry()) ? "" : list.getCountry();
			lines[0] = list.getFirstNameNative();
			multiColumn.setKey(list.getPatientNo());
			multiColumn.setValue(columns);
			multiColumn.setData(lines);
			suggestions.add(multiColumn);
		}
		return suggestions;
	}
	
}
