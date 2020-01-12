package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.DatabaseEntry.WebsiteEntry;
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
	@FXML
	protected JFXTextField addWebsiteName;
	@FXML
	protected JFXTextField addWebsiteURL;
	@FXML
	protected JFXTextField addPassword;
	

	protected String fileAddress;
	protected String folderAddress;
	List<WebsiteEntry> Passwords = new ArrayList<>();

	public void selectFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("AES Encrypted files", "*.aes"));
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
		promptDB.setVisible(false);
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
			} else {
				gaussianBlur.setRadius(0);
				menu.setEffect(gaussianBlur);
				promptDB.setVisible(false);
				menu.setMouseTransparent(false);
				menu.setDisable(false);
			}
	}

	public void onCreateDB(MouseEvent e) throws Exception {
		if (setPasswordDB.getText().isEmpty() || setNameDB.getText().isEmpty() || folderAddress == null) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("Please enter choose a location or enter your password/database name");
			alert.showAndWait();
		} else {
			
			File file = new File("" + ChooseDirectory.getText() + "\\" + setNameDB.getText());
			fileAddress = file.getAbsolutePath();
			if (file.createNewFile()) {
				SecureFileIO.fileEncrypt(setPasswordDB.getText(), fileAddress);
				promptDB.setVisible(true);
				fileAddress = file.getAbsolutePath() + ".aes";
				System.out.println(fileAddress);
				openDB.setFont(Font.font("System", 12));
				openDB.setText(fileAddress);
				lock.setVisible(false);
				createDBPage.setVisible(false);
				promptDB.setVisible(true);
			}

			// System.out.println(""+ChooseDirectory.getText()+"\\"+setNameDB.getText()+".txt");
			// create file and serialize to json
		}
	}

	public void backToMenu(MouseEvent e) throws Exception {
		createDBPage.setVisible(false);
		promptDB.setVisible(true);
	}

	public void chooseDirectory(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(null);
		if (selectedDirectory != null) {
			ChooseDirectory.setFont(Font.font("System", 12));
			ChooseDirectory.setText(selectedDirectory.getAbsolutePath());
			folderAddress = selectedDirectory.getAbsolutePath();
		}
	}
	
	public void createPassword(MouseEvent e) throws Exception {
		File file = new File("" + ChooseDirectory.getText() + "\\" + setNameDB.getText());
		
		DatabaseEntry dbentry = new DatabaseEntry();
		Object websiteEntry = dbentry.createEntry(1);
		((WebsiteEntry) websiteEntry).setNameOfWebsite(addWebsiteName.getText());
		((WebsiteEntry) websiteEntry).setWebURL(addWebsiteURL.getText());
		((WebsiteEntry) websiteEntry).setPassword(addPassword.getText());
		
		Passwords.add((WebsiteEntry) websiteEntry);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer = new FileWriter(file);
	    BufferedWriter bw = new BufferedWriter(writer);
	    bw.write(gson.toJson(websiteEntry));
	    bw.close();
	}
	
	public void deserializeWebsiteEntry(ActionEvent e) throws Exception{
		File file = new File("" + ChooseDirectory.getText() + "\\" + setNameDB.getText());
		Gson gson = new Gson();
		String S = gson.toJson(file);
		WebsiteEntry[] websiteEntryArray = gson.fromJson(S, WebsiteEntry[].class);
	}
	
}
