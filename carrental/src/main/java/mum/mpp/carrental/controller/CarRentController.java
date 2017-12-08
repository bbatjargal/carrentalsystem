package mum.mpp.carrental.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mum.mpp.carrental.Main;
import mum.mpp.carrental.enums.CarColorEnum;
import mum.mpp.carrental.enums.VehicleStatusEnum;
import mum.mpp.carrental.factory.CarFactory;
import mum.mpp.carrental.factory.CarRentalFactory;
import mum.mpp.carrental.model.Car;
import mum.mpp.carrental.model.CarRental;
import mum.mpp.carrental.model.Customer;
import mum.mpp.carrental.rulesets.RuleException;

public class CarRentController {
	
	private Car car;
	
	private CarListController parentController;

	private Stage stage;
	
	@FXML
	private TextField txtLicenseNumber;
	
	@FXML
	private TextField txtBaseRentCost; 	
	
	@FXML
	private ComboBox<CarColorEnum> cmboxState;

	@FXML
	private TextField txtDescription; 
	
    @FXML 
    private Text messageText;
    
    @FXML
    private ListView<String> listViewStudents;

	@FXML
	private DatePicker dpRentDate; 	

	@FXML
	private DatePicker dpReturnDate; 	

	@FXML
	private TextField txtRentCost; 	
	
	
    @FXML
    public void initialize() {
    	car = null;
    	cmboxState.setItems( FXCollections.observableArrayList( CarColorEnum.values()));
    }
    
    public void initData(Car car, CarListController parentController, Stage stage) {
    	if(car != null) {        	
    		this.txtLicenseNumber.setText(car.getLicenseNumber());
    		this.txtBaseRentCost.setText(String.valueOf(car.getBaseRentCost()));
    		this.txtDescription.setText(car.getDescription());
    		this.cmboxState.getSelectionModel().select(car.getColor());
        	this.car = car;
    	}else {
    		this.car = new Car();
    	}
    	this.parentController = parentController;
    	this.stage = stage;
    }

    @FXML 
    protected void handleCancel(ActionEvent event) {   
    	stage.close();
    }
    @FXML 
    protected void handleRentCar(ActionEvent event) {    	  	
    	try {
    		LocalDate rentDate = dpRentDate.getValue();
    		LocalDate returnDate = dpReturnDate.getValue();

        	CarRental carRental = new CarRental((Customer)Main.currentUser, car, rentDate, returnDate);  

			car.setStatus(VehicleStatusEnum.Rented);
        	
			CarRentalFactory.saveAll(Arrays.asList(car, carRental ));
						
    		messageText.setText("You have successfully rented this car."); 

    		parentController.refreshCarList();
    		
		} catch (Exception e) {
			e.printStackTrace();
			messageText.setText(e.getMessage());
		}    	    	
    }
    
}
