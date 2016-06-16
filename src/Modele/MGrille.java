package Modele;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class MGrille extends Observable {

	int nblignes;
	int nbcolonnes;
	ArrayList<MCase> cases = new ArrayList<MCase>();
	int drapeauxPosés = 0;
	int nbBombes;
	
	public void clicEvent(String s, MCase mCase) {
		mCase.clicDroitGauche(s);
		setChanged();
		notifyObservers();
	}
	
	public MGrille(int nblig, int nbcol, int nbBombes){
		nblignes=nblig;
		nbcolonnes=nbcol;
		this.nbBombes=nbBombes;
		initialisationListeCases();
		for(int i = 0; i < nbBombes ; i++){
			initialisationBombes();
		}
		for(int i = 0 ; i < cases.size() ; i++){
			caseVoisins(cases.get(i));
			listeCheck(cases.get(i));
		}
		
	}
	
	public void initialisationListeCases(){
		for(int i=0;i<nblignes*nbcolonnes;i++){
			//On ajoute les cases avec leurs index
			cases.add(new MCase(i));
		}
	}
	
	public void initialisationBombes(){
		Random r = new Random();
		int caseHasard = r.nextInt(nblignes*nbcolonnes); //Entre 0 et nblignes*nbcolonnes
		if (!cases.get(caseHasard).isBombe()){
			cases.get(caseHasard).setBombe(true);
		} else {
			initialisationBombes();
		}
		//System.out.println("J'ai pose une bombe sur la case d'index "+caseHasard);		
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
	
	public boolean gagnee(){
		int compt = 0;
		for (MCase v : cases){
			if (v.isDrapeau() == true && v.isBombe() == true){
				compt ++;

			}
		}
		if (compt == nbBombes){
			return true;
		}
		return false;
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

	public int getDrapeauxPosés() {
		return drapeauxPosés;
	}

	public void setDrapeauxPosés(int drapeau) {
		this.drapeauxPosés = drapeau;
	}

	public int getNblignes() {
		return nblignes;
	}

	public void setNblignes(int nblignes) {
		this.nblignes = nblignes;
	}

	public int getNbcolonnes() {
		return nbcolonnes;
	}

	public void setNbcolonnes(int nbcolonnes) {
		this.nbcolonnes = nbcolonnes;
	}

}
