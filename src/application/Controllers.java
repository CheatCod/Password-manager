package application;

import java.io.File;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import com.google.gson.*;

public class Controllers {
	@FXML
	protected JFXButton openDB;
	@FXML
	protected JFXButton switchDB;
	@FXML
	protected JFXPasswordField txtPswField;
	@FXML
	protected AnchorPane menu;
	@FXML
	protected AnchorPane promptDB;
	@FXML
	protected JFXButton creatDBButton;
	@FXML 
	protected AnchorPane createDBPage;
	@FXML
	protected Pane banner;
	@FXML
	protected MaterialDesignIconView lock;
	@FXML
	protected JFXButton ChooseDirectory;
	@FXML 
	protected JFXPasswordField setPasswordDB;
	@FXML
	protected JFXTextField setNameDB;

	protected String fileAddress;
	protected String folderAddress;

	public void selectFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("AES Encrypted files", "*.aes"));
		File selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null) {
			openDB.setFont(Font.font("System", 12));
			openDB.setText(selectedFile.getAbsolutePath());
			fileAddress = selectedFile.getAbsolutePath();
			lock.setVisible(false);
		}

	}
	public void createDB(ActionEvent event) {
		createDBPage.setVisible(true);
	}

	public void switchDB(ActionEvent event) {
		GaussianBlur gaussianBlur = new GaussianBlur();
		promptDB.setVisible(true);
		createDBPage.setVisible(false);
		menu.setDisable(true);
		menu.setEffect(gaussianBlur);
		txtPswField.setText("");
	}
	public void Login(ActionEvent event) throws Exception {
		GaussianBlur gaussianBlur = new GaussianBlur();
		if (fileAddress != null) 
			if (!SecureFileIO.fileDecrypt(txtPswField.getText(), fileAddress)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("Wrong password or corrupted database");
				alert.showAndWait();
			}
			else {
				gaussianBlur.setRadius(0);
				menu.setEffect(gaussianBlur);
				promptDB.setVisible(false);
				menu.setMouseTransparent(false);
				menu.setDisable(false);
			}
	}
	public void onCreateDB(MouseEvent e) throws Exception {
		if(setPasswordDB.getText().isEmpty() || setNameDB.getText().isEmpty()|| folderAddress == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("Please enter choose a location or enter your password/database name");
			alert.showAndWait();
		}else {
			createDBPage.setVisible(false);
			//create file and serialize to json
		}
	}
	public void backToMenu(MouseEvent e) throws Exception {
		createDBPage.setVisible(false);
	}
	public void chooseDirectory(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(null);
		if(selectedDirectory != null) {
			ChooseDirectory.setFont(Font.font("System", 12));
			ChooseDirectory.setText(selectedDirectory.getAbsolutePath());
			folderAddress = selectedDirectory.getAbsolutePath();
		}
	}
}
