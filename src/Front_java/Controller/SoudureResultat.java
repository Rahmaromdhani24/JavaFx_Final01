package Front_java.Controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import Front_java.ChartEttenduSoudure;
import Front_java.ChartMoyenneSoudure;
import Front_java.ConfirmationQualiteController;
import Front_java.Configuration.AppInformations;
import Front_java.Configuration.EmailRequest;
import Front_java.Configuration.SoudureInformations;
import Front_java.Configuration.SoudureInformationsCodeB;
import Front_java.Configuration.SoudureReponse;
import Front_java.Configuration.SoudureResult;
import Front_java.Modeles.OperateurInfo;
import Front_java.Modeles.UserDTO;
import Front_java.Services.ServiceSoudure;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.animation.KeyFrame;
import javafx.util.Duration;


public class SoudureResultat{

	 private ServiceSoudure serviceSoudure = new ServiceSoudure();
	 @FXML
	 private BorderPane rootPane; 
	 
	 @FXML
	 private StackPane stackPane;
	 
    @FXML
     private LineChart<Number, Number> chartEtendu;

    @FXML
    private LineChart<Number, Number> chartMoyenne;
  
	@FXML
	private  Button btnSuivant;

	@FXML
	private Button btnLogout ;
	
    @FXML
    private Label dateSystem;

    @FXML
    private Label heureSystem;

    @FXML
    private Label matriculeUser;

    @FXML
    private Label labelMatricule ; 
	
   @FXML
   private Label labelPlant ; 

   @FXML
   private Label  labelPoste ; 
   
   @FXML
   private Label labelSegment ; 
   
   @FXML
   private Label labelOperation ; 
    
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
    private Label valeurNumeroCycle ; 
    @FXML
    private Label nbrEch ;
    @FXML
    private Label freqControle ;
    
    @FXML
    private Label valeurDistanceBC;

    @FXML
    private Label valeurEtendu;

    @FXML
    private Label valeurGrandeurLot;

    @FXML
    private Label valeurMoyenne;

    @FXML
    private Label valeurNumKanban;

    @FXML
    private Label valeurNumNoeud;

    @FXML
    private Label valeurPelageX1;

    @FXML
    private Label valeurPelageX2;

    @FXML
    private Label valeurPelageX3;

    @FXML
    private Label valeurPelageX4;

    @FXML
    private Label valeurPelageX5;

    @FXML
    private Label valeurPliage;

    @FXML
    private Label valeurQuantite;

    @FXML
    private Label valeurTraction; 
    @FXML
    private Label codeControleSelectionner ;   
    /**********************/
    @FXML
    private Pane paneChartMoyenne;
    
    @FXML
    private Pane paneChartEtenduR ; 
    @FXML
	private Label labelProjet;
	
	@FXML
	private Label labelSectionFil;
	
	@FXML
	private Label labelLimitePelage;
	
	@FXML
	private Label labelNombreEch;
	
	@FXML
	private Label labelCodeControle;
	
	@FXML
	private Label labelNumeroCycle;
	
	@FXML
	private Label labelPliage;
	
	@FXML
	private Label labelTraction ;
	
	@FXML
	private Label labelQuantiteAtteint;
	
	@FXML
	private Label labelNumeroKanban ; 
	
	@FXML
	private Label labelGrandeurLot ; 
	
	
	@FXML
	private Label labelNbrNoeud ; 
	
	
	@FXML
	private Label labelDistance ; 
	
	
	@FXML
	private Label labelX1 ; 
	
	
	@FXML
	private Label labelX2 ; 
	
	@FXML
	private Label labelX3 ; 
	
	
	@FXML
	private Label labelX4 ; 
	
	@FXML
	private Label labelMoyenne ;
	
	@FXML
	private Label labelEtendu ;
	
	@FXML
	private Label labelX5 ; 
	
