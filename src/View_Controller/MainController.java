/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Product;
import Model.Part;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * FXML Controller class
 *
 * @author dalton
 */

// custom exceptions
class NoResultsException extends Exception {
    public NoResultsException() {
        super("Search returned no results");
    }
}

class ProductPriceException extends Exception {
    public ProductPriceException() {
        super("Product price can't be less than total cost of parts.\n");
    }
}

class InvalidInputException extends Exception {
    public InvalidInputException(){
        super("Failed: Invalid Input");
    }
}

class InventoryOutOfRangeException extends Exception {
    public InventoryOutOfRangeException() {
        super("Inventory level must be between minimum and maximum level.\n");
    }
}

class NoPartsException extends Exception {
    public NoPartsException() {
        super("Product must have at least one part.\n");
    }
}

public class MainController implements Initializable {

    @FXML
    private Button partsSearchButton;
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partTablePartID;
    @FXML
    private TableColumn<Part, String> partTablePartName;
    @FXML
    private TableColumn<Part, Integer> partTableInventoryLevel;
    @FXML
    private TableColumn<Part, Double> partTableCostPerUnit;
    @FXML
    private Button partsAdd;
    @FXML
    private Button partsModify;
    @FXML
    private Button partsDelete;
    @FXML
    private TextField productsTextField;
    @FXML
    private Button productsSearchButton;
    @FXML
    private TableColumn<Product, Integer> productsTableProductID;
    @FXML
    private TableColumn<Product, String> productsTableProductName;
    @FXML
    private TableColumn<Product, Integer> productsTableInventoryLevel;
    @FXML
    private TableColumn<Product, Double> productsTablePricePerUnit;
    @FXML
    private Button productsAddButton;
    @FXML
    private Button productsModifyButton;
    @FXML
    private Button productsDeleteButton;
    @FXML
    private Button mainMenuExit;
    @FXML
    private TableView<Product> productsTableView;
    
    static boolean entered;
    
    static Inventory inventory = new Inventory();
    @FXML
    private ChoiceBox<String> partsSearchChoiceBox;
    @FXML
    private ChoiceBox<String> productsSearchChoiceBox;
    @FXML
    private TextField partsSearchField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        // TODO
        
        //Initialize tableviews
        partTablePartID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partTablePartName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partTableInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partTableCostPerUnit.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        productsTableProductID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productsTableProductName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productsTableInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsTablePricePerUnit.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        //Initialize choice boxes
        partsSearchChoiceBox.setItems(FXCollections.observableArrayList("Part ID", "Part Name"));
        partsSearchChoiceBox.getSelectionModel().select("Part ID");
        productsSearchChoiceBox.setItems(FXCollections.observableArrayList("Product ID", "Product Name"));
        productsSearchChoiceBox.getSelectionModel().select("Product ID");
        
