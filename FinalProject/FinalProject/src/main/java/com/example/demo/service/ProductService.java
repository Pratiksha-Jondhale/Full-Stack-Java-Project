package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService 
{
	@Autowired
	ProductRepository productRepository;
	
	public List<Product> getAllProduct()
	{
		return productRepository.findAll();
	}
	
	public void addProduct(Product product)
	{
		productRepository.save(product);
	}
	
	public void deleteProductById(Long id)
	{
		productRepository.deleteById(id);
	}
	
	public Optional<Product> getProductById(Long id)
	{
		return productRepository.findById(id);
	}
	
	public List<Product> getProductByCategoryId(int categoryId)
	{
		return productRepository.findByCategoryId(categoryId);
	}
}