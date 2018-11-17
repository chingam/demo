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
import com.example.demo.model.T130961;
import com.example.demo.repo.T130961Repository;

@Controller
@RequestMapping("/setup/patnerconfig")
public class PatnerConfigurationController implements AbstractController{

	@Autowired private T130961Repository repository;
	
	@ModelAttribute("events")
	public List<Button> getControleEvents() {
		return getControls();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getScreen(final ModelMap model, final Locale locale) {
		model.addAttribute("formCode", "T130961");
		model.addAttribute("formName", "Job title Registration");
		model.addAttribute("controller", "patnerconfig");
		model.addAttribute("bean", new T130961());
		return "setup/template";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> save(@Valid T130961 t130961, final ModelMap model){
		Map<String, Object> response = new HashMap<>();
		if(StringUtils.isEmpty(t130961.getPatnerId()) && repository.findByPatnerName(t130961.getPatnerName()) != null) {
			response.put("status", "error");
			response.put("message", t130961.getPatnerName() + " already exist");
			return ResponseEntity.ok(response);
		}
		
		T130961 job = repository.save(t130961);
		if (job == null) throw new BadRequestException("Job could not save");
		response.put("status", "success");
		response.put("message", t130961.getArchive() == 1 ? t130961.getPatnerName() + " Successfully deleted" : t130961.getPatnerName() + " Successfully save");
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String findAll(final ModelMap model) {
		List<T130961> list = new ArrayList<>();
		repository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		model.addAttribute("jobs", list);
		return "patnerconfig/table";
	}
	
	@RequestMapping(value = "/find/{code}", method = RequestMethod.GET)
	public String findByCode(@PathVariable String code, final ModelMap model) {
		model.addAttribute("jobs", repository.findByPatnerNameContaining(code).stream().filter(a -> a.getArchive() == 0).collect(Collectors.toList()));
		return "patnerconfig/table";
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(@RequestParam String code, final ModelMap model) {
		model.addAttribute("bean", repository.findById(code));
		return "patnerconfig/patnerconfig :: fdiv";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> delete(@Valid T130961 t130961, final ModelMap model){
		t130961.setArchive(1);
		return save(t130961, model);
	}
	
}
