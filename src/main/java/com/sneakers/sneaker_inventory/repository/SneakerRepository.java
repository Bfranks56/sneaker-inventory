package com.sneakers.sneaker_inventory.repository;

import com.sneakers.sneaker_inventory.model.Sneaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SneakerRepository extends JpaRepository<Sneaker, Long> {
    // That's it! Spring generates all CRUD methods automatically
}