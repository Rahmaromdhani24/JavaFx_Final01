package Front_java.SertissageNormal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import Front_java.Configuration.AppInformations;
import Front_java.Configuration.SertissageNormaleInformations;
import Front_java.Configuration.SoudureInformations;
import Front_java.Configuration.SoudureInformationsCodeB;
import Front_java.Modeles.OperateurInfo;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class SelectionSertissageNormal{
    @FXML
    private Button btnClose;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnMinimize;

    @FXML
    private Button btnSuivant;

    @FXML
    private Label code1;

    @FXML
    private Label code2;

    @FXML
    private Label code3;

    @FXML
    private Label code4;

    @FXML
    private Label code5;

    @FXML
    private Label code6;

    @FXML
    private Label dateSystem;

    @FXML
    private Label description1;

    @FXML
    private Label description2;

    @FXML
    private Label description3;

    @FXML
    private Label description4;

    @FXML
    private Label description5;

    @FXML
    private Label description6;

    @FXML
    private Pane descriptionCode;

    @FXML
    private Label heureSystem;

    @FXML
    private ComboBox<String> listeCodeControle;

    @FXML
    private ComboBox<String> listeContacts;

    @FXML
    private ComboBox<String> listeProjets;

    @FXML
    private ComboBox<String> listeSectionFil;

    @FXML
    private Label matriculeUser;

    @FXML
    private Label nomPrenomUser;

    @FXML
    private Label operationUser;

    @FXML
    private Label plantUser;

    @FXML
    private Label posteUser;

    @FXML
    private BorderPane rootPane;

    @FXML
    private TextField searchField;

    @FXML
    private Label segementUser;

    @FXML
    private StackPane stackPane;
    
    @FXML
    private ImageView clearImage;
    @FXML
    private Label labelMatricule ; 
    
    @FXML
    private Label labelPlant ; 

    @FXML
    private Label  labelPoste ; 
    
    @FXML
    private Label labelSegment ; 
    
    @FXML
    private Label labelOperation ; 
    
    @FXML
    private Label labelChoisirCodeControle ; 
    
    @FXML
    private Label labelChoisirProjet ; 
 
    @FXML
    private Label labelSection , labelOutil , labelContact ; 
    
    
   
    private final HttpClient httpClient = HttpClient.newHttpClient(); // Client HTTP

    
	public TextField activeTextField;
	
	public TextField getActiveTextField() {
		return activeTextField;
	}
	public void setActiveOnFocus(TextField textField) {
		textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal) {
				activeTextField = textField;
			}
		});
	}
	  @FXML
	    public void handleButtonClick(ActionEvent event) {
	        if (activeTextField != null) {
	            Button clickedButton = (Button) event.getSource();
	            String buttonText = clickedButton.getText();
	            activeTextField.appendText(buttonText);
	        }
	    }

	    // Méthode pour définir le TextField actif
	    public void setActiveTextField(TextField textField) {
	        this.activeTextField = textField;
	    }

	    
    @FXML
    public void initialize() {
    	setActiveOnFocus(searchField);
        afficherInfosOperateur();
        afficherDateSystem(); // Afficher la date du système
        afficherHeureSystem();
        loadCodesControles() ; 
        loadProjets() ; 
        chargerCodesEtDescriptions(); 
    	traduireLabels();

        if (SertissageNormaleInformations.numeroOutils != null) {
            searchField.setText(SertissageNormaleInformations.numeroOutils);
        }
        if (SertissageNormaleInformations.numeroContacts != null) {
            listeContacts.setValue(SertissageNormaleInformations.numeroContacts);
        }
        if (SertissageNormaleInformations.sectionFil != null) {
            listeSectionFil.setValue(SertissageNormaleInformations.sectionFil);
        }

        if (SertissageNormaleInformations.codeControleSelectionner!= null) {
            listeCodeControle.setValue(SertissageNormaleInformations.codeControleSelectionner);
        }

        if (SertissageNormaleInformations.projetSelectionner!= null) {
            listeProjets.setValue(SertissageNormaleInformations.projetSelectionner);
        }
        
        clearImage.setOnMouseClicked(event -> {
			if (activeTextField != null) {
				activeTextField.clear();
			}
		});
        
        // Écouteur pour détecter les changements dans le champ de recherche (saisie du numéro d'outil)
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty()) {
                    // Utilise le token d'authentification pour appeler l'API et récupérer les contacts
                    List<String> contacts = fetchContactsFromAPI(newValue);
                    
                    // Met à jour la liste des contacts dans la ComboBox
                    listeContacts.getItems().setAll(contacts);
                } else {
                    // Si le champ de recherche est vide, vide la liste des contacts
                    listeContacts.getItems().clear();
                }
            });

            // Écouteur pour détecter la sélection d'un contact
         // Ajoute un écouteur à la ComboBox des contacts
            listeContacts.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    // Lorsque l'opérateur sélectionne un contact, récupère les sections de fil
                    String numeroOutil = searchField.getText(); // Le numéro d'outil saisi
                    String numeroContact = newValue; // Le contact sélectionné
                    
                    SertissageNormaleInformations.numeroOutils = numeroOutil;
                    SertissageNormaleInformations.numeroContacts = numeroContact;
                    
                    // Appel de l'API pour récupérer les sections de fil
                    List<String> sectionsFil = fetchSectionsFilFromAPI(numeroOutil, numeroContact);
                    
                 // Ajouter "mm²" à chaque section pour l'affichage
                    List<String> sectionsFilWithUnit = sectionsFil.stream()
                                                                  .map(section -> section + " mm²") 
                                                                  .collect(Collectors.toList());

                    // Vider la ComboBox avant d'ajouter les nouvelles sections
                    listeSectionFil.getItems().clear();
                    listeSectionFil.getItems().addAll(sectionsFilWithUnit);

                    // Ne pas sélectionner de valeur par défaut
                    listeSectionFil.getSelectionModel().clearSelection();
                    listeSectionFil.setValue(null);

                    // Écouteur pour enregistrer la valeur sélectionnée sans "mm²"
                    listeSectionFil.valueProperty().addListener((obs, oldSelection, newSelection) -> {
                        if (newSelection != null) {
                            // Supprimer " mm²" avant d'enregistrer la valeur
                            SertissageNormaleInformations.sectionFil = newSelection.replace(" mm²", "").trim();
                        }
                    });


                }
            });
    }

    
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
    void submit(ActionEvent event) {
        if (listeCodeControle.getValue() == null ||
            listeSectionFil.getValue() == null ||
            listeProjets.getValue() == null ||
            listeContacts.getValue() == null ||
            searchField.getText()==null) {

            showErrorDialog("Veuillez sélectionner une valeur pour chaque champ avant de continuer." ,"Champs manquants" );

        } else {
            try {       
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/SertissageNormal/RemplirSertissageNormal.fxml"));
                Parent dashboard2Root = loader.load();
                dashboard2Root.getStylesheets().add(getClass().getResource("/Front_java/SertissageNormal/RemplirSertissageNormal.css").toExternalForm());

                Scene dashboard2Scene = new Scene(dashboard2Root);

                Stage newStage = new Stage();
                newStage.initStyle(StageStyle.UNDECORATED);
                newStage.setScene(dashboard2Scene);
                newStage.setMaximized(true);
                newStage.getIcons().add(new Image("/logo_app.jpg"));
                newStage.show();

                // Fermer l'ancien stage
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();
                showErrorDialog("Erreur lors du chargement du tableau de bord." ,"Erreur");
            }
        }
    }

    @FXML
    void logout(ActionEvent event) {

    	AppInformations.reset();
    	SertissageNormaleInformations.reset();


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front_java/Login.fxml"));
            Scene loginScene = new Scene(loader.load());
            loginScene.getStylesheets().add(getClass().getResource("/Front_java/loginDesign.css").toExternalForm());

            Stage loginStage = new Stage();
            loginStage.initStyle(StageStyle.UNDECORATED); // Placer cette ligne avant show()
            loginStage.setResizable(false); // Désactiver le redimensionnement
            Image icon = new Image("/logo_app.jpg");
            loginStage.getIcons().add(icon);
            
            loginStage.setScene(loginScene);
            loginStage.show();
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la fenêtre login : " + e.getMessage());
        }
    }

    
    public void afficherInfosOperateur() {
        // Vérifier si les informations de l'opérateur existent
        if (AppInformations.operateurInfo != null) {
            OperateurInfo operateurInfo = AppInformations.operateurInfo;

            // Mettre à jour les labels avec les informations de l'opérateur
            matriculeUser.setText(String.valueOf(operateurInfo.getMatricule())); 
            nomPrenomUser.setText(operateurInfo.getNom() + " " + operateurInfo.getPrenom());
            operationUser.setText("Sertissage ");
            plantUser.setText(operateurInfo.getPlant());
            posteUser.setText(operateurInfo.getPoste());
            segementUser.setText(operateurInfo.getSegment());
        } else {
            System.out.println("Aucune information d'opérateur disponible.");
        }
    }

    private void afficherDateSystem() {
        LocalDate dateActuelle = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        dateSystem.setText(dateActuelle.format(formatter));
    }
    private void afficherHeureSystem() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalTime heureActuelle = LocalTime.now();
            heureSystem.setText(heureActuelle.format(formatter));
        }));

        timeline.setCycleCount(Timeline.INDEFINITE); // Répéter indéfiniment
        timeline.play(); // Démarrer l'animation
    }
    
  
  
    /***** ComboBox Codes des controles ******************/
    private List<String> getCodesControlesFromApi() throws Exception {
        String token = AppInformations.token;

        // Créer une requête HTTP avec l'en-tête Authorization
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8281/operations/CodesOperation/Sertissage_Normal"))
                .header("Authorization", "Bearer " + token)  // Ajout de l'en-tête Authorization
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper objectMapper = new ObjectMapper();
            
            // Convertir la réponse en List<String>
            List<String> codesControles = objectMapper.readValue(response.body(), List.class);
                       
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
                    SertissageNormaleInformations.codeControleSelectionner = newValue;
                }
            });
        });

        task.setOnFailed(event -> {
            Throwable e = task.getException();
            System.out.println("Erreur lors du chargement des codes de contrôle : " + e.getMessage());
        });

        new Thread(task).start();
    }

    /***** ComboBox projets  ******************/
    
    private List<String> getProjetsPlantsFromApi() throws Exception {
        String token = AppInformations.token;

        // Créer une requête HTTP avec l'en-tête Authorization
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8281/operateur/projets/" + AppInformations.operateurInfo.getPlant()))
                .header("Authorization", "Bearer " + token)  // Ajout de l'en-tête Authorization
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String responseBody = response.body();
                        
            // Utilisation de ObjectMapper pour mapper la réponse JSON
            ObjectMapper objectMapper = new ObjectMapper();
            
            // Convertir la réponse en une liste de String (noms des projets)
            List<String> projets = objectMapper.readValue(responseBody, new TypeReference<List<String>>(){});
            
            return projets;
        } else {
            throw new Exception("Erreur lors de la récupération des données: " + response.statusCode());
        }
    }
    private void loadProjets() {
        // Utilisation d'un Task pour effectuer la requête API de manière asynchrone
        Task<List<String>> task = new Task<>() {
            @Override
            protected List<String> call() throws Exception {
                return getProjetsPlantsFromApi();  // Appel de la méthode pour récupérer les projets
            }
        };

        // Quand la tâche réussit, peupler le ComboBox avec les projets récupérés
        task.setOnSucceeded(event -> {
            List<String> projets = task.getValue();  // Récupérer la liste des projets
            ObservableList<String> observableProjets = FXCollections.observableArrayList(projets);  // Convertir en ObservableList
            listeProjets.setItems(observableProjets);  // Peupler le ComboBox

            // Ajouter un listener pour récupérer l'élément sélectionné
            listeProjets.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
                if (newValue != null) {
                    SertissageNormaleInformations.projetSelectionner = newValue;
                }
            });
        });

        // Si la tâche échoue, afficher l'erreur
        task.setOnFailed(event -> {
            Throwable e = task.getException();
            System.out.println("Erreur lors du chargement des projets : " + e.getMessage());
        });

        // Lancer la tâche dans un thread séparé
        new Thread(task).start();
    }

  
