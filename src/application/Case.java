package application;

import java.util.ArrayList;

import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Case extends Region {

	private boolean bombe;
	private int index;	
	protected ArrayList<Case> voisins = new ArrayList<Case>(); 
	protected int nbBombesAutour = 0;
	
	public Case(int index){
		this.index=index;
	}

	public boolean isBombe() {
		return bombe;
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
