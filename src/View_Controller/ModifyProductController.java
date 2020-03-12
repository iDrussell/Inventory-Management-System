/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Part;
import Model.Product;
import static View_Controller.MainController.inventory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dalton
 */
public class ModifyProductController implements Initializable {

    @FXML
    private TextField productName;
    @FXML
    private TextField productIDField;
    @FXML
    private TextField productInv;
    @FXML
    private TextField productPrice;
    @FXML
    private TextField productInvMin;
    @FXML
    private TextField productInvMax;
    @FXML
    private TableView<Part> productAvailablePartsTable;
    @FXML
    private TableColumn<Part, Integer> availablePartsPartID;
    @FXML
    private TableColumn<Part, String> availablePartsPartName;
    @FXML
    private TableColumn<Part, Integer> availablePartsInv;
    @FXML
    private TableColumn<Part, Double> availablePartsPrice;
    @FXML
    private Button addPartToProductButton;
    @FXML
    private TableView<Part> selectedPartsTable;
    @FXML
    private TableColumn<Part, Integer> selectedPartsPartID;
    @FXML
    private TableColumn<Part, String> selectedPartsPartName;
    @FXML
    private TableColumn<Part, Integer> selectedPartsInv;
    @FXML
    private TableColumn<Part, Double> selectedPartsPrice;
    @FXML
    private Button addProductDeletePartButton;
    @FXML
    private TextField availablePartsSearchField;
    @FXML
    private ChoiceBox<String> availablePartsChoiceBox;
    
    Product selectedProduct;
    //ObservableList<Part> selectedParts = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Intialize table views
        availablePartsPartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        availablePartsPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        availablePartsInv.setCellValueFactory(new PropertyValueFactory("Stock"));
        availablePartsPrice.setCellValueFactory(new PropertyValueFactory("Price"));
        
        selectedPartsPartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        selectedPartsPartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        selectedPartsInv.setCellValueFactory(new PropertyValueFactory("Stock"));
        selectedPartsPrice.setCellValueFactory(new PropertyValueFactory("Price"));
        
        //initialize choice box
        availablePartsChoiceBox.setItems(FXCollections.observableArrayList("Part ID", "Part Name"));
        availablePartsChoiceBox.getSelectionModel().select("Part ID");
        
