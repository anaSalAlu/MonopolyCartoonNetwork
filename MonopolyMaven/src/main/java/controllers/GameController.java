
package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dao.DAOManager;
import dao.GameDAO;
import dao.PlayerCardDAO;
import dao.PlayerDAO;
import dao.PlayerPropertyDAO;
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
import models.Card;
import models.Card.CardType;
import models.Cell;
import models.Game;
import models.Player;
import models.PlayerProperty;
import models.Profile;
import models.Property;
import models.RentHouseValue;

public class GameController {

	// TODO obtener el directorio resources
	public static final String RESOURCES_DIR = "src/main/resources";

	// public static final String DICE_IMAGE = RESOURCES_DIR + "/dice/dice0{0}.png";
	public static final String DICE_IMAGE = "images/dice/dice0{0}.png";

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

	/*
	 * private Player jugadorActual; private Property propiedadSeleccionada;
	 * 
	 * private List<Player> players; private Board board; private List<Property>
	 * properties; private List<Card> cards; private List<Card> chestCards; private
	 * List<Card> luckyCards; private List<Card> propertyCards; private List<Card>
	 * chestLuckyCards;
	 * 
	 * @FXML private ImageView fichaJugador;
	 * 
	 * private Player jugador1; private Player jugador2;
	 * 
	 * private String fichaSeleccionadaJugador; private List<Cell> cells; private
	 * Game game;
	 */

	// DICE

	@FXML
	private ImageView imageDiceFirst;

	@FXML
	private ImageView imageDiceSecond;

	@FXML
	private Button rollDice;

	private int dice1;
	private int dice2;
	private boolean[] isDouble;

	// Game
	private Game actualGame;
	private Boolean isFinished = false;
	private List<Profile> players = new ArrayList<>();
	private Player[] orderTurn;

	// DAOs
	private DAOManager daoManager = new DAOManager();
	private PlayerDAO playerDAO = daoManager.getPlayerDAO();
	private GameDAO gameDAO = daoManager.getGameDAO();
	private PlayerCardDAO playerCardDAO = daoManager.getPlayerCardDAO();
	private PlayerPropertyDAO playerPropertyDAO = daoManager.getPlayerPropertyDAO();

	@FXML
	public void initialize() throws IOException {
		if (players == null || players.isEmpty()) {
			System.out.println("Error: Los perfiles no se han cargado en el GameController.");
		} else {
			System.out.println("Perfiles cargados en el GameController: " + players.size());
			for (Profile profile : players) {
				System.out.println("Perfil: " + profile.getNickname());
			}
		}
		/*
		 * // Paso 4: Seleccionar fichas para cada jugador List<String> availableTokens
		 * = new ArrayList<>(Arrays.asList("gema.png", "mascara.png", "pankake.png",
		 * "probeta.png", "rinyonera.png", "tabla.png"));
		 * 
		 * List<Player> players = new ArrayList<>();
		 * 
		 * Cell startingCell = cellDAO.findCellById(0); // Obtener la celda de inicio
		 * (id 0) for (Profile profile : selectedProfiles) { ChoiceDialog<String>
		 * tokenDialog = new ChoiceDialog<>(availableTokens.get(0), availableTokens);
		 * tokenDialog.setTitle("Seleccionar Ficha");
		 * tokenDialog.setHeaderText("Selecciona una ficha para " +
		 * profile.getNickname()); tokenDialog.setContentText("Fichas disponibles:");
		 * 
		 * Optional<String> selectedToken = tokenDialog.showAndWait();
		 * 
		 * if (selectedToken.isPresent()) { Player player = new Player();
		 * player.setProfile(profile); player.setMoney(1500); player.setGame(newGame);
		 * player.setSelectedTocken(selectedToken.get()); player.setCell(startingCell);
		 * // << CORRECTO AHORA players.add(player);
		 * 
		 * availableTokens.remove(selectedToken.get()); } else { Alert alert = new
		 * Alert(Alert.AlertType.ERROR);
		 * alert.setTitle("Error en la Selección de Ficha"); alert.setHeaderText(null);
		 * alert.setContentText("Debes seleccionar una ficha para " +
		 * profile.getNickname()); alert.showAndWait(); return; } }
		 * 
		 * // Paso 6: Insertar la partida y los jugadores en la base de datos GameDAO
		 * gameDAO = new GameDAOSQLITE(); gameDAO.addGame(newGame);
		 * 
		 * PlayerDAO playerDAO = new PlayerDAOSQLITE(); for (Player player : players) {
		 * playerDAO.addPlayer(player); }
		 */

		// Paso 7: Cargar la vista del juego y pasar los datos necesarios al controlador
		// FXMLLoader gameLoader = new
		// FXMLLoader(getClass().getResource("/views/GameView.fxml"));
		// Parent gameRoot = gameLoader.load();
		/*
		 * GameController gameController = gameLoader.getController();
		 * 
		 * // Obtener todas las celdas desde la base de datos List<Cell> allCells =
		 * cellDAO.getAll(); if (allCells == null || allCells.size() < 40) { Alert alert
		 * = new Alert(Alert.AlertType.ERROR); alert.setTitle("Error en el Tablero");
		 * alert.setHeaderText(null);
		 * alert.setContentText("No se pudieron cargar las 40 celdas del tablero.");
		 * alert.showAndWait(); return; }
		 * 
		 * // Crear un tablero y asignarle las celdas Board board = new Board(0, null,
		 * 0); board.setCells(allCells); board.setIdBoard(1); // o autogenerado si
		 * corresponde
		 */

		// Asignar correctamente todo al GameController
		/*
		 * gameController.setGame(newGame); gameController.setPlayers(players);
		 * gameController.setBoard(board); gameController.setCells(allCells); //
		 * necesario para dibujar el board gameController.setProperties(new
		 * ArrayList<>()); // si aún no tienes propiedades gameController.setCards(new
		 * ArrayList<>()); gameController.setChestCards(new ArrayList<>());
		 * gameController.setLuckyCards(new ArrayList<>());
		 * gameController.setPropertyCards(new ArrayList<>());
		 * gameController.setChestLuckyCards(new ArrayList<>());
		 */
	}

