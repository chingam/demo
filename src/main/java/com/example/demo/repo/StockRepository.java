package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

	@Query(value="select s.id,s.quantity, s.costprice, s.salesprice, i.name from stock s left outer join item i on s.itemid = i.id", nativeQuery=true)
	public List<Object[]> findWithItemName();
	
	@Query(value="select s.id,s.quantity, s.costprice, s.salesprice, i.name, i.id as itmid from stock s left outer join item i on s.itemid = i.id where i.name like :name", nativeQuery=true)
	public List<Object[]> findItemsByName(@Param("name") String name);
	
	public Stock findByItemId(Long itemId);
}
