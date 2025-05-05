package Front_java.SertissageNormal;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import Front_java.ChartHauteurSertissageNormal;
import Front_java.Configuration.AppInformations;
import Front_java.Configuration.EmailRequest;
import Front_java.Configuration.EmailValidationPdek;
import Front_java.Configuration.SertissageIDCInformations;
import Front_java.Configuration.SertissageNormaleInformations;
import Front_java.Configuration.TorsadageInformations;
import Front_java.Modeles.OperateurInfo;
import Front_java.Modeles.SertissageNormalData;
import Front_java.Modeles.UserDTO;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.util.Duration;




public class ResultatSertissageNormal {

	// Variables pour stocker la position de la souris
	private double xOffset = 0;
	private double yOffset = 0;
	   @FXML
	    private Button btnClose;

	    @FXML
	    private Button btnLogout;

	    @FXML
	    private Button btnMinimize;

	    @FXML
	    private Button btnSuivant;

	    @FXML
	    private Label codeControleSelectionner;

	    @FXML
	    private Label dateSystem;

	    @FXML
	    private Label hauteurIsolant;

	    @FXML
	    private Label hauteurIsolantEchFin;

	    @FXML
	    private Label hauteurSertissageEch1;


	    @FXML
	    private Label hauteurSertissageEch2;

	    @FXML
	    private Label hauteurSertissageEch3;

	    @FXML
	    private Label hauteurSertissageEchFin;

	    @FXML
	    private Label heureSystem;

	    @FXML
	    private Label labelHauteurIsolant;

	    @FXML
	    private Label labelHauteurSertissage;

	    @FXML
	    private Label labelLargeurIsolant;

	    @FXML
	    private Label labelLargeurSertissage;

	    @FXML
	    private Label labelTraction;

	    @FXML
	    private Label largeurIsolant;

	    @FXML
	    private Label largeurIsolantEchFin;

	    @FXML
	    private Label largeurSertissage;

	    @FXML
	    private Label largeurSertissageEchFin;

	    @FXML
	    private Label machineTraction;

	    @FXML
	    private Label matriculeUser;

	    @FXML
	    private Label nbrCycle;

	    @FXML
	    private Label nomPrenomUser;

	    @FXML
	    private Label nomProjet;

	    @FXML
	    private Label numContact;

	    @FXML
	    private Label numOutil;

	    @FXML
	    private Label operationUser;

	    @FXML
	    private Label plantUser;

	    @FXML
	    private Label posteUser;

	    @FXML
	    private Label produit;

	    @FXML
	    private Label quantiteCycle;

	    @FXML
	    private BorderPane rootPane;

	    @FXML
	    private Label sectionFil;

	    @FXML
	    private Label segementUser;

	    @FXML
	    private Label serieProduit;

	    @FXML
	    private StackPane stackPane;

	    @FXML
	    private Label traction;

	    @FXML
	    private Label tractionEchFin;

	    @FXML
	    private Pane paneChartSertissageNormal;
	    
	    public static int numPageSertissageNormalGlobale;
		public static long idPdekSertissageNormalGlobale;
		
