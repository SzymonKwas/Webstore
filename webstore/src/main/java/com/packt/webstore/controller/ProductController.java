package com.packt.webstore.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.packt.webstore.domain.Product;
import com.packt.webstore.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	ServletContext context;

	@RequestMapping
	public String list(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}

	@RequestMapping("/all")
	public ModelAndView allProducts() {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("products", productService.getAllProducts());
		modelAndView.setViewName("products");
		return modelAndView;
	}

	@RequestMapping("/{category}")
	public String getProductsByCategory(@PathVariable("category") String productCategory, Model model) {
		model.addAttribute("products", productService.getProductsByCategory(productCategory));
		return "products";
	}

	@RequestMapping("/filter/{ByCriteria}")
	public String getProductsByFilter(@MatrixVariable(pathVar = "ByCriteria") Map<String, List<String>> filterParams,
			Model model) {
		model.addAttribute("products", productService.getProductsByFilter(filterParams));
		return "products";
	}

	@RequestMapping("/{category}/filter/byprice/{ByPrice}")
	public String getProductsByPriceFilter(@PathVariable("category") String productCategory,
			@MatrixVariable(pathVar = "ByPrice") Map<String, List<String>> filterParams, Model model) {

		model.addAttribute("products", productService.getProductsByPriceFilter(productCategory, filterParams));
		return "products";
	}

	@RequestMapping("/product")
	public String getProductById(@RequestParam("id") String productId, Model model) {

		model.addAttribute("product", productService.getProductById(productId));
		return "product";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		return "addProduct";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") Product productToBeAdded, BindingResult result,
			HttpServletRequest request) {

		String[] suppressedFields = result.getSuppressedFields();
		if (suppressedFields.length > 0) {
			throw new RuntimeException(
					"Proba wiazania niedozwolonych pól: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}

		MultipartFile productImage = productToBeAdded.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		if (productImage != null && !productImage.isEmpty()) {
			try {
				productImage.transferTo(
						new File(rootDirectory + "resources\\images\\" + productToBeAdded.getProductId() + ".png"));
			} catch (Exception e) {
				throw new RuntimeException("Niepowodzenie podczas próby zapisu obrazka produktu", e);
			}
		}

		MultipartFile productPDF = productToBeAdded.getProductPDF();
		if (productPDF != null && !productPDF.isEmpty()) {
			try {
				productPDF.transferTo(
						new File(rootDirectory + "resources\\pdf\\" + productToBeAdded.getProductId() + ".pdf"));
			} catch (Exception e) {
				throw new RuntimeException("Niepowodzenie podczas próby zapisu pliku pdf produktu", e);
			}

		}

		productService.addProduct(productToBeAdded);
		return "redirect:/products";
	}

	@RequestMapping(value = "/product/pdf", method = RequestMethod.GET, produces = "application/pdf")

	public @ResponseBody HttpEntity<byte[]> openProductsPDF(@RequestParam("id") String productId) throws IOException {
	
		File file = new File(context.getRealPath("/resources/pdf/") + "\\"+productId+".pdf");
		if (!file.exists()) {
			throw new FileNotFoundException("file was not found.");
		}
		byte[] document = FileCopyUtils.copyToByteArray(file);
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "pdf"));
		header.set("Content-Disposition", "inline; filename=" + file.getName());
		header.setContentLength(document.length);

		return new HttpEntity<byte[]>(document, header);
	}

	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setDisallowedFields("unitsInOrder", "discontinued");
		binder.setAllowedFields("productId", "name", "unitPrice", "description", "manufacturer", "category",
				"unitsInStock", "productImage", "productPDF");
	}
}
