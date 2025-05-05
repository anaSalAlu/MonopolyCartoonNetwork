package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Profile;

public class ListProfilesController {

	@FXML
	private AnchorPane ListProfilesLayout;

	@FXML
	private ListView<Profile> listProfiles;
	@FXML
	private Button goBackButton;
	static Scene previousScene;

	/**
	 * @author Ana
	 */
	@FXML
	private void initialize() {

	}

	@FXML
	public void goBack(ActionEvent event) {
		try {

			// Restaurar la escena anterior si existe
			if (previousScene != null) {
				Stage stage = (Stage) goBackButton.getScene().getWindow();
				stage.setScene(previousScene);
				stage.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
