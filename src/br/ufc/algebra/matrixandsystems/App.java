package br.ufc.algebra.matrixandsystems;

import br.ufc.algebra.matrixandsystems.view.fxml.FXMLResources;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
	
	private static Scene scene;
    private static Stage stage;
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(FXMLResources.LOGIN);
        primaryStage.setTitle("Matrix And Systems");
        scene = new Scene(root, 800, 700);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
        stage = primaryStage;
	}
	
	public static void main(String [] args){
		launch(args);
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		App.scene = scene;
	}

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		App.stage = stage;
	}

}
