package Front_java.SertissageIDC.loading;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import Front_java.Configuration.AppInformations;
import Front_java.Configuration.SertissageIDCInformations;
import Front_java.Configuration.SoudureInformations;
import Front_java.Configuration.SoudureInformationsCodeB;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;


public class ChoisirCodeControles {

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
    private ComboBox<String> listeCodeControle;

    @FXML
    private void closeFenetre2(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void initialize() {
    	loadCodesControles() ; 
    }
@FXML
private void pauseChargement() {
	
}
 
@FXML
private void terminerChargement(ActionEvent event ) {
	  if (listeCodeControle.getValue() == null) {

	            showErrorDialog("Veuillez sélectionner une valeur de code de contrôle avant de continuer." ,"Champs manquants" );
	    } else {

 
    try {
    	SertissageIDCInformations.sectionFilSelectionner = null ; 
    	SertissageIDCInformations.codeControleSelectionner = null  ; 
    	SertissageIDCInformations.projetSelectionner = null ; 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/SertissageIDC/SelectionSertissageIDC.fxml"));
        Scene dashboardScene = new Scene(loader.load());
        dashboardScene.getStylesheets().add(getClass().getResource("/Front_java/SertissageIDC/SelectionSertissageIDC.css").toExternalForm());

        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setMaximized(true);
        dashboardStage.initStyle(StageStyle.UNDECORATED);
        Image icon = new Image("/logo_app.jpg");
        dashboardStage.getIcons().add(icon);
        dashboardStage.show();

        Stage currentStage = (Stage) btnTerminer.getScene().getWindow();
        currentStage.close();
    } catch (IOException e) {
        System.out.println("Erreur lors du chargement de la fenêtre dashboard : " + e.getMessage());
        showErrorDialog( "Erreur lors du chargement du tableau de bord !" , "Erreur");
    }

}
	    
}



/***** ComboBox Codes des controles ******************/
private List<String> getCodesControlesFromApi() throws Exception {
    String token = AppInformations.token;

    // Créer une requête HTTP avec l'en-tête Authorization
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8281/operations/CodesOperation/Sertissage_IDC"))
            .header("Authorization", "Bearer " + token)  // Ajout de l'en-tête Authorization
            .build();

    HttpClient client = HttpClient.newHttpClient();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    if (response.statusCode() == 200) {
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Convertir la réponse en List<String>
        List<String> codesControles = objectMapper.readValue(response.body(), List.class);
        
        // Afficher le résultat dans la console
        System.out.println("Réponse de l'API : " + codesControles);
        
        return codesControles;
    } else {
        throw new Exception("Erreur lors de la récupération des données: " + response.statusCode());
    }
}
private void loadCodesControles() {
    Task<List<String>> task = new Task<>() {
        @Override
        protected List<String> call() throws Exception {
            return getCodesControlesFromApi();
        }
    };

    task.setOnSucceeded(event -> {
        List<String> codesControles = task.getValue();
        ObservableList<String> observableCodesControles = FXCollections.observableArrayList(codesControles);
        listeCodeControle.setItems(observableCodesControles);

        listeCodeControle.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
            	SoudureInformationsCodeB.codecontroleselectionner = newValue ; 
            	SoudureInformationsCodeB.numCycle= (SoudureInformations.numeroCycle + 1) ; 
                System.out.println("Code de contrôle sélectionné : " + SoudureInformationsCodeB.codecontroleselectionner);
                      }
        });
    });

    task.setOnFailed(event -> {
        Throwable e = task.getException();
        System.out.println("Erreur lors du chargement des codes de contrôle : " + e.getMessage());
    });

    new Thread(task).start();
}
/**********************************/
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
	JFXDialog dialog = new JFXDialog(rootPane, content, JFXDialog.DialogTransition.CENTER);
	closeButton.setOnAction(e -> dialog.close());

	dialog.show();

	Platform.runLater(() -> {
		StackPane overlayPane = (StackPane) dialog.lookup(".jfx-dialog-overlay-pane");
		if (overlayPane != null) {
			overlayPane.setStyle("-fx-background-color: transparent;");
		}
	});
}
}

