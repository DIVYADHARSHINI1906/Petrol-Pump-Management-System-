package com.petrolpump.service;

import com.petrolpump.model.Customer;
import com.petrolpump.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> getActiveCustomers() {
        return customerRepository.findByActiveTrue();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByVehicleNumber(String vehicleNumber) {
        return customerRepository.findByVehicleNumber(vehicleNumber);
    }

    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByVehicleNumber(customer.getVehicleNumber())) {
            throw new RuntimeException("Vehicle number already exists");
        }
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (!customer.getVehicleNumber().equals(customerDetails.getVehicleNumber()) && 
            customerRepository.existsByVehicleNumber(customerDetails.getVehicleNumber())) {
            throw new RuntimeException("Vehicle number already exists");
        }

        customer.setVehicleNumber(customerDetails.getVehicleNumber());
        customer.setCustomerName(customerDetails.getCustomerName());
        customer.setPhoneNumber(customerDetails.getPhoneNumber());
        customer.setAddress(customerDetails.getAddress());
        customer.setActive(customerDetails.getActive());

        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerRepository.delete(customer);
    }

    public List<Customer> searchCustomersByName(String name) {
        return customerRepository.findByCustomerNameContainingIgnoreCase(name);
    }

    public Customer getOrCreateCustomer(String vehicleNumber, String customerName, String phoneNumber, String address) {
        Optional<Customer> existingCustomer = customerRepository.findByVehicleNumber(vehicleNumber);
        
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            customer.setCustomerName(customerName);
            customer.setPhoneNumber(phoneNumber);
            customer.setAddress(address);
            return customerRepository.save(customer);
        } else {
            Customer newCustomer = new Customer(vehicleNumber, customerName, phoneNumber, address);
            return customerRepository.save(newCustomer);
        }
    }
}
