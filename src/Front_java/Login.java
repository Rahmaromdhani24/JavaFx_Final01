package Front_java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import java.io.IOException;

public class Login {

    private double x = 0;
    private double y = 0;

    public void showWindow(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml")); 
            Parent root = loader.load();
            
            Scene scene = new Scene(root);

            // Ajouter des événements de souris pour permettre de déplacer la fenêtre
            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                if (!stage.isIconified()) {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(0.8);
                }
            });

            root.setOnMouseReleased((MouseEvent event) -> {
                stage.setOpacity(1);
            });

            // Initialiser la fenêtre
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Connexion Opérateur");
            stage.setScene(scene);
            //stage.setMaximized(true);
            //stage.setMinWidth(600);
           // stage.setMinHeight(400);
            stage.show();

        } catch (IOException e) {
            // Si une erreur survient lors du chargement du fichier FXML
            System.err.println("Erreur de chargement du fichier FXML : " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Capturer toute autre exception
            System.err.println("Erreur inattendue : " + e.getMessage());
            e.printStackTrace();
        }
    }

}