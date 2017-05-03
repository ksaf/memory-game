package application;


import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Tile extends StackPane{
	
	Text text = new Text();
	ImageView imv = new ImageView();
	private String path;
	private GameBoard gb;
	
	public Tile(String path, GameBoard gb) {
		this.path=path;
		this.gb=gb;
		//create a black rectangle on the border of the tile
		Rectangle border = new Rectangle(GameBoard.WIDTH/GameBoard.NUM_PER_ROW,(GameBoard.HEIGHT-100)/GameBoard.NUM_PER_ROW);
		border.setFill(null);
		border.setStroke(Color.BLACK);;
		
		//put an image on the center of the tile
		HBox pictureRegion = new HBox();
		try{
			Image image2 = new Image(Main.class.getResourceAsStream(path));
			imv.setFitHeight((GameBoard.HEIGHT-100)/GameBoard.NUM_PER_ROW - 20);
			imv.setFitWidth(GameBoard.WIDTH/GameBoard.NUM_PER_ROW -20);
			imv.setPreserveRatio(true);
	        imv.setImage(image2);
		} catch(Exception e){}

        pictureRegion.getChildren().add(imv);
        pictureRegion.setAlignment(Pos.CENTER);
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(border,pictureRegion);
		
		//handle mouse click
		setOnMouseClicked(this::handleMouseClick);
		
		//we need the tiles to be closed at the beginning
		close();
	}
	
	public void handleMouseClick(MouseEvent e){
		if(isOpen() || gb.getClickCount() == 0)
			return;	
		gb.setClickCount(gb.getClickCount()-1);
		
		if(gb.getSelected()==null){
			gb.setSelected(this);
			open(()-> {});
		}
		else {
			open(()-> {
				if(!hasSameValue(gb.getSelected())){
					gb.getSelected().close();
					this.close();
				}
				gb.setSelected(null);
				gb.setClickCount(2);;
				gb.increaseMoveCount();
			});
		}	
	}
	
	public void open(Runnable action){
		FadeTransition ft = new FadeTransition(Duration.seconds(0.5),imv);
		ft.setToValue(1);
		ft.setOnFinished(e->action.run());
		ft.play();
	}
	public void close(){
		FadeTransition ft = new FadeTransition(Duration.seconds(0.5),imv);
		ft.setToValue(0);
		ft.play();
	}
	
	public boolean isOpen(){
		return imv.getOpacity()>0;
	}
	
	public boolean hasSameValue(Tile other) {
		return this.path.equals(other.path);
	}
}
