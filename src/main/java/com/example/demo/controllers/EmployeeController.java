package com.example.demo.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.example.demo.model.Employee;
import com.example.demo.repo.EmployeeRepository;

/**
 * @author Minhaj
 * @version 0.0.1
 * @since 05-07-2018
 */

@Controller
@RequestMapping("/employee")
public class EmployeeController extends AbstractController {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeRepository employeeRepository;

	@RequestMapping(method = RequestMethod.GET)
	public String getItem(final ModelMap model) {
		model.addAttribute("employee", new Employee());
		model.addAttribute("brand", "Employee");
		model.addAttribute("page", "employee");
		return "template";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> add(@Valid Employee employee, BindingResult binding, final ModelMap model) {
		LOG.info("Call eployee request");
		try {
			if (binding.hasErrors()) {
				response.setErrorStatus();
				response.setErrorStatusAndMessage(binding.getFieldError().getField() +" : "+ binding.getFieldError().getDefaultMessage());
				return response.prepareAndGetJSONResponse();
			}
			
			employeeRepository.save(employee);
			response.setSuccessMessage(SUCCESS_MESSAGE);
			response.setSuccessStatus();
		} catch (Exception e) {
			LOG.error("Operation failed {}", e.getMessage());
		}
		return response.prepareAndGetJSONResponse();
	}
	
	@RequestMapping(value="/{employeeId}", method=RequestMethod.GET)
	public String edit(@PathVariable String employeeId, final ModelMap model) {
		model.addAttribute("employee", employeeRepository.getOne(employeeId));
		return "employee/employee :: employeeModal";
	}
	
	@RequestMapping(value="/{employeeId}", method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> delete(@PathVariable String employeeId) throws DemoException{
		if (employeeId == null) throw new DemoException();
		employeeRepository.deleteById(employeeId);
		response.setSuccessStatus();
		return response.prepareAndGetJSONResponse();
	}

	@RequestMapping(value = "/find/all", method = RequestMethod.GET)
	public String getAll(final ModelMap model) {
		List<Employee> employees = employeeRepository.findAll().stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).collect(Collectors.toList());
		model.put("employees", employees);
		return "employee/employee :: datas";
	}
}
