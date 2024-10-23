package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ProductDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;

@Controller
public class AdminController
{

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	
	public static String uploadDir=System.getProperty("user.dir")+"/src/main/resources/static/productImages";

	
	@GetMapping("/admin/registration1")
	public String getRegistration()
	{
		return "AdminRegistration";
	}
	
	
	@PostMapping("/admin/registration1")
	public String saveUser(@ModelAttribute("user")UserDTO userDTO,Model model)	
	{
		userDTO.setRole("ADMIN");												
		userService.save(userDTO);
		model.addAttribute("message", "Registration Successfully");
		return "AdminRegistration"; 
	}
	
	@GetMapping("/admin")		//router
	public String adminHome()
	{
		return "adminHome";//html page
	}
	
	
	/////////////////Category////////////////////////////
	
	
	@GetMapping("/admin/categories")
	public String viewCategories(Model model)
	{
		
		model.addAttribute("listCategories",categoryService.getAllCategory());
		return "view_categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String addCategory(Model model)
	{
		model.addAttribute("category",new Category());
		return "Category";
	}
	
	@PostMapping("/admin/categories/save")
    public String saveCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/admin/categories"; //it will be redirect to "view_categories.html"page after clicked on submit button
    }
	
	@GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        categoryService.deleteCategoryById(id);
        return "redirect:/admin/categories"; 
    }
	
	 @GetMapping("/admin/categories/update/{id}")
	 public String updateCategory(@PathVariable("id") int id, Model model)
	 {
		Optional<Category> category=categoryService.getCategoryById(id);
		if(category.isPresent())
		{
			model.addAttribute("category",category.get());
			return "Category";
		}
		else {
			return "404";
		}
	 }
	 
	 
		/////////////////Product////////////////////////////

	@GetMapping("/admin/products")
	public String viewProducts(Model model)
	{
		model.addAttribute("products", productService.getAllProduct());
		return "Product";
	}
	
	@GetMapping("/admin/products/add")
	public String addProduct(Model model)
	{
		model.addAttribute("productDTO",new ProductDTO());
		model.addAttribute("categories",categoryService.getAllCategory());
		return "AddProduct";
	}
	
	@PostMapping("/admin/products/save")
    public String saveProduct(@ModelAttribute ("productDTO")ProductDTO productDTO,
    						@RequestParam("productImage")MultipartFile file,
    						@RequestParam("imgName")String imgName)throws IOException
	{
		Product product=new Product();

		product.setProduct_id(productDTO.getProduct_id());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setImgName(productDTO.getImgName());
		product.setWeight(productDTO.getWeight());
		String imageUUID;
		if(!file.isEmpty())
		{
			imageUUID=file.getOriginalFilename();
			Path fileNameAndPath=Paths.get(uploadDir,imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
			
		}
		else
		{
			imageUUID=imgName;
		}
		product.setImgName(imageUUID);	
		productService.addProduct(product);
		
        return "redirect:/admin/products"; //it will be redirect to "view_categories.html"page after clicked on submit button
    }
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable("id")Long id, Model model)
	{
		productService.deleteProductById(id);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/update/{id}")
	public String updateProduct(@PathVariable long id,Model model)
	{
		Product product=productService.getProductById(id).get();
		ProductDTO productDTO=new ProductDTO();
		productDTO.setProduct_id(product.getProduct_id());
		productDTO.setName(product.getName());
		productDTO.setCategoryId((product.getCategory().getId()));
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("productDTO",productDTO);
		return "AddProduct";
	}
}

