package application;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import application.classes.AblakMode;
import application.classes.Hajo;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class foAblakController {
    
	private static ArrayList<Hajo> hajok = new ArrayList<>();
	
	private static AblakMode mode= null;
	
	@FXML
    private Button btn_DeleteDeletedatabase;
	
    @FXML
    private Button btn_Felvetel;

    @FXML
    private Button btn_Kiiras;

    @FXML
    private Button btn_Szerkeztes;

    @FXML
    private Button btn_Torles;

    @FXML
    private Label lb_Darab;

    @FXML
    private CheckBox chk_Cirkalo;

    @FXML
    private CheckBox chk_Csatahajo;

    @FXML
    private CheckBox chk_Rephordozo;

    @FXML
    private CheckBox chk_Rombolo;

    @FXML
    private CheckBox chk_TengeralattJ;
    
    @FXML
    private ListView<Hajo> lv_Lista;

    @FXML
    private MenuItem nav_Felvetel;

    @FXML
    private MenuItem nav_Kilepes;

    @FXML
    private MenuItem nav_Megnyitas;

    @FXML
    private MenuItem nav_Mentes;
    
    @FXML
    private MenuItem nav_Torles;

    @FXML
    private MenuItem nav_Nevjegy;

    @FXML
    private MenuItem nav_Szerkeztes;

    @FXML
    private TextArea txa_Leiras;

    public ListView<Hajo> getHajok_Lista() {
    	return lv_Lista;
    }
    
    public ArrayList<Hajo> getHajok() {
    	return hajok;
    }
    
    public AblakMode getMode() {
    	return mode;
    } 

    public Hajo getKivalsztott_Hajo() {
    	return lv_Lista.getSelectionModel().getSelectedItem();
    }
	
    
    @FXML
    void btn_Felvetel_Click(ActionEvent event) {
    	mode=AblakMode.ADD;
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdatDialog.fxml"));
    		Pane root = loader.load();
    		Scene scene = new Scene(root,500,400);
    		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
    		Stage hozzadasModal = new Stage();
		
    		hozzadasModal.setScene(scene);
    		hozzadasModal.getIcons().add(new Image("/add hajo.png"));
    		hozzadasModal.setTitle("Csatahajó hozzáadása");
    		hozzadasModal.initModality(Modality.APPLICATION_MODAL);
    		hozzadasModal.setResizable(false);
		
    		adatController adatController = loader.getController();
		

    		adatController.setHajoListaController(this);
    		adatController.setMode(mode);
    		
    		adatController.setGomb_Szoveg("Hajó hozzáadása");
		
    		hozzadasModal.show();
		
		
		} catch (IOException e) {
			e.printStackTrace();
			
			Alert hiba = new Alert(AlertType.ERROR);
			
			hiba.setTitle("Hiba");
			hiba.setHeaderText("Hiba a dialóg megnyitása során.");
			
			hiba.showAndWait();
		}
		
		
		
    }

    @FXML
    void btn_Szerkeztes_Click(ActionEvent event) {
    	
    	if(lv_Lista.getSelectionModel().getSelectedItem()==null) {
    		Alert hiba = new Alert(AlertType.WARNING);
    		
    		hiba.setTitle("Hiba");
    		hiba.setHeaderText("Válasszon ki egy hajót!");
    		
    		hiba.showAndWait();
    		
    	} else {
    	mode=AblakMode.EDIT;
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdatDialog.fxml"));
    		Pane root = loader.load();
    		Scene scene = new Scene(root,500,400);
    		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    		
    		Stage hozzadasModal = new Stage();
    		
    		hozzadasModal.setScene(scene);
    		hozzadasModal.setTitle("Csatahajó szerkeztése");
    		hozzadasModal.getIcons().add(new Image("/edit hajo.png"));
    		hozzadasModal.initModality(Modality.APPLICATION_MODAL);
    		hozzadasModal.setResizable(false);
    		
    		adatController adatController = loader.getController();
    		
    		
    		adatController.setHajoListaController(this);
    		adatController.setMode(mode);
    		
    		adatController.setGomb_Szoveg("Hajó szerkeztése");
    		
    		hozzadasModal.show();
    		
    		
    		
    		} catch (IOException e) {
    			Alert hiba = new Alert(AlertType.ERROR);
    			
    			hiba.setTitle("Hiba");
    			hiba.setHeaderText("Hiba a dialóg megnyitása során.");
    			
    			hiba.showAndWait();
    		}
    	}
    	
    }

    @FXML
    void btn_Torles_Click(ActionEvent event) {
    	
    	Hajo kivalsztott_Hajo = lv_Lista.getSelectionModel().getSelectedItem();
    	
    	lv_Lista.getItems().remove(kivalsztott_Hajo);
    	
    	hajok.remove(lv_Lista.getSelectionModel().getSelectedIndex());
    	
    	listaFrissites();
    }
    
    @FXML
    void btn_Kiiras_Click(ActionEvent event) {
    	JSON_Mentes();
    }
    
    @FXML
    void nav_Megnyitas_Click(ActionEvent event) {

    	
    	FileChooser fc = new FileChooser();
    	
    	ExtensionFilter ef = new ExtensionFilter("Csak szöveges fájlok","*.txt");
    	
    	fc.getExtensionFilters().add(ef);
    	
    	File kivalasztott = fc.showOpenDialog(null);
    	
    	if(kivalasztott!=null) {
    		lv_Lista.getItems().clear();
        	
        	hajok.clear();
        	
        	beolvasas(kivalasztott,lv_Lista);
        	
        	listaFrissites();
        	
        	lb_Darab.setText(Integer.toString(hajok.size())+" db");
        	
    	} else {
    		Alert hiba = new Alert(AlertType.ERROR);
    		
    		hiba.setTitle("Hiba");
    		
    		hiba.setHeaderText("Hiba a beolvasás során!");
    		hiba.showAndWait();
    	}
    	}

    @FXML
    void nav_Nevjegy_Click(ActionEvent event) {

    	Alert nevj=new Alert(AlertType.INFORMATION);
    	
    	nevj.setTitle("Névjegy");
    	
    	nevj.setHeaderText("Csatahajó program");
    	nevj.setContentText("Készítette: Halmai Bence");
    	
    	nevj.showAndWait();
    }

    @FXML
    void nav_Kilepes_Click(ActionEvent event) {

    	Platform.exit();
    }
    
    @FXML
    void btn_DeleteDeletedatabase_Click(ActionEvent event) {
    	lv_Lista.getItems().clear();
    	
    	txa_Leiras.clear();
    	
    	hajok.clear();
    	
    	listaFrissites();
    }
    
    @FXML
    void lv_Lista_Click(MouseEvent event) {
    	if(event.getClickCount()==2) {
    		mode=AblakMode.EDIT;
        	try {
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdatDialog.fxml"));
        		Pane root = loader.load();
        		Scene scene = new Scene(root,500,400);
        		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        		
        		Stage hozzadasModal = new Stage();
        		
        		hozzadasModal.setScene(scene);
        		hozzadasModal.setTitle("Csatahajó szerkeztése");
        		hozzadasModal.getIcons().add(new Image("/edit hajo.png"));
        		hozzadasModal.initModality(Modality.APPLICATION_MODAL);
        		hozzadasModal.setResizable(false);
        		
        		adatController adatController = loader.getController();
        		
        		adatController.setHajoListaController(this);
        		adatController.setMode(mode);
        		
        		adatController.setGomb_Szoveg("Hajó szerkeztése");
        		
        		hozzadasModal.show();
        		
        		
        		
        		} catch (IOException e) {
        			Alert hiba = new Alert(AlertType.ERROR);
        			
        			hiba.setTitle("Hiba");
        			hiba.setHeaderText("Hiba a dialóg megnyitása során.");
        			
        			hiba.showAndWait();
        		}
    	}
    }
    
    @FXML
    void chk_Hajo_Fliter() {	
    	filter(chk_Rombolo,chk_Cirkalo,chk_Csatahajo,chk_Rephordozo,chk_TengeralattJ);
    	
    }
    	
	
    @FXML
    void initialize() {
    	
    	lb_Darab.setText("");
    	
    	chk_Cirkalo.setSelected(true);
    	
    	chk_Rombolo.setSelected(true);
    	
    	chk_Csatahajo.setSelected(true);
    	
    	chk_Rephordozo.setSelected(true);
    	
    	chk_TengeralattJ.setSelected(true);
    	
    	File f = new File("hajok.txt");
    	
    	beolvasas(f,lv_Lista);
    	
    	listaFrissites();
    	
    	lb_Darab.setText(Integer.toString(hajok.size())+" db");
    	
    	lv_Lista.getSelectionModel().selectedItemProperty().addListener((Hajo,oldValue,newValue) ->{
    		
    		if(newValue!= null) {
    			txa_Leiras.setText(newValue.getInfo());
    		}
    	});

    }
    
    
    public void listaFrissites() {
		lv_Lista.getItems().clear();
		
		hajok.sort(new Comparator<Hajo>() {

			@Override
			public int compare(Hajo o1, Hajo o2) {
				return o1.getNev().compareTo(o2.getNev());
			}});
		
		for(Hajo h : hajok) {
			lv_Lista.getItems().add(h);
		}
		
		lb_Darab.setText(Integer.toString(hajok.size())+" db");
		
	}
    
    
    private static void beolvasas(File f, ListView<Hajo> lv_Lista) {
    	if(f!=null) {
    	try {
    		Scanner sc = new Scanner(f);
    		
    		while(sc.hasNext()) {
    			String sor = sc.nextLine();
    			
    			if(!(sor.startsWith("#"))) {
    				if(!(sor.equals(""))) {
    					
    				ArrayList<String> fegyverzet_Array = new ArrayList<>();
    				
    				String[] elemek = sor.split(";");
    				
    				String[] fegyverzet_Tomb = elemek[4].split(",");
    				
    				for(String fegyver : fegyverzet_Tomb) {
    					fegyverzet_Array.add(fegyver.strip());
    				}
    				
    				Hajo hajo = new Hajo(
    						elemek[0].strip(),
    						elemek[1].strip(),
    						elemek[2].strip(),
    						Integer.parseInt(elemek[3].strip()),
    						fegyverzet_Array);
    				
    				System.out.println(hajo.getNev());
    				
    				hajok.add(hajo);	
    			}
    			}
    		}
    		
    		sc.close();
    	} catch(FileNotFoundException e) {
    		e.printStackTrace();
    		
    		Alert hiba = new Alert(AlertType.ERROR);
    		
    		hiba.setTitle("Hiba");
    		
    		hiba.setHeaderText("Hiba a beolvasás során!");
    		hiba.showAndWait();
    	}
    	}
    }
       
    
    private static void JSON_Mentes() {
    	FileChooser fc = new FileChooser();
    	
    	ExtensionFilter ef = new ExtensionFilter("Csak szöveges fájlok",".txt");
    	
    	fc.getExtensionFilters().add(ef);
    	
    	String felhasznalo = System.getProperty("user.home");
    	
    	fc.setInitialDirectory(new File(felhasznalo+"/Desktop"));
    	
    	File kivalsztott = fc.showSaveDialog(null);
    	
    	if(kivalsztott!=null) {
    	
    	try {
    		FileWriter fw = new FileWriter(kivalsztott);
    		
    		fw.write("[");
    		fw.flush();
    		
    		for(Hajo h : hajok) {
    			if(hajok.get(hajok.size()-1)!=h) {
    				
    				fw.write(String.format("{\n"
						+ "	\"HajoNev\":\"%s\",\n"
						+ "	\"HajoOrszag\":\"%s\",\n"
						+ "	\"HajoOsztaly\":\"%s\",\n"
						+ "	\"HajoTomeg\":\"%d\",\n"
						+ "	\"HajoFegyverzet\":\"%s\""
						+ "},\n",h.getNev(),h.getOrszag(),h.getOrszag(),h.getTomeg(),h.getJSONFegyverzet()));
    				fw.flush();
    				
    			} else {
    				fw.write(String.format("{\n"
    						+ "	\"HajoNev\":\"%s\",\n"
    						+ "	\"HajoOrszag\":\"%s\",\n"
    						+ "	\"HajoOsztaly\":\"%s\",\n"
    						+ "	\"HajoTomeg\":\"%d\",\n"
    						+ "	\"HajoFegyverzet\":\"%s\""
    						+ "}\n",h.getNev(),h.getOrszag(),h.getOrszag(),h.getTomeg(),h.getJSONFegyverzet()));
        				fw.flush();
    			}
    			
    			
    		}
  		
    		fw.write("]");
    		fw.flush();
    		
    		fw.close();
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    }
    
    
    private void filter(CheckBox chk_Rombolo, CheckBox chk_Cirkalo, CheckBox chk_Csatahajo, CheckBox chk_Rephordozo, CheckBox chk_TengeralattJ) {
		lv_Lista.getItems().clear();
		
		if(chk_Rombolo.isSelected()) {
			for(Hajo h : hajok) {
				if(h.getOsztaly().toLowerCase().contains("romboló")) {
					lv_Lista.getItems().add(h);
				}
			}
		}
		
		if(chk_Cirkalo.isSelected()) {
			for(Hajo h : hajok) {
				if(h.getOsztaly().toLowerCase().contains("cirkáló")) {
					lv_Lista.getItems().add(h);
				}
			}
		}
		
		if(chk_Csatahajo.isSelected()) {
			for(Hajo h : hajok) {
				if(h.getOsztaly().toLowerCase().contains("csatahajó")) {
					lv_Lista.getItems().add(h);
				}
			}
		}
		
		if(chk_Rephordozo.isSelected()) {
			for(Hajo h : hajok) {
				if(h.getOsztaly().toLowerCase().contains("repülőgép")) {
					lv_Lista.getItems().add(h);
				}
			}
		}
		
		if(chk_TengeralattJ.isSelected()) {
			for(Hajo h : hajok) {
				if(h.getOsztaly().toLowerCase().contains("tengeralattjáró")) {
					lv_Lista.getItems().add(h);
				}
			}
		}
	}
}