        if(!entered) {
            inventory.addPart(new InHouse(1, "part 1", 5.12, 10, 5, 30, 5001));
            entered = true;
            
            inventory.addProduct(new Product(1, "Magic Wand", 12.30, 10, 8, 20));
            
        }
        partsTableView.setItems(inventory.getAllParts());
        productsTableView.setItems(inventory.getAllProducts());
    }    

    @FXML
    private void partsSearchHandler(ActionEvent event) {
        /** Search Parts on main screen.. */
        
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        Part foundPart;
        try{
            if(partsSearchChoiceBox.getSelectionModel().getSelectedItem().equals("Part ID")) {
                int searchId= 0;
                try {
                    searchId = Integer.parseInt(partsSearchField.getText());
                    foundPart = inventory.lookupPart(searchId);
                    if (foundPart == null) {
                        throw new NoResultsException();
                    }
                    foundParts.add(foundPart);
                    partsTableView.setItems(foundParts);
                    
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Search criterion must be a number.");

                    alert.showAndWait();
                }    
            } else if (partsSearchChoiceBox.getSelectionModel().getSelectedItem().equals("Part Name")) {

                String searchName = partsSearchField.getText();

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
                    partsTableView.setItems(foundParts);
                } 
            }
           // Custom exception for when no results are returned.
        } catch (NoResultsException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void partsAddHandler(ActionEvent event) throws IOException {
        
        
        // Switch to add part scene
        Parent add_part_parent = FXMLLoader.load(getClass().getResource("Add Part.fxml"));
        Scene add_part_scene = new Scene(add_part_parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(add_part_scene);
        stage.show();
        
    }

    @FXML
    private void partsModifyHandler(ActionEvent event) throws IOException {
        //** Switth to Modify Part screen.
        Parent modify;
        
        Part part = partsTableView.getSelectionModel().getSelectedItem();
        if (part != null) {
            
            // Switch to parts modify screen
            FXMLLoader modify_part_loader = new FXMLLoader(getClass().getResource("modifyPart.fxml"));
            modify = modify_part_loader.load();
            Scene modify_scene = new Scene(modify);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(modify_scene);
            stage.show();

            ModifyPartController controller = modify_part_loader.getController();
            // set part to modify
            controller.setPart(part);
             
        } else {
            // If there was no selection
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Please make a selection.");

            alert.showAndWait();
        }
        
        
    }

    @FXML
    private void partsDeleteHandler(ActionEvent event) {
        /** Delete part from parts */
        
        int partId;
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        
        if (selectedPart != null) {
            partId = selectedPart.getId();
            
            //Confirm Delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete...");
            alert.setHeaderText("Deleting...");
            alert.setContentText("Are you sure you want to delete " + selectedPart.getName()+" now ?");        
            alert.showAndWait()

                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> inventory.deletePart(selectedPart));
            
            // automatically change partId
            if (inventory.getNumParts() > 0) {
                for (Part p: inventory.getAllParts()) {
                    if (p.getId() > partId) {
                        p.setId(p.getId()-1);
                    }
                }
            }
            
            //update table
            partsTableView.setItems(inventory.getAllParts());
        // throw alert if no part is selected
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Please make a selection.");

            alert.showAndWait();
        }
    }

    @FXML
    private void productsSearchHandler(ActionEvent event) {
        /** Seerch for Products on main screne */
        
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        Product foundProduct;
        try{
            if(productsSearchChoiceBox.getSelectionModel().getSelectedItem().equals("Product ID")) {
                int searchId;
                try {
                    searchId = Integer.parseInt(productsTextField.getText());
                    foundProduct = inventory.lookupProduct(searchId);
                    if (foundProduct == null) {
                        throw new NoResultsException();
                    }
                    foundProducts.add(foundProduct);
                    productsTableView.setItems(foundProducts);
                    
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("A number is required when searching for Product ID");

                    alert.showAndWait();
                }    
            } else if (productsSearchChoiceBox.getSelectionModel().getSelectedItem().equals("Product Name")) {

                String searchName = productsTextField.getText();

                if((searchName.equals(""))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Empty search for Part Name.");
                    alert.showAndWait();
                } else {
                    foundProducts = inventory.lookupProduct(searchName);              
                    if (foundProducts.isEmpty()) {
                        throw new NoResultsException();
                    }
                    productsTableView.setItems(foundProducts);
                } 
            }
            // No results were returned
        } catch (NoResultsException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void productsAddHandler(ActionEvent event) throws IOException {
        
        // Switch to product add screen
        Parent add_part_parent = FXMLLoader.load(getClass().getResource("addProduct.fxml"));
        Scene add_part_scene = new Scene(add_part_parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(add_part_scene);
        stage.show();
    }

    @FXML
    private void productsModifyHandler(ActionEvent event) throws IOException {
        /** Switch to modify product with the selected product */
        Parent modify;
        
        Product product = productsTableView.getSelectionModel().getSelectedItem();
        if (product != null) {
            
            // Switch to parts modify screen
            FXMLLoader modify_product_loader = new FXMLLoader(getClass().getResource("modifyProduct.fxml"));
            modify = modify_product_loader.load();
            Scene modify_scene = new Scene(modify);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(modify_scene);
            stage.show();

            ModifyProductController controller = modify_product_loader.getController();
                
            controller.setProduct(product);
             
        } else {
            // Nothing was selected.
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Please make a selection.");

            alert.showAndWait();
        }
        
        
    }

    @FXML
    private void productsDeleteHandler(ActionEvent event) {
        
        /** Delete part from parts */
        int productId;
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        
        if (selectedProduct != null) {
            productId = selectedProduct.getId();
            
            //Confirm Delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete...");
            alert.setHeaderText("Deleting...");
            alert.setContentText("Are you sure you want to delete " + selectedProduct.getName()+" now ?");        
            alert.showAndWait()

                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> inventory.deleteProduct(selectedProduct));
            
            // automatically change partId
            if (inventory.getNumProducts() > 0) {
                for (Product p: inventory.getAllProducts()) {
                    if (p.getId() > productId) {
                        p.setId(p.getId()-1);
                    }
                }
            }
            
            //update table
            partsTableView.setItems(inventory.getAllParts());
        // throw alert if no part is selected
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("Please make a selection.");

            alert.showAndWait();
        }
        
    }

    @FXML
    private void mainMenuExitHandler(ActionEvent event) {
        
        //Goodbye
        System.exit(0);
    }

    @FXML
    private void viewAllPartsHandler(ActionEvent event) {
        // Resets Parts table to show all parts
        partsTableView.setItems(inventory.getAllParts());
    }

    @FXML
    private void viewAllProductsHandler(ActionEvent event) {
        // Resets products view
        productsTableView.setItems(inventory.getAllProducts());
    }

    
}
