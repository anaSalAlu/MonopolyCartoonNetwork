module MonopolyCartoonNetwork {
	requires javafx.controls;
	requires javafx.fxml;
	requires java.sql;

	opens application to javafx.graphics, javafx.fxml;

	exports application;
	exports controllers;

	opens controllers to javafx.fxml;
}
