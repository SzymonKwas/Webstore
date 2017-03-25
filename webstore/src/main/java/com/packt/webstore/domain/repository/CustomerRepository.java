package com.packt.webstore.domain.repository;

import java.util.List;

import com.packt.webstore.domain.Customer;

public interface CustomerRepository {
	List<Customer> getAllCustomers();
	void saveCustomer(Customer customer);
	Customer getCustomer(String customerId);
	Boolean customerExists(String customerId);
}
