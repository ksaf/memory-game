package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameBoard {
	
	public static final int NUM_OF_PAIRS = 8;
	public static final int NUM_PER_ROW = 4;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 700;
	private Tile selected = null;
	private int clickCount = 2;	
	private int moveCount = 0;

	Text txt2;
	Button restart;
	
	public Tile getSelected() {
		return selected;
	}

	public void setSelected(Tile selected) {
		this.selected = selected;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
		
	public Parent createContent(Stage primaryStage){
		Pane window = new Pane();
		window.setPrefSize(WIDTH, HEIGHT);
		
		//create tiles, shuffle them and put them in a gridpane
		char c = 'A';
		List<Tile> tiles = new ArrayList<>();
		for(int i=0; i<NUM_OF_PAIRS; i++){
			tiles.add(new Tile(c+ ".png", this));
			tiles.add(new Tile(c+ ".png", this));
			c++;
			if(c>'H')c='A';
		}
		Collections.shuffle(tiles);
		
		GridPane tilesArea = new GridPane();
		for(int i=0; i<tiles.size(); i++){
			Tile tile = tiles.get(i);
			tilesArea.add(tile, (i%NUM_PER_ROW), (i/NUM_PER_ROW));
		}
		//create a 100 height area for the move counter and the restart button
		HBox movesPane = new HBox();
		movesPane.setMinHeight(100);
		Text txt1 = new Text("Moves:");
		txt1.setFont(Font.font(30));
		txt2 = new Text(Integer.toString(moveCount));
		txt2.setFont(Font.font(30));
		restart = new Button("Restart");
		restart.setMinSize(200, 80);
		restart.setOnAction(e->newGame(primaryStage));
		movesPane.getChildren().addAll(txt1, txt2, restart);
		movesPane.setSpacing(10);
		movesPane.setAlignment(Pos.CENTER);
		

		movesPane.setTranslateY(WIDTH);
		window.getChildren().addAll(movesPane, tilesArea);
		return window;
	}
	
	public void increaseMoveCount(){
		moveCount++;
		txt2.setText(Integer.toString(moveCount));
	}
	public void newGame(Stage primaryStage){
		moveCount=0;
		primaryStage.setScene(new Scene(createContent(primaryStage)));	
	}

}
