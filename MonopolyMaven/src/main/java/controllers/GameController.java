package controllers;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import dao.CellDAO;
import dao.DAOManager;
import dao.GameDAO;
import dao.PlayerCardDAO;
import dao.PlayerDAO;
import dao.PlayerPropertyDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
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

	public static final String[] TOKENS_IMAGES = { "/images/tokens/gema.png", "/images/tokens/mascara.png",
			"/images/tokens/pankake.png", "/images/tokens/probeta.png", "/images/tokens/rinyonera.png",
			"/images/tokens/tabla.png" };
	public static final String DICE_IMAGE = "images/dice/dice0{0}.png";
	private static final int TOTAL_NUM_CELLS = 40;

	@FXML
	private Button exitButton;
	@FXML
	private GridPane board_game;

	@FXML
	private Label lblFirstPlayer;

	@FXML
	private Label lblFourthPlayer;

	@FXML
	private Text lblPlayerName;

	@FXML
	private ImageView imgPlayerPhoto;

	@FXML
	private Label lblSecondPlayer;

	@FXML
	private Label lblThirdPlayer;

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
	private boolean faseInicial = true;
	private List<Player> orderTurn = new ArrayList<Player>();
	List<Player> allPlayers = new ArrayList<Player>();
	private List<Profile> selectedProfiles = new ArrayList<Profile>();
	private List<String> selectedTokens = new ArrayList<String>();
	private List<Card> playerCards = new ArrayList<Card>();
	private List<Property> playerProperties = new ArrayList<Property>();
	private List<Cell> cells = new ArrayList<Cell>();
	private List<TiradaJugador> tiradas = new ArrayList<>();
	private Map<Profile, Integer> tiradasPorJugador = new HashMap<>();
	private Consumer<TiradaResultado> resultadoCallback;

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

		// Creamos las celdas
		cells = cellDAO.getAll();
		System.out.println("Número de celdas cargadas: " + cells.size());
		for (Cell c : cells) {
			System.out.println("Celda ID: " + c.getIdCell() + ", tipo: " + c.getType());
		}

		// Cremos el tablero
		board = new Board(1, cells);

		// Iniciar selección de tokens uno a uno
		selectedTokens.clear();
		for (Profile profile : selectedProfiles) {
			seleccionarFichaJugador();
			// initPlayers();
		}

	}

	private void initPlayers() {
		for (int i = 0; i < selectedProfiles.size(); i++) {
			Player player = new Player();

			player.setProfile(selectedProfiles.get(i));
			for (Cell cell : cells) {
				if (cell.getType() == Cell.CellType.START) {
					player.setCell(cell);
					break;
				}
			}
			player.setMoney(1500);
			player.setCards(new ArrayList<Card>());
			player.setProperties(new ArrayList<Property>());
			player.setGame(actualGame);
			player.setBankrupt(false);
			player.setJailTurnsLeft(0);
			player.setToken(selectedTokens.get(i));

			allPlayers.add(player);

			// Creamos el jugador en la base de datos
			playerDAO.addPlayer(player);
			System.out.println("Jugador " + player.getProfile().getNickname() + " creado con éxito.");
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
			} else if (hasOwner && owner != null && owner.getIdPlayer() != player.getIdPlayer()) {
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

	private void seleccionarFichaJugador() {
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		TilePane tilePane = new TilePane();
		tilePane.setPrefColumns(4);
		tilePane.setPrefRows(2);
		for (String imagePath : TOKENS_IMAGES) {
			Image image = new Image(imagePath, true);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(90);
			imageView.setFitHeight(90);
			imageView.setPreserveRatio(true);
			imageView.setCursor(Cursor.HAND);

			imageView.setOnMouseClicked(e -> {
				selectedTokens.add(imagePath);
				System.out.println("Ficha seleccionada: " + imagePath);

				currentProfileIndex++;
				if (currentProfileIndex < selectedProfiles.size()) {
					seleccionarFichaJugador();
				} else {
					// todos seleccionaron, ir al juego
					initPlayers();
					mostrarPanelOrdenDeTurno();
				}
			});

			tilePane.getChildren().add(imageView);
		}
		centerPane.getChildren().clear();
		centerPane.getChildren().add(scrollPane);
		scrollPane.setContent(tilePane);
	}

	private void mostrarPanelOrdenDeTurno() {
		VBox vbox = new VBox(20);
		vbox.setAlignment(Pos.CENTER);

		// Jugador actual
		lblPlayerName.setText(selectedProfiles.get(0).getNickname());
		// lblPlayerName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
		imgPlayerPhoto.setImage(new Image(selectedProfiles.get(0).getImage()));
		imgPlayerPhoto.setFitWidth(55);
		imgPlayerPhoto.setFitHeight(55);

		imageDiceFirst = new ImageView();
		imageDiceSecond = new ImageView();
		imageDiceFirst.setFitWidth(80);
		imageDiceFirst.setPreserveRatio(true);
		imageDiceSecond.setFitWidth(80);
		imageDiceSecond.setPreserveRatio(true);

		Button lanzarButton = new Button("Lanzar dados");
		currentProfileIndex = 0;
		lanzarButton.setOnAction(e -> {
			lanzarButton.setDisable(true);

			// Obtener jugador actual
			Profile jugador = selectedProfiles.get(currentProfileIndex);

			// Definir el callback para cuando termine de lanzar
			resultadoCallback = resultado -> {
				int total = resultado.getDado1() + resultado.getDado2();

				if (faseInicial) {
					tiradasPorJugador.put(jugador, total);
					currentProfileIndex++;
					Platform.runLater(() -> {
						avanzarTurnoLanzamiento();
						lanzarButton.setDisable(false);
					});
				}
			};

			// Iniciar tirada
			rollDice();
		});

		vbox.getChildren().addAll(imageDiceFirst, imageDiceSecond, lanzarButton);

		centerPane.getChildren().clear();
		centerPane.getChildren().add(vbox);

		avanzarTurnoLanzamiento();
	}

	private void avanzarTurnoLanzamiento() {
		if (currentProfileIndex >= selectedProfiles.size()) {
			if (faseInicial) {
				System.out.println("Todos los jugadores han lanzado los dados para determinar el orden.");
				determinarOrdenTurno();
			} else {
				// Pasar al siguiente jugador
				currentProfileIndex = 0;
			}
			return;
		}

		Profile jugador = selectedProfiles.get(currentProfileIndex);
		lblPlayerName.setText(jugador.getNickname());
		imgPlayerPhoto.setImage(new Image(jugador.getImage()));
	}

	private void determinarOrdenTurno() {
		List<Profile> ordenFinal = selectedProfiles.stream().filter(p -> tiradasPorJugador.containsKey(p))
				.sorted(Comparator.comparingInt(p -> tiradasPorJugador.get(p)).reversed()).collect(Collectors.toList());

		orderTurn.clear();
		for (Profile p : ordenFinal) {
			for (Player jugador : allPlayers) {
				if (jugador.profile.equals(p)) {
					orderTurn.add(jugador);
					break;
				}
			}
		}

		faseInicial = false;
		currentProfileIndex = 0;
		startGame();
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

						if (resourceUrlFirst != null && resourceUrlSecond != null) {
							Image diceFace1 = new Image(resourceUrlFirst.toString());
							Image diceFace2 = new Image(resourceUrlSecond.toString());

							int finalNum1 = randomNum1;
							int finalNum2 = randomNum2;

							Platform.runLater(() -> {
								imageDiceFirst.setImage(diceFace1);
								imageDiceSecond.setImage(diceFace2);
							});

							if (i == 14) {
								dice1 = finalNum1;
								dice2 = finalNum2;

								// Llamamos al callback con el resultado final de los dados
								TiradaResultado resultado = new TiradaResultado(dice1, dice2);
								Platform.runLater(() -> {
									if (resultadoCallback != null) {
										resultadoCallback.accept(resultado);
									}
								});
							}
						} else {
							System.out.println("No se encontró la imagen:");
							System.out.println("   " + resourcePathFirst + " -> " + resourceUrlFirst);
							System.out.println("   " + resourcePathSecond + " -> " + resourceUrlSecond);
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

	public void startGame() {
		turnIndex = 0;
		startTurn();
	}

	private void startTurn() {
		Player currentPlayer = orderTurn.get(turnIndex);
		mostrarPanelTurnoJugador(currentPlayer.getProfile());
	}

	private void mostrarPanelTurnoJugador(Profile perfilJugador) {
		VBox vbox = new VBox(20);
		vbox.setAlignment(Pos.CENTER);

		lblPlayerName.setText(perfilJugador.getNickname());
		imgPlayerPhoto.setImage(new Image(perfilJugador.getImage()));
		imgPlayerPhoto.setFitWidth(55);
		imgPlayerPhoto.setFitHeight(55);

		imageDiceFirst = new ImageView();
		imageDiceSecond = new ImageView();
		imageDiceFirst.setFitWidth(80);
		imageDiceFirst.setPreserveRatio(true);
		imageDiceSecond.setFitWidth(80);
		imageDiceSecond.setPreserveRatio(true);

		Button lanzarButton = new Button("Lanzar dados");

		lanzarButton.setOnAction(e -> {
			lanzarButton.setDisable(true);

			resultadoCallback = resultado -> {
				int dado1 = resultado.getDado1();
				int dado2 = resultado.getDado2();
				int total = dado1 + dado2;

				Player actualPlayer = orderTurn.get(turnIndex);

				if (actualPlayer.getIsBankrupt()) {
					System.out
							.println("El jugador " + actualPlayer.getProfile().getNickname() + " está en bancarrota.");
					turnIndex++;
					if (turnIndex >= orderTurn.size()) {
						turnIndex = 0;
					}
					resultadoCallback = null;
					return;
				}

				ejecutarMovimiento(actualPlayer, dado1, dado2);
				resultadoCallback = null;

				Button siguienteTurno = new Button("Terminar turno");
				siguienteTurno.setOnAction(ev -> {
					turnIndex++;
					if (turnIndex >= orderTurn.size()) {
						turnIndex = 0;
					}
					startTurn();
				});

				Platform.runLater(() -> {
					vbox.getChildren().add(siguienteTurno);
				});
			};

			rollDice();
		});

		vbox.getChildren().addAll(imageDiceFirst, imageDiceSecond, lanzarButton);

		centerPane.getChildren().clear();
		centerPane.getChildren().add(vbox);
	}

	public void ejecutarMovimiento(Player actualPlayer, int dado1, int dado2) {
		if (isGameFinished()) {
			System.out.println("El juego ha terminado. No se puede ejecutar el movimiento.");
			// mostrarMensajeJuegoTerminado();
		}

		System.out.println("Turno del jugador " + actualPlayer.getProfile().getNickname());
		// Verificamos si el jugador está en la cárcel
		if (actualPlayer.getJailTurnsLeft() != 0) {
			if (dado1 == dado2) {
				// Si el jugador ha sacado dobles, lanza el dado de nuevo
				actualPlayer.setJailTurnsLeft(0);
				System.out.println("El jugador " + actualPlayer.getProfile().getNickname()
						+ " ha sacado dobles y sale de la cárcel.");
			} else {
				System.out.println("El jugador " + actualPlayer.getProfile().getNickname()
						+ " no ha sacado dobles y sigue en la cárcel.");
				actualPlayer.setJailTurnsLeft(actualPlayer.getJailTurnsLeft() - 1);

			}
			return;
		} else {
			// Si el jugador no está en la cárcel, lanza el dado
			System.out.println(
					"El jugador " + actualPlayer.getProfile().getNickname() + " ha sacado " + dado1 + " y " + dado2);
			// TODO mirar lo del doble, porque no tiene sentido
			if (dado1 == dado2) {
				// Si el jugador ha sacado dobles, lanza el dado de nuevo
				System.out.println("El jugador " + actualPlayer.getProfile().getNickname()
						+ " ha sacado dobles y lanza de nuevo.");
				// rollDice();
			}
			// Avanzamos la ficha
			Cell actualCell = actualPlayer.getCell();
			int actualCellNumber = actualCell.getIdCell();
			System.out.println("Celda actual: " + actualCellNumber + ", dados: " + dado1 + " + " + dado2);
			int nextCellNumber = (actualCellNumber + dado1 + dado2) % TOTAL_NUM_CELLS;
			System.out.println("Moviendo a la celda con ID: " + nextCellNumber);
			// TODO coger la celda de la base de datos
			Cell newCell = cellDAO.findCellById(nextCellNumber);
			actualPlayer.setCell(newCell);
			switch (newCell.getType()) {
			case PROPERTY:
				handlePropertyCell(newCell, actualPlayer);
				break;
			case JAIL:
				handleJailCell(actualPlayer);
				break;
			case LUCK:
				handleLuckCell(newCell, actualPlayer);
				break;
			case COMMUNITY_CHEST:
				handleCommunityChestCell(newCell, actualPlayer);
				break;
			case START:
				handleStartCell(newCell, actualPlayer);
				break;
			case TAX:
				handleTaxCell(newCell, actualPlayer);
				break;
			default:
				break;
			}

			// TODO mirar si el jugador ha ganado o no

			// TODO Actualizamos el estado del jugador
		}
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

	// TODO cambiar los parámetros y nombres para que se ponga con los nombres
	// correspondientes

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

}