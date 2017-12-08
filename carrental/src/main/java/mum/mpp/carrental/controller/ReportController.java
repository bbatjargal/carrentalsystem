package mum.mpp.carrental.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mum.mpp.carrental.factory.ReportFactory;
import mum.mpp.carrental.model.CarRental;

public class ReportController {

	private Stage stage;
	
	private StackPane pane;
    @FXML
    private TableView<CarRental> tblReport; 

    @FXML private Text messageText;
    
    @FXML private TextField txtSearch;
    
    @FXML private DatePicker dpBeginDate;    
    @FXML private DatePicker dpEndDate;
    
    @FXML private Button btnClearSearch;
    @FXML private Text txtTotalIncome;
    
	@FXML
    public void initialize() {        
    	ObservableList<CarRental> carRentals =  FXCollections.observableArrayList(new ArrayList<CarRental>());    	
    	tblReport.setItems(carRentals);	
    }
	
    public void initData(StackPane pane, Stage stage) {
    	this.pane = pane;
    	this.stage = stage;
    }
    
    @FXML 
    protected void handleSearch(ActionEvent event) { 
    	if(dpBeginDate.getValue() == null || dpEndDate.getValue() == null) {
            messageText.setText("Please enter dates.");
            return;
    	}
    		
    	List<CarRental>  carRentalsList = searchCarRentals(dpBeginDate.getValue(), dpEndDate.getValue());
    	
    	Double totalIncome = 0.0;
    	for(CarRental carRental : carRentalsList) {
    		totalIncome += carRental.getTotalRentCost();
    	}
    	ObservableList<CarRental> carRentals =  FXCollections.observableArrayList(carRentalsList);    	
    	tblReport.setItems(carRentals);	
    	
    	txtTotalIncome.setText(String.valueOf(totalIncome));
    } 
    
    @FXML 
    protected void handlePrint(ActionEvent event) { 
    	 PrinterJob printerJob = PrinterJob.createPrinterJob();
    	 if(printerJob != null) {
    	   if(printerJob.showPrintDialog(stage.getOwner()) && printerJob.printPage(pane))
    	       printerJob.endJob();
    	 }else {
             messageText.setText("No printer found.");    		 
    	 }
    } 
    	
    private List<CarRental> searchCarRentals(LocalDate beginDate, LocalDate endDate){
    	List<CarRental> carRentals = null;
		try {
			carRentals = ReportFactory.search(beginDate, endDate);
		} catch (SQLException e) {
			e.printStackTrace();
            messageText.setText(e.getMessage());
		}
    	return carRentals;
    }
}
