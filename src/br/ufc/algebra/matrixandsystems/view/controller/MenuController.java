package br.ufc.algebra.matrixandsystems.view.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import br.ufc.algebra.matrixandsystems.App;
import br.ufc.algebra.matrixandsystems.calculationsMatrix.Matrix;
import br.ufc.algebra.matrixandsystems.calculationsSystems.CalculationGauss;
import br.ufc.algebra.matrixandsystems.calculationsSystems.CalculationGramSchimidt;
import br.ufc.algebra.matrixandsystems.utils.AlertUtils;
import br.ufc.algebra.matrixandsystems.utils.AnimationGenerator;
import br.ufc.algebra.matrixandsystems.utils.ImageEditor;
import br.ufc.algebra.matrixandsystems.view.fxml.FXMLResources;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

@SuppressWarnings("unused")
public class MenuController implements Initializable {

	private static final String THE_PATH = "br/ufc/algebra/matrixandsystems/utils/buttons/";
	private static final String style = "-fx-background-color: #00ADC1";
	private boolean isOpened = false;

	private static final int DEFAULT_STARTING_X_POSITION = 0;
	private static final int DEFAULT_ENDING_X_POSITION = -120;

	private boolean isSystems = false;
	private boolean isOneMatrix = false;
	private boolean isTwoMatrix = false;

	private final AnimationGenerator animationGenerator;
	private final ImageEditor imageEditor;

	@FXML
	private Pane pane;

	@FXML
	private BorderPane borderPane;

	@FXML
	protected ImageView menuButton, portrait, logOut;

	@FXML
	private Label title;

	@FXML
	private VBox menuRoot;

	@FXML
	private Separator sep_1, sep_2, sep_3, sep_4, sep_5;

	private Separator[] separators = new Separator[5];

	private int prefSizeX = 40, prefSizeY = 40;

	/* Apartir de agora só parte gráfica estatica de matriz */
	private Map<String, Integer> optionsOne = new HashMap<>();
	private Map<String, Integer> optionsTwo = new HashMap<>();

	private int lineColumn[];
	private int layoutX1 = 70, layoutY1 = 70;
	private int layoutX2 = (1200 / 2), layoutY2 = 60;;
	private Button enter;
	private ComboBox<String> operationsComboBox;
	private TextField matrixTextFied1[][];
	private TextField matrixTextFied2[][];
	private double matrixDados1[][];
	private float gramsChimidtDados[][];
	private double matrixDados2[][];
	private int countMat;
	//TODO AQUI
	private Button potenciaButton, inversaButton, transporstaButton, determinanteButton, multipEscalarButton,
			somaMatrizButton, multiplicaMatrixButton, gramSchimidtButton;

	List<Button> buttonMatrix;
	/* fim */

	/* Apartir de agora só parte gráfica estatica dos sistemas */

	private TextField linearTextField[][];
	private Label xs[][];
	private double linearDouble[][];
	private double linearVectDouble[];
	private Button fewerButton;
	private Button moreButton;
	private Button clearButton;

	private int line;
	private int column;
	private int lineMatrix;
	private int layoutX = 60, layoutY = 60;

	/* fim */

	/* PARTE DO MENU DIFERENTE */

	private Button menu;
	private Button menuOneMatrix;
	private Button menuTwoMatrix;

	private Button metodoGauss, metodoGaussJordan, fatoraLU, estudoSistema;
	private boolean isOpenedMenu = false;

	private void initSeparators() {
		separators[0] = sep_1;
		separators[1] = sep_2;
		separators[2] = sep_3;
		separators[3] = sep_4;
		separators[4] = sep_5;
	}

	public MenuController() {
		animationGenerator = new AnimationGenerator();
		imageEditor = new ImageEditor();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initSeparators();
		setImages();
		closeAnimation();
	}

	// #############################################################################
	/* MÉTODOS PARA MANIPULAR O MENU LATERAL DIRETO */
	// #############################################################################

	private void initComponentsMenu() {
		menu = new Button();

		metodoGauss = new Button();
		metodoGaussJordan = new Button();
		fatoraLU = new Button();
		estudoSistema = new Button();
		fewerButton = new Button();
		moreButton = new Button();
		clearButton = new Button();

		setComponetsMenuPane();
		initListenersMenuPane();
		setLayoutComponentsMenu();
	}

	private void setComponetsMenuPane() {
		this.pane.getChildren().add(menu);
		this.pane.getChildren().add(metodoGauss);
		this.pane.getChildren().add(metodoGaussJordan);
		this.pane.getChildren().add(fatoraLU);
		this.pane.getChildren().add(estudoSistema);

		this.pane.getChildren().add(fewerButton);
		this.pane.getChildren().add(moreButton);
		this.pane.getChildren().add(clearButton);

		pane.getStylesheets().add("br/ufc/algebra/matrixandsystems/view/css/pane.css");
	}

