package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import Front_java.Configuration.AppInformations;
import Front_java.Configuration.SoudureInformations;
import Front_java.Configuration.SoudureInformationsCodeB;
import Front_java.Loading.LoadingController;
import Front_java.Modeles.OperateurInfo;
import Front_java.Modeles.SoudureDTO;
import Front_java.SoudureUltrason.CodeB.RemplirQuantitieAtteintAvantCodeB;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.util.Duration;




public class RemplirTorsadage {

	// Variables pour stocker la position de la souris
	private double xOffset = 0;
	private double yOffset = 0;
 
	@FXML
	private BorderPane rootPane;

	@FXML
	private StackPane stackPane;

	@FXML
	private Button btnSuivant;

	@FXML
	private Label dateSystem;

	@FXML
	private Label heureSystem;

	@FXML
	private Label matriculeUser;

	@FXML
	private Label nomPrenomUser;

	@FXML
	private Label operationUser;

	@FXML
	private Label segementUser;
	@FXML
	private Label plantUser;

	@FXML
	private Label posteUser;

	@FXML
	private Label nomProjet;

	@FXML
	private Label sectionFilPDEK;

	@FXML
	private Label nbrpelage;

	@FXML
	private Label valeurNumeroCycle;
	@FXML
	private Label nbrEch;
	@FXML
	private Label freqControle;

	@FXML
	private ComboBox<String> pliageCombo;

	@FXML
	private ComboBox<String> distanceCombo;


	public TextField activeTextField;
	
