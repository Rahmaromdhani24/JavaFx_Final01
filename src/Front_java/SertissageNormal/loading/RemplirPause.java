package Front_java.SertissageNormal.loading;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import Front_java.Configuration.ActiveTextFieldManager;
import Front_java.Configuration.AppInformations;
import Front_java.Configuration.SertissageNormaleInformations;
import Front_java.Modeles.SertissageNormalData;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;


public class RemplirPause {

	// Variables pour stocker la position de la souris
	private double xOffset = 0;
	private double yOffset = 0;
 
    
	  @FXML
	    private Button btnAnnuler;

	    @FXML
	    private Button btnTerminer;

	    @FXML
	    private TextField hauteurIsolant;

	    @FXML
	    private TextField hauteurSertissage;

	    @FXML
	    private TextField largeurIsolant;

	    @FXML
	    private TextField largeurSertissage;

	    @FXML
	    private TextField quantiteCycle;

	    @FXML
	    private StackPane stackPane;

	    @FXML
	    private TextField traction;



    @FXML
    private void closeFenetre2(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setActiveOnFocus(TextField textField) {
        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                ActiveTextFieldManager.getInstance().setActiveTextField(textField);
            }
        });
    }
    
    @FXML
    public void initialize() {
    }
    @FXML
    private void annuler(ActionEvent event ) {
    	  try {
              // Charger le fichier FXML pour la fenêtre de chargement
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/SertissageNormal/loading/LoadingSertissageNormal.fxml"));
              Scene loadingScene = new Scene(loader.load());

              // Ajouter le fichier CSS
              String cssPath = "/Front_java/SertissageNormal/loading/LoadingSertissageNormal.css";
              if (getClass().getResource(cssPath) != null) {
                  loadingScene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
              } else {
                  System.out.println("Fichier CSS introuvable : " + cssPath);
              }

              // Récupérer la fenêtre parente
              Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

              // Cacher la fenêtre parente
              parentStage.hide();

              // Créer la fenêtre de chargement
              Stage loadingStage = new Stage();
              loadingStage.setScene(loadingScene);
              loadingStage.initStyle(StageStyle.UNDECORATED);
              loadingStage.initModality(Modality.APPLICATION_MODAL);
              loadingStage.initOwner(parentStage);

              // Ajouter une icône (facultatif)
              String iconPath = "/logo_app.jpg";
              if (getClass().getResource(iconPath) != null) {
                  loadingStage.getIcons().add(new Image(getClass().getResourceAsStream(iconPath)));
              } else {
                  System.out.println("Icône introuvable : " + iconPath);
              }

              // Permettre de déplacer la fenêtre avec la souris
              loadingScene.setOnMousePressed(eventMouse -> {
                  xOffset = eventMouse.getSceneX();
                  yOffset = eventMouse.getSceneY();
              });

              loadingScene.setOnMouseDragged(eventMouse -> {
                  loadingStage.setX(eventMouse.getScreenX() - xOffset);
                  loadingStage.setY(eventMouse.getScreenY() - yOffset);
              });

              // Afficher la fenêtre de chargement
              loadingStage.show();
          } catch (IOException ex) {
              System.out.println("❌ Erreur lors du chargement de la fenêtre de chargement : " + ex.getMessage());
              ex.printStackTrace();
          }
    }

    @FXML
    private void terminerChargement(ActionEvent event) {
        // 1. Vérification si les champs sont vides
        if (quantiteCycle.getText().isEmpty() || traction.getText().isEmpty() || hauteurSertissage.getText().isEmpty() ||
            largeurSertissage.getText().isEmpty() || hauteurIsolant.getText().isEmpty() || largeurIsolant.getText().isEmpty()) {
            showErrorDialog("Veuillez remplir tous les champs obligatoires avant de continuer !", "Champs obligatoires");
            return;
        }

        // 2. Stockage des valeurs saisies
        SertissageNormaleInformations.quantiteAtteint = quantiteCycle.getText();
        SertissageNormaleInformations.traction = traction.getText();
        SertissageNormaleInformations.hauteurSertissageEchFin = Double.parseDouble(hauteurSertissage.getText());
        SertissageNormaleInformations.largeurSertissageEchFin = Double.parseDouble(largeurSertissage.getText());
        SertissageNormaleInformations.hauteurIsolantEchFin = Double.parseDouble(hauteurIsolant.getText());
        SertissageNormaleInformations.largeurIsolantEchFin = Double.parseDouble(largeurIsolant.getText());
        double val1= Double.parseDouble(hauteurSertissage.getText());
        // 3. Validation des valeurs
        boolean hasError = false;
      
    	//// ligne 1 : haauteur Sertissage 
    	 double valHauteurSertissage = SertissageNormaleInformations.labelHauteurSertissage ; 
    	  if (areFieldsEqual(SertissageNormaleInformations.hauteurSertissageEch1, SertissageNormaleInformations.hauteurSertissageEch2, SertissageNormaleInformations.hauteurSertissageEch3, hauteurSertissage )) {
              colorBorderRed(hauteurSertissage);
              showErrorDialog("Les valeurs des échantillons de hauteur de sertissage  doivent être différentes.", "");
              hasError = true;
          }
        // Vérification des valeurs hors limites
        List<TextField> hauteurSertissageFields = Arrays.asList(
        		hauteurSertissage        );
       
      
        for (TextField field : hauteurSertissageFields) {
            try {
                double valeur = Double.parseDouble(field.getText());
                if (valeur < (valHauteurSertissage - 0.05) || valeur > ( valHauteurSertissage+ 0.05)) {
                    colorBorderRed(field);
                    showErrorDialog("La valeur " + valeur + " dans  champs de hauteur de sertissage est hors limites. Elle doit être entre " +(valHauteurSertissage - 0.05)  + " et " + (valHauteurSertissage + 0.05)  + ".", "");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                colorBorderRed(field);
                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
                hasError = true;
            }
        }

        //// ligne 2 : largeur Sertissage 
	       double valLargeurSertissage = SertissageNormaleInformations.labelLargeurSertissage ; 
        String toleranceStr = fetchToleranceLargeurSertissageFromAPI(
        		SertissageNormaleInformations.numeroOutils,
        		SertissageNormaleInformations.numeroContacts,
        		SertissageNormaleInformations.sectionFil
        		).replace("±", "").trim(); // Suppression du symbole ±
        		double toleranceLargeurSertissage = Double.parseDouble(toleranceStr);
        		
          	  if (areFieldsEqualDeuxChamps(SertissageNormaleInformations.largeurSertissage, largeurSertissage )) {
	            colorBorderRed(largeurSertissage);
	            showErrorDialog("Les valeurs des échantillons de largeur de sertissage  doivent être différentes et ne dépasse pas ."+valLargeurSertissage+toleranceLargeurSertissage, "");
	            hasError = true;
	        }
	        // Vérification des valeurs hors limites
	        List<TextField> largeurSertissageFields = Arrays.asList(
	        		largeurSertissage    	        		
	        );
	       
	     
	        for (TextField field : largeurSertissageFields) {
	            try {
	                double valeur = Double.parseDouble(field.getText());
	                if (valeur < (valLargeurSertissage - toleranceLargeurSertissage) || valeur > ( valLargeurSertissage+ toleranceLargeurSertissage)) {
	                    colorBorderRed(field);
	                    showErrorDialog("La valeur " + valeur + " dans  champs de largeur de sertissage est hors limites. Elle doit être entre " +(valHauteurSertissage - toleranceLargeurSertissage)  + " et " + (valHauteurSertissage + toleranceLargeurSertissage)  + ".", "");
	                    hasError = true;
	                }
	            } catch (NumberFormatException e) {
	                colorBorderRed(field);
	                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
	                hasError = true;
	            }
	        }
	        //// ligne 3 : hauteur isolant   
   	       double valHauteurIsolant = SertissageNormaleInformations.labelHauteurIsolant ; 
 	        String toleranceStrHauteurIsolant = fetchToleranceHauteurIsolantFromAPI(
 	        		SertissageNormaleInformations.numeroOutils,
 	        		SertissageNormaleInformations.numeroContacts,
 	        		SertissageNormaleInformations.sectionFil
 	        		).replace("±", "").trim(); // Suppression du symbole ±
 	        		double toleranceHauteurIsolant = Double.parseDouble(toleranceStrHauteurIsolant);
 	        		
  	    	 if (areFieldsEqualDeuxChamps(SertissageNormaleInformations.hauteurIsolant , hauteurIsolant )) {
  	            colorBorderRed(hauteurIsolant);
  	            showErrorDialog("Les valeurs des échantillons de hauteur de isolant   doivent être différentes et ne dépasse pas ."+valHauteurIsolant+toleranceHauteurIsolant, "");
  	            hasError = true;
  	        }
  	        // Vérification des valeurs hors limites
  	        List<TextField> HauteurIsolantsFields = Arrays.asList(
  	        		hauteurIsolant    	        		
  	        );
  	       
  	     
  	        for (TextField field : HauteurIsolantsFields) {
  	            try {
  	                double valeur = Double.parseDouble(field.getText());
  	                if (valeur < (valHauteurIsolant - toleranceHauteurIsolant) || valeur > ( valHauteurIsolant+ toleranceHauteurIsolant)) {
  	                    colorBorderRed(field);
  	                    showErrorDialog("La valeur " + valeur + " dans  champs de hauteur de isolant  est hors limites. Elle doit être entre " +(valHauteurIsolant - toleranceHauteurIsolant)  + " et " + (valHauteurIsolant+ toleranceHauteurIsolant)  + ".", "");
  	                    hasError = true;
  	                }
  	            } catch (NumberFormatException e) {
  	                colorBorderRed(field);
  	                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
  	                hasError = true;
  	            }
  	        }
  	       //// ligne 4  : largeur isolant 
   	       double valLargeurIsolant = SertissageNormaleInformations.labelLargeurIsolant ; 
 	        String toleranceStrLargeurIsolant = fetchToleranceLargeurIsolantFromAPI(
 	        		SertissageNormaleInformations.numeroOutils,
 	        		SertissageNormaleInformations.numeroContacts,
 	        		SertissageNormaleInformations.sectionFil
 	        		).replace("±", "").trim(); // Suppression du symbole ±
 	        		double toleranceLargeurIsolant = Double.parseDouble(toleranceStrLargeurIsolant);
 	        		
  	    	 if (areFieldsEqualDeuxChamps(SertissageNormaleInformations.largeurIsolant ,   largeurIsolant  )) {
  	            colorBorderRed(largeurIsolant);
  	            showErrorDialog("Les valeurs des échantillons de largeur de isolant  doivent être différentes et ne dépasse pas ."+(valLargeurIsolant+toleranceLargeurIsolant), "");
  	            hasError = true;
  	        }
  	        // Vérification des valeurs hors limites
  	        List<TextField> largeurIsolantFields = Arrays.asList(
  	        		largeurIsolant       		
  	        );
  	       
  	     
  	        for (TextField field : largeurIsolantFields) {
  	            try {
  	                double valeur = Double.parseDouble(field.getText());
  	                if (valeur < (valLargeurIsolant - toleranceLargeurIsolant) || valeur > ( valLargeurIsolant+ toleranceLargeurIsolant)) {
  	                    colorBorderRed(field);
  	                    showErrorDialog("La valeur " + valeur + " dans  champs de hauteur de sertissage est hors limites. Elle doit être entre " +(valLargeurIsolant - toleranceLargeurIsolant)  + " et " + (valLargeurIsolant+ toleranceLargeurIsolant)  + ".", "");
  	                    hasError = true;
  	                }
  	            } catch (NumberFormatException e) {
  	                colorBorderRed(field);
  	                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
  	                hasError = true;
  	            }
  	        }
  	        //// ligne 5 : traction
    	       String valtractionString = SertissageNormaleInformations.labelTraction ; 
            int tractionValue = extractValue(valtractionString) ; 
            System.out.print(" valeur de traction numerique "+tractionValue);
  	        		
   	    	 if (areFieldsEqualDeuxChamps(SertissageNormaleInformations.tractionFinEch , traction  )) {
   	            colorBorderRed(traction );
   	            showErrorDialog("Les valeurs des échantillons de traction  doivent être différentes et ne dépasse pas ."+tractionValue+" N", "");
   	            hasError = true;
   	        }
   	        // Vérification des valeurs hors limites
   	        List<TextField> tractionFields = Arrays.asList(
   	        		traction 	
   	        );
   	       
   	     
   	        for (TextField field : tractionFields) {
   	            try {
   	                int valeur = Integer.parseInt(field.getText());
   	                if ( valeur < ( tractionValue)) {
   	                    colorBorderRed(field);
   	                    showErrorDialog("La valeur " + valeur + " dans  champs traction  est hors limites. Elle doit être superieur ou égale a  " +tractionValue+" N"  + ".", "");
   	                    hasError = true;
   	                }
   	            } catch (NumberFormatException e) {
   	                colorBorderRed(field);
   	                showErrorDialog("Veuillez entrer une valeur numérique valide " , "");
   	                hasError = true;
   	            }
   	        }
  	        if (hasError) {
  	            return;
  	        }

        // 4. Affichage de la confirmation avant d'ajouter le PDEK
        String message = "Veuillez confirmer les données saisies ? \n\n";
        showConfirmationDialog(message, "Confirmation", () -> {
            // Ajout du PDEK après confirmation
            ajouterPdekAvecSertissageNormal() ; 

            // 5. Affichage de la notification de succès avant d'exécuter afficherFenetreSelection()
            Platform.runLater(() -> {
                afficherNotificationSucces("Maintenant vous passez au cycle suivant");

                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> {
                	  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                      stage.close();
                      
                    Stage nouvelleFenetre = afficherFenetreSelection(); // Récupérer la nouvelle fenêtre

                    // Fermer toutes les autres fenêtres sauf celle-ci
                    for (Window window : Window.getWindows()) {
                        if (window instanceof Stage && window != nouvelleFenetre) {
                            ((Stage) window).close();
                        }
                    }
                });
                pause.play();
            });

        });
    }

    public Stage afficherFenetreSelection() {
        try {
            // Réinitialisation des sélections
            SertissageNormaleInformations.sectionFil = null;
            SertissageNormaleInformations.codeControleSelectionner = null;
            SertissageNormaleInformations.projetSelectionner = null;
            SertissageNormaleInformations.numeroContacts = null;
            SertissageNormaleInformations.numeroOutils = null;

            
            // Chargement de la nouvelle fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/SertissageNormal/SelectionSertissageNormal.fxml"));
            Scene dashboardScene = new Scene(loader.load());
            dashboardScene.getStylesheets().add(getClass().getResource("/Front_java/SertissageNormal/SelectionSertissageNormal.css").toExternalForm());

            // Création et configuration de la nouvelle fenêtre
            Stage dashboardStage = new Stage();
            dashboardStage.setScene(dashboardScene);
            dashboardStage.setMaximized(true);
            dashboardStage.initStyle(StageStyle.UNDECORATED);
            Image icon = new Image("/logo_app.jpg");
            dashboardStage.getIcons().add(icon);
            dashboardStage.show();

            return dashboardStage; // Retourner la nouvelle fenêtre pour gestion

        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la fenêtre dashboard : " + e.getMessage());
            showErrorDialog("Erreur lors du chargement du tableau de bord !", "Erreur");
            return null;
        }
    }

