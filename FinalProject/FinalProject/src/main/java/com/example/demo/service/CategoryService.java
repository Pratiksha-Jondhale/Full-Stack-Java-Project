package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryService
{

	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> getAllCategory()
	{
		return categoryRepository.findAll();
	}
	
	@Transactional
    public void deleteCategoryById(int id)
	{
        categoryRepository.deleteById(id);
    }
	
	 
	public Optional<Category> getCategoryById(int id)
	{
		return categoryRepository.findById(id);
	}
	
	
	public void save(Category category)
	{
		 categoryRepository.save(category);
	}
	
	
}