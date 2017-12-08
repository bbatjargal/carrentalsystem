package mum.mpp.carrental.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AboutController {
	
	private Stage stage;
    
    public void initData(Stage stage) {
    	this.stage = stage;
    }
    
    @FXML 
    protected void handleOk(ActionEvent event) {    	  	
		stage.close();	    	
    }    
}