    @FXML
    public void initialize() {
        traduireLabels();
        recupererValeurMoyenneVertMax();
        testerMoyenne(SoudureInformations.moyenne);
        testerEtendu(SoudureInformations.etendu);
        afficherInfosOperateur();
        afficherDateSystem();
        afficherHeureSystem();
        initialiserDonneesPDEKEnregistrer();

        // Charger les données de soudure depuis l’API et créer le chart après
       // chargerSouduresEtCreerChart();
        
        // Une fois les données récupérées, créer le graphique
        StackPane moyenneChartWithZones = ChartMoyenneSoudure.createMoyenneChartWithZones("Numéro Courant :"+SoudureInformations.numeroCycle ,
        		SoudureInformations.moyenne );
        paneChartMoyenne.getChildren().clear(); // au cas où il y a un ancien graphique
        paneChartMoyenne.getChildren().add(moyenneChartWithZones);

        // Idem pour le graphique d'étendue si nécessaire
        StackPane etenduChartWithZones = ChartEttenduSoudure.createEtenduChartWithZones("Numéro Courant :"+SoudureInformations.numeroCycle ,
        		SoudureInformations.etendu );
        paneChartEtenduR.getChildren().clear();
        paneChartEtenduR.getChildren().add(etenduChartWithZones);
    }

