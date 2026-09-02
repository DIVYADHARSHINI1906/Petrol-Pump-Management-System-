package com.petrolpump.config;

import com.petrolpump.model.Employee;
import com.petrolpump.model.Fuel;
import com.petrolpump.model.User;
import com.petrolpump.repository.EmployeeRepository;
import com.petrolpump.repository.FuelRepository;
import com.petrolpump.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FuelRepository fuelRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Initialize default admin user if not exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("Administrator");
            admin.setEmail("admin@petrolpump.com");
            admin.setRole("ADMIN");
            admin.setActive(true);
            userRepository.save(admin);
            System.out.println("Default admin user created: username=admin, password=admin123");
        }

        // Initialize default staff user if not exists
        if (!userRepository.existsByUsername("staff")) {
            User staff = new User();
            staff.setUsername("staff");
            staff.setPassword(passwordEncoder.encode("staff123"));
            staff.setFullName("Staff Member");
            staff.setEmail("staff@petrolpump.com");
            staff.setRole("STAFF");
            staff.setActive(true);
            userRepository.save(staff);
            System.out.println("Default staff user created: username=staff, password=staff123");
        }

        // Initialize fuel types if not exists
        if (fuelRepository.count() == 0) {
            Fuel petrol = new Fuel();
            petrol.setName("Petrol");
            petrol.setPrice(new BigDecimal("95.50"));
            petrol.setQuantity(new BigDecimal("1000.00"));
            petrol.setLowStockThreshold(new BigDecimal("200.00"));
            petrol.setActive(true);
            fuelRepository.save(petrol);

            Fuel diesel = new Fuel();
            diesel.setName("Diesel");
            diesel.setPrice(new BigDecimal("85.00"));
            diesel.setQuantity(new BigDecimal("1500.00"));
            diesel.setLowStockThreshold(new BigDecimal("300.00"));
            diesel.setActive(true);
            fuelRepository.save(diesel);

            System.out.println("Default fuel types created: Petrol and Diesel");
        }

        // Initialize sample employee if not exists
        if (employeeRepository.count() == 0) {
            Employee employee = new Employee();
            employee.setName("John Doe");
            employee.setPosition("Pump Attendant");
            employee.setPhoneNumber("9876543210");
            employee.setEmail("john@petrolpump.com");
            employee.setSalary(new BigDecimal("25000.00"));
            employee.setActive(true);
            employeeRepository.save(employee);

            System.out.println("Sample employee created: John Doe");
        }
    }
}
