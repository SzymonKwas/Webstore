package com.packt.webstore.domain.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Address;
import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.CustomerRepository;
import com.packt.webstore.exception.ProductNotFoundException;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {
	private List<Customer> customers = new ArrayList<Customer>();
	private long nextCustomerId;

	public InMemoryCustomerRepository() {
		nextCustomerId = 1000;
		Customer customer1 = new Customer(getNextCustomerId(), "Andrzej Pisiarkczyk", "606125125",
				new Address("Polska", "Slask", "Gliwice", "44-100", "Kochanowskiego", "6/8"));

		Customer customer2 = new Customer(getNextCustomerId(), "Kazimierz Kat", "686125149",
				new Address("Polska", "Wielkopolska", "Poznan", "22-180", "Pulawska", "70A"));

		Customer customer3 = new Customer(getNextCustomerId(), "Sonia Wilczynska", "801333324",
				new Address("Polska", "Malopolskie", "Krakow", "11-120", "Witta", "8"));

		customers.add(customer1);
		customers.add(customer2);
		customers.add(customer3);
	}

	public List<Customer> getAllCustomers() {
		return customers;
	}

	public void saveCustomer(Customer customer) {
		customer.setCustomerId(getNextCustomerId());
		customers.add(customer);
	}

	public Customer getCustomer(String customerId) {
		Customer customerById = getCustomerById(customerId);

		return customerById;
	}

	public Boolean customerExists(String customerId) {
		for (Customer customer : customers) {
			if (customer.getCustomerId().equalsIgnoreCase(customerId)) {
				return true;
			}
		}
		return false;
	}

	private String getNextCustomerId() {
		nextCustomerId = nextCustomerId + 1;
		return ("C" + Long.toString(nextCustomerId));

	}

	public Customer getCustomerById(String customerId) {
		Customer customerById = null;

		for (Customer customer : customers) {
			if (customer != null && customer.getCustomerId() != null && customer.getCustomerId().equals(customerId)) {
				customerById = customer;
				break;
			}
		}

		if (customerById == null) {
			throw new ProductNotFoundException(customerId);
		}

		return customerById;
	}
}