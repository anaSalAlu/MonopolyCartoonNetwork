
package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Board;
import models.Card;
import models.Cell;
import models.Game;
import models.Player;
import models.Property;
import models.RentHouseValue;

public class GameController {

	// TODO obtener el directorio resources
	public static final String RESOURCES_DIR = "src/main/resources";

	// public static final String DICE_IMAGE = RESOURCES_DIR + "/dice/dice0{0}.png";
	public static final String DICE_IMAGE = "dice/dice0{0}.png";

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
	private List<Cell> cells;
	private Game game;
	@FXML
	private ImageView imageDiceFirst;

	@FXML
	private ImageView imageDiceSecond;

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

	@FXML
	public void rollDice() {
		Random random = new Random();
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					for (int i = 0; i < 15; i++) {
						// Generamos los números de forma aleatoria del 1 al 6
						int randomNum1 = random.nextInt(6) + 1;
						int randomNum2 = random.nextInt(6) + 1;

						// Generamos las rutas de los recursos (ya corregidas)
						String resourcePathFirst = MessageFormat.format(DICE_IMAGE, randomNum1);
						String resourcePathSecond = MessageFormat.format(DICE_IMAGE, randomNum2);

						// Cargar las imágenes desde el classpath
						URL resourceUrlFirst = getClass().getClassLoader().getResource(resourcePathFirst);
						URL resourceUrlSecond = getClass().getClassLoader().getResource(resourcePathSecond);

						// Creamos una imagen con el número de la cara aleatoria
						// Image diceFace1 = new Image(MessageFormat.format(DICE_IMAGE, randomNum1));
						// Image diceFace2 = new Image(MessageFormat.format(DICE_IMAGE, randomNum2));

						Image diceFace1 = new Image(resourceUrlFirst.toString());
						Image diceFace2 = new Image(resourcePathSecond.toString());

						// Seteamos la imagen de la cara para hacer como que se está lanzando el dado
						imageDiceFirst.setImage(diceFace1);
						imageDiceSecond.setImage(diceFace2);

						Thread.sleep(50);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
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

	// TODO cambiar método para que esté hecho de la misma forma que los demás
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

	// TODO implementar la venta de la propiedad
	public void venderPropiedad() {
		System.out.println("Vendiendo propiedad...");
	}

	/**
	 * @author Ana
	 */
	// implementar la compra del hotel
	// TODO pensar en si se necesita poner la implementación del bot,
	// si eres bot, compras o mirar como coño hacerlo
	public void comprarHotel(Property property, Player player) {
		if (property.getHouseNumber() == 2 || property.getHotelNumber() < 1) {
			System.out.println("Comprando hotel...");
			int hotelValue = property.getHotelBuyValue();
			int actualMoney = player.getMoney();
			if (checkIfPlayerCanPurchase(actualMoney, hotelValue)) {
				int substractedMoney = actualMoney - hotelValue;
				player.setMoney(substractedMoney);
				property.setHotelNumber(1);
			} else {
				player.setBankrupt(true);
			}
		} else {
			System.out.println("Error, no tiene suficientes casas o ya tiene un hotel comprado");
		}
	}

	/**
	 * 
	 * @author Ana
	 */
	// implementar la venta del hotel
	public void venderHotel(Property property, Player player) {
		if (property.getHotelNumber() == 1) {
			System.out.println("Vendiendo hotel...");
			int hotelValue = property.getHotelBuyValue();
			int actualMoney = player.getMoney();
			if (!player.getIsBankrupt()) {
				int addedMoney = actualMoney + hotelValue;
				player.setMoney(addedMoney);
				property.setHotelNumber(0);
			} else {
				System.out.println("Estás en bancarrota, ya no puedes jugar");
			}
		} else {
			System.out.println("Error, no puedes vender el hotel porque no tienes uno");
		}
	}

	// TODO mostrar la carta
	public void mostrarCarta() {
		System.out.println("Mostrando carta...");
	}

	// TODO mirar si estos métodos son necesarios
	public void comprarBot() {
		System.out.println("Bot comprando propiedad...");
	}

	// TODO mirar si estos métodos son necesarios
	public void noComprarBot() {
		System.out.println("Bot no puede comprar propiedad...");
	}

	// TODO implementar el cobro de alquiler
	public void cobrarAlquiler(Property property, Player owner, Player renter) {
		System.out.println("Cobrando alquiler...");
		int rent = 0;
		if (property.getHouseNumber() == 0 || property.getHotelNumber() == 0) {
			rent = property.getRentBaseValue();
		} else if (property.getHouseNumber() > 0) {
			List<RentHouseValue> rents = null;
			switch (property.getHouseNumber()) {
			case 1:
				rents = property.getRentHouseValue();
				rent = rents.get(0).getRentValue();
				break;

			case 2:
				rents = property.getRentHouseValue();
				rent = rents.get(1).getRentValue();
				break;
			case 3:
				rents = property.getRentHouseValue();
				rent = rents.get(2).getRentValue();
				break;
			default:
				break;
			}
		} else if (property.getHotelNumber() == 1) {
			rent = property.getRentHotelValue();
		}
		// TODO mirar si el jugador renter puede pagar el alquiler y sino decirle que
		// está en bancarrota
		// TODO cobrar
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

	public void setGame(Game game) {
		this.game = game;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public void setChestCards(List<Card> chestCards) {
		this.chestCards = chestCards;
	}

	public void setLuckyCards(List<Card> luckyCards) {
		this.luckyCards = luckyCards;
	}

	public void setPropertyCards(List<Card> propertyCards) {
		this.propertyCards = propertyCards;
	}

	public void setChestLuckyCards(List<Card> chestLuckyCards) {
		this.chestLuckyCards = chestLuckyCards;
	}

	public Boolean checkIfPlayerCanPurchase(int actualMoney, int quantity) {
		int substractedMoney = actualMoney - quantity;
		if (actualMoney == 0) {
			return false;
		} else if (substractedMoney < 0) {
			return false;
		} else {
			return true;
		}
	}

}
