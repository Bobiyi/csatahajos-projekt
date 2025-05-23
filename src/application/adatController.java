package application;

import java.util.ArrayList;

import application.classes.AblakMode;
import application.classes.Hajo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class adatController {

	private ArrayList<String> fegyverzet = new ArrayList<>();
	
	private foAblakController foAblakController;
	
	@FXML
    private Button btn_Hozz_Szerk;
	
	@FXML
    private Button btn_Megsem;
	
    @FXML
    private TextField txa_Fegyverzet;

    @FXML
    private TextField tx_Nev;

    @FXML
    private TextField tx_Orszag;

    @FXML
    private TextField tx_Osztaly;

    @FXML
    private TextField tx_Tomeg;
    
    public void setHajoListaController(foAblakController hajoListaController) {
    	this.foAblakController = hajoListaController;
    	
    	} 
    
    
    @FXML
    void btn_Hozz_Szerk_Click(ActionEvent event) {
    	
    	if(foAblakController.getMode()==AblakMode.EDIT) {
    		if(hibaGyujto()) {
    		int selected_Hajo_Index=foAblakController.getHajok_Lista().getSelectionModel().getSelectedIndex();
    		
    		String[] fegyverzet_Tomb=txa_Fegyverzet.getText().split(",");
    		
    		for(String fegyver : fegyverzet_Tomb) {
    			fegyverzet.add(fegyver.strip());
    		}
    		
    		Hajo h_Frissitett = new Hajo(
    				tx_Nev.getText().strip(),
    				tx_Orszag.getText().strip(),
    				tx_Osztaly.getText().strip(),
    				Integer.parseInt(tx_Tomeg.getText()),
    				fegyverzet);
    		
    		foAblakController.getHajok().set(foAblakController.getHajok_Lista().getSelectionModel().getSelectedIndex(), h_Frissitett);
    		
    		foAblakController.listaFrissites();
    		
    		foAblakController.getHajok_Lista().getSelectionModel().select(selected_Hajo_Index);
    		
    		Stage stage = (Stage) btn_Megsem.getScene().getWindow();
    		
    		stage.close();
    		}
    	}else {
    			if(hibaGyujto()) {		
    		String[] fegyverzet_Tomb=txa_Fegyverzet.getText().split(",");
    		
    		for(String fegyver : fegyverzet_Tomb) {
    			fegyverzet.add(fegyver.strip());
    		}
    		
    		Hajo h = new Hajo(
    				tx_Nev.getText().strip(),
    				tx_Orszag.getText().strip(),
    				tx_Osztaly.getText().strip(),
    				Integer.parseInt(tx_Tomeg.getText()),
    				fegyverzet);
  
    		foAblakController.getHajok().add(h);
    		
    		foAblakController.listaFrissites();
    		
    		Stage stage = (Stage) btn_Megsem.getScene().getWindow();
    		
    		stage.close();
    			}
    	}
    	}
    	
    
    @FXML
    void btn_Megsem_Click(ActionEvent event) {
    	Stage stage = (Stage) btn_Megsem.getScene().getWindow();
    	stage.close();
    }
    
    @FXML
    public void initialize() {
    }
    
    
    public void setMode(AblakMode mode) {
    	
    	if(mode==AblakMode.EDIT) {
    		
    		Hajo h_Kivalasztott=foAblakController.getKivalsztott_Hajo();
    		
    		txa_Fegyverzet.setText(h_Kivalasztott.getFegyverzet_Leiras());
    		tx_Nev.setText(h_Kivalasztott.getNev());
    		tx_Orszag.setText(h_Kivalasztott.getOrszag());
    		tx_Osztaly.setText(h_Kivalasztott.getOsztaly());
    		tx_Tomeg.setText(Integer.toString(h_Kivalasztott.getTomeg()));
    		}
    	
    }
    
    public void setGomb_Szoveg(String sz) {
    	btn_Hozz_Szerk.setText(sz);
    }
    
    private boolean hibaGyujto() {
    	boolean ervenyes=true;
    	
    	String kiiras="";
    	
    	if(tx_Nev.getText().equals("")) {
    		kiiras += "Hiányzó név";
    		ervenyes = false;
    	}
    	
    	if(tx_Orszag.getText().equals("")) {
    		kiiras += "\nHiányzó ország";
    		ervenyes = false;
    	}
    	if(tx_Osztaly.getText().equals("")) {
    		kiiras += "\nHiányzó osztály";
    		ervenyes = false;
    	}
    	
    	try {
    		int tomeg = Integer.parseInt(tx_Tomeg.getText());
    	} catch (NumberFormatException e) {
    		kiiras += "\nÉrvénytelen tömeg";
    		ervenyes = false;
    	}
    	
    	if(txa_Fegyverzet.getText().equals("")) {
    		kiiras += "\nHiányzó fegyveret";
    		ervenyes = false;
    	}
    	
    	if(!ervenyes) {
    		Alert hiba = new Alert(AlertType.ERROR);
    		
    		hiba.setTitle("Hibás adatok");
    		
    		hiba.setHeaderText("Érvénytelen adatok:");
    		hiba.setContentText(kiiras);
    		
    		hiba.showAndWait();
    	}
    	
    	return ervenyes;
    }
}
