package com.example.demo.controllers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.exception.DemoException;
import com.example.demo.model.Stock;
import com.example.demo.repo.StockRepository;
import com.example.demo.view.StockData;

@Controller
@RequestMapping("/stock")
public class StockController extends AbstractController {
	private static final Logger LOG = LoggerFactory.getLogger(StockController.class);
	@Autowired
	StockRepository repo;

	@RequestMapping(method = RequestMethod.GET)
	public String getItem(final ModelMap model) {
		model.addAttribute("stock", new Stock());
		model.addAttribute("brand", "Stock");
		model.addAttribute("page", "stock");
		return "template";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(@Valid Stock stock, BindingResult binding, final ModelMap model) {
		LOG.info("Call stock request");
		try {
			if (binding.hasErrors()) {
				response.setErrorStatus();
				response.setErrorStatusAndMessage(binding.getFieldError().getField() +" : "+ binding.getFieldError().getDefaultMessage());
				return response.prepareAndGetJSONResponse();
			}
			
			stock.setEntryDate(new Date());
			repo.save(stock);
			// call service to save 
			response.setSuccessMessage(SUCCESS_MESSAGE);
			response.setSuccessStatus();
		} catch (Exception e) {
			LOG.error("Operation failed {}", e.getMessage());
		}
		return response.prepareAndGetJSONResponse();
	}
	
	@RequestMapping(value="/{stockId}", method=RequestMethod.GET)
	public String edit(@PathVariable Long stockId, final ModelMap model) {
		model.addAttribute("stock", repo.findById(stockId));
		return "stock/stock :: stockModal";
	}
	
	@RequestMapping(value="/{stockId}", method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> delete(@PathVariable Long stockId) throws DemoException{
		if (stockId == null) throw new DemoException();
		repo.deleteById(stockId);
		response.setSuccessStatus();
		return response.prepareAndGetJSONResponse();
	}

	@RequestMapping(value = "/find/all", method = RequestMethod.GET)
	public String getAll(final ModelMap model) {
		List<StockData> list = new ArrayList<>();
		repo.findWithItemName().forEach(a -> {
			StockData std = new StockData();
			BigInteger l = (BigInteger) a[0];
			std.setId(l.longValue());
			std.setQuantity((Integer) a[1]);
			std.setCostPrice((BigDecimal) a[2]);
			std.setSalesPrice((BigDecimal) a[3]);
			std.setName((String) a[4]);
			list.add(std);
		});
		model.put("stocks", list);
		return "stock/stock :: datas";
	}
}
