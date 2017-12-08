package mum.mpp.carrental;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mum.mpp.carrental.factory.BaseFactory;

public class Login extends Application {

	public static Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}	
	
	@Override
	public void init() throws Exception {
		super.init();
	}

	@SuppressWarnings("static-access")
	@Override
	public void start(Stage primaryStage) throws Exception {
		BaseFactory.initSessionFactory();
		BaseFactory.loadInitData();

		this.primaryStage = primaryStage;
		Parent root = FXMLLoader.load(getClass()
                .getResource("/fxml/login.fxml"));

		primaryStage.setTitle("Car Rental System");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		BaseFactory.closeSessionFacetory();
		super.stop();	
    	Platform.exit();			
	}	
}
