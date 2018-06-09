package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.exception.DemoException;
import com.example.demo.model.Item;
import com.example.demo.repo.ItemRepository;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	ItemRepository repo;

	@RequestMapping(method = RequestMethod.GET)
	public String getItem(final ModelMap model) {
		model.addAttribute("item", new Item());
		model.addAttribute("brand", "Item setup");
		model.addAttribute("page", "item");
		return "template";// 01716291680
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> save(@Valid Item item, BindingResult binding, final ModelMap model) throws BindException {
		Map<String, String> result = new HashMap<>();
		if (binding.hasErrors())
			throw new BindException(binding);
		repo.save(item);
		result.put("status", "success");
		result.put("redirect", "item/find/all");
		return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getItem(@PathVariable Long id) throws DemoException {
		Map<String, Object> result = new HashMap<>();
		if (id == null)
			throw new DemoException("Item id should not be emtry");
		result.put("status", "success");
		result.put("data", repo.findById(id));
		return result;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> deleteItem(@PathVariable Long id) throws DemoException {
		Map<String, String> result = new HashMap<>();
		if (id == null)
			throw new DemoException("Item id should not be emtry");
		repo.deleteById(id);
		result.put("status", "success");
		result.put("redirect", "item/find/all");
		return result;
	}

	@RequestMapping(value = "/find/all", method = RequestMethod.GET)
	public String getAll(final ModelMap model) {
		model.put("list", repo.findAll());
		return "item/table";
	}
}