	@FXML
	public void initialize(){
		ajouterPdekAvecSertissageNormal() ;
		initialiserDonneesPDEKEnregistrer() ; 
		afficherInfosOperateur();
		SertissageNormaleInformations.testTerminitionCommande = 0 ; 
		
		afficherDateSystem();
		afficherHeureSystem();
		/*testerHauteurSertissageNormal(SertissageNormaleInformations.hauteurSertissageEch1 ,
        		SertissageNormaleInformations.hauteurSertissageEch2 ,
        		SertissageNormaleInformations.hauteurSertissageEch3 ,
        		SertissageNormaleInformations.hauteurSertissageEchFin) ; 
		*/

		   // Une fois les données récupérées, créer le graphique
        StackPane moyenneChartWithZones =  ChartHauteurSertissageNormal.createChartWithZones(
        		SertissageNormaleInformations.hauteurSertissageEch1 ,
        		SertissageNormaleInformations.hauteurSertissageEch2 ,
        		SertissageNormaleInformations.hauteurSertissageEch3 ,
        		SertissageNormaleInformations.hauteurSertissageEchFin
        		);
        paneChartSertissageNormal.getChildren().clear(); // au cas où il y a un ancien graphique
        paneChartSertissageNormal.getChildren().add(moyenneChartWithZones);

	}
	/* private void chargerSertissagesParPdekEtPage() {
	        Task<List<SertissageNormalReponse>> task = new Task<>() {
	            @Override
	            protected List<SertissageNormalReponse> call() throws Exception {
	                String token = AppInformations.token;
	                String url = "http://localhost:8281/operations/SertissageNormal/sertissages-par-pdek-et-page"
	                           + "?pdekId=" +idPdekSertissageNormalGlobale
	                           + "&pageNumber=" + numPageSertissageNormalGlobale ; 

	                System.out.println("id de pdek :"+idPdekSertissageNormalGlobale) ; 
	                System.out.println("num page  de pdek :"+numPageSertissageNormalGlobale) ; 

	                HttpRequest request = HttpRequest.newBuilder()
	                        .uri(URI.create(url))
	                        .header("Authorization", "Bearer " + token)
	                        .GET()
	                        .build();

	                HttpClient client = HttpClient.newHttpClient();
	                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	                if (response.statusCode() == 200) {
	                    ObjectMapper mapper = new ObjectMapper();
	                    return mapper.readValue(response.body(), new TypeReference<List<SertissageNormalReponse>>() {});
	                } else {
	                    throw new Exception("Erreur " + response.statusCode() + " : " + response.body());
	                }
	            }
	        };

	        task.setOnSucceeded(event -> {
	            List<SertissageNormalReponse> reponses = task.getValue();
	           

	         
	       
	        });

	        task.setOnFailed(event -> {
	            System.out.println("Erreur chargement soudures : " + task.getException().getMessage());
	        });

	        new Thread(task).start();
	    }*/
public void initialiserDonneesPDEKEnregistrer() {
		
		nbrCycle.setText(SertissageNormaleInformations.numCycle+"" );
		hauteurSertissageEch1.setText(SertissageNormaleInformations.hauteurSertissageEch1 +"");
		hauteurSertissageEch2.setText(SertissageNormaleInformations.hauteurSertissageEch2 +"");
		hauteurSertissageEch3.setText(SertissageNormaleInformations.hauteurSertissageEch3 +"");
		hauteurSertissageEchFin.setText(SertissageNormaleInformations.hauteurSertissageEchFin +"");
		
		largeurSertissage.setText(SertissageNormaleInformations.largeurSertissage +"");
		largeurSertissageEchFin.setText(SertissageNormaleInformations.largeurSertissageEchFin +"");
		
		
		largeurIsolant.setText(SertissageNormaleInformations.largeurIsolant +"");
		largeurIsolantEchFin.setText(SertissageNormaleInformations.largeurIsolantEchFin +"");
	
		hauteurIsolant.setText(SertissageNormaleInformations.hauteurIsolant +"");
		hauteurIsolantEchFin.setText(SertissageNormaleInformations.hauteurIsolantEchFin +"");
		traction.setText(SertissageNormaleInformations.traction );
		tractionEchFin.setText(SertissageNormaleInformations.tractionFinEch +"");
		
		produit.setText(SertissageNormaleInformations.produit);
		serieProduit.setText(SertissageNormaleInformations.serieProduit +"");
		machineTraction.setText(SertissageNormaleInformations.machineTraction);
	
		quantiteCycle.setText(SertissageNormaleInformations.quantiteAtteint );
		
		labelHauteurSertissage.setText(SertissageNormaleInformations.labelHauteurSertissage +"/±0.05 mm");
		labelLargeurSertissage.setText(SertissageNormaleInformations.labelLargeurSertissageComplet) ; 
		labelLargeurIsolant.setText(SertissageNormaleInformations.labelLargeurIsolantComplet);
		labelHauteurIsolant.setText(SertissageNormaleInformations.labelHauteurIsolantComplet);
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
	

	@FXML
	public void suivant(ActionEvent event) {
		
		SertissageNormaleInformations.projetSelectionner= null ; 
		SertissageNormaleInformations.codeControleSelectionner= null ; 
		SertissageNormaleInformations.sectionFil = null ; 
		SertissageNormaleInformations.numeroContacts = null ; 
		SertissageNormaleInformations.numeroOutils = null ; 
		try {
			// Charger la scène de Dashboard1
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/SertissageNormal/SelectionSertissageNormal.fxml"));
			Scene dashboard1Scene = new Scene(loader.load());
			dashboard1Scene.getStylesheets()
					.add(getClass().getResource("/Front_java/SertissageNormal/SelectionSertissageNormal.css").toExternalForm());

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
    	SertissageNormaleInformations.reset();

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

	public void afficherInfosOperateur()  {
		// Vérifier si les informations de l'opérateur existent
		if (AppInformations.operateurInfo != null) {
			OperateurInfo operateurInfo = AppInformations.operateurInfo;

			// Mettre à jour les labels avec les informations de l'opérateur
			matriculeUser.setText(String.valueOf(operateurInfo.getMatricule()));
			nomPrenomUser.setText(operateurInfo.getNom() + " " + operateurInfo.getPrenom());
			operationUser.setText("Sertissage ");
			plantUser.setText(operateurInfo.getPlant());
			posteUser.setText(operateurInfo.getPoste());
			segementUser.setText(operateurInfo.getSegment());
			nomProjet.setText(SertissageNormaleInformations.projetSelectionner);
			sectionFil.setText(SertissageNormaleInformations.sectionFil +" mm²");
			codeControleSelectionner.setText(SertissageNormaleInformations.codeControleSelectionner);
			numOutil.setText(SertissageNormaleInformations.numeroOutils);
			numContact.setText(SertissageNormaleInformations.numeroContacts);
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


/*	private void loadNumeroCycleMax() {
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
	}*/

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
 		Image warningIcon = new Image(getClass().getResource("/warning.jpg").toExternalForm());
 		ImageView warningImageView = new ImageView(warningIcon);
 		warningImageView.setFitWidth(150);
 		warningImageView.setFitHeight(150);

 		VBox iconBox = new VBox(warningImageView);
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
	/********************************************* Ajout PDEK  ***************************************************************/
	private void ajouterPdekAvecSertissageNormal() {
		Task<Void> task = new Task<>() {
			@Override
			protected Void call() throws Exception {
				try {
					// Code pour l'ajout du PDEK
					String token = AppInformations.token;
					String encodedProjet = URLEncoder.encode(SertissageNormaleInformations.projetSelectionner,
							StandardCharsets.UTF_8);

					String url = "http://localhost:8281/operations/SertissageNormal/ajouterPdekSertissageNormal" + "?matricule="
							+ AppInformations.operateurInfo.getMatricule() + "&nomProjet=" + encodedProjet;

					// Récupération des valeurs saisies et création de l'objet SoudureDTO
					SertissageNormalData sertissageNormal = new SertissageNormalData();							

					sertissageNormal.setSectionFil(SertissageNormaleInformations.sectionFil);
					LocalDate dateActuelle = LocalDate.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					sertissageNormal.setDate(dateActuelle.format(formatter)); 		
				    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
				    sertissageNormal.setHeureCreation(LocalTime.now().format(timeFormatter));
                    sertissageNormal.setHauteurSertissageEch1(SertissageNormaleInformations.hauteurSertissageEch1); 					    
                    sertissageNormal.setHauteurSertissageEch2(SertissageNormaleInformations.hauteurSertissageEch2); 
                    sertissageNormal.setHauteurSertissageEch3(SertissageNormaleInformations.hauteurSertissageEch3); 
                    sertissageNormal.setHauteurSertissageEchFin(SertissageNormaleInformations.hauteurSertissageEchFin); 
                    sertissageNormal.setLargeurSertissage(SertissageNormaleInformations.largeurSertissage); 
                    sertissageNormal.setLargeurSertissageEchFin(SertissageNormaleInformations.largeurSertissageEchFin); 				    
                    sertissageNormal.setHauteurIsolant(SertissageNormaleInformations.hauteurIsolant); 
                    sertissageNormal.setHauteurIsolantEchFin(SertissageNormaleInformations.hauteurIsolantEchFin); 
                    sertissageNormal.setLargeurIsolant(SertissageNormaleInformations.largeurIsolant); 				    
                    sertissageNormal.setLargeurIsolantEchFin(SertissageNormaleInformations.largeurIsolantEchFin); 
                    sertissageNormal.setTraction(SertissageNormaleInformations.traction); 
                    sertissageNormal.setTractionFinEch(SertissageNormaleInformations.tractionFinEch); 
                    sertissageNormal.setProduit (SertissageNormaleInformations.produit); 
                    sertissageNormal.setSerieProduit(SertissageNormaleInformations.serieProduit); 
                    sertissageNormal.setQuantiteCycle(Integer.parseInt(SertissageNormaleInformations.quantiteAtteint)); 				    
                    sertissageNormal.setCodeControle(SertissageNormaleInformations.codeControleSelectionner); 					    
                    sertissageNormal.setSegment (Integer.parseInt(AppInformations.operateurInfo.getSegment())); 
                    sertissageNormal.setNumeroMachine(SertissageNormaleInformations.machineTraction); 
					sertissageNormal.setTolerance(Double.parseDouble(fetchTolerance(SertissageNormaleInformations.numeroOutils , 
						                                     	SertissageNormaleInformations.numeroContacts ,
						                                     	SertissageNormaleInformations.sectionFil )) ) ; 
					
					  sertissageNormal.setNumeroOutils(SertissageNormaleInformations.numeroOutils) ;  
					  sertissageNormal.setNumeroContacts(SertissageNormaleInformations.numeroContacts) ;   

					
					// Conversion de l'objet SoudureDTO en JSON
					ObjectMapper objectMapper = new ObjectMapper();
					String sertissageNormalJson = objectMapper.writeValueAsString(sertissageNormal);

					HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
							.header("Authorization", "Bearer " + token).header("Content-Type", "application/json")
							.POST(HttpRequest.BodyPublishers.ofString(sertissageNormalJson)).build();

					HttpClient client = HttpClient.newHttpClient();
					HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

					if (response.statusCode() == 200 || response.statusCode() == 201) {
					    String responseBody = response.body();
					    ObjectMapper mapper = new ObjectMapper();
					    JsonNode jsonResponse = mapper.readTree(responseBody);
					    
					    String idSertissage =  jsonResponse.get("idSertissage").asText(); // ou jsonResponse.get("soudureId") selon ton backend
	                    long idSertissageValue = Long.parseLong(idSertissage);
	                    SertissageNormaleInformations.idSertissageNormal= idSertissageValue ; 
	                    
	                    if (testerAjout(SertissageNormaleInformations.hauteurSertissageEch1,
	                    		SertissageNormaleInformations.hauteurSertissageEch2,
	                    		SertissageNormaleInformations.hauteurSertissageEch3 , 
	                    		SertissageNormaleInformations.hauteurSertissageEchFin
	                    		, SertissageNormaleInformations.labelHauteurSertissage)) {
	                        sendMailValidationPDEK();
	                        
	                    } 
	                    else {
	                        System.out.println("Les valeurs ne sont pas dans la tolérance.");
	                    }
					    
					    // On vérifie la présence des champs et on met des valeurs par défaut si manquants
					    long id = jsonResponse.has("pdekId") && !jsonResponse.get("pdekId").isNull()
					            ? jsonResponse.get("pdekId").asLong()
					            : -1L; // valeur par défaut

					    
					    int num = jsonResponse.has("pageNumber") && !jsonResponse.get("pageNumber").isNull()
					            ? jsonResponse.get("pageNumber").asInt()
					            : -1; // valeur par défaut

		
					  
						
					    System.out.println("✅ ID Sertissage recuperer: " + idSertissageValue);
					    System.out.println("✅ ID Sertissage application: " + idSertissageValue);
					    System.out.println("✅ PDEK ID: " + id);
					    System.out.println("✅ Numéro de page: " + num);

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
		task.setOnSucceeded(event -> {
		    // Ces méthodes s'exécutent uniquement quand l'ajout est réussi
			testerHauteurSertissageNormal(SertissageNormaleInformations.hauteurSertissageEch1 ,
	        		SertissageNormaleInformations.hauteurSertissageEch2 ,
	        		SertissageNormaleInformations.hauteurSertissageEch3 ,
	        		SertissageNormaleInformations.hauteurSertissageEchFin) ; 
		   // chargerTorsadagesParPdekEtPage();
		});
		new Thread(task).start();
	}

	
	/****************** Extraire valeur depuis section fil ****************/
	public double extraireValeurNumerique(String sectionFil) {
	    return Double.parseDouble(sectionFil.trim().split(" ")[0] );
	}
	public int extraireValeurNumeriqueInteger(String sectionFil) {
	    return Integer.parseInt(sectionFil.trim().split(" ")[0] );
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
/***** recuperation tolerance de instance de sertissage normal ***************************/
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public  String  fetchTolerance(String numeroOutil, String numeroContact, String sectionFil) {
        try {
            // Construire l'URL avec les paramètres
            String url = "http://localhost:8281/operations/SertissageNormal/tolerance"
                    + "?numeroOutil=" + numeroOutil
                    + "&numeroContact=" + numeroContact
                    + "&sectionFil=" + sectionFil;

            // Construire la requête HTTP avec le token d'authentification
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + AppInformations.token) // Ajouter le token si nécessaire
                    .GET()
                    .build();

            // Envoyer la requête et récupérer la réponse
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Vérifier si la réponse est réussie (code 200 OK)
            if (response.statusCode() == 200) {
                return response.body(); // Retourne directement la hauteur de sertissage
            } else {
                System.out.println("Erreur de l'API: " + response.statusCode());
                return "Erreur API: " + response.statusCode();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur de connexion à l'API";
        }
    }
	/***************************** Envoie mail de validation de pdek *****************************/
 	public void sendMailValidationPDEK() {
 	    Task<Void> task = new Task<Void>() {
 	        @Override
 	        protected Void call() throws Exception {
 	            try {
 	                EmailValidationPdek request = new EmailValidationPdek();
 	                List<UserDTO> listeAgentsQualite = fetchAgentsQualiteByPlant();
 	                System.out.println("Agents qualité récupérés : " + listeAgentsQualite);

 	                for (UserDTO agent : listeAgentsQualite) {
 	                    request.setToEmail(agent.getEmail());
 	                    request.setNomResponsable(agent.getPrenom() + " " + agent.getNom());
 	                    request.setLocalisation("Plant :" + AppInformations.operateurInfo.getPlant() + " , Segment : " + AppInformations.operateurInfo.getSegment());
 	                    request.setNomProcess(AppInformations.operateurInfo.getOperation());
 	                    request.setSectionFil(SertissageNormaleInformations.sectionFil);
 	                    request.setPosteMachine(AppInformations.operateurInfo.getPoste() + " /" + AppInformations.operateurInfo.getMachine());
 	                    request.setDateRemplissage(LocalDate.now()+"");
 	                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
 	                    String formattedTime = LocalTime.now().format(formatter);
 	                  request.setHeureRemplissage(formattedTime);		 	                }

 	                HttpClient client = HttpClient.newHttpClient();
 	                ObjectMapper objectMapper = new ObjectMapper();
 	                String requestBody = objectMapper.writeValueAsString(request);

 	                HttpRequest httpRequest = HttpRequest.newBuilder()
 	                        .uri(URI.create("http://localhost:8281/admin/validerPdekAgentQualite"))
 	                        .header("Content-Type", "application/json")
 	                        .header("Authorization", "Bearer " + AppInformations.token) // Ajout du token ici
 	                        .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
 	                        .build();

 	                HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

 	                // Vérification de la réponse HTTP
 	                if (response.statusCode() == 202) {
 	                    System.out.println("✅ Email d'alerte envoyée avec succès.");
 	                } else if (response.statusCode() == 403) {
 	                    // Analyser et afficher la réponse détaillée en cas de 403
 	                    System.out.println("❌ Échec de l'envoi de l'alerte (accès refusé).");
 	                    System.out.println("Détails de l'erreur 403: " + response.body()); // Affiche le contenu du corps de la réponse
 	                } else {
 	                    System.out.println("⚠️ Erreur lors de l'envoi : code = " + response.statusCode());
 	                    System.out.println("Réponse serveur : " + response.body());
 	                }

 	            } catch (Exception e) {
 	                System.out.println("Erreur technique interne.");
 	                e.printStackTrace(); // Pour logs dev
 	            }
 	            return null;
 	        }
 	    };

 	    new Thread(task).start();
 	}
	/******************************* Recuperer agents des qualite par plant *****************/
 	public List<UserDTO> fetchAgentsQualiteByPlant() {
 	    List<UserDTO> operateurs = new ArrayList<>();

 	    try {
 	        // 🔐 Récupérer le token depuis AppInformations
 	        String token = AppInformations.token;

 	        HttpClient client = HttpClient.newHttpClient();
 	        HttpRequest request = HttpRequest.newBuilder()
 	                .uri(URI.create("http://localhost:8281/operateur/AgentQualiteParPlant?nomPlant=" + AppInformations.operateurInfo.getPlant()))
 	                .header("Authorization", "Bearer " + token) // Ajout du token dans le header
 	                .build();

 	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

 	        if (response.statusCode() == 200) {
 	            ObjectMapper objectMapper = new ObjectMapper();
 	            operateurs = objectMapper.readValue(
 	                    response.body(),
 	                    objectMapper.getTypeFactory().constructCollectionType(List.class, UserDTO.class)
 	            );
 	            System.out.println("Liste des agents : " + operateurs);
 	        } else {
 	            System.err.println("Erreur HTTP: " + response.statusCode());
 	        }

 	    } catch (Exception e) {
 	        e.printStackTrace();
 	    }

 	    return operateurs;
 	}

 	public List<UserDTO> fetchChefDesLignesByPlantAndSegment() {
 	    List<UserDTO> operateurs = new ArrayList<>();

 	    try {
 	        // 🔐 Récupérer le token depuis AppInformations
 	        String token = AppInformations.token;

 	        HttpClient client = HttpClient.newHttpClient();
 	        HttpRequest request = HttpRequest.newBuilder()
 	                .uri(URI.create("http://localhost:8281/operateur/ChefLigneParPlantEtSegment?nomPlant=" + AppInformations.operateurInfo.getPlant()+
 	                		"&segment="+AppInformations.operateurInfo.getSegment()
 	                		+"&operation="+AppInformations.operateurInfo.getOperation()))
 	                .header("Authorization", "Bearer " + token) // Ajout du token dans le header
 	                .build();

 	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

 	        if (response.statusCode() == 200) {
 	            ObjectMapper objectMapper = new ObjectMapper();
 	            operateurs = objectMapper.readValue(
 	                    response.body(),
 	                    objectMapper.getTypeFactory().constructCollectionType(List.class, UserDTO.class)
 	            );
 	            System.out.println("Liste des agents : " + operateurs);
 	        } else {
 	            System.err.println("Erreur HTTP: " + response.statusCode());
 	        }

 	    } catch (Exception e) {
 	        e.printStackTrace();
 	    }

 	    return operateurs;
 	}
 	/***************************************** tester hauteur de sertissage ***************************************************/
	 public void testerHauteurSertissageNormal(double ech1 , double ech2 , double ech3 , double echFin){// 1.25  1.28  1.28  1.28
	    double ideal = SertissageNormaleInformations.labelHauteurSertissage ; //1.25
		double min = SertissageNormaleInformations.labelHauteurSertissage - 0.05 ; //1.20
		double max = SertissageNormaleInformations.labelHauteurSertissage + 0.05 ; //1.30
        double maxJaune =(ideal -0.02) ;  //1.23
        double minJaune2 =(ideal + 0.02) ; //1.27

        if(ech1 <= min ||ech2 <= min ||  ech3 <= min ||  echFin <= min) {
		    Platform.runLater(() -> {
	            updateZoneEtRempliePlanAction( "rouge") ; 

	            showErrorDialog("Une des valeurs mesureés des échantillons dépasse les limites de de contrôle (zone rouge).\n L'opérateur "
	                + AppInformations.operateurInfo.getPrenom() + " " 
	                + AppInformations.operateurInfo.getNom() 
	                + " doit appliquer l'arrêt 1er défaut.", "Problème détecté dans hauteur de sertissage");

	    	    });
			List<Double> valeursNonConformes = new ArrayList<>();
			if (ech1 <= min ) valeursNonConformes.add(ech1);
			if (ech2 <= min ) valeursNonConformes.add(ech2);
			if (ech3 <= min ) valeursNonConformes.add( ech3);
			if (echFin <= min ) valeursNonConformes.add(echFin);
			
			sendErrorNotificationEmailToAgentQualiter( joinValeursAvecPointVirgule(valeursNonConformes), "Supérieur à " + (ideal-0.02)+" et inférieur à " + (ideal + 0.02)) ; 
			sendErrorNotificationEmailToChefDeLigne( joinValeursAvecPointVirgule(valeursNonConformes), "Supérieur à " + (ideal-0.02)+" et inférieur à " + (ideal + 0.02)) ; 
		}
		if(ech1 >= max ||ech2 >=  max  ||  ech3 >= max ||  echFin >=  max) {
		    Platform.runLater(() -> {
	            updateZoneEtRempliePlanAction( "rouge") ; 

	            showErrorDialog("Une des valeurs mesureés des échantillons  dépasse les limites de de contrôle (zone rouge).\n L'opérateur "
	                + AppInformations.operateurInfo.getPrenom() + " " 
	                + AppInformations.operateurInfo.getNom() 
	                + " doit appliquer l'arrêt 1er défaut.", "Problème détecté dans hauteur de sertissage ");

	    	    });
		    
			List<Double> valeursNonConformes = new ArrayList<>();
			if (ech1 >= max ) valeursNonConformes.add(ech1);
			if (ech2 >= max ) valeursNonConformes.add(ech2);
			if (ech3 >= max ) valeursNonConformes.add( ech3);
			if (echFin >= max ) valeursNonConformes.add(echFin);
			
			sendErrorNotificationEmailToAgentQualiter( joinValeursAvecPointVirgule(valeursNonConformes), "Supérieur à " + (ideal-0.02)+" et inférieur à " + (ideal + 0.02)) ; 
			sendErrorNotificationEmailToChefDeLigne( joinValeursAvecPointVirgule(valeursNonConformes),"Supérieur à " + (ideal-0.02)+" et inférieur à " + (ideal + 0.02)) ; 

		}
		if((ech1 <= maxJaune  && ech1 > min ) ||(ech2 <= maxJaune  && ech2 > min ) || (ech3 <= maxJaune  && ech3 > min) ||  (echFin <= maxJaune && echFin > min)) {
		    Platform.runLater(() -> {
	            updateZoneEtRempliePlanAction( "jaune") ; 

	            showWarningDialog("Une des valeurs mesureés des échantillons dépasse les limites de de d'alarme (zone jaune).\n L'opérateur "
	                + AppInformations.operateurInfo.getPrenom() + " " 
	                + AppInformations.operateurInfo.getNom() 
	                + " doit informer son supérieur hiérachique.", "Problème détecté dans hauteur de sertissage");
	    	    });
		    
		    List<Double> valeursNonConformes = new ArrayList<>();
			if (ech1 <= maxJaune && ech1 > min ) valeursNonConformes.add(ech1);
			if (ech2 <= maxJaune && ech2 > min ) valeursNonConformes.add(ech2);
			if (ech3 <= maxJaune && ech3 > min ) valeursNonConformes.add( ech3);
			if (echFin <= maxJaune && echFin > min) valeursNonConformes.add(echFin);
			
			sendWarningNotificationEmailToAgentQualiter( joinValeursAvecPointVirgule(valeursNonConformes),  "Supérieur à " + (ideal-0.02)+" et inférieur à " + (ideal + 0.02)) ; 
			sendWarningNotificationEmailToChefDeLigne( joinValeursAvecPointVirgule(valeursNonConformes), "Supérieur à " + (ideal-0.02)+" et inférieur à " + (ideal + 0.02)) ; 
		    
	 }
		if((ech1 < max  && ech1 > minJaune2 ) ||(ech2 < max  && ech2 > minJaune2 ) || (ech3 < max  && ech3 > minJaune2) ||  (echFin < max && echFin > minJaune2)) {
		    Platform.runLater(() -> {
	            updateZoneEtRempliePlanAction( "jaune") ; 

	            showWarningDialog("Une des valeurs mesureés des échantillons dépasse les limites de de d'alarme (zone jaune).\n L'opérateur "
	                + AppInformations.operateurInfo.getPrenom() + " " 
	                + AppInformations.operateurInfo.getNom() 
	                + " doit informer son supérieur hiérachique.", "Problème détecté dans hauteur de sertissage");
	    	    });
		    
		    List<Double> valeursNonConformes = new ArrayList<>();
			if (ech1 < max && ech1 > minJaune2 ) valeursNonConformes.add(ech1);
			if (ech2 < max && ech2 > minJaune2 ) valeursNonConformes.add(ech2);
			if (ech3 < max && ech3 > minJaune2 ) valeursNonConformes.add( ech3);
			if (echFin < max && echFin > minJaune2) valeursNonConformes.add(echFin);
			
			sendWarningNotificationEmailToAgentQualiter( joinValeursAvecPointVirgule(valeursNonConformes),  "Supérieur à " + (ideal-0.02)+" et inférieur à " + (ideal + 0.02)) ; 
			sendWarningNotificationEmailToChefDeLigne( joinValeursAvecPointVirgule(valeursNonConformes), "Supérieur à " + (ideal-0.02)+" et inférieur à " + (ideal + 0.02)) ; 
		    
	 }
	 }
		/***************************** Envoie mail erreur "Agent qualite" *****************************/
	 	public void sendErrorNotificationEmailToAgentQualiter(String valeurMesurer, String limiteAcceptable) {
	 	    Task<Void> task = new Task<Void>() {
	 	        @Override
	 	        protected Void call() throws Exception {
	 	            try {
	 	                EmailRequest request = new EmailRequest();
	 	                List<UserDTO> listeAgentsQualite = fetchAgentsQualiteByPlant();
	 	                System.out.println("Agents qualité récupérés : " + listeAgentsQualite);

	 	                for (UserDTO agent : listeAgentsQualite) {
	 	                    request.setToEmail(agent.getEmail());
	 	                    request.setNomResponsable(agent.getPrenom() + " " + agent.getNom());
	 	                    request.setLocalisation("Plant :" + AppInformations.operateurInfo.getPlant() + " , Segment : " + AppInformations.operateurInfo.getSegment());
	 	                    request.setNomProcess(AppInformations.operateurInfo.getOperation());
	 	                    request.setSectionFil(SertissageIDCInformations.sectionFilSelectionner);
	 	                    request.setPosteMachine(AppInformations.operateurInfo.getPoste() + " /" + AppInformations.operateurInfo.getMachine());
	 	                    request.setValeurMesuree(valeurMesurer);
	 	                    request.setLimitesAcceptables(limiteAcceptable);
	 	                    request.setDescriptionErreur("Une des valeurs mesurées dépasse les limites de contrôle (zone rouge). L'opérateur applique l'arrêt de 1er défaut.");
	 	                }

	 	                HttpClient client = HttpClient.newHttpClient();
	 	                ObjectMapper objectMapper = new ObjectMapper();
	 	                String requestBody = objectMapper.writeValueAsString(request);

	 	                HttpRequest httpRequest = HttpRequest.newBuilder()
	 	                        .uri(URI.create("http://localhost:8281/admin/AgentQualiteSendMailErreur"))
	 	                        .header("Content-Type", "application/json")
	 	                        .header("Authorization", "Bearer " + AppInformations.token) // Ajout du token ici
	 	                        .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
	 	                        .build();

	 	                HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

	 	                // Vérification de la réponse HTTP
	 	                if (response.statusCode() == 202) {
	 	                    System.out.println("✅ Email d'alerte envoyée avec succès.");
	 	                } else if (response.statusCode() == 403) {
	 	                    // Analyser et afficher la réponse détaillée en cas de 403
	 	                    System.out.println("❌ Échec de l'envoi de l'alerte (accès refusé).");
	 	                    System.out.println("Détails de l'erreur 403: " + response.body()); // Affiche le contenu du corps de la réponse
	 	                } else {
	 	                    System.out.println("⚠️ Erreur lors de l'envoi : code = " + response.statusCode());
	 	                    System.out.println("Réponse serveur : " + response.body());
	 	                }

	 	            } catch (Exception e) {
	 	                System.out.println("Erreur technique interne.");
	 	                e.printStackTrace(); // Pour logs dev
	 	            }
	 	            return null;
	 	        }
	 	    };

	 	    new Thread(task).start();
	 	}
		/***************************** Envoie mail warning "Agent qualite" *****************************/
	 	public void sendWarningNotificationEmailToAgentQualiter(String valeurMesurer, String limiteAcceptable) {
	 	    Task<Void> task = new Task<Void>() {
	 	        @Override
	 	        protected Void call() throws Exception {
	 	            try {
	 	                EmailRequest request = new EmailRequest();
	 	                List<UserDTO> listeAgentsQualite = fetchAgentsQualiteByPlant();
	 	                System.out.println("Agents qualité récupérés : " + listeAgentsQualite);

	 	                for (UserDTO agent : listeAgentsQualite) {
	 	                    request.setToEmail(agent.getEmail());
	 	                    request.setNomResponsable(agent.getPrenom() + " " + agent.getNom());
	 	                    request.setLocalisation("Plant :" + AppInformations.operateurInfo.getPlant() + " , Segment : " + AppInformations.operateurInfo.getSegment());
	 	                    request.setNomProcess("Sertissage");
	 	                    request.setSectionFil(SertissageNormaleInformations.sectionFil+"  mm²");
	 	                    request.setPosteMachine(AppInformations.operateurInfo.getPoste() + " /" + AppInformations.operateurInfo.getMachine());
	 	                    request.setValeurMesuree(valeurMesurer);
	 	                    request.setLimitesAcceptables(limiteAcceptable);
	 	                    request.setDescriptionErreur("Une des valeurs mesurées des échantillons au démarrage  dépasse les limites d'alarme (zone jaune).");
	 	                }

	 	                HttpClient client = HttpClient.newHttpClient();
	 	                ObjectMapper objectMapper = new ObjectMapper();
	 	                String requestBody = objectMapper.writeValueAsString(request);

	 	                HttpRequest httpRequest = HttpRequest.newBuilder()
	 	                        .uri(URI.create("http://localhost:8281/admin/AgentQualiteSendMailWarning"))
	 	                        .header("Content-Type", "application/json")
	 	                        .header("Authorization", "Bearer " + AppInformations.token) // Ajout du token ici
	 	                        .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
	 	                        .build();

	 	                HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

	 	                // Vérification de la réponse HTTP
	 	                if (response.statusCode() == 202) {
	 	                    System.out.println("✅ Email d'alerte envoyée avec succès.");
	 	                } else if (response.statusCode() == 403) {
	 	                    // Analyser et afficher la réponse détaillée en cas de 403
	 	                    System.out.println("❌ Échec de l'envoi de l'alerte (accès refusé).");
	 	                    System.out.println("Détails de l'erreur 403: " + response.body()); // Affiche le contenu du corps de la réponse
	 	                } else {
	 	                    System.out.println("⚠️ Erreur lors de l'envoi : code = " + response.statusCode());
	 	                    System.out.println("Réponse serveur : " + response.body());
	 	                }

	 	            } catch (Exception e) {
	 	                System.out.println("Erreur technique interne.");
	 	                e.printStackTrace(); // Pour logs dev
	 	            }
	 	            return null;
	 	        }
	 	    };

	 	    new Thread(task).start();
	 	}

	 	/***************************** Envoie mail erreur "chef de ligne " *****************************/
	 	public void sendErrorNotificationEmailToChefDeLigne(String valeurMesurer, String limiteAcceptable) {
	 	    Task<Void> task = new Task<Void>() {
	 	        @Override
	 	        protected Void call() throws Exception {
	 	            try {
	 	                EmailRequest request = new EmailRequest();
	 	                List<UserDTO> listeChefLignes = fetchChefDesLignesByPlantAndSegment();
	 	                System.out.println("Agents qualité récupérés : " + listeChefLignes);

	 	                for (UserDTO chefLigne : listeChefLignes) {
	 	                    request.setToEmail(chefLigne.getEmail());
	 	                    request.setNomResponsable(chefLigne.getPrenom() + " " + chefLigne.getNom());
	 	                    request.setLocalisation("Plant :" + AppInformations.operateurInfo.getPlant() + " , Segment : " + AppInformations.operateurInfo.getSegment());
	 	                   request.setNomProcess("Sertissage");
	 	                    request.setSectionFil(SertissageNormaleInformations.sectionFil +"  mm²");
	 	                    request.setPosteMachine(AppInformations.operateurInfo.getPoste() + " /" + AppInformations.operateurInfo.getMachine());
	 	                    request.setValeurMesuree(valeurMesurer);
	 	                    request.setLimitesAcceptables(limiteAcceptable);
	 	                    request.setDescriptionErreur("Une des valeurs mesurées dépasse les limites de contrôle (zone rouge). L'opérateur applique l'arrêt de 1er défaut.");
	 	                }

	 	                HttpClient client = HttpClient.newHttpClient();
	 	                ObjectMapper objectMapper = new ObjectMapper();
	 	                String requestBody = objectMapper.writeValueAsString(request);

	 	                HttpRequest httpRequest = HttpRequest.newBuilder()
	 	                        .uri(URI.create("http://localhost:8281/admin/chefLigneSendMailErreur"))
	 	                        .header("Content-Type", "application/json")
	 	                        .header("Authorization", "Bearer " + AppInformations.token) // Ajout du token ici
	 	                        .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
	 	                        .build();

	 	                HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

	 	                // Vérification de la réponse HTTP
	 	                if (response.statusCode() == 202) {
	 	                    System.out.println("✅ Email d'alerte envoyée avec succès.");
	 	                } else if (response.statusCode() == 403) {
	 	                    // Analyser et afficher la réponse détaillée en cas de 403
	 	                    System.out.println("❌ Échec de l'envoi de l'alerte (accès refusé).");
	 	                    System.out.println("Détails de l'erreur 403: " + response.body()); // Affiche le contenu du corps de la réponse
	 	                } else {
	 	                    System.out.println("⚠️ Erreur lors de l'envoi : code = " + response.statusCode());
	 	                    System.out.println("Réponse serveur : " + response.body());
	 	                }

	 	            } catch (Exception e) {
	 	                System.out.println("Erreur technique interne.");
	 	                e.printStackTrace(); // Pour logs dev
	 	            }
	 	            return null;
	 	        }
	 	    };

	 	    new Thread(task).start();
	 	}
		/***************************** Envoie mail warning "chef de ligne" *****************************/
	 	public void sendWarningNotificationEmailToChefDeLigne(String valeurMesurer, String limiteAcceptable) {
	 	    Task<Void> task = new Task<Void>() {
	 	        @Override
	 	        protected Void call() throws Exception {
	 	            try {
	 	                EmailRequest request = new EmailRequest();
	 	                List<UserDTO> listeChefLignes = fetchChefDesLignesByPlantAndSegment() ; 
	 	                System.out.println("Agents qualité récupérés : " + listeChefLignes);

	 	                for (UserDTO chefLigne : listeChefLignes) {
	 	                    request.setToEmail(chefLigne.getEmail());
	 	                    request.setNomResponsable(chefLigne.getPrenom() + " " + chefLigne.getNom());
	 	                    request.setLocalisation("Plant :" + AppInformations.operateurInfo.getPlant() + " , Segment : " + AppInformations.operateurInfo.getSegment());
	 	                    request.setNomProcess(AppInformations.operateurInfo.getOperation());
	 	                    request.setSectionFil(SertissageIDCInformations.sectionFilSelectionner);
	 	                    request.setPosteMachine(AppInformations.operateurInfo.getPoste() + " /" + AppInformations.operateurInfo.getMachine());
	 	                    request.setValeurMesuree(valeurMesurer);
	 	                    request.setLimitesAcceptables(limiteAcceptable);
	 	                    request.setDescriptionErreur("Une des valeurs mesurées des échantillons au démarrage  dépasse les limites d'alarme (zone jaune).");
	 	                }

	 	                HttpClient client = HttpClient.newHttpClient();
	 	                ObjectMapper objectMapper = new ObjectMapper();
	 	                String requestBody = objectMapper.writeValueAsString(request);

	 	                HttpRequest httpRequest = HttpRequest.newBuilder()
	 	                        .uri(URI.create("http://localhost:8281/admin/chefLigneSendMailWarning"))
	 	                        .header("Content-Type", "application/json")
	 	                        .header("Authorization", "Bearer " + AppInformations.token) // Ajout du token ici
	 	                        .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
	 	                        .build();

	 	                HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

	 	                // Vérification de la réponse HTTP
	 	                if (response.statusCode() == 202) {
	 	                    System.out.println("✅ Email d'alerte envoyée avec succès.");
	 	                } else if (response.statusCode() == 403) {
	 	                    // Analyser et afficher la réponse détaillée en cas de 403
	 	                    System.out.println("❌ Échec de l'envoi de l'alerte (accès refusé).");
	 	                    System.out.println("Détails de l'erreur 403: " + response.body()); // Affiche le contenu du corps de la réponse
	 	                } else {
	 	                    System.out.println("⚠️ Erreur lors de l'envoi : code = " + response.statusCode());
	 	                    System.out.println("Réponse serveur : " + response.body());
	 	                }

	 	            } catch (Exception e) {
	 	                System.out.println("Erreur technique interne.");
	 	                e.printStackTrace(); // Pour logs dev
	 	            }
	 	            return null;
	 	        }
	 	    };

	 	    new Thread(task).start();
	 	}

	 /***************************/
	 	public String joinValeursAvecPointVirgule(List<Double> valeurs) {
	 	    StringBuilder sb = new StringBuilder();

	 	    for (Double valeur : valeurs) {
	 	        sb.append(valeur).append("; ");
	 	    }

	 	    // Supprimer le dernier "; " s'il existe
	 	    if (sb.length() > 0) {
	 	        sb.setLength(sb.length() - 2);
	 	    }

	 	    return sb.toString();
	 	}

	 	 /**************** modifier zone si il ya erreur *********************/
		 public static void updateZoneEtRempliePlanAction(String zone) {
		        try {
		            // Construire l'URL
		            URL url = new URL("http://localhost:8281/operations/SertissageNormal/plan-action-zone/" + zone + "/" + 
		            		SertissageNormaleInformations.idSertissageNormal);
		            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		            // Définir la méthode HTTP PUT
		            connection.setRequestMethod("PUT");
		            connection.setDoOutput(true);

		            // Ajouter les en-têtes
		            connection.setRequestProperty("Authorization", "Bearer " + AppInformations.token);
		            connection.setRequestProperty("Content-Type", "application/json");
		            connection.setRequestProperty("Accept", "application/json");

		            // Envoyer la requête
		            connection.connect();

		            int responseCode = connection.getResponseCode();
		            if (responseCode == HttpURLConnection.HTTP_OK) {
		                System.out.println("Zone mise à jour avec succès !");
		                System.out.println("id de sertissage normal : " + SertissageNormaleInformations.idSertissageNormal);
		            } else {
		                System.out.println("Erreur lors de la mise à jour : " + responseCode);
		            }

		            connection.disconnect();

		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		 
		 public boolean testerAjout(double ech1, double ech2, double ech3, double echFin, double hauteurSertissage) {
			    double min = hauteurSertissage - 0.2;
			    double max = hauteurSertissage + 0.2;

			    return (ech1 >= min && ech1 <= max) &&
			           (ech2 >= min && ech2 <= max) &&
			           (ech3 >= min && ech3 <= max) &&
			           (echFin >= min && echFin <= max);
			}

	}	