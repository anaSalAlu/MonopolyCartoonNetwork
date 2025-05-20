package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.DAOManager;
import dao.ProfileDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
	private static DAOManager daoManager = new DAOManager();
	private static ProfileDAO profileDAO = daoManager.getProfileDAO();

	@FXML
	private void initialize() {
		// Enable multiple selection for profiles
		// listProfiles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// Conseguir los perfiles ya directamente para que se vean al abrir la ventana
		List<Profile> profiles = profileDAO.getAll();
		listProfiles.getItems().clear();
		listProfiles.setCellFactory(list -> new ListCell<Profile>() {
			private final ImageView imageView = new ImageView();
			private final Label label = new Label();
			private final HBox content = new HBox(10);

			{
				imageView.setFitWidth(80);
				imageView.setFitHeight(80);
				label.setStyle("-fx-font-size: 18px;");
				content.setStyle("-fx-padding: 10px;");
				content.getChildren().addAll(imageView, label);
			}

			@Override
			protected void updateItem(Profile item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
					setGraphic(null);
				} else {
					imageView.setImage(new Image(item.image, true));
					label.setText(item.nickname);
					setGraphic(content);
				}
			}
		});

		listProfiles.getItems().addAll(profiles);

		// Listener para detectar el cambio de selección
		listProfiles.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				openProfile();
			}
		});
	}

	public void openProfile() {
		// Get the selected profile
		Profile selectedProfile = listProfiles.getSelectionModel().getSelectedItem();

		if (selectedProfile != null) {
			try {

				// Load the profile view
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ProfileView.fxml"));
				Parent profileViewRoot = loader.load();

				// Pass the selected profile to the ProfileController
				ProfileController profileController = loader.getController();
				profileController.setProfile(selectedProfile);

				Scene profileViewScene = new Scene(profileViewRoot);
				Stage stage = (Stage) listProfiles.getScene().getWindow();

				stage.setScene(profileViewScene);
				stage.show();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void addProfile(ActionEvent event) {
		// Show the profile creation view
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ProfileView.fxml"));
			Parent addProfileRoot = loader.load();

			Scene addProfileScene = new Scene(addProfileRoot);
			Stage stage = (Stage) listProfiles.getScene().getWindow();

			stage.setScene(addProfileScene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
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
