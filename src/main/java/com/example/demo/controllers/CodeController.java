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
import com.example.demo.repo.CodeRepository;

@Controller
@RequestMapping("/transaction/code")
public class CodeController implements AbstractController{

	@Autowired private CodeRepository repository;
	
	@ModelAttribute("events")
	public List<Button> getControleEvents() {
		return getControls();
	}
	
	@GetMapping
	public String getScreen(final ModelMap model, final Locale locale) {
		model.addAttribute("formCode", "T91004");
		model.addAttribute("formName", "Code Setup");
		model.addAttribute("controller", "code");
		model.addAttribute("bean", new Code());
		return "transaction/template";
	}
	
	@PostMapping
	@ResponseBody
	public ResponseEntity<Object> save(@Valid Code code, final ModelMap model){
		Map<String, Object> response = new HashMap<>();
		if(StringUtils.isEmpty(code.getId()) && repository.findByCode(code.getCode()) != null) {
			response.put("status", "error");
			response.put("message", code.getCode() + " already exist");
			return ResponseEntity.ok(response);
		}
		
		Code zone = repository.save(code);
		if (zone == null) throw new BadRequestException("Code could not save");
		response.put("status", "success");
		response.put("message", code.getArchive() == 1 ? code.getCode() + " have been deleted" : code.getCode() + " have been saved");
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(path = "/all")
	public String findAll(final ModelMap model) {
		List<Code> list = new ArrayList<>();
		repository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		model.addAttribute("codes", list);
		return "code/table";
	}
	
	@GetMapping(path = "/find/{code}")
	public String findByCode(@PathVariable String code, final ModelMap model) {
		if ("all".equals(code)) {
			List<Code> list = new ArrayList<>();
			repository.findAll().forEach(a -> {
				if (a.getArchive() == 0 ) {
					list.add(a);
				}
			});
			model.addAttribute("codes", list);
			return "code/table";
			
		}
		model.addAttribute("codes", repository.findByCodeContaining(code).stream().filter(a -> a.getArchive() == 0).collect(Collectors.toList()));
		return "code/table";
	}
	
	@GetMapping(path = "/find")
	public String find(@RequestParam String code, final ModelMap model) {
		model.addAttribute("bean", repository.findById(code));
		return "code/code :: fdiv";
	}
	
	@PostMapping(path = "/delete")
	@ResponseBody
	public ResponseEntity<Object> delete(@Valid Code code, final ModelMap model){
		code.setArchive(1);
		return save(code, model);
	}
	
}