	public void turn() {
		int turn = 0;
		while (!isGameFinished()) {
			// Verificamos si el jugador está en bancarrota
			if (orderTurn[turn].getIsBankrupt()) {
				System.out.println("El jugador " + orderTurn[turn].getProfile().getNickname() + " está en bancarrota.");
				// Cambiamos al siguiente jugador
				turn++;
				if (turn >= orderTurn.length) {
					turn = 0;
				}
				break;
			} else {
				System.out.println("Turno del jugador " + orderTurn[turn].getProfile().getNickname());
				// Verificamos si el jugador está en la cárcel
				if (orderTurn[turn].getJailTurnsLeft() != 0) {
					rollDice();
					if (isDouble[0]) {
						// Si el jugador ha sacado dobles, lanza el dado de nuevo
						orderTurn[turn].setJailTurnsLeft(0);
						System.out.println("El jugador " + orderTurn[turn].getProfile().getNickname()
								+ " ha sacado dobles y sale de la cárcel.");
					} else {
						System.out.println("El jugador " + orderTurn[turn].getProfile().getNickname()
								+ " no ha sacado dobles y sigue en la cárcel.");
						orderTurn[turn].setJailTurnsLeft(orderTurn[turn].getJailTurnsLeft() - 1);

					}
				} else {
					// Si el jugador no está en la cárcel, lanza el dado
					rollDice();
					System.out.println("El jugador " + orderTurn[turn].getProfile().getNickname() + " ha sacado "
							+ dice1 + " y " + dice2);
					// TODO mirar lo del doble, porque no tiene sentido
					if (isDouble[0]) {
						// Si el jugador ha sacado dobles, lanza el dado de nuevo
						System.out.println("El jugador " + orderTurn[turn].getProfile().getNickname()
								+ " ha sacado dobles y lanza de nuevo.");
						rollDice();
					}
					// Avanzamos la ficha
					Cell actualCell = orderTurn[turn].getCell();
					int actualCellNumber = actualCell.getIdCell();
					int nextCellNumber = actualCellNumber + (dice1 + dice2);
					// TODO coger la celda de la base de datos
					// orderTurn[turn].setCell();
					Cell newCell = new Cell();
					switch (newCell.getType()) {
					case PROPERTY:
						handlePropertyCell(newCell, orderTurn[turn]);
						break;
					case JAIL:
						handleJailCell(orderTurn[turn]);
						break;
					case LUCK:
						handleLuckCell(newCell, orderTurn[turn]);
						break;
					case COMMUNITY_CHEST:
						handleCommunityChestCell(newCell, orderTurn[turn]);
						break;
					case START:
						handleStartCell(newCell, orderTurn[turn]);
						break;
					case TAX:
						handleTaxCell(newCell, orderTurn[turn]);
						break;
					default:
						break;
					}
					// Se termina el turno
					turn++;
					if (turn >= orderTurn.length) {
						turn = 0;
					}

					// TODO mirar si el jugador ha ganado o no

					// TODO Actualizamos el estado del jugador
				}
			}
		}
	}

	// TODO mirar si el estado del juego no está en playing y si el isFinished es
	// false
	public boolean isGameFinished() {
		return isFinished;
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

						if (i == 14) {
							// Guardamos el valor de los dados
							dice1 = randomNum1;
							dice2 = randomNum2;
							isDouble[0] = (dice1 == dice2);
						}

						Thread.sleep(50);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}

	// manejar la celda de propiedad
	public void handlePropertyCell(Cell cell, Player player) {
		System.out.println("Manejando celda de propiedad...");
		Property property = cell.getProperty();
		if (property != null) {
			boolean hasOwner = playerPropertyDAO.isPropertyOwned(property.getIdProperty(), actualGame.getIdGame());
			int ownerId = playerPropertyDAO.getPropertyOwner(property.getIdProperty(), actualGame.getIdGame());
			Player owner = playerDAO.findPlayerById(ownerId);
			if (!hasOwner) {
				comprarPropiedad(property, player);
			} else if (hasOwner && owner.getIdPlayer() != player.getIdPlayer()) {
				cobrarAlquiler(property, owner, player);
			} else {
				// TODO mirar si el jugador quiere comprar o vender, etc.
			}

		} else {
			System.out.println("No hay propiedad en esta celda.");
		}
	}