/***************************  Alerte d'erreur ********************************************************/

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


public void afficherNotificationSucces(String message) {
    System.out.println(" Affichage d'une notification : " + message);

    if (stackPane == null) {
        System.out.println("❌ Erreur : stackPane est null.");
        return;
    }

    // Chargement de l'icône avec vérification
    ImageView infoImageView = new ImageView();
    URL iconURL = getClass().getResource("/valide.png");
    if (iconURL != null) {
        infoImageView.setImage(new Image(iconURL.toExternalForm()));
    } 
    infoImageView.setFitWidth(120);
    infoImageView.setFitHeight(120);
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
   // HBox buttonBox = new HBox(closeButton);
   // buttonBox.setAlignment(Pos.CENTER);
   // buttonBox.setPadding(new Insets(10, 0, 0, 0)); // Optionnel : Ajoute un peu d'espace en haut

    // Ajout du bouton centré dans la boîte de dialogue
  //  content.setActions(buttonBox);

    JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);

    closeButton.setOnAction(e -> {
        dialog.close();
        System.out.println("Notification fermée par l'utilisateur.");
    });

    dialog.show();

    // Fermeture automatique après 5 secondes
    PauseTransition pause = new PauseTransition(Duration.seconds(10));
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

/**********************************************************************************************/
private static void colorBorderRed(TextField... fields) {
    for (TextField field : fields) {
        field.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        field.setOnMouseClicked(event -> field.setStyle("-fx-border-color: red; -fx-border-width: 2px;"));

    }
}
public static boolean areFieldsEqual(double f1, double f2, double f3, TextField f4) {
    try {
        double f4Value = Double.parseDouble(f4.getText().trim()); // Convertir le texte en double
        return Double.compare(f1, f2) == 0 &&
               Double.compare(f1, f3) == 0 &&
               Double.compare(f1, f4Value) == 0;
    } catch (NumberFormatException e) {
        return false; // Retourne false si f4 ne contient pas un nombre valide
    }
}
public static boolean areFieldsEqualDeuxChamps(double f1,  TextField f4) {
    try {
        double f4Value = Double.parseDouble(f4.getText().trim()); // Convertir le texte en double
        return 
               Double.compare(f1, f4Value) == 0;
    } catch (NumberFormatException e) {
        return false; // Retourne false si f4 ne contient pas un nombre valide
    }
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
/*******************************************************************************************/
private static final HttpClient httpClient = HttpClient.newHttpClient();

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
public static int extractValue(String input) {
    Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?"); // Capture les nombres entiers et décimaux
    Matcher matcher = pattern.matcher(input);
    
    if (matcher.find()) {
        return Integer.parseInt(matcher.group()); // Convertir en double
    }
    throw new IllegalArgumentException("Aucun nombre trouvé dans la chaîne !");
}
/***************** Ajout pdek **********************/
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

				sertissageNormal.setSectionFil(SertissageNormaleInformations.sectionFil+" mm²");
				LocalDate dateActuelle = LocalDate.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				sertissageNormal.setDate(dateActuelle.format(formatter)); 									
                sertissageNormal.setNumeroOutils(SertissageNormaleInformations.numeroOutils);
                sertissageNormal.setNumeroContacts(SertissageNormaleInformations.numeroContacts); 
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

				if (response.statusCode() == 201) {
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
}
