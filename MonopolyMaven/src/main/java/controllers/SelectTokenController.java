package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

public class SelectTokenController {
	public static final String[] TOKENS_IMAGES = { "/images/tokens/gema.png", "/images/tokens/mascara.png",
			"/images/tokens/pankake.png", "/images/tokens/probeta.png", "/images/tokens/rinyonera.png",
			"/images/tokens/tabla.png" };
	// TODO crear el botón y el tilePane

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TilePane tokenPane;

	private GameController gameController;

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	@FXML
	public void initialize() {
		for (String imagePath : TOKENS_IMAGES) {
			Image image = new Image(imagePath, true);
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(90);
			imageView.setFitHeight(90);
			imageView.setPreserveRatio(true);
			imageView.setCursor(Cursor.HAND);

			imageView.setOnMouseClicked(e -> {
				gameController.setSelectedToken(imagePath);
			});

			tokenPane.getChildren().add(imageView);
		}
	}

	@FXML
	public void selectToken(ActionEvent event) {
		// TODO recoger la ficha del TilePane
		String token = "";
		// gameController.fichaSeleccionada(token);
	}
}
