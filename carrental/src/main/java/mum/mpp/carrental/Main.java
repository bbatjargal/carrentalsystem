package mum.mpp.carrental;

import org.hibernate.SessionFactory;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import mum.mpp.carrental.controller.ReportController;
import mum.mpp.carrental.controller.fxml.MainController;
import mum.mpp.carrental.factory.BaseFactory;
import mum.mpp.carrental.model.User;

public class Main extends Application{
	
	public static Stage primaryStage = new Stage();
	@SuppressWarnings("unused")
	private static SessionFactory sessionFactory = null;
	
	public static User currentUser;

	@Override
	public void init() throws Exception {
		super.init();
	}
	
	@Override
	@SuppressWarnings("static-access")
	public void start(Stage primaryStage) throws Exception {
		BaseFactory.initSessionFactory();
		this.primaryStage = primaryStage;

    	FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/fxml/main.fxml"));
		Parent root = fxmlLoader.load();

		primaryStage.setTitle("Car Rental System");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		

        MainController controller = fxmlLoader.<MainController>getController();
        controller.initData(primaryStage);

	}

	@Override
	public void stop() throws Exception {
		BaseFactory.closeSessionFacetory();
		super.stop();	
		Platform.exit();
	}
}
