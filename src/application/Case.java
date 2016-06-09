package application;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	protected boolean drapeau = false;
	protected  ImageView logoBombe;
	protected  ImageView logoDrapeau;

	public Case(int index){
		this.index = index;
		logoBombe =  new ImageView(new Image(getClass().getResourceAsStream("bombe.jpg")));
		logoDrapeau =  new ImageView(new Image(getClass().getResourceAsStream("drapeau.jpg")));
		
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-45.0);
		light.setColor(Color.web("#FFCCFF"));
		Lighting li = new Lighting();
		li.setLight(light);
		this.setEffect(li);
		drapeau();

	}

	public ArrayList<Case> getCaseCheck() {
		return caseCheck;
	}


	public void setCaseCheck(ArrayList<Case> caseCheck) {
		this.caseCheck = caseCheck;
	}


	public boolean isDrapeau() {
		return drapeau;
	}


	public void setDrapeau(boolean drapeau) {
		this.drapeau = drapeau;
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
		nbVoisin.setFill(Color.BLACK);
		nbVoisin.setX(38);
		nbVoisin.setY(50);
		nbVoisin.setVisible(false);

		
		this.getChildren().add(nbVoisin);//ajout de la lettre de la touche
		if (nbBombesAutour != 0){
			   nbVoisin.setVisible(true);
		   }
		
	}
	
	public void bombe(){
		//this.setStyle("-fx-background-color: red;");
		logoBombe.setFitHeight(90);
		logoBombe.setPreserveRatio(true);
		logoBombe.setVisible(false);
		
		this.getChildren().add(logoBombe);
		if (isBombe()){
			   logoBombe.setVisible(true);
		   }
	    
	}
		
	public void drapeau(){
		logoDrapeau.setFitHeight(100);
		logoDrapeau.setPreserveRatio(true);
		logoDrapeau.setVisible(false);

		this.getChildren().add(logoDrapeau);

	}
}
