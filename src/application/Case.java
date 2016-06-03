package application;

import java.util.ArrayList;

import javafx.scene.layout.Region;

public class Case extends Region {

	private boolean bombe;
	private int index;	
	private ArrayList<Case> voisins = new ArrayList<Case>();
	
	
	
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
	
	public int nbVoisins(){
		return voisins.size();
}
	
}
