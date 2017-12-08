package mum.mpp.carrental.controller;

import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import mum.mpp.carrental.Main;
import mum.mpp.carrental.enums.CarColorEnum;
import mum.mpp.carrental.enums.CountryCodeEnum;
import mum.mpp.carrental.enums.StateEnum;
import mum.mpp.carrental.enums.VehicleStatusEnum;
import mum.mpp.carrental.factory.CarFactory;
import mum.mpp.carrental.factory.CarRentalFactory;
import mum.mpp.carrental.factory.CustomerFactory;
import mum.mpp.carrental.model.Car;
import mum.mpp.carrental.model.CarRental;
import mum.mpp.carrental.model.Customer;

public class CustomerController {
	
	private Customer cust;
	private CustomerListController parentController;
	
    @FXML 
    private Text messageText;
	
	@FXML
	private TextField txtUserName;
	
	@FXML
	private PasswordField txtPassword; 	

	@FXML
	private TextField txtCustomerName; 
	
    @FXML 
    private TextField txtStreet;
    
    @FXML 
    private TextField txtCity;
    
    @FXML 
    private ComboBox<StateEnum> cboState;
    
    @FXML 
    private TextField txtZip;
    
	@FXML
	private ComboBox<CountryCodeEnum> cboCountry;
    
    @FXML 
    private TextField txtPhoneNumber;
    
    @FXML
    private TableView<CarRental> tblViewCustomerRentals;
    
    @FXML
    private Button btnUpdate;

    @FXML
    private TextField txtSearch;
    
    @FXML
    public void initialize() {
    	this.cust = null;
    	txtUserName.setDisable(false);
    	btnUpdate.setText("Insert");
    	cboState.setItems( FXCollections.observableArrayList(StateEnum.values()));
    	cboCountry.setItems( FXCollections.observableArrayList(CountryCodeEnum.values()));
    }
    
    public void initData(Customer cust, CustomerListController parentController) {
    	if(cust != null) {       
    		txtUserName.setDisable(true);
    		txtUserName.setText(cust.getUserName());
        	txtPassword.setText(cust.getPassword());
        	txtCustomerName.setText(cust.getFullName());
        	txtStreet.setText(cust.getStreet());
        	txtCity.setText(cust.getCity());
        	cboState.getSelectionModel().select(cust.getState());
        	txtZip.setText(String.valueOf(cust.getZip()));
        	cboCountry.getSelectionModel().select(cust.getCountry());
        	txtPhoneNumber.setText(cust.getPhoneNumber());
        	
        	this.cust = cust;
        	btnUpdate.setText("Update");
        	
        	ObservableList<CarRental> carRentals = FXCollections.observableArrayList(this.getCarRentalByCurrentCustomer(this.cust));
        	
        	tblViewCustomerRentals.setItems(carRentals);	
        	tblViewCustomerRentals.setOnMousePressed(new EventHandler<MouseEvent>() {
        	    public void handle(MouseEvent event) {
        	        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
        	        	//CarRental carRental = tblViewCustomerRentals.getSelectionModel().getSelectedItem();
        	        	//showCarRentalWindow(carRental);                  
        	        }
        	    }
        	});
        	
    	}else {
    		this.cust = null;
    		txtUserName.setDisable(false);
    		btnUpdate.setText("Insert");
    	}
    	
    	this.parentController = parentController;
    }
    
    
    @FXML
    public void handleUpdateCustomer() {
    	try {
        	boolean isNew = false;
        	
        	if(this.cust == null)
        		this.cust = new Customer();
        	
        	if( this.cust.getUserId() == null) {
        		isNew = true;      		
        	}
        	
        	cust.setUserName(txtUserName.getText());
        	cust.setPassword(txtPassword.getText());
        	cust.setFullName(txtCustomerName.getText());
        	cust.setStreet(txtStreet.getText());
        	cust.setCity(txtCity.getText());
        	cust.setState(cboState.getValue());
        	cust.setZip(txtZip.getText());
        	cust.setCountry(cboCountry.getValue());
        	cust.setPhoneNumber(txtPhoneNumber.getText());
        	    		
			CustomerFactory.saveCustomer(cust);

    		if(isNew) {
    			messageText.setText("Customer added successfully.");
    		}
    		else {
    			messageText.setText("Customer updated successfully."); 
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
			messageText.setText(e.getMessage());
		} 
    }
    
    private List<CarRental> getCarRentalByCurrentCustomer(Customer cust) {
    	List<CarRental> carRentals = null;
    	
		try {
			carRentals = CarRentalFactory.filterByCustomer(cust.getUserId());
			
		} catch (SQLException e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
            
		}
		
    	return carRentals;
    }
}
