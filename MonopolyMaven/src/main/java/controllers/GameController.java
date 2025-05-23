
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import dao.CellDAO;
import dao.DAOManager;
import dao.GameDAO;
import dao.PlayerCardDAO;
import dao.PlayerDAO;
import dao.PlayerPropertyDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Board;
import models.Card;
import models.Card.CardType;
import models.Cell;
import models.Game;
import models.Game.State;
import models.Player;
import models.PlayerProperty;
import models.Profile;
import models.Property;
import models.RentHouseValue;
import models.TiradaJugador;
import models.TiradaResultado;

public class GameController {

	@FXML
	private Button exitButton;
	@FXML
	private GridPane board_game;

	// TODO quitar todos estos textos e imágenes y simplemente poner un texto y una
	// imagen
	// con el jugador actual, no ponemos los demás jugadores, si acaso, ponemos un
	// pequeño
	// list view y que salga el orden de juego para saber a quién le toca el
	// siguiente y ya
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

	// DICE

	@FXML
	private ImageView imageDiceFirst;

	@FXML
	private ImageView imageDiceSecond;

	@FXML
	private Button rollDice;

	// TODO hay que crear un ImageView por cada ficha del jugador o sea se 4

	// TODO ponerle un id al AnchorPane del centro del tablero y ponerlo aquí
	@FXML
	private AnchorPane centerPane;

	// Game
	private Game actualGame;
	private Boolean isFinished = false;
	private Board board;
	private int dice1;
	private int dice2;
	private boolean[] isDouble;
	private Player actualPlayer;
	private int currentProfileIndex = 0;
	private int turnIndex = 0;
	private List<Player> orderTurn = new ArrayList<Player>();
	private List<Profile> selectedProfiles = new ArrayList<Profile>();
	private List<String> selectedTokens = new ArrayList<String>();
	private List<Card> playerCards = new ArrayList<Card>();
	private List<Property> playerProperties = new ArrayList<Property>();
	private List<Cell> cells = new ArrayList<Cell>();
	private List<TiradaJugador> tiradas = new ArrayList<>();

	// DAOs
	private DAOManager daoManager = new DAOManager();
	private GameDAO gameDAO = daoManager.getGameDAO();
	private CellDAO cellDAO = daoManager.getCellDAO();
	private PlayerDAO playerDAO = daoManager.getPlayerDAO();
	private PlayerCardDAO playerCardDAO = daoManager.getPlayerCardDAO();
	private PlayerPropertyDAO playerPropertyDAO = daoManager.getPlayerPropertyDAO();

	// TODO mirar esto porque igual ni lo necesito
	public void setGame(Game game) {
		this.actualGame = game;
	}

	public void setSelectedToken(String path) {
		this.selectedTokens.add(path);
	}

	public void setProfiles(List<Profile> selectedProfiles) {
		this.selectedProfiles = selectedProfiles;
		if (selectedProfiles == null || selectedProfiles.isEmpty()) {
			System.out.println("Error: Los perfiles no se han cargado en el GameController.");
			return;
		} else {
			initGame();
		}
	}

	private void initGame() {
		if (selectedProfiles == null || selectedProfiles.isEmpty()) {
			System.out.println("Error: Los perfiles no se han cargado en el GameController.");
		} else {
			System.out.println("Perfiles cargados en el GameController: " + selectedProfiles.size());
			for (Profile profile : selectedProfiles) {
				System.out.println("Perfil: " + profile.getNickname());
			}
		}
		// centerPane.setVisible(false);

		// Creamos el juego
		actualGame = new Game();
		actualGame.setDuration("60");
		actualGame.setState(State.IN_GAME);

		// Creamos el juego en la base de datos
		gameDAO.addGame(actualGame);

		// Iniciar selección de tokens uno a uno
		selectedTokens.clear();
		seleccionarTokenParaPerfil(0);
	}

