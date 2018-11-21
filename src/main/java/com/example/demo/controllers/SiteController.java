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
import com.example.demo.model.T02017;
import com.example.demo.model.T02064;
import com.example.demo.model.T02065;
import com.example.demo.repo.T02017Repository;
import com.example.demo.repo.T02064Repository;
import com.example.demo.repo.T02065Repository;

@Controller
@RequestMapping("/setup/site")
public class SiteController implements AbstractController{

	@Autowired private T02064Repository zoneRepository;
	@Autowired private T02065Repository repository;
	@Autowired private T02017Repository statusRepository;
	
	@ModelAttribute("events")
	public List<Button> getControleEvents() {
		return getControls();
	}
	
	@ModelAttribute("status")
	public List<T02017> getSiteStatus() {
		List<T02017> list = new ArrayList<>();
		statusRepository.findAll().forEach(a -> {
			list.add(a);
		});
		return list;
		
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

	@RequestMapping(method = RequestMethod.GET)
	public String getScreen(final ModelMap model, final Locale locale) {
		model.addAttribute("formCode", "T02065");
		model.addAttribute("formName", "Site Registration");
		model.addAttribute("controller", "site");
		model.addAttribute("bean", new T02065());
		return "setup/template";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> save(@Valid T02065 t02065, final ModelMap model){
		Map<String, Object> response = new HashMap<>();
		if(StringUtils.isEmpty(t02065.getSiteId()) && repository.findBySiteCode(t02065.getSiteCode()) != null) {
			response.put("status", "error");
			response.put("message", t02065.getSiteCode() + " already exist");
			return ResponseEntity.ok(response);
		}
		
		T02065 zone = repository.save(t02065);
		if (zone == null) throw new BadRequestException("Zone could not save");
		response.put("status", "success");
		response.put("message", t02065.getArchive() == 1 ? t02065.getSiteCode() + " have been deleted" : t02065.getSiteCode() + " have been saved");
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String findAll(final ModelMap model) {
		List<T02065> list = new ArrayList<>();
		repository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		model.addAttribute("sites", list);
		return "site/table";
	}
	
	@RequestMapping(value = "/find/{code}", method = RequestMethod.GET)
	public String findByCode(@PathVariable String code, final ModelMap model) {
		model.addAttribute("sites", repository.findBySiteCodeContaining(code).stream().filter(a -> a.getArchive() == 0).collect(Collectors.toList()));
		return "site/table";
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(@RequestParam String code, final ModelMap model) {
		model.addAttribute("bean", repository.findById(code));
		return "site/site :: fdiv";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> delete(@Valid T02065 t02065, final ModelMap model){
		t02065.setArchive(1);
		return save(t02065, model);
	}

}
