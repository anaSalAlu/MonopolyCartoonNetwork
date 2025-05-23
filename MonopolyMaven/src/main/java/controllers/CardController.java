package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class CardController {

	@FXML
	private Button btnExecuteAction;

	@FXML
	private ImageView imgCard;

	@FXML
	private AnchorPane rootPane;

	@FXML
	void executeAction(ActionEvent event) {

	}

	// TODO crear método de conseguir una carta aleatoria para que se muestre
	// TODO cuando se le de al botón se tiene que ejecutar la acción o bien pasar la
	// acción para que se ejecute en el GameController

}
