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
public class Outsourced extends Part {
    
    private String companyName;
    
    public Outsourced(int Id,
                      String name,
                      double price,
                      int stock,
                      int min,
                      int max,
                      String companyName) {
        
        super(Id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    
    public void setCompanyName(String companyName) {
        /** Set company name*/
        
        this.companyName = companyName;
    }
    
    public String getCompanyName() {
        /** Return company name */
        
        return this.companyName;
    }
    
//    public static void main(String args[]) {
//        // testing
//        Outsourced part = new Outsourced(1, "Part 1", 5.26, 20, 10, 30, "ECF Manufacturing");
//        System.out.println("Comapny Name: " + part.getCompanyName());
//        System.out.println("Part ID: " + part.getId());
//        System.out.println("Part name: " + part.getName());
//        System.out.println("Get Price: " + part.getPrice());
//        System.out.println("Stock level: " + part.getStock());
//        System.out.println("Min stocke level: " + part.getMin());
//        System.out.println("Max stock level: " + part.getMax());
//       
//    }
    
}
