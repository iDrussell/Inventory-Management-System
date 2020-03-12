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

//Inventory class that holds Parts and Products
public class Inventory {
    //Observable list for parts and products
    private ObservableList<Part> allParts = FXCollections.observableArrayList();
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public void addPart(Part newPart) { 
        /* Add part to Parts collection */
        
        this.allParts.add(newPart);
    }
    
    public void addProduct(Product newProduct) {
        /* Add product to products list */
        
        this.allProducts.add(newProduct);
    }
    
    public Part lookupPart(int partID) {
        /** Look up part by part ID
         * Inputs:
         *      int partID: ID of part you want to find.
         * Return:
         *      Part part: the part with the ID
        */
        
        for(Part p: allParts){
            if(p.getId() == partID) {
                return p;
            }
        }
        return null;
        
        
    }
    
    public Product lookupProduct(int productID) {
        /** Look up product by product ID
         * Inputs:
         *      int productID: ID of product to find.
         * Return:
         *      Product product: product with the ID
        */
        
        for (Product p: allProducts) {
            if(p.getId() == productID) {
                return p;
            }
        }
        return null;
       
    }
    
    public ObservableList<Part> lookupPart(String partName) {
        /** Lookup part by part name
         * Inputs:
         *      String partName: name of part to find
         * Returns:
         *      ObservableList<Part>: List containing parts
         *      that match.
         */
        ObservableList<Part> results = FXCollections.observableArrayList();
        for(Part p: allParts) {
            if(p.getName().toLowerCase().contains(partName.toLowerCase())) {
                results.add(p);
            }
        }
        
        return results;
        
        
    }
    
    public ObservableList<Product> lookupProduct(String productName) {
        /** Lookup product by product name.
         * Inputs:
         *      String productName: name of product
         * Returns:
         *      ObservableList<Product>: List containing products
         *      that match.
         */
        ObservableList<Product> results = FXCollections.observableArrayList();
        for(Product p: allProducts) {
            if(p.getName().toLowerCase().contains(productName.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }
    
    public void updatePart(int index, Part selectedPart) {
        /** Updates  part */       
        Part oldPart = allParts.set(index, selectedPart);
    }
    
    public void updateProduct(int index, Product selectedProduct) {
        /** Modifies product */
        Product oldProduct = allProducts.set(index, selectedProduct);
    }
    
    public void deletePart(Part selectedPart) {
        /** Deletes selected part
         * Inputs:
         *      selectedPart: part to delete
         * Returns:
         *      None
         */
        
        this.allParts.remove(selectedPart);
    }
    
    public void deleteProduct(Product selectedProduct) {
        /**Deletes selected product
         * Inputs:
         *      selectedProduct: product to delete.
         * Returns:
         *      none.
         */
        
        this.allProducts.remove(selectedProduct);
    }
    
    public ObservableList<Part> getAllParts() {
        /**Returns all of the current parts */
        
        return allParts;
    }
    
    public ObservableList<Product> getAllProducts() {
        /** Returns all of the current products*/
        
        return this.allProducts;
    }
    
    //Temp function for use with auto increment
    public int getNumParts() {
        
        return allParts.size();
    }
    
    public int getNumProducts() {
        
        return allProducts.size();
    }
    
    
}