	private void initListenersMenuPane() {
		menu.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if (isSystems && isOneMatrix == false && isTwoMatrix == false) {
					setVisibleButtonsMenu(!isOpenedMenu);
				} else if (isSystems == false && isOneMatrix && isTwoMatrix == false) {
					setVisibleButtonsMenuOneMatriz(!isOpenedMenu);
					System.out.println("I MATRIZ");
				} else if (isSystems == false && isOneMatrix == false && isTwoMatrix) {
					setVisibleButtonsMenuTwoMatriz(!isOpenedMenu);
				}
				isOpenedMenu = !isOpenedMenu;
			}
		});
		metodoGauss.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				getInformationSystems(linearTextField, linearDouble);
				linearVectDouble = new double[linearDouble.length];
				linearVectDouble = soluction(linearDouble);
				AlertUtils.showDialog("Resolução é: \n " + toStringAux(linearVectDouble), AlertType.INFORMATION);
				linearVectDouble = null;
			}
		});

		metodoGaussJordan.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				getInformationSystems(linearTextField, linearDouble);
				linearVectDouble = new double[linearDouble.length];
				linearVectDouble = soluction(linearDouble);
				AlertUtils.showDialog("Resolução é: \n " + toStringAux(linearVectDouble), AlertType.INFORMATION);
				linearVectDouble = null;
			}
		});
		fatoraLU.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {

			}
		});

		// VERIFICAR SE O SISTEMA TEM SOLUÇÃO
		estudoSistema.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				getInformationSystems(linearTextField, linearDouble);
				if (Matrix.verificaMatrixQudrada(linearDouble, 0)) {
					if (Matrix.laplace(getMatrix(linearDouble)) == 0.0) {
						if (Matrix.verificarSistema(linearDouble) == false) {
							AlertUtils.showDialog("S.I. - Sistema Impossível", AlertType.INFORMATION);
						} else {
							AlertUtils.showDialog("S.P.I. - Sistema Possivel Indeterminavel", AlertType.INFORMATION);
						}

						if (Matrix.verificarSistemaHomogenio(linearDouble)) {
							AlertUtils.showDialog("S.P.I. - Sistema Homogênio Possivel Indeterminavel",
									AlertType.INFORMATION);
						}
					} else {
						if (Matrix.verificarSistemaHomogenio(linearDouble)
								&& (linearDouble[0].length - 1 == linearDouble.length - 1)) {
							AlertUtils.showDialog("S.P.D. - Sistema Homogênio Possivel é Determinado",
									AlertType.INFORMATION);
						}
						AlertUtils.showDialog("S.P.D. - Sistema Possivel é Determinado", AlertType.INFORMATION);
					}
				} else {
					AlertUtils.showDialog("Desculpa, Não pode calcular o determinante", AlertType.INFORMATION);
				}
			}
		});

		// LIMPAR OS CAMPOS DO SISTEMA
		clearButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				if (isSystems == false && isOneMatrix == true && isTwoMatrix == false) {
					clear(matrixTextFied1);
				} else if (isSystems && isOneMatrix == false && isTwoMatrix == false) {
					clear(linearTextField);
				} else if (isSystems == false && isOneMatrix == false && isTwoMatrix) {
					System.out.println("Quando for duas matriz");
					clear(matrixTextFied1);
					clear(matrixTextFied2);
				}
			}
		});

		// BOTÃO DE MENOS
		fewerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (column > 2) {
					if (isSystems == false && isOneMatrix && isTwoMatrix == false) {
						construtonAxuMatrixOne('-');
						System.out.println("Uma matriz");
					} else if (isSystems && isOneMatrix == false && isTwoMatrix == false) {
						construtonAxu('-');
					} else if (isSystems == false && isOneMatrix == false && isTwoMatrix) {
						System.out.println("Quando for duas matriz");
					}

				}
			}
		});

		// BOTÃO DE MAIS
		moreButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (line <= 10) {
					if (isSystems == false && isOneMatrix == true && isTwoMatrix == false) {
						construtonAxuMatrixOne('+');
					} else if (isSystems && isOneMatrix == false && isTwoMatrix == false) {
						construtonAxu('+');
					} else if (isSystems == false && isOneMatrix == false && isTwoMatrix) {
						System.out.println("Quando for duas matriz");
					}

				}
			}
		});

	}

	private void setVisibleButtonsMenu(boolean value) {
		modifyImagemDoMenu(value);
		componentesDoMenuSistema(value);
	}

	private void setVisibleButtonsMenuTwoMatriz(boolean value) {
		modifyImagemDoMenu(value);
		potenciaButton.setVisible(value);
		inversaButton.setVisible(value);
		transporstaButton.setVisible(value);
		determinanteButton.setVisible(value);
		multipEscalarButton.setVisible(value);
		somaMatrizButton.setVisible(value);
		multiplicaMatrixButton.setVisible(value);
	}

	private void setVisibleButtonsMenuOneMatriz(boolean value) {
		modifyImagemDoMenu(value);
		gramSchimidtButton.setVisible(value);
		potenciaButton.setVisible(value);
		inversaButton.setVisible(value);
		transporstaButton.setVisible(value);
		determinanteButton.setVisible(value);
		multipEscalarButton.setVisible(value);
	}

	private void modifyImagemDoMenu(boolean value) {
		if (value)
			menu.setGraphic(new ImageView(THE_PATH + "menu3App.png"));
		else
			menu.setGraphic(new ImageView(THE_PATH + "menu2App.png"));
	}

	private void setLayoutComponentsMenu() {

		int LayoytX = 1100;

		fewerButton.setLayoutX(LayoytX - 210);
		fewerButton.setLayoutY(600);
		fewerButton.setPrefSize(2, 2);
		fewerButton.setId("round-red");

		moreButton.setLayoutX(LayoytX - 140);
		moreButton.setLayoutY(600);
		moreButton.setPrefSize(2, 2);
		moreButton.setId("round-red");

		clearButton.setLayoutX(LayoytX - 70);
		clearButton.setLayoutY(600);
		clearButton.setPrefSize(2, 2);
		clearButton.setId("round-red");

		menu.setLayoutX(LayoytX);
		menu.setLayoutY(600);
		menu.setPrefSize(2, 2);
		menu.setId("round-red");

		metodoGauss.setLayoutX(LayoytX);
		metodoGauss.setLayoutY(530);
		metodoGauss.setPrefSize(2, 2);
		metodoGauss.setId("round-red");

		metodoGaussJordan.setLayoutX(LayoytX);
		metodoGaussJordan.setLayoutY(460);
		metodoGaussJordan.setPrefSize(2, 2);
		metodoGaussJordan.setId("round-red");

		fatoraLU.setLayoutX(LayoytX);
		fatoraLU.setLayoutY(390);
		fatoraLU.setPrefSize(2, 2);
		fatoraLU.setId("round-red");

		estudoSistema.setLayoutX(LayoytX);
		estudoSistema.setLayoutY(320);
		estudoSistema.setPrefSize(2, 2);
		estudoSistema.setId("round-red");

		metodoGauss.setTooltip(new Tooltip("Método de Gauss"));
		metodoGaussJordan.setTooltip(new Tooltip("Método de Gauss Jordan"));
		fatoraLU.setTooltip(new Tooltip("Fatoração LU"));
		estudoSistema.setTooltip(new Tooltip("Estudo do Sistema"));
		menu.setTooltip(new Tooltip("Menu"));

		menu.setGraphic(new ImageView(THE_PATH + "menu2App.png"));
		metodoGauss.setGraphic(new ImageView(THE_PATH + "gApp.png"));
		metodoGaussJordan.setGraphic(new ImageView(THE_PATH + "gjApp.png"));
		fatoraLU.setGraphic(new ImageView(THE_PATH + "luApp.png"));
		estudoSistema.setGraphic(new ImageView(THE_PATH + "esApp.png"));

		fewerButton.setTooltip(new Tooltip("Diminuir"));
		moreButton.setTooltip(new Tooltip("Aumentar"));
		clearButton.setTooltip(new Tooltip("Limpar os Campos"));

		moreButton.setGraphic(new ImageView(THE_PATH + "maisApp.png"));
		fewerButton.setGraphic(new ImageView(THE_PATH + "menosApp.png"));
		clearButton.setGraphic(new ImageView(THE_PATH + "limparApp.png"));

		setVisibleButtonsMenu(isOpenedMenu);
	}

	// #############################################################################
	/* fim */
	// #############################################################################

	private void setImages() {
		imageEditor.setImageOn(menuButton, THE_PATH + "Menu-50.png", 35, 18);
		imageEditor.setImageOn(portrait, THE_PATH + "Bruno.jpg", 150, 150);
		imageEditor.setRectangularClipOf(portrait, 15, 15);
		imageEditor.setImageOn(logOut, THE_PATH + "Shutdown-52.png", 35, 18);
	}

	private void openAnimation() {
		animationGenerator.applyTranslateAnimationOn(menuRoot, 500, DEFAULT_ENDING_X_POSITION,
				DEFAULT_STARTING_X_POSITION);
		animationGenerator.applyTranslateAnimationOn(pane, 500, DEFAULT_ENDING_X_POSITION, DEFAULT_STARTING_X_POSITION);
		animationGenerator.applyFadeAnimationOn(portrait, 500, 0.1f, 1.0f, null);
		animateSeparators(0.1f, 1.0f);
		rotateMenuIcon();
		isOpened = true;
	}

	public void closeAnimation() {
		animationGenerator.applyTranslateAnimationOn(menuRoot, 500, DEFAULT_STARTING_X_POSITION,
				DEFAULT_ENDING_X_POSITION);
		animationGenerator.applyTranslateAnimationOn(pane, 500, DEFAULT_STARTING_X_POSITION, DEFAULT_ENDING_X_POSITION);
		animationGenerator.applyFadeAnimationOn(portrait, 500, 1.0f, 0.1f, null);
		animateSeparators(1.0, 0.1f);
		rotateMenuIcon();
		isOpened = false;
	}

	private void rotateMenuIcon() {
		animationGenerator.applyRotationOn(menuButton, 500, 180f, 1);
	}

	private void animateSeparators(double from, double to) {
		for (Separator separator : separators)
			animationGenerator.applyFadeAnimationOn(separator, 500, from, to, null);
	}

	@FXML
	private void logoutSelected() {
		try {
			Parent loginView = FXMLLoader.load(FXMLResources.LOGIN);
			animationGenerator.applyFadeAnimationOn(borderPane, 2000, 1.0f, 0f, event -> {
				App.getStage().setScene(new Scene(loginView, 800, 700));
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void setOnMenuClick() {
		if (isOpened)
			closeAnimation();
		else
			openAnimation();
	}

	@FXML
	private void setSistemasSelected() {
		verifyComponents();
		closeAnimation();
		initComponentsMenu();
		initComponentsSystems();
		setComponentsPaneAux(linearTextField, xs);
		animationGenerator.applyFadeAnimationOn(pane, 500, 0f, 1.0f, null);
		initLayoutSystems();
		this.isSystems = true;
		this.isOneMatrix = false;
		this.isTwoMatrix = false;
		boolean value = false;
		componentesDoMenuSistema(value);
	}

	private void componentesDoMenuSistema(boolean value) {
		metodoGauss.setVisible(value);
		metodoGaussJordan.setVisible(value);
		fatoraLU.setVisible(value);
		estudoSistema.setVisible(value);
	}

	private void verifyComponents() {
		if (isOneMatrix) {
			// offAllComponentsMatrixOne(false);
			offComponentsMatrixOne(matrixTextFied1);
		}
		if (isTwoMatrix) {
			offComponentsMatrixOne(matrixTextFied1);
			offComponentsMatrixOne(matrixTextFied2);
			// offAllComponentsMatrixTwo(false);
		}
		if (isSystems) {
			setVisibleButtonsMenu(false);
			this.isOpenedMenu = false;
			// menu.setVisible(false);
			offAllComponentsSystems(false);
		}
	}

	@FXML
	private void setIMatrizSelected() {
		verifyComponents();
		closeAnimation();
		initComponentsOneMatriz();
		setComponentsPaneAuxOneMatrix(matrixTextFied1);
		animationGenerator.applyFadeAnimationOn(pane, 500, 0f, 1.0f, null);
		initLayoutMatrixOne();
		setComponentsPane();
		initLayoutButton();
		initComponentsMenu();
		initListenerOneMatriz();

		this.isSystems = false;
		this.isOneMatrix = true;
		this.isTwoMatrix = false;
		setVisibleButtonsMenuOneMatriz(false);
		multiplicaMatrixButton.setVisible(false);
		somaMatrizButton.setVisible(false);
	}

	@FXML
	private void setIIMatrizSelected() {
		verifyComponents();
		closeAnimation();
		initComponentsTwoMatriz();
		setComponentsPaneAuxOneMatrix(matrixTextFied1);
		setComponentsPaneAuxOneMatrix(matrixTextFied2);
		animationGenerator.applyFadeAnimationOn(pane, 500, 0f, 1.0f, null);
		initLayoutMatrixTwo();

		setComponentsPane();
		initLayoutButton();
		initComponentsMenu();

		setVisibleButtonsMenuTwoMatriz(false);
		gramSchimidtButton.setVisible(false);
		this.isSystems = false;
		this.isOneMatrix = false;
		this.isTwoMatrix = true;
	}

	// #############################################################################
	/* Inicio dos métodos que manipulam a parte gráfica das matrizes */
	// #############################################################################

	private void initComponentsOneMatriz() {
		this.lineMatrix = 4;
		this.matrixTextFied1 = new TextField[lineMatrix][lineMatrix];
		this.matrixDados1 = new double[lineMatrix][lineMatrix];
		this.gramsChimidtDados = new float[lineMatrix][lineMatrix];
		gramSchimidtButton = new Button();
		potenciaButton = new Button();
		inversaButton = new Button();
		transporstaButton = new Button();
		determinanteButton = new Button();
		multipEscalarButton = new Button();
		somaMatrizButton = new Button();
		multiplicaMatrixButton = new Button();
		constructorInitComponentsMatrix(matrixTextFied1);
	}

	private void initComponentsTwoMatriz() {
		this.lineMatrix = 4;
		initComponentsOneMatriz();
		this.matrixTextFied2 = new TextField[this.lineMatrix][this.lineMatrix];
		this.matrixDados2 = new double[this.lineMatrix][this.lineMatrix];
		constructorInitComponentsMatrix(matrixTextFied2);
	}

	private void initListenerOneMatriz() {

		gramSchimidtButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				getDados(matrixTextFied1, gramsChimidtDados);
				Map<Integer, float[]> result = new HashMap<>();
				result = CalculationGramSchimidt.gramSchimidt(CalculationGramSchimidt.transforma(gramsChimidtDados));
				AlertUtils.showDialog("O resultado é: \n" + toStringGramSchimidt(result), AlertType.INFORMATION);
			}
		});
		potenciaButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				int p = AlertUtils.inputDialog();
				getInformationSystems(matrixTextFied1, matrixDados1);
				switch (p) {
				case 0:
					AlertUtils.showDialog("O resultado é: \n" + toStringResultMatriz(matrizUm(matrixDados1)),
							AlertType.INFORMATION);
					break;
				case 1:
					AlertUtils.showDialog("O resultado é: \n" + toStringResultMatriz(matrixDados1),
							AlertType.INFORMATION);
					break;
				default:
					AlertUtils.showDialog("O resultado é: \n" + toStringResultMatriz(Matrix.potencia(matrixDados1, p)),
							AlertType.INFORMATION);
					break;
				}
			}
		});

		inversaButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				getInformationSystems(matrixTextFied1, matrixDados1);
				double determinante = Matrix.laplace(matrixDados1);
				if (determinante != 0.0 || determinante != 0) {
					AlertUtils.showDialog("O resultado é: \n" + toStringResultMatriz(Matrix.invert(matrixDados1)),
							AlertType.INFORMATION);
				} else {
					AlertUtils.showDialog("Atenção, matriz não tem inversa", AlertType.WARNING);
				}
			}
		});

		transporstaButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				getInformationSystems(matrixTextFied1, matrixDados1);
				AlertUtils.showDialog("O resultado é: \n" + toStringResultMatriz(Matrix.transposed(matrixDados1)),
						AlertType.INFORMATION);
			}
		});

		determinanteButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				getInformationSystems(matrixTextFied1, matrixDados1);
				AlertUtils.showDialog("O resultado é: \n" + Matrix.laplace(matrixDados1), AlertType.INFORMATION);
			}
		});

		multipEscalarButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				int k = 0;
				k = AlertUtils.inputDialog();
				getInformationSystems(matrixTextFied1, matrixDados1);
				AlertUtils.showDialog("O resultado é: \n" + toStringResultMatriz(Matrix.escalar(matrixDados1, k)),
						AlertType.INFORMATION);
			}
		});

		somaMatrizButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				getInformationSystems(matrixTextFied1, matrixDados1);
				getInformationSystems(matrixTextFied2, matrixDados2);
				if (Matrix.verificaMatrixQudrada(matrixDados1, 1) && Matrix.verificaMatrixQudrada(matrixDados2, 1)) {
					AlertUtils.showDialog(
							"O resultado é: \n" + toStringResultMatriz(Matrix.sum(matrixDados1, matrixDados2)),
							AlertType.INFORMATION);
				} else {
					AlertUtils.showDialog("As Matrizes não são quadradas", AlertType.WARNING);
				}
			}
		});

		multiplicaMatrixButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				getInformationSystems(matrixTextFied1, matrixDados1);
				getInformationSystems(matrixTextFied2, matrixDados2);
				if (Matrix.verificaMatrixQudrada(matrixDados1, 1) && Matrix.verificaMatrixQudrada(matrixDados2, 1)) {
					AlertUtils.showDialog(
							"O resultado é: \n"
									+ toStringResultMatriz(Matrix.multiplication(matrixDados1, matrixDados2)),
							AlertType.INFORMATION);
				} else {
					AlertUtils.showDialog("As Matrizes não são quadradas", AlertType.WARNING);
				}

			}
		});
	}

	private void setComponentsPane() {

		this.pane.getChildren().add(gramSchimidtButton);
		this.pane.getChildren().add(potenciaButton);
		this.pane.getChildren().add(inversaButton);

		this.pane.getChildren().add(transporstaButton);
		this.pane.getChildren().add(determinanteButton);
		this.pane.getChildren().add(multipEscalarButton);

		this.pane.getChildren().add(somaMatrizButton);
		this.pane.getChildren().add(multiplicaMatrixButton);
	}

	private void constructorInitComponentsMatrix(TextField[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				matrix[i][j] = new TextField();
			}
		}
	}

	private void initLayoutMatrixOne() {
		initLayoutButton();
		constructorInitLayoutMatrix(matrixTextFied1, layoutX, layoutY, prefSizeX, prefSizeY, this.layoutX);
	}

	private void initLayoutMatrixTwo() {
		initLayoutButton();
		constructorInitLayoutMatrix(matrixTextFied1, layoutX, layoutY, prefSizeX, prefSizeY, this.layoutX);
		constructorInitLayoutMatrix(matrixTextFied2, layoutX2, layoutY2, prefSizeX, prefSizeY, this.layoutX2);
	}

	private void initLayoutButton() {
		int LayotX = 1100;
		int LayoutY = 600;

		multipEscalarButton.setLayoutX(LayotX);
		multipEscalarButton.setLayoutY(LayoutY - 70);
		multipEscalarButton.setPrefSize(2, 2);
		multipEscalarButton.setId("round-red");

		determinanteButton.setLayoutX(LayotX);
		determinanteButton.setLayoutY(LayoutY - 140);
		determinanteButton.setPrefSize(2, 2);
		determinanteButton.setId("round-red");

		transporstaButton.setLayoutX(LayotX);
		transporstaButton.setLayoutY(LayoutY - 210);
		transporstaButton.setPrefSize(2, 2);
		transporstaButton.setId("round-red");

		inversaButton.setLayoutX(LayotX);
		inversaButton.setLayoutY(LayoutY - 280);
		inversaButton.setPrefSize(2, 2);
		inversaButton.setId("round-red");

		potenciaButton.setLayoutX(LayotX);
		potenciaButton.setLayoutY(LayoutY - 350);
		potenciaButton.setPrefSize(2, 2);
		potenciaButton.setId("round-red");

		gramSchimidtButton.setLayoutX(LayotX);
		gramSchimidtButton.setLayoutY(LayoutY - 420);
		gramSchimidtButton.setPrefSize(2, 2);
		gramSchimidtButton.setId("round-red");

		somaMatrizButton.setLayoutX(LayotX);
		somaMatrizButton.setLayoutY(LayoutY - 420);
		somaMatrizButton.setPrefSize(2, 2);
		somaMatrizButton.setId("round-red");

		multiplicaMatrixButton.setLayoutX(LayotX);
		multiplicaMatrixButton.setLayoutY(LayoutY - 490);
		multiplicaMatrixButton.setPrefSize(2, 2);
		multiplicaMatrixButton.setId("round-red");

		gramSchimidtButton.setTooltip(new Tooltip("Gram Schimidt"));
		gramSchimidtButton.setGraphic(new ImageView(THE_PATH + "gsApp.png"));

		multipEscalarButton.setTooltip(new Tooltip("Multiplicação por Escalar"));
		multipEscalarButton.setGraphic(new ImageView(THE_PATH + "multiplicacaoEscalarApp.png"));

		determinanteButton.setTooltip(new Tooltip("Determinante"));
		determinanteButton.setGraphic(new ImageView(THE_PATH + "determinanteApp.png"));

		transporstaButton.setTooltip(new Tooltip("Transposta"));
		transporstaButton.setGraphic(new ImageView(THE_PATH + "transpostaApp.png"));

		inversaButton.setTooltip(new Tooltip("Inversa"));
		inversaButton.setGraphic(new ImageView(THE_PATH + "inversaApp.png"));

		potenciaButton.setTooltip(new Tooltip("Potencia"));
		potenciaButton.setGraphic(new ImageView(THE_PATH + "potenciaApp.png"));

		somaMatrizButton.setTooltip(new Tooltip("Soma de Matriz"));
		somaMatrizButton.setGraphic(new ImageView(THE_PATH + "somaApp.png"));

		multiplicaMatrixButton.setTooltip(new Tooltip("Multiplicação Entre Matriz"));
		multiplicaMatrixButton.setGraphic(new ImageView(THE_PATH + "multiplicacaoApp.png"));

	}

	private void setComponentsPaneAuxOneMatrix(TextField matrixTextFied1[][]) {
		int length = matrixTextFied1.length;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				this.pane.getChildren().add(matrixTextFied1[i][j]);
			}
		}
	}

	private void construtonAuxMatrizTwo(char c) {
		offComponentsMatrixOne(matrixTextFied1);
		offComponentsMatrixOne(matrixTextFied2);
		initDimension(c);
		constructorInitComponentsMatrix(matrixTextFied1);
		setComponentsPaneAuxOneMatrix(matrixTextFied1);
		constructorInitComponentsMatrix(matrixTextFied2);
		setComponentsPaneAuxOneMatrix(matrixTextFied2);
		constructorInitLayoutMatrix(matrixTextFied1, layoutX, layoutY, prefSizeX, prefSizeY, this.layoutX);
		constructorInitLayoutMatrix(matrixTextFied2, layoutX2, layoutY2, prefSizeX, prefSizeY, this.layoutX2);

	}

	private void construtonAxuMatrixOne(char c) {
		offComponentsMatrixOne(matrixTextFied1);
		initDimension(c);
		constructorInitComponentsMatrix(matrixTextFied1);
		setComponentsPaneAuxOneMatrix(matrixTextFied1);
		constructorInitLayoutMatrix(matrixTextFied1, layoutX, layoutY, prefSizeX, prefSizeY, this.layoutX);
	}

	private void constructorInitLayoutMatrix(TextField matrixOne[][], int layoutX, int layoutY, int prefSizeX,
			int prefSiezeY, int value) {
		int length = matrixOne.length;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				matrixOne[i][j].setLayoutX(layoutX);
				matrixOne[i][j].setLayoutY(layoutY);
				matrixOne[i][j].setPrefSize(prefSizeX, prefSizeY);
				layoutX += prefSizeX;
			}
			layoutX = value;
			layoutY += prefSiezeY;
		}
	}

	private void initListenersMatrix() {

	}

	private void offAllComponentsMatrixOne(boolean value) {
		offComponentsMatrixOne(matrixTextFied1);
	}

	private void offComponentsMatrixOne(TextField[][] matrixOne) {
		for (int i = 0; i < matrixOne.length; i++) {
			for (int j = 0; j < matrixOne[0].length; j++) {
				matrixOne[i][j].setVisible(false);
			}
		}
	}

	private void offAllComponentsMatrixTwo(boolean value) {

	}

	/* fim */

	// #############################################################################
	// # # Sistemas x=140 y=540 posições para os butões
	// #############################################################################

	/* Metodos sobre sistemas */

	private void initComponentsSystems() {
		this.line = 4;
		this.column = 5;
		linearTextField = new TextField[line][column];
		linearDouble = new double[line][column];
		xs = new Label[line][column];
		constructorInitComponentsSystems(linearTextField, xs);
	}

	private void constructorInitComponentsSystems(TextField[][] mat, Label[][] xs) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				mat[i][j] = new TextField();
				xs[i][j] = new Label();
			}
		}
	}

	private void offAllComponentsSystems(boolean value) {
		offTextFieldsXs(linearTextField, xs);
	}

	private void initLayoutSystems() {
		constructorInitLayoutSystems(linearTextField, xs, layoutX, layoutY, prefSizeX, prefSizeY);
	}

	private void constructorInitLayoutSystems(TextField linearTextField[][], Label xs[][], int x, int y, int px,
			int py) {
		for (int i = 0; i < linearTextField.length; i++) {
			for (int j = 0; j < linearTextField[0].length; j++) {

				linearTextField[i][j].setLayoutX(x);
				linearTextField[i][j].setLayoutY(y);

				linearTextField[i][j].setPrefSize(px, py);

				x += px;
				if (j < linearTextField[0].length - 1) {
					xs[i][j].setFont(new Font("Dialog", 16));
					xs[i][j].setLayoutX(x + 7);
					xs[i][j].setLayoutY(y);
					xs[i][j].setPrefSize(px, py);
					if (j == linearTextField[0].length - 2) {
						xs[i][j].setText("x" + (j + 1) + "=");
					} else {
						xs[i][j].setText("x" + (j + 1) + "+");
					}

				}

				x += px;
			}
			x = layoutX;
			y += py;
		}

	}

	private void offTextFieldsXs(TextField mat[][], Label xs[][]) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				mat[i][j].setVisible(false);
				xs[i][j].setVisible(false);
			}
		}
	}

	private void construtonAxu(char c) {
		offTextFieldsXs(linearTextField, xs);
		initDimension(c);
		constructorInitComponentsSystems(linearTextField, xs);
		setComponentsPaneAux(linearTextField, xs);
		constructorInitLayoutSystems(linearTextField, xs, layoutX, layoutY, prefSizeX, prefSizeY);
	}

	private void initDimension(char c) {
		switch (c) {
		case '+':
			if (isSystems == false && isOneMatrix && isTwoMatrix == false) {
				this.lineMatrix++;
			} else if (isSystems && isOneMatrix == false && isTwoMatrix == false) {
				this.line++;
				this.column++;
			} else if (isSystems == false && isOneMatrix == false && isTwoMatrix) {
				this.lineMatrix++;
			}
			break;
		case '-':
			if (isSystems == false && isOneMatrix && isTwoMatrix == false) {
				this.lineMatrix--;
			} else if (isSystems && isOneMatrix == false && isTwoMatrix == false) {
				this.line--;
				this.column--;
			} else if (isSystems == false && isOneMatrix == false && isTwoMatrix) {
				this.lineMatrix--;
			}
			break;
		}

		if (isSystems == false && isOneMatrix && isTwoMatrix == false) {
			this.gramsChimidtDados = new float[lineMatrix][lineMatrix];
			this.matrixTextFied1 = new TextField[lineMatrix][lineMatrix];
			this.matrixDados1 = new double[lineMatrix][lineMatrix];
		} else if (isSystems && isOneMatrix == false && isTwoMatrix == false) {

			this.linearTextField = new TextField[line][column];
			this.linearDouble = new double[line][column];
			this.xs = new Label[line][column];

		} else if (isSystems == false && isOneMatrix == false && isTwoMatrix) {

			this.matrixTextFied1 = new TextField[lineMatrix][lineMatrix];
			this.matrixDados1 = new double[lineMatrix][lineMatrix];

			this.matrixTextFied2 = new TextField[lineMatrix][lineMatrix];
			this.matrixDados2 = new double[lineMatrix][lineMatrix];

		}
	}

	private void setComponentsPaneAux(TextField mat[][], Label xs[][]) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				this.pane.getChildren().add(mat[i][j]);
				this.pane.getChildren().add(xs[i][j]);
			}
		}
	}

	private void clear(TextField mat[][]) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				mat[i][j].setText("");
			}
		}
	}

	private void getDados(TextField mat[][], float linearFloat[][]) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				if (!mat[i][j].equals("")) {
					linearFloat[i][j] = Float.parseFloat(mat[i][j].getText());
				} else {
					linearFloat[i][j] = 0;
				}

			}
		}
	}

	private void getInformationSystems(TextField mat[][], double linearDouble[][]) {
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[0].length; j++) {
				if (!mat[i][j].equals(""))
					linearDouble[i][j] = Float.parseFloat(mat[i][j].getText());
				else
					linearDouble[i][j] = (float) 0.0;
			}
		}
	}

	private double[] soluction(double l[][]) {
		try {
			return CalculationGauss.resolve(getMatrix(l), getResultSystem(l));
		} catch (Exception e) {
			AlertUtils.showDialog("Não foi possível resolver o sistema! \n ", AlertType.INFORMATION);
		}
		return null;
	}

	private static double[][] getMatrix(double linearDouble[][]) {
		double linearDoubleAux[][] = new double[linearDouble.length][linearDouble[0].length - 1];
		for (int i = 0; i < linearDouble.length; i++) {
			for (int j = 0; j < linearDouble[0].length - 1; j++) {
				linearDoubleAux[i][j] = linearDouble[i][j];
			}
		}
		return linearDoubleAux;
	}

	private static double[] getResultSystem(double linearDouble[][]) {
		double vector[] = new double[linearDouble.length];
		int k = linearDouble[0].length - 1;
		for (int i = 0; i < linearDouble.length; i++) {
			vector[i] = linearDouble[i][k];
		}
		return vector;
	}

	private String toStringAux(double m[]) {
		String aux = "";
		for (int i = 0; i < m.length; i++) {
			aux = aux + "x" + (i + 1) + "= " + m[i] + "\n";
		}
		return aux;
	}

	/* fim */

	private String toStringResultMatriz(double[][] a) {
		String aux = "";
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				aux = aux + a[i][j] + " ";
			}
			aux = aux + "\n";
		}
		return aux;
	}

	private String toStringGramSchimidt(Map<Integer, float[]> b) {
		String aux = "";
		int tam = gramsChimidtDados.length;
		float[] r = new float[tam];
		for (int i = 0; i < b.size(); i++) {
			r = b.get(i);
			for (int j = 0; j < tam; j++) {
				aux = aux + r[j] + " ";
			}
			aux = aux + "\n";
		}
		return aux;
	}

	private double[][] matrizUm(double[][] a) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				a[i][j] = 1;
			}
		}
		return a;
	}
}
