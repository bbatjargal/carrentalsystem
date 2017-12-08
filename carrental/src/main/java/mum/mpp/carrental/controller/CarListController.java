package mum.mpp.carrental.controller;

import java.sql.SQLException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mum.mpp.carrental.Main;
import mum.mpp.carrental.enums.CarColorEnum;
import mum.mpp.carrental.enums.UserRoleEnum;
import mum.mpp.carrental.enums.VehicleStatusEnum;
import mum.mpp.carrental.factory.CarFactory;
import mum.mpp.carrental.model.Car;

public class CarListController {
	
    @FXML
    private ListView<String> listViewStudents;
    	
    @FXML
    private BorderPane borderPaneListView;

    @FXML
    private TableView<Car> tblCars; 

    @FXML 
    private Text messageText;
    
    @FXML
    private TextField txtSearch;
    
    @FXML private Button btnClearSearch;
    @FXML private Button btnRentCar;

    @FXML private Button btnCreateCar;
    @FXML private Button btnDeleteCar;
    
    
    @FXML
    private TableColumn<Car, CarColorEnum> columnCarColor;
    @FXML
    private TableColumn<Car, VehicleStatusEnum> columnCarStatus;
    
	@FXML
    public void initialize() {

		if(Main.currentUser.getUserRole().equals(UserRoleEnum.System)) {
			btnRentCar.setVisible(false);	

	    	tblCars.setOnMousePressed(new EventHandler<MouseEvent>() {
	    	    public void handle(MouseEvent event) {
	    	        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
	    	        	Car car = tblCars.getSelectionModel().getSelectedItem();
	    	        	showCarWindow(car);                  
	    	        }
	    	    }
	    	});
	    	
		}else {
			btnCreateCar.setVisible(false);
			btnDeleteCar.setVisible(false);

	    	tblCars.setOnMousePressed(new EventHandler<MouseEvent>() {
	    	    public void handle(MouseEvent event) {
	    	        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
	    	        	Car car = tblCars.getSelectionModel().getSelectedItem();
	    	    		showRentCarWindow(car);           
	    	        }
	    	    }
	    	});
		}
				
		
		btnClearSearch.setVisible(false);
		
    	tblCars.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	columnCarColor.setCellValueFactory(new PropertyValueFactory<Car, CarColorEnum>("color"));
    	columnCarStatus.setCellValueFactory(new PropertyValueFactory<Car, VehicleStatusEnum>("status"));
        
    	ObservableList<Car> cars =  FXCollections.observableArrayList(getCars());
    	
    	tblCars.setItems(cars);	
    	
    	
    }

    @FXML 
    protected void handleCreateCar(ActionEvent event) {
    	
    	try {
    		showCarWindow(null);
		} catch (Exception e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
		}    	
    }
    
    protected void showCarWindow(Car car) {

    	try {
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/car.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        //stage.initStyle(StageStyle.UNDECORATED);
	        stage.setTitle("Car Detail");
	        stage.setScene(new Scene(root1));

	        CarController controller = fxmlLoader.<CarController>getController();
	        controller.initData(car, this, stage);
	        
	        stage.show();
		} catch (Exception e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
		}
    }
    
    @FXML 
    protected void handleDeleteCar(ActionEvent event) {
    	
    	try {
    		Car car = tblCars.getSelectionModel().getSelectedItem();
    		if(car == null) {
                messageText.setText("Select a car which want to delete!");
                return;
    		}
    		CarFactory.deleteCar(car);
            messageText.setText("A car is deleted successfully.");
            refreshCarList();
		} catch (Exception e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
		}    	
    }

    @FXML 
    protected void handleRentCar(ActionEvent event) {  
    	Car car = tblCars.getSelectionModel().getSelectedItem();
		if(car == null) {
            messageText.setText("Select a car which want to rent!");
            return;
		}
		showRentCarWindow(car);
    }
    
    protected void showRentCarWindow(Car car) {


    	try {
    		
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/carRent.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        //stage.initStyle(StageStyle.UNDECORATED);
	        stage.setTitle("Rent Car");
	        stage.setScene(new Scene(root1));

	        CarRentController controller = fxmlLoader.<CarRentController>getController();
	        controller.initData(car, this, stage);
	        
	        stage.show();
		} catch (Exception e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
		}  
    }
    
    @FXML
    protected void handleSearch(ActionEvent event){	
    	try {
    		btnClearSearch.setVisible(true);
        	List<Car> carList = CarFactory.search(txtSearch.getText().trim());
        	ObservableList<Car> cars =  FXCollections.observableArrayList(carList);    	
        	tblCars.setItems(cars);	
		} catch (Exception e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
		}    	
    }
    
    @FXML
    protected void handleClearSearch(ActionEvent event){	
    	try {
    		txtSearch.setText("");
        	ObservableList<Car> cars =  FXCollections.observableArrayList(getCars());    	
        	tblCars.setItems(cars);	
    		btnClearSearch.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
		}    	
    }
    
    public void refreshCarList() {
    	ObservableList<Car> cars =  FXCollections.observableArrayList(getCars());    	
    	tblCars.setItems(cars);	
    }
    	
    private List<Car> getCars(){
    	List<Car> cars = null;
		try {
			cars = CarFactory.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
		}
    	return cars;
    }
}
