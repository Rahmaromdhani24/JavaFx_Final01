package Front_java.Fenetres;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Dashboard1 {

    private double xOffset = 0;
    private double yOffset = 0;
	 
    public void showDashboard1() {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/FXML/dashboard1.fxml"));
            Parent root = loader.load();
            root.getScene().getStylesheets().add(getClass().getResource("/Front_java/CSS/dashboard1.css").toExternalForm());


            // Permettre de déplacer la fenêtre en cliquant et en glissant
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            root.setOnMouseDragged(event -> {
                Stage stage = (Stage) root.getScene().getWindow();
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });

            // Créer une nouvelle fenêtre (Stage)
            Stage dashboardStage = new Stage();
            dashboardStage.setScene(new Scene(root));
            dashboardStage.initStyle(StageStyle.UNDECORATED); // Supprime le titre et les boutons

            Image icon = new Image("/logo_app.jpg"); 
            dashboardStage.getIcons().add(icon);
        	
            // Afficher la fenêtre
            dashboardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
