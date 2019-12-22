package application;

import java.io.File;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Controllers {
	@FXML
	private JFXButton openDB;
	@FXML
	private JFXPasswordField txtPswField;
	@FXML
	private AnchorPane menu;
	@FXML
	private AnchorPane promptDB;
	private String fileAddress;

	public void selectFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("AES Encrypted files", "*.aes"));
		File selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null) {
			openDB.setFont(Font.font("System", 12));
			openDB.setText(selectedFile.getAbsolutePath());
			fileAddress = selectedFile.getAbsolutePath();
		}

	}

	public void Login(ActionEvent event) throws Exception {
		GaussianBlur gaussianBlur = new GaussianBlur();
		if (fileAddress != null) 
		if (!SecureFileIO.fileDecrypt(txtPswField.getText(), fileAddress)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Wrong password or corrupted database");
			alert.showAndWait();
		}
		else {
			gaussianBlur.setRadius(0);
			menu.setEffect(gaussianBlur);
			promptDB.setVisible(false);
		}
		
	}
}
