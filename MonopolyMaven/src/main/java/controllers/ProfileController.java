package controllers;

import java.io.IOException;

import dao.DAOManager;
import dao.ProfileDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import models.Profile;

public class ProfileController {

	private static final String[] IMAGE_PATHS = { "/images/profile_photos/finn.jpg", "/images/profile_photos/jake.jpg",
			"/images/profile_photos/bmo.jpg", "/images/profile_photos/finn.jpg", "/images/profile_photos/jake.jpg" };

	@FXML
	private Button btnEditNickname;

	@FXML
	private Button btnEditProfilePhoto;

	@FXML
	private Button btnGoBack;

	@FXML
	private Button btnSaveProfile;

	@FXML
	private ImageView imgProfilePhoto;

	@FXML
	private TextField tfNickname;

	@FXML
	private TilePane profilePhotosPane;

	@FXML
	private ScrollPane scrollPane;

	static Scene previousScene;
	private Profile selectedProfile;
	private String selectedImagePath;
	private DAOManager daoManager = new DAOManager();
	private ProfileDAO profileDAO = daoManager.getProfileDAO();

	@FXML
	private void initialize() {
		tfNickname.setEditable(true);
		btnEditNickname.setVisible(false);
		btnEditProfilePhoto.setVisible(false);
		scrollPane.setVisible(true);
		profilePhotosPane.setVisible(true);
		setImagePane();
	}

	@FXML
	public void goBack(ActionEvent event) {
		try {
			// Carga la nueva vista
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListProfilesView.fxml"));
			Parent mainViewRoot = loader.load();

			Scene mainViewScene = new Scene(mainViewRoot);
			Stage stage = (Stage) btnGoBack.getScene().getWindow();

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

	@FXML
	void editName(ActionEvent event) {
		tfNickname.setEditable(true);
	}

	@FXML
	void editPhoto(ActionEvent event) {
		scrollPane.setVisible(true);
		profilePhotosPane.setVisible(true);
		setImagePane();
	}

	void setImagePane() {
		for (String imagePath : IMAGE_PATHS) {
			Image image = new Image(imagePath, true);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(100);
			imageView.setFitHeight(100);
			imageView.setPreserveRatio(true);
			imageView.setCursor(Cursor.HAND);

			imageView.setOnMouseClicked(e -> {
				imgProfilePhoto.setImage(image);
				selectedImagePath = imagePath;
			});

			profilePhotosPane.getChildren().add(imageView);
		}
	}

	@FXML
	void saveProfile(ActionEvent event) {
		// TODO mirar si funciona el guardar en la base de datos
		if (selectedProfile != null) {
			profileDAO.updateProfile(selectedProfile);
		} else {
			String nickname = tfNickname.getText();
			Profile profile = new Profile(nickname, selectedImagePath);
			profileDAO.addProfile(profile);
		}

	}

	public void setProfile(Profile selectedProfile) {
		this.selectedProfile = selectedProfile;

		if (selectedProfile != null) {
			tfNickname.setEditable(false);
			btnEditNickname.setVisible(true);
			btnEditProfilePhoto.setVisible(true);
			tfNickname.setText(selectedProfile.getNickname());
			imgProfilePhoto.setImage(new Image(selectedProfile.getImage(), true));
			scrollPane.setVisible(false);
			profilePhotosPane.setVisible(false);
		}
	}
}