        // add available parts to table view
        productAvailablePartsTable.setItems(inventory.getAllParts());
        
        
        
    }    

    @FXML
    private void addPartToProductHandler(ActionEvent event) {
        //* Adds part to selected parts table
        Part part = productAvailablePartsTable.getSelectionModel().getSelectedItem();
        this.selectedProduct.addAssociatedPart(part);
        selectedPartsTable.setItems(this.selectedProduct.getAllAssociatedParts());
    }


    @FXML
    private void SearchPartsHandler(ActionEvent event) {
        /* Search available parts */
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        Part foundPart;
        try {
            if(availablePartsChoiceBox.getSelectionModel().getSelectedItem().equals("Part ID")) {
                int searchId;
                try {
                    searchId = Integer.parseInt(availablePartsSearchField.getText());
                    foundPart = inventory.lookupPart(searchId);
                    if (foundPart == null) {
                        throw new NoResultsException();
                    }
                    foundParts.add(foundPart);
                    productAvailablePartsTable.setItems(foundParts);
                    
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("A number must be provided when searching by part ID\n.");

                    alert.showAndWait();
                }
            } else if (availablePartsChoiceBox.getSelectionModel().getSelectedItem().equals("Part Name")) {
                String searchName = availablePartsSearchField.getText();
                
                if((searchName.equals(""))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Empty search for Part Name.");
                    alert.showAndWait();
                    
                } else {
                    foundParts = inventory.lookupPart(searchName);
                    if (foundParts.isEmpty()) {
                        throw new NoResultsException();
                    }
                    productAvailablePartsTable.setItems(foundParts);
                    
                }  
                    
            }
            
            
            
        } catch (NoResultsException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void vieawAllAvailablePartsHandler(ActionEvent event) {
        /** Resets available parts table */
        productAvailablePartsTable.setItems(inventory.getAllParts());
    }

    @FXML
    private void DeletePartHandler(ActionEvent event) {
        /* Remove part from selected parts */
        
        Part part = selectedPartsTable.getSelectionModel().getSelectedItem();
        if ( part != null) {
            
            //Confirm Delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Remove...");
            alert.setHeaderText("Removing...");
            alert.setContentText("Are you sure you want to remove " + part.getName()+"?");        
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> this.selectedProduct.deleteAssociatedPart(part));
        }
        
        // update table
        selectedPartsTable.setItems(this.selectedProduct.getAllAssociatedParts());
    }

    @FXML
    private void CancelHandler(ActionEvent event) throws IOException {
        
        // Switch back to main screne
        Parent main_parent = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene main_scene = new Scene (main_parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(main_scene);
        stage.show();
    }

    @FXML
    private void SaveHandler(ActionEvent event) {
        /**Save product to inventory */
        String name;
        StringBuilder error_message = new StringBuilder();
        int inv = 0, min = 0, max = 0;
        double price = 0.0;
        boolean invalid = false;
        try{
        
            name = productName.getText();
            if (name.equals("")) {
                error_message.append("Product Name is required.\n");
                invalid = true;
                
            }
            
            try {
                double totalPartsCost = 0.0;
                price = Double.parseDouble(productPrice.getText());
                for(Part p : this.selectedProduct.getAllAssociatedParts()) {
                    totalPartsCost = totalPartsCost + p.getPrice();
                }
                if(price < totalPartsCost) {
                    throw new ProductPriceException();
                }
                
            } catch (NumberFormatException e) {
                error_message.append("Price is required and must be a number.\n");
                invalid = true;
            } catch (ProductPriceException e) {
                error_message.append(e.getMessage());
                invalid = true;
            }
            
            try {
                max = Integer.parseInt(productInvMax.getText());
                if (max <= 0) {
                    error_message.append("Max number of product must be greater than zero.");
                    invalid = true;
                }
            } catch (NumberFormatException e) {
                error_message.append("Max is required and must be a number.\n");
                invalid = true;
            }
            
            try {
                min = Integer.parseInt(productInvMin.getText());
                if (min > max) {
                    error_message.append("Minimum inventory cannot be greater than maximum.\n");
                    invalid = true;
                }
            } catch(NumberFormatException e) {
                error_message.append("Minimum inventory is required and must be a number.\n");
                invalid = true;
            }
            
            try {
                inv = Integer.parseInt(productInv.getText());
                if(inv > max || inv < min) {
                    throw new InventoryOutOfRangeException();
                }
            } catch (NumberFormatException e) {
                error_message.append("Inventory level is required and must be a number.\n");
                invalid = true;
            } catch (InventoryOutOfRangeException e) {
                error_message.append(e.getMessage());
                invalid = true;
            }
            
            if(this.selectedProduct.getAllAssociatedParts().isEmpty()) {
                error_message.append("Product needs at least one part.\n");
                invalid = true;
            }
            
            if(invalid) {
                throw new InvalidInputException();
            }
            
            try {
                //The only place the set functions are used
                // is in the Product/part modifier.
                // they seem redundent when the
                // updatePart/udateProduct functions take in
                // an index and the Part to replace it with
                // You could just use allParts.set(index, Part)
                
                this.selectedProduct.setName(name);
                this.selectedProduct.setPrice(price);
                this.selectedProduct.setStock(inv);
                this.selectedProduct.setMin(min);
                this.selectedProduct.setMax(max);
          
                
                inventory.updateProduct((this.selectedProduct.getId()-1),this.selectedProduct);
                
                // Switch back to main screne after add
                Parent main_parent = FXMLLoader.load(getClass().getResource("main.fxml"));
                Scene main_scene = new Scene (main_parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(main_scene);
                stage.show();
                
                
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Unknown error in adding product");

                alert.showAndWait();
            }
            
            
        } catch(InvalidInputException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(e.getMessage());
            alert.setContentText("Details: \n" + error_message.toString());

            alert.showAndWait();
        }
    }
    
    public void setProduct(Product product) {
        /**Sets product for modify
         * Decided to create a temp part.
         * 
         */
        this.selectedProduct = new Product(product.getId(),
                                           product.getName(),
                                           product.getPrice(),
                                           product.getStock(),
                                           product.getMin(),
                                           product.getMax());
        
        productIDField.setText(Integer.toString(this.selectedProduct.getId()));
        productName.setText(this.selectedProduct.getName());
        productInv.setText(Integer.toString(this.selectedProduct.getStock()));
        productPrice.setText(Double.toString(this.selectedProduct.getPrice()));
        productInvMin.setText(Integer.toString(this.selectedProduct.getMin()));
        productInvMax.setText(Integer.toString(this.selectedProduct.getMax()));
        
        for(Part p: product.getAllAssociatedParts()) {
            this.selectedProduct.addAssociatedPart(p);
        }
        selectedPartsTable.setItems(this.selectedProduct.getAllAssociatedParts());
        
    }
    
}
