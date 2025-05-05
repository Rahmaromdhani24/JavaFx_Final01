package Front_java.SoudureUltrason.CodeB;

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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import Front_java.Configuration.AppInformations;
import Front_java.Configuration.SoudureInformations;
import Front_java.Modeles.SoudureDTO;
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
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;


public class RemplirQuantitieAtteintAvantCodeB {

	// Variables pour stocker la position de la souris
	private double xOffset = 0;
	private double yOffset = 0;
 
    
    @FXML
    private StackPane stackPane; 
    
    @FXML
    private Button closeButton;
    
    
    @FXML
    private Button btnTerminer;

    @FXML
    private Label numCycleAncien;

    @FXML
    private TextField quantiteAtteintAncien;




    @FXML
    private void closeFenetre2(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {

        numCycleAncien.setText(SoudureInformations.numeroCycle +"");
    }


    @FXML
    private void annuler(ActionEvent event ) {
    	  try {
              // Charger le fichier FXML pour la fenêtre de chargement
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/Loading/Loading.fxml"));
              Scene loadingScene = new Scene(loader.load());

              // Ajouter le fichier CSS
              String cssPath = "/Front_java/Loading/Loading.css";
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
        // 1. Vérification si le champ de quantité atteint est vide
        if (quantiteAtteintAncien.getText().isEmpty()) {
            // Afficher l'alerte d'erreur si le champ est vide
            showErrorDialog("Veuillez remplir le champ de quantité atteinte avant de continuer !", "Champs obligatoires");
            return; // Arrêter la méthode si un champ est vide
        }
        SoudureInformations.quantiteAtteint= Integer.parseInt(quantiteAtteintAncien.getText()) ;
        // 2. Si le champ n'est pas vide, appeler la méthode ajouterPdekAvecSoudure()
        ajouterPdekAvecSoudure();

        // 3. Afficher la notification de succès
        afficherNotificationSucces("Maintenant vous passez au cycle suivant");

        // 4. Ajouter un délai pour que la notification ait le temps d'apparaître
        PauseTransition pause = new PauseTransition(Duration.seconds(2)); // Attendre 2 secondes
        pause.setOnFinished(e -> {
            try {
                // Charger la nouvelle fenêtre
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/SoudureUltrason/CodeB/ChoisirCodeControles.fxml"));
                Scene loadingScene = new Scene(loader.load());

                // Ajouter le fichier CSS
                String cssPath = "/Front_java/SoudureUltrason/CodeB/ChoisirCodeControles.css";
                if (getClass().getResource(cssPath) != null) {
                    loadingScene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
                } else {
                    System.out.println("❌ Fichier CSS introuvable : " + cssPath);
                }

                // Récupérer la fenêtre actuelle (parent)
                Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                parentStage.hide(); // Masquer la fenêtre parent

                // Créer la nouvelle fenêtre
                Stage loadingStage = new Stage();
                loadingStage.setScene(loadingScene);
                loadingStage.initStyle(StageStyle.UNDECORATED);
                loadingStage.initModality(Modality.APPLICATION_MODAL);
                loadingStage.initOwner(parentStage);

                // Ajouter une icône (optionnel)
                String iconPath = "/logo_app.jpg";
                if (getClass().getResource(iconPath) != null) {
                    loadingStage.getIcons().add(new Image(getClass().getResourceAsStream(iconPath)));
                } else {
                    System.out.println("❌ Icône introuvable : " + iconPath);
                }

                // Permettre le déplacement de la fenêtre
                loadingScene.setOnMousePressed(eventMouse -> {
                    xOffset = eventMouse.getSceneX();
                    yOffset = eventMouse.getSceneY();
                });

                loadingScene.setOnMouseDragged(eventMouse -> {
                    loadingStage.setX(eventMouse.getScreenX() - xOffset);
                    loadingStage.setY(eventMouse.getScreenY() - yOffset);
                });

                // Afficher la nouvelle fenêtre après le délai
                loadingStage.show();

            } catch (IOException ex) {
                System.out.println("❌ Erreur lors du chargement de la nouvelle fenêtre : " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Démarrer le délai avant d'afficher la nouvelle fenêtre
        pause.play();
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
				int x1 = SoudureInformations.pelageX1;
				int x2 = SoudureInformations.pelageX2;
				int x3 = SoudureInformations.pelageX3;
				int x4 = SoudureInformations.pelageX4;
				int x5 = SoudureInformations.pelageX5;

				// Calcul des valeurs max et min
				int maxValue = Math.max(Math.max(Math.max(x1, x2), Math.max(x3, x4)), x5);
				int minValue = Math.min(Math.min(Math.min(x1, x2), Math.min(x3, x4)), x5);
				int moy = (x1 + x2 + x3 + x4 + x5) / 5;
				int R = maxValue - minValue;

				// Remplir l'objet SoudureDTO avec les valeurs
				soudure.setCode(SoudureInformations.codeControleSelectionner );
				soudure.setNumeroCycle(SoudureInformations.numeroCycle + 1);
				SoudureInformations.numerCyclePDEK = SoudureInformations.numeroCycle;
				soudure.setSectionFil(AppInformations.sectionFilSelectionner);
				SoudureInformations.sectionFilSelectionner = AppInformations.sectionFilSelectionner;
				soudure.setLimitePelage(AppInformations.nbrPelage);
				SoudureInformations.nbrPelage = AppInformations.nbrPelage;
				soudure.setPliage(SoudureInformations.pliage);
				soudure.setDistanceBC(SoudureInformations.distanceBC);
				soudure.setTraction(SoudureInformations.traction);
				soudure.setPelageX1(x1);
				soudure.setPelageX2(x2);
				soudure.setPelageX3(x3);
				soudure.setPelageX4(x4);
				soudure.setPelageX5(x5);
				soudure.setNombreKanban(SoudureInformations.numeroKanban);
				soudure.setGrendeurLot(SoudureInformations.grandeurLot);
				soudure.setNombreNoeud(SoudureInformations.numNoeud);
				soudure.setMoyenne(moy);
				SoudureInformations.moyenne = moy;
				soudure.setEtendu(R);
				SoudureInformations.etendu = R;
				LocalDate dateActuelle = LocalDate.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				soudure.setDate(dateActuelle.format(formatter));
				SoudureInformations.dateCreation = dateActuelle.format(formatter);
				soudure.setQuantiteAtteint(Integer.parseInt(quantiteAtteintAncien.getText()));
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



}
 

