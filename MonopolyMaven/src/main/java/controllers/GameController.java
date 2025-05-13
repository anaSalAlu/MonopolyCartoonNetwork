
package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	@FXML
	private ImageView player3_icon;
	@FXML
	private Text player3_name;

	@FXML
	private ImageView player4_icon;
	@FXML
	private Text player4_name;

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
	private ImageView fichaJugador;

	private Player jugador1;
	private Player jugador2;

	private String fichaSeleccionadaJugador;

	@FXML
	public void initialize() {

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
	public void seleccionarFichaJugador() {
		// Lógica para seleccionar la ficha del jugador
		if (jugadorActual != null) {
			jugadorActual.setCell(jugadorActual.getCell());
			fichaSeleccionadaJugador = "fichaSeleccionada.png"; // Cambia esto por la lógica real
			System.out.println("Ficha seleccionada: " + fichaSeleccionadaJugador);
		} else {
			System.out.println("Jugador no definido.");
		}
	}

}
