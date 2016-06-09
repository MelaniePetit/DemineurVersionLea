package Modele;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import javafx.scene.Parent;

public class MGrille extends Observable {

	int nblignes;
	int nbcolonnes;
	ArrayList<MCase> cases = new ArrayList<MCase>();
	int nbBombes = 10;
	int bombesDecouvertes = 0;
	
	public void maj(int i) {
		// TODO : appeler la case ...
		setChanged();
		notifyObservers();
	}
	
	public MGrille(int nblig, int nbcol){
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
			cases.add(new MCase(i));
			ok++;
		}
		System.out.println("J'ai créé "+ok+" cases! \n");
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
	
	public void caseVoisins(MCase c){
		int j = c.getIndex()%nbcolonnes; //colonne
		int i = (c.getIndex()-j)/nbcolonnes; //ligne
		
		if(j>0){
			MCase gauche = cases.get(c.getIndex()-1);
			c.ajouterVoisin(gauche);
			
			if(i>0){
				MCase diagHautGauche = cases.get(c.getIndex()-nbcolonnes-1);
				c.ajouterVoisin(diagHautGauche);
			}
		}
		
		if (i>0) {
			MCase haut = cases.get(c.getIndex()-nbcolonnes);
			c.ajouterVoisin(haut);
			
			if(j<nbcolonnes-1){
				MCase diagHautDroite = cases.get(c.getIndex()-nbcolonnes+1);
				c.ajouterVoisin(diagHautDroite);
			}
		}
		
		if (i< nblignes-1){
			MCase bas = cases.get(c.getIndex()+nbcolonnes);
			c.ajouterVoisin(bas);
			
			if (j<nbcolonnes-1){
				MCase diagBasDroite = cases.get(c.getIndex()+nbcolonnes+1);
				c.ajouterVoisin(diagBasDroite);
			}
			if (j>0){
				MCase diagBasGauche = cases.get(c.getIndex()+nbcolonnes-1);
				c.ajouterVoisin(diagBasGauche);

			}

		}
		
		if (j<nbcolonnes-1){
			MCase droite = cases.get(c.getIndex()+1);
			c.ajouterVoisin(droite);
		}

	}

	public void compteurBombe(MCase c){
		c.nbBombesAutour = 0;
		for (MCase v : c.voisins){
			if (v.isBombe()){
				c.setNbBombesAutour(c.nbBombesAutour +1);
			}
		}

	}

	// on recupere les voisins : haut, bas, droite, gauche
	public void listeCheck(MCase c){
		int j = c.getIndex()%nbcolonnes; //colonne
		int i = (c.getIndex()-j)/nbcolonnes; //ligne

		if(j>0){
			MCase gauche = cases.get(c.getIndex()-1);
			c.ajouterCheck(gauche);

		}

		if (i>0) {
			MCase haut = cases.get(c.getIndex()-nbcolonnes);
			c.ajouterCheck(haut);

		}

		if (i< nblignes-1){
			MCase bas = cases.get(c.getIndex()+nbcolonnes);
			c.ajouterCheck(bas);

		}

		if (j<nbcolonnes-1){
			MCase droite = cases.get(c.getIndex()+1);
			c.ajouterCheck(droite);
		}

	}

	//si la case selectionnee n'a pas de bombe autour d'elle, on regarde les cases autours
	public void checkCases(MCase c){
		if (c.nbBombesAutour == 0 && !(c.isVerif())){
			c.setVerif(true);
			listeCheck(c);
			/*for (Case v : c.caseCheck){
			System.out.println(c.getIndex()+" a pour voisin "+v.getIndex());

		}*/
			for (MCase v : c.voisins){
				//System.out.println(v.getIndex());
				if (!(v.isBombe()) && !(v.isVerif())){
					//System.out.println("on check "+v.getIndex());
					compteurBombe(v);
					/*v.afficherNbBombes();
					v.setStyle("-fx-background-color: #9966FF ;");*/
				}

			}
			for (MCase v : c.caseCheck){
				checkCases(v);
			}
		}
		c.setVerif(true);
	}
	
	//getters and setters
	
	public ArrayList<MCase> getCases() {
		return cases;
	}

	public void setCases(ArrayList<MCase> cases) {
		this.cases = cases;
	}

	public int getNbBombes() {
		return nbBombes;
	}

	public void setNbBombes(int nbBombes) {
		this.nbBombes = nbBombes;
	}

	public int getBombesDecouvertes() {
		return bombesDecouvertes;
	}

	public void setBombesDecouvertes(int bombesDecouvertes) {
		this.bombesDecouvertes = bombesDecouvertes;
	}


}
