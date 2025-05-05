package Front_java.SertissageNormal;

import java.io.IOException;
import java.net.*;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import Front_java.Configuration.AppInformations;
import Front_java.Configuration.SertissageNormaleInformations;
import Front_java.Configuration.ToleranceParser;
import Front_java.Modeles.OperateurInfo;
import Front_java.SertissageNormal.loading.LoadingSertissageNormal;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.util.Duration;




public class RemplirSertissageNormal {

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
	    private Button btnPrecedant;

	    @FXML
	    private Button btnSuivant;

	    @FXML
	    private ImageView clearImage;

	    @FXML
	    private Label codeControleSelectionner;

	    @FXML
	    private Label dateSystem;	  

	    @FXML
	    private TextField hauteurIsolant;

	    @FXML
	    private TextField hauteurIsolantEchFin;

	    @FXML
	    private TextField hauteurSertissageEch1;

	 
	    @FXML
	    private TextField hauteurSertissageEch2;

	 
	    @FXML
	    private TextField hauteurSertissageEch3;


	    @FXML
	    private TextField hauteurSertissageEchFin;

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
	    private TextField largeurIsolant;

	    @FXML
	    private TextField largeurIsolantEchFin;

	    @FXML
	    private TextField largeurSertissage;

	    @FXML
	    private TextField largeurSertissageEchFin;

	    @FXML
	    private ComboBox<String> listeMachineTraction;

	    @FXML
	    private ComboBox<String> listeProduits;

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
	    private TextField quantiteCycle;

	    @FXML
	    private BorderPane rootPane;

	    @FXML
	    private Label sectionFil;

	    @FXML
	    private Label segementUser;

	    @FXML
	    private TextField serieProduit;

	    @FXML
	    private StackPane stackPane;

	    @FXML
	    private TextField traction;

	    @FXML
	    private TextField tractionEchFin;

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
		        }
		    }

		    // Méthode pour définir le TextField actif
		    public void setActiveTextField(TextField textField) {
		        this.activeTextField = textField;
		    }

	@FXML
	public void initialize() throws Exception {
		loadDernierNumeroCycle() ; 
	hauteurSertissageEchFin.setDisable(true); 
	largeurSertissageEchFin.setDisable(true);
	hauteurIsolantEchFin.setDisable(true);
    largeurIsolantEchFin.setDisable(true);
    tractionEchFin.setDisable(true);
    quantiteCycle.setDisable(true);

        loadListeMachinesTractions() ; 
        loadListeProduits() ; 
		afficherInfosOperateur();
        SertissageNormaleInformations.testTerminitionCommande = 0 ; 
		
		afficherDateSystem();
		afficherHeureSystem();
	    clearImage.setOnMouseClicked(event -> {
			if (activeTextField != null) {
				activeTextField.clear();
			}
		});

		setActiveOnFocus(hauteurSertissageEch1);
		setActiveOnFocus(hauteurSertissageEch2);
		setActiveOnFocus(hauteurSertissageEch3);
		setActiveOnFocus(hauteurSertissageEchFin);
		setActiveOnFocus(largeurSertissage);
		setActiveOnFocus(largeurSertissageEchFin);
		

		setActiveOnFocus(hauteurIsolant);
		setActiveOnFocus(hauteurIsolantEchFin);
		setActiveOnFocus(largeurIsolant);		
		setActiveOnFocus(largeurIsolantEchFin);		


		setActiveOnFocus(traction);
		setActiveOnFocus(tractionEchFin);
		setActiveOnFocus(serieProduit);
		setActiveOnFocus(quantiteCycle);

	
	
	}
/********************** load Liste machine traction ************************/
	private void loadListeMachinesTractions() {
	    Task<ObservableList<String>> task = new Task<>() {
	        @Override
	        protected ObservableList<String> call() {
	            return FXCollections.observableArrayList(
	                "D-128", "D-101"
	            );
	        }
	    };

	    task.setOnSucceeded(event -> {
	      listeMachineTraction.setItems(task.getValue());
	      listeMachineTraction.getSelectionModel().clearSelection(); // Désélectionner toute valeur par défaut
	      listeMachineTraction.setValue(null); // S'assurer qu'aucune valeur n'est affichée au démarrage

	      listeMachineTraction.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
	       
	      });
	    });

	    task.setOnFailed(event -> {
	        System.out.println("Erreur lors du chargement des numéros de fils : " + task.getException().getMessage());
	    });

	    new Thread(task).start();
	}