	private void seleccionarTokenParaPerfil(int index) {
		if (index >= selectedProfiles.size()) {
			// Ya se seleccionaron todos los tokens, continuar con el juego
			inicializarDespuesDeSeleccionTokens();
			ocultarPanelSeleccionFicha();
			return;
		}

		Profile perfilActual = selectedProfiles.get(index);
		CompletableFuture<String> futureToken = new CompletableFuture<>();

		loadCentralView("/views/SelectTokenView.fxml", controlador -> {
			if (controlador instanceof SelectTokenController) {
				((SelectTokenController) controlador).setGameController(this);
				((SelectTokenController) controlador).setTokenSelectedFuture(futureToken);
			}
		});

		futureToken.thenAccept(token -> {
			selectedTokens.add(token);
			seleccionarTokenParaPerfil(index + 1); // Repetimos para el siguiente perfil
			System.out.println("Token seleccionado para " + perfilActual.getNickname() + ": " + token);
		});
	}

	private void inicializarDespuesDeSeleccionTokens() {
		cells = cellDAO.getAll();
		board = new Board(1, cells);
		initPlayers();
		decidirOrdenTurno();
	}

	public void decidirOrdenTurno() {
		tiradas.clear();
		pedirTiradaSecuencial(0);
	}

	private void pedirTiradaSecuencial(int index) {
		if (index >= orderTurn.size()) {
			// Ya todos han tirado. Ordenar por resultado (mayor suma de dados primero)
//			tiradas.sort(Comparator.comparingInt(t -> -(t.resultado.dado1 + t.resultado.dado2)));
//			orderTurn = tiradas.stream().map(t -> t.jugador).collect(Collectors.toList());
//
//			
//
//			Platform.runLater(() -> {
//				System.out.println("Orden de turnos decidido:");
//				for (Player p : orderTurn) {
//					System.out.println(p.getProfile().getNickname());
//				}
//			});

			// Ordenamos las tiradas según la suma de los dados (mayor primero)
			tiradas.sort(Comparator.comparingInt(t -> -(t.resultado.dado1 + t.resultado.dado2)));

			// Limpiamos la lista existente y le agregamos los jugadores en orden
			orderTurn.clear();
			for (TiradaJugador t : tiradas) {
				orderTurn.add(t.jugador);
				System.out.println("Jugador " + t.jugador.getProfile().getNickname() + " tiró: "
						+ (t.resultado.dado1 + t.resultado.dado2));
			}

			ocultarPanelSeleccionFicha();
			startTurnCycle();
			return;
		}

		Player jugador = orderTurn.get(index);
		CompletableFuture<TiradaResultado> future = new CompletableFuture<>();

		Platform.runLater(() -> {
			loadCentralView("/views/RollDice.fxml", controller -> {
				if (controller instanceof RollDiceController) {
					RollDiceController c = (RollDiceController) controller;
					c.setGameController(this);
					c.setResultadoCallback(future);
				}
			});
		});

		future.thenAccept(resultado -> {
			tiradas.add(new TiradaJugador(jugador, resultado));
			pedirTiradaSecuencial(index + 1);
		});
	}