/**************************************** Description Code ******************************************************/
    private void chargerCodesEtDescriptions() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String token = AppInformations.token;

                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8281/operations/details/Sertissage_Normal"))
                    .header("Authorization", "Bearer " + token)
                    .build();

                HttpClient client = HttpClient.newHttpClient();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    ObjectMapper objectMapper = new ObjectMapper();

                    // Récupération sous forme de Map
                    Map<String, String> codesDescriptions = objectMapper.readValue(
                        response.body(),
                        new com.fasterxml.jackson.core.type.TypeReference<Map<String, String>>() {}
                    );

                    Platform.runLater(() -> {
                        List<Label> descriptionsLabels = List.of(description1, description2, description3, description4, description5, description6);

                        int index = 0;
                        for (Map.Entry<String, String> entry : codesDescriptions.entrySet()) {
                            if (index >= descriptionsLabels.size()) break; // Limiter à 6

                            descriptionsLabels.get(index).setText(entry.getKey() + " = " + entry.getValue());
                            index++;
                        }

                        // Vider les labels restants si nécessaire
                        for (int i = index; i < descriptionsLabels.size(); i++) {
                            descriptionsLabels.get(i).setText("");
                        }
                    });
                } else {
                    System.out.println("Erreur API : " + response.statusCode());
                }
                return null;
            }
        };

        new Thread(task).start();
    }
    private void showErrorDialog(String title, String message) {
        Image errorIcon = new Image(getClass().getResource("/icone_erreur.png").toExternalForm());
        ImageView errorImageView = new ImageView(errorIcon);
        errorImageView.setFitWidth(100);
        errorImageView.setFitHeight(100);

        VBox iconBox = new VBox(errorImageView);
        iconBox.setAlignment(Pos.CENTER);

        Label messageLabel = new Label(message);
        messageLabel.setWrapText(true);
        messageLabel.setStyle("-fx-font-size: 19px; -fx-font-weight: bold; -fx-text-alignment: center;");

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
        closeButton.setStyle("-fx-font-size: 19px; -fx-padding: 10px 20px;");
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

/*************** recuperer liste des contacts *************************/

    public List<String> fetchContactsFromAPI(String toolNumber) {
        try {
            // Crée la requête HTTP avec le paramètre "numeroOutil" et ajoute l'en-tête Authorization avec le token
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8281/operations/SertissageNormal/contacts?numeroOutil=" + toolNumber)) // Remplace par ton API
                    .header("Authorization", "Bearer " + AppInformations.token) // Ajouter le token d'authentification
                    .GET()
                    .build();

            // Envoie la requête et récupère la réponse
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Vérifie si la réponse est réussie (code 200 OK)
            if (response.statusCode() == 200) {
                // Convertit le corps de la réponse JSON en liste
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.body(), List.class); // Convertir JSON en liste
            } else {
                System.out.println("Erreur de l'API: " + response.statusCode());
                return new ArrayList<>(); // Retourne une liste vide si l'API échoue
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Retourne une liste vide en cas d'erreur
        }
    }
    
    /********************************  recuperer liste des sections des fils *********************/
    // Méthode pour récupérer les sections de fil à partir de l'API
    public List<String> fetchSectionsFilFromAPI(String toolNumber, String contactNumber) {
        try {
            // Crée la requête HTTP avec les paramètres "numeroOutil" et "numeroContact" et ajoute l'en-tête Authorization avec le token
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8281/operations/SertissageNormal/sections?numeroOutil=" + toolNumber + "&numeroContact=" + contactNumber)) // Remplacer par l'URL de l'API
                    .header("Authorization", "Bearer " + AppInformations.token) // Ajouter le token d'authentification
                    .GET()
                    .build();

            // Envoie la requête et récupère la réponse
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Vérifie si la réponse est réussie (code 200 OK)
            if (response.statusCode() == 200) {
                // Convertit le corps de la réponse JSON en liste de sections de fil
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(response.body(), List.class); // Convertir JSON en liste
            } else {
                System.out.println("Erreur de l'API: " + response.statusCode());
                return new ArrayList<>(); // Retourne une liste vide si l'API échoue
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Retourne une liste vide en cas d'erreur
        }
    }
    /*********************************** Traduction ************************************************************/
    private Locale getLocaleFromString(String langue) {
        if (langue == null) return new Locale("fr"); // langue par défaut
        if (langue.contains("ar")) return new Locale("ar");
        if (langue.contains("en")) return new Locale("en");
        return new Locale("fr");
    }
    private void traduireLabels() {
        try {
            Locale locale = getLocaleFromString(AppInformations.langueSelectionnee);
            ResourceBundle bundle = ResourceBundle.getBundle("lang", locale);
            System.out.println("🌍 langue sélectionnée dans Dashboard1 : " + locale.getLanguage());

            labelMatricule.setText(bundle.getString("label.matricule"));
            labelPlant.setText(bundle.getString("label.plant"));
            labelSegment.setText(bundle.getString("label.segment"));
            labelPoste.setText(bundle.getString("label.poste"));
            labelOperation.setText(bundle.getString("label.operation"));
            labelChoisirCodeControle.setText(bundle.getString("label.chooseControlCode"));
            labelChoisirProjet.setText(bundle.getString("label.chooseProject"));
            labelOutil.setText(bundle.getString("label.chooseOutil"));
            labelContact.setText(bundle.getString("label.chooseContact"));
            btnSuivant.setText(bundle.getString("boutonSuivant"));
            description1.setText(bundle.getString("sertN.A"));
            description2.setText(bundle.getString("sertN.S"));
            description3.setText(bundle.getString("sertN.l"));
            description4.setText(bundle.getString("sertN.R"));
            description5.setText(bundle.getString("sertN.w"));
            description6.setText(bundle.getString("sertN.M"));
          


        } catch (MissingResourceException e) {
            System.out.println("❌ Erreur : Fichier de langue introuvable");
            e.printStackTrace();
        }
    }



}