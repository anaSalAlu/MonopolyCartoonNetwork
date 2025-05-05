package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameController {
	@FXML
	private Button exitButton;
	@FXML
	private GridPane board_game;

	@FXML
	private ImageView player1_icon;

	@FXML
	private Text player1_name;

	@FXML
	private ImageView player2_icon;

	@FXML
	private Text player2_name;

	@FXML
	private ImageView player3_icon;

	@FXML
	private Text player3_name;

	@FXML
	private ImageView player4_icon;

	@FXML
	private Text player4_name;

	private boolean isNewGame;

	public void setNewGame(boolean isNewGame) {
		this.isNewGame = isNewGame;
	}

	@FXML
	public void initialize() {
		if (isNewGame) {
			// Lógica para inicializar una nueva partida
			System.out.println("Iniciando nueva partida...");
		} else {
			// Lógica para cargar una partida guardada
			System.out.println("Cargando partida guardada...");
		}
	}

	@FXML
	public void exitGame() {
		// Create a confirmation dialog
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Salir del Juego");
		alert.setHeaderText("¿Estás seguro que quieres salir?");
		alert.setContentText("Elige una opción:");

		// Add buttons for the options
		ButtonType mainMenuButton = new ButtonType("Menu Principal");
		ButtonType exitAppButton = new ButtonType("Salir", ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(mainMenuButton, exitAppButton, cancelButton);

		// Show the dialog and wait for the user's response
		alert.showAndWait().ifPresent(response -> {
			if (response == mainMenuButton) {
				try {
					Stage stage = (Stage) this.exitButton.getScene().getWindow();

					// Get current stage dimensions
					double currentWidth = stage.getWidth();
					double currentHeight = stage.getHeight();

					FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
					Parent root = loader.load();

					// Set the new scene with the same dimensions
					Scene scene = new Scene(root, currentWidth, currentHeight);
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (response == exitAppButton) {
				System.exit(0);
			}
		});
	}

}