package com.sneakers.sneaker_inventory.service;

import com.sneakers.sneaker_inventory.model.Sneaker;
import com.sneakers.sneaker_inventory.repository.SneakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SneakerService {

    @Autowired
    private SneakerRepository repository;

    // Create
    public Sneaker createSneaker(Sneaker sneaker) {
        return repository.save(sneaker);
    }

    // Read All
    public List<Sneaker> getAllSneakers() {
        return repository.findAll();
    }

    // Read one
    public Optional<Sneaker> getSneakerById(Long id) {
        return repository.findById(id);
    }

    // Update
    public Sneaker updateSneaker(Long id, Sneaker updatedSneaker) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updatedSneaker.getName());
                    existing.setModelNumber(updatedSneaker.getModelNumber());
                    existing.setSize(updatedSneaker.getSize());
                    existing.setCondition(updatedSneaker.getCondition());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Sneaker not found with id: " + id));
    }

    // Delete
    public void deleteSneaker(Long id) {
        repository.deleteById(id);
    }
}
