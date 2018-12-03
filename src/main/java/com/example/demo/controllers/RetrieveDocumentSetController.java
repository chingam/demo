package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Button;
import com.example.demo.model.Button.Buttons;
import com.example.demo.model.T130961;
import com.example.demo.repo.T130961Repository;
import com.example.demo.service.RetrieveDocumentSetService;

@Controller
@RequestMapping("/report/retrieve")
public class RetrieveDocumentSetController {

	@Autowired
	private RetrieveDocumentSetService queryService;
	
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
			if (a.getArchive() == 0 && "repository".equals(a.getType())) {
				list.add(a);
			}
		});
		return list;
	}
	
	@GetMapping
	public String getScreen(final ModelMap model, final Locale locale) {
		model.addAttribute("formCode", "T130960");
		model.addAttribute("formName", "Download Report (RDS)");
		model.addAttribute("controller", "retrieve");
		model.addAttribute("bean", null);
		return "report/template";
	}
	
	@GetMapping(path = "/all")
	public String findAll(@RequestParam("documentUniqueId") String documentUniqueId, @RequestParam("homeCommunityId") String homeCommunityId, @RequestParam("repositoryUniqueId") String repositoryUniqueId, @RequestParam("patnerId") String patnerId, final ModelMap model) {
		if (StringUtils.isBlank(documentUniqueId) || StringUtils.isBlank(patnerId)) {
			
		}
		model.addAttribute("list", queryService.retrieveDocument(documentUniqueId, homeCommunityId, repositoryUniqueId, patnerId));
		return "retrieve/table";
	}
}
