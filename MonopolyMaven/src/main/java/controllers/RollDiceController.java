package controllers;

import java.util.concurrent.CompletableFuture;

import javafx.fxml.FXML;
import models.TiradaResultado;

public class RollDiceController {

	private GameController gameController;
	private CompletableFuture<TiradaResultado> futureResultado;

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public void setResultadoCallback(CompletableFuture<TiradaResultado> futureResultado) {
		this.futureResultado = futureResultado;
	}

	@FXML
	public void onClickTirarDados() {
		int dado1 = (int) (Math.random() * 6) + 1;
		int dado2 = (int) (Math.random() * 6) + 1;
		boolean esDoble = dado1 == dado2;

		futureResultado.complete(new TiradaResultado(dado1, dado2, esDoble));
	}
}