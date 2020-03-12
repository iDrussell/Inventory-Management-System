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
public class InHouse extends Part {
    
    private int machineId;
    
    public InHouse(int Id,
                   String name,
                   double price,
                   int stock,
                   int min,
                   int max,
                   int machineId) {
        
        super(Id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    
    public void setMachineId(int machineId) {
        /** Set machine ID for part */
        this.machineId = machineId;
    }
    
    public Integer getMachineId() {
        /** Return machine ID */
        
        return this.machineId;
    }
    
//    public static void main(String args[]) {
//        // testing
//        InHouse part = new InHouse(1, "Part 1", 5.00, 20, 10, 30, 11);
//        System.out.println("Machine ID: " + part.getMachineId());
//        System.out.println("Part ID: " + part.getId());
//        System.out.println("Part name: " + part.getName());
//        System.out.println("Get Price: " + part.getPrice());
//        System.out.println("Stock level: " + part.getStock());
//        System.out.println("Min stocke level: " + part.getMin());
//        System.out.println("Max stock level: " + part.getMax());
//       
//    }
    
    
}