	// manejar la celda de cárcel
	public void handleJailCell(Player player) {
		System.out.println("Manejando celda de cárcel...");
		player.setJailTurnsLeft(3);
	}

	// manejar la celda de suerte
	public void handleLuckCell(Cell cell, Player player) {
		System.out.println("Manejando celda de suerte...");
		Card luckyCard = getRandomCard(CardType.LUCK);
		List<Card> cards = player.getCards();
		cards.add(luckyCard);
		player.setCards(cards);
	}

	// manejar la celda de cofre comunidad
	public void handleCommunityChestCell(Cell cell, Player player) {
		System.out.println("Manejando celda de cofre comunitario...");
		Card chestCard = getRandomCard(CardType.LUCK);
		List<Card> cards = player.getCards();
		cards.add(chestCard);
		player.setCards(cards);
	}

	// manejar la celda de salida
	public void handleStartCell(Cell cell, Player player) {
		System.out.println("Manejando celda de salida...");
		int startMoney = 200;
		int actualMoney = player.getMoney();
		int addedMoney = actualMoney + startMoney;
		player.setMoney(addedMoney);
	}

	// manejar la celda de impuestos
	public void handleTaxCell(Cell cell, Player player) {
		System.out.println("Manejando celda de impuestos...");
		int taxAmount = 200;
		int actualMoney = player.getMoney();
		if (checkIfPlayerCanPurchase(actualMoney, taxAmount)) {
			int substractedMoney = actualMoney - taxAmount;
			player.setMoney(substractedMoney);
		} else {
			player.setBankrupt(true);
		}
	}

	// TODO Lógica para obtener una carta aleatoria
	public Card getRandomCard(CardType cardType) {
		// Conseguimos todas las cartas de la base de datos
		// Conseguimos un número aleatorio entre 0 y el número de cartas
		// Devolvemos la carta
		return new Card();
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

	/**
	 * @author Ana
	 */
	public void comprarPropiedad(Property property, Player player) {
		System.out.println("Comprando propiedad...");
		boolean hasOwner = playerPropertyDAO.isPropertyOwned(property.getIdProperty(), actualGame.getIdGame());
		if (!hasOwner) {
			int propertyValue = property.getBuyValue();
			int actualMoney = player.getMoney();
			if (checkIfPlayerCanPurchase(actualMoney, propertyValue)) {
				int substractedMoney = actualMoney - propertyValue;
				player.setMoney(substractedMoney);
				List<Property> properties = player.getProperties();
				properties.add(property);
				player.setProperties(properties);
				playerPropertyDAO.addPlayerProperty(
						new PlayerProperty(player.getIdPlayer(), property.getIdProperty(), actualGame.getIdGame()));
			} else {
				player.setBankrupt(true);
			}
		} else {
			System.out.println("La propiedad ya tiene dueño");
		}
	}

	// TODO implementar la venta de la propiedad
	public void venderPropiedad(Property property, Player seller, Player buyer) {
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

	/**
	 * @author Ana
	 */
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
		if (checkIfPlayerCanPurchase(renter.getMoney(), rent)) {
			int substractedMoney = renter.getMoney() - rent;
			renter.setMoney(substractedMoney);
			int addedMoney = owner.getMoney() + rent;
			owner.setMoney(addedMoney);
		} else {
			renter.setBankrupt(true);
			System.out.println("El jugador " + renter.getProfile().getNickname() + " está en bancarrota.");
		}
	}

	// TODO
	public void mostrarCartaCofreSuerte() {
		System.out.println("Mostrando carta de cofre o suerte...");
	}

	@FXML
	public void seleccionarFichaJugador() {
		// Lógica para seleccionar la ficha del jugador
		/*
		 * if (jugadorActual != null) { jugadorActual.setCell(jugadorActual.getCell());
		 * fichaSeleccionadaJugador = "fichaSeleccionada.png"; // Cambia esto por la
		 * lógica real System.out.println("Ficha seleccionada: " +
		 * fichaSeleccionadaJugador); } else {
		 * System.out.println("Jugador no definido."); }
		 */
	}

	/**
	 * @author Ana
	 */
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

	// Método para establecer el juego
	public void setGame(Game game) {
		this.actualGame = game;
	}

	// Método para establecer los jugadores
	public void setPlayers(List<Profile> players) {
		if (players == null || players.isEmpty()) {
			System.out.println("Error: La lista de perfiles está vacía o no se ha cargado.");
		} else {
			System.out.println("Perfiles cargados correctamente: " + players.size());
			for (Profile profile : players) {
				System.out.println("Perfil: " + profile.getNickname());
			}
		}
		this.players = players;
	}
}
