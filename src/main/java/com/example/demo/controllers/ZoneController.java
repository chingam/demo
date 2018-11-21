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
import com.example.demo.model.T02064;
import com.example.demo.repo.T02064Repository;

@Controller
@RequestMapping("/setup/zone")
public class ZoneController implements AbstractController{

	@Autowired private T02064Repository repository;
	
	@ModelAttribute("events")
	public List<Button> getControleEvents() {
		return getControls();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getScreen(final ModelMap model, final Locale locale) {
		model.addAttribute("formCode", "T02064");
		model.addAttribute("formName", "Zone Registration");
		model.addAttribute("controller", "zone");
		model.addAttribute("bean", new T02064());
		return "setup/template";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> save(@Valid T02064 t02064, final ModelMap model){
		Map<String, Object> response = new HashMap<>();
		if(StringUtils.isEmpty(t02064.getZoneId()) && repository.findByZoneCode(t02064.getZoneCode()) != null) {
			response.put("status", "error");
			response.put("message", t02064.getLangName1() + " already exist");
			return ResponseEntity.ok(response);
		}
		
		T02064 zone = repository.save(t02064);
		if (zone == null) throw new BadRequestException("Zone could not save");
		response.put("status", "success");
		response.put("message", t02064.getArchive() == 1 ? t02064.getZoneCode() + " have been deleted" : t02064.getZoneCode() + " have been saved");
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String findAll(final ModelMap model) {
		List<T02064> list = new ArrayList<>();
		repository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		model.addAttribute("zones", list);
		return "zone/table";
	}
	
	@RequestMapping(value = "/find/{code}", method = RequestMethod.GET)
	public String findByCode(@PathVariable String code, final ModelMap model) {
		model.addAttribute("zones", repository.findByzoneCodeContaining(code).stream().filter(a -> a.getArchive() == 0).collect(Collectors.toList()));
		return "zone/table";
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(@RequestParam String code, final ModelMap model) {
		model.addAttribute("bean", repository.findById(code));
		return "zone/zone :: fdiv";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> delete(@Valid T02064 t02064, final ModelMap model){
		t02064.setArchive(1);
		return save(t02064, model);
	}
	
}
