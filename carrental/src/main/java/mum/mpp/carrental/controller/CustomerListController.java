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
import mum.mpp.carrental.enums.CarColorEnum;
import mum.mpp.carrental.enums.VehicleStatusEnum;
import mum.mpp.carrental.factory.CustomerFactory;
import mum.mpp.carrental.model.Car;
import mum.mpp.carrental.model.Customer;

public class CustomerListController {  
	
	
    @FXML
    private ListView<String> listViewStudents;
    	
    @FXML
    private BorderPane borderPaneListView;

    @FXML
    private TableView<Customer> tblCustomers; 

    @FXML 
    private Text messageText;
    
    @FXML
    private TextField txtSearch;
    
    @FXML
    private Button btnClearSearch;
    
	@FXML
    public void initialize() {

		btnClearSearch.setVisible(false);
		
		tblCustomers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
    	ObservableList<Customer> customers =  FXCollections.observableArrayList(getCustomers());
    	
    	tblCustomers.setItems(customers);	
    	tblCustomers.setOnMousePressed(new EventHandler<MouseEvent>() {
    	    public void handle(MouseEvent event) {
    	        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
    	        	Customer customer = tblCustomers.getSelectionModel().getSelectedItem();
    	        	showCustomerWindow(customer);                  
    	        }
    	    }
    	});
    }

    @FXML 
    protected void handleCreateCustomer(ActionEvent event) {
    	
    	try {
    		showCustomerWindow(null);
		} catch (Exception e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
		}    	
    }
    
    protected void showCustomerWindow(Customer customer) {

    	try {
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/customerDetail.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        //stage.initStyle(StageStyle.UNDECORATED);
	        stage.setTitle("Customer Detail");
	        stage.setScene(new Scene(root1));

	        CustomerController controller = fxmlLoader.<CustomerController>getController();
	        controller.initData(customer, this);	        
	        
	        stage.show();
            
            
		} catch (Exception e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
		}
    }
    
    @FXML 
    protected void handleDeleteCustomer(ActionEvent event) {
    	
    	try {
    		Customer customer = tblCustomers.getSelectionModel().getSelectedItem();
    		if(customer == null) {
                messageText.setText("Select a customer which want to delete!");
                return;
    		}
    		CustomerFactory.deleteCustomer(customer);
            messageText.setText("A customer is deleted successfully.");
            refreshCustomerList();
		} catch (Exception e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
		}    	
    }
    
    @FXML
    protected void handleSearch(ActionEvent event){	
    	try {
    		btnClearSearch.setVisible(true);
        	List<Customer> customerList = CustomerFactory.search(txtSearch.getText().trim());
        	ObservableList<Customer> customers =  FXCollections.observableArrayList(customerList);    	
        	tblCustomers.setItems(customers);	
		} catch (Exception e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
		}    	
    }
    
    @FXML
    protected void handleClearSearch(ActionEvent event){	
    	try {
    		txtSearch.setText("");
        	ObservableList<Customer> customers =  FXCollections.observableArrayList(getCustomers());    	
        	tblCustomers.setItems(customers);	
    		btnClearSearch.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
		}    	
    }
    
    public void refreshCustomerList() {
    	ObservableList<Customer> customers =  FXCollections.observableArrayList(getCustomers());    	
    	tblCustomers.setItems(customers);	
    }
    	
    private List<Customer> getCustomers(){
    	List<Customer> customers = null;
		try {
			customers = CustomerFactory.selectAll();
		} catch (SQLException e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
		}
    	return customers;
    }
}
