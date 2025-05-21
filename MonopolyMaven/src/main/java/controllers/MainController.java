
package controllers;

import java.io.IOException;

import dao.ManagerConnection;
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
	private boolean isNewGame = false;

	public void setNewGame(boolean isNewGame) {
		this.isNewGame = isNewGame;
	}

	@FXML
	public void initialize() {
		// Cargamos primero de todo la base de datos
		ManagerConnection.obtenirConnexio();

		Image img = new Image(getClass().getResource("/images/logos/home_logo.png").toExternalForm());
		imgLogo.setImage(img);
		imgLogo.setFitWidth(600);
		imgLogo.setFitHeight(400);
		imgLogo.setPreserveRatio(true);
		if (isNewGame) {
			System.out.println("Iniciando nueva partida...");

		} else {
			System.out.println("Cargando partida guardada...");

		}

	}

	@FXML
	public void exitMenu() {
		System.exit(0);
	}

	/*
	 * @FXML void gameMenu(ActionEvent event) { try { Stage stage = (Stage)
	 * ((Button) event.getSource()).getScene().getWindow();
	 * 
	 * if (event.getSource() == btnNewGame) { // Nueva partida FXMLLoader loader =
	 * new FXMLLoader(getClass().getResource("/views/GameView.fxml")); Parent root =
	 * loader.load();
	 * 
	 * // Pasar el parámetro al GameController GameController gameController =
	 * loader.getController(); // gameController.setNewGame(true);
	 * 
	 * stage.setScene(new Scene(root)); stage.show(); } else if (event.getSource()
	 * == btnContinueGame) {
	 * 
	 * FXMLLoader loader = new
	 * FXMLLoader(getClass().getResource("/views/SavedGamesView.fxml")); Parent root
	 * = loader.load();
	 * 
	 * stage.setScene(new Scene(root)); stage.show(); } } catch (IOException e) {
	 * e.printStackTrace(); } }
	 */

	@FXML
	void goToProfile(ActionEvent event) {
		try {
			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListProfilesView.fxml"));
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	// Esto se queda aqui.
	// TODO solo cargar la vista de los perfiles
	// TODO cargar si es una nueva partida(DataClass)

	// Esto iría en la clase GameController.
	// TODO pasar los demas pasos al GameController (initialize)
	// TODO una vez obtenidos los jugadores y la vista entonces determinamos el
	// orden de turno. Esto se hace tirando los dados y el que saque mayor numero
	// empieza.
	@FXML
	public void startNewGame(ActionEvent event) {
		try {
			// Paso 1: Cargar la vista ListProfilesView para seleccionar perfiles
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ListProfilesView.fxml"));
			Parent root = loader.load();
			ListProfilesController listController = loader.getController();

			listController.setNewGame(true);
			listController.configureView();

			// Mostrar la vista para seleccionar perfiles
			Scene listViewScene = new Scene(root);
			Stage stage = (Stage) mainLayout.getScene().getWindow();

			stage.setScene(listViewScene);
			stage.show();

			// Paso 2: Obtener los perfiles seleccionados
			/*
			 * List<Profile> selectedProfiles = controller.getSelectedProfiles(); if
			 * (selectedProfiles.isEmpty() || selectedProfiles.size() > 2) { Alert alert =
			 * new Alert(Alert.AlertType.ERROR); alert.setTitle("Selección Inválida");
			 * alert.setHeaderText(null);
			 * alert.setContentText("Por favor, selecciona 1 o 2 perfiles.");
			 * alert.showAndWait();
			 * 
			 * return; }
			 * 
			 * // Paso 3: Crear una nueva partida Game newGame = new Game();
			 * newGame.setDuration(String.valueOf(60)); // Duración de la partida
			 * newGame.setState(State.IN_GAME); // Estado inicial de la partida
			 * 
			 * // Paso 4: Cargar la vista del juego y pasar los datos necesarios al
			 * controlador FXMLLoader gameLoader = new
			 * FXMLLoader(getClass().getResource("/views/GameView.fxml")); Parent gameRoot =
			 * gameLoader.load(); GameController gameController =
			 * gameLoader.getController();
			 * 
			 * // Pasar la partida y los perfiles seleccionados al GameController
			 * gameController.setGame(newGame); gameController.setPlayers(selectedProfiles);
			 * 
			 * // Mostrar la vista del juego Stage gameStage = (Stage) ((Button)
			 * event.getSource()).getScene().getWindow(); gameStage.setScene(new
			 * Scene(gameRoot)); gameStage.show();
			 * 
			 * System.out.println("Perfiles seleccionados: " + selectedProfiles);
			 */

			// El método initialize() de GameController se ejecutará automáticamente
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO
//	@FXML
//	public void cargarPartidaGuardada(ActionEvent event) {
//		GameDAO partidaDAO = new GameDAOSQLITE();
//		Game partida = partidaDAO.obtenerPartidaGuardada();
//		if (partida != null) {
//			// Cargar la partida guardada
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameView.fxml"));
//			try {
//				Parent root = loader.load();
//				GameController controller = loader.getController();
//				controller.setGame(partida);
//				Stage stage = (Stage) btnContinueGame.getScene().getWindow();
//				stage.setScene(new Scene(root));
//				stage.show();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else {
//			// No hay partida guardada
//			System.out.println("No hay partida guardada.");
//		}
//	}

//	public void setGame(Game partida) {
//		this.players = partida.getPlayers();
//		this.board = partida.getBoard();
//		this.properties = partida.getProperties();
//		this.cards = partida.getCards();
//		this.chestCards = partida.getChestCards();
//		this.luckyCards = partida.getLuckyCards();
//		this.propertyCards = partida.getPropertyCards();
//		this.chestLuckyCards = partida.getChestLuckyCards();
//
//		if (players.size() >= 2) {
//			jugador1 = players.get(0);
//			jugador2 = players.get(1);
//			player1_name.setText(jugador1.getProfile().getName());
//			player2_name.setText(jugador2.getProfile().getName());
//			player1_icon.setImage(jugador1.getProfile().getImage());
//			player2_icon.setImage(jugador2.getProfile().getImage());
//		} else {
//			System.out.println("No hay suficientes jugadores.");
//		}
//
//	}
//	@FXML
//	public void createProfile(ActionEvent event) {
//		try {
//			// Obtener el Stage actual
//			Stage stage = (Stage) btnProfiles.getScene().getWindow();
//
//			// Almacenar la escena actual en una variable estática
//			ProfileController.previousScene = stage.getScene();
//
//			// Cargar la vista del ProfileController
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ProfileView.fxml"));
//			Parent root = loader.load();
//
//			// Cambiar a la nueva escena
//			stage.setScene(new Scene(root));
//			stage.show();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//	}
}
