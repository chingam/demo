package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Stock;

public interface StockRepo extends JpaRepository<Stock, Long> {

}
