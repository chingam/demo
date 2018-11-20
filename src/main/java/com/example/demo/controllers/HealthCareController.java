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
import com.example.demo.model.HealthCare;
import com.example.demo.repo.HealthCareRepository;

@Controller
@RequestMapping("/setup/healthcare")
public class HealthCareController implements AbstractController{

	@Autowired private HealthCareRepository repository;
	
	@ModelAttribute("events")
	public List<Button> getControleEvents() {
		return getControls();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getScreen(final ModelMap model, final Locale locale) {
		model.addAttribute("formCode", "T91008");
		model.addAttribute("formName", "Health Care Facility Screen");
		model.addAttribute("controller", "healthcare");
		model.addAttribute("bean", new HealthCare());
		return "setup/template";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> save(@Valid HealthCare healthCare, final ModelMap model){
		Map<String, Object> response = new HashMap<>();
		if(StringUtils.isEmpty(healthCare.getHealthCareId()) && repository.findByHealthCareCodeAndPatientNo(healthCare.getHealthCareCode(), healthCare.getPatientNo()) != null) {
			response.put("status", "error");
			response.put("message", healthCare.getHealthCareCode() + " already exist with patient" + healthCare.getPatientNo());
			return ResponseEntity.ok(response);
		}
		
		HealthCare zone = repository.save(healthCare);
		if (zone == null) throw new BadRequestException("Health care code could not save");
		response.put("status", "success");
		response.put("message", healthCare.getArchive() == 1 ? healthCare.getHealthCareCode() + " Successfully deleted" : healthCare.getHealthCareCode() + " Successfully save");
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String findAll(final ModelMap model) {
		List<HealthCare> list = new ArrayList<>();
		repository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		model.addAttribute("zones", list);
		return "healthcare/table";
	}
	
	@RequestMapping(value = "/find/{code}", method = RequestMethod.GET)
	public String findByCode(@PathVariable String code, final ModelMap model) {
		model.addAttribute("zones", repository.findByHealthCareCodeContaining(code).stream().filter(a -> a.getArchive() == 0).collect(Collectors.toList()));
		return "healthcare/table";
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(@RequestParam String code, final ModelMap model) {
		model.addAttribute("bean", repository.findById(code));
		return "healthcare/healthcare :: fdiv";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> delete(@Valid HealthCare healthCare, final ModelMap model){
		healthCare.setArchive(1);
		return save(healthCare, model);
	}
	
}
