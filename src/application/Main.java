package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	final KeyCombination kb = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
	@SuppressWarnings("rawtypes")
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/resources/App login.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setTitle("Password Manager");
		Controllers controllers = new Controllers();
		Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
		primaryStage.getIcons().add(icon);
		
		primaryStage.setMinHeight(585);
		primaryStage.setMinWidth(815);
		primaryStage.setScene(scene);
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.C && event.isControlDown()) { 
					System.out.println("key combo pressed");
					controllers.addToClipBoard(controllers.password1.getText());
			    }
			}
		});
			
		primaryStage.show();

		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

	public void stop() throws Exception {
		if (Controllers.dbPassword != null && Controllers.fileAddress != null) {
			SecureFileIO.fileEncrypt(Controllers.dbPassword, Controllers.fileAddress);
			System.out.println("Application closed");
		} else
			System.out.println("Application closed");

	}
}
