package com.packt.webstore.domain.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.packt.webstore.domain.Customer;
import com.packt.webstore.domain.repository.CustomerRepository;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {
	private List<Customer> customers = new ArrayList<Customer>();

	public InMemoryCustomerRepository() {

		Customer customer1 = new Customer("C001", "Andrzej Pisiarkczyk");
		
		Customer customer2 = new Customer("C002", "Kazimierz Kat");
	
		Customer customer3 = new Customer("C003", "Sonia Wilczynska");
		

		customers.add(customer1);
		customers.add(customer2);
		customers.add(customer3);
	}


	public List<Customer> getAllCustomers() {
		return customers;
	}

}
