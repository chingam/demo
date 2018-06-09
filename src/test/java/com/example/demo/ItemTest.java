package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Item;
import com.example.demo.repo.ItemRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemTest {
	@Autowired ItemRepository repo;

	@Test
	public void firstTest() {
		Item item = new Item();
		item.setName("Mobile");
		item.setDescription("Apple 10");
		Item itemObj = repo.save(item);
		Assert.assertNotNull(itemObj);
		Assert.assertNotNull(itemObj.getId());
		System.setProperty("id", itemObj.getId().toString());
		
	}
	
	@Test
	public void secondTest() {
		Item item = new Item();
		item.setId(1L);
		repo.delete(item);
	}
}
