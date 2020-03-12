/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author dalton
 */
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    int Id;
    String name;
    double price;
    int stock;
    int min;
    int max;
    
    public Product(int Id, String name, double price, int stock, int min, int max) {
        this.Id = Id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    public void setId(int Id) {
        /** Set product Id */
        this.Id = Id;
        
    }
    
    public void setName(String name) {
        /** Set product name */
        
        this.name = name;
    }
    
    public void setPrice(double price) {
        /** Set product price */
        
        this.price = price;
    }
    
    public void setStock(int stock) {
        /** Set product stock level */
        
        this.stock = stock;
    }
    
    public void setMin(int min) {
        /** Set minimum stock level */
        
        this.min = min;
    }
    
    public void setMax(int max) {
        /*8 set maximum stock level */
        
        this.max = max;
    }
    
    public int getId() {
        /** Return product Id */
        
        return this.Id;
    }
    
    public String getName() {
        /** Return product name */
        
        return this.name;
    }
    
    public double getPrice() {
        /** Return product price */
        
        return this.price;
    }
    
    public int getStock() {
        /** Return current stock level */
        
        return this.stock;
    }
    
    public int getMin() {
        /** Return stock minimum level */
        
        return this.min;
    }
    
    public int getMax() {
        /** Return stock maximum level */
        
        return this.max;
    }
    
    public void addAssociatedPart(Part part) {
        /** Add associated part to part list */
        
        this.associatedParts.add(part);
        
    }
    
    public void deleteAssociatedPart(Part associatedPart) {
        /** Delete associated part */
        this.associatedParts.remove(associatedPart);
    }
    
    public ObservableList<Part> getAllAssociatedParts() {
        /** Return associated parts for product */
        
        return this.associatedParts;
    }
}

