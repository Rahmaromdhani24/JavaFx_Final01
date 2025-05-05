package Front_java.SertissageIDC;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import Front_java.Configuration.ActiveTextFieldManager;
import Front_java.Configuration.AppInformations;
import Front_java.Configuration.SertissageIDCInformations;
import Front_java.Modeles.OperateurInfo;
import Front_java.SertissageIDC.loading.LoadingSertissageIDC;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.util.Duration;




public class RemplirSertissageIDC {

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
	    private TextField forceTractionEch1C1;

	    @FXML
	    private TextField forceTractionEch1C2;

	    @FXML
	    private TextField forceTractionEch2C1;

	    @FXML
	    private TextField forceTractionEch2C2;

	    @FXML
	    private TextField forceTractionEch3C1;

	    @FXML
	    private TextField forceTractionEch3C2;

	    @FXML
	    private TextField forceTractionEchFinC1;

	    @FXML
	    private TextField forceTractionEchFinC2;

	    @FXML
	    private TextField hauteurSertissageEch1C1;

	    @FXML
	    private TextField hauteurSertissageEch1C2;

	    @FXML
	    private TextField hauteurSertissageEch2C1;

	    @FXML
	    private TextField hauteurSertissageEch2C2;

	    @FXML
	    private TextField hauteurSertissageEch3C1;

	    @FXML
	    private TextField hauteurSertissageEch3C2;

	    @FXML
	    private TextField hauteurSertissageEchFinC1;

	    @FXML
	    private TextField hauteurSertissageEchFinC2;

	    @FXML
	    private Label heureSystem;

	    @FXML
	    private Label matriculeUser;

	    @FXML
	    private Label nbrCycle;

	    @FXML
	    private Label nbrEch;

	    @FXML
	    private Label nomPrenomUser;

	    @FXML
	    private Label nomProjet;

	    @FXML
	    private TextField numMachine;

	    @FXML
	    private Label operationUser;

	    @FXML
	    private Label plantUser;

	    @FXML
	    private Label posteUser;

	    @FXML
	    private TextField produit;

	    @FXML
	    private BorderPane rootPane;

	    @FXML
	    private Label segementUser;

	    @FXML
	    private TextField serieProduit;

	
	    @FXML
	    private StackPane stackPane;

	    @FXML
	    private Label forceTraction ; 
	    
	    @FXML
	    private Label sectionFil ; 
	    
	    @FXML
	    private TextField quantiteCycle ; 
	    
		public TextField activeTextField;
		
		// Définition des bornes
		final double MIN_HAUTEUR = 10.85;
		final double MAX_HAUTEUR = 11;
		final int MIN_TRACTION = 50;
		final int MAX_TRACTION = 110;
		
		public TextField getActiveTextField() {
			return activeTextField;
		}

