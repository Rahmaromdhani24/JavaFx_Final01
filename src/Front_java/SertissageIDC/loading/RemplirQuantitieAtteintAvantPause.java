package Front_java.SertissageIDC.loading;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import Front_java.Configuration.ActiveTextFieldManager;
import Front_java.Configuration.AppInformations;
import Front_java.Configuration.SertissageIDCInformations;
import Front_java.Modeles.SertissageIDCData;
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


public class RemplirQuantitieAtteintAvantPause {

	// Variables pour stocker la position de la souris
	private double xOffset = 0;
	private double yOffset = 0;
 
    
	 @FXML
	    private Button btnAnnuler;

	    @FXML
	    private Button btnTerminer;

	    @FXML
	    private TextField forceTractionC1;

	    @FXML
	    private TextField forceTractionC2;

	    @FXML
	    private TextField hauteurSertissageC1;

	    @FXML
	    private TextField hauteurSertissageC2;
	    
	    @FXML
	    private TextField quantiteCycle;

	    @FXML
	    private StackPane stackPane;

		// Définition des bornes
		final double MIN_HAUTEUR = 10.85;
		final double MAX_HAUTEUR = 11;
		final int MIN_TRACTION = 50;
		final int MAX_TRACTION = 110;

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
        if (quantiteCycle.getText().isEmpty() || hauteurSertissageC1.getText().isEmpty() || hauteurSertissageC2.getText().isEmpty() ||
            forceTractionC1.getText().isEmpty() || forceTractionC2.getText().isEmpty()) {
            showErrorDialog("Veuillez remplir tous les champs obligatoires avant de continuer !", "Champs obligatoires");
            return;
        }

        // 2. Stockage des valeurs saisies
        SertissageIDCInformations.quantiteCycle = Integer.parseInt(quantiteCycle.getText());
        SertissageIDCInformations.hauteurSertissageC1EchFin = Double.parseDouble(hauteurSertissageC1.getText());
        SertissageIDCInformations.hauteurSertissageC2EchFin = Double.parseDouble(hauteurSertissageC2.getText());
        SertissageIDCInformations.forceTractionEchFinC1 = Integer.parseInt(forceTractionC1.getText());
        SertissageIDCInformations.forceTractionEchFinC2 = Integer.parseInt(forceTractionC2.getText());

        // 3. Validation des valeurs
        boolean hasError = false;
        if (areFieldsEqual(SertissageIDCInformations.hauteurSertissageC1Ech1, SertissageIDCInformations.hauteurSertissageC1Ech2, SertissageIDCInformations.hauteurSertissageC1Ech3, hauteurSertissageC1)) {
            colorBorderRed(hauteurSertissageC1);
            showErrorDialog("Les valeurs des échantillons de hauteur de sertissage C1 doivent être différentes.", "");
            hasError = true;
        }

        if (areFieldsEqual(SertissageIDCInformations.hauteurSertissageC2Ech1, SertissageIDCInformations.hauteurSertissageC2Ech2, SertissageIDCInformations.hauteurSertissageC2Ech3, hauteurSertissageC2)) {
            colorBorderRed(hauteurSertissageC2);
            showErrorDialog("Les valeurs des échantillons de hauteur de sertissage C2 doivent être différentes.", "");
            hasError = true;
        }

        if (areFieldsEqual(SertissageIDCInformations.forceTractionEch1C1, SertissageIDCInformations.forceTractionEch2C1, SertissageIDCInformations.forceTractionEch3C1, forceTractionC1)) {
            colorBorderRed(forceTractionC1);
            showErrorDialog("Les valeurs des échantillons de force de traction C1 doivent être différentes.", "");
            hasError = true;
        }

        if (areFieldsEqual(SertissageIDCInformations.forceTractionEch1C2, SertissageIDCInformations.forceTractionEch2C2, SertissageIDCInformations.forceTractionEch3C2, forceTractionC2)) {
            colorBorderRed(forceTractionC2);
            showErrorDialog("Les valeurs des échantillons de force de traction C2 doivent être différentes.", "");
            hasError = true;
        }

