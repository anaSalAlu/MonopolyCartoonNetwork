
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.GameDAO;
import dao.GameDAOSQLITE;
import dao.PlayerDAO;
import dao.PlayerDAOSQLITE;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Board;
import models.Card;
import models.Player;
import models.Property;

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

	private boolean isNewGame = false;;
	private Player jugadorActual;
	private Property propiedadSeleccionada;

	private List<Player> players;
	private Board board;
	private List<Property> properties;
	private List<Card> cards;
	private List<Card> chestCards;
	private List<Card> luckyCards;
	private List<Card> propertyCards;
	private List<Card> chestLuckyCards;
	@FXML
	private ImageView fichaJugador1;
	@FXML
	private ImageView fichaJugador2;

	private Player jugador1;
	private Player jugador2;

	private String fichaSeleccionadaJugador1;
	private String fichaSeleccionadaJugador2;

	public void setNewGame(boolean isNewGame) {
		this.isNewGame = isNewGame;
	}

	@FXML
	public void initialize() {
		if (isNewGame) {
			System.out.println("Iniciando nueva partida...");
			inicializarNuevaPartida();
		} else {
			System.out.println("Cargando partida guardada...");
			cargarPartidaGuardada();
		}
	}

	@FXML
	public void exitGame() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Salir del Juego");
		alert.setHeaderText("¿Estás seguro que quieres salir?");
		alert.setContentText("Elige una opción:");

		ButtonType mainMenuButton = new ButtonType("Menu Principal");
		ButtonType exitAppButton = new ButtonType("Salir", ButtonBar.ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(mainMenuButton, exitAppButton, cancelButton);

		alert.showAndWait().ifPresent(response -> {
			if (response == mainMenuButton) {
				try {
					Stage stage = (Stage) this.exitButton.getScene().getWindow();
					double currentWidth = stage.getWidth();
					double currentHeight = stage.getHeight();

					FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
					Parent root = loader.load();

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

	// TODO
	private void inicializarNuevaPartida() {

		try {
			// Load ProfileView for Player 1
			FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/views/ProfileView.fxml"));
			Parent root1 = loader1.load();
			ProfileController profileController1 = loader1.getController();

			Stage stage1 = new Stage();
			stage1.setTitle("Jugador 1 - Crear Perfil");
			stage1.setScene(new Scene(root1));
			stage1.showAndWait();

			// Retrieve Player 1 data
			TextArea nombreJugador1 = profileController1.getName_text_area();
			Image ImagenJugador1 = profileController1.getPlayer_image();

			// Load ProfileView for Player 2
			FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/views/ProfileView.fxml"));
			Parent root2 = loader2.load();
			ProfileController profileController2 = loader2.getController();

			Stage stage2 = new Stage();
			stage2.setTitle("Jugador 2 - Crear Perfil");
			stage2.setScene(new Scene(root2));
			stage2.showAndWait();

			// Retrieve Player 2 data
			TextArea nombreJugador2 = profileController2.getName_text_area();
			Image ImagenJugador2 = profileController2.getPlayer_image();

			// Create Player Profiles
			PlayerDAO playerDAO = new PlayerDAOSQLITE();
			jugador1 = new Player(nombreJugador1, ImagenJugador1);
			jugador2 = new Player(nombreJugador2, ImagenJugador2);

			playerDAO.addPlayer(jugador1);
			playerDAO.addPlayer(jugador2);

			System.out.println("Player profiles created and saved in the database.");

			// Allow players to select their tokens
			seleccionarFichaJugador1("/fichas/ficha1.png"); // Example path
			seleccionarFichaJugador2("/fichas/ficha2.png"); // Example path

			// Save selected tokens in the database
			jugador1.setFichaSeleccionadaJugador1(fichaSeleccionadaJugador1);
			jugador2.setFichaSeleccionadaJugador2(fichaSeleccionadaJugador2);

			playerDAO.updatePlayerFicha(jugador1);
			playerDAO.updatePlayerFicha(jugador2);

			System.out.println("Tokens selected and saved in the database.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO
	private void cargarPartidaGuardada() {
		GameDAO partidaDAO = new GameDAOSQLITE();
		this.players = partidaDAO.loadPlayers();
		this.properties = partidaDAO.loadProperties();
		this.board = partidaDAO.loadBoards();
	}

	// TODO
	// Métodos de carga de cartas (simulados)
	private List<Card> loadCards() {
		return new ArrayList<>(); // Placeholder implementation
	}

	private List<Card> loadChestCards() {
		return new ArrayList<>(); // Placeholder implementation
	}

	private List<Card> loadLuckyCards() {
		return new ArrayList<>(); // Placeholder implementation
	}

	private List<Card> loadPropertiesCards() {
		return new ArrayList<>(); // Placeholder implementation
	}

	private List<Card> loadLuckyChestCards() {
		return new ArrayList<>(); // Placeholder implementation
	}

	// --- FUNCIONALIDADES DE JUEGO ---

	// TODO
	public void comprarPropiedad() {
		System.out.println("Comprando propiedad...");
		if (jugadorActual != null && propiedadSeleccionada != null) {
			double precio = propiedadSeleccionada.getBuyValue();
			if (jugadorActual.getMoney() >= precio) {
				jugadorActual.setMoney((int) (jugadorActual.getMoney() - precio));
				propiedadSeleccionada.setIdProperty(propiedadSeleccionada.getIdProperty());
				System.out.println("Propiedad comprada: " + propiedadSeleccionada.getName());
			} else {
				System.out.println("Dinero insuficiente.");
			}
		} else {
			System.out.println("Jugador o propiedad no definidos.");
		}
	}

//TODO
	public void venderPropiedad() {
		System.out.println("Vendiendo propiedad...");
	}

	// TODO
	public void comprarHotel() {
		System.out.println("Comprando hotel...");
	}

	// TODO
	public void venderHotel() {
		System.out.println("Vendiendo hotel...");
	}

	// TODO
	public void mostrarCarta() {
		System.out.println("Mostrando carta...");
	}

	// TODO
	public void comprarBot() {
		System.out.println("Bot comprando propiedad...");
	}

	// TODO
	public void noComprarBot() {
		System.out.println("Bot no puede comprar propiedad...");
	}

	// TODO
	public void cobrarAlquiler() {
		System.out.println("Cobrando alquiler...");
	}

	// TODO
	public void mostrarCartaCofreSuerte() {
		System.out.println("Mostrando carta de cofre o suerte...");
	}

	@FXML
	public void seleccionarFichaJugador1(String rutaFicha) {
		fichaSeleccionadaJugador1 = rutaFicha;
		fichaJugador1.setImage(new javafx.scene.image.Image(rutaFicha));
		System.out.println("Token selected for Player 1: " + rutaFicha);
	}

	@FXML
	public void seleccionarFichaJugador2(String rutaFicha) {
		fichaSeleccionadaJugador2 = rutaFicha;
		fichaJugador2.setImage(new javafx.scene.image.Image(rutaFicha));
		System.out.println("Token selected for Player 2: " + rutaFicha);
	}
}