	private void initPlayers() {
		for (int i = 0; i < selectedProfiles.size(); i++) {
			Player player = new Player();

			player.profile = selectedProfiles.get(i);
			player.cell = cells.get(0);
			player.money = 1500;
			player.cards = new ArrayList<>();
			player.properties = new ArrayList<>();
			player.game = actualGame;
			player.isBankrupt = false;
			player.jailTurnsLeft = 0;

			orderTurn.add(player);

			// Creamos el jugador en la base de datos
			playerDAO.addPlayer(player);
		}
	}

//	public void turn() {
//		int turn = 0;
//		while (!isGameFinished()) {
//			// Conseguimos el jugador actual
//			actualPlayer = orderTurn.get(turn);
//			// Verificamos si el jugador está en bancarrota
//			if (actualPlayer.getIsBankrupt()) {
//				System.out.println("El jugador " + actualPlayer.getProfile().getNickname() + " está en bancarrota.");
//				// Cambiamos al siguiente jugador
//				turn++;
//				if (turn >= orderTurn.size()) {
//					turn = 0;
//				}
//				break;
//			} else {
//				System.out.println("Turno del jugador " + actualPlayer.getProfile().getNickname());
//				// Verificamos si el jugador está en la cárcel
//				if (actualPlayer.getJailTurnsLeft() != 0) {
//					// rollDice();
//					if (isDouble[0]) {
//						// Si el jugador ha sacado dobles, lanza el dado de nuevo
//						actualPlayer.setJailTurnsLeft(0);
//						System.out.println("El jugador " + actualPlayer.getProfile().getNickname()
//								+ " ha sacado dobles y sale de la cárcel.");
//					} else {
//						System.out.println("El jugador " + actualPlayer.getProfile().getNickname()
//								+ " no ha sacado dobles y sigue en la cárcel.");
//						actualPlayer.setJailTurnsLeft(actualPlayer.getJailTurnsLeft() - 1);
//
//					}
//				} else {
//					// Si el jugador no está en la cárcel, lanza el dado
//					// rollDice();
//					System.out.println("El jugador " + actualPlayer.getProfile().getNickname() + " ha sacado " + dice1
//							+ " y " + dice2);
//					// TODO mirar lo del doble, porque no tiene sentido
//					if (isDouble[0]) {
//						// Si el jugador ha sacado dobles, lanza el dado de nuevo
//						System.out.println("El jugador " + actualPlayer.getProfile().getNickname()
//								+ " ha sacado dobles y lanza de nuevo.");
//						// rollDice();
//					}
//					// Avanzamos la ficha
//					Cell actualCell = actualPlayer.getCell();
//					int actualCellNumber = actualCell.getIdCell();
//					int nextCellNumber = actualCellNumber + (dice1 + dice2);
//					// TODO coger la celda de la base de datos
//					// actualPlayer.setCell();
//					Cell newCell = new Cell();
//					switch (newCell.getType()) {
//					case PROPERTY:
//						handlePropertyCell(newCell, actualPlayer);
//						break;
//					case JAIL:
//						handleJailCell(actualPlayer);
//						break;
//					case LUCK:
//						handleLuckCell(newCell, actualPlayer);
//						break;
//					case COMMUNITY_CHEST:
//						handleCommunityChestCell(newCell, actualPlayer);
//						break;
//					case START:
//						handleStartCell(newCell, actualPlayer);
//						break;
//					case TAX:
//						handleTaxCell(newCell, actualPlayer);
//						break;
//					default:
//						break;
//					}
//					// Se termina el turno
//					turn++;
//					if (turn >= orderTurn.size()) {
//						turn = 0;
//					}
//
//					// TODO mirar si el jugador ha ganado o no
//
//					// TODO Actualizamos el estado del jugador
//				}
//			}
//		}
//	}

