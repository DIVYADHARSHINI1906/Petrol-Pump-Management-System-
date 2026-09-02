package com.petrolpump.service;

import com.petrolpump.model.Customer;
import com.petrolpump.model.Employee;
import com.petrolpump.model.Fuel;
import com.petrolpump.model.Sale;
import com.petrolpump.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private FuelService fuelService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public List<Sale> getSalesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return saleRepository.findBySaleDateBetween(startDate, endDate);
    }

    public List<Sale> getSalesByFuel(Long fuelId) {
        return saleRepository.findByFuelId(fuelId);
    }

    public List<Sale> getSalesByCustomer(Long customerId) {
        return saleRepository.findByCustomerId(customerId);
    }

    public List<Sale> getSalesByEmployee(Long employeeId) {
        return saleRepository.findByEmployeeId(employeeId);
    }

    @Transactional
    public Sale createSale(Sale sale) {
        // Validate fuel availability
        Fuel fuel = fuelService.getFuelById(sale.getFuel().getId())
                .orElseThrow(() -> new RuntimeException("Fuel not found"));

        if (fuel.getQuantity().compareTo(sale.getQuantity()) < 0) {
            throw new RuntimeException("Insufficient fuel stock");
        }

        // Set price per unit from fuel
        sale.setPricePerUnit(fuel.getPrice());

        // Calculate total amount
        BigDecimal totalAmount = sale.getQuantity().multiply(fuel.getPrice());
        sale.setTotalAmount(totalAmount);

        // Set employee if not set
        if (sale.getEmployee() == null || sale.getEmployee().getId() == null) {
            throw new RuntimeException("Employee is required");
        }

        Employee employee = employeeService.getEmployeeById(sale.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        sale.setEmployee(employee);

        // Handle customer (create if not exists)
        if (sale.getCustomer() != null && sale.getCustomer().getVehicleNumber() != null) {
            Customer customer = customerService.getOrCreateCustomer(
                    sale.getCustomer().getVehicleNumber(),
                    sale.getCustomer().getCustomerName(),
                    sale.getCustomer().getPhoneNumber(),
                    sale.getCustomer().getAddress()
            );
            sale.setCustomer(customer);
        }

        // Save the sale
        Sale savedSale = saleRepository.save(sale);

        // Update fuel stock
        fuelService.updateFuelStock(fuel.getId(), sale.getQuantity());

        return savedSale;
    }

    public Sale updateSale(Long id, Sale saleDetails) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        // Note: In a real system, you might want to handle stock adjustments here
        // For simplicity, we're not allowing modification of quantity/fuel after sale

        sale.setPaymentMethod(saleDetails.getPaymentMethod());
        sale.setRemarks(saleDetails.getRemarks());

        return saleRepository.save(sale);
    }

    public void deleteSale(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found"));
        saleRepository.delete(sale);
    }

    public BigDecimal getTotalSalesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal total = saleRepository.getTotalSalesBetweenDates(startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }

    public Long getTotalTransactionsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        Long count = saleRepository.getTotalTransactionsBetweenDates(startDate, endDate);
        return count != null ? count : 0L;
    }

    public List<Sale> getTodaySales() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return saleRepository.findBySaleDateBetween(startOfDay, endOfDay);
    }

    public BigDecimal getTodayTotalSales() {
        return getTotalSalesBetweenDates(
                LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0),
                LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999)
        );
    }

    public Long getTodayTotalTransactions() {
        return getTotalTransactionsBetweenDates(
                LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0),
                LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999)
        );
    }

    public BigDecimal getTotalFuelSoldBetweenDates(Long fuelId, LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal total = saleRepository.getTotalFuelSoldBetweenDates(fuelId, startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }
}
