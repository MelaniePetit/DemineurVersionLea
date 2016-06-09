package application;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;


public class Grille extends Parent{

	int nblignes;
	int nbcolonnes;
	ArrayList<Case> cases = new ArrayList<Case>();
	int nbBombes = 10;
	int bombesDecouvertes = 0;
	
	public Grille(int nblig, int nbcol){
		nblignes=nblig;
		nbcolonnes=nbcol;
		initialisationListeCases();
		for(int i = 0; i < nbBombes ; i++){
			initialisationBombes();
		}
		for(int i = 0 ; i < cases.size() ; i++){
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
                	   String s = event.getButton().toString(); 
                	  
                	   //clic droit
                	   if(s == "SECONDARY"){
                		   if (!c.isVerif()){
                			   if (!c.drapeau){
                				   c.setDrapeau(true);
                				   bombesDecouvertes ++;
                				   c.logoDrapeau.setVisible(true);
                				   c.setStyle("-fx-background-color: cyan;");
                			   }
                			   else{ 
                				   c.setDrapeau(false);
                				   c.logoDrapeau.setVisible(false);
                				   bombesDecouvertes --;
                				   c.setStyle("-fx-background-color: white;");
                			   }
                    		   //mettre en place les drapeaux
                    		   gagner();
                		   }  
                	   }
                	   
                	   //clic gauche
                	   if(s == "PRIMARY"){
                		   if (!c.isVerif()){
	                		   if (c.isBombe()){
	                    		   //c.setStyle("-fx-background-color: red;");
	                               c.bombe();
	                    		   perdre();
	
	                    	   } else {
	                    		   compteurBombe(c);
	                    		   c.afficherNbBombes();
	                               c.setStyle("-fx-background-color: #9966FF;");
	                               checkCases(c);
	                               gagner();
	                    	   } 
                		   }
                	   }
                   }
               });
            }
        }
        gridGame.setGridLinesVisible(true);
        return gridGame;
	}
	
	public void initialisationBombes(){
		Random r = new Random();
		int caseHasard = r.nextInt(nblignes*nbcolonnes); //Entre 0 et nblignes*nbcolonnes
		if (!cases.get(caseHasard).isBombe()){
			cases.get(caseHasard).setBombe(true);
		} else {
			initialisationBombes();
		}
		System.out.println("J'ai pose une bombe sur la case d'index "+caseHasard);		
	}
	
	public int gagner(){
		if (bombesDecouvertes == nbBombes){  
			for (Case c : cases){
				if (!c.isVerif()){
					System.out.println("bouh");
					return 0;
				}
			}
			System.out.println("GAGNE");
		}
		return 1;
		
	}
	
	public void perdre(){
		//afficher le score 
		// Il y a pas vraiment de score...
		
		//Afficher perdu
		ImageIcon img;
		JOptionPane jop = new JOptionPane();
		String fin = "La partie est terminee \n";
		fin += "\nDommage vous avez fait exploser une bombe !";
		img = new ImageIcon("images/looser.jpg");
        jop.showMessageDialog(null, fin, "Fin de la partie", JOptionPane.INFORMATION_MESSAGE,img); 
		//Empecher de jouer
        
	}
	
	// on ajout a la case tous ces voisins
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
	
	public void compteurBombe(Case c){
		c.nbBombesAutour = 0;
		for (Case v : c.voisins){
			if (v.isBombe()){
				c.setNbBombesAutour(c.nbBombesAutour +1);
			}
		}
		
	}
	
	// on recupere les voisins : haut, bas, droite, gauche
	public void listeCheck(Case c){
		int j = c.getIndex()%nbcolonnes; //colonne
		int i = (c.getIndex()-j)/nbcolonnes; //ligne
		
		if(j>0){
			Case gauche = cases.get(c.getIndex()-1);
			c.ajouterCheck(gauche);
			
		}
		
		if (i>0) {
			Case haut = cases.get(c.getIndex()-nbcolonnes);
			c.ajouterCheck(haut);
			
		}
		
		if (i< nblignes-1){
			Case bas = cases.get(c.getIndex()+nbcolonnes);
			c.ajouterCheck(bas);

		}
		
		if (j<nbcolonnes-1){
			Case droite = cases.get(c.getIndex()+1);
			c.ajouterCheck(droite);
		}

	}
	
	//si la case selectionnee n'a pas de bombe autour d'elle, on regarde les cases autours
	public void checkCases(Case c){
		if (c.nbBombesAutour == 0 && !(c.isVerif())){
			c.setVerif(true);
			listeCheck(c);
			/*for (Case v : c.caseCheck){
				System.out.println(c.getIndex()+" a pour voisin "+v.getIndex());

			}*/
 			for (Case v : c.voisins){
				//System.out.println(v.getIndex());
				if (!(v.isBombe()) && !(v.isVerif())){
					//System.out.println("on check "+v.getIndex());
					compteurBombe(v);
         		   	v.afficherNbBombes();
         		   	v.setStyle("-fx-background-color: #9966FF ;");
				}
				
			}
 			for (Case v : c.caseCheck){
 				checkCases(v);
 			}
		}
		c.setVerif(true);
	}
		
}
