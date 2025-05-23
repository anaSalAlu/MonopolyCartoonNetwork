package controllers;

import java.util.concurrent.CompletableFuture;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

/**
 * 
 * @author Ana
 */
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
	private CompletableFuture<String> tokenSelectedFuture;

	public void setGameController(GameController gameController) {
		this.gameController = gameController;
	}

	public void setTokenSelectedFuture(CompletableFuture<String> tokenSelectedFuture) {
		this.tokenSelectedFuture = tokenSelectedFuture;
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
				if (tokenSelectedFuture != null && !tokenSelectedFuture.isDone()) {
					tokenSelectedFuture.complete(imagePath);

				}
			});

			tokenPane.getChildren().add(imageView);
		}
	}
}
