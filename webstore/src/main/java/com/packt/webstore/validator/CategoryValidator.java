package com.packt.webstore.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.ProductService;

public class CategoryValidator implements ConstraintValidator<Category, String> {
	@Autowired
	private ProductService productService;

	public void initialize(Category constraintAnnotation) {

	}

	public boolean isValid(String value, ConstraintValidatorContext context) {

		List<Product> productsByCategory;
		try {
			productsByCategory = productService.getProductsByCategory(value);

		} catch (ProductNotFoundException e) {
			return false;
		}

		if (productsByCategory.isEmpty()) {
			return false;
		}

		return true;
	}

}