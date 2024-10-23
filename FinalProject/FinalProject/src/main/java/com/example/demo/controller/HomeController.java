package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.global.GlobalData;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;

@Controller
public class HomeController {

	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/shop")
	public String shop(Model model) {
        List<Product> products = productService.getAllProduct();
        List<Category>category= categoryService.getAllCategory();
		model.addAttribute("cartCount",GlobalData.cart.size());

        model.addAttribute("products", products);
        model.addAttribute("categories",category);
        return "shop";
    }
	@GetMapping("/shop/category/{id}")
	public String getProductByCategoryId(@PathVariable("id") int id,Model model)
	{
        List<Category>category= categoryService.getAllCategory();
		model.addAttribute("cartCount",GlobalData.cart.size());

        model.addAttribute("categories",category);
        model.addAttribute("products",productService.getProductByCategoryId(id));
			return "/shop";
		
		
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(@PathVariable("id") Long id, Model model)
	{
        model.addAttribute("product",productService.getProductById(id).get());
		model.addAttribute("cartCount",GlobalData.cart.size());


		return "viewProduct";
	}
	
	
}