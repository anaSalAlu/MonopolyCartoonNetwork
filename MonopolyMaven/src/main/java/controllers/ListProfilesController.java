
package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import models.Profile;

public class ListProfilesController {

	@FXML
	private ListView<Profile> listProfiles;

	@FXML
	private Button confirmButton;

	@FXML
	private Button selectImageButton;

	@FXML
	private TextField nicknameField;

	private List<Profile> selectedProfiles = new ArrayList<>();
	private String selectedImagePath;

	@FXML
	private void initialize() {
		// Enable multiple selection for profiles
		listProfiles.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.MULTIPLE);
	}

	@FXML
	public void selectImage(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Profile Image");
		fileChooser.setInitialDirectory(new File("logos")); // Set the initial directory to 'logos'
		fileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

		File selectedFile = fileChooser.showOpenDialog(selectImageButton.getScene().getWindow());
		if (selectedFile != null) {
			selectedImagePath = selectedFile.getAbsolutePath();
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Image Selected");
			alert.setHeaderText(null);
			alert.setContentText("Selected Image: " + selectedFile.getName());
			alert.showAndWait();
		}
	}

	@FXML
	public void confirmSelection(ActionEvent event) {
		String nickname = nicknameField.getText();

		if (nickname == null || nickname.isEmpty() || selectedImagePath == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Invalid Input");
			alert.setHeaderText("Nickname or Image Missing");
			alert.setContentText("Please enter a nickname and select an image.");
			alert.showAndWait();
			return;
		}

		// Create a new Profile with the selected nickname and image
		Profile newProfile = new Profile(0, nickname, selectedImagePath);
		listProfiles.getItems().add(newProfile);

		// Clear the input fields for the next profile
		nicknameField.clear();
		selectedImagePath = null;
	}

	public List<Profile> getSelectedProfiles() {
		return listProfiles.getSelectionModel().getSelectedItems();
	}
}
