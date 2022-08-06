package org.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NumerologyApplication extends Application {
	private final String START_FXML_NAME = "/fxmls/startPage.fxml";


	@Override
	public void start(Stage stage){
		try{
			Parent root = FXMLLoader.load(getClass().getResource(START_FXML_NAME));
			stage.setTitle("Numerológa");
			stage.setResizable(false);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException ioe){
			System.out.println("Cannot find startPage.fxml");
		}
	}
}
