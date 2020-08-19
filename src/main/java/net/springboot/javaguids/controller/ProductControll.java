package net.springboot.javaguids.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import net.springboot.javaguids.Product;
import net.springboot.javaguids.ProductService;

@Controller
public class ProductControll {
	String sortDrc="asc";	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/")
	public String init(Model model) {

		
		
		model.addAttribute("sortDrc", sortDrc);
		return initByPage(model,1,"name","asc");
	}
	
	
	@GetMapping("/page/{pageNumber}")
	public String initByPage(Model model,@PathVariable int pageNumber,@Param("sortField") String sortField,@Param("sortDirec") String sortDirec) {
		
		Page<Product> page=productService.getAll(pageNumber,sortDirec,sortField);
		List<Product> listProducts=page.getContent();
		
		long totalItem=page.getTotalElements();
		int totalPages=page.getTotalPages();
		
		
		model.addAttribute("currentpage", pageNumber);
		model.addAttribute("totalItem", totalItem);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("listProducts", listProducts);
		
		sortDrc=this.sortDrc.equals("asc")?"desc":"asc";
		model.addAttribute("sortDrc", sortDrc);
		return "index";
	}
	
	
	
	@GetMapping("/delete/{id}")
	public String delete(Model model,@PathVariable int  id) {
		
		productService.delete(id);
		
		
		return init(model);
	}
	
	
	
	@GetMapping("/showForm")
	public String showForm() {
		
		return "add-product";
	}
	
	
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable int id,Model model) {
		
		Product product=productService.getById(id);
		model.addAttribute("product", product);
		
		return "update-product";
	}
	
	
	@PostMapping("/update/{id}")
	public String udate(@PathVariable int id,Model model,Product product) {
		
		product.setId(id);
		productService.save(product);
		
		return "redirect:/";
	}
	
	
	
	
	
	@PostMapping("/add")
	public String addProduct(Product product,Model model) {
		
		productService.save(product);
	
		return "redirect:/";
	}
	
	
	
	
	
	
	
	

}
