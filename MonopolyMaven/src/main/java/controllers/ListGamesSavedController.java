package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Game;

public class ListGamesSavedController {
	@FXML
	private AnchorPane listGamesLayout;
	@FXML
	private Button goBackButton;

	@FXML
	private ListView<Game> listProfiles;
	static Scene previousScene;

	@FXML
	private void initialize() {

	}


@FXML
public void goBack(ActionEvent event) {
    try {
        // Verifica si la escena anterior está configurada
        if (previousScene != null) {
            Stage stage = (Stage) goBackButton.getScene().getWindow();
            stage.setScene(previousScene); // Cambia a la escena anterior
            stage.show();
        } else {
            System.out.println("No previous scene set.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
