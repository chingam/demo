package com.example.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Employee;
import com.example.demo.repo.EmployeeRepository;

/**
 * @author Minhaj
 * @version 0.0.1
 * @since 05-07-2018
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeTest {
	@Autowired
	EmployeeRepository repo;

	@Test
	public void test1() {
		Employee employee = new Employee();
		employee.setName("Minhaj");
		employee.setFatherName("Iddris ali");
		employee.setMotherName("Gulshan ara");
		employee.setSalary(50000.00);
		Employee employeeObj = repo.save(employee);
		Assert.assertNotNull(employeeObj);
		Assert.assertNotNull(employeeObj.getId());
	}
}
