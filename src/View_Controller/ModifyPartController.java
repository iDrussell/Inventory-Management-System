/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InHouse;
import Model.Part;
import Model.Outsourced;
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


public class ModifyPartController implements Initializable {

    @FXML
    private RadioButton modifyPartInhouseRadioButton;
    @FXML
    private ToggleGroup partInhouseOutsourcedGroup;
    @FXML
    private RadioButton modifyPartOutsourcedRadioButton;
    @FXML
    private TextField modifyPartIDField;
    @FXML
    private TextField modifyPartNameField;
    @FXML
    private TextField modifyPartInvField;
    @FXML
    private TextField modifyPartPriceField;
    @FXML
    private TextField modifyPartMinField;
    @FXML
    private TextField modifyPartMaxField;
    @FXML
    private Button modifyPartCancelButton;
    @FXML
    private Button modifyPartSaveButton;
    
    @FXML
    private Label modifyPartCompanyNameMachineID;
    @FXML
    private TextField modifyPartCompanyNameMachineIDField;
    
    // Part to be modified
    Part part;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifyPartInhouseButtonHandler(ActionEvent event) {
        /* Inhouse radio button handler */
        modifyPartInhouseRadioButton.setSelected(true);
        modifyPartCompanyNameMachineID.setText("Machine ID");
    }

    @FXML
    private void modifyPartOutsourcedButtonHandler(ActionEvent event) {
        /* Outsourced radio button handler */
        modifyPartOutsourcedRadioButton.setSelected(true);
        modifyPartCompanyNameMachineID.setText("Company Name");
    }

    @FXML
    private void modifyPartCancelButtonHandler(ActionEvent event) throws IOException {
        
        // Switch back to main screne
        Parent main_parent = FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene main_scene = new Scene (main_parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(main_scene);
        stage.show();
    }

    @FXML
    private void modifyPartSaveButtonHandler(ActionEvent event) {
        String name;
        String companyName = "";
        StringBuilder error_message = new StringBuilder();
        int inv = 0, min = 0, max = 0, machineId = 0;
        double price = 0.0;
        boolean invalid = false;
        
        name = modifyPartNameField.getText();
        if (name.equals("")) {
            error_message.append("Name field is required.\n");
            invalid = true;
        }
        
        try {
            price = Double.parseDouble(modifyPartPriceField.getText());
        } catch (NumberFormatException e) {
            error_message.append("Price is required and must be a number.\n");
            invalid = true;
        }
        try {
            max = Integer.parseInt(modifyPartMaxField.getText());
            if (max == 0) {
                error_message.append("Max number of parts cannot be zero.\n");
                invalid = true;
            }
        } catch (NumberFormatException e) {
            error_message.append("Max is required and must be a number.\n");
            invalid = true;
        }
        
        try {
            min = Integer.parseInt(modifyPartMinField.getText());
            if (min > max) {
                error_message.append("Min cannot be greater than max.\n");
                invalid = true;
            }
            
        } catch (NumberFormatException e) {
            error_message.append("Min is required and must be a number \n");
            invalid = true;
        }
        
        try {
            inv = Integer.parseInt(modifyPartInvField.getText());
            if (inv > max || inv < min) {
                throw new InventoryOutOfRangeException();
            }
        } catch (NumberFormatException e) {
            error_message.append("Inventory level is required and must be a number.\n");
            invalid = true;
        } catch (InventoryOutOfRangeException e) {
            error_message.append(e.getMessage());
            invalid = true;
        }
        
        if (modifyPartInhouseRadioButton.isSelected()) {
            try {
                machineId = Integer.parseInt(modifyPartCompanyNameMachineIDField.getText());
            } catch (NumberFormatException e) {
                error_message.append("Machine ID is required and must be a number.\n");
                invalid = true;
            }
        } else if (modifyPartOutsourcedRadioButton.isSelected()) {
            companyName = modifyPartCompanyNameMachineIDField.getText();
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
                if (modifyPartInhouseRadioButton.isSelected()) {
                    if(this.part instanceof InHouse) {
                        this.part.setName(name);
                        this.part.setPrice(price);
                        this.part.setStock(inv);
                        this.part.setMin(min);
                        this.part.setMax(max);
                        ((InHouse) this.part).setMachineId(machineId);
                        
                    } else {
                        this.part = new InHouse(this.part.getId(),
                                                          name,
                                                          price,
                                                          inv,
                                                          min,
                                                          max,
                                                          machineId);
                    }
                        
                    
                } else {
                    if(this.part instanceof Outsourced) {
                        this.part.setName(name);
                        this.part.setPrice(price);
                        this.part.setStock(inv);
                        this.part.setMin(min);
                        this.part.setMax(max);
                        ((Outsourced)this.part).setCompanyName(companyName);
                    } else{
                         this.part = new Outsourced(this.part.getId(),
                                                                name,
                                                                price,
                                                                inv,
                                                                min,
                                                                max,
                                                                companyName);
                    }
                    
                       
                    
                }
                
                inventory.updatePart((this.part.getId()-1), this.part);
                
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
    
    // set Fields of part
    private <X extends Part> void setFields(X part) {
        modifyPartIDField.setText(new Integer(part.getId()).toString());
        modifyPartNameField.setText(part.getName());
        modifyPartInvField.setText(new Integer(part.getStock()).toString());
        modifyPartPriceField.setText(new Double(part.getPrice()).toString());
        modifyPartMinField.setText(new Integer(part.getMin()).toString());
        modifyPartMaxField.setText(new Integer(part.getMax()).toString());
    }
    
    public void setPart(Part part) {
        // Set part then populate field
        
        if(part instanceof InHouse) {
            this.part = new InHouse(part.getId(),
                                    part.getName(),
                                    part.getPrice(),
                                    part.getStock(),
                                    part.getMin(),
                                    part.getMax(),
                                    ((InHouse) part).getMachineId());
            modifyPartInhouseRadioButton.setSelected(true);
            modifyPartCompanyNameMachineID.setText("Machine ID");
            modifyPartCompanyNameMachineIDField.setText(new Integer(((InHouse) part).getMachineId()).toString());
            
        } else {
            this.part = new Outsourced(part.getId(),
                                       part.getName(),
                                       part.getPrice(),
                                       part.getStock(),
                                       part.getMin(),
                                       part.getMax(),
                                       ((Outsourced) part).getCompanyName());
            modifyPartOutsourcedRadioButton.setSelected(true);
            modifyPartCompanyNameMachineID.setText("Company Name");
            modifyPartCompanyNameMachineIDField.setText(((Outsourced) part).getCompanyName());
        }
        setFields(this.part);
        
    }
        
        
        

    
}
