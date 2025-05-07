package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ProfileController {

	@FXML
	private Button edit_image_button;

	@FXML
	private Button edit_name_nutton;

	public TextArea getName_text_area() {
		return name_text_area;
	}

	public void setName_text_area(TextArea name_text_area) {
		this.name_text_area = name_text_area;
	}

	public ImageView getPlayer_image() {
		return player_image;
	}

	public void setPlayer_image(ImageView player_image) {
		this.player_image = player_image;
	}

	@FXML
	private TextArea name_text_area;

	@FXML
	private Button goBackButton;

	@FXML
	private ImageView player_image;

	@FXML
	private Button save_button;
	static Scene previousScene;

	@FXML
	public void goBack(ActionEvent event) {
		try {

			// Restaurar la escena anterior si existe
			if (previousScene != null) {
				Stage stage = (Stage) goBackButton.getScene().getWindow();
				stage.setScene(previousScene);
				stage.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}