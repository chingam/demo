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
import com.example.demo.model.T02016;
import com.example.demo.repo.T02016Repository;

@Controller
@RequestMapping("/setup/job")
public class JobController implements AbstractController{

	@Autowired private T02016Repository repository;
	
	@ModelAttribute("events")
	public List<Button> getControleEvents() {
		return getControls();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getScreen(final ModelMap model, final Locale locale) {
		model.addAttribute("formCode", "T02016");
		model.addAttribute("formName", "Job title Registration");
		model.addAttribute("controller", "job");
		model.addAttribute("bean", new T02016());
		return "setup/template";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> save(@Valid T02016 t01007, final ModelMap model){
		Map<String, Object> response = new HashMap<>();
		if(StringUtils.isEmpty(t01007.getTitleId()) && repository.findByTitleNo(t01007.getTitleNo()) != null) {
			response.put("status", "error");
			response.put("message", t01007.getLangName1() + " already exist");
			return ResponseEntity.ok(response);
		}
		
		T02016 job = repository.save(t01007);
		if (job == null) throw new BadRequestException("Job could not save");
		response.put("status", "success");
		response.put("message", t01007.getArchive() == 1 ? t01007.getTitleNo() + " have been deleted" : t01007.getTitleNo() + " have been saved");
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String findAll(final ModelMap model) {
		List<T02016> list = new ArrayList<>();
		repository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		model.addAttribute("jobs", list);
		return "job/table";
	}
	
	@RequestMapping(value = "/find/{code}", method = RequestMethod.GET)
	public String findByCode(@PathVariable String code, final ModelMap model) {
		model.addAttribute("jobs", repository.findByTitleNoContaining(code).stream().filter(a -> a.getArchive() == 0).collect(Collectors.toList()));
		return "job/table";
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(@RequestParam String code, final ModelMap model) {
		model.addAttribute("bean", repository.findById(code));
		return "job/job :: fdiv";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> delete(@Valid T02016 t01007, final ModelMap model){
		t01007.setArchive(1);
		return save(t01007, model);
	}
	
}
