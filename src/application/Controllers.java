package application;

import java.io.File;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;

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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

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
	
	protected String fileAddress;

	public void selectFile(ActionEvent event) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("AES Encrypted files", "*.aes"));
		File selectedFile = fc.showOpenDialog(null);
		if (selectedFile != null) {
			openDB.setFont(Font.font("System", 12));
			openDB.setText(selectedFile.getAbsolutePath());
			fileAddress = selectedFile.getAbsolutePath();
		}
		lock.setVisible(false);

	}
	public void createDB() {
		createDBPage.setVisible(true);
	}
	
	public void switchDB() {
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
		createDBPage.setVisible(false);
	}
}
