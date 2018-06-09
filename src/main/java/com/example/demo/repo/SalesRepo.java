package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Sales;

public interface SalesRepo extends JpaRepository<Sales, Long> {

}
