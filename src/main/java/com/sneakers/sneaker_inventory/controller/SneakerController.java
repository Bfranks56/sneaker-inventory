package com.sneakers.sneaker_inventory.controller;

import com.sneakers.sneaker_inventory.model.Sneaker;
import com.sneakers.sneaker_inventory.service.SneakerService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sneakers")
public class SneakerController {

    @Autowired
    private SneakerService service;

    // Post
    @PostMapping
    public ResponseEntity<Sneaker> createSneaker(@RequestBody Sneaker sneaker) {
        Sneaker created = service.createSneaker(sneaker);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

//    Get
    @GetMapping
    public ResponseEntity<List<Sneaker>> getAllSneakers() {
        List<Sneaker> sneakers = service.getAllSneakers();
        return ResponseEntity.ok(sneakers);
    }

//    Get - get one sneaker by id
    @GetMapping("/{id}")
    public ResponseEntity<Sneaker> getSneakerById(@PathVariable Long id) {
        return service.getSneakerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    Put - Update Sneaker
    @PutMapping("/{id}")
    public ResponseEntity<Sneaker> updateSneaker(@PathVariable Long id, @RequestBody Sneaker sneaker) {
        try {
            Sneaker updated = service.updateSneaker(id, sneaker);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

//    Delete - delete sneaker
    @DeleteMapping("/{id}")
    public ResponseEntity<Sneaker> deleteSneaker(@PathVariable Long id) {
        service.deleteSneaker(id);
        return ResponseEntity.noContent().build();
    }
}
