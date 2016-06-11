package VueControleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class ControleurFXML implements Initializable {
	
	@FXML 	private Button lancer;
	@FXML 	private Button quit;
	@FXML 	private Label title;
	@FXML 	private ComboBox<String>	nbbombes;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nbbombes.getItems().removeAll(nbbombes.getItems());
		nbbombes.getItems().addAll("10 bombes", "20 bombes", "30 bombes");
	}
	
	@FXML	private void quitter(ActionEvent event){
		System.out.println("Quitter la partie");
		Platform.exit();
	}
	
	@FXML 	private void lancer(ActionEvent event){
		String result = nbbombes.getSelectionModel().getSelectedItem();
		Platform.exit();
		System.out.println("Lancer la partie ");
	}


	
	
}
