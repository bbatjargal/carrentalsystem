package mum.mpp.carrental.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mum.mpp.carrental.enums.CarColorEnum;
import mum.mpp.carrental.enums.VehicleStatusEnum;
import mum.mpp.carrental.factory.CarFactory;
import mum.mpp.carrental.factory.CarRentalFactory;
import mum.mpp.carrental.model.Car;
import mum.mpp.carrental.model.CarRental;
import mum.mpp.carrental.rulesets.RuleException;

public class CarController {
	
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
    protected void handleSaveCar(ActionEvent event) {    	  	
    	try {
    		costFloatRule();

        	String licenseNumber = this.txtLicenseNumber.getText();
        	Double baseRentCost = Double.valueOf(this.txtBaseRentCost.getText());
        	
        	boolean isNew = false;
        	if(car.getVehicleId() == null) {
        		isNew = true;
        		car.setStatus(VehicleStatusEnum.Available);        		
        	}
        	
        	car.setLicenseNumber(licenseNumber);
        	car.setBaseRentCost(baseRentCost);
        	car.setColor(cmboxState.getValue());
        	car.setDescription(txtDescription.getText());
        	    		
			CarFactory.saveCar(car);

    		if(isNew) 
    			messageText.setText("Car added successfully.");
    		else
    			messageText.setText("Car updated successfully."); 

    		parentController.refreshCarList();
    		
		} catch (Exception e) {
			e.printStackTrace();
			messageText.setText(e.getMessage());
		}    	    	
    }


	private void costFloatRule() throws RuleException {
		String baseCost = this.txtBaseRentCost.getText();
	
		if(!Pattern.matches("[0-9]*\\.[0-9]{2}", baseCost))
			throw new RuleException("Cost must be a float with two decimal places!");		
	}

    private List<CarRental> getCarRentalByCurrentCar(Car car) {
    	List<CarRental> carRentals = null;
    	
		try {
			carRentals = CarRentalFactory.filterByCustomer(car.getVehicleId());
			
		} catch (SQLException e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
            
		}
		
    	return carRentals;
    }
    
}
