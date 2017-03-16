package com.packt.webstore.domain.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;

@Repository
public class InMemoryProductRepository implements ProductRepository {

	private List<Product> listOfProducts = new ArrayList<Product>();

	public InMemoryProductRepository() {
		Product iphone = new Product("P1234", "iPhone 5s", new BigDecimal(500));
		iphone.setDescription(
				"Apple iPhone 6s, smartfon z 6-calowym ekranem o rozdzielczosci 860x1200 i 8-megapikselowym aparatem");
		iphone.setCategory("smartfon");
		iphone.setManufacturer("Apple");
		iphone.setUnitsInStock(1000);

		Product laptop_dell = new Product("P1235", "Dell Inspiron", new BigDecimal(700));
		laptop_dell.setDescription("Dell Inspiron, 15.7-calowy laptop (czarny) z procesorami Intel Core 6. generacji");
		laptop_dell.setCategory("laptop");
		laptop_dell.setManufacturer("Dell");
		laptop_dell.setUnitsInStock(1000);

		Product tablet_Nexus = new Product("P1236", "Nexus 9", new BigDecimal(300));
		tablet_Nexus.setDescription(
				"Google Nexus 9 jest najlzejszym 7-calowym tabletem z 8-rdzeniowym procesorem Qualcomm Snapdragon S6 Pro");
		tablet_Nexus.setCategory("tablet");
		tablet_Nexus.setManufacturer("Google");
		tablet_Nexus.setUnitsInStock(1000);

		listOfProducts.add(iphone);
		listOfProducts.add(laptop_dell);
		listOfProducts.add(tablet_Nexus);

	}

	public List<Product> getAllProducts() {
		return listOfProducts;
	}

	public Product getProductById(String productId) {
		Product productById = null;

		for (Product product : listOfProducts) {
			if (product != null && product.getProductId() != null && product.getProductId().equals(productId)) {
				productById = product;
				break;
			}
		}

		if (productById == null) {
			throw new IllegalArgumentException("Brak produktu o wskazanym id: " + productId);
		}

		return productById;
	}

	public List<Product> getProductsByCategory(String category) {
		List<Product> productsByCategory = new ArrayList<Product>();

		for (Product product : listOfProducts) {
			if (category.equalsIgnoreCase(product.getCategory())) {
				productsByCategory.add(product);
			}
		}

		return productsByCategory;
	}

	public Set<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
		Set<Product> productsByBrand = new HashSet<Product>();
		Set<Product> productsByCategory = new HashSet<Product>();

		Set<String> criterias = filterParams.keySet();

		if (criterias.contains("brand")) {
			for (String brandName : filterParams.get("brand")) {
				for (Product product : listOfProducts) {
					if (brandName.equalsIgnoreCase(product.getManufacturer())) {
						productsByBrand.add(product);
					}
				}
			}
		}

		if (criterias.contains("category")) {
			for (String category : filterParams.get("category")) {
				productsByCategory.addAll(this.getProductsByCategory(category));
			}
		}

		productsByCategory.retainAll(productsByBrand);

		return productsByCategory;
	}

	public List<Product> getProductsByManufacturer(String manufacturer) {

		List<Product> productsByManufacturer = new ArrayList<Product>();

		for (Product product : listOfProducts) {
			if (manufacturer.equalsIgnoreCase(product.getManufacturer())) {
				productsByManufacturer.add(product);
			}
		}
		return productsByManufacturer;
	}

	public Set<Product> getProductsByPriceFilter(String category,Map<String, List<String>> filterParams) {

		Set<Product> productsByManufacturer = new HashSet<Product>();
		Set<Product> productsBiggerThanPrice = new HashSet<Product>();
		Set<Product> productsLowerThanPrice = new HashSet<Product>();

		Set<String> criterias = filterParams.keySet();

		if (criterias.contains("brand")) {
			for (String brandName : filterParams.get("brand")) {
				for (Product product : getProductsByCategory(category)) {
					
					if (brandName.equalsIgnoreCase(product.getManufacturer())) {
						productsByManufacturer.add(product);
					}
				}
			}
		} else {
			productsByManufacturer.addAll(listOfProducts);
		}

		if (criterias.contains("low")) {
			for (String lowPrice : filterParams.get("low")) {
				for (Product product : listOfProducts) {
					if (product.getUnitPrice().compareTo(BigDecimal.valueOf(Long.parseLong(lowPrice))) >= 0) {
						productsBiggerThanPrice.add(product);
					}
				}
			}
			productsByManufacturer.retainAll(productsBiggerThanPrice);
		}

		if (criterias.contains("high")) {
			for (String highPrice : filterParams.get("high")) {
				for (Product product : listOfProducts) {
					if (product.getUnitPrice().compareTo(BigDecimal.valueOf(Long.parseLong(highPrice))) <= 0) {
						productsLowerThanPrice.add(product);
					}
				}
			}
			productsByManufacturer.retainAll(productsLowerThanPrice);
		}

		return productsByManufacturer;
	}
}
