package Front_java;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.*;

import Front_java.Configuration.AppInformations;
import Front_java.Configuration.TokenExtractor;
import Front_java.Modeles.OperateurInfo;



public class LoginController {

    private static final String API_URL = "http://localhost:8281/"; 
    
    private Locale currentLocale = new Locale("fr"); // Langue par défaut : Français
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Label loginLabel;
    @FXML
    private Label matriculeLabel;
    
    @FXML
    private Button langBtn;
    
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
    private void minimize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    private void initialize() {
        // Charger la langue par défaut (Français)
        loadLanguage(currentLocale);
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
    @FXML
    private void showLanguageMenu(MouseEvent event) {
        // Création d'un menu contextuel
        ContextMenu languageMenu = new ContextMenu();

        // Création des items du menu en fonction de la langue actuelle
        MenuItem frenchItem = new MenuItem("Français");
        MenuItem englishItem = new MenuItem("English");
        MenuItem arabicItem = new MenuItem("العربية");

        // Ajout des actions sur les items
        frenchItem.setOnAction(e -> changeLanguage(new Locale("fr")));
        englishItem.setOnAction(e -> changeLanguage(new Locale("en")));
        arabicItem.setOnAction(e -> changeLanguage(new Locale("ar")));

        // Configurer les éléments du menu en fonction de la langue actuelle
        if (currentLocale.equals(Locale.FRENCH)) {
            // Si la langue actuelle est le français, ne montrer que l'anglais et l'arabe
            languageMenu.getItems().addAll(englishItem, arabicItem);
        } else if (currentLocale.equals(Locale.ENGLISH)) {
            // Si la langue actuelle est l'anglais, ne montrer que le français et l'arabe
            languageMenu.getItems().addAll(frenchItem, arabicItem);
        } else if (currentLocale.equals(new Locale("ar"))) {
            // Si la langue actuelle est l'arabe, ne montrer que le français et l'anglais
            languageMenu.getItems().addAll(frenchItem, englishItem);
        }

        // Affichage du menu à la position du clic
        languageMenu.show(langBtn, event.getScreenX(), event.getScreenY());
    }
 // Méthode pour changer la langue
    private void changeLanguage(Locale newLocale) {
        currentLocale = newLocale;
        loadLanguage(currentLocale);
    }
       
       private void loadLanguage(Locale locale) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("lang", locale);
            AppInformations.langueSelectionnee=locale+"" ; 
            System.out.println("langue selectionner : "+locale)     ; 
          
            loginLabel.setText(bundle.getString("login.title"));
            matriculeLabel.setText(bundle.getString("login.matricule"));
            matricule.setPromptText(bundle.getString("login.prompt.matricule"));
            loginBtn.setText(bundle.getString("login.button"));
        } catch (MissingResourceException e) {
            System.out.println("❌ Erreur : Fichier de langue introuvable pour " + locale);
            e.printStackTrace();
        }
    }

       @FXML
       private void toggleLanguage() {
           if (currentLocale.getLanguage().equals("fr")) {
               currentLocale = new Locale("en");
           } else if (currentLocale.getLanguage().equals("en")) {
               currentLocale = new Locale("ar");
           } else {
               currentLocale = new Locale("fr");
           }
           loadLanguage(currentLocale); // Charger la nouvelle langue
       }


    @FXML
    private void scanneMatricule(ActionEvent event) {
        // Fonction pour scanner le matricule si nécessaire
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String matriculeValue = matricule.getText();

        if (matriculeValue.isEmpty()) {
            System.out.println("Matricule is required!");
            return;
        }

        // Convertir matricule en entier et appeler l'API pour l'authentification
        try {
            int matriculeInt = Integer.parseInt(matriculeValue);
            authenticateUser(matriculeInt);
        } catch (NumberFormatException e) {
            System.out.println("Invalid matricule format. Please enter a valid integer.");
        }
    }

    private void authenticateUser(int matricule) {

        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = "{\"matricule\":" + matricule + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "auth/loginOperateur"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        response.thenAccept(res -> {
            Platform.runLater(() -> {
                if (res.statusCode() == 200) {
                    String token = TokenExtractor.extractToken(res.body());
                    AppInformations.token = token;

                    getOperateurInfo(matricule, token).thenAccept(userInfo -> {
                        if (userInfo != null) {
                            AppInformations.operateurInfo = userInfo;
                            Platform.runLater(() -> {
                            	if(userInfo.getOperation().equals("Soudure")) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/FXML/dashboard1.fxml"));
                                    Scene dashboardScene = new Scene(loader.load());
                                    dashboardScene.getStylesheets().add(getClass().getResource("/Front_java/CSS/dashboard1.css").toExternalForm());

                                    Stage dashboardStage = new Stage();
                                    dashboardStage.setScene(dashboardScene);
                                    dashboardStage.setMaximized(true);
                                    dashboardStage.initStyle(StageStyle.UNDECORATED);
                                    Image icon = new Image("/logo_app.jpg");
                                    dashboardStage.getIcons().add(icon);
                                    dashboardStage.show();

                                    Stage currentStage = (Stage) loginBtn.getScene().getWindow();
                                    currentStage.close();
                                } catch (IOException e) {
                                    System.out.println("Erreur lors du chargement de la fenêtre dashboard : " + e.getMessage());
                                    showErrorDialog( "Erreur lors du chargement du tableau de bord !" , "Erreur");
                                }
                            } else if(userInfo.getOperation().equals("Torsadage")) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/Torsadage/SelectionInitiale.fxml"));
                                    Scene dashboardScene = new Scene(loader.load());
                                    dashboardScene.getStylesheets().add(getClass().getResource("/Front_java/Torsadage/SelectionInitiale.css").toExternalForm());

                                    Stage dashboardStage = new Stage();
                                    dashboardStage.setScene(dashboardScene);
                                    dashboardStage.setMaximized(true);
                                    dashboardStage.initStyle(StageStyle.UNDECORATED);
                                    Image icon = new Image("/logo_app.jpg");
                                    dashboardStage.getIcons().add(icon);
                                    dashboardStage.show();

                                    Stage currentStage = (Stage) loginBtn.getScene().getWindow();
                                    currentStage.close();
                                } catch (IOException e) {
                                    System.out.println("Erreur lors du chargement de la fenêtre dashboard : " + e.getMessage());
                                    showErrorDialog( "Erreur lors du chargement du tableau de bord !" , "Erreur");
                                }
                            }else if(userInfo.getOperation().equals("Sertissage_Normal")) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/SertissageNormal/SelectionSertissageNormal.fxml"));
                                    Scene dashboardScene = new Scene(loader.load());
                                    dashboardScene.getStylesheets().add(getClass().getResource("/Front_java/SertissageNormal/SelectionSertissageNormal.css").toExternalForm());

                                    Stage dashboardStage = new Stage();
                                    dashboardStage.setScene(dashboardScene);
                                    dashboardStage.setMaximized(true);
                                    dashboardStage.initStyle(StageStyle.UNDECORATED);
                                    Image icon = new Image("/logo_app.jpg");
                                    dashboardStage.getIcons().add(icon);
                                    dashboardStage.show();

                                    Stage currentStage = (Stage) loginBtn.getScene().getWindow();
                                    currentStage.close();
                                } catch (IOException e) {
                                    System.out.println("Erreur lors du chargement de la fenêtre dashboard : " + e.getMessage());
                                    showErrorDialog( "Erreur lors du chargement du tableau de bord !" , "Erreur");
                                }
                            }else if(userInfo.getOperation().equals("Sertissage_IDC")) {
                                try {
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

                                    Stage currentStage = (Stage) loginBtn.getScene().getWindow();
                                    currentStage.close();
                                } catch (IOException e) {
                                    System.out.println("Erreur lors du chargement de la fenêtre dashboard : " + e.getMessage());
                                    showErrorDialog( "Erreur lors du chargement du tableau de bord !" , "Erreur");
                                }
                            }
                            });
                        }
                    }).exceptionally(e -> {
                        Platform.runLater(() -> showErrorDialog("Erreur", "Une erreur réseau est survenue lors de la récupération des informations de l'opérateur !"));
                        e.printStackTrace();
                        return null;
                    });

                } else {
                    showErrorDialog( "Matricule incorrect. Veuillez réessayer !" ,"Erreur");
                }
            });
        }).exceptionally(e -> {
            Platform.runLater(() -> showErrorDialog("Erreur", "Une erreur réseau est survenue !"));
            e.printStackTrace();
            return null;
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
            messageLabel.setWrapText(true);
            messageLabel.setStyle("-fx-font-size: 19px; -fx-font-weight: bold;");

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
        
        public CompletableFuture<OperateurInfo> getOperateurInfo(int matricule, String token) {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8281/operateur/getOperateur/" + matricule))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .GET()
                    .build();

            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            return response.thenApply(res -> {
                if (res.statusCode() == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    try {
                        OperateurInfo operateurInfo = objectMapper.readValue(res.body(), OperateurInfo.class);
                        return operateurInfo;
                    } catch (JsonMappingException e) {
                        e.printStackTrace();
                        throw new RuntimeException("Erreur de mapping JSON : " + e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException("Erreur lors de la récupération des informations de l'opérateur !");
                    }

                } else if (res.statusCode() == 404) {
                    Platform.runLater(() -> {
                    	showErrorDialog( "Le matricule saisi ne correspond pas à un opérateur !" , "Erreur");
                        
                    });
                    return null; // ou tu peux throw si nécessaire
                } else {
                    throw new RuntimeException("Erreur lors de la récupération des informations de l'opérateur ! Code HTTP: " + res.statusCode());
                }
            }).exceptionally(e -> {
                e.printStackTrace();
                return null;
            });
        }



}