/********************** load liste des produits ***********************/
	private void loadListeProduits() {
	    Task<ObservableList<String>> task = new Task<>() {
	        @Override
	        protected ObservableList<String> call() {
	            return FXCollections.observableArrayList(
	                "ZKW", "Produit 1", "Produit 2" , "Produit 3"
	            );
	        }
	    };

	    task.setOnSucceeded(event -> {
	      listeProduits.setItems(task.getValue());
	      listeProduits.getSelectionModel().clearSelection(); // Désélectionner toute valeur par défaut
	      listeProduits.setValue(null); // S'assurer qu'aucune valeur n'est affichée au démarrage

	      listeProduits.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
	       
	      });
	    });

	    task.setOnFailed(event -> {
	        System.out.println("Erreur lors du chargement des numéros de fils : " + task.getException().getMessage());
	    });

	    new Thread(task).start();
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
	    return !hauteurSertissageEch1.getText().isEmpty() &&
	           !hauteurSertissageEch2.getText().isEmpty() &&
	           !hauteurSertissageEch3.getText().isEmpty() &&
	           !largeurSertissage.getText().isEmpty() &&
	           !hauteurIsolant.getText().isEmpty() &&
	           !largeurIsolant.getText().isEmpty() &&
	           !traction.getText().isEmpty() &&
	           listeProduits.getValue() != null &&	  
	    	   listeMachineTraction.getValue() != null &&	          	         
	           !serieProduit.getText().isEmpty() ;
	}
	@FXML
	public void suivant(ActionEvent event) {
		  centerTextFields(
				  hauteurSertissageEch1, hauteurSertissageEch2, hauteurSertissageEch3, 
				  largeurSertissage, hauteurIsolant, largeurIsolant, 
				  traction, serieProduit , quantiteCycle , hauteurIsolantEchFin , hauteurIsolantEchFin,
				  hauteurSertissageEchFin , largeurIsolantEchFin , largeurSertissageEchFin
			    );
		 
		 // 1. Vérification des champs obligatoires
		  if (hauteurSertissageEch1.getText().isEmpty() || hauteurSertissageEch2.getText().isEmpty() || hauteurSertissageEch3.getText().isEmpty()
		            || largeurSertissage.getText().isEmpty() || hauteurIsolant.getText().isEmpty() || largeurIsolant.getText().isEmpty()
		            || traction.getText().isEmpty() || listeProduits.getValue() == null         
		            || serieProduit.getText().isEmpty()  ||  serieProduit.getText().isEmpty() 
		            || listeMachineTraction.getValue() == null  ) {

		        showErrorDialog("Veuillez remplir tous les champs avant de continuer !", "Champs obligatoires");
		        return; // Arrêt si un champ est vide
		    }
		    if ((!hauteurSertissageEchFin.isDisabled() && hauteurSertissageEchFin.getText().isEmpty()) ||
		    	    (!largeurSertissageEchFin.isDisabled() && largeurSertissageEchFin.getText().isEmpty()) ||
		    	    (!largeurIsolantEchFin.isDisabled() && largeurIsolantEchFin.getText().isEmpty()) ||
		    	    (!hauteurIsolantEchFin.isDisabled() && hauteurIsolantEchFin.getText().isEmpty()) ||
		    	    (!tractionEchFin.isDisabled() && tractionEchFin.getText().isEmpty()) ||
		    	    (!quantiteCycle.isDisabled() && quantiteCycle.getText().isEmpty())) {
		    	    
		    	    showErrorDialog("Veuillez remplir tous les champs avant de continuer !", "Champs obligatoires");
		    	    return; // Arrête l'exécution de la méthode après l'alerte
		    	}
	   
	    // 3. Si tous les champs sont remplis, afficher l'alerte de confirmation
     	    boolean hasError = false;

	    // Vérifier si des champs obligatoires sont vides
     	    if (checkOtherFields() && !hauteurSertissageEchFin.getText().isEmpty()&& !largeurSertissageEchFin.getText().isEmpty() 
            		&& !hauteurIsolantEchFin.getText().isEmpty()  && !largeurIsolantEchFin.getText().isEmpty() && 
            		!tractionEchFin.getText().isEmpty() &&  !quantiteCycle.getText().isEmpty()) {
     	    	
	    	
	 
     	    	//// ligne 1 : hauteur Sertissage 
     	    	 double valHauteurSertissage = SertissageNormaleInformations.labelHauteurSertissage ; 
     	    	 double maxHauteur = valHauteurSertissage +0.05 ; 
     	    	 if (areFieldsEqual(hauteurSertissageEch1 , hauteurSertissageEch2 , hauteurSertissageEch3 , hauteurSertissageEchFin)) {
     	            colorBorderRed(hauteurSertissageEch1, hauteurSertissageEch2, hauteurSertissageEch3, hauteurSertissageEchFin);
     	            showErrorDialog("Les valeurs des échantillons de hauteur de sertissage  doivent être différentes et ne dépasse pas "+ maxHauteur, "");
     	            hasError = true;
     	        }
     	    	 
     	     
     	  //// ligne 2 : largeur Sertissage 
     	        double valLargeurSertissage = SertissageNormaleInformations.labelLargeurSertissage ; 
     	  	   ToleranceParser.Tolerance tol = ToleranceParser.parseTolerance(
     	  			    fetchToleranceLargeurSertissageFromAPI(
     	  			        SertissageNormaleInformations.numeroOutils,
     	  			        SertissageNormaleInformations.numeroContacts,
     	  			        SertissageNormaleInformations.sectionFil
     	  			    )
     	  			);
     	  	   
     	  	   double min = valLargeurSertissage - tol.lower;
     	  	   double max = valLargeurSertissage + tol.upper;   	 

    	    	 if (areFieldsEqualDeuxChamps(largeurSertissage , largeurSertissageEchFin )) {
    	            colorBorderRed(largeurSertissage, largeurSertissageEchFin);
    	            showErrorDialog("Les valeurs des échantillons de largeur de sertissage  doivent être différentes et ne dépasse pas "+max, "");
    	            hasError = true;
    	        }
    	      
    	        //// ligne 3 : hauteur isolant   
      	       double valHauteurIsolant = SertissageNormaleInformations.labelHauteurIsolant ; 
    	        String toleranceStrHauteurIsolant = fetchToleranceHauteurIsolantFromAPI(
    	        		SertissageNormaleInformations.numeroOutils,
    	        		SertissageNormaleInformations.numeroContacts,
    	        		SertissageNormaleInformations.sectionFil
    	        		).replace("±", "").trim(); // Suppression du symbole ±
    	        		double toleranceHauteurIsolant = Double.parseDouble(toleranceStrHauteurIsolant);
    	        		
     	    	 if (areFieldsEqualDeuxChamps(hauteurIsolant , hauteurIsolantEchFin )) {
     	            colorBorderRed(hauteurIsolant, hauteurIsolantEchFin);
     	            showErrorDialog("Les valeurs des échantillons de hauteur de isolant   doivent être différentes et ne dépasse pas "+(valHauteurIsolant+toleranceHauteurIsolant ), "");
     	            hasError = true;
     	        }
     	      
     	       //// ligne 4  : largeur isolant 
      	       double valLargeurIsolant = SertissageNormaleInformations.labelLargeurIsolant ; 
    	       	
    	        		   ToleranceParser.Tolerance tolIsolant = ToleranceParser.parseTolerance(
    	        				   fetchToleranceLargeurIsolantFromAPI(
    	            			        SertissageNormaleInformations.numeroOutils,
    	            			        SertissageNormaleInformations.numeroContacts,
    	            			        SertissageNormaleInformations.sectionFil
    	            			    )
    	            			);
    	            	   
    	            	 double minTolIsol = valLargeurIsolant - tolIsolant.lower;
    	            	 double maxTolIsol = valLargeurIsolant + tolIsolant.upper;   
    	            	 System.out.println("le minTolIsol est ="+minTolIsol) ;
    	             	 System.out.println("le maxTolIsol est ="+maxTolIsol) ;
     	    	 if (areFieldsEqualDeuxChamps(largeurIsolant , largeurIsolantEchFin )) {
     	            colorBorderRed(largeurIsolant, largeurIsolantEchFin);
     	            showErrorDialog("Les valeurs des échantillons de largeur de isolant  doivent être différentes et ne dépasse pas "+(maxTolIsol), "");
     	            hasError = true;
     	        }
     	     
     	        //// ligne 5 : traction
       	       String valtractionString = SertissageNormaleInformations.labelTraction ; 
               int tractionValue = extractValue(valtractionString) ; 
               System.out.print(" valeur de traction numerique "+tractionValue);
     	        		
      	    	 if (areFieldsEqualDeuxChamps(traction , tractionEchFin )) {
      	            colorBorderRed(traction , tractionEchFin);
      	            showErrorDialog("Les valeurs des échantillons de traction  doivent être différentes et ne dépasse pas "+tractionValue+" N", "");
      	            hasError = true;
      	        }
      	  
      	    if (verifierHauteurSertissagesFinales()) {
          	    return; // stop si erreur détectée
          	}
      	    if (verifierLargeurSertissagesFinales()) {
          	    return; // stop si erreur détectée
          	}
      	    if (verifierHauteurIsolantFinales()) {
        	    return; // stop si erreur détectée
        	}
    	    if (verifierLargeurIsolantFinales()) {
        	    return; // stop si erreur détectée
        	}
    	    if (verifierTractionFinale()) {
        	    return; // stop si erreur détectée
        	}
      	      
     	        if (hasError) {
     	            return;
     	        }

	        // Aucune erreur => afficher la confirmation
	        String message = "Veuillez confirmer les données saisies ? \n\n";

	        showConfirmationDialog(message, "Confirmation", () -> {
	        	
	        	SertissageNormaleInformations.hauteurSertissageEch1 = Double.parseDouble(hauteurSertissageEch1.getText()); 
            	SertissageNormaleInformations.hauteurSertissageEch2 = Double.parseDouble(hauteurSertissageEch2.getText()); 
            	SertissageNormaleInformations.hauteurSertissageEch3 = Double.parseDouble(hauteurSertissageEch3.getText()); 
            	SertissageNormaleInformations.hauteurSertissageEchFin =Double.parseDouble( hauteurSertissageEchFin.getText()); 
            	SertissageNormaleInformations.largeurSertissage = Double.parseDouble(largeurSertissage.getText()); 
            	SertissageNormaleInformations.largeurSertissageEchFin = Double.parseDouble(largeurSertissageEchFin.getText()); 
            	SertissageNormaleInformations.hauteurIsolant = Double.parseDouble(hauteurIsolant.getText());
            	SertissageNormaleInformations.hauteurIsolantEchFin = Double.parseDouble(hauteurIsolantEchFin.getText());
            	SertissageNormaleInformations.largeurIsolant = Double.parseDouble(largeurIsolant.getText());
            	SertissageNormaleInformations.largeurIsolantEchFin = Double.parseDouble(largeurIsolantEchFin.getText());
            	SertissageNormaleInformations.traction = traction.getText();
            	SertissageNormaleInformations.tractionFinEch = Integer.parseInt( tractionEchFin.getText());
            	SertissageNormaleInformations.produit = listeProduits.getValue();
            	SertissageNormaleInformations.quantiteAtteint = quantiteCycle.getText();
            	SertissageNormaleInformations.machineTraction = listeMachineTraction.getValue();
            	SertissageNormaleInformations.serieProduit = serieProduit.getText() ; 
            	    
                // Affichage direct de la fenêtre SoudureResultat
                try {
                	  // Chargement de la nouvelle fenêtre
            	    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/Front_java/SertissageNormal/ResultatSertissageNormal.fxml"));
            	    Scene resultScene = new Scene(loader2.load());
            	    resultScene.getStylesheets().add(getClass().getResource("/Front_java/SertissageNormal/ResultatSertissageNormal.css").toExternalForm());                   	    
            	    Stage resultStage = new Stage();
            	    resultStage.setScene(resultScene);
            	    resultStage.setMaximized(true);
            	    resultStage.initStyle(StageStyle.UNDECORATED);                    	    
            	    // Ajout d'une icône
            	    Image icon = new Image("/logo_app.jpg");
            	    resultStage.getIcons().add(icon);                   	    
            	    // Affichage de la nouvelle fenêtre
            	    resultStage.show();
            	    // Fermeture de la fenêtre actuelle
            	    Stage currentStage = (Stage) btnSuivant.getScene().getWindow();
                    currentStage.close();
                } catch (IOException ex) {
                    System.out.println("Erreur lors du chargement de la fenêtre verification : " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
        } else {     
        	if (verifierHauteurSertissages()) {
        	    return; // stop si erreur détectée
        	}
        	if (verifierLargeurSertissages()) {
        	    return; // stop si erreur détectée
        	}
        	if (verifierHauteurIsolant()) {
        	    return; // stop si erreur détectée
        	}
        	if (verifierLargeurIsolant()) {
        	    return; // stop si erreur détectée
        	}
        	if (verifierTraction()) {
        	    return; // stop si erreur détectée
        	}
        			  centerTextFields(
        					  hauteurSertissageEch1, hauteurSertissageEch2, hauteurSertissageEch3, 
        					  largeurSertissage, hauteurIsolant, largeurIsolant, 
        					  traction, serieProduit , quantiteCycle , hauteurIsolantEchFin , hauteurIsolantEchFin,
        					  hauteurSertissageEchFin , largeurIsolantEchFin , largeurSertissageEchFin , tractionEchFin 
        				    );
        			 SertissageNormaleInformations.numCycle = Integer.parseInt( nbrCycle.getText()) ; 
                  	SertissageNormaleInformations.hauteurSertissageEch1 = Double.parseDouble(hauteurSertissageEch1.getText()); 
                  	SertissageNormaleInformations.hauteurSertissageEch2 = Double.parseDouble(hauteurSertissageEch2.getText()); 
                  	SertissageNormaleInformations.hauteurSertissageEch3 = Double.parseDouble(hauteurSertissageEch3.getText()); 
                  	SertissageNormaleInformations.largeurSertissage = Double.parseDouble(largeurSertissage.getText()); 
                  	SertissageNormaleInformations.hauteurIsolant = Double.parseDouble(hauteurIsolant.getText());
                  	SertissageNormaleInformations.largeurIsolant = Double.parseDouble(largeurIsolant.getText());
                  	SertissageNormaleInformations.traction = traction.getText();
                  	SertissageNormaleInformations.produit = listeProduits.getValue();
                  	SertissageNormaleInformations.machineTraction = listeMachineTraction.getValue();
                  	SertissageNormaleInformations.serieProduit = serieProduit.getText() ; 
        			  try {
        	                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/SertissageNormal/loading/LoadingSertissageNormal.fxml"));
        	                Scene loadingScene = new Scene(loader.load());
        	                String cssPath = "/Front_java/SertissageNormal/loading/LoadingSertissageNormal.css";
        	                if (getClass().getResource(cssPath) != null) {
        	                    loadingScene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
        	                } else {
        	                    System.out.println("❌ Fichier CSS introuvable : " + cssPath);
        	                }

        	                LoadingSertissageNormal loadingController = loader.getController();
        	                loadingController.setParentController(this);

        	                // Définir l'action à exécuter lorsque le bouton "Terminer" est cliqué
        	                loadingController.setOnTerminerAction(() -> {
        	                    // Rendre le champ "quantité atteinte" activé
        	                	hauteurSertissageEchFin.setDisable(false); 
        	                	largeurSertissageEchFin.setDisable(false);
        	                	largeurIsolantEchFin.setDisable(false);
        	                	hauteurIsolantEchFin.setDisable(false) ; 
        	                	tractionEchFin.setDisable(false) ; 
        	                    quantiteCycle.setDisable(false);

        	                    // Si tous les champs sont remplis, passer à la fenêtre de résultats
        	                    if (checkOtherFields()) {
        	                    	try {
        	                    		SertissageNormaleInformations.numCycle = Integer.parseInt( nbrCycle.getText()) ; 
        	                        	SertissageNormaleInformations.hauteurSertissageEch1 = Double.parseDouble(hauteurSertissageEch1.getText()); 
        	                        	SertissageNormaleInformations.hauteurSertissageEch2 = Double.parseDouble(hauteurSertissageEch2.getText()); 
        	                        	SertissageNormaleInformations.hauteurSertissageEch3 = Double.parseDouble(hauteurSertissageEch3.getText()); 
        	                        	SertissageNormaleInformations.hauteurSertissageEchFin =Double.parseDouble( hauteurSertissageEchFin.getText()); 
        	                        	SertissageNormaleInformations.largeurSertissage = Double.parseDouble(largeurSertissage.getText()); 
        	                        	SertissageNormaleInformations.largeurSertissageEchFin = Double.parseDouble(largeurSertissageEchFin.getText()); 
        	                        	SertissageNormaleInformations.hauteurIsolant = Double.parseDouble(hauteurIsolant.getText());
        	                        	SertissageNormaleInformations.hauteurIsolantEchFin = Double.parseDouble(hauteurIsolantEchFin.getText());
        	                        	SertissageNormaleInformations.largeurIsolant = Double.parseDouble(largeurIsolant.getText());
        	                        	SertissageNormaleInformations.largeurIsolantEchFin = Double.parseDouble(largeurIsolantEchFin.getText());
        	                        	SertissageNormaleInformations.traction = traction.getText();
        	                        	SertissageNormaleInformations.tractionFinEch = Integer.parseInt( tractionEchFin.getText());
        	                        	SertissageNormaleInformations.produit = listeProduits.getValue();
        	                        	SertissageNormaleInformations.quantiteAtteint = quantiteCycle.getText();
        	                        	SertissageNormaleInformations.machineTraction = listeMachineTraction.getValue();
        	                        	SertissageNormaleInformations.serieProduit = serieProduit.getText() ; 

        	                        	
        	                    	    // Chargement de la nouvelle fenêtre
        	                    	    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/Front_java/SertissageNormal/loading/LoadingSertissageNormal.fxml"));
        	                    	    Scene resultScene = new Scene(loader2.load());
        	                    	    resultScene.getStylesheets().add(getClass().getResource("/Front_java/SertissageNormal/loading/LoadingSertissageNormal.css").toExternalForm());
        	                    	    
        	                    	    Stage resultStage = new Stage();
        	                    	    resultStage.setScene(resultScene);
        	                    	    resultStage.setMaximized(true);
        	                    	    resultStage.initStyle(StageStyle.UNDECORATED);
        	                    	    
        	                    	    // Ajout d'une icône
        	                    	    Image icon = new Image("/logo_app.jpg");
        	                    	    resultStage.getIcons().add(icon);
        	                    	    
        	                    	    // Affichage de la nouvelle fenêtre
        	                    	    resultStage.show();

        	                    	    // Fermeture de la fenêtre actuelle
        	                    	    Stage currentStage = (Stage) btnSuivant.getScene().getWindow();
        	                            currentStage.close();

        	                    	} catch (IOException ex) {
        	                    	    System.out.println("Erreur lors du chargement de la fenêtre SoudureResultat : " + ex.getMessage());
        	                    	    ex.printStackTrace();
        	                    	}

        	                    }
        	                });

        	                Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        	                Stage loadingStage = new Stage();
        	                loadingStage.setScene(loadingScene);
        	                loadingStage.initStyle(StageStyle.UNDECORATED);
        	                loadingStage.initModality(Modality.APPLICATION_MODAL);
        	                loadingStage.initOwner(parentStage);
        	                loadingStage.show();

        	            } catch (IOException ex) {
        	                System.out.println("❌ Erreur lors du chargement de la fenêtre de chargement : " + ex.getMessage());
        	                ex.printStackTrace();
        	            }
        	        }
}
	
	private boolean verifierHauteurSertissages() {
	    boolean erreur = false;
    	 double valHauteurSertissage = SertissageNormaleInformations.labelHauteurSertissage ; 

	    List<TextField> hauteurSertissageFields = Arrays.asList(
	        		hauteurSertissageEch1, hauteurSertissageEch2, hauteurSertissageEch3    	        		
	        );
	       

	    for (TextField field : hauteurSertissageFields) {
	        if (!field.isDisabled() && !field.getText().isEmpty()) {
	        	 try {
  	                double valeur = Double.parseDouble(field.getText());
  	                if (valeur < (valHauteurSertissage - 0.05) || valeur > ( valHauteurSertissage+ 0.05)) {
  	                    colorBorderRed(field);
  	                    showErrorDialog("La valeur " + valeur + " dans  champs de hauteur de sertissage est hors limites. Elle doit être entre " +(valHauteurSertissage - 0.05)  + " et " + (valHauteurSertissage + 0.05)  + ".", "");
  	                  erreur = true;
  	                }
  	            } catch (NumberFormatException e) {
  	                colorBorderRed(field);
  	                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
  	              erreur = true;
  	            }
	        }
	    }

	    return erreur;
	}

	private boolean verifierLargeurSertissages() {
	    boolean erreur = false;
	    double valLargeurSertissage = SertissageNormaleInformations.labelLargeurSertissage ; 
  	   ToleranceParser.Tolerance tol = ToleranceParser.parseTolerance(
  			    fetchToleranceLargeurSertissageFromAPI(
  			        SertissageNormaleInformations.numeroOutils,
  			        SertissageNormaleInformations.numeroContacts,
  			        SertissageNormaleInformations.sectionFil
  			    )
  			);
  	   
  	   double min = valLargeurSertissage - tol.lower;
  	   double max = valLargeurSertissage + tol.upper;   	 
  	   System.out.println("le min est ="+min) ;
  	    System.out.println("le max est ="+max) ;
	    List<TextField> hauteurSertissageFields = Arrays.asList(
	    		largeurSertissage
	        );
	       

	    for (TextField field : hauteurSertissageFields) {
	        if (!field.isDisabled() && !field.getText().isEmpty()) {
	            try {
	                double valeur = Double.parseDouble(field.getText());
	                if ((valeur <  min) || (valeur >   max)) {
	                    colorBorderRed(field);
	                    showErrorDialog("La valeur " + valeur + " dans  champs de largeur  de sertissage est hors limites. Elle doit être entre " +( min)  + " et " + (max)  + ".", "");
	                    erreur = true;
	                }
	            }  catch (NumberFormatException e) {
  	                colorBorderRed(field);
  	                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
  	              erreur = true;
  	            }
	        }
	    }

	    return erreur;
	}
	private boolean verifierLargeurSertissagesFinales() {
	    boolean erreur = false;
	    double valLargeurSertissage = SertissageNormaleInformations.labelLargeurSertissage ; 
  	   ToleranceParser.Tolerance tol = ToleranceParser.parseTolerance(
  			    fetchToleranceLargeurSertissageFromAPI(
  			        SertissageNormaleInformations.numeroOutils,
  			        SertissageNormaleInformations.numeroContacts,
  			        SertissageNormaleInformations.sectionFil
  			    )
  			);
  	   
  	   double min = valLargeurSertissage - tol.lower;
  	   double max = valLargeurSertissage + tol.upper;   	 
  	   System.out.println("le min est ="+min) ;
  	    System.out.println("le max est ="+max) ;
	    List<TextField> hauteurSertissageFields = Arrays.asList(
	    		largeurSertissageEchFin
	        );
	       

	    for (TextField field : hauteurSertissageFields) {
	        if (!field.isDisabled() && !field.getText().isEmpty()) {
	            try {
	                double valeur = Double.parseDouble(field.getText());
	                if ((valeur <  min) || (valeur >   max)) {
	                    colorBorderRed(field);
	                    showErrorDialog("La valeur " + valeur + " dans  champs de largeur  de sertissage est hors limites. Elle doit être entre " +( min)  + " et " + (max)  + ".", "");
	                    erreur = true;
	                }
	            }  catch (NumberFormatException e) {
  	                colorBorderRed(field);
  	                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
  	              erreur = true;
  	            }
	        }
	    }

	    return erreur;
	}
	
	private boolean verifierHauteurSertissagesFinales() {
	    boolean erreur = false;
    	 double valHauteurSertissage = SertissageNormaleInformations.labelHauteurSertissage ; 

	    List<TextField> hauteurSertissageFields = Arrays.asList(
	        		hauteurSertissageEchFin    	        		
	        );
	       

	    for (TextField field : hauteurSertissageFields) {
	        if (!field.isDisabled() && !field.getText().isEmpty()) {
	        	 try {
  	                double valeur = Double.parseDouble(field.getText());
  	                if (valeur < (valHauteurSertissage - 0.05) || valeur > ( valHauteurSertissage+ 0.05)) {
  	                    colorBorderRed(field);
  	                    showErrorDialog("La valeur " + valeur + " dans  champs de hauteur de sertissage est hors limites. Elle doit être entre " +(valHauteurSertissage - 0.05)  + " et " + (valHauteurSertissage + 0.05)  + ".", "");
  	                  erreur = true;
  	                }
  	            } catch (NumberFormatException e) {
  	                colorBorderRed(field);
  	                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
  	              erreur = true;
  	            }
	        }
	    }

	    return erreur;
	}
	private boolean verifierHauteurIsolant() {
	    boolean erreur = false;
	    double valHauteurIsolant = SertissageNormaleInformations.labelHauteurIsolant ; 
        String toleranceStrHauteurIsolant = fetchToleranceHauteurIsolantFromAPI(
        		SertissageNormaleInformations.numeroOutils,
        		SertissageNormaleInformations.numeroContacts,
        		SertissageNormaleInformations.sectionFil
        		).replace("±", "").trim(); // Suppression du symbole ±
        		double toleranceHauteurIsolant = Double.parseDouble(toleranceStrHauteurIsolant);
        		
	    List<TextField> hauteurSertissageFields = Arrays.asList(
	        		hauteurIsolant	        		
	        );
	       

	    for (TextField field : hauteurSertissageFields) {
	        if (!field.isDisabled() && !field.getText().isEmpty()) {
	        	   try {
    	                double valeur = Double.parseDouble(field.getText());
    	                if (valeur < (valHauteurIsolant - toleranceHauteurIsolant) || valeur > ( valHauteurIsolant+ toleranceHauteurIsolant)) {
    	                    colorBorderRed(field);
    	                    showErrorDialog("La valeur " + valeur + " dans  champs de hauteur de isolant  est hors limites. Elle doit être entre " +(valHauteurIsolant - toleranceHauteurIsolant)  + " et " + (valHauteurIsolant+ toleranceHauteurIsolant)  + ".", "");
    	                    erreur = true;
    	                }
    	            } catch (NumberFormatException e) {
  	                colorBorderRed(field);
  	                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
  	              erreur = true;
  	            }
	        }
	    }

	    return erreur;
	}

	private boolean verifierLargeurIsolant() {
	    boolean erreur = false;
	    double valLargeurIsolant = SertissageNormaleInformations.labelLargeurIsolant ; 
       	
		   ToleranceParser.Tolerance tolIsolant = ToleranceParser.parseTolerance(
				   fetchToleranceLargeurIsolantFromAPI(
 			        SertissageNormaleInformations.numeroOutils,
 			        SertissageNormaleInformations.numeroContacts,
 			        SertissageNormaleInformations.sectionFil
 			    )
 			);
 	   
 	 double minTolIsol = valLargeurIsolant - tolIsolant.lower;
 	 double maxTolIsol = valLargeurIsolant + tolIsolant.upper;   
 	 System.out.println("le minTolIsol est ="+minTolIsol) ;
  	 System.out.println("le maxTolIsol est ="+maxTolIsol) ;
 	 
	    List<TextField> hauteurSertissageFields = Arrays.asList(
	    		largeurIsolant
	        );
	       

	    for (TextField field : hauteurSertissageFields) {
	        if (!field.isDisabled() && !field.getText().isEmpty()) {
	            try {
 	                double valeur = Double.parseDouble(field.getText());
 	                if ((valeur < minTolIsol) ||( valeur >  maxTolIsol)) {
 	                    colorBorderRed(field);
 	                    showErrorDialog("La valeur " + valeur + " dans  champs de hauteur de sertissage est hors limites. Elle doit être entre " +(minTolIsol)  + " et " + (maxTolIsol)  + ".", "");
 	                   erreur = true;
 	                }
 	            }  catch (NumberFormatException e) {
  	                colorBorderRed(field);
  	                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
  	              erreur = true;
  	            }
	        }
	    }

	    return erreur;
	}
	private boolean verifierLargeurIsolantFinales() {
		  boolean erreur = false;
		    double valLargeurIsolant = SertissageNormaleInformations.labelLargeurIsolant ; 
	       	
			   ToleranceParser.Tolerance tolIsolant = ToleranceParser.parseTolerance(
					   fetchToleranceLargeurIsolantFromAPI(
	 			        SertissageNormaleInformations.numeroOutils,
	 			        SertissageNormaleInformations.numeroContacts,
	 			        SertissageNormaleInformations.sectionFil
	 			    )
	 			);
	 	   
	 	 double minTolIsol = valLargeurIsolant - tolIsolant.lower;
	 	 double maxTolIsol = valLargeurIsolant + tolIsolant.upper;   
	 	 System.out.println("le minTolIsol est ="+minTolIsol) ;
	  	 System.out.println("le maxTolIsol est ="+maxTolIsol) ;
	 	 
		    List<TextField> hauteurSertissageFields = Arrays.asList(
		    		largeurIsolantEchFin
		        );
		       

		    for (TextField field : hauteurSertissageFields) {
		        if (!field.isDisabled() && !field.getText().isEmpty()) {
		            try {
	 	                double valeur = Double.parseDouble(field.getText());
	 	                if ((valeur < minTolIsol) ||( valeur >  maxTolIsol)) {
	 	                    colorBorderRed(field);
	 	                    showErrorDialog("La valeur " + valeur + " dans  champs de hauteur de sertissage est hors limites. Elle doit être entre " +(minTolIsol)  + " et " + (maxTolIsol)  + ".", "");
	 	                   erreur = true;
	 	                }
	 	            }  catch (NumberFormatException e) {
	  	                colorBorderRed(field);
	  	                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
	  	              erreur = true;
	  	            }
		        }
		    }

		    return erreur;
	}
	
	private boolean verifierHauteurIsolantFinales() {
		 boolean erreur = false;
		    double valHauteurIsolant = SertissageNormaleInformations.labelHauteurIsolant ; 
	        String toleranceStrHauteurIsolant = fetchToleranceHauteurIsolantFromAPI(
	        		SertissageNormaleInformations.numeroOutils,
	        		SertissageNormaleInformations.numeroContacts,
	        		SertissageNormaleInformations.sectionFil
	        		).replace("±", "").trim(); // Suppression du symbole ±
	        		double toleranceHauteurIsolant = Double.parseDouble(toleranceStrHauteurIsolant);
	        		
		    List<TextField> hauteurSertissageFields = Arrays.asList(
		        		hauteurIsolantEchFin	        		
		        );
		       

		    for (TextField field : hauteurSertissageFields) {
		        if (!field.isDisabled() && !field.getText().isEmpty()) {
		        	   try {
	    	                double valeur = Double.parseDouble(field.getText());
	    	                if (valeur < (valHauteurIsolant - toleranceHauteurIsolant) || valeur > ( valHauteurIsolant+ toleranceHauteurIsolant)) {
	    	                    colorBorderRed(field);
	    	                    showErrorDialog("La valeur " + valeur + " dans  champs de hauteur de isolant  est hors limites. Elle doit être entre " +(valHauteurIsolant - toleranceHauteurIsolant)  + " et " + (valHauteurIsolant+ toleranceHauteurIsolant)  + ".", "");
	    	                    erreur = true;
	    	                }
	    	            } catch (NumberFormatException e) {
	  	                colorBorderRed(field);
	  	                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
	  	              erreur = true;
	  	            }
		        }
		    }

		    return erreur;
	}
	private boolean verifierTraction() {
		  boolean erreur = false;
		  String valtractionString = SertissageNormaleInformations.labelTraction ; 
          int tractionValue = extractValue(valtractionString) ; 
          System.out.print(" valeur de traction numerique "+tractionValue);
	       	
		    List<TextField> hauteurSertissageFields = Arrays.asList(
		    		traction
		        );
		       

		    for (TextField field : hauteurSertissageFields) {
		        if (!field.isDisabled() && !field.getText().isEmpty()) {
		            try {
      	                int valeur = Integer.parseInt(field.getText());
      	                if ( valeur < ( tractionValue)) {
      	                    colorBorderRed(field);
      	                    showErrorDialog("La valeur " + valeur + " dans  champs traction  est hors limites. Elle doit être superieur ou égale a  " +tractionValue+" N"  + ".", "");
      	                    erreur = true;
      	                }
      	            }  catch (NumberFormatException e) {
	  	                colorBorderRed(field);
	  	                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
	  	              erreur = true;
	  	            }
		        }
		    }

		    return erreur;
	}
	private boolean verifierTractionFinale() {
		  boolean erreur = false;
		  String valtractionString = SertissageNormaleInformations.labelTraction ; 
          int tractionValue = extractValue(valtractionString) ; 
          System.out.print(" valeur de traction numerique "+tractionValue);
	       	
		    List<TextField> hauteurSertissageFields = Arrays.asList(
		    		traction
		        );
		       

		    for (TextField field : hauteurSertissageFields) {
		        if (!field.isDisabled() && !field.getText().isEmpty()) {
		            try {
      	                int valeur = Integer.parseInt(field.getText());
      	                if ( valeur < ( tractionValue)) {
      	                    colorBorderRed(field);
      	                    showErrorDialog("La valeur " + valeur + " dans  champs traction  est hors limites. Elle doit être superieur ou égale a  " +tractionValue+" N"  + ".", "");
      	                    erreur = true;
      	                }
      	            }  catch (NumberFormatException e) {
	  	                colorBorderRed(field);
	  	                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
	  	              erreur = true;
	  	            }
		        }
		    }

		    return erreur;
	}
	
	  public static boolean areFieldsEqual(TextField f1, TextField f2, TextField f3, TextField f4) {
	        return f1.getText().equals(f2.getText()) &&
	               f1.getText().equals(f3.getText()) &&
	               f1.getText().equals(f4.getText());
	    }
	  
	  public static boolean areFieldsEqualDeuxChamps(TextField f1, TextField f2) {
	        return f1.getText().equals(f2.getText());
	    }
	  private static void colorBorderRed(TextField... fields) {
	        for (TextField field : fields) {
	            field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
	            field.setOnMouseClicked(event -> field.setStyle("-fx-border-color: red; -fx-border-width: 2px;"));

	        }
	    }
	@FXML
	void precedant(ActionEvent event) {
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

	public void afficherInfosOperateur() throws Exception {
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
			numContact.setText(SertissageNormaleInformations.numeroContacts );
			numOutil.setText(SertissageNormaleInformations.numeroOutils );
			
			String toleranceHauteurIsolant = fetchToleranceHauteurIsolantFromAPI(SertissageNormaleInformations.numeroOutils,
					                                                     SertissageNormaleInformations.numeroContacts,
					                                                     SertissageNormaleInformations.sectionFil ) ; 
			
			String toleranceLargeurIsolant = fetchToleranceLargeurIsolantFromAPI(SertissageNormaleInformations.numeroOutils,
                    SertissageNormaleInformations.numeroContacts,
                    SertissageNormaleInformations.sectionFil ) ; 
			
			String toleranceLargeurSertissageString= fetchToleranceLargeurSertissageFromAPI(SertissageNormaleInformations.numeroOutils,
                    SertissageNormaleInformations.numeroContacts,
                    SertissageNormaleInformations.sectionFil ) ; 
			
			SertissageNormaleInformations.labelHauteurSertissage = Double.parseDouble(fetchHauteurSertissageFromAPI(SertissageNormaleInformations.numeroOutils ,
																			                    SertissageNormaleInformations.numeroContacts,
																			                    SertissageNormaleInformations.sectionFil ));
			
			SertissageNormaleInformations.labelLargeurSertissage = Double.parseDouble(fetchLargeurSertissageFromAPI(SertissageNormaleInformations.numeroOutils,
                    SertissageNormaleInformations.numeroContacts,
                    SertissageNormaleInformations.sectionFil ) ) ; 

			
			SertissageNormaleInformations.labelHauteurIsolant = Double.parseDouble(fetchHauteurIsolantFromAPI(SertissageNormaleInformations.numeroOutils,
																                    SertissageNormaleInformations.numeroContacts,
																                    SertissageNormaleInformations.sectionFil ));

			
			SertissageNormaleInformations.labelLargeurIsolant = Double.parseDouble(fetchLargeurIsolantFromAPI(SertissageNormaleInformations.numeroOutils,
                    SertissageNormaleInformations.numeroContacts,
                    SertissageNormaleInformations.sectionFil ));

			
			labelHauteurSertissage.setText(SertissageNormaleInformations.labelHauteurSertissage +"/±0.05 mm");
			
		
			labelLargeurSertissage.setText(SertissageNormaleInformations.labelLargeurSertissage +"/"+toleranceLargeurSertissageString);
			SertissageNormaleInformations.labelLargeurSertissageComplet=SertissageNormaleInformations.labelLargeurSertissage +"/"+toleranceLargeurSertissageString ; 
			
			labelHauteurIsolant.setText(SertissageNormaleInformations.labelHauteurIsolant +"/"+toleranceHauteurIsolant+" mm");
			SertissageNormaleInformations.labelHauteurIsolantComplet = SertissageNormaleInformations.labelHauteurIsolant +"/"+toleranceHauteurIsolant+" mm" ; 
			
			
			labelLargeurIsolant.setText(SertissageNormaleInformations.labelLargeurIsolant +"/"+toleranceLargeurIsolant+" mm");
			SertissageNormaleInformations.labelLargeurIsolantComplet = SertissageNormaleInformations.labelLargeurIsolant +"/"+toleranceLargeurIsolant+" mm" ; 
			
			String traction =fetchTractionFromAPI(SertissageNormaleInformations.numeroOutils,
                    SertissageNormaleInformations.numeroContacts,
                    SertissageNormaleInformations.sectionFil ) ; 
					traction = traction.replace("&gt;", ">").replace("&lt;", "<"); // Décodage manuel
					labelTraction.setText(traction);
					
					SertissageNormaleInformations.labelTraction = traction ; 
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




	private void loadDernierNumeroCycle() {
	    String dernierNumeroStr = fetchNumMaxCycle();

	    // Vérifier si la réponse est un nombre valide
	    try {
	        int dernierNumeroCycle = Integer.parseInt(dernierNumeroStr);

	        SertissageNormaleInformations.numCycle = dernierNumeroCycle ; 
	        if (dernierNumeroCycle == 8) {
	            nbrCycle.setText("1");
	        } else if (dernierNumeroCycle < 8) {
	            nbrCycle.setText(String.valueOf(dernierNumeroCycle + 1));
	        } else {
	            nbrCycle.setText("Erreur");
	            System.out.println("Erreur lors de la récupération du dernier numéro de cycle.");
	        }
	    } catch (NumberFormatException e) {
	        nbrCycle.setText("Erreur");
	        System.out.println("Impossible de convertir la réponse en nombre : " + dernierNumeroStr);
	    }
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
	/********************************************* Ajout PDEK  ***************************************************************/
	private void ajouterPdekAvecSoudure() {
	
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
	   if (SertissageNormaleInformations.testTerminitionCommande == 1) {
		    hauteurSertissageEchFin.setDisable(false); 
		    hauteurSertissageEchFin.getStyleClass().add("textfield-blue-border");
		    
	       	largeurSertissageEchFin.setDisable(false);
	       	largeurSertissageEchFin.getStyleClass().add("textfield-blue-border");
	       	
	     	hauteurIsolantEchFin.setDisable(false);
	     	hauteurIsolantEchFin.getStyleClass().add("textfield-blue-border");
	       	
	       	largeurIsolantEchFin.setDisable(false);
         	largeurIsolantEchFin.getStyleClass().add("textfield-blue-border");
         	
        	tractionEchFin.setDisable(false);
        	tractionEchFin.getStyleClass().add("textfield-blue-border");
         	
            quantiteCycle.setDisable(false);
            quantiteCycle.getStyleClass().add("textfield-blue-border");
	       
	    }
	}

    public void afficherNotification(String message) {

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
        });

        dialog.show();

        // Fermeture automatique après 5 secondes
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> {
            if (dialog.isVisible()) {
                dialog.close();
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
/*********************  Recupere des detailes des elements ****
    private String getElementFromSection(String sectionFil, String element) throws Exception {
        String token = AppInformations.token;

        // Encodage correct des paramètres pour éviter tout problème
        String encodedSectionFil = URLEncoder.encode(sectionFil, StandardCharsets.UTF_8);
        // Remplacer les '+' par '%20' pour éviter que '+' soit interprété comme un espace
        encodedSectionFil = encodedSectionFil.replace("+", "%20");

        String encodedElement = URLEncoder.encode(element, StandardCharsets.UTF_8);

        // URL de l'API avec la section et l'élément spécifiques
        String url = "http://localhost:8281/operations/SertissageNormal/sections/" + encodedSectionFil + "/" + encodedElement;


        // Construction de la requête HTTP avec le token d'authentification
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + token)
                .build();

        // Création du client HTTP
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Vérification du code de statut HTTP
        if (response.statusCode() == 200) {
            String responseBody = response.body().trim();

            // Retourner la valeur de l'élément demandé
            return responseBody;
        } else {
            throw new Exception("Erreur lors de la récupération de l'élément : "
                + response.statusCode() + " - " + response.body());
        }
    }

*/
    /********************* details des hauteurs et largeurs sertissage et islolan ************************/
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public  String fetchHauteurSertissageFromAPI(String numeroOutil, String numeroContact, String sectionFil) {

        try {
            // Construire l'URL avec les paramètres
            String url = "http://localhost:8281/operations/SertissageNormal/hauteurSertissage"
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

    public  String fetchLargeurSertissageFromAPI(String numeroOutil, String numeroContact, String sectionFil) {

        try {
            // Construire l'URL avec les paramètres
            String url = "http://localhost:8281/operations/SertissageNormal/largeurSertissage"
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
    public  String fetchHauteurIsolantFromAPI(String numeroOutil, String numeroContact, String sectionFil) {

        try {
            // Construire l'URL avec les paramètres
            String url = "http://localhost:8281/operations/SertissageNormal/hauteurIsolant"
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

    public  String fetchLargeurIsolantFromAPI(String numeroOutil, String numeroContact, String sectionFil) {

        try {
            // Construire l'URL avec les paramètres
            String url = "http://localhost:8281/operations/SertissageNormal/largeurIsolant"
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
public  String fetchTractionFromAPI(String numeroOutil, String numeroContact, String sectionFil) {
        try {
            // Construire l'URL avec les paramètres
            String url = "http://localhost:8281/operations/SertissageNormal/traction"
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
public  String fetchToleranceLargeurSertissageFromAPI(String numeroOutil, String numeroContact, String sectionFil) {
    try {
        // Construire l'URL avec les paramètres
        String url = "http://localhost:8281/operations/SertissageNormal/ToleranceLargeurSertissage"
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
public  String fetchToleranceLargeurIsolantFromAPI(String numeroOutil, String numeroContact, String sectionFil) {
    try {
        // Construire l'URL avec les paramètres
        String url = "http://localhost:8281/operations/SertissageNormal/ToleranceLargeurIsolant"
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
public  String fetchToleranceHauteurIsolantFromAPI(String numeroOutil, String numeroContact, String sectionFil) {
    try {
        // Construire l'URL avec les paramètres
        String url = "http://localhost:8281/operations/SertissageNormal/ToleranceHauteurIsolant"
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
/*********************  extraire mm depuis section fil *****/


public double extraireValeurNumeriqueSectionFil(String sectionFil) {
    return Double.parseDouble(sectionFil.trim().split(" ")[0] );
}
public void centerTextFields(TextField... fields) {
    for (TextField field : fields) {
        field.setStyle("-fx-alignment: center;"); // Centre le texte dans le champ
    }
}
public static int extractValue(String input) {
    Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?"); // Capture les nombres entiers et décimaux
    Matcher matcher = pattern.matcher(input);
    
    if (matcher.find()) {
        return Integer.parseInt(matcher.group()); // Convertir en double
    }
    throw new IllegalArgumentException("Aucun nombre trouvé dans la chaîne !");
}
/***************************** Récuperation dernier numero de cycle de pdek courant **************************/


public String fetchNumMaxCycle() {
    try {
        // Encoder les paramètres pour éviter les erreurs d'URL
        String sectionFilEncoded = URLEncoder.encode(SertissageNormaleInformations.sectionFil, StandardCharsets.UTF_8);
        String projetEncoded = URLEncoder.encode(SertissageNormaleInformations.projetSelectionner, StandardCharsets.UTF_8);
        String nomPlantEncoded = URLEncoder.encode(AppInformations.operateurInfo.getPlant(), StandardCharsets.UTF_8);

        // Construire l'URL avec les paramètres encodés
        String urlString = "http://localhost:8281/operations/SertissageNormal/dernier-numero-cycle?" +
                "sectionFil=" + sectionFilEncoded +
                "&segment=" + AppInformations.operateurInfo.getSegment() +
                "&nomPlant=" + nomPlantEncoded +
                "&projetName=" + projetEncoded;

        System.out.println("URL encodée : " + urlString);

        // Construire la requête HTTP avec le token
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlString))
                .header("Authorization", "Bearer " + AppInformations.token)
                .GET()
                .build();

        // Envoyer la requête
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Statut HTTP reçu : " + response.statusCode());
        System.out.println("Réponse brute : " + response.body());

        // Vérifier si la réponse est réussie (code 200 OK)
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            return "Erreur API: " + response.statusCode();
        }

    } catch (Exception e) {
        e.printStackTrace();
        return "Erreur de connexion à l'API";
    }
}

}