package application;

import java.util.ArrayList;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Case extends Region {

	private boolean bombe;
	private int index;	
	protected ArrayList<Case> voisins = new ArrayList<Case>(); 
	protected ArrayList<Case> caseCheck = new ArrayList<Case>(); 
	protected int nbBombesAutour = 0;
	protected boolean verif = false;
	
	public Case(int index){
		this.index=index;
	}

	public boolean isBombe() {
		return bombe;
	}

	public int nbVoisins(){
		return voisins.size();
	}
	
	public void setBombe(boolean bombe) {
		this.bombe = bombe;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public void ajouterVoisin(Case c){
		this.voisins.add(c);
	}
	
	public void ajouterCheck(Case c){
		this.caseCheck.add(c);
	}
	
	public ArrayList<Case> getCasecheck() {
		return caseCheck;
	}

	public void setCasecheck(ArrayList<Case> casecheck) {
		this.caseCheck = casecheck;
	}

	public String nbBombes(){
		return Integer.toString(nbBombesAutour);
	}
	
	public ArrayList<Case> getVoisins() {
		return voisins;
	}

	public void setVoisins(ArrayList<Case> voisins) {
		this.voisins = voisins;
	}

	public int getNbBombesAutour() {
		return nbBombesAutour;
	}

	public void setNbBombesAutour(int nbBombesAutour) {
		this.nbBombesAutour = nbBombesAutour;
	}

	public boolean isVerif() {
		return verif;
	}

	public void setVerif(boolean verif) {
		this.verif = verif;
	}

	public void afficherNbBombes(){
		Text nbVoisin = new Text(nbBombes());
		
		nbVoisin.setFont(new Font(25));
		nbVoisin.setFill(Color.WHITE);
		nbVoisin.setX(38);
		nbVoisin.setY(50);
		
		this.getChildren().add(nbVoisin);//ajout de la lettre de la touche
	    nbVoisin.setVisible(true);
	}
}
