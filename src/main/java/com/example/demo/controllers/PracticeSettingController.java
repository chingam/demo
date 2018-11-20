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
import com.example.demo.model.PracticeSetting;
import com.example.demo.model.PracticeSetting;
import com.example.demo.repo.PracticeSettingRepository;

@Controller
@RequestMapping("/setup/practicesetting")
public class PracticeSettingController implements AbstractController{

	@Autowired private PracticeSettingRepository repository;
	
	@ModelAttribute("events")
	public List<Button> getControleEvents() {
		return getControls();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getScreen(final ModelMap model, final Locale locale) {
		model.addAttribute("formCode", "T91007");
		model.addAttribute("formName", "Practice Setting Screen");
		model.addAttribute("controller", "practicesetting");
		model.addAttribute("bean", new PracticeSetting());
		return "setup/template";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> save(@Valid PracticeSetting practiceSetting, final ModelMap model){
		Map<String, Object> response = new HashMap<>();
		if(StringUtils.isEmpty(practiceSetting.getPracticeId()) && repository.findByPracticeCodeAndPatientNo(practiceSetting.getPracticeCode(), practiceSetting.getPatientNo()) != null) {
			response.put("status", "error");
			response.put("message", practiceSetting.getPracticeCode() + " already exist with patient" + practiceSetting.getPatientNo());
			return ResponseEntity.ok(response);
		}
		
		PracticeSetting zone = repository.save(practiceSetting);
		if (zone == null) throw new BadRequestException("Practice could not save");
		response.put("status", "success");
		response.put("message", practiceSetting.getArchive() == 1 ? practiceSetting.getPracticeCode() + " Successfully deleted" : practiceSetting.getPracticeCode() + " Successfully save");
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String findAll(final ModelMap model) {
		List<PracticeSetting> list = new ArrayList<>();
		repository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		model.addAttribute("zones", list);
		return "practicesetting/table";
	}
	
	@RequestMapping(value = "/find/{code}", method = RequestMethod.GET)
	public String findByCode(@PathVariable String code, final ModelMap model) {
		model.addAttribute("zones", repository.findByPracticeCodeContaining(code).stream().filter(a -> a.getArchive() == 0).collect(Collectors.toList()));
		return "practicesetting/table";
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(@RequestParam String code, final ModelMap model) {
		model.addAttribute("bean", repository.findById(code));
		return "practicesetting/practicesetting :: fdiv";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> delete(@Valid PracticeSetting practiceSetting, final ModelMap model){
		practiceSetting.setArchive(1);
		return save(practiceSetting, model);
	}
	
}
