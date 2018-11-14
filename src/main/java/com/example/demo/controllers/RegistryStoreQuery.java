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

import com.example.demo.model.Button;
import com.example.demo.model.Button.Buttons;
import com.example.demo.model.T01008;
import com.example.demo.model.T130961;
import com.example.demo.repo.T130961Repository;
import com.example.demo.service.RegistryQueryService;

@Controller
@RequestMapping("/query/registryquery")
public class RegistryStoreQuery {

	// ExtrinsicObject, RegistryPackage, and Association objects
	
	/*
	 * patient Id: 9fe89bd266ef460^^^&1.3.6.1.4.1.21367.2005.13.20.1000&ISO

Title: AllergyDoc

creation time: 20180803014646

status: urn:oasis:names:tc:ebxml-regrep:StatusType:Approved

creation time: 20180803014646

Home CommunityId:

repository UniqueId: 1.3.6.1.4.1.21367.2011.2.3.248

Document UniqueId: 1.3.6.1.4.1.12559.11.1.2.2.1.1.3.125974

mimeType: text/xml

URI:
	 */
	
	@Autowired
	private RegistryQueryService queryService;
	
	@Autowired
	private T130961Repository repository;
	
	@ModelAttribute("events")
	public List<Button> getControleEvents() {
		return Arrays.asList(new Button(Buttons.NEW, false, true), 
				new Button(Buttons.CLEAR, false, true), 
				new Button(Buttons.NEXT_QUERY, true, true));
	}
	
	@ModelAttribute("configs")
	public List<T130961> getConfiguration() {
		List<T130961> list = new ArrayList<>();
		repository.findAll().forEach(a -> {
			if (a.getArchive() == 0 ) {
				list.add(a);
			}
		});
		return list;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String getScreen(final ModelMap model, final Locale locale) {
		model.addAttribute("formCode", "T130960");
		model.addAttribute("formName", "Registry Store Query");
		model.addAttribute("controller", "registryquery");
		model.addAttribute("bean", new T01008());
		return "query/template";
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public String findAll(final ModelMap model) {
		List<T01008> list = new ArrayList<>();
		String patientNo = "", returnType = "";
		model.addAttribute("list", queryService.getPatientMetaData(patientNo, returnType));
		return "registryquery/table";
	}
}
