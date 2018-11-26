package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.Button;
import com.example.demo.model.Code;
import com.example.demo.model.SubmissionSetDocument;
import com.example.demo.repo.CodeRepository;
import com.example.demo.repo.SubmissionSetDocumentRepository;
import com.example.demo.repo.SubmissionSetDocumentRepository;

@Controller
@RequestMapping("/transaction/submissionsetdocument")
public class SubmissionSetDocumentController implements AbstractController{

	@Autowired private SubmissionSetDocumentRepository repository;
	@Autowired private CodeRepository codeRepository;
	
	@ModelAttribute("events")
	public List<Button> getControleEvents() {
		return getControls();
	}
	
	@ModelAttribute("codes")
	public List<Code> getCodes() {
		List<Code> list = new ArrayList<>();
		codeRepository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		return list;
		
	}
	
	@GetMapping
	public String getScreen(final ModelMap model, final Locale locale) {
		model.addAttribute("formCode", "T91005");
		model.addAttribute("formName", "Submission Set Document");
		model.addAttribute("controller", "submissionsetdocument");
		model.addAttribute("bean", new SubmissionSetDocument());
		return "transaction/template";
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<Object> save(@Valid SubmissionSetDocument patientDocument, final ModelMap model){
		Map<String, Object> response = new HashMap<>();
		if(StringUtils.isEmpty(patientDocument.getSubmissionId()) && repository.findByPatientNo(patientDocument.getPatientNo()) != null) {
			response.put("status", "error");
			response.put("message", patientDocument.getPatientNo() + " already exist");
			return ResponseEntity.ok(response);
		}
		
		SubmissionSetDocument zone = repository.save(patientDocument);
		if (zone == null) throw new BadRequestException("SubmissionSet Document could not save");
		response.put("status", "success");
		response.put("message", patientDocument.getArchive() == 1 ? patientDocument.getPatientNo() + " have been deleted" : patientDocument.getPatientNo() + " have been saved");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(path = "/all")
	public String findAll(final ModelMap model) {
		List<SubmissionSetDocument> list = new ArrayList<>();
		repository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		model.addAttribute("zones", list);
		return "documententry/table";
	}
	
	@GetMapping(path = "/find/{code}")
	public String findByCode(@PathVariable String code, final ModelMap model) {
		model.addAttribute("zones", repository.findByPatientNoContaining(code).stream().filter(a -> a.getArchive() == 0).collect(Collectors.toList()));
		return "documententry/table";
	}
	
	@GetMapping(path = "/find")
	public String find(@RequestParam String code, final ModelMap model) {
		model.addAttribute("bean", repository.findById(code));
		return "documententry/documententry :: fdiv";
	}
	
	@PostMapping(path = "/delete")
	@ResponseBody
	public ResponseEntity<Object> delete(@Valid SubmissionSetDocument patientDocument, final ModelMap model){
		patientDocument.setArchive(1);
		return save(patientDocument, model);
	}
	
}