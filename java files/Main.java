package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application{

	public void start(Stage primaryStage){
		GameBoard gb = new GameBoard();
		primaryStage.setScene(new Scene(gb.createContent(primaryStage),600,700));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	

}
