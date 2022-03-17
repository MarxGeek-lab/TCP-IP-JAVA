package application;
	
import application.ClassAction.ControllerHome;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	public Stage stageHome;
	@Override
	public void start(Stage primaryStage) {
		stageHome = primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("./FXML/Home.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stageHome.setScene(scene);
			stageHome.setMaximized(false);
			
			ControllerHome control = loader.getController();
			control.setMain(this);
			
			stageHome.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Stage getStage () {
		return stageHome;
	}
	public static void main(String[] args) {
		launch(args);
	}
}
