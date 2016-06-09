package Modele;

import java.util.ArrayList;
import java.util.Observable;

import VueControleur.VueControleur;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class MCase {


	private boolean bombe;
	protected boolean clickBombe = false;
	private int index;	
	protected ArrayList<MCase> voisins = new ArrayList<MCase>(); 
	protected ArrayList<MCase> caseCheck = new ArrayList<MCase>(); 
	protected int nbBombesAutour = 0;
	protected boolean verif = false;
	protected boolean drapeau = false;
	protected boolean bloque = false;
	protected boolean chiffre = false;
	
	public MCase(int index){
		this.index=index;
	}
	
	public MCase(int index, ImageView a){
		this.index = index;
		initCase(a);
	}
	
	public void initCase(ImageView a){
		//logoBombe =  new ImageView(new Image(getClass().getResourceAsStream("bombe.jpg")));
		//logoDrapeau =  new ImageView(new Image(getClass().getResourceAsStream("drapeau.jpg")));
		
		Light.Distant light = new Light.Distant();
		light.setAzimuth(-45.0);
		light.setColor(Color.web("#FFCCFF"));
		Lighting li = new Lighting();
		li.setLight(light);
		a.setEffect(li);
	}
	
	public void clicDroitGauche(String s){
		if(s == "SECONDARY"){
				if (!isVerif()){
					if (!isDrapeau()){
						setDrapeau(true);
						//bombesDecouvertes ++;
						setBloque(true);
					}
					else{ 
						setDrapeau(false);
						setBloque(false);
						//bombesDecouvertes --
					}
					//gagner();
				}  
			}

			//clic gauche
			if(s == "PRIMARY"){
				if (!isVerif() && !isBloque()){
					if (isBombe()){
						setClickBombe(true);
						
					} else {				
						compteurBombe();
						setChiffre(true);
						checkCases(this);
						setBloque(true);
					} 
				}
				if (isBloque()){
					//return;
				}
				//gagner();

			}
		}
	

	
	private void setClickBombe(boolean b) {
		clickBombe = b;		
	}

	public void setChiffre(boolean b) {
		chiffre=b;		
	}

	public void compteurBombe(){
		nbBombesAutour = 0;
		for (MCase v : voisins){
			if (v.isBombe()){
				setNbBombesAutour(nbBombesAutour +1);
			}
		}

	}
	
	public void checkCases(MCase c){
		//System.out.println("Je chech la case :"+c.getIndex());
		if (c.getNbBombesAutour() == 0 && !(c.isVerif())){
			c.setVerif(true);
			//System.out.println("Je chech la case:"+c.getIndex());
			/*for (MCase v : c.caseCheck){
			System.out.println(c.getIndex()+" a pour voisin "+v.getIndex());

			}*/
			for (MCase v : c.voisins){
				//System.out.println(v.getIndex());
				if (!(v.isBombe()) && !(v.isChiffre())){
					//System.out.println("on check "+v.getIndex());
					v.compteurBombe();
					v.setChiffre(true);

				}

			}
			for (MCase v : c.caseCheck){
				//System.out.println("on check "+v.getIndex());
				checkCases(v);
			}
		}
	}

	
	public boolean isBloque() {
		return bloque;
	}

	public void setBloque(boolean bloque) {
		this.bloque = bloque;
	}
	
	public ArrayList<MCase> getCaseCheck() {
		return caseCheck;
	}


	public void setCaseCheck(ArrayList<MCase> caseCheck) {
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
	
	public void ajouterVoisin(MCase c){
		this.voisins.add(c);
	}
	
	public void ajouterCheck(MCase c){
		this.caseCheck.add(c);
	}
	
	public ArrayList<MCase> getCasecheck() {
		return caseCheck;
	}

	public void setCasecheck(ArrayList<MCase> casecheck) {
		this.caseCheck = casecheck;
	}

	public String nbBombes(){
		return Integer.toString(nbBombesAutour);
	}
	
	public ArrayList<MCase> getVoisins() {
		return voisins;
	}

	public void setVoisins(ArrayList<MCase> voisins) {
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

	public boolean isChiffre() {
		// TODO Auto-generated method stub
		return chiffre;
	}

	public boolean isClickBombe() {
		// TODO Auto-generated method stub
		return clickBombe;
	}

}