	public TextField getActiveTextField() {
		return activeTextField;
	}
	public void setActiveOnFocus(TextField textField) {
		textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal) {
				activeTextField = textField;
			}
		});
	}
	  @FXML
	    public void handleButtonClick(ActionEvent event) {
	        if (activeTextField != null) {
	            Button clickedButton = (Button) event.getSource();
	            String buttonText = clickedButton.getText();
	            activeTextField.appendText(buttonText);
	            System.out.println("Texte ajouté : " + buttonText);
	        }
	    }

	    // Méthode pour définir le TextField actif
	    public void setActiveTextField(TextField textField) {
	        this.activeTextField = textField;
	    }

	
	
	@FXML
	private TextField tractionField, x1Pleage, x2Pleage, x3Pleage, x4Pleage, x5Pleage, quantiteField, kanbanField,
			grandeurField, nbrNoeudField;

	@FXML
	private ImageView clearImage;

	@FXML
	private Label codeControleSelectionner;

	@FXML
	public void initialize() {
		
		afficherInfosOperateur();
		quantiteField.setDisable(true); 
		AppInformations.testTerminitionCommande = 0 ; 
		
		afficherDateSystem();
		afficherHeureSystem();
		getPelageFromApi();
		loadNumeroCycleMax();
		pliageCombo.getItems().addAll("OK", "NOK");
		distanceCombo.getItems().addAll("OK", "NOK");
		clearImage.setOnMouseClicked(event -> {
			if (activeTextField != null) {
				activeTextField.clear();
			}
		});

		setActiveOnFocus(tractionField);
		setActiveOnFocus(x1Pleage);
		setActiveOnFocus(x2Pleage);
		setActiveOnFocus(x3Pleage);
		setActiveOnFocus(x4Pleage);
		setActiveOnFocus(x5Pleage);

		setActiveOnFocus(quantiteField);
		setActiveOnFocus(kanbanField);
		setActiveOnFocus(grandeurField);
		setActiveOnFocus(nbrNoeudField);
		

	}



	@FXML
	private void close(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();
	}

	@FXML
	private void minimize(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}
	@FXML
	private void scanne(ActionEvent event) {
	
	}
	private boolean checkOtherFields() {
	    // Check if all the relevant fields are not empty
	    return !nbrNoeudField.getText().isEmpty() &&
	           !grandeurField.getText().isEmpty() &&
	           !kanbanField.getText().isEmpty() &&
	           !quantiteField.getText().isEmpty() &&
	           !tractionField.getText().isEmpty() &&
	           !x1Pleage.getText().isEmpty() &&
	           !x2Pleage.getText().isEmpty() &&
	           !x3Pleage.getText().isEmpty() &&
	           !x4Pleage.getText().isEmpty() &&
	           !x5Pleage.getText().isEmpty() &&
	           distanceCombo.getValue() != null && 
	           pliageCombo.getValue() != null;
	}


	@FXML
	public void suivant(ActionEvent event) {
	
	}

	    
	@FXML
	void precedant(ActionEvent event) {
		try {
			// Charger la scène de Dashboard1
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/FXML/dashboard1.fxml"));
			Scene dashboard1Scene = new Scene(loader.load());
			dashboard1Scene.getStylesheets()
					.add(getClass().getResource("/Front_java/CSS/dashboard1.css").toExternalForm());

			// Créer une nouvelle fenêtre (Stage)
			Stage dashboard1Stage = new Stage();
			dashboard1Stage.setScene(dashboard1Scene);
			dashboard1Stage.setMaximized(true);
			dashboard1Stage.initStyle(StageStyle.UNDECORATED); // Supprimer le titre et les boutons
			Image icon = new Image("/logo_app.jpg");
			dashboard1Stage.getIcons().add(icon);
			dashboard1Stage.show();

			// Fermer la fenêtre actuelle (Dashboard2)
			Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			currentStage.close();

		} catch (IOException e) {
			System.out.println("Erreur lors du chargement de la fenêtre dashboard1 : " + e.getMessage());
		}
	}

	@FXML
	void logout(ActionEvent event) {

    	AppInformations.reset();
    	SoudureInformations.reset();
    	SoudureInformationsCodeB.reset();

		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.close();

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/Login.fxml"));
			Scene loginScene = new Scene(loader.load());
			loginScene.getStylesheets().add(getClass().getResource("/Front_java/loginDesign.css").toExternalForm());

			Stage loginStage = new Stage();
			loginStage.initStyle(StageStyle.UNDECORATED); // Placer cette ligne avant show()
			loginStage.setResizable(false); // Désactiver le redimensionnement
			Image icon = new Image("/logo_app.jpg");
			loginStage.getIcons().add(icon);

			loginStage.setScene(loginScene);
			loginStage.show();
		} catch (IOException e) {
			System.out.println("Erreur lors du chargement de la fenêtre login : " + e.getMessage());
		}
	}

	public void afficherInfosOperateur() {
		// Vérifier si les informations de l'opérateur existent
		if (AppInformations.operateurInfo != null) {
			OperateurInfo operateurInfo = AppInformations.operateurInfo;

			// Mettre à jour les labels avec les informations de l'opérateur
			matriculeUser.setText(String.valueOf(operateurInfo.getMatricule()));
			nomPrenomUser.setText(operateurInfo.getNom() + " " + operateurInfo.getPrenom());
			operationUser.setText(operateurInfo.getOperation());
			plantUser.setText(operateurInfo.getPlant());
			posteUser.setText(operateurInfo.getPoste());
			segementUser.setText(operateurInfo.getSegment());
			nomProjet.setText(AppInformations.projetSelectionner);
			sectionFilPDEK.setText(AppInformations.sectionFilSelectionner);
			nbrEch.setText("5 Piéces");
			codeControleSelectionner.setText(AppInformations.codeControleSelectionner);

		} else {
			System.out.println("Aucune information d'opérateur disponible.");
		}
	}

	private void afficherDateSystem() {
		LocalDate dateActuelle = LocalDate.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		dateSystem.setText(dateActuelle.format(formatter));
	}

	private void afficherHeureSystem() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			LocalTime heureActuelle = LocalTime.now();
			heureSystem.setText(heureActuelle.format(formatter));
		}));

		timeline.setCycleCount(Timeline.INDEFINITE); // Répéter indéfiniment
		timeline.play(); // Démarrer l'animation
	}



	private void getPelageFromApi() {
		String token = AppInformations.token;
		String[] parts = AppInformations.sectionFilSelectionner.split(" ");
		String valeurSection = parts[0];
		String apiUrl = "http://localhost:8281/operations/soudure/pelage/" + valeurSection;

		Task<String> task = new Task<>() {
			@Override
			protected String call() throws Exception {
				HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrl))
						.header("Authorization", "Bearer " + token).build();

				HttpClient client = HttpClient.newHttpClient();
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

				if (response.statusCode() == 200) {
					System.out.println("Valeur de pelage : " + response.body());
					return response.body();
				} else {
					throw new Exception("Erreur " + response.statusCode() + ": " + response.body());
				}
			}
		};

		task.setOnSucceeded(event -> {
			String pelageValue = task.getValue();
			nbrpelage.setText(pelageValue);
			AppInformations.nbrPelage = pelageValue;
			SoudureInformations.nbrPelage = pelageValue ; 
			System.out.println("Pelage sauvegardé dans AppInformations : " + AppInformations.nbrPelage);
		});

		task.setOnFailed(event -> {
			Throwable e = task.getException();
			System.out.println("Erreur lors de la récupération du pelage : " + e.getMessage());
		});

		new Thread(task).start();
	}

	/**** recuperation numero de cycle de pdek ****/
	private int getNumeroCycleMaxFromApi() throws Exception {
	    String token = AppInformations.token;

	    // Encodage correct des paramètres pour éviter tout problème
	    String encodedSectionFil = URLEncoder.encode(AppInformations.sectionFilSelectionner, StandardCharsets.UTF_8);
	    String encodedNomProjet = URLEncoder.encode(AppInformations.projetSelectionner, StandardCharsets.UTF_8);
	    String encodedSegmentPDEK = URLEncoder.encode(String.valueOf(AppInformations.operateurInfo.getSegment()), StandardCharsets.UTF_8);
	    String encodedPlantPDEK = URLEncoder.encode(AppInformations.operateurInfo.getPlant(), StandardCharsets.UTF_8);

	    String url = "http://localhost:8281/operations/soudure/numCycleMax?sectionFil=" + encodedSectionFil 
	            + "&segment=" + encodedSegmentPDEK
	            + "&nomPlant=" + encodedPlantPDEK  // Correction ici
	            + "&nomProjet=" + encodedNomProjet;

	    System.out.println("URL API : " + url); // Debugging pour vérifier l'URL générée

	    HttpRequest request = HttpRequest.newBuilder()
	            .uri(URI.create(url))
	            .header("Authorization", "Bearer " + token)
	            .build();

	    HttpClient client = HttpClient.newHttpClient();
	    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	    if (response.statusCode() == 200) {
	        String responseBody = response.body().trim();
	        System.out.println("Numéro de cycle reçu : " + responseBody);

	        try {
	            return Integer.parseInt(responseBody); // Conversion de la réponse en int
	        } catch (NumberFormatException e) {
	            throw new Exception("Réponse inattendue de l'API : " + responseBody);
	        }
	    } else {
	        throw new Exception("Erreur lors de la récupération du numéro de cycle : " + response.statusCode() + " - " + response.body());
	    }
	}


	private void loadNumeroCycleMax() {
		Task<Integer> task = new Task<>() {
			@Override
			protected Integer call() throws Exception {
				return getNumeroCycleMaxFromApi(); // Appelle la méthode corrigée avec encodage
			}
		};

		task.setOnSucceeded(event -> {
			int numeroCycleMax = task.getValue();
			valeurNumeroCycle.setText(String.valueOf(numeroCycleMax + 1));
			SoudureInformations.numeroCycle = numeroCycleMax + 1;
			System.out.println("Numéro de cycle max récupéré : " + numeroCycleMax);
		});

		task.setOnFailed(event -> {
			Throwable e = task.getException();
			valeurNumeroCycle.setText("Erreur");
			System.out.println("Erreur lors de la récupération du numéro de cycle : " + e.getMessage());
		});

		// Lance la tâche dans un thread séparé
		new Thread(task).start();
	}

	/*********************************          Alerts        ***************************************/

	private void showErrorDialog(String title, String message) {
		Image errorIcon = new Image(getClass().getResource("/icone_erreur.png").toExternalForm());
		ImageView errorImageView = new ImageView(errorIcon);
		errorImageView.setFitWidth(100);
		errorImageView.setFitHeight(100);

		VBox iconBox = new VBox(errorImageView);
		iconBox.setAlignment(Pos.CENTER);

		Label messageLabel = new Label(message);
		messageLabel.setWrapText(true);
		messageLabel.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-text-alignment: center; -fx-text-fill: black;");

		Label titleLabel = new Label(title);
		titleLabel.setStyle("-fx-font-size: 19px; -fx-text-alignment: center;");
		VBox titleBox = new VBox(titleLabel);
		titleBox.setAlignment(Pos.CENTER);

		VBox contentBox = new VBox(10, iconBox, messageLabel, titleBox);
		contentBox.setAlignment(Pos.CENTER);

		JFXDialogLayout content = new JFXDialogLayout();
		content.setBody(contentBox);
		content.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

		JFXButton closeButton = new JFXButton("Fermer");
		closeButton.setStyle("-fx-font-size: 19px; -fx-padding: 10px 20px;-fx-font-weight: bold;");
		content.setActions(closeButton);

		// Utilisation de stackPane ici
		JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
		closeButton.setOnAction(e -> dialog.close());

		dialog.show();

		Platform.runLater(() -> {
			StackPane overlayPane = (StackPane) dialog.lookup(".jfx-dialog-overlay-pane");
			if (overlayPane != null) {
				overlayPane.setStyle("-fx-background-color: transparent;");
			}
		});
	}

	private void showConfirmationDialog(String title, String message, Runnable onConfirm) {
		// Créer l'icône de confirmation
		Image confirmIcon = new Image(getClass().getResource("/confirmation.png").toExternalForm());
		ImageView confirmImageView = new ImageView(confirmIcon);
		confirmImageView.setFitWidth(200);
		confirmImageView.setFitHeight(200);

		// Créer le conteneur pour l'icône
		VBox iconBox = new VBox(confirmImageView);
		iconBox.setAlignment(Pos.CENTER);

		// Créer le message
		Label messageLabel = new Label(message);
		messageLabel.setWrapText(true);
		messageLabel.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-text-alignment: center;-fx-text-fill: black;");

		// Créer le titre
		Label titleLabel = new Label(title);
		titleLabel.setStyle("-fx-font-size: 19px; -fx-text-alignment: center;");
		VBox titleBox = new VBox(titleLabel);
		titleBox.setAlignment(Pos.CENTER);

		// Créer le conteneur principal pour le corps du dialog
		VBox contentBox = new VBox(10, iconBox, messageLabel, titleBox);
		contentBox.setAlignment(Pos.CENTER);

		// Créer la mise en page du JFXDialog
		JFXDialogLayout content = new JFXDialogLayout();
		content.setBody(contentBox);
		content.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

		// Créer les boutons de confirmation
		JFXButton confirmButton = new JFXButton("Confirmer");
		confirmButton.setStyle("-fx-font-size: 20px; -fx-padding: 10px 20px; -fx-font-weight: bold;");
		JFXButton cancelButton = new JFXButton("Annuler");
		cancelButton.setStyle("-fx-font-size: 19px; -fx-padding: 10px 20px;");

		// Ajouter les boutons à l'action du JFXDialog
		content.setActions(confirmButton, cancelButton);

		// Utilisation de StackPane pour afficher le dialog
		JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);

		// Gérer l'action du bouton "Confirmer"
		confirmButton.setOnAction(e -> {
			onConfirm.run(); // Exécute l'action passée en paramètre
			dialog.close();
		});

		// Gérer l'action du bouton "Annuler"
		cancelButton.setOnAction(e -> dialog.close());

		// Afficher le dialog
		dialog.show();

		// Rendre l'overlay transparent (comme dans la méthode pour l'erreur)
		Platform.runLater(() -> {
			StackPane overlayPane = (StackPane) dialog.lookup(".jfx-dialog-overlay-pane");
			if (overlayPane != null) {
				overlayPane.setStyle("-fx-background-color: transparent;");
			}
		});
	}

	
	private void showWarningDialog(String title, String message) {
		Image warningIcon = new Image(getClass().getResource("/warning.png").toExternalForm());
		ImageView warningImageView = new ImageView(warningIcon);
		warningImageView.setFitWidth(100);
		warningImageView.setFitHeight(100);

		VBox iconBox = new VBox(warningImageView);
		iconBox.setAlignment(Pos.CENTER);

		Label messageLabel = new Label(message);
		messageLabel.setWrapText(true);
		messageLabel.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-text-alignment: center;");

		Label titleLabel = new Label(title);
		titleLabel.setStyle("-fx-font-size: 19px; -fx-text-alignment: center;");
		VBox titleBox = new VBox(titleLabel);
		titleBox.setAlignment(Pos.CENTER);

		VBox contentBox = new VBox(10, iconBox, messageLabel, titleBox);
		contentBox.setAlignment(Pos.CENTER);

		JFXDialogLayout content = new JFXDialogLayout();
		content.setBody(contentBox);
		content.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

		JFXButton closeButton = new JFXButton("Fermer");
		closeButton.setStyle("-fx-font-size: 19px; -fx-padding: 10px 20px; -fx-font-weight: bold;");
		content.setActions(closeButton);

		JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
		closeButton.setOnAction(e -> dialog.close());

		dialog.show();

		Platform.runLater(() -> {
			StackPane overlayPane = (StackPane) dialog.lookup(".jfx-dialog-overlay-pane");
			if (overlayPane != null) {
				overlayPane.setStyle("-fx-background-color: transparent;");
			}
		});
	}
	/********************************************
	 * Ajout PDEK
	 ***************************************************************/
	private void ajouterPdekAvecSoudure() {
		Task<Void> task = new Task<>() {
			@Override
			protected Void call() throws Exception {
				try {
					// Code pour l'ajout du PDEK
					String token = AppInformations.token;
					String encodedProjet = URLEncoder.encode(AppInformations.projetSelectionner,
							StandardCharsets.UTF_8);

					String url = "http://localhost:8281/operations/soudure/ajouterPDEK" + "?matriculeOperateur="
							+ AppInformations.operateurInfo.getMatricule() + "&projet=" + encodedProjet;

					// Récupération des valeurs saisies et création de l'objet SoudureDTO
					SoudureDTO soudure = new SoudureDTO();
					int x1 = Integer.parseInt(x1Pleage.getText());
					int x2 = Integer.parseInt(x2Pleage.getText());
					int x3 = Integer.parseInt(x3Pleage.getText());
					int x4 = Integer.parseInt(x4Pleage.getText());
					int x5 = Integer.parseInt(x5Pleage.getText());

					// Calcul des valeurs max et min
					int maxValue = Math.max(Math.max(Math.max(x1, x2), Math.max(x3, x4)), x5);
					int minValue = Math.min(Math.min(Math.min(x1, x2), Math.min(x3, x4)), x5);
					int moy = (x1 + x2 + x3 + x4 + x5) / 5;
					int R = maxValue - minValue;

					// Remplir l'objet SoudureDTO avec les valeurs
					soudure.setCode(AppInformations.codeControleSelectionner);
					SoudureInformations.codeControleSelectionner = AppInformations.codeControleSelectionner;
					soudure.setNumeroCycle(SoudureInformations.numeroCycle + 1);
					SoudureInformations.numerCyclePDEK = SoudureInformations.numeroCycle;
					soudure.setSectionFil(AppInformations.sectionFilSelectionner);
					SoudureInformations.sectionFilSelectionner = AppInformations.sectionFilSelectionner;
					soudure.setLimitePelage(AppInformations.nbrPelage);
					SoudureInformations.nbrPelage = AppInformations.nbrPelage;
					soudure.setPliage(pliageCombo.getValue());
					SoudureInformations.pliage = pliageCombo.getValue();
					soudure.setDistanceBC(distanceCombo.getValue());
					SoudureInformations.distanceBC = distanceCombo.getValue();
					soudure.setTraction(tractionField.getText()+" N");
					SoudureInformations.traction = tractionField.getText();
					soudure.setPelageX1(x1);
					SoudureInformations.pelageX1 = x1;
					soudure.setPelageX2(x2);
					SoudureInformations.pelageX2 = x2;
					soudure.setPelageX3(x3);
					SoudureInformations.pelageX3 = x3;
					soudure.setPelageX4(x4);
					SoudureInformations.pelageX4 = x4;
					soudure.setPelageX5(x5);
					SoudureInformations.pelageX5 = x5;
					soudure.setNombreKanban(Integer.parseInt(kanbanField.getText()));
					SoudureInformations.numeroKanban = Integer.parseInt(kanbanField.getText());
					soudure.setGrendeurLot(Integer.parseInt(grandeurField.getText()));
					SoudureInformations.grandeurLot = Integer.parseInt(grandeurField.getText());
					soudure.setNombreNoeud(nbrNoeudField.getText());
					SoudureInformations.numNoeud = nbrNoeudField.getText();
					soudure.setMoyenne(moy);
					SoudureInformations.moyenne = moy;
					soudure.setEtendu(R);
					SoudureInformations.etendu = R;
					LocalDate dateActuelle = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					soudure.setDate(dateActuelle.format(formatter));
					SoudureInformations.dateCreation = dateActuelle.format(formatter);
					soudure.setQuantiteAtteint(Integer.parseInt(quantiteField.getText()));
					SoudureInformations.quantiteAtteint = Integer.parseInt(quantiteField.getText());
					// Conversion de l'objet SoudureDTO en JSON
					ObjectMapper objectMapper = new ObjectMapper();
					String soudureJson = objectMapper.writeValueAsString(soudure);

					HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
							.header("Authorization", "Bearer " + token).header("Content-Type", "application/json")
							.POST(HttpRequest.BodyPublishers.ofString(soudureJson)).build();

					HttpClient client = HttpClient.newHttpClient();
					HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

					if (response.statusCode() == 200) {
						System.out.println("Succès Ajout PDEK : " + response.body());
					} else {
						System.out.println("Erreur dans l'ajout PDEK, code : " + response.statusCode() + ", message : "
								+ response.body());
						throw new Exception("Erreur dans l'ajout PDEK : " + response.body());
					}

				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("Erreur dans la méthode ajouterPdekAvecSoudure : " + e.getMessage());
				}
				return null;
			}
		};

		task.setOnFailed(event -> {
			Throwable exception = task.getException();
			System.out.println("Erreur lors de l'ajout du PDEK : " + exception.getMessage());
			showErrorDialog("Erreur", "Erreur lors de l'ajout du PDEK : " + exception.getMessage());
		});

		new Thread(task).start();
	}
	/*********************** Traction ***************************/
	public CompletableFuture<Integer> getTractionFromApi(String sectionFil) {
	    return CompletableFuture.supplyAsync(() -> {
	        try {
	            String sectionFilEncodee = URLEncoder.encode(sectionFil, StandardCharsets.UTF_8.toString());
	            sectionFilEncodee = sectionFilEncodee.replace("+", "%20");
	            String apiUrl = "http://localhost:8281/operations/soudure/traction2/" + sectionFilEncodee;
	            System.out.println("URL de l'API : " + apiUrl);  // Log pour vérifier l'URL envoyée
	            // Créer une connexion HTTP
	            URL url = new URL(apiUrl);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");
	            connection.setRequestProperty("Authorization", "Bearer " + AppInformations.token);  // Ajouter le token

	            int responseCode = connection.getResponseCode();
	            System.out.println("Code de réponse HTTP : " + responseCode);  // Log du code de réponse

	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                // Lire la réponse de l'API et la convertir en entier
	                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                String inputLine;
	                StringBuilder response = new StringBuilder();
	                while ((inputLine = in.readLine()) != null) {
	                    response.append(inputLine);
	                }
	                in.close();
	                String responseString = response.toString().trim();

	                // Log de la réponse de l'API
	                System.out.println("Réponse de l'API : " + responseString);

	                // Convertir la réponse en entier (traction)
	                try {
	                    return Integer.parseInt(responseString);
	                } catch (NumberFormatException e) {
	                    System.out.println("Erreur lors de la conversion de la réponse en entier : " + e.getMessage());
	                    return -1;  // Retourner -1 si la conversion échoue
	                }
	            } else {
	                // Log du message d'erreur détaillé
	                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
	                StringBuilder errorResponse = new StringBuilder();
	                String errorLine;
	                while ((errorLine = errorReader.readLine()) != null) {
	                    errorResponse.append(errorLine);
	                }
	                errorReader.close();
	                System.out.println("Erreur de l'API, code : " + responseCode);
	                System.out.println("Message d'erreur : " + errorResponse.toString());
	                return -1;  // Retourner -1 en cas d'erreur
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            return -1;  // En cas d'exception, retourner -1
	        }
	    });
	}

	/****************** Extraire valeur depuis section fil ****************/
	public double extraireValeurNumerique(String sectionFil) {
	    return Double.parseDouble(sectionFil.trim().split(" ")[0] );
	}
	public int extraireValeurNumeriqueInteger(String sectionFil) {
	    return Integer.parseInt(sectionFil.trim().split(" ")[0] );
	}

	/****************************************/
    public void actualiserFenetreMere() {
        if (AppInformations.testTerminitionCommande == 1) {
            quantiteField.setDisable(false); // Activer le champ quantiteField
        }
    }
    public void afficherNotification(String message) {
        System.out.println("📢 Affichage d'une notification : " + message);

        if (stackPane == null) {
            System.out.println("❌ Erreur : stackPane est null.");
            return;
        }

        // Chargement de l'icône avec vérification
        ImageView infoImageView = new ImageView();
        URL iconURL = getClass().getResource("/icone_info.png");
        if (iconURL != null) {
            infoImageView.setImage(new Image(iconURL.toExternalForm()));
        } else {
            System.out.println("❌ Image non trouvée : /icone_info.png");
        }

        infoImageView.setFitWidth(80);
        infoImageView.setFitHeight(80);
        VBox iconBox = new VBox(infoImageView);
        iconBox.setAlignment(Pos.CENTER);

        // Titre et message
        Label titleLabel = new Label("Notification");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-alignment: center;");

        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setStyle("-fx-font-size: 18px; -fx-text-alignment: center; -fx-text-fill: black;-fx-font-weight: bold; ");

        VBox contentBox = new VBox(10, iconBox, titleLabel, messageLabel);
        contentBox.setAlignment(Pos.CENTER);

        // Création du layout de la boîte de dialogue
        JFXDialogLayout content = new JFXDialogLayout();
        content.setBody(contentBox);
        content.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 20px;");

        // Bouton de fermeture stylisé
        JFXButton closeButton = new JFXButton("Fermer");
        closeButton.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-background-color: #007BFF; -fx-text-fill: white;");

        // Conteneur pour centrer le bouton
        HBox buttonBox = new HBox(closeButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0)); // Optionnel : Ajoute un peu d'espace en haut

        // Ajout du bouton centré dans la boîte de dialogue
        content.setActions(buttonBox);

        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);

        closeButton.setOnAction(e -> {
            dialog.close();
            System.out.println("Notification fermée par l'utilisateur.");
        });

        dialog.show();

        // Fermeture automatique après 5 secondes
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> {
            if (dialog.isVisible()) {
                dialog.close();
                System.out.println(" Notification fermée automatiquement après 5 secondes.");
            }
        });
        pause.play();

        // Supprimer l'overlay gris foncé
        Platform.runLater(() -> {
            Node overlayPane = dialog.lookup(".jfx-dialog-overlay-pane");
            if (overlayPane != null) {
                overlayPane.setStyle("-fx-background-color: transparent;");
            }
        });
    }

}