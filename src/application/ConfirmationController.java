package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ConfirmationController {

	private foAblakController foAblakController;
	
    @FXML                                                                                                             
    private Button btn_Delete_Igen;                                                                                   
                                                                                                                      
    @FXML                                                                                                             
    private Button btn_Delete_Nem;                                                                                    
                                                                                                                      
    public void seConfirmationController(foAblakController ConfirmationController) {                                  
    	this.foAblakController = ConfirmationController;                                                              
    	                                                                                                              
    	}                                                                                                             
                                                                                                                      
    @FXML                                                                                                             
    void btn_Delete_Igen_Click(ActionEvent event) {                                                                   
    	foAblakController.deleteDatabase();                                                                           
    	                                                                                                              
    	Stage stage = (Stage) btn_Delete_Nem.getScene().getWindow();
		
		stage.close();
    }

    @FXML
    void btn_Delete_Nem_Click(ActionEvent event) {
    	Stage stage = (Stage) btn_Delete_Nem.getScene().getWindow();
		
		stage.close();
    }

    @FXML
    void initialize() {
    	btn_Delete_Igen.setCursor(new ImageCursor(new Image("/trash can cursor.png")));
    }

}
