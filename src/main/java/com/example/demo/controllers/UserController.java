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
import com.example.demo.model.T01007;
import com.example.demo.model.T01009;
import com.example.demo.model.T02016;
import com.example.demo.model.T02064;
import com.example.demo.model.T02065;
import com.example.demo.repo.T01007Repository;
import com.example.demo.repo.T01009Repository;
import com.example.demo.repo.T02016Repository;
import com.example.demo.repo.T02064Repository;
import com.example.demo.repo.T02065Repository;

@Controller
@RequestMapping("/setup/user")
public class UserController implements AbstractController{

	@Autowired private T01009Repository repository;
	@Autowired private T02064Repository zoneRepository;
	@Autowired private T02065Repository siteRepository;
	@Autowired private T02016Repository jobRepository;
	@Autowired private T01007Repository roleRepository;
	
	@ModelAttribute("events")
	public List<Button> getControleEvents() {
		return getControls();
	}
	
	@ModelAttribute("zones")
	public List<T02064> getData() {
		List<T02064> list = new ArrayList<>();
		zoneRepository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		return list;
	}
	
	@ModelAttribute("sites")
	public List<T02065> getSites() {
		List<T02065> list = new ArrayList<>();
		siteRepository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		return list;
	}
	
	@ModelAttribute("jobs")
	public List<T02016> getJobs() {
		List<T02016> list = new ArrayList<>();
		jobRepository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		return list;
	}
	
	@ModelAttribute("roles")
	public List<T01007> getSiteStatus() {
		List<T01007> list = new ArrayList<>();
		roleRepository.findAll().forEach(a -> {
			list.add(a);
		});
		return list;
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getScreen(final ModelMap model, final Locale locale) {
		model.addAttribute("formCode", "T01009");
		model.addAttribute("formName", "User Registration");
		model.addAttribute("controller", "user");
		model.addAttribute("bean", new T01009());
		return "setup/template";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> save(@Valid T01009 t01009, final ModelMap model){
		Map<String, Object> response = new HashMap<>();
		if(StringUtils.isEmpty(t01009.getEmployeeCode()) && repository.findByEmployeeCode(t01009.getRoleCode()) != null) {
			response.put("status", "error");
			response.put("message", t01009.getEmployeeCode() + " already exist");
			return ResponseEntity.ok(response);
		}
		
		T01009 zone = repository.save(t01009);
		if (zone == null) throw new BadRequestException("User could not save");
		response.put("status", "success");
		response.put("message", t01009.getArchive() == 1 ? t01009.getRoleCode() + " have been deleted" : t01009.getRoleCode() + " have been saved");
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String findAll(final ModelMap model) {
		List<T01009> list = new ArrayList<>();
		repository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		model.addAttribute("users", list);
		return "user/table";
	}
	
	@RequestMapping(value = "/find/{code}", method = RequestMethod.GET)
	public String findByCode(@PathVariable String code, final ModelMap model) {
		model.addAttribute("users", repository.findByEmployeeCodeContaining(code).stream().filter(a -> a.getArchive() == 0).collect(Collectors.toList()));
		return "user/table";
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(@RequestParam String code, final ModelMap model) {
		model.addAttribute("bean", repository.findById(code));
		return "user/user :: fdiv";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> delete(@Valid T01009 t01009, final ModelMap model){
		t01009.setArchive(1);
		return save(t01009, model);
	}
	
}