        // Vérification des limites
        List<TextField> hauteurFields = Arrays.asList(hauteurSertissageC1, hauteurSertissageC2);
        for (TextField field : hauteurFields) {
            try {
                double valeur = Double.parseDouble(field.getText());
                if (valeur < MIN_HAUTEUR || valeur > MAX_HAUTEUR) {
                    colorBorderRed(field);
                    showErrorDialog("La valeur " + valeur + " dans le champ de hauteur de sertissage est hors limites.", "");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                colorBorderRed(field);
                showErrorDialog("Veuillez entrer une valeur numérique valide pour " + field.getId() + ".", "");
                hasError = true;
            }
        }

        List<TextField> tractionFields = Arrays.asList(forceTractionC1, forceTractionC2);
        for (TextField field : tractionFields) {
            try {
                int valeur = Integer.parseInt(field.getText());
                if (valeur < MIN_TRACTION || valeur > MAX_TRACTION) {
                    colorBorderRed(field);
                    showErrorDialog("La valeur " + valeur + " dans le champ de traction est hors limites.", "");
                    hasError = true;
                }
            } catch (NumberFormatException e) {
                colorBorderRed(field);
                showErrorDialog("Veuillez entrer une valeur numérique valide pour " + field.getId() + ".", "");
                hasError = true;
            }
        }

        // Si une erreur est détectée, stopper l'exécution
        if (hasError) {
            return;
        }

        // 4. Affichage de la confirmation avant d'ajouter le PDEK
        String message = "Veuillez confirmer les données saisies ? \n\n";
        showConfirmationDialog(message, "Confirmation", () -> {
            // Ajout du PDEK après confirmation
            ajouterPdekAvecSertissageIDC();

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
            SertissageIDCInformations.sectionFilSelectionner = null;
            SertissageIDCInformations.codeControleSelectionner = null;
            SertissageIDCInformations.projetSelectionner = null;

            // Chargement de la nouvelle fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/SertissageIDC/SelectionSertissageIDC.fxml"));
            Scene dashboardScene = new Scene(loader.load());
            dashboardScene.getStylesheets().add(getClass().getResource("/Front_java/SertissageIDC/SelectionSertissageIDC.css").toExternalForm());

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
private void ajouterPdekAvecSertissageIDC() {
	Task<Void> task = new Task<>() {
		@Override
		protected Void call() throws Exception {
			try {
				// Code pour l'ajout du PDEK
				String token = AppInformations.token;
				String encodedProjet = URLEncoder.encode(SertissageIDCInformations.projetSelectionner,
						StandardCharsets.UTF_8);

				String url = "http://localhost:8281/operations/SertissageIDC/ajouterPdekSertissageIDC" + "?matricule="
						+ AppInformations.operateurInfo.getMatricule() + "&nomProjet=" + encodedProjet;

				// Récupération des valeurs saisies et création de l'objet SoudureDTO
				SertissageIDCData sertissageIDC = new SertissageIDCData();							

				sertissageIDC.setSectionFil(SertissageIDCInformations.sectionFilSelectionner);
				sertissageIDC.setForceTraction("50 N");
				sertissageIDC.setHauteurSertissageMax(11);
				sertissageIDC.setHauteurSertissageMin(10.85); 	
				LocalDate dateActuelle = LocalDate.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				sertissageIDC.setDate(dateActuelle.format(formatter)); 									
				sertissageIDC.setHauteurSertissageC1Ech1(SertissageIDCInformations.hauteurSertissageC1Ech1);
				sertissageIDC.setHauteurSertissageC1Ech2(SertissageIDCInformations.hauteurSertissageC1Ech2);
				sertissageIDC.setHauteurSertissageC1Ech3(SertissageIDCInformations.hauteurSertissageC1Ech3);
				sertissageIDC.setHauteurSertissageC1EchFin(SertissageIDCInformations.hauteurSertissageC1EchFin);
				sertissageIDC.setHauteurSertissageC2Ech1(SertissageIDCInformations.hauteurSertissageC2Ech1);		
				sertissageIDC.setHauteurSertissageC2Ech2(SertissageIDCInformations.hauteurSertissageC2Ech2);
				sertissageIDC.setHauteurSertissageC2Ech3(SertissageIDCInformations.hauteurSertissageC2Ech3);					
				sertissageIDC.setHauteurSertissageC2EchFin(SertissageIDCInformations.hauteurSertissageC2EchFin);
				sertissageIDC.setCodeControle(SertissageIDCInformations.codeControleSelectionner);
				sertissageIDC.setProduit(SertissageIDCInformations.produit);
				sertissageIDC.setSerieProduit(SertissageIDCInformations.serieProduit);
				sertissageIDC.setQuantiteCycle(SertissageIDCInformations.quantiteCycle);
				sertissageIDC.setNumeroMachine(SertissageIDCInformations.numeroMachine );
				sertissageIDC.setForceTractionC1Ech1(SertissageIDCInformations.forceTractionEch1C1);
				sertissageIDC.setForceTractionC1Ech2(SertissageIDCInformations.forceTractionEch2C1);
				sertissageIDC.setForceTractionC1Ech3(SertissageIDCInformations.forceTractionEch3C1);
				sertissageIDC.setForceTractionC1EchFin(SertissageIDCInformations.forceTractionEchFinC1);
				sertissageIDC.setForceTractionC2Ech1(SertissageIDCInformations.forceTractionEch1C2);
				sertissageIDC.setForceTractionC2Ech2(SertissageIDCInformations.forceTractionEch2C2);
				sertissageIDC.setForceTractionC2Ech3(SertissageIDCInformations.forceTractionEch3C2);
				sertissageIDC.setForceTractionC2EchFin(SertissageIDCInformations.forceTractionEchFinC2);
                sertissageIDC.setQuantiteCycle(SertissageIDCInformations.quantiteCycle) ; 
                
				// Conversion de l'objet SoudureDTO en JSON
				ObjectMapper objectMapper = new ObjectMapper();
				String sertissageIDCJson = objectMapper.writeValueAsString(sertissageIDC);

				HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
						.header("Authorization", "Bearer " + token).header("Content-Type", "application/json")
						.POST(HttpRequest.BodyPublishers.ofString(sertissageIDCJson)).build();

				HttpClient client = HttpClient.newHttpClient();
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

				if (response.statusCode() == 201) {
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
