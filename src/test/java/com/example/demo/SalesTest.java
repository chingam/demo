package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.repo.SalesRepo;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SalesTest {
	@Autowired SalesRepo rep;

	@Test
	public void firstTest() {
		
	}
}
