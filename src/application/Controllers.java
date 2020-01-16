package application;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
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
import com.google.gson.reflect.TypeToken;

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
	protected JFXTextField directoryTextField;
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
	@FXML
	protected JFXButton passwordMenuSelection;
	@FXML
	protected JFXButton securedText;
	@FXML
	protected AnchorPane websitePage;
	@FXML
	protected AnchorPane securedtextPage;

	protected static String fileAddress;
	protected static String folderAddress;
	protected static String dbPassword;
	protected static String dbName;
	List<WebsiteEntry> Passwords = new ArrayList<>();
	public void openWebsite(MouseEvent mouseEvent) throws IOException, URISyntaxException {
		System.out.println("method called");
		if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                System.out.println("Double clicked");
                Desktop.getDesktop().browse(new URI("http://www."+url1.getText()));
                addToClipBoard(account1.getText());
            }
        }
 
	}
	public void addToClipBoard(String string) {
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(string);
		clipboard.setContent(content);
	}
	public void copyAccount(MouseEvent event) {
		addToClipBoard(account1.getText());
	}
	public void copyPws(MouseEvent event) {
		addToClipBoard(password1.getText());
	}
	public void resetPages() {
		securedtextPage.setVisible(false);
		websitePage.setVisible(false);
		promptDB.setVisible(true);
		createDBPage.setVisible(true);
		
	}
	public void switchPageFromTo(AnchorPane pgToBeSwtiched, AnchorPane pgToSwitch) {
		pgToBeSwtiched.setVisible(false);
		pgToSwitch.setVisible(true);
	}
	public void switchPageFromTo(Pane pgToBeSwtiched, AnchorPane pgToSwitch) {
		pgToBeSwtiched.setVisible(false);
		pgToSwitch.setVisible(true);
	}
	public void switchPageFromTo(AnchorPane pgToBeSwtiched, Pane pgToSwitch) {
		pgToBeSwtiched.setVisible(false);
		pgToSwitch.setVisible(true);
	}
	public void switchPageFromTo(Pane pgToBeSwtiched, Pane pgToSwitch) {
		pgToBeSwtiched.setVisible(false);
		pgToSwitch.setVisible(true);
	}
	public void editSecuredText(MouseEvent e) {

	}
	public void securedTextSelection(ActionEvent e) {
		securedtextPage.setVisible(true);
		websitePage.setVisible(false);
	}
	public void passwordMenuSelection(ActionEvent e) {
		websitePage.setVisible(true);
		securedtextPage.setVisible(false);
	}
	public void editPassword (MouseEvent e) {
		websiteEntryPrompt.setVisible(true);
	}
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

	public void switchDB(ActionEvent event) throws Exception {
		GaussianBlur gaussianBlur = new GaussianBlur();
		promptDB.setVisible(true);
		createDBPage.setVisible(false);
		menu.setDisable(true);
		menu.setEffect(gaussianBlur);
		websitePage.setDisable(true);
		websitePage.setEffect(gaussianBlur);
		securedtextPage.setDisable(true);
		securedtextPage.setEffect(gaussianBlur);
		promptDB.setVisible(true);

		SecureFileIO.fileEncrypt(dbPassword, fileAddress);
		fileAddress = fileAddress+".aes";
		txtPswField.setText("");

	}
	
	public void Login(ActionEvent event) throws Exception {
		GaussianBlur gaussianBlur = new GaussianBlur();
		if (fileAddress != null && txtPswField.getText()!=null) {
			dbPassword = txtPswField.getText();
			dbName = setNameDB.getText();
			System.out.println(fileAddress);
			System.out.println(dbName);
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
				websitePage.setDisable(false);
				websitePage.setEffect(gaussianBlur);
				securedtextPage.setDisable(false);
				securedtextPage.setEffect(gaussianBlur);
				
				fileAddress = fileAddress.replace(".aes", "");
				deserializeWebsiteEntry();
			}
		}
	}

	public void onCreateDB(ActionEvent e) throws Exception {
		try {
			setNameDB.getText().isEmpty();
		}
		catch (Exception e1){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("An error has occurred. Please try again");
			alert.showAndWait();
		}
		if (setPasswordDB.getText().isEmpty() || setNameDB.getText().isEmpty() || folderAddress == null) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("Please enter choose a location or enter your password/database name");
			alert.showAndWait();
		} else {
			File file = new File("" + directoryTextField.getText() + "\\" + setNameDB.getText());
			fileAddress = file.getAbsolutePath();
			if (file.createNewFile()) {
				System.out.println("encrypting file at " + fileAddress+" with password" + setPasswordDB.getText());
				SecureFileIO.fileEncrypt(setPasswordDB.getText(), fileAddress);
				promptDB.setVisible(true);
				fileAddress = file.getAbsolutePath() + ".aes";

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

			folderAddress = selectedDirectory.getAbsolutePath();
			directoryTextField.setText(folderAddress);
		}
	}

	public void savePassword(ActionEvent e) throws Exception {
		File file = new File(fileAddress);

		DatabaseEntry dbentry = new DatabaseEntry();
		Object websiteEntry = dbentry.createEntry(1);
		((WebsiteEntry) websiteEntry).setNameOfWebsite(addWebsiteName.getText());
		((WebsiteEntry) websiteEntry).setWebURL(addWebsiteURL.getText());
		((WebsiteEntry) websiteEntry).setPassword(addPassword.getText());
		((WebsiteEntry) websiteEntry).setAccount(addAccount.getText());

		Passwords.add((WebsiteEntry) websiteEntry);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(writer);
		bw.write(gson.toJson(Passwords));
		bw.close();
		websiteEntryPrompt.setVisible(false);
		deserializeWebsiteEntry();
	}
	private static String readLineByLineJava8(String filePath) 
	{
		StringBuilder contentBuilder = new StringBuilder();
		try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) 
		{
			stream.forEach(s -> contentBuilder.append(s).append("\n"));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return contentBuilder.toString();
	}

	public void deserializeWebsiteEntry() throws Exception{
		File file = new File(fileAddress);
		Gson gson = new Gson();
		String S = readLineByLineJava8(file.getAbsolutePath());
		Passwords.clear();
		websiteName1.setText("");
		websiteName2.setText("");
		websiteName3.setText("");
		websiteName4.setText("");
		websiteName5.setText("");
		websiteName6.setText("");
		url1.setText("");
		url2.setText("");
		url3.setText("");
		url4.setText("");
		url5.setText("");
		url6.setText("");
		password1.setText("");
		password2.setText("");
		password3.setText("");
		password4.setText("");
		password5.setText("");
		password6.setText("");
		account1.setText("");
		account2.setText("");
		account3.setText("");
		account4.setText("");
		account5.setText("");
		account6.setText("");
		if (S.isEmpty()) {
			System.out.println("empty database");
		}
		else {
		System.out.println(S);
		Type WebsiteEntryListType = new TypeToken<ArrayList<WebsiteEntry>>(){}.getType();
		Passwords =gson.fromJson(S, WebsiteEntryListType);  
		//Type founderListType = new TypeToken<ArrayList<Founder>>(){}.getType();
		WebsiteEntry[] websiteEntryArray = gson.fromJson(S, WebsiteEntry[].class);
		//List<Founder> websiteEntryArray = gson.fromJson(json, classOfT)
		//read website name
		if(websiteEntryArray.length>=1) {
			if(websiteEntryArray[0].getNameofWebsite()!=null) {
				websiteName1.setText(websiteEntryArray[0].getNameofWebsite());
			}
		}
		if(websiteEntryArray.length>=2) {
			if(websiteEntryArray[1].getNameofWebsite()!=null) {
				websiteName2.setText(websiteEntryArray[1].getNameofWebsite());
			}
		}
		if(websiteEntryArray.length>=3) {
			if(websiteEntryArray[2].getNameofWebsite()!=null) {
				websiteName3.setText(websiteEntryArray[2].getNameofWebsite());
			}
		}
		if(websiteEntryArray.length>=4) {
			if(websiteEntryArray[3].getNameofWebsite()!=null) {
				websiteName4.setText(websiteEntryArray[3].getNameofWebsite());
			}
		}
		if(websiteEntryArray.length>=5) {
			if(websiteEntryArray[4].getNameofWebsite()!=null) {
				websiteName5.setText(websiteEntryArray[4].getNameofWebsite());
			}
		}if(websiteEntryArray.length>=6) {
			if(websiteEntryArray[5].getNameofWebsite()!=null) {
				websiteName6.setText(websiteEntryArray[5].getNameofWebsite());
			}
		}


		//read url
		if(websiteEntryArray.length>=1) {
			if(websiteEntryArray[0].getWebURL()!=null) {
				url1.setText(websiteEntryArray[0].getWebURL());
			}
		}
		if(websiteEntryArray.length>=2) {
			if(websiteEntryArray[1].getWebURL()!=null) {
				url2.setText(websiteEntryArray[1].getWebURL());
			}
		}
		if(websiteEntryArray.length>=3) {
			if(websiteEntryArray[2].getWebURL()!=null) {
				url3.setText(websiteEntryArray[2].getWebURL());
			}
		}
		if(websiteEntryArray.length>=4) {
			if(websiteEntryArray[3].getWebURL()!=null) {
				url4.setText(websiteEntryArray[3].getWebURL());
			}
		}
		if(websiteEntryArray.length>=5) {
			if(websiteEntryArray[4].getWebURL()!=null) {
				url5.setText(websiteEntryArray[4].getWebURL());
			}
		}if(websiteEntryArray.length>=6) {
			if(websiteEntryArray[5].getWebURL()!=null) {
				url6.setText(websiteEntryArray[5].getWebURL());
			}
		}


		//read password
		if(websiteEntryArray.length>=1) {
			if(websiteEntryArray[0].getPassword()!=null) {
				password1.setText(websiteEntryArray[0].getPassword());
			}
		}if(websiteEntryArray.length>=2) {
			if(websiteEntryArray[1].getPassword()!=null) {
				password2.setText(websiteEntryArray[1].getPassword());
			}
		}if(websiteEntryArray.length>=3) {
			if(websiteEntryArray[2].getPassword()!=null) {
				password3.setText(websiteEntryArray[2].getPassword());
			}
		}if(websiteEntryArray.length>=4) {
			if(websiteEntryArray[3].getPassword()!=null) {
				password4.setText(websiteEntryArray[3].getPassword());
			}
		}
		if(websiteEntryArray.length>=5) {
			if(websiteEntryArray[4].getPassword()!=null) {
				password5.setText(websiteEntryArray[4].getPassword());
			}
		}
		if(websiteEntryArray.length>=6) {
			if(websiteEntryArray[5].getPassword()!=null) {
				password6.setText(websiteEntryArray[5].getPassword());
			}
		}

		//read account
		if(websiteEntryArray.length>=1) {
			if(websiteEntryArray[0].getAccount()!=null) {
				account1.setText(websiteEntryArray[0].getAccount());
			}
		}
		if(websiteEntryArray.length>=2) {
			if(websiteEntryArray[1].getAccount()!=null) {
				account2.setText(websiteEntryArray[1].getAccount());
			}
		}
		if(websiteEntryArray.length>=3) {
			if(websiteEntryArray[2].getAccount()!=null) {
				account3.setText(websiteEntryArray[2].getAccount());
			}
		}
		if(websiteEntryArray.length>=4) {
			if(websiteEntryArray[3].getAccount()!=null) {
				account4.setText(websiteEntryArray[3].getAccount());
			}
		}
		if(websiteEntryArray.length>=5) {
			if(websiteEntryArray[4].getAccount()!=null) {
				account5.setText(websiteEntryArray[4].getAccount());
			}
		}
		if(websiteEntryArray.length>=6) {
			if(websiteEntryArray[5].getAccount()!=null) {
				account6.setText(websiteEntryArray[5].getAccount());
			}
		}


	}
	}

}
