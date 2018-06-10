package com.example.demo;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Stock;
import com.example.demo.repo.StockRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StockTest {

	@Autowired
	StockRepository rep;

	@Test
	public void firstTest() {
		Stock stock = new Stock();
		stock.setItemId(1L);
		stock.setCostPrice(new BigDecimal(12.00));
		stock.setSalesPrice(new BigDecimal(15.00));
		Stock st = rep.save(stock);
		Assert.assertNotNull(st);
	}
	
	@Test
	public void secondTest() {
		List<Stock> sts = rep.findAll();
		Assert.assertNotNull(sts);
		sts.forEach(f -> {
			System.out.println("cost price >> "+ f.getCostPrice());
		});
	}
	
	@Test
	public void thirdTest() {
		List<Object[]> sts = rep.findWithItemName();
		
		Assert.assertNotNull(sts);
	}
}
