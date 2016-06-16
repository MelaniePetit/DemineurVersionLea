package Modele;

public class MChrono extends Thread {

	public long tempsInitial;
	public long temps;
	private int secondes=0;
	private int minutes=0;
	
	
	
	public void secondesPlus(){
		if (secondes==60){
			minutes+=1;
			secondes=0;
		} else {
			secondes+=1;
		}
	}
	
	public String affichage(){
		System.out.println(minutes+" : "+secondes);
		return minutes+" : "+secondes;
	}
	
    public void run(){
    	tempsInitial=System.currentTimeMillis();
    	if(temps-tempsInitial>=1000){
    		tempsInitial=temps;
    		secondesPlus();
    	}
    }
    
    
    
}
