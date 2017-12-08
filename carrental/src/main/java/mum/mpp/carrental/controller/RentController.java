package mum.mpp.carrental.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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

public class RentController {
	
	private List<CarRental> carRentals;

	private Stage stage;
	
//	@FXML
//	private TextField txtLicenseNumber;
//	
//	@FXML
//	private TextField txtBaseRentCost; 	
	
	@FXML
    private TableView<CarRental> tblRent;
	
	
    @FXML
    public void initialize() throws SQLException {
    	carRentals = CarRentalFactory.selectAll();
    	
    	tblRent.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
    	ObservableList<CarRental> rentals =  FXCollections.observableArrayList(carRentals);
    	
    	tblRent.setItems(rentals);
    }
    
    public void initData(Stage stage) {
    	this.stage = stage;
    }
    
}
