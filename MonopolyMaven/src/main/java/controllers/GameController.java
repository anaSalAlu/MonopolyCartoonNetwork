
package controllers;

import java.io.IOException;
import java.util.List;

import dao.GameDAO;
import dao.GameDAOSQLITE;
import dao.PlayerDAO;
import dao.PlayerDAOSQLITE;
import dao.PropertyDAO;
import dao.PropertyDAOSQLITE;
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

	private ImageView player3_icon;
	@FXML
	private Text player3_name;

	@FXML
	private ImageView player4_icon;
	@FXML
	private Text player4_name;

	private boolean isNewGame;
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
		PlayerDAO jugadorDAO = new PlayerDAOSQLITE();
		PropertyDAO propiedadDAO = new PropertyDAOSQLITE();

		this.players = jugadorDAO.cargarJugadores();
		this.properties = propiedadDAO.cargarPropiedades();
		this.board = new Board(0, null, 0);
		this.cards = loadCards();
		this.chestCards = loadChestCards();
		this.luckyCards = loadLuckyCards();
		this.propertyCards = loadPropertiesCards();
		this.chestLuckyCards = loadLuckyChestCards();

		System.out.println("Jugadores: " + players);
		System.out.println("Propiedades: " + properties);
		System.out.println("Tablero: " + board);
		System.out.println("Cartas: " + cards);
		System.out.println("Cartas Cofre: " + chestCards);
		System.out.println("Cartas Suerte: " + luckyCards);
		System.out.println("Cartas Propiedad: " + propertyCards);
		System.out.println("Cartas Cofre y Suerte: " + chestLuckyCards);
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
		return null;
	}

	// TODO
	private List<Card> loadChestCards() {
		return null;
	}

	// TODO
	private List<Card> loadLuckyCards() {
		return null;
	}

	// TODO
	private List<Card> loadPropertiesCards() {
		return null;
	}

	// TODO
	private List<Card> loadLuckyChestCards() {
		return null;
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
}
