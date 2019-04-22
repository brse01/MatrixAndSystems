package br.ufc.algebra.matrixandsystems.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.ufc.algebra.matrixandsystems.App;
import br.ufc.algebra.matrixandsystems.utils.AnimationGenerator;
import br.ufc.algebra.matrixandsystems.utils.ImageEditor;
import br.ufc.algebra.matrixandsystems.view.fxml.FXMLResources;

public class LoginController implements Initializable {

	private static final String PAHT = "br/ufc/algebra/matrixandsystems/utils";
	@FXML
	private ImageView imgView;

	@FXML
	private GridPane root;

	@FXML
	private TextField txField;

	@FXML
	private PasswordField pwField;

	@FXML
	private Label loginLabel, registerLabel;

	private final ImageEditor imageEditor;
	private final AnimationGenerator animationGenerator;

	public LoginController() {
		imageEditor = new ImageEditor();
		animationGenerator = new AnimationGenerator();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		animationGenerator.applyFadeAnimationOn(root, 3000, 0f, 1.0f, null);
		imageEditor.setImageOn(imgView, PAHT + "/buttons/login.png", 75, 75);
		setOnKeyPressed();
		registerLabel.setVisible(false);
	}

	@FXML
	private void mouseMovedLoginLabel() {
		animationGenerator.applyFadeAnimationOn(loginLabel, 500, 1.0f, 0.7f, null);
	}

	@FXML
	private void mouseMovedRegisterLabel() {
		animationGenerator.applyFadeAnimationOn(registerLabel, 500, 1.0f, 0.7f, null);
	}

	@FXML
	private void mouseExitedLoginLabel() {
		animationGenerator.applyFadeAnimationOn(loginLabel, 500, 0.7f, 1.0f, null);
	}

	@FXML
	private void mouseExitedRegisterLabel() {
		animationGenerator.applyFadeAnimationOn(registerLabel, 500, 0.7f, 1.0f, null);
	}

	@FXML
	private void login() {
		if (loginSuccessful())
			animateWhenLoginSuccess();
		else
			animateWhenBadLogin();
	}

	private boolean loginSuccessful() {
		return txField.getText().equals("bruno") && pwField.getText().equals("bruno");
	}

	private void setOnKeyPressed() {
		root.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER) && loginSuccessful())
				animateWhenLoginSuccess();
			else
				animateWhenBadLogin();
		});
	}

	private void animateWhenLoginSuccess() {
		try {
			// ONDE CHAMO O MENU PRINCIPAL
			Parent main = FXMLLoader.load(FXMLResources.MENU);
			StackPane temp = new StackPane();
			temp.getChildren().add(new ImageView(new Image(PAHT + "/buttons/Checkmark-50.png")));
			animationGenerator.applyFadeAnimationOn(root, 1000, 1.0f, 0f, event -> {
				temp.setOpacity(0);
				App.getStage().setScene(new Scene(temp, 800, 700));
				animationGenerator.applyFadeAnimationOn(temp, 1000, 0f, 1.0f, event1 -> {
					animationGenerator.applyFadeAnimationOn(temp, 1000, 1.0f, 0f, event2 -> {
						App.getStage().setScene(new Scene(main, 800, 700));
						animationGenerator.applyFadeAnimationOn(main, 1000, 0f, 1.0f, null);
					});
				});
			});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void animateWhenBadLogin() {
		try {
			StackPane temp = new StackPane();
			Parent root = FXMLLoader.load(FXMLResources.LOGIN);
			temp.getChildren().add(new ImageView(new Image(PAHT + "/buttons/Delete-50.png")));
			animationGenerator.applyFadeAnimationOn(root, 1000, 1.0f, 0f, event -> {
				temp.setOpacity(0f);
				App.getStage().setScene(new Scene(temp, 1400, 700));
				animationGenerator.applyFadeAnimationOn(temp, 1000, 0f, 1.0f, event1 -> {
					root.setOpacity(0f);
					App.getStage().setScene(new Scene(root, 1400, 700));
					animationGenerator.applyFadeAnimationOn(root, 1000, 0f, 1.0f, null);
				});
			});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
