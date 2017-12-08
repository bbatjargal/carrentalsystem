package mum.mpp.carrental.controller.fxml;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mum.mpp.carrental.Main;
import mum.mpp.carrental.controller.AboutController;
import mum.mpp.carrental.controller.CustomerController;
import mum.mpp.carrental.controller.ReportController;
import mum.mpp.carrental.enums.UserRoleEnum;
import mum.mpp.carrental.model.Customer;

public class MainController {
	private Stage stage;
	
	@FXML private StackPane main_right_pane;
	@FXML private Button btnShowProfile;
	
	@FXML private Button btnShowRent;
	@FXML private Button btnShowCustomer;
	@FXML private Button btnShowCar;
	@FXML private Button btnShowReport;
	@FXML private VBox vboxMenu;
	@FXML private ImageView imgLogo;
	
	@FXML
	private void initialize() {
		if(Main.currentUser.getUserRole().equals(UserRoleEnum.System)) {
			btnShowProfile.setVisible(false);
		}else {
			//btnShowRent.setVisible(false);
			//btnShowCustomer.setVisible(false);
			//btnShowReport.setVisible(false);

			//btnShowCar.setVisible(true);
			//btnShowProfile.setVisible(true);
			
			vboxMenu.getChildren().clear();
			
			vboxMenu.getChildren().add(imgLogo);
			vboxMenu.getChildren().add(btnShowCar);
			vboxMenu.getChildren().add(btnShowProfile);
		}
	}
	
    public void initData( Stage stage) {
    	this.stage = stage;
    }
    
	@FXML protected void showRent(ActionEvent event) throws IOException {
		StackPane newLoadedPane = FXMLLoader.load(getClass().getResource("/fxml/rent.fxml") );
		main_right_pane.getChildren().clear();
		main_right_pane.getChildren().add(newLoadedPane);
    }
    
    @FXML protected void showCustomer(ActionEvent event) throws IOException {
    	StackPane newLoadedPane = FXMLLoader.load(getClass().getResource("/fxml/customerList.fxml") );
		main_right_pane.getChildren().clear();
		main_right_pane.getChildren().add(newLoadedPane);
    }
    
    @FXML protected void showCar(ActionEvent event) throws IOException {
    	StackPane newLoadedPane = FXMLLoader.load(getClass().getResource("/fxml/carList.fxml") );
    	main_right_pane.getChildren().clear();
		main_right_pane.getChildren().add(newLoadedPane);
    }
    
    @FXML protected void showReport(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/report.fxml"));
    	StackPane newLoadedPane = fxmlLoader.load();    	
    	main_right_pane.getChildren().clear();
		main_right_pane.getChildren().add(newLoadedPane);

        ReportController controller = fxmlLoader.<ReportController>getController();
        controller.initData(newLoadedPane, stage);
    }
    
    @FXML protected void showProfile(ActionEvent event) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/customerDetail.fxml"));
    	AnchorPane newLoadedPane = fxmlLoader.load();
    	main_right_pane.getChildren().clear();
		main_right_pane.getChildren().add(newLoadedPane);

        CustomerController controller = fxmlLoader.<CustomerController>getController();
        controller.initData(Main.currentUser.getUserRole() == UserRoleEnum.Customer ? (Customer) Main.currentUser : null, null);
    }

    @FXML 
    protected void handleAbout(ActionEvent event) {

    	try {
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/about.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setResizable(false);
	        stage.setTitle("About");
	        stage.setScene(new Scene(root1));	 

	        AboutController controller = fxmlLoader.<AboutController>getController();
	        controller.initData(stage);
	        
	        stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}  	
    }
}
