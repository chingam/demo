package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Employee;

/**
 * @author Minhaj
 * @version 0.0.1
 * @since 05-07-2018
 */

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
