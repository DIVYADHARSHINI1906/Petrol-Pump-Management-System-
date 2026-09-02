package com.petrolpump.service;

import com.petrolpump.model.Fuel;
import com.petrolpump.repository.FuelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class FuelService {

    @Autowired
    private FuelRepository fuelRepository;

    public List<Fuel> getAllFuels() {
        return fuelRepository.findAll();
    }

    public List<Fuel> getActiveFuels() {
        return fuelRepository.findByActiveTrue();
    }

    public Optional<Fuel> getFuelById(Long id) {
        return fuelRepository.findById(id);
    }

    public Optional<Fuel> getFuelByName(String name) {
        return fuelRepository.findByName(name);
    }

    public Fuel createFuel(Fuel fuel) {
        if (fuelRepository.existsByName(fuel.getName())) {
            throw new RuntimeException("Fuel type already exists");
        }
        return fuelRepository.save(fuel);
    }

    public Fuel updateFuel(Long id, Fuel fuelDetails) {
        Fuel fuel = fuelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fuel not found"));

        if (!fuel.getName().equals(fuelDetails.getName()) && 
            fuelRepository.existsByName(fuelDetails.getName())) {
            throw new RuntimeException("Fuel type already exists");
        }

        fuel.setName(fuelDetails.getName());
        fuel.setPrice(fuelDetails.getPrice());
        fuel.setQuantity(fuelDetails.getQuantity());
        fuel.setLowStockThreshold(fuelDetails.getLowStockThreshold());
        fuel.setActive(fuelDetails.getActive());

        return fuelRepository.save(fuel);
    }

    public void deleteFuel(Long id) {
        Fuel fuel = fuelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fuel not found"));
        fuelRepository.delete(fuel);
    }

    public void updateFuelStock(Long fuelId, BigDecimal quantitySold) {
        Fuel fuel = fuelRepository.findById(fuelId)
                .orElseThrow(() -> new RuntimeException("Fuel not found"));

        BigDecimal newQuantity = fuel.getQuantity().subtract(quantitySold);
        if (newQuantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Insufficient fuel stock");
        }

        fuel.setQuantity(newQuantity);
        fuelRepository.save(fuel);
    }

    public List<Fuel> getLowStockFuels() {
        return fuelRepository.findByActiveTrue().stream()
                .filter(Fuel::isLowStock)
                .toList();
    }

    public boolean isLowStock(Long fuelId) {
        Fuel fuel = fuelRepository.findById(fuelId)
                .orElseThrow(() -> new RuntimeException("Fuel not found"));
        return fuel.isLowStock();
    }
}
