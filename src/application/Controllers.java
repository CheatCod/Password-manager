package application;

import java.io.File;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Controllers {
	@FXML
	private JFXButton openDB;
	public void selectFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("AES Encrypted files", "*.aes"));
		File selectedFile = fc.showOpenDialog(null);
		openDB.setFont(Font.font("System", 12));
		openDB.setText(selectedFile.getAbsolutePath());
		
	}
	
}
