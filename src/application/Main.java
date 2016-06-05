package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class Main extends Application {
	
	public Scene scene;
	public int nbcol = 10;
	public int nblig = 10;
	
	public void start(Stage primaryStage) {
		try {
			Grille g = new Grille(nblig,nbcol);
			GridPane gridGame = g.gridGame(nblig, nbcol);
			BorderPane root = borderPane(gridGame);
			scene = new Scene(root,nblig*100,nbcol*100); 
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public BorderPane borderPane(GridPane gridGame){
		final Label topLabel = new Label("Jouer au demineur!"); 
        topLabel.setStyle("-fx-alignment: center;"); 
        topLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        topLabel.setMinHeight(50); 
        final Label rightLabel = new Label("Droite"); 
        rightLabel.setStyle("-fx-alignment: center;"); 
        rightLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        rightLabel.setMinWidth(50); 
        final Label bottomLabel = new Label("Bas"); 
        bottomLabel.setStyle("-fx-alignment: center;"); 
        bottomLabel.setMinHeight(50); 
        bottomLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        final Label leftLabel = new Label("Gauche"); 
        leftLabel.setStyle("-fx-alignment: center;"); 
        leftLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        leftLabel.setMinWidth(50); 
        
		BorderPane root = new BorderPane();
        root.setTop(topLabel); 
        root.setRight(rightLabel); 
        root.setBottom(bottomLabel); 
        root.setLeft(leftLabel); 
        root.setCenter(gridGame); 
        return root;
	}
	
	
}
