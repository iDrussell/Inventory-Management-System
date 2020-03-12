/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author dalton
 */
public abstract class Part {
    private int Id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    public Part(int Id, String name, double price, int stock, int min, int max) {
        this.Id = Id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    public void setId(int Id) {
        /**Set part ID */
        
        this.Id = Id;
    }
    
    public void setName(String name) {
        /** Set part name */
        
        this.name = name;
    }
    
    public void setPrice(double price) {
        /** Set part price */
        
        this.price = price;
    }
    
    public void setStock(int stock) {
        /** Set stock level*/
        
        this.stock = stock;
    }
    
    public void setMin(int min) {
        /**Set min level of stock */
        
        this.min = min;
    }
    
    public void setMax(int max) {
        /**Set max level of stock */
        
        
        this.max = max;
    }
    
    public int getId() {
        /**Returns part ID*/
        
        return this.Id;
    }
    
    public String getName() {
        /** Returns part name*/
        
        return this.name;
    }
    
    public double getPrice() {
        /** Return the price of the part */
        
        return this.price;
    }
    
    public int getStock() {
        /** Return the stock of the part */
        
        return this.stock;
    }
    
    public int getMin() {
        /** Returns the minimum stock allowed */
        
        return this.min;
    }
    
    public int getMax() {
        /** Returns the maximum stock allowed */
        
        return this.max;
    }
}
