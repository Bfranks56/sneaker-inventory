package com.sneakers.sneaker_inventory.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sneakers")
public class Sneaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;

    @Column(name = "model_number")
    private String modelNumber;

    @Column(nullable = false)
    private Double size;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Condition condition;

        // Default constructor (required by JPA)
        public Sneaker() {}

        // Constructor for creating a new sneaker
        public Sneaker(String name, String modelNumber, Double size, Condition condition) {
            this.name = name;
            this.modelNumber = modelNumber;
            this.size = size;
            this.condition = condition;
        }

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getModelNumber() {
            return modelNumber;
        }

        public void setModelNumber(String modelNumber) {
            this.modelNumber = modelNumber;
        }

        public Double getSize() {
            return size;
        }

        public void setSize(Double size) {
            this.size = size;
        }

        public Condition getCondition() {
            return condition;
        }

        public void setCondition(Condition condition) {
            this.condition = condition;
        }
}
