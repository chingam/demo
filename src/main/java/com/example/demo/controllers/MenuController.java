package com.example.demo.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.repo.T01199Repository;

@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired private T01199Repository repo;
	
	@GetMapping()
	public String getScreen(final ModelMap model, final Locale locale) {
		model.addAttribute("formCode", "M29050");
		model.addAttribute("formName", "Electronic Health Record");
		return "menu/menu";
	}
	
	@GetMapping(path = "/link")
	public String getLinks(@RequestParam Integer linkId, final ModelMap model, final Locale locale) {
		// role code get from session
		String roleCode = "0003";
		model.addAttribute("links", repo.findByRoleCodeAndLinkSeperation(roleCode, linkId));
		return "menu/link";
	}
}
