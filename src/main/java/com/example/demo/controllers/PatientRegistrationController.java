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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.Button;
import com.example.demo.model.T03001;
import com.example.demo.repo.T03001Repository;

@Controller
@RequestMapping("/transaction/patientregistration")
public class PatientRegistrationController implements AbstractController{

	@Autowired private T03001Repository repository;
	
	@ModelAttribute("events")
	public List<Button> getControleEvents() {
		return getControls();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getScreen(final ModelMap model, final Locale locale) {
		model.addAttribute("formCode", "T03001");
		model.addAttribute("formName", "Patient Registration");
		model.addAttribute("controller", "patientregistration");
		model.addAttribute("bean", new T03001());
		return "transaction/template";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> save(@Valid T03001 t03001, final ModelMap model){
		Map<String, Object> response = new HashMap<>();
		if(StringUtils.isEmpty(t03001.getPatientNo()) && repository.findByFirstNameNativeAndFamilyNameNative(t03001.getFirstNameNative(), t03001.getFamilyNameNative()) != null) {
			response.put("status", "error");
			response.put("message", t03001.getFirstNameNative() + " already exist");
			return ResponseEntity.ok(response);
		}
		
		T03001 zone = repository.save(t03001);
		if (zone == null) throw new BadRequestException("Zone could not save");
		response.put("status", "success");
		response.put("message", t03001.getArchive() == 1 ? t03001.getFirstNameNative() + " have been deleted" : t03001.getFirstNameNative() + " have been saved");
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String findAll(final ModelMap model) {
		List<T03001> list = new ArrayList<>();
		repository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		model.addAttribute("zones", list);
		return "patientregistration/table";
	}
	
	@RequestMapping(value = "/find/{code}", method = RequestMethod.GET)
	public String findByCode(@PathVariable String code, final ModelMap model) {
		model.addAttribute("zones", repository.findByFirstNameNativeContaining(code).stream().filter(a -> a.getArchive() == 0).collect(Collectors.toList()));
		return "patientregistration/table";
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(@RequestParam String code, final ModelMap model) {
		model.addAttribute("bean", repository.findById(code));
		return "patientregistration/patientregistration :: fdiv";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> delete(@Valid T03001 t03001, final ModelMap model){
		t03001.setArchive(1);
		return save(t03001, model);
	}
	
}
