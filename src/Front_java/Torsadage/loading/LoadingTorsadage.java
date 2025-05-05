package Front_java.Torsadage.loading;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import Front_java.Configuration.AppInformations;
import Front_java.Configuration.TorsadageInformations;
import Front_java.Torsadage.RemplirTorsadage;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;



public class LoadingTorsadage {

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

    @FXML
    private Label  labelCommandeEncours ; 
    
   @FXML
    private void closeFenetre2(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize() {
    	traduireLabels() ; 
    }
    @FXML
    private void pauseChargement(ActionEvent event) {
        try {
            // Charger le fichier FXML pour la fenêtre de chargement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/Torsadage/loading/RemplirPause.fxml"));
            Scene loadingScene = new Scene(loader.load());

            // Ajouter le fichier CSS
            String cssPath = "/Front_java/Torsadage/loading/RemplirPause.css";
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


    private RemplirTorsadage parentController; // Référence vers la fenêtre mère

    public void setParentController(RemplirTorsadage parentController) {
        this.parentController = parentController;
    }
    
    @FXML
    public void terminerChargement() {
        TorsadageInformations.testTerminitionCommande = 1;

        // Vérifier si le parentController n'est pas null avant d'appeler la mise à jour
        if (parentController != null) {
            parentController.actualiserFenetreMere();
            Locale locale = getLocaleFromString(AppInformations.langueSelectionnee);
            ResourceBundle bundle = ResourceBundle.getBundle("lang", locale);
            String message = bundle.getString("message.quantiteManquante");
            parentController.afficherNotification(message);
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

            btnPause.setText(bundle.getString("pause"));
            btnTerminer.setText(bundle.getString("terminer"));
            labelCommandeEncours.setText(bundle.getString("commandeEnCours"));
  
        } catch (MissingResourceException e) {
            System.out.println("❌ Erreur : Fichier de langue introuvable");
            e.printStackTrace();
        }
    }

    

}

