package com.example.demo.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long>
{
	List<Product> findByCategoryId(int categoryId);	
}