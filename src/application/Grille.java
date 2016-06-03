package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class Grille {

	int nblignes;
	int nbcolonnes;
	ArrayList<Case> cases = new ArrayList<Case>();
	int nbBombes=5;
	
	public Grille(int nblig, int nbcol){
		nblignes=nblig;
		nbcolonnes=nbcol;
		initialisationListeCases();
		for(int i = 0; i < nbBombes ; i++){
			initialisationBombes();
		}
		for(int i = 0 ; i< cases.size() ; i++){
			caseVoisins(cases.get(i));
		}
		
	}
	
	public void initialisationListeCases(){
		int ok=0;
		for(int i=0;i<nblignes*nbcolonnes;i++){
			//On ajoute les cases avec leurs index
			cases.add(new Case(i));
			ok++;
		}
		System.out.println("J'ai créé "+ok+" cases! \n");
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

        // Ajout des boutons
        for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                    		   Case c = cases.get(i*nbcolonnes+j);
                               c.setStyle("-fx-background-color: white;");
                               //t.setWrappingWidth(100);
                               gridGame.add(c, j, i);
                               
                               c.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                   
                                   @Override
                                   public void handle(MouseEvent event) {
                                	   if (c.isBombe()){
                                           c.setStyle("-fx-background-color: red;");
                                           theEnd();

                                	   } else {
                                		   // ECRIRE LE NOMBRE DE BOMBES
                                		   String nbVoisins = ""+c.nbVoisins();
                                		   Text nb = new Text(nbVoisins);
                                		   //gridGame.add(nb, j, i); Je veux ajouter le texte :'(
                                           c.setStyle("-fx-background-color: blue;");
                                	   }
                                   }
                                   
                               });
                    }
        }
        gridGame.setGridLinesVisible(true);
        return gridGame;
	}
	
	public void initialisationBombes(){
		int nbCases = cases.size();
		Random r = new Random();
		int caseHasard = r.nextInt(nblignes*nbcolonnes); //Entre 0 et nblignes*nbcolonnes
		if (!cases.get(caseHasard).isBombe()){
			cases.get(caseHasard).setBombe(true);
		} else {
			initialisationBombes();
		}
		System.out.println("J'ai posé une bombe sur la case d'index "+caseHasard);		
	}
	
	public void theEnd() {
		//afficher le score
		//Afficher perdu
		//Empecher de jouer
	}
	
	public void caseVoisins(Case c){
			int j = c.getIndex()%nbcolonnes; //colonne
			int i = (c.getIndex()-j)/nbcolonnes; //ligne
			
			if(j>0){
				Case gauche = cases.get(c.getIndex()-1);
				c.ajouterVoisin(gauche);
				
				if(i>0){
					Case diagHautGauche = cases.get(c.getIndex()-nbcolonnes-1);
					c.ajouterVoisin(diagHautGauche);
				}
			}
			
			if (i>0) {
				Case haut = cases.get(c.getIndex()-nbcolonnes);
				c.ajouterVoisin(haut);
				
				if(j<nbcolonnes-1){
					Case diagHautDroite = cases.get(c.getIndex()-nbcolonnes+1);
					c.ajouterVoisin(diagHautDroite);
				}
			}
			
			if (i< nblignes-1){
				Case bas = cases.get(c.getIndex()+nbcolonnes);
				c.ajouterVoisin(bas);
				
				if (j<nbcolonnes-1){
					Case diagBasDroite = cases.get(c.getIndex()+nbcolonnes+1);
					c.ajouterVoisin(diagBasDroite);
				}
				if (j>0){
					Case diagBasGauche = cases.get(c.getIndex()+nbcolonnes-1);
					c.ajouterVoisin(diagBasGauche);

				}

			}
			
			if (j<nbcolonnes-1){
				Case droite = cases.get(c.getIndex()+1);
				c.ajouterVoisin(droite);
			}

	}
	
}
