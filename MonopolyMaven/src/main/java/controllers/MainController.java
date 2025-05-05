package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author Ana
 */
public class MainController {

	@FXML
	private Button btnContinueGame;

	@FXML
	private Button btnExit;

	@FXML
	private Button btnNewGame;

	@FXML
	private Button btnProfiles;

	@FXML
	private ImageView imgLogo;

	@FXML
	private AnchorPane mainLayout;

	@FXML
	public void initialize() {
		Image img = new Image(getClass().getResource("/logos/home_logo.png").toExternalForm());
		imgLogo.setImage(img);
		imgLogo.setFitWidth(600);
		imgLogo.setFitHeight(400);
		imgLogo.setPreserveRatio(true);
	}

	@FXML
	public void exitMenu() {
		System.exit(0);
	}

	@FXML
	public void gameMenu() {

	}

	@FXML
	public void profileMenu() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ListProfilesView.fxml"));
		try {
			AnchorPane secondView = loader.load();
			Stage stage = (Stage) mainLayout.getScene().getWindow();
			stage.setScene(new Scene(secondView));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
