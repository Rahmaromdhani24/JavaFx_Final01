package Front_java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import Front_java.Configuration.AppInformations;
import Front_java.Controller.SoudureResultat;
import Front_java.SertissageIDC.Resultat;
import Front_java.SertissageNormal.ResultatSertissageNormal;
import Front_java.Torsadage.ResultatTorsadage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConfirmationQualiteController {

	   private double xOffset = 0;
	    private double yOffset = 0;

	  @FXML
	    private Label loginLabel;
	    @FXML
	    private Label matriculeLabel;
	    
	    @FXML
	    private Button loginBtn;
	    
	    @FXML
	    private StackPane rootPane; 
	    
	    @FXML
	    private Button closeButton;
	    
	    @FXML
	    private Region leftPane;

	    @FXML
	    private Button minimizeButton;

	    @FXML
	    private Region rightPane;

	    @FXML
	    private TextField matricule;

	    @FXML
	    private void close(ActionEvent event) {
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.close();
	    }
	    @FXML
	    private void scanneMatricule(ActionEvent event) {
	        // Fonction pour scanner le matricule si nécessaire
	    }
	    @FXML
	    private void minimize(ActionEvent event) {
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setIconified(true);
	    }
	    @FXML
	    private void initialize() {
	        // Charger la langue par défaut (Français)
	        // Lorsque l'utilisateur appuie sur Entrée dans le champ matricule
	        matricule.setOnKeyPressed(event -> {
	            if (event.getCode() == KeyCode.ENTER) {
	                loginBtn.fire(); // simule un clic sur le bouton login
	            }
	        });
	        // Permet le déplacement de la fenêtre avec la souris
	        rootPane.setOnMousePressed(event -> {
	            xOffset = event.getSceneX();
	            yOffset = event.getSceneY();
	        });

	        rootPane.setOnMouseDragged(event -> {
	            Stage stage = (Stage) rootPane.getScene().getWindow();
	            stage.setX(event.getScreenX() - xOffset);
	            stage.setY(event.getScreenY() - yOffset);
	        });
	    }
	
	     private void showErrorDialog(String title, String message) {
	            // Charger l'icône d'erreur
	            Image errorIcon = new Image(getClass().getResource("/icone_erreur.png").toExternalForm());
	            ImageView errorImageView = new ImageView(errorIcon);
	            errorImageView.setFitWidth(100);
	            errorImageView.setFitHeight(100);

	            // Centrer l'icône en haut
	            VBox iconBox = new VBox(errorImageView);
	            iconBox.setAlignment(Pos.CENTER);

	            // Message d'erreur au milieu
	            Label messageLabel = new Label(message);
	            messageLabel.setWrapText(false);  // <-- ici on désactive le retour à la ligne
	            messageLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

	            // Titre en bas
	            Label titleLabel = new Label(title);
	            titleLabel.setStyle("-fx-font-size: 19px; -fx-text-alignment: center;");
	            VBox titleBox = new VBox(titleLabel);
	            titleBox.setAlignment(Pos.CENTER);

	            // Regrouper les éléments dans un VBox principal
	            VBox contentBox = new VBox(10, iconBox, messageLabel, titleBox);
	            contentBox.setAlignment(Pos.CENTER);

	            // Création du layout de l'alerte
	            JFXDialogLayout content = new JFXDialogLayout();
	            content.setBody(contentBox);

	            // Bouton de fermeture
	            JFXButton closeButton = new JFXButton("Fermer");
	            closeButton.setStyle("-fx-font-size: 19px; -fx-padding: 10px 20px;  -fx-font-weight: bold;");
	            JFXDialog dialog = new JFXDialog(rootPane, content, JFXDialog.DialogTransition.CENTER);
	            closeButton.setOnAction(e -> dialog.close());

	            content.setActions(closeButton);
	            dialog.show();
	        }
	        
	     @FXML
	     private void handleLogin(ActionEvent event) {
	         String matriculeValue = matricule.getText();

	         if (matriculeValue == null || matriculeValue.trim().isEmpty()) {
	             showErrorDialog("Erreur de saisie", "Veuillez saisir votre matricule.");
	             return;
	         }

	         try {
	             int matriculeInt = Integer.parseInt(matriculeValue);
	             String plantUser = AppInformations.operateurInfo.getPlant();

	             // Appel de la méthode sur le contrôleur principal
	             if (soudureResultController != null) {
	                 boolean estValide = soudureResultController.activerSuivantSiAgentValide(matriculeInt, plantUser);

	                 if (estValide) {
	                     // ✅ Si valide → fermer la fenêtre
	                     Stage stage = (Stage) loginBtn.getScene().getWindow();
	                     stage.close();
	                 } else {
	                     // ❌ Sinon → afficher erreur et ne rien faire
	                     showErrorDialog("Accès refusé", "Matricule non autorisé pour la validation.");
	                 }
	             }

	             if (torsadageResultController != null) {
	                 boolean estValide = torsadageResultController.activerSuivantSiAgentValide(matriculeInt, plantUser);

	                 if (estValide) {
	                     // ✅ Si valide → fermer la fenêtre
	                     Stage stage = (Stage) loginBtn.getScene().getWindow();
	                     stage.close();
	                 } else {
	                     // ❌ Sinon → afficher erreur et ne rien faire
	                     showErrorDialog("Accès refusé", "Matricule non autorisé pour la validation.");
	                 }
	             }
	             if (sertissageNormalResultController != null) {
	                 boolean estValide = sertissageNormalResultController.activerSuivantSiAgentValide(matriculeInt, plantUser);

	                 if (estValide) {
	                     // ✅ Si valide → fermer la fenêtre
	                     Stage stage = (Stage) loginBtn.getScene().getWindow();
	                     stage.close();
	                 } else {
	                     // ❌ Sinon → afficher erreur et ne rien faire
	                     showErrorDialog("Accès refusé", "Matricule non autorisé pour la validation.");
	                 }
	             }
	             if (sertissageIDCResultController != null) {
	                 boolean estValide = sertissageIDCResultController.activerSuivantSiAgentValide(matriculeInt, plantUser);

	                 if (estValide) {
	                     // ✅ Si valide → fermer la fenêtre
	                     Stage stage = (Stage) loginBtn.getScene().getWindow();
	                     stage.close();
	                 } else {
	                     // ❌ Sinon → afficher erreur et ne rien faire
	                     showErrorDialog("Accès refusé", "Matricule non autorisé pour la validation.");
	                 }
	             }
	         } catch (NumberFormatException e) {
	             showErrorDialog("Erreur", "Matricule invalide.");
	         }
	     }



	     public static boolean verifierAgentQualite(int matricule, String plant) {
	    	    try {
	    	        String baseUrl = "http://localhost:8281/operateur/verifier-agent-qualite";
	    	        String urlWithParams = baseUrl + "?matricule=" + matricule + "&plant=" + plant;

	    	        URL url = new URL(urlWithParams);
	    	        HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    	        con.setRequestMethod("GET");

	    	        // 👉 Ajouter le token d'authentification (Authorization header)
	    	        String token = AppInformations.token; // Remplace par la bonne source
	    	        con.setRequestProperty("Authorization", "Bearer " + token);

	    	        int status = con.getResponseCode();
	    	        if (status == 200) {
	    	            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    	            String response = in.readLine();
	    	            in.close();
	    	            con.disconnect();

	    	            return Boolean.parseBoolean(response); // "true" ou "false"
	    	        } else {
	    	            System.err.println("Erreur HTTP: " + status);
	    	        }

	    	    } catch (Exception e) {
	    	        e.printStackTrace();
	    	    }
	    	    return false;
	    	}

	   
	     private SoudureResultat soudureResultController;
	     private ResultatTorsadage torsadageResultController;
	     private ResultatSertissageNormal sertissageNormalResultController;
	     private Resultat sertissageIDCResultController;


	     public void setSoudureResultController(SoudureResultat controller) {
	         this.soudureResultController = controller;
	     }
	     
	     public void setTorsadageResultController(ResultatTorsadage controller) {
	         this.torsadageResultController = controller;
	     }
	     
	     public void setSertissageNormalResultController(ResultatSertissageNormal controller) {
	         this.sertissageNormalResultController = controller;
	     }
	     public void setSertissageIDCResultController(Resultat controller) {
	         this.sertissageIDCResultController = controller;
	     }
}