	public void startTurnCycle() {
		if (isGameFinished()) {
			mostrarPantallaFinJuego();
			return;
		}

		actualPlayer = orderTurn.get(turnIndex);

		if (actualPlayer.getIsBankrupt()) {
			System.out.println("El jugador " + actualPlayer.getProfile().getNickname() + " está en bancarrota.");
			avanzarTurno();
			return;
		}

		System.out.println("Turno del jugador " + actualPlayer.getProfile().getNickname());

		if (actualPlayer.getJailTurnsLeft() != 0) {
			mostrarVistaTiradaDados(resultado -> {
				dice1 = resultado.dado1;
				dice2 = resultado.dado2;
				isDouble[0] = (dice1 == dice2);

				if (isDouble[0]) {
					actualPlayer.setJailTurnsLeft(0);
					System.out.println("Sale de la cárcel por dobles.");
				} else {
					actualPlayer.setJailTurnsLeft(actualPlayer.getJailTurnsLeft() - 1);
					System.out.println("Sigue en la cárcel.");
				}

				avanzarTurno();
			});
		} else {
			mostrarVistaTiradaDados(resultado -> {
				dice1 = resultado.dado1;
				dice2 = resultado.dado2;
				isDouble[0] = (dice1 == dice2);

				System.out.println("Ha sacado " + dice1 + " y " + dice2);

				moverJugador(actualPlayer, () -> {
					Cell nuevaCelda = actualPlayer.getCell();

					switch (nuevaCelda.getType()) {
					case PROPERTY:
						handlePropertyCell(nuevaCelda, actualPlayer, this::avanzarTurno);
					case JAIL:
						handleJailCell(actualPlayer, this::avanzarTurno);
					case LUCK:
						handleLuckCell(nuevaCelda, actualPlayer, this::avanzarTurno);
					case COMMUNITY_CHEST:
						handleCommunityChestCell(nuevaCelda, actualPlayer, this::avanzarTurno);
					case START:
						handleStartCell(nuevaCelda, actualPlayer, this::avanzarTurno);
					case TAX:
						handleTaxCell(nuevaCelda, actualPlayer, this::avanzarTurno);
					default:
						avanzarTurno();
					}
				});
			});
		}
	}

	private void avanzarTurno() {
		turnIndex = (turnIndex + 1) % orderTurn.size();
		Platform.runLater(this::startTurnCycle);
	}

	private void mostrarVistaTiradaDados(Consumer<TiradaResultado> callback) {
		loadCentralView("/views/RollDice.fxml", controller -> {
			if (controller instanceof RollDiceController c) {
				c.setGameController(this);
				// c.setResultadoCallback(callback);
			}
		});
	}

	private void moverJugador(Player jugador, Runnable onFinish) {
		int nextId = jugador.getCell().getIdCell() + (dice1 + dice2);
		Cell nextCell = cellDAO.findCellById(nextId);

		jugador.setCell(nextCell);
		System.out
				.println("Jugador " + jugador.getProfile().getNickname() + " se mueve a celda " + nextCell.getIdCell());

		onFinish.run(); // continúa al manejar la celda
	}

	private void handlePropertyCell(Cell cell, Player player, Runnable onFinish) {
		loadCentralView("/views/PropertyCardView.fxml", controller -> {
			if (controller instanceof PropertyCardController c) {
				c.mostrarCarta(cell, player, onFinish);
			}
		});
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

	// TODO cambiar los parámetros y nombres para que se ponga con los nombres
	// correspondientes
	public void loadCentralView(String nombreFXML, Consumer<Object> configurador) {
		try {
			centerPane.setVisible(true);
			System.out.println("Intentando cargar FXML: " + nombreFXML);
			URL resource = getClass().getResource(nombreFXML);
			System.out.println("Recurso cargado? " + (resource != null) + " -> " + resource);
			FXMLLoader loader = new FXMLLoader(resource);
			Parent vista = loader.load();

			Object controlador = loader.getController();

			// Lógica personalizada para configurar el controlador hijo
			if (configurador != null) {
				configurador.accept(controlador);
			}

			centerPane.getChildren().clear();
			centerPane.getChildren().add(vista);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ocultarPanelSeleccionFicha() {

		// TODO cambiar el contenedorCentral por el contenedor que toca que es el del
		// centro
		// Aquí ocultas la vista (por ejemplo, limpias el panel)
		centerPane.getChildren().clear();
		// TODO hacerlo transparente después para que se vea el centro del tablero
		// centerPane.setVisible(false);
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

	// TODO mirar si esto es necesario, que yo creo que no
	// Método para establecer los jugadores
	/*
	 * public void setPlayers(List<Profile> players) { if (players == null ||
	 * players.isEmpty()) { System.out.
	 * println("Error: La lista de perfiles está vacía o no se ha cargado."); } else
	 * { System.out.println("Perfiles cargados correctamente: " + players.size());
	 * for (Profile profile : players) { System.out.println("Perfil: " +
	 * profile.getNickname()); } } this.players = players; }
	 */
}