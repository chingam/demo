package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Button;
import com.example.demo.model.Button.Buttons;
import com.example.demo.model.T01007;
import com.example.demo.model.T01008;
import com.example.demo.repo.T01003Repository;
import com.example.demo.repo.T01007Repository;
import com.example.demo.repo.T01008Repository;

@Controller
@RequestMapping("/setup/rolepermission")
public class RolePermissionController implements AbstractController{

	@Autowired private T01008Repository repository;
	@Autowired private T01007Repository roleRepository;
	@Autowired private T01003Repository formRepository;
	
	
	@ModelAttribute("events")
	public List<Button> getControleEvents() {
		return Arrays.asList(new Button(Buttons.NEW, false, true), 
				new Button(Buttons.SAVE, true, true), 
				new Button(Buttons.CLEAR, false, true), 
				new Button(Buttons.NEXT_QUERY, true, true));
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
		model.addAttribute("formCode", "T01008");
		model.addAttribute("formName", "Role permission Registration");
		model.addAttribute("controller", "rolepermission");
		model.addAttribute("bean", new T01008());
		return "setup/template";
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String findAll(@RequestParam String roleCode, @RequestParam String formCode,final ModelMap model) {
		List<T01008> list = new ArrayList<>();
		repository.findByRoleCodeAndFormCodeContaining(roleCode, formCode).forEach(a -> {
			formRepository.findAll().forEach(b -> {
				if (a.getFormCode().equals(b.getFormCode())) {
					a.setLangName1(b.getLangName1());
					a.setLangName2(b.getLangName2());
					list.add(a);
				}
			});
		});
		model.addAttribute("list", list);
		return "rolepermission/table";
	}
	
//	@RequestMapping(method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<Object> save(@Valid T01008 t01008, final ModelMap model){
//		Map<String, Object> response = new HashMap<>();
//		if(StringUtils.isEmpty(t01008.getTitleId()) && repository.findByTitleNo(t01008.getTitleNo()) != null) {
//			response.put("status", "error");
//			response.put("message", t01008.getLangName1() + " already exist");
//			return ResponseEntity.ok(response);
//		}
//		
//		T01008 job = repository.save(t01008);
//		if (job == null) throw new BadRequestException("Job could not save");
//		response.put("status", "success");
//		response.put("message", t01008.getArchive() == 1 ? t01008.getTitleNo() + " Successfully deleted" : t01008.getTitleNo() + " Successfully save");
//		return ResponseEntity.ok(response);
//	}
//	
//	@RequestMapping(value = "/all", method = RequestMethod.GET)
//	public String findAll(final ModelMap model) {
//		List<T01008> list = new ArrayList<>();
//		repository.findAll().forEach(a -> {
//			if (a.getArchive() == 0 ) {
//				list.add(a);
//			}
//		});
//		model.addAttribute("jobs", list);
//		return "job/table";
//	}
//	
//	@RequestMapping(value = "/find/{code}", method = RequestMethod.GET)
//	public String findByCode(@PathVariable String code, final ModelMap model) {
//		model.addAttribute("jobs", repository.findByTitleNoContaining(code).stream().filter(a -> a.getArchive() == 0).collect(Collectors.toList()));
//		return "job/table";
//	}
//	
//	@RequestMapping(value = "/find", method = RequestMethod.GET)
//	public String find(@RequestParam String code, final ModelMap model) {
//		model.addAttribute("bean", repository.findById(code));
//		return "job/job :: fdiv";
//	}
//	
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<Object> delete(@Valid T01008 t01008, final ModelMap model){
//		t01008.setArchive(1);
//		return save(t01008, model);
//	}
	
}
