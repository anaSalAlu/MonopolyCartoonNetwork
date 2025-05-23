package controllers;

import java.net.URL;
import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.TiradaResultado;

public class RollDiceController {

	// TODO obtener el directorio resources
	public static final String RESOURCES_DIR = "src/main/resources";

	// public static final String DICE_IMAGE = RESOURCES_DIR + "/dice/dice0{0}.png";
	public static final String DICE_IMAGE = "images/dice/dice0{0}.png";

	@FXML
	private ImageView imageDiceFirst;

	@FXML
	private ImageView imageDiceSecond;

	@FXML
	private Button rollDice;

	@FXML
	private AnchorPane rootPane;

	private GameController gameController;
	private CompletableFuture<TiradaResultado> resultadoCallback;
	private int dice1;
	private int dice2;

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public void setResultadoCallback(CompletableFuture<TiradaResultado> callback) {
		this.resultadoCallback = callback;
	}

	@FXML
	public void rollDice() {
		Random random = new Random();
		Thread thread = new Thread(() -> {
			try {
				for (int i = 0; i < 15; i++) {
					int randomNum1 = random.nextInt(6) + 1;
					int randomNum2 = random.nextInt(6) + 1;

					String resourcePathFirst = MessageFormat.format(DICE_IMAGE, randomNum1);
					String resourcePathSecond = MessageFormat.format(DICE_IMAGE, randomNum2);

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
							// Final values of the dice
							dice1 = finalNum1;
							dice2 = finalNum2;
						}
					}

					Thread.sleep(50);
				}

				// Esperamos un poco después de la última cara para claridad
				Thread.sleep(300);

				// Terminamos el futuro en JavaFX thread
				Platform.runLater(() -> {
					if (resultadoCallback != null && !resultadoCallback.isDone()) {
						boolean esDoble = dice1 == dice2;
						resultadoCallback.complete(new TiradaResultado(dice1, dice2, esDoble));
					}
				});

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		thread.setDaemon(true); // se cierra con la app
		thread.start();
	}
}