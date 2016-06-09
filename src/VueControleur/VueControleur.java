package VueControleur;
	
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import Modele.MCase;
import Modele.MGrille;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class VueControleur extends Application implements Observer {
	
	public Scene scene;
	public int nbcol = 10;
	public int nblig = 10;
	public MGrille g;
	protected ImageView maCase;
	public GridPane gridGame;
	private HashMap<MCase,ImageView> map;
	
	public void start(Stage primaryStage) {
		try {
			map = new HashMap<>();
			g = new MGrille(nblig,nbcol);
			gridGame = gridGame(nblig, nbcol);
			BorderPane root = borderPane(gridGame);
			
			//root.setCenter(g);
			//FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/text.fxml"));
			//Parent root = (Parent) loader.load();
			// scene = new Scene(root,nblig*100,nbcol*100); 
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			scene = new Scene(root);
			
			gridGame.setGridLinesVisible(true);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
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
				a.setImage(new Image(getClass().getResourceAsStream("case.jpg")));
				a.setStyle("-fx-background-color: white;");
				a.setFitWidth(80);
				a.setFitHeight(80);               

				gridGame.add(a, j, i);

				a.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						String s = event.getButton().toString(); 
						System.out.println("hello");
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
        //final Label bottomLabel = new Label("Nombre de bombes : "+g.getNbBombes()+" \nNombre de bombes découvertes : "+g.getBombesDecouvertes()); 
        //bottomLabel.setStyle("-fx-alignment: center;"); 
        //bottomLabel.setMinHeight(50); 
        //bottomLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        final Label leftLabel = new Label(); 
        leftLabel.setStyle("-fx-alignment: center;"); 
        leftLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        leftLabel.setMinWidth(50); 
        
		BorderPane root = new BorderPane();
        root.setTop(topLabel); 
        root.setRight(rightLabel); 
        root.setBottom(score()); 
        root.setLeft(leftLabel); 
        root.setCenter(gridGame); 
        return root;
	}
	
	public void gagner(){
		if (g.getBombesDecouvertes() == g.getNbBombes()){  // verifier emplacement des bombes
			//Afficher gagné
			ImageIcon img;
			JOptionPane jop = new JOptionPane();
			String fin = "La partie est terminée \n";
			fin += "\n Félicitation vous avez gagné! !";
			img = new ImageIcon("images/winner.jpg");
	        jop.showMessageDialog(null, fin, "Fin de la partie", JOptionPane.INFORMATION_MESSAGE,img); 
			}
				
	}
	
	public Text score(){
		Text score = new Text("Nombre de bombes : "+g.getNbBombes()+" \nNombre de bombes découvertes : "+g.getBombesDecouvertes());
        score.setFont(new Font(25));
        score.setFill(Color.WHITE);
        score.setTranslateX(35);
        return score;
	}
	
	public void perdre(){
		
		//Afficher perdu
		ImageIcon img;
		JOptionPane jop = new JOptionPane();
		String fin = "La partie est terminee \n";
		fin += "\nDommage vous avez fait exploser une bombe !";
		img = new ImageIcon("images/looser.jpg");
        jop.showMessageDialog(null, fin, "Fin de la partie", JOptionPane.INFORMATION_MESSAGE,img); 
		//Rajouter bouton QUITTER et bouton REJOUER
        //Empecher de jouer
        
	}
	public void victoire(){
		//afficher le score 
		// Il y a pas vraiment de score...
		
		//Afficher perdu
		ImageIcon img;
		JOptionPane jop = new JOptionPane();
		String fin = "La partie est terminee \n";
		fin += "\nBravo vous avez gagne !!";
		img = new ImageIcon("images/winner.png");
        jop.showMessageDialog(null, fin, "Fin de la partie", JOptionPane.INFORMATION_MESSAGE,img); 
		//Rajouter bouton QUITTER et bouton REJOUER
        
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

	//inutile !
	public void bombe(MCase c, ImageView logoBombe){
		//this.setStyle("-fx-background-color: red;");
		logoBombe.setFitHeight(90);
		logoBombe.setPreserveRatio(true);
		logoBombe.setVisible(false);
		
		gridGame.getChildren().add(logoBombe);
	    
	}
	
	//inutile ! 
	public void drapeau(MCase c, ImageView logoDrapeau){
		logoDrapeau.setFitHeight(100);
		logoDrapeau.setPreserveRatio(true);
		logoDrapeau.setVisible(false);

		gridGame.getChildren().add(logoDrapeau);

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		g.setBombesDecouvertes(0);
		for(MCase c : g.getCases()){
			Node i = map.get(c);
			if (c.isDrapeau()){
				g.setBombesDecouvertes(g.getBombesDecouvertes() +1);
				((ImageView) i).setImage(new Image(getClass().getResourceAsStream("drapeau.jpg")));
			} else {
				//supprimer l'autre image??
				g.setBombesDecouvertes(g.getBombesDecouvertes() -1);
				((ImageView) i).setImage(new Image(getClass().getResourceAsStream("case.jpg")));
			}
			if (c.isChiffre()){
				afficherNbBombes(c);
				((ImageView) i).setImage(new Image(getClass().getResourceAsStream("carre.png")));
			}
			if (c.isClickBombe()){
				((ImageView) i).setImage(new Image(getClass().getResourceAsStream("bombe.jpg")));
				perdre();
			}
			if (g.gagnee()){
				victoire();
			}
		}
		
		
		//fonction qui dit pour chaque case 
		//
		// gridGame.getChildren() recupere les cases graphiques
		
		// parcourir les cases pour tout rafraichir
		// qui declenche la notif cote vue?
	}
	
	//PROBLEME : 
}
