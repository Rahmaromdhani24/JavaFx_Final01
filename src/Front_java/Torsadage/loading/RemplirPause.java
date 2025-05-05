package Front_java.Torsadage.loading;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import Front_java.Configuration.ActiveTextFieldManager;
import Front_java.Configuration.AppInformations;
import Front_java.Configuration.SertissageIDCInformations;
import Front_java.Configuration.TorsadageInformations;
import Front_java.Modeles.SertissageIDCData;
import Front_java.Modeles.TorsadageDTO;
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
	    private TextField decalageMaxC1;

	    @FXML
	    private TextField decalageMaxC2;

	    @FXML
	    private TextField longueurFinCommande;

	    @FXML
	    private TextField longueurPasFinCommande;

	    @FXML
	    private TextField quantiteAtteint;

	    @FXML
	    private StackPane stackPane;

	

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
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/Torsadage/loading/LoadingTorsadage.fxml"));
              Scene loadingScene = new Scene(loader.load());

              // Ajouter le fichier CSS
              String cssPath = "/Front_java/Torsadage/loading/LoadingTorsadage.css";
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
        if (quantiteAtteint.getText().isEmpty() || longueurFinCommande.getText().isEmpty() || longueurPasFinCommande.getText().isEmpty() ||
        		decalageMaxC1.getText().isEmpty() || decalageMaxC2.getText().isEmpty()) {
            showErrorDialog("Veuillez remplir tous les champs obligatoires avant de continuer !", "Champs obligatoires");
            return;
        }

        // 2. Stockage des valeurs saisies
        TorsadageInformations.quantiteAtteint = quantiteAtteint.getText();
        TorsadageInformations.longueurPasFinCde =longueurPasFinCommande.getText();
        TorsadageInformations.decalageFinC1 =decalageMaxC1.getText();
        TorsadageInformations.decalageFinC2 = decalageMaxC2.getText();
        TorsadageInformations.longueurFinalFinCde = longueurFinCommande.getText();
        
        
        // 4. Affichage de la confirmation avant d'ajouter le PDEK
        String message = "Veuillez confirmer les données saisies ? \n\n";
        showConfirmationDialog(message, "Confirmation", () -> {
            // Ajout du PDEK après confirmation
            ajouterPdekAvecTorsadage() ; 

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
            TorsadageInformations.specificationsMesure = null;
            TorsadageInformations.codeControleSelectionner = null;
            TorsadageInformations.projetSelectionner = null;

            // Chargement de la nouvelle fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/Torsadage/SelectionInitiale.fxml"));
            Scene dashboardScene = new Scene(loader.load());
            dashboardScene.getStylesheets().add(getClass().getResource("/Front_java/Torsadage/SelectionInitiale.css").toExternalForm());

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

/********************************* Ajouter PDEK *******************************************/
private void ajouterPdekAvecTorsadage() {
	Task<Void> task = new Task<>() {
		@Override
		protected Void call() throws Exception {
			try {
				// Code pour l'ajout du PDEK
				String token = AppInformations.token;
				String encodedProjet = URLEncoder.encode(TorsadageInformations.projetSelectionner,
						StandardCharsets.UTF_8);

				String url = "http://localhost:8281/operations/torsadage/ajouterPDEK" + "?matriculeOperateur="
						+ AppInformations.operateurInfo.getMatricule() + "&projet=" + encodedProjet;

				// Récupération des valeurs saisies et création de l'objet SoudureDTO
				TorsadageDTO torsadage = new TorsadageDTO();
				int x1 = Integer.parseInt(TorsadageInformations.ech1);
				int x2=  Integer.parseInt(TorsadageInformations.ech2);
				int x3 = Integer.parseInt(TorsadageInformations.ech3);
				int x4 = Integer.parseInt(TorsadageInformations.ech4);
				int x5 = Integer.parseInt(TorsadageInformations.ech5);

				// Calcul des valeurs max et min
				int maxValue = Math.max(Math.max(Math.max(x1, x2), Math.max(x3, x4)), x5);
				int minValue = Math.min(Math.min(Math.min(x1, x2), Math.min(x3, x4)), x5);
				double moy = (x1 + x2 + x3 + x4 + x5) / 5.0;
				int R = maxValue - minValue;

				
	

				
				// Remplir l'objet SoudureDTO avec les valeurs
				torsadage.setCode(TorsadageInformations.codeControleSelectionner);
				torsadage.setNumeroCycle(TorsadageInformations.numCourant );
				torsadage.setSpecificationMesure(TorsadageInformations.specificationsMesure);
				torsadage.setDecalageMaxDebutCdec1(Integer.parseInt(TorsadageInformations.decalageDebutC1)); 					
				torsadage.setDecalageMaxDebutCdec2(Integer.parseInt(TorsadageInformations.decalageDebutC2)); 		
				torsadage.setDecalageMaxFinCdec1(Integer.parseInt(TorsadageInformations.decalageFinC1 )); 					
				torsadage.setDecalageMaxFinCdec2(Integer.parseInt(TorsadageInformations.decalageFinC2 )); 									
				torsadage.setEch1(x1);
				torsadage.setEch2(x2);
				torsadage.setEch3(x3);
				torsadage.setEch4(x4 );
				torsadage.setEch5(x5);		
				torsadage.setMoyenne(moy);
				torsadage.setEtendu(R);
				ZoneId zoneId = ZoneId.of("Europe/Paris"); // Remplace par ton fuseau horaire
				LocalDate dateActuelle = Instant.now().atZone(zoneId).toLocalDate();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				torsadage.setDate(dateActuelle.format(formatter));
				torsadage.setQuantiteAtteint(Integer.parseInt(quantiteAtteint.getText()));
				torsadage.setQuantiteTotale(Integer.parseInt(TorsadageInformations.quantiteTotal));
				torsadage.setNumerofil(TorsadageInformations.numFils);
				torsadage.setNumCommande(Integer.parseInt(TorsadageInformations.numCommande));
				torsadage.setLongueurBoutDebutCdeC1(Integer.parseInt(TorsadageInformations.lognueurBoutDebutC1));
				torsadage.setLongueurBoutDebutCdeC2(Integer.parseInt(TorsadageInformations.lognueurBoutDebutC2));
				torsadage.setLongueurBoutFinCdeC1(Integer.parseInt(TorsadageInformations.lognueurBoutFinC1));
				torsadage.setLongueurBoutFinCdeC2(Integer.parseInt(TorsadageInformations.lognueurBoutFinC2));
				torsadage.setLongueurFinalDebutCde(Integer.parseInt(TorsadageInformations.longueurFinalDebutCde));
				torsadage.setLongueurFinalFinCde(Integer.parseInt(TorsadageInformations.longueurFinalFinCde));
				torsadage.setLongueurPasFinCde(Double.parseDouble(TorsadageInformations.longueurPasFinCde));

				// Conversion de l'objet SoudureDTO en JSON
				ObjectMapper objectMapper = new ObjectMapper();
				String torsadageJson = objectMapper.writeValueAsString(torsadage);

				HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
						.header("Authorization", "Bearer " + token).header("Content-Type", "application/json")
						.POST(HttpRequest.BodyPublishers.ofString(torsadageJson)).build();

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


}
