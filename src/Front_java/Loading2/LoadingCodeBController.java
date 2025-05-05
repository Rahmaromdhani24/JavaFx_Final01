package Front_java.Loading2;

import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import Front_java.Configuration.AppInformations;
import Front_java.Configuration.SoudureInformationsCodeB;
import Front_java.Controller.Dashboard2Controller;
import Front_java.SoudureUltrason.RemplirFormCodeB.RemplirFormCodeB;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Insets;
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



public class LoadingCodeBController {

	// Variables pour stocker la position de la souris
	private double xOffset = 0;
	private double yOffset = 0;
 
    
    @FXML
    private StackPane rootPane; 
    
    @FXML
    private Button closeButton;
    
    @FXML
    private Button btnTerminer;

    @FXML
    private Button btnPause;

   /* @FXML
    private void closeFenetre2(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }*/

    @FXML
    private void initialize() {
        // Charger la langue par défaut (Français)

    }
    @FXML
    private void pauseChargement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/SoudureUltrason/CodeB/RemplirQuantiteAtteintAvantCodeB.fxml"));
            Scene loadingScene = new Scene(loader.load());
            String cssPath = "/Front_java/SoudureUltrason/CodeB/RemplirQuantitieAtteintAvantCodeB.css";
            if (getClass().getResource(cssPath) != null) {
                loadingScene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            } else {
                System.out.println("Fichier CSS introuvable : " + cssPath);
            }
            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            parentStage.hide();
            Stage loadingStage = new Stage();
            loadingStage.setScene(loadingScene);
            loadingStage.initStyle(StageStyle.UNDECORATED);
            loadingStage.initModality(Modality.APPLICATION_MODAL);
            loadingStage.initOwner(parentStage);
            String iconPath = "/logo_app.jpg";
            if (getClass().getResource(iconPath) != null) {
                loadingStage.getIcons().add(new Image(getClass().getResourceAsStream(iconPath)));
            } else {
                System.out.println("Icône introuvable : " + iconPath);
            }

            loadingScene.setOnMousePressed(eventMouse -> {
                xOffset = eventMouse.getSceneX();
                yOffset = eventMouse.getSceneY();
            });

            loadingScene.setOnMouseDragged(eventMouse -> {
                loadingStage.setX(eventMouse.getScreenX() - xOffset);
                loadingStage.setY(eventMouse.getScreenY() - yOffset);
            });

            loadingStage.show();
        } catch (IOException ex) {
            System.out.println("❌ Erreur lors du chargement de la fenêtre de chargement : " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    private RemplirFormCodeB parentControllerCodeB; // Référence vers la fenêtre mère

    public void setParentControllerCodeB(RemplirFormCodeB parentControllerCodeB) {
        this.parentControllerCodeB = parentControllerCodeB;
    }
    
    @FXML
    public void terminerChargement() {
        AppInformations.testTerminitionCommande = 1;

        // Vérifier si le parentController n'est pas null avant d'appeler la mise à jour
        if (parentControllerCodeB != null) {
        	parentControllerCodeB.actualiserFenetreMere();
            parentControllerCodeB.afficherNotification("Veuillez remplir la quantité atteinte.");
        }

        // Fermer la fenêtre fille
        Stage stage = (Stage) btnTerminer.getScene().getWindow();
        stage.close();
    }
    private Runnable onTerminerAction;

    // Méthode pour définir l'action à exécuter lorsque le bouton Terminer est cliqué
    public void setOnTerminerAction(Runnable action) {
        this.onTerminerAction = action;
    }

    // Méthode appelée lorsque le bouton "Terminer" est cliqué
    @FXML
    private void onTerminerClicked() {
        if (onTerminerAction != null) {
            onTerminerAction.run(); // Exécute l'action définie
        }
        // Fermer la fenêtre de chargement si nécessaire
        Stage stage = (Stage) btnTerminer.getScene().getWindow();
        stage.close();
    }



    // Méthode pour afficher une alerte
    public void afficherNotification(String message) {
        System.out.println("📢 Affichage d'une notification : " + message);

        if (rootPane == null) {
            System.out.println("❌ Erreur : rootPane est null.");
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

        // Création du JFXDialog
        JFXDialog dialog = new JFXDialog(rootPane, content, JFXDialog.DialogTransition.CENTER);

        closeButton.setOnAction(e -> {
            dialog.close();
            System.out.println("Notification fermée par l'utilisateur.");
        });

        // Affichage du dialogue
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

/*************************/


}