		public void setActiveOnFocus(TextField textField) {
		    textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
		        if (newVal) {
		            ActiveTextFieldManager.getInstance().setActiveTextField(textField);
		        }
		    });
		}
		
		@FXML
		public void handleButtonClick(ActionEvent event) {
		    TextField activeTextField = ActiveTextFieldManager.getInstance().getActiveTextField();
		    if (activeTextField != null) {
		        Button clickedButton = (Button) event.getSource();
		        String buttonText = clickedButton.getText();
		        activeTextField.appendText(buttonText); // Ajoute le texte du bouton au champ actif
		    }
		}

	@FXML
	public void initialize() {
		  hauteurSertissageEchFinC1.setDisable(true) ; 
		  forceTractionEchFinC1.setDisable(true);
		  hauteurSertissageEchFinC2.setDisable(true);
		  forceTractionEchFinC2.setDisable(true);
		  quantiteCycle.setDisable(true);
		  
		afficherInfosOperateur();
		SertissageIDCInformations.testTerminitionCommande = 0 ; 
		
		afficherDateSystem();
		afficherHeureSystem();
		loadNumeroCycleMax();
		clearImage.setOnMouseClicked(event -> {
		    TextField activeTextField = ActiveTextFieldManager.getInstance().getActiveTextField();
		    if (activeTextField != null) {
		        activeTextField.clear();
		    }
		});
		setActiveOnFocus(hauteurSertissageEch1C1);
		setActiveOnFocus(hauteurSertissageEch2C1);
		setActiveOnFocus(hauteurSertissageEch3C1);
		setActiveOnFocus(forceTractionEch1C1);
		setActiveOnFocus(forceTractionEch2C1);
		setActiveOnFocus(forceTractionEch3C1);
		
		setActiveOnFocus(hauteurSertissageEch1C2);
		setActiveOnFocus(hauteurSertissageEch2C2);
		setActiveOnFocus(hauteurSertissageEch3C2);
		setActiveOnFocus(forceTractionEch1C2);
		setActiveOnFocus(forceTractionEch2C2);
		setActiveOnFocus(forceTractionEch3C2);
		
		setActiveOnFocus(forceTractionEchFinC1);
		setActiveOnFocus(forceTractionEchFinC2);
		setActiveOnFocus(hauteurSertissageEchFinC1);
		setActiveOnFocus(hauteurSertissageEchFinC2);
		
		setActiveOnFocus(produit);
		setActiveOnFocus(serieProduit);
		setActiveOnFocus(numMachine);
		setActiveOnFocus(quantiteCycle);

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
	    return !hauteurSertissageEch1C1.getText().isEmpty() &&
	           !hauteurSertissageEch2C1.getText().isEmpty() &&
	           !hauteurSertissageEch3C1.getText().isEmpty() &&
	           !forceTractionEch1C1.getText().isEmpty() &&
	           !forceTractionEch2C1.getText().isEmpty() &&
	           !forceTractionEch3C1.getText().isEmpty() &&
	           !hauteurSertissageEch1C2.getText().isEmpty() &&
	           !hauteurSertissageEch2C2.getText().isEmpty() &&	          	         
	           !hauteurSertissageEch3C2.getText().isEmpty() &&
	           !forceTractionEch1C2.getText().isEmpty() &&
	           !forceTractionEch2C2.getText().isEmpty() &&
	           !forceTractionEch3C2.getText().isEmpty() &&
	           !produit.getText().isEmpty() &&
	           !serieProduit.getText().isEmpty() &&
	           !numMachine.getText().isEmpty();	    
	}

	  public static boolean areFieldsEqual(TextField f1, TextField f2, TextField f3, TextField f4) {
	        return f1.getText().equals(f2.getText()) &&
	               f1.getText().equals(f3.getText()) &&
	               f1.getText().equals(f4.getText());
	    }
	@FXML
	public void suivant(ActionEvent event) {
		  centerTextFields(
			        hauteurSertissageEch1C1, hauteurSertissageEch2C1, hauteurSertissageEch3C1, 
			        forceTractionEch1C1, forceTractionEch2C1, forceTractionEch3C1, 
			        hauteurSertissageEch1C2, hauteurSertissageEch2C2, hauteurSertissageEch3C2, 
			        forceTractionEch1C2, forceTractionEch2C2, forceTractionEch3C2, 
			        produit, serieProduit, numMachine , quantiteCycle
			    );
		 
		 // 1. Vérification des champs obligatoires
	    if (hauteurSertissageEch1C1.getText().isEmpty() || hauteurSertissageEch2C1.getText().isEmpty() || hauteurSertissageEch3C1.getText().isEmpty()
	            || forceTractionEch1C1.getText().isEmpty() || forceTractionEch2C1.getText().isEmpty() || forceTractionEch3C1.getText().isEmpty()
	            || hauteurSertissageEch1C2.getText().isEmpty() || hauteurSertissageEch2C2.getText().isEmpty()        
	            || hauteurSertissageEch3C2.getText().isEmpty() || forceTractionEch1C2.getText().isEmpty() || forceTractionEch2C2.getText().isEmpty()
	            || forceTractionEch3C2.getText().isEmpty()|| produit.getText().isEmpty()
	            || serieProduit.getText().isEmpty()
	            || numMachine.getText().isEmpty() ) {

	        showErrorDialog("Veuillez remplir tous les champs avant de continuer !", "Champs obligatoires");
	        return; // Arrêt si un champ est vide
	    }
	    if ((!hauteurSertissageEchFinC1.isDisabled() && hauteurSertissageEchFinC1.getText().isEmpty()) ||
	    	    (!forceTractionEchFinC1.isDisabled() && forceTractionEchFinC1.getText().isEmpty()) ||
	    	    (!hauteurSertissageEchFinC2.isDisabled() && hauteurSertissageEchFinC2.getText().isEmpty()) ||
	    	    (!forceTractionEchFinC2.isDisabled() && forceTractionEchFinC2.getText().isEmpty()) ||
	    	    (!quantiteCycle.isDisabled() && quantiteCycle.getText().isEmpty())) {
        	
	    	    showErrorDialog("Veuillez remplir tous les champs avant de continuer !", "Champs obligatoires");
	    	    return; // Arrête l'exécution de la méthode après l'alerte
	    	}
	   
	    // 3. Si tous les champs sont remplis, afficher l'alerte de confirmation
     	    boolean hasError = false;

	    // Vérifier si des champs obligatoires sont vides
	    if (checkOtherFields() && !hauteurSertissageEchFinC1.getText().isEmpty()&& !forceTractionEchFinC1.getText().isEmpty() 
	        		               && !hauteurSertissageEchFinC2.getText().isEmpty() && !forceTractionEchFinC2.getText().isEmpty()
	        		               && !quantiteCycle.getText().isEmpty()) {
	    	
	    	// Vérification des champs vides
	    	if (hauteurSertissageEchFinC1.getText().isEmpty() || forceTractionEchFinC1.getText().isEmpty() 
	     	    || hauteurSertissageEchFinC2.getText().isEmpty() || forceTractionEchFinC2.getText().isEmpty()|| quantiteCycle.getText().isEmpty()) {

	     	    showErrorDialog("Veuillez remplir tous les champs avant de continuer !", "Champs obligatoires");
	     	    return; // Arrêt immédiat si un champ est vide
	     	}

	        // Vérification des valeurs identiques et coloration des champs
	        if (areFieldsEqual(hauteurSertissageEch1C1, hauteurSertissageEch2C1, hauteurSertissageEch3C1, hauteurSertissageEchFinC1)) {
	            colorBorderRed(hauteurSertissageEch1C1, hauteurSertissageEch2C1, hauteurSertissageEch3C1, hauteurSertissageEchFinC1);
	            showErrorDialog("Les valeurs des échantillons de hauteur de sertissage C1 doivent être différentes.", "");
	            hasError = true;
	        }
	        if (areFieldsEqual(forceTractionEch1C1, forceTractionEch2C1, forceTractionEch3C1, forceTractionEchFinC1)) {
	            colorBorderRed(forceTractionEch1C1, forceTractionEch2C1, forceTractionEch3C1, forceTractionEchFinC1);
	            showErrorDialog("Les valeurs des échantillons de force de traction C1 doivent être différentes.", "");
	            hasError = true;
	        }
	        if (areFieldsEqual(hauteurSertissageEch1C2, hauteurSertissageEch2C2, hauteurSertissageEch3C2, hauteurSertissageEchFinC2)) {
	            colorBorderRed(hauteurSertissageEch1C2, hauteurSertissageEch2C2, hauteurSertissageEch3C2, hauteurSertissageEchFinC2);
	            showErrorDialog("Les valeurs des échantillons de hauteur de sertissage C2 doivent être différentes.", "");
	            hasError = true;
	        }
	        if (areFieldsEqual(forceTractionEch1C2, forceTractionEch2C2, forceTractionEch3C2, forceTractionEchFinC2)) {
	            colorBorderRed(forceTractionEch1C2, forceTractionEch2C2, forceTractionEch3C2, forceTractionEchFinC2);
	            showErrorDialog("Les valeurs des échantillons de force de traction C2 doivent être différentes.", "");
	            hasError = true;
	        }
	        if (verifierValeursSertissagesFinale()) {
        	    return; // stop si erreur détectée
        	}
        	if (verifierValeursTractionFinal()) {
        	    return; // stop si erreur détectée
        	}
        	if (hasError) {
        	    return;
        	}
	        // Aucune erreur => afficher la confirmation
	        String message = "Veuillez confirmer les données saisies ? \n\n";

	        showConfirmationDialog(message, "Confirmation", () -> {
	        	
	        	 // Exemple d'utilisation pour les champs que tu as mentionnés
	            	double hauteurSertissageEch1C1Value = parseDoubleWithCleanup(hauteurSertissageEch1C1.getText());
	                double hauteurSertissageEch2C1Value = parseDoubleWithCleanup(hauteurSertissageEch2C1.getText());
	                double hauteurSertissageEch3C1Value = parseDoubleWithCleanup(hauteurSertissageEch3C1.getText());


	                double hauteurSertissageEch1C2Value = parseDoubleWithCleanup(hauteurSertissageEch1C2.getText());
	                double hauteurSertissageEch2C2Value = parseDoubleWithCleanup(hauteurSertissageEch2C2.getText());
	                double hauteurSertissageEch3C2Value = parseDoubleWithCleanup(hauteurSertissageEch3C2.getText());

	        
	            	SertissageIDCInformations.hauteurSertissageC1Ech1 = hauteurSertissageEch1C1Value;
	           	    SertissageIDCInformations.hauteurSertissageC1Ech2 = hauteurSertissageEch2C1Value;
	           	    SertissageIDCInformations.hauteurSertissageC1Ech3 = hauteurSertissageEch3C1Value ;
	            	SertissageIDCInformations.hauteurSertissageC1EchFin =Double.parseDouble(hauteurSertissageEchFinC1.getText()) ;
	            	SertissageIDCInformations.hauteurSertissageC2EchFin =Double.parseDouble(hauteurSertissageEchFinC2.getText()) ;
	            	SertissageIDCInformations.forceTractionEchFinC1 =Integer.parseInt(forceTractionEchFinC1.getText()) ;
	            	SertissageIDCInformations.forceTractionEchFinC2 =Integer.parseInt(forceTractionEchFinC2.getText()) ;

	            	
	           	    SertissageIDCInformations.forceTractionEch1C1 = Integer.parseInt(forceTractionEch1C1.getText()); 
	     	        SertissageIDCInformations.forceTractionEch2C1 = Integer.parseInt(forceTractionEch2C1.getText()); 
	     	        SertissageIDCInformations.forceTractionEch3C1 = Integer.parseInt(forceTractionEch3C1.getText()); 
	     	    
	     	        SertissageIDCInformations.hauteurSertissageC2Ech1 = hauteurSertissageEch1C2Value ;
	       	        SertissageIDCInformations.hauteurSertissageC2Ech2 = hauteurSertissageEch2C2Value ;
	       	        SertissageIDCInformations.hauteurSertissageC2Ech3 = hauteurSertissageEch3C2Value ;
	       	   
	       	        SertissageIDCInformations.forceTractionEch1C2 = Integer.parseInt( forceTractionEch1C2.getText()); 
	     	        SertissageIDCInformations.forceTractionEch2C2 = Integer.parseInt(forceTractionEch1C2.getText()); 
	     	        SertissageIDCInformations.forceTractionEch3C2 = Integer.parseInt(forceTractionEch3C2.getText());
	     	    
	       	        SertissageIDCInformations.produit = produit.getText(); 
	     	        SertissageIDCInformations.serieProduit = serieProduit.getText(); 
	     	        SertissageIDCInformations.numeroMachine =  Integer.parseInt(numMachine.getText()); 
	     	        SertissageIDCInformations.quantiteCycle= Integer.parseInt(quantiteCycle.getText()) ; 
            	    
                // Affichage direct de la fenêtre SoudureResultat
                try {
                	  // Chargement de la nouvelle fenêtre
            	    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/Front_java/SertissageIDC/Resultat.fxml"));
            	    Scene resultScene = new Scene(loader2.load());
            	    resultScene.getStylesheets().add(getClass().getResource("/Front_java/SertissageIDC/Resultat.css").toExternalForm());                   	    
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
        	if (verifierValeursSertissages()) {
        	    return; // stop si erreur détectée
        	}
        	if (verifierValeursTraction()) {
        	    return; // stop si erreur détectée
        	}
        	  centerTextFields(
        		        hauteurSertissageEch1C1, hauteurSertissageEch2C1, hauteurSertissageEch3C1, 
        		        forceTractionEch1C1, forceTractionEch2C1, forceTractionEch3C1,hauteurSertissageEchFinC1 ,
        		        hauteurSertissageEch1C2, hauteurSertissageEch2C2, hauteurSertissageEch3C2, 
        		        forceTractionEch1C2, forceTractionEch2C2, forceTractionEch3C2, hauteurSertissageEchFinC2 , 
        		        produit, serieProduit, numMachine , forceTractionEchFinC1 , forceTractionEchFinC2 
        		    );
            try {
            	 // Exemple d'utilisation pour les champs que tu as mentionnés
                double hauteurSertissageEch1C1Value = parseDoubleWithCleanup(hauteurSertissageEch1C1.getText());
                double hauteurSertissageEch2C1Value = parseDoubleWithCleanup(hauteurSertissageEch2C1.getText());
                double hauteurSertissageEch3C1Value = parseDoubleWithCleanup(hauteurSertissageEch3C1.getText());

                double hauteurSertissageEch1C2Value = parseDoubleWithCleanup(hauteurSertissageEch1C2.getText());
                double hauteurSertissageEch2C2Value = parseDoubleWithCleanup(hauteurSertissageEch2C2.getText());
                double hauteurSertissageEch3C2Value = parseDoubleWithCleanup(hauteurSertissageEch3C2.getText());

        
            	SertissageIDCInformations.hauteurSertissageC1Ech1 = hauteurSertissageEch1C1Value;
           	    SertissageIDCInformations.hauteurSertissageC1Ech2 = hauteurSertissageEch2C1Value;
           	    SertissageIDCInformations.hauteurSertissageC1Ech3 = hauteurSertissageEch3C1Value ;
           	    
           	    SertissageIDCInformations.forceTractionEch1C1 = Integer.parseInt(forceTractionEch1C1.getText()); 
     	        SertissageIDCInformations.forceTractionEch2C1 = Integer.parseInt(forceTractionEch2C1.getText()); 
     	        SertissageIDCInformations.forceTractionEch3C1 = Integer.parseInt(forceTractionEch3C1.getText()); 
     	    
     	        SertissageIDCInformations.hauteurSertissageC2Ech1 = hauteurSertissageEch1C2Value ;
       	        SertissageIDCInformations.hauteurSertissageC2Ech2 = hauteurSertissageEch2C2Value ;
       	        SertissageIDCInformations.hauteurSertissageC2Ech3 = hauteurSertissageEch3C2Value ;
       	   
       	        SertissageIDCInformations.forceTractionEch1C2 = Integer.parseInt( forceTractionEch1C2.getText()); 
     	        SertissageIDCInformations.forceTractionEch2C2 = Integer.parseInt(forceTractionEch1C2.getText()); 
     	        SertissageIDCInformations.forceTractionEch3C2 = Integer.parseInt(forceTractionEch3C2.getText());
     	    
       	        SertissageIDCInformations.produit = produit.getText(); 
     	        SertissageIDCInformations.serieProduit = serieProduit.getText(); 
     	        SertissageIDCInformations.numeroMachine =  Integer.parseInt(numMachine.getText()); 
       	    
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/SertissageIDC/loading/LoadingSertissageIDC.fxml"));
                Scene loadingScene = new Scene(loader.load());
                String cssPath = "/Front_java/SertissageIDC/loading/LoadingSertissageIDC.css";
                if (getClass().getResource(cssPath) != null) {
                    loadingScene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
                } else {
                    System.out.println("❌ Fichier CSS introuvable : " + cssPath);
                }

                LoadingSertissageIDC loadingController = loader.getController();
                loadingController.setParentController(this);

                // Définir l'action à exécuter lorsque le bouton "Terminer" est cliqué
                loadingController.setOnTerminerAction(() -> {
                    // Rendre le champ "quantité atteinte" activé
                	hauteurSertissageEchFinC1.setDisable(false); 
                	forceTractionEchFinC1.setDisable(false);
                	hauteurSertissageEchFinC2.setDisable(false);
                	forceTractionEchFinC2.setDisable(false);      
                	quantiteCycle.setDisable(false);      
                	
                    // Si tous les champs sont remplis, passer à la fenêtre de résultats
                    if (checkOtherFields()) {
                    	
                    	try {
                    		 // Exemple d'utilisation pour les champs que tu as mentionnés
                            double hauteurSertissageEch1C1Value1 = parseDoubleWithCleanup(hauteurSertissageEch1C1.getText());
                            double hauteurSertissageEch2C1Value1 = parseDoubleWithCleanup(hauteurSertissageEch2C1.getText());
                            double hauteurSertissageEch3C1Value1 = parseDoubleWithCleanup(hauteurSertissageEch3C1.getText());

                        
                            double hauteurSertissageEch1C2Value1 = parseDoubleWithCleanup(hauteurSertissageEch1C2.getText());
                            double hauteurSertissageEch2C2Value1 = parseDoubleWithCleanup(hauteurSertissageEch2C2.getText());
                            double hauteurSertissageEch3C2Value1 = parseDoubleWithCleanup(hauteurSertissageEch3C2.getText());

                            
                        	SertissageIDCInformations.hauteurSertissageC1Ech1 = hauteurSertissageEch1C1Value1;
                       	    SertissageIDCInformations.hauteurSertissageC1Ech2 = hauteurSertissageEch2C1Value1;
                       	    SertissageIDCInformations.hauteurSertissageC1Ech3 = hauteurSertissageEch3C1Value1 ;
                       	    
                       	    SertissageIDCInformations.forceTractionEch1C1 = Integer.parseInt(forceTractionEch1C1.getText()); 
                 	        SertissageIDCInformations.forceTractionEch2C1 = Integer.parseInt(forceTractionEch2C1.getText()); 
                 	        SertissageIDCInformations.forceTractionEch3C1 = Integer.parseInt(forceTractionEch3C1.getText()); 
                 	    
                 	        SertissageIDCInformations.hauteurSertissageC2Ech1 = hauteurSertissageEch1C2Value1 ;
                   	        SertissageIDCInformations.hauteurSertissageC2Ech2 = hauteurSertissageEch2C2Value1;
                   	        SertissageIDCInformations.hauteurSertissageC2Ech3 = hauteurSertissageEch3C2Value1 ;
                   	   
                   	        SertissageIDCInformations.forceTractionEch1C2 = Integer.parseInt( forceTractionEch1C2.getText()); 
                 	        SertissageIDCInformations.forceTractionEch2C2 = Integer.parseInt(forceTractionEch1C2.getText()); 
                 	        SertissageIDCInformations.forceTractionEch3C2 = Integer.parseInt(forceTractionEch3C2.getText());
                 	    
                 	       SertissageIDCInformations.hauteurSertissageC1EchFin =Double.parseDouble(hauteurSertissageEchFinC1.getText()) ;
       	            	   SertissageIDCInformations.hauteurSertissageC2EchFin =Double.parseDouble(hauteurSertissageEchFinC2.getText()) ;
       	            	   SertissageIDCInformations.forceTractionEchFinC1 =Integer.parseInt(forceTractionEchFinC1.getText()) ;
       	            	   SertissageIDCInformations.forceTractionEchFinC2 =Integer.parseInt(forceTractionEchFinC2.getText()) ;

                    	    SertissageIDCInformations.produit = produit.getText(); 
                    	    SertissageIDCInformations.serieProduit = serieProduit.getText(); 
                    	    SertissageIDCInformations.numeroMachine =  Integer.parseInt(numMachine.getText()); 
                    	    SertissageIDCInformations.quantiteCycle= Integer.parseInt(quantiteCycle.getText()) ; 


                    	    // Chargement de la nouvelle fenêtre
                    	    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/Front_java/SertissageIDC/Resultat.fxml"));
                    	    Scene resultScene = new Scene(loader2.load());
                    	    resultScene.getStylesheets().add(getClass().getResource("/Front_java/SertissageIDC/Resultat.css").toExternalForm());
                    	    
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
                    	    System.out.println("Erreur lors du chargement de la fenêtre Sertissage IDC : " + ex.getMessage());
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
	
	private boolean verifierValeursTraction() {
	    boolean erreur = false;

	    List<TextField> tractionFields = Arrays.asList(
	        forceTractionEch1C1, forceTractionEch2C1, forceTractionEch3C1,
	        forceTractionEch1C2, forceTractionEch2C2, forceTractionEch3C2
	    );

	    for (TextField field : tractionFields) {
	        if (!field.isDisabled() && !field.getText().isEmpty()) {
	            try {
	                int valeur = Integer.parseInt(field.getText());
	                if (valeur < MIN_TRACTION || valeur > MAX_TRACTION) {
	                    colorBorderRed(field);
	                    showErrorDialog("La valeur " + valeur + " dans le champ de traction est hors limites. Elle doit être entre " + MIN_TRACTION + " et " + MAX_TRACTION + ".", "");
	                    erreur = true;
	                }
	            } catch (NumberFormatException e) {
	                colorBorderRed(field);
	                showErrorDialog("Veuillez entrer une valeur numérique valide pour le champ de traction " + field.getId() + ".", "");
	                erreur = true;
	            }
	        }
	    }

	    return erreur;
	}
	private boolean verifierValeursTractionFinal() {
	    boolean erreur = false;

	    List<TextField> tractionFields = Arrays.asList(
	        forceTractionEchFinC1 , forceTractionEchFinC2
	    );

	    for (TextField field : tractionFields) {
	        if (!field.isDisabled() && !field.getText().isEmpty()) {
	            try {
	                int valeur = Integer.parseInt(field.getText());
	                if (valeur < MIN_TRACTION || valeur > MAX_TRACTION) {
	                    colorBorderRed(field);
	                    showErrorDialog("La valeur " + valeur + " dans le champ de traction est hors limites. Elle doit être entre " + MIN_TRACTION + " et " + MAX_TRACTION + ".", "");
	                    erreur = true;
	                }
	            } catch (NumberFormatException e) {
	                colorBorderRed(field);
	                showErrorDialog("Veuillez entrer une valeur numérique valide pour le champ de traction " + field.getId() + ".", "");
	                erreur = true;
	            }
	        }
	    }

	    return erreur;
	}

	private boolean verifierValeursSertissages() {
	    boolean erreur = false;

	    List<TextField> hauteurFields = Arrays.asList(
	        hauteurSertissageEch1C1, hauteurSertissageEch2C1, hauteurSertissageEch3C1,
	        hauteurSertissageEch1C2, hauteurSertissageEch2C2, hauteurSertissageEch3C2
	    );

	    for (TextField field : hauteurFields) {
	        if (!field.isDisabled() && !field.getText().isEmpty()) {
	            try {
	                double valeur = Double.parseDouble(field.getText());
	                if (valeur < MIN_HAUTEUR || valeur > MAX_HAUTEUR) {
	                    colorBorderRed(field);
	                    showErrorDialog(
	                        "La valeur " + valeur + " dans le champ de hauteur de sertissage est hors limites.\nElle doit être entre " + MIN_HAUTEUR + " et " + MAX_HAUTEUR + ".", ""
	                    );
	                    erreur = true;
	                }
	            } catch (NumberFormatException e) {
	                colorBorderRed(field);
	                showErrorDialog(
	                    "Veuillez entrer une valeur numérique valide pour le champ : " + field.getId() + ".", ""
	                );
	                erreur = true;
	            }
	        }
	    }

	    return erreur;
	}

	    private boolean verifierValeursSertissagesFinale() {
		    boolean erreur = false;

		    List<TextField> hauteurFields = Arrays.asList(
		        hauteurSertissageEchFinC1,
		        hauteurSertissageEchFinC2
		    );

		    for (TextField field : hauteurFields) {
		        if (!field.isDisabled() && !field.getText().isEmpty()) {
		            try {
		                double valeur = Double.parseDouble(field.getText());
		                if (valeur < MIN_HAUTEUR || valeur > MAX_HAUTEUR) {
		                    colorBorderRed(field);
		                    showErrorDialog(
		                        "La valeur " + valeur + " dans le champ de hauteur de sertissage est hors limites.\nElle doit être entre " + MIN_HAUTEUR + " et " + MAX_HAUTEUR + ".", ""
		                    );
		                    erreur = true;
		                }
		            } catch (NumberFormatException e) {
		                colorBorderRed(field);
		                showErrorDialog(
		                    "Veuillez entrer une valeur numérique valide pour le champ : " + field.getId() + ".", ""
		                );
		                erreur = true;
		            }
		        }
		    }
	    return erreur;
	}

	@FXML
	void precedant(ActionEvent event) {
		try {
			// Charger la scène de Dashboard1
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/SertissageIDC/SelectionSertissageIDC.fxml"));
			Scene dashboard1Scene = new Scene(loader.load());
			dashboard1Scene.getStylesheets()
					.add(getClass().getResource("/Front_java/SertissageIDC/SelectionSertissageIDC.css").toExternalForm());

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
    	SertissageIDCInformations.reset() ; 

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
			nomProjet.setText(SertissageIDCInformations.projetSelectionner);
			codeControleSelectionner.setText(SertissageIDCInformations.codeControleSelectionner);

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
	    String encodedSectionFil = URLEncoder.encode(SertissageIDCInformations.sectionFilSelectionner, StandardCharsets.UTF_8);
	    String encodedNomProjet = URLEncoder.encode(SertissageIDCInformations.projetSelectionner, StandardCharsets.UTF_8);
	    String encodedSegmentPDEK = URLEncoder.encode(String.valueOf(AppInformations.operateurInfo.getSegment()), StandardCharsets.UTF_8);
	    String encodedPlantPDEK = URLEncoder.encode(AppInformations.operateurInfo.getPlant(), StandardCharsets.UTF_8);

	    String url = "http://localhost:8281/operations/SertissageIDC/numCycleMax?sectionFilSelectionner=" + encodedSectionFil 
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
	    String dernierNumeroStr = fetchNumMaxCycle();

	    try {
	        int dernierNumeroCycle = Integer.parseInt(dernierNumeroStr);
	        SertissageIDCInformations.numCycle = String.valueOf(dernierNumeroCycle);
	        
	        if (dernierNumeroCycle == 8) {
	            nbrCycle.setText("1");
	            SertissageIDCInformations.numCycle  =  "1" ; 
	        } else if (dernierNumeroCycle < 8) {
	            nbrCycle.setText(String.valueOf(dernierNumeroCycle + 1));
	            SertissageIDCInformations.numCycle  =dernierNumeroCycle + 1+"" ; 
	        } else {
	            nbrCycle.setText("1"); // Valeur par défaut si erreur
	        }
	    } catch (NumberFormatException e) {
	        nbrCycle.setText("1"); // Valeur par défaut si conversion échoue
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

	
	/****************** Extraire valeur depuis section fil ****************/
	public double extraireValeurNumerique(String sectionFil) {
	    return Double.parseDouble(sectionFil.trim().split(" ")[0] );
	}
	public int extraireValeurNumeriqueInteger(String sectionFil) {
	    return Integer.parseInt(sectionFil.trim().split(" ")[0] );
	}

	/****************************************/
	public void actualiserFenetreMere() {
	    if (SertissageIDCInformations.testTerminitionCommande == 1) {
	    		hauteurSertissageEchFinC1.setDisable(false); 
	    		hauteurSertissageEchFinC1.getStyleClass().add("textfield-blue-border");
	    		
	    		hauteurSertissageEchFinC2.setDisable(false);
	    		hauteurSertissageEchFinC2.getStyleClass().add("textfield-blue-border");
	    		
	 	        forceTractionEchFinC1.setDisable(false);
	 	        forceTractionEchFinC1.getStyleClass().add("textfield-blue-border");
	 	       
	 	        forceTractionEchFinC2.setDisable(false);
	 	        forceTractionEchFinC2.getStyleClass().add("textfield-blue-border");
	 	       
	 	        quantiteCycle.setDisable(false);
	 	       quantiteCycle.getStyleClass().add("textfield-blue-border");
	 	       
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
    
    private static void colorBorderRed(TextField... fields) {
        for (TextField field : fields) {
            field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            field.setOnMouseClicked(event -> field.setStyle("-fx-border-color: red; -fx-border-width: 2px;"));

        }
    }
    /***************************************************/
    public void centerTextFields(TextField... fields) {
        for (TextField field : fields) {
            field.setStyle("-fx-alignment: center;"); // Centre le texte dans le champ
        }
    }
    public double parseDoubleWithCleanup(String input) {
        if (input == null || input.trim().isEmpty()) {
            return 0.0; // Retourne une valeur par défaut (0.0) si l'entrée est vide ou nulle
        }
        
        // Supprimer les espaces
        input = input.replaceAll("\\s", "");
        
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Erreur de format pour la valeur : " + input);
            return 0.0; // Retourner une valeur par défaut en cas d'erreur de format
        }
    }
    /************************************************* Recupertion dernier num cycle **************************/
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    public String fetchNumMaxCycle() {
        try {
            // Encoder les paramètres pour éviter les erreurs d'URL
            String sectionFilEncoded = URLEncoder.encode(SertissageIDCInformations.sectionFilSelectionner, StandardCharsets.UTF_8);
            String projetEncoded = URLEncoder.encode(SertissageIDCInformations.projetSelectionner, StandardCharsets.UTF_8);
            String nomPlantEncoded = URLEncoder.encode(AppInformations.operateurInfo.getPlant(), StandardCharsets.UTF_8);

            // Construire l'URL avec les paramètres encodés
            String urlString = "http://localhost:8281/operations/SertissageIDC/dernier-numero-cycle?" +
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