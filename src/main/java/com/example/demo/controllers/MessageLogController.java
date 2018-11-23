package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Button;
import com.example.demo.model.ServerLog;
import com.example.demo.repo.ServerLogRepository;

@Controller
@RequestMapping("/query/messagelog")
public class MessageLogController {

	@Autowired
	private ServerLogRepository repository;

	@ModelAttribute("events")
	public List<Button> getControleEvents() {
		return Collections.emptyList();
	}

	@ModelAttribute("messages")
	public List<ServerLog> getConfiguration() {
		return repository.findTop100ByOrderByDateDesc();
	}
	
	@GetMapping
	public String getScreen(final ModelMap model, final Locale locale) {
		model.addAttribute("formCode", "T09011");
		model.addAttribute("formName", "Message Logs");
		model.addAttribute("controller", "messagelog");
		model.addAttribute("bean", new ServerLog());
		return "query/template";
	}
}
