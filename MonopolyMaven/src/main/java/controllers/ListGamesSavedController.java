package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ListGamesSavedController {

	@FXML
	private Button goBackButton;

	@FXML
	public void goBack(ActionEvent event) {
		try {
			// Load the GameController view
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
			Parent root = loader.load();

			// Get the current stage and set the new scene
			Stage stage = (Stage) goBackButton.getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
