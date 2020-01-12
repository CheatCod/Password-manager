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
import javafx.scene.control.PasswordField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
	protected JFXTextField addAccount;
	@FXML
	protected JFXPasswordField addPassword;
	@FXML
	protected Text websiteName1;
	@FXML
	protected Text websiteName2;
	@FXML
	protected Text websiteName3;
	@FXML
	protected Text websiteName4;
	@FXML
	protected Text websiteName5;
	@FXML
	protected Text websiteName6;
	@FXML
	protected Text url1;
	@FXML
	protected Text url2;
	@FXML
	protected Text url3;
	@FXML
	protected Text url4;
	@FXML
	protected Text url5;
	@FXML
	protected Text url6;
	@FXML
	protected Text account1;
	@FXML
	protected Text account2;
	@FXML
	protected Text account3;
	@FXML
	protected Text account4;
	@FXML
	protected Text account5;
	@FXML
	protected Text account6;
	@FXML
	protected PasswordField password1;
	@FXML
	protected PasswordField password2;
	@FXML
	protected PasswordField password3;
	@FXML
	protected PasswordField password4;
	@FXML
	protected PasswordField password5;
	@FXML
	protected PasswordField password6;
	@FXML
	protected JFXButton webEntry;
	@FXML
	protected AnchorPane websiteEntryPrompt;


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
		
		//read website name
		if(websiteEntryArray[0].getNameofWebsite()!=null) {
			websiteName1.setText(websiteEntryArray[0].getNameofWebsite());
		}
		if(websiteEntryArray[1].getNameofWebsite()!=null) {
			websiteName2.setText(websiteEntryArray[1].getNameofWebsite());
		}
		if(websiteEntryArray[2].getNameofWebsite()!=null) {
			websiteName3.setText(websiteEntryArray[2].getNameofWebsite());
		}
		if(websiteEntryArray[3].getNameofWebsite()!=null) {
			websiteName4.setText(websiteEntryArray[3].getNameofWebsite());
		}
		if(websiteEntryArray[4].getNameofWebsite()!=null) {
			websiteName5.setText(websiteEntryArray[4].getNameofWebsite());
		}
		if(websiteEntryArray[5].getNameofWebsite()!=null) {
			websiteName6.setText(websiteEntryArray[5].getNameofWebsite());
		}
		
		//read url
		
		if(websiteEntryArray[0].getWebURL()!=null) {
			url1.setText(websiteEntryArray[0].getWebURL());
		}
		if(websiteEntryArray[1].getWebURL()!=null) {
			url2.setText(websiteEntryArray[1].getWebURL());
		}
		if(websiteEntryArray[2].getWebURL()!=null) {
			url3.setText(websiteEntryArray[2].getWebURL());
		}
		if(websiteEntryArray[3].getWebURL()!=null) {
			url4.setText(websiteEntryArray[3].getWebURL());
		}
		if(websiteEntryArray[4].getWebURL()!=null) {
			url5.setText(websiteEntryArray[4].getWebURL());
		}
		if(websiteEntryArray[5].getWebURL()!=null) {
			url6.setText(websiteEntryArray[5].getWebURL());
		}
		
		//read password
		
		if(websiteEntryArray[0].getPassword()!=null) {
			password1.setText(websiteEntryArray[0].getPassword());
		}
		if(websiteEntryArray[1].getPassword()!=null) {
			password2.setText(websiteEntryArray[1].getPassword());
		}
		if(websiteEntryArray[2].getPassword()!=null) {
			password3.setText(websiteEntryArray[2].getPassword());
		}
		if(websiteEntryArray[3].getPassword()!=null) {
			password4.setText(websiteEntryArray[3].getPassword());
		}
		if(websiteEntryArray[4].getPassword()!=null) {
			password5.setText(websiteEntryArray[4].getPassword());
		}
		if(websiteEntryArray[5].getPassword()!=null) {
			password6.setText(websiteEntryArray[5].getPassword());
		}
		
		//readd account
		
		if(websiteEntryArray[0].getAccount()!=null) {
			account1.setText(websiteEntryArray[0].getAccount());
		}
		if(websiteEntryArray[1].getAccount()!=null) {
			account2.setText(websiteEntryArray[1].getAccount());
		}
		if(websiteEntryArray[2].getAccount()!=null) {
			account3.setText(websiteEntryArray[2].getAccount());
		}
		if(websiteEntryArray[3].getAccount()!=null) {
			account4.setText(websiteEntryArray[3].getAccount());
		}
		if(websiteEntryArray[4].getAccount()!=null) {
			account5.setText(websiteEntryArray[4].getAccount());
		}
		if(websiteEntryArray[5].getAccount()!=null) {
			account6.setText(websiteEntryArray[5].getAccount());
		}
		

	}

}
