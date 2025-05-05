 package Front_java;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {    	
    	 Image icon = new Image("/logo_app.jpg"); 
    	 primaryStage.getIcons().add(icon);    	 
	     Login fenetre1 = new Login();
	     fenetre1.showWindow(primaryStage);	     
    }

    public static void main(String[] args) {
        launch(args);
    }
}
