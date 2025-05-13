package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Profile;

public class ListProfilesController {

	@FXML
	private ListView<Profile> listProfiles;

	@FXML
	private Button confirmButton;

	@FXML
	private Button selectImageButton;

	@FXML
	private Button goBackButton;

	@FXML
	private TextField nicknameField;

	private String selectedImagePath;
	private static Scene previousScene;

	@FXML
	private void initialize() {
		// Enable multiple selection for profiles
		listProfiles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	@FXML
	public void selectImage(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Profile Image");
		fileChooser.setInitialDirectory(new File("logos"));
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
		String nickname = nicknameField.getText().trim();

		if (nickname.isEmpty() || selectedImagePath == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Invalid Input");
			alert.setHeaderText("Nickname or Image Missing");
			alert.setContentText("Please enter a nickname and select an image.");
			alert.showAndWait();
			return;
		}

		Profile newProfile = new Profile(0, nickname, selectedImagePath);
		listProfiles.getItems().add(newProfile);

		// Clear fields
		nicknameField.clear();
		selectedImagePath = null;
	}

	public List<Profile> getSelectedProfiles() {
		// Return a new ArrayList to avoid exposing internal selection model directly
		return new ArrayList<>(listProfiles.getSelectionModel().getSelectedItems());
	}

	@FXML
	private void goBack(ActionEvent event) {
		try {
			// Carga la nueva vista
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
			Parent mainViewRoot = loader.load();

			Scene mainViewScene = new Scene(mainViewRoot);
			Stage stage = (Stage) goBackButton.getScene().getWindow();

			stage.setScene(mainViewScene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Load Error");
			alert.setHeaderText("Could not load Main View");
			alert.setContentText("An error occurred while trying to load the main view.");
			alert.showAndWait();
		}
	}

	public static void setPreviousScene(Scene scene) {
		previousScene = scene;
	}
}
