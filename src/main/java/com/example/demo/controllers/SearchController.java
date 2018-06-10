package com.example.demo.controllers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Item;
import com.example.demo.repo.ItemRepository;
import com.example.demo.repo.StockRepository;
import com.example.demo.view.JobSearchReponse;
import com.example.demo.view.MultiColumnSearch;
import com.example.demo.view.ProductSearchReponse;
import com.example.demo.view.StockData;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired StockRepository repo;
	@Autowired ItemRepository itemRepo;
	
	@RequestMapping(value="/products/{hint}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JobSearchReponse productSearch(@PathVariable String hint) {
		return new JobSearchReponse(populateData(hint));
	}
	
	@RequestMapping(value="/prods/{hint}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProductSearchReponse search(@PathVariable String hint) {
		return new ProductSearchReponse(populateProducts(hint));
	}

	private List<MultiColumnSearch> populateData(String hint) {
		List<MultiColumnSearch> suggestions = new ArrayList<>();
		List<StockData> products = new ArrayList<>();
		repo.findItemsByName("%"+hint+"%").forEach(a -> {
			StockData std = new StockData();
			BigInteger l = (BigInteger) a[0];
			std.setId(l.longValue());
			std.setQuantity((Integer) a[1]);
			std.setCostPrice((BigDecimal) a[2]);
			std.setSalesPrice((BigDecimal) a[3]);
			std.setName((String) a[4]);
			BigInteger ietm = (BigInteger) a[5];
			std.setItemId(ietm.longValue());
			products.add(std);
			
		});
		
		for (StockData list : products) {
			MultiColumnSearch multiColumn = new MultiColumnSearch();
			String[] columns = new String[3]; // for job search list
			String[] lines = new String[2];
			
			columns[0] = list.getName();
			columns[1] = list.getQuantity().toString();
			columns[2] = list.getSalesPrice().toString();
			lines[0] = list.getItemId().toString();
			multiColumn.setKey(list.getName());
			multiColumn.setValue(columns);
			multiColumn.setData(lines);
			suggestions.add(multiColumn);
		}
		return suggestions;
	}
	
	private List<MultiColumnSearch> populateProducts(String hint) {
		List<MultiColumnSearch> suggestions = new ArrayList<>();
		for (Item list : itemRepo.findByNameContaining(hint)) {
			MultiColumnSearch multiColumn = new MultiColumnSearch();
			String[] columns = new String[2]; // for job search list
			String[] lines = new String[2];
			
			columns[0] = list.getName();
			columns[1] = list.getDescription();
			lines[0] = list.getId().toString();
			multiColumn.setKey(list.getName());
			multiColumn.setValue(columns);
			multiColumn.setData(lines);
			suggestions.add(multiColumn);
		}
		return suggestions;
	}
}
