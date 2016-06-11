package VueControleur;
	
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

import Modele.MCase;
import Modele.MGrille;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class VueControleur extends Application implements Observer, Initializable {
	
	public int nbcol = 10;
	public int nblig = 10;
	public MGrille g;
	protected ImageView maCase;
	public GridPane gridGame;
	private HashMap<MCase,ImageView> map;
	Parent root;
	Stage stage;
	
	//FXML
	
	@FXML 	private Button lancer;
	@FXML 	private Button quit;
	@FXML 	private Label title;
	@FXML 	private ComboBox<String>	nbbombes;

	public void start(final Stage primaryStage) {
		try {
			Scene scene = bienvenue();
			stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Demineur");
			stage.showAndWait();
			
			stage.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Scene bienvenue(){
			Scene scene=null;

			try {
				// Load root layout from fxml file.
				final URL url = getClass().getResource("../Accueil.fxml");
		        final FXMLLoader loader = new FXMLLoader(url);

				root = (AnchorPane) loader.load();
				scene = new Scene(root);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return scene;
	       
	}
	
	public Scene nouvellePartie(int nbBombes){
		  	map = new HashMap<>();
			g = new MGrille(nblig,nbcol,nbBombes);
			gridGame = gridGame(nblig, nbcol);
		    
			root = borderPane(gridGame);
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());			
			gridGame.setGridLinesVisible(true);
			
			return scene;
	}
	
	public GridPane gridGame(int row, int col){
		final GridPane gridGame = new GridPane(); 

		// Grille 
		for (int i = 0; i < row; i++) {
			final RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setPercentHeight(100/row);
			gridGame.getRowConstraints().add(rowConstraints);
		}

		for (int i = 0; i < col; i++) {
			final ColumnConstraints columnConstraints = new ColumnConstraints();
			columnConstraints.setPercentWidth(100/col);
			gridGame.getColumnConstraints().add(columnConstraints);
		}

		g.addObserver(this);

		// Ajout des boutons
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				MCase c = g.getCases().get(i*col+j);
				ImageView a = new ImageView();
				map.put(c, a); //regroupement de la case et de l'image
				a.setImage(new Image(getClass().getResourceAsStream("caseViolet.jpg")));
				a.setStyle("-fx-background-color: white;");
				a.setFitWidth(80);
				a.setFitHeight(80);               

				gridGame.add(a, j, i);

				a.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						String s = event.getButton().toString(); 
						//clic droit ou gauche

						c.clicDroitGauche(s);
						g.maj(c.getIndex());

					}
				});
				//gridGame.setGridLinesVisible(true);
			}
		}
		return gridGame;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public BorderPane borderPane(GridPane gridGame){
		final Label topLabel = new Label("Jouer au demineur!"); 
        topLabel.setStyle("-fx-alignment: center;"); 
        topLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        topLabel.setMinHeight(50); 
        final Label rightLabel = new Label(); 
        rightLabel.setStyle("-fx-alignment: center;"); 
        rightLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        rightLabel.setMinWidth(50); 
        final Label bottomLabel = new Label("Nombre de bombes : "+g.getNbBombes()+" \nNombre de bombes découvertes : "+g.getBombesDecouvertes()); 
        bottomLabel.setStyle("-fx-alignment: center;"); 
        bottomLabel.setMinHeight(50); 
        bottomLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        final Label leftLabel = new Label(); 
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
	
	
	public Text score(){
		Text score = new Text("Nombre de bombes : "+g.getNbBombes()+" \nNombre de bombes découvertes : "+g.getBombesDecouvertes());
        score.setFont(new Font(25));
        score.setFill(Color.WHITE);
        score.setTranslateX(35);
        return score;
	}
	
	public void perdre(){
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("La partie est terminee !");
		alert.setHeaderText("Dommage vous avez perdu...");
		alert.setContentText("Que voulez-vous faire ?");

		ButtonType buttonTypeOne = new ButtonType("Rejouer");
		ButtonType buttonTypeTwo = new ButtonType("Quitter");

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
		} else if (result.get() == buttonTypeTwo) {
		    Platform.exit();
		}
        
	}
	public void victoire(){
        Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("La partie est terminee !");
		alert.setHeaderText("Felicitation vous avez gagne !!");
		alert.setContentText("Que voulez-vous faire ?");

		ButtonType buttonTypeOne = new ButtonType("Rejouer");
		ButtonType buttonTypeTwo = new ButtonType("Quitter");

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne){
			//rejouer
		} else if (result.get() == buttonTypeTwo) {
		    Platform.exit();
		}
        
	}
	
	
	public void afficherNbBombes(MCase c){
		if (c.getNbBombesAutour() != 0){
		Text nbVoisin = new Text(""+c.getNbBombesAutour());
		
		nbVoisin.setFont(new Font(25));
		nbVoisin.setFill(Color.WHITE);
		nbVoisin.setTranslateX(35);
		
		int j = c.getIndex()%(g.getNbcolonnes()); //colonne
		int i = (c.getIndex()-j)/(g.getNbcolonnes()); //ligne
		
		gridGame.add(nbVoisin, j, i);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		g.setBombesDecouvertes(0);
		for(MCase c : g.getCases()){
			Node i = map.get(c);
			if (c.isDrapeau()){
				g.setBombesDecouvertes(g.getBombesDecouvertes() +1);
				((ImageView) i).setImage(new Image(getClass().getResourceAsStream("drapeauViolet.jpg")));
			} else {
				//supprimer l'autre image??
				g.setBombesDecouvertes(g.getBombesDecouvertes() -1);
				((ImageView) i).setImage(new Image(getClass().getResourceAsStream("caseViolet.jpg")));
			}
			if (c.isChiffre()){
				afficherNbBombes(c);
				((ImageView) i).setImage(new Image(getClass().getResourceAsStream("rose.jpg")));
			}
			if (c.isClickBombe()){
				((ImageView) i).setImage(new Image(getClass().getResourceAsStream("bombeViolette.jpg")));
				perdre();
			}
			if (g.gagnee()){
				victoire();
			}
		}
	
	}
	
	//FXML
	

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nbbombes.getItems().removeAll(nbbombes.getItems());
		nbbombes.getItems().addAll("10 bombes", "20 bombes", "30 bombes");
	}
	
	@FXML	private void quitter(ActionEvent event){
		System.out.println("Quitter la partie");
		  Stage stage = (Stage)quit.getScene().getWindow();
	      stage.close();
	}
	
	@FXML 	private void lancer(ActionEvent event){
		try {
			String result = nbbombes.getSelectionModel().getSelectedItem();
			System.out.println(result);
			int nb=0;
			if (result.equals("10 bombes")){
				nb=10;
			} else if (result.equals("20 bombes")){
				nb=20;
			} else nb=30;
			System.out.println("Lancer la partie ");
			Stage stage = (Stage)lancer.getScene().getWindow();
		    stage.close();
		    
			Scene scene = nouvellePartie(nb);
			Stage stage2 = new Stage();
			stage2.setScene(scene);
			stage2.setTitle("Demineur");
			stage2.show();
		} catch (Exception e){
			
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setTitle("ATTENTION");
			dialog.setHeaderText("Et les bombes alors?");
			dialog.setContentText("Vous n'avez pas choisis le nombre de bombes... \n" +
			"Veuillez recommencer");
			dialog.showAndWait();
			
		}
		

	}



}
