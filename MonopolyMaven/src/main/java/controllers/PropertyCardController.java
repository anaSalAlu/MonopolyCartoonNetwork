package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import models.Cell;
import models.Player;

public class PropertyCardController {

	@FXML
	private Button btnBuyProperty;

	@FXML
	private ImageView imgProperty;

	@FXML
	private AnchorPane rootPane;

	@FXML
	void buyProperty(ActionEvent event) {

	}

	public void mostrarCarta(Cell cell, Player player, Runnable onFinish) {
		// TODO Auto-generated method stub

	}

}
