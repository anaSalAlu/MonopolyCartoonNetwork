
package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
	void gameMenu(ActionEvent event) {
		try {
			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

			if (event.getSource() == btnNewGame) {
				// Nueva partida
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameView.fxml"));
				Parent root = loader.load();

				// Pasar el parámetro al GameController
				GameController controller = loader.getController();
				controller.setNewGame(true);

				stage.setScene(new Scene(root));
				stage.show();
			} else if (event.getSource() == btnContinueGame) {

				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/SavedGamesView.fxml"));
				Parent root = loader.load();

				stage.setScene(new Scene(root));
				stage.show();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void goToProfile(ActionEvent event) {
		try {
			// Obtener el Stage actual
			Stage stage = (Stage) btnProfiles.getScene().getWindow();

			// Almacenar la escena actual en una variable estática
			ProfileController.previousScene = stage.getScene();

			// Cargar la vista del ProfileController
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ProfileView.fxml"));
			Parent root = loader.load();

			// Cambiar a la nueva escena
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