   /* private void chargerSouduresEtCreerChart() {
        Task<List<SoudureReponse>> task = new Task<>() {
            @Override
            protected List<SoudureReponse> call() throws Exception {
                String token = AppInformations.token;
                String url = "http://localhost:8281/operations/soudure/soudures-par-pdek-et-page"
                           + "?pdekId=" + Dashboard2Controller.idPdekGlobale
                           + "&pageNumber=" + Dashboard2Controller.numPageGlobale ; 

                System.out.println("id de pdek :"+Dashboard2Controller.idPdekGlobale) ; 
                System.out.println("num page  de pdek :"+Dashboard2Controller.numPageGlobale) ; 

                System.out.println("id de pdek depuis resultat soudure:"+SoudureResult.getPdekId()) ; 
                System.out.println("num page  depuis resultat soudure:"+SoudureResult.getPageNumber()) ; 

                
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Authorization", "Bearer " + token)
                        .GET()
                        .build();

                HttpClient client = HttpClient.newHttpClient();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(response.body(), new TypeReference<List<SoudureReponse>>() {});
                } else {
                    throw new Exception("Erreur " + response.statusCode() + " : " + response.body());
                }
            }
        };

        task.setOnSucceeded(event -> {
            List<SoudureReponse> reponses = task.getValue();
            SoudureResult.setReponses(reponses);

            // Une fois les données récupérées, créer le graphique
            StackPane moyenneChartWithZones = ChartMoyenneSoudure.createMoyenneChartWithZones(reponses);
            paneChartMoyenne.getChildren().clear(); // au cas où il y a un ancien graphique
            paneChartMoyenne.getChildren().add(moyenneChartWithZones);

            // Idem pour le graphique d'étendue si nécessaire
            StackPane etenduChartWithZones = ChartEttenduSoudure.createEtenduChartWithZones(reponses); 
            paneChartEtenduR.getChildren().clear();
            paneChartEtenduR.getChildren().add(etenduChartWithZones);
        });

        task.setOnFailed(event -> {
            System.out.println("Erreur chargement soudures : " + task.getException().getMessage());
        });

        new Thread(task).start();
    }
*/
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
	void logout(ActionEvent event) {

		AppInformations.reset();
    	SoudureInformations.reset();
    	SoudureInformationsCodeB.reset();
    	SoudureResult.reset();


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

    @FXML
    void submit(ActionEvent event) {  
    	
    	SoudureInformations.reset();
    	SoudureInformationsCodeB.reset();
    	SoudureResult.reset();
    	
        try {
        	AppInformations.sectionFilSelectionner = null ; 
        	AppInformations.codeControleSelectionner = null  ; 
        	AppInformations.projetSelectionner = null ; 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/FXML/dashboard1.fxml"));
            Scene dashboardScene = new Scene(loader.load());
            dashboardScene.getStylesheets().add(getClass().getResource("/Front_java/CSS/dashboard1.css").toExternalForm());

            Stage dashboardStage = new Stage();
            dashboardStage.setScene(dashboardScene);
            dashboardStage.setMaximized(true);
            dashboardStage.initStyle(StageStyle.UNDECORATED);
            Image icon = new Image("/logo_app.jpg");
            dashboardStage.getIcons().add(icon);
            dashboardStage.show();

            Stage currentStage = (Stage) btnSuivant.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la fenêtre dashboard : " + e.getMessage());
            showErrorDialog( "Erreur lors du chargement du tableau de bord !" , "Erreur");
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


 
  
/*************************** 	  initialiser les informations ajouter precedament     **********************************/
   public void initialiserDonneesPDEKEnregistrer () {
	   
	   valeurNumeroCycle.setText(SoudureInformations.numeroCycle +"");
	   valeurPliage.setText(SoudureInformations.pliage);
	   valeurTraction.setText(SoudureInformations.traction +"N");
	   valeurQuantite.setText(SoudureInformations.quantiteAtteint+"");
	   valeurNumKanban.setText(SoudureInformations.numeroKanban+"");
	   valeurGrandeurLot.setText(SoudureInformations.grandeurLot +"");
       valeurNumNoeud.setText("N"+SoudureInformations.numNoeud);
	   valeurMoyenne.setText(SoudureInformations.moyenne+"");

	   valeurDistanceBC.setText(SoudureInformations.distanceBC);
	   valeurPelageX1.setText(SoudureInformations.pelageX1+"");
	   valeurPelageX2.setText(SoudureInformations.pelageX2+"");
	   valeurPelageX3.setText(SoudureInformations.pelageX3+"");
	   valeurPelageX4.setText(SoudureInformations.pelageX4+"");
	   valeurPelageX5.setText(SoudureInformations.pelageX5+"");
	   valeurEtendu.setText(SoudureInformations.etendu +"");
	   nbrpelage.setText(SoudureInformations.nbrPelage);

	

   }
   
      /*********************   Alerts  Erreur et warning    ***********/
   
   private void showErrorDialog(String title, String message) {
		Locale locale = getLocaleFromString(AppInformations.langueSelectionnee);
		ResourceBundle bundle = ResourceBundle.getBundle("lang", locale);
		String errorTitle = bundle.getString("error.title");
		String errorMessage = bundle.getString("error.message.ajout_pdek") ;
		String btnFemer = bundle.getString("error.button.close") ;
		Image errorIcon = new Image(getClass().getResource("/icone_erreur.png").toExternalForm());
		ImageView errorImageView = new ImageView(errorIcon);
		errorImageView.setFitWidth(100);
		errorImageView.setFitHeight(100);

		VBox iconBox = new VBox(errorImageView);
		iconBox.setAlignment(Pos.CENTER);

		Label messageLabel = new Label(errorMessage);
		messageLabel.setWrapText(true);
		messageLabel.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-text-alignment: center; -fx-text-fill: black;");

		Label titleLabel = new Label(errorTitle);
		titleLabel.setStyle("-fx-font-size: 19px; -fx-text-alignment: center;");
		VBox titleBox = new VBox(titleLabel);
		titleBox.setAlignment(Pos.CENTER);

		VBox contentBox = new VBox(10, iconBox, messageLabel, titleBox);
		contentBox.setAlignment(Pos.CENTER);

		JFXDialogLayout content = new JFXDialogLayout();
		content.setBody(contentBox);
		content.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

		JFXButton closeButton = new JFXButton(btnFemer);
		closeButton.setStyle("-fx-font-size: 19px; -fx-padding: 10px 20px;-fx-font-weight: bold;");
		content.setActions(closeButton);

		// Utilisation de stackPane ici
		JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
		closeButton.setOnAction(e -> dialog.close());

		// 👉 Lorsqu'on clique sur le bouton "Fermer"
		closeButton.setOnAction(e -> {
			dialog.close(); // Fermer le JFXDialog
			ouvrirFenetreConfirmationQualite(); // 👈 Ouvrir la fenêtre de confirmation
		});

		dialog.show();

		Platform.runLater(() -> {
			StackPane overlayPane = (StackPane) dialog.lookup(".jfx-dialog-overlay-pane");
			if (overlayPane != null) {
				overlayPane.setStyle("-fx-background-color: transparent;");
			}
		});
	}
	
   private void showWarningDialog(String title, String message) {
		Locale locale = getLocaleFromString(AppInformations.langueSelectionnee);
		ResourceBundle bundle = ResourceBundle.getBundle("lang", locale);
		String closeButtonTitle = bundle.getString("error.button.close");

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

		JFXButton closeButton = new JFXButton(closeButtonTitle);
		closeButton.setStyle("-fx-font-size: 19px; -fx-padding: 10px 20px; -fx-font-weight: bold;");
		content.setActions(closeButton);

		JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);

		// 👉 Lorsqu'on clique sur le bouton "Fermer"
		closeButton.setOnAction(e -> {
			dialog.close(); // Fermer le JFXDialog
			ouvrirFenetreConfirmationQualite(); // 👈 Ouvrir la fenêtre de confirmation
		});

		dialog.show();

		Platform.runLater(() -> {
			StackPane overlayPane = (StackPane) dialog.lookup(".jfx-dialog-overlay-pane");
			if (overlayPane != null) {
				overlayPane.setStyle("-fx-background-color: transparent;");
			}
		});
	}


	/**** extraire N depuis valeur Pelage *******/
	public int extractNumber(String input) {
	    String numericPart = input.substring(0, input.length() - 1);
	    return Integer.parseInt(numericPart); 
	}

	/****************** extraire mm depuis section fil ****/
	public double extraireValeurNumeriqueSectionFil(String sectionFil) {
	    return Double.parseDouble(sectionFil.trim().split(" ")[0] );
	}
	
	/******************   Recuperer valeur vert   min de moyenne &&& Max *****************/
	
	public void testerMoyenne(int moyenneEch) {
	    int minPelage = extractNumber(AppInformations.nbrPelage);
	    int maxVert = SoudureInformations.MoyenneVertMax;
        int minVert = serviceSoudure.fetchValeurMoyenneMin() ; 
	    CompletableFuture<String> future = serviceSoudure.getValeurMoyenneMinFromApi();

	    future.thenApply(result -> {
	        System.out.println("Valeur reçue de moyenne vert min  : " + result); // Debugging
	        try {
	            return Integer.parseInt(result.trim());
	        } catch (NumberFormatException e) {
	            System.out.println("Erreur lors de la conversion de la valeur reçue de l'API : " + result);
	            return 0; // Retourne 0 en cas d'erreur
	        }
	    }).thenAccept(parsedValueMoyenne -> {
	    	SoudureInformations.MoyenneVertMin = parsedValueMoyenne ; 
	    	SoudureInformations.minPelage = minPelage ; 
	    	System.out.println("Vérification des valeurs :");
	    	System.out.println("moyenneEch : " + moyenneEch);
	    	System.out.println("minPelage : " + minPelage);
	    	System.out.println("parsedValue : " + parsedValueMoyenne);

	    	
	    	if ((moyenneEch < parsedValueMoyenne) && (minPelage < moyenneEch)) { // Zone jaune
	    	    System.out.println("Zone jaune détectée");
	    	    Platform.runLater(() -> {
	    	        showWarningDialog("La valeur X dépasse les limites d'alarme (zone jaune). \nL'opérateur " 
	    	            + AppInformations.operateurInfo.getPrenom() + " " 
	    	            + AppInformations.operateurInfo.getNom() 
	    	            + " doit informer son supérieur hiérarchique immédiatement.", "Attention - Limite dépassée");
	    	        
	    	    });
	    	    List<Integer> valeursNonConformes = new ArrayList<>();
				if ((moyenneEch < parsedValueMoyenne) && (minPelage < moyenneEch)) valeursNonConformes.add(moyenneEch);
				
				sendWarningNotificationEmailToAgentQualiter( joinValeursAvecPointVirgule(valeursNonConformes), "Supérieur à " + (minVert)+" et inférieur à " + (maxVert)) ; 
				sendWarningNotificationEmailToChefDeLigne( joinValeursAvecPointVirgule(valeursNonConformes), "Supérieur à " + (minVert)+" et inférieur à " + (maxVert)) ; 
			
	    	} 
	        if (moyenneEch < minPelage) { // Zone rouge
	            System.out.println("Zone rouge détectée");
	    	    Platform.runLater(() -> {
	            showErrorDialog("La valeur X dépasse la limite de contrôle (zone rouge). \nL'opérateur " 
	                + AppInformations.operateurInfo.getPrenom() + " " 
	                + AppInformations.operateurInfo.getNom() 
	                + " doit appliquer l'arrêt 1er défaut.", "Problème détecté");
	            
	    	    });
	    	    List<Integer> valeursNonConformes = new ArrayList<>();
				if (moyenneEch < minPelage) valeursNonConformes.add(moyenneEch);
				
				sendErrorNotificationEmailToAgentQualiter( joinValeursAvecPointVirgule(valeursNonConformes), "Supérieur à " + (minVert)+" et inférieur à " + (maxVert)) ; 
				sendErrorNotificationEmailToChefDeLigne( joinValeursAvecPointVirgule(valeursNonConformes), "Supérieur à " + (minVert)+" et inférieur à " + (maxVert)) ; 
			
	    	    } else {
	            System.out.println("Aucune alerte déclenchée");
	        }
	    });
	}
	
	public void recupererValeurMoyenneVertMax() {
	    
	  
		int maxVert  =serviceSoudure.fetchValeurMoyenneMax();
		SoudureInformations.MoyenneVertMax = maxVert ; 
	    System.out.println("max courbe  : " + maxVert);
	    int rouge =  extractNumber(AppInformations.nbrPelage);
	    SoudureInformations.minPelage = rouge; 
	    System.out.println("ligne rouge de  courbe  : " + rouge);
	    int min = rouge -4 ; 
	    System.out.println("valeur min de courbe  : " + min);
		int minVert  =serviceSoudure.fetchValeurMoyenneMin();
		SoudureInformations.moyenneMin = minVert ; 
	    System.out.println("debut zone vert de courbe  : " + minVert );
        int maxEtendu = serviceSoudure.fetchValeurEtenduMax() ;
        SoudureInformations.EtenduValueMax =maxEtendu ; 
	    System.out.println("etendu max de courbe  : " + maxEtendu );

	}

	/******************   Recuperer valeur max  de etendu  *****************/
	
	

	public void testerEtendu(int etenduEch) {
	    
		double max = SoudureInformations.EtenduValueMax  ; 
	    CompletableFuture<String> future = serviceSoudure.getValeurEtenduFromApi();

	    future.thenApply(result -> {
	        System.out.println("Valeur reçue de valeur max de etendu  : " + result); // Debugging
	        try {
	            return Integer.parseInt(result.trim());
	        } catch (NumberFormatException e) {
	            System.out.println("Erreur lors de la conversion de la valeur reçue de l'API : " + result);
	            return 0; // Retourne 0 en cas d'erreur
	        }
	    }).thenAccept(parsedEtenduValue -> {
	    	System.out.println("Vérification des valeurs :");
	    	System.out.println("etenduEch : " + etenduEch);
	    	System.out.println("parsedEtenduValue : " + parsedEtenduValue);
	    	SoudureInformations.EtenduValueMax = parsedEtenduValue ; 
	    	
	    	

	    	if (etenduEch >=  parsedEtenduValue) {
	    		  Platform.runLater(() -> {
	  	            showErrorDialog("La valeur X dépasse la limite de contrôle (zone rouge). \nL'opérateur " 
	  	                + AppInformations.operateurInfo.getPrenom() + " " 
	  	                + AppInformations.operateurInfo.getNom() 
	  	                + " doit appliquer l'arrêt 1er défaut.", "Problème détecté");
	  	            
	  	    	    });	
	    		  List<Integer> valeursNonConformes = new ArrayList<>();
					if (etenduEch >=  parsedEtenduValue) valeursNonConformes.add(etenduEch);
					
					sendErrorNotificationEmailToAgentQualiter( joinValeursAvecPointVirgule(valeursNonConformes), "Inférieur à " + (max)) ; 
					sendErrorNotificationEmailToChefDeLigne( joinValeursAvecPointVirgule(valeursNonConformes), "Inférieur à " + (max)) ; 
				
	               }
	           }) ;
	}
	/******************************************************************************************************************/

	/***************************** Envoie mail erreur "Agent qualite" *****************************/
 	public  void sendErrorNotificationEmailToAgentQualiter(String valeurMesurer, String limiteAcceptable) {
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
 	                    request.setSectionFil(SoudureInformations.sectionFilSelectionner);
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
 	                    request.setNomProcess(AppInformations.operateurInfo.getOperation());
 	                    request.setSectionFil(SoudureInformations.sectionFilSelectionner);
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
 	                    request.setNomProcess(AppInformations.operateurInfo.getOperation());
 	                    request.setSectionFil(SoudureInformations.sectionFilSelectionner);
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
 	/************************************* Recuperer agents des qualite par plant **************************/
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
 	                    request.setSectionFil(SoudureInformations.sectionFilSelectionner);
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
/***********************************************************************************************************************************/
 	public String joinValeursAvecPointVirgule(List<Integer> valeurs) {
 	    StringBuilder sb = new StringBuilder();

 	    for (Integer  valeur : valeurs) {
 	        sb.append(valeur).append("; ");
 	    }

 	    // Supprimer le dernier "; " s'il existe
 	    if (sb.length() > 0) {
 	        sb.setLength(sb.length() - 2);
 	    }

 	    return sb.toString();
 	}
	/*********************************** Traduction ************************************************************/
    private Locale getLocaleFromString(String langue) {
        if (langue == null) return new Locale("fr"); // langue par défaut
        if (langue.contains("ar")) return new Locale("ar");
        if (langue.contains("en")) return new Locale("en");
        return new Locale("fr"); }
   
    private void traduireLabels() {
        try {
            Locale locale = getLocaleFromString(AppInformations.langueSelectionnee);
            ResourceBundle bundle = ResourceBundle.getBundle("lang", locale);
            System.out.println("🌍 langue sélectionnée dans Dashboard1 : " + locale.getLanguage());

            labelMatricule.setText(bundle.getString("label.matricule"));
            labelPlant.setText(bundle.getString("label.plant"));
            labelSegment.setText(bundle.getString("label.segment"));
            labelPoste.setText(bundle.getString("label.poste"));
            labelOperation.setText(bundle.getString("label.operation"));	         
            btnSuivant.setText(bundle.getString("boutonSuivant"));
            
            labelProjet.setText(bundle.getString("label.projet"));
            labelSectionFil.setText(bundle.getString("label.sectionFil"));
            labelLimitePelage.setText(bundle.getString("label.limitePelage"));
            labelNombreEch.setText(bundle.getString("label.nbreEchantillons"));
            labelCodeControle.setText(bundle.getString("label.codeControle"));
            labelNumeroCycle.setText(bundle.getString("label.numeroCycle"));
            labelPliage.setText(bundle.getString("label.pliage"));
            labelTraction.setText(bundle.getString("label.traction"));
            labelQuantiteAtteint.setText(bundle.getString("label.quantiteAtteint"));
            labelNumeroKanban.setText(bundle.getString("label.numeroKanban"));
            labelGrandeurLot.setText(bundle.getString("label.grandeurLot"));
            labelNbrNoeud.setText(bundle.getString("label.nbrNoeud"));
            labelDistance.setText(bundle.getString("label.distanceBC"));
            labelX1.setText(bundle.getString("label.pelageX1"));
            labelX2.setText(bundle.getString("label.pelageX2"));
            labelX3.setText(bundle.getString("label.pelageX3"));
            labelX4.setText(bundle.getString("label.pelageX4"));
            labelX5.setText(bundle.getString("label.pelageX5"));
            labelMoyenne.setText(bundle.getString("label.moyenne"));
            labelEtendu.setText(bundle.getString("label.etendu"));
        } catch (MissingResourceException e) {
            System.out.println("❌ Erreur : Fichier de langue introuvable");
            e.printStackTrace();
        }
    }

    private void ouvrirFenetreConfirmationQualite() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/ConfirmationQM.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de la fenêtre QM
            ConfirmationQualiteController controller = loader.getController();

            // 👉 Injecter la référence du contrôleur courant
            controller.setSoudureResultController(this);

            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Confirmation de l'agent de qualité");

            Scene scene = new Scene(root);
            stage.setScene(scene);

            btnSuivant.setDisable(true);

            // Si tu veux le réactiver *au cas où* la fenêtre se ferme manuellement sans vérification
            stage.setOnHidden(event -> {
                // ne rien faire ici, on attend la confirmation depuis QM
            });

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean activerSuivantSiAgentValide(int matricule, String plant) {
        boolean estAgentQualite = ConfirmationQualiteController.verifierAgentQualite(matricule, plant);
        if (estAgentQualite) {
            btnSuivant.setDisable(false);
            return true;
        } else {
            System.out.println("Utilisateur non autorisé");
            return false;
        }
    }

   
  
}	
