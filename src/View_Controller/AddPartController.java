/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Outsourced;
import Model.Part;
import static View_Controller.MainController.inventory;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dalton
 */
public class AddPartController implements Initializable {

    @FXML
    private RadioButton inhouseButton;
    @FXML
    private ToggleGroup partInhouseOutsourcedGroup;
    @FXML
    private Label addPartCompanyNameMachineID;
    @FXML
    private TextField addPartCompanyNameMachineIDField;
    @FXML
    private RadioButton outsourcedRadioButton;
    @FXML
    private TextField addPartIDField;
    @FXML
    private TextField addPartNameField;
    @FXML
    private TextField addPartInvField;
    @FXML
    private TextField addPartPriceField;
    @FXML
    private TextField addPartMinField;
    @FXML
    private TextField addPartMaxField;
    @FXML
    private Button addPartCancelButton;
    @FXML
    private Button addPartSaveButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        // initialize as in house
        inhouseButton.setSelected(true);
        addPartCompanyNameMachineID.setText("Machine ID");
        addPartCompanyNameMachineIDField.setPromptText("Machine ID");
    }    

    @FXML
    private void inhouseButtonHandler(ActionEvent event) {
        //change field values
        addPartCompanyNameMachineID.setText("Machine ID");
        addPartCompanyNameMachineIDField.setPromptText("Machine ID");
    }

    @FXML
    private void outsourcedButtonHandler(ActionEvent event) {
        // change field values to Outsourced
        addPartCompanyNameMachineID.setText("Company Name");
        addPartCompanyNameMachineIDField.setPromptText("Company Name");
    }

    @FXML
    private void addPartCancelButtonHandler(ActionEvent event) throws IOException {
        
        // Switch back to main screne
        Parent main_parent = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene main_scene = new Scene (main_parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(main_scene);
        stage.show();
    }

    @FXML
    private void addPartSaveButtonHandler(ActionEvent event) {
        /** Adds Part to Parts inventory.
         * There is probably a smoother way to implement all of these
         * input checks.
         */
        String name;
        String companyName = "";
        StringBuilder error_message = new StringBuilder();
        int inv = 0, min = 0, max = 0, machineId = 0;
        double price = 0.0;
        boolean invalid = false;
        
        name = addPartNameField.getText();
        if (name.equals("")) {
            error_message.append("Name field is required.");
            invalid = true;
        }
        
        try {
            price = Double.parseDouble(addPartPriceField.getText());
        } catch (NumberFormatException e) {
            error_message.append("Price is required and must be a number.\n");
            invalid = true;
        }
        try {
            max = Integer.parseInt(addPartMaxField.getText());
            if (max <= 0) {
                error_message.append("Max number of parts must be greater than zero.\n");
                invalid = true;
            }
        } catch (NumberFormatException e) {
            error_message.append("Max is required and must be a number.\n");
            invalid = true;
        }
        
        try {
            min = Integer.parseInt(addPartMinField.getText());
            if (min > max) {
                error_message.append("Min cannot be greater than max.\n");
                invalid = true;
            }
            
        } catch (NumberFormatException e) {
            error_message.append("Min is required and must be a number \n");
            invalid = true;
        }
        
        try {
            inv = Integer.parseInt(addPartInvField.getText());
            if (inv > max || inv < min) {
                throw new InventoryOutOfRangeException();
            }
        } catch (NumberFormatException e) {
            error_message.append("Inventory level is required and must be a number.\n");
            invalid = true;
        } catch(InventoryOutOfRangeException e) {
            error_message.append(e.getMessage());
            invalid = true;
        }
        
        if (inhouseButton.isSelected()) {
            try {
                machineId = Integer.parseInt(addPartCompanyNameMachineIDField.getText());
            } catch (NumberFormatException e) {
                error_message.append("Machine ID is required.\n");
                invalid = true;
            }
        } else if (outsourcedRadioButton.isSelected()) {
            companyName = addPartCompanyNameMachineIDField.getText();
            if (companyName.equals("")) {
                error_message.append("Company name is required.\n");
                invalid= true;
            }
        }
        
        
        if (invalid) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error");
            alert.setContentText(error_message.toString());

            alert.showAndWait();
        }
        
        
        else {
        
            try {
                if (inhouseButton.isSelected()) {
                    InHouse part = new InHouse((inventory.getNumParts()+1),
                                                name,
                                                price,
                                                inv,
                                                min,
                                                max,
                                                machineId);

                    inventory.addPart(part);
                    
                } else {
                    Outsourced part = new Outsourced((inventory.getNumParts()+1),
                                                 name,
                                                 price,
                                                 inv,
                                                 min,
                                                 max,
                                                 companyName);
                    
                    inventory.addPart(part);
                }
                
                
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
                alert.setContentText("Unknown error in adding part");

                alert.showAndWait();
                
            }
        }
            
    }

    
}
