package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import model.Product;
import service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping
	public String defaultPage(Model model)
	{
		List<Product > products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "productList";
	}
	

	@RequestMapping("/all")
	public String getAllProducts(Model model)
	{
		List<Product > products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "productList";
	}
	
	@RequestMapping("/product")
	public String getProductById(@RequestParam(name="id") String productId , Model model)
	{
	  Product product =	productService.getProductgetById(productId);
	  model.addAttribute("product", product);
	  return "productDetail";
	}
	
	@RequestMapping("/category/{categoryName}")
	public String listProductsByCategoryNamed(@PathVariable(name = "categoryName") String categoryName, Model model) {
		List<Product> productss = productService.getProductByCategory(categoryName);
		model.addAttribute("products", productss);
		return "productList";
	}

	
	// // http://localhost:8080/SpringMVCWeek3/products/brand/brands=apple,google,samsung,
	@RequestMapping("/brand/{brands}")
	public String listProductsByCategoryBrands(@MatrixVariable(pathVar = "brands") List<String> brands, Model model) {
		List<Product> products = productService.getProductByBrands(brands);
		model.addAttribute("products", products);
		return "productList";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	//JSp sayfasindaki modelAttribute ile ayni isimde olmalidi! yoksa exception verir!
	public String addProductPage(@ModelAttribute("newProduct") Product productToAdded) {
		System.out.println("addProductPage#get invoked!");
		productToAdded.setDescription("aciklama kismi....");
		productToAdded.setCategory("laptop");
		return "productAdd";
	}
	
	
	// Bu metodumuzda Arrays sýnýfýmýzýn aslist() metodu yardýmýyla bir liste yapýsýna aktarabiliriz. 
	@ModelAttribute("manufacturerList")
	public List<String> prepareManufacturers() {
		System.out.println("prepareManufacturers#invoked!");
		return Arrays.asList("Apple","Samsung","Dell","Google","Sony");
	}
	

	@ModelAttribute("categoryList")
	public List<String> prepareCategories(Model model) {
		System.out.println("categoryList#invoked!");
		return Arrays.asList("Tablet","Laptop","TV","SmartPhone");
	}
	
	
	@ModelAttribute("conditionsMap")
	public Map<String , String> prepareConditions() {
		System.out.println("prepareConditions#invoked!");
		Map<String, String> conditions = new LinkedHashMap<>();
		conditions.put("new", "New");
		conditions.put("old", "Old");
		conditions.put("refurbished", "Refurbished");
		return conditions;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addProductPageSubmit(@ModelAttribute("newProduct") Product productToAdded) {
	productService.addProduct(productToAdded);
		return "redirect:/products";
	}
	
	

}
