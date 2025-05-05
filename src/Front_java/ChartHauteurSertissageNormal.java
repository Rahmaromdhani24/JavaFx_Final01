package Front_java;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Front_java.Configuration.AppInformations;
import Front_java.Configuration.SertissageNormalReponse;
import Front_java.Configuration.SertissageNormaleInformations;
import Front_java.Configuration.SoudureReponse;
import Front_java.Controller.Dashboard2Controller;
import Front_java.SertissageNormal.ResultatSertissageNormal;

public class ChartHauteurSertissageNormal extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        // Charge les données et affiche le chart une fois terminé
        //chargerSertissagesParPdekEtPage(root);


        Scene scene = new Scene(root, 600, 450);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Zones Colorées - Hauteur Sertissage Normal");
        primaryStage.show();

      
    }

    public static StackPane createChartWithZones(double y1a, double y1b, double y1c, double y1d) {
        CategoryAxis xAxis = new CategoryAxis();
        double rouge1Min = SertissageNormaleInformations.labelHauteurSertissage - 0.07;
        double rouge2Max = SertissageNormaleInformations.labelHauteurSertissage + 0.07;

        NumberAxis yAxis = new NumberAxis(rouge1Min, rouge2Max, 0.01);
        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setLegendVisible(false); // Masque toute la légende
        chart.setAnimated(false);
        chart.setPrefSize(420, 400);

        // Série fantôme pour relier les points
        XYChart.Series<String, Number> seriesLineConnector = new XYChart.Series<>();
        seriesLineConnector.setName(""); 
        seriesLineConnector.getData().add(new XYChart.Data<>("Échantillon 1", y1a));
        seriesLineConnector.getData().add(new XYChart.Data<>("Échantillon 2", y1b));
        seriesLineConnector.getData().add(new XYChart.Data<>("Échantillon 3", y1c));
        seriesLineConnector.getData().add(new XYChart.Data<>("Fin Cde", y1d));

        // Séries de points individuels (sans nom)
        XYChart.Series<String, Number> seriesHauteurEch1 = new XYChart.Series<>();
        seriesHauteurEch1.setName("");
        seriesHauteurEch1.getData().add(new XYChart.Data<>("Échantillon 1", y1a));

        XYChart.Series<String, Number> seriesHauteurEch2 = new XYChart.Series<>();
        seriesHauteurEch2.setName("");
        seriesHauteurEch2.getData().add(new XYChart.Data<>("Échantillon 2", y1b));

        XYChart.Series<String, Number> seriesHauteurEch3 = new XYChart.Series<>();
        seriesHauteurEch3.setName("");
        seriesHauteurEch3.getData().add(new XYChart.Data<>("Échantillon 3", y1c));

        XYChart.Series<String, Number> seriesHauteurEchFin = new XYChart.Series<>();
        seriesHauteurEchFin.setName("");
        seriesHauteurEchFin.getData().add(new XYChart.Data<>("Fin Cde", y1d));

        // Ajout des séries au graphique
        chart.getData().addAll(
            seriesLineConnector,
            seriesHauteurEch1,
            seriesHauteurEch2,
            seriesHauteurEch3,
            seriesHauteurEchFin
        );

        // Appliquer la couleur bleue à tous les éléments une fois la scène chargée
        chart.applyCss();
        chart.getData().forEach(series -> {
            series.getNode().setStyle("-fx-stroke: blue;");

            for (XYChart.Data<String, Number> data : series.getData()) {
                if (data.getNode() != null) {
                    data.getNode().setStyle("-fx-background-color: blue, blue;");
                }
            }
        });

        StackPane stackPane = new StackPane(chart);

        chart.lookup(".chart-plot-background").boundsInParentProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(() -> drawZones(stackPane, chart, yAxis));
        });

        return stackPane;
    }



    public static void drawZones(StackPane stackPane, LineChart<String, Number> chart, NumberAxis yAxis) {
        // Limites des zones colorées
    	  double  rouge1Min =  SertissageNormaleInformations.labelHauteurSertissage - 0.07 ; //1.03
          double  rouge1Max =  SertissageNormaleInformations.labelHauteurSertissage - 0.05 ; //1.05
          
          double  jaune1Min =  SertissageNormaleInformations.labelHauteurSertissage - 0.05 ; //1.05
          double  jaune1Max =  jaune1Min + 0.03 ; //1.08
          
          double  vertMin =  jaune1Max ; //1.08
          double  vertMax =  SertissageNormaleInformations.labelHauteurSertissage + 0.02 ; //1.12
          
          double  jaune2Min =  vertMax ; //1.12
          double  jaune2Max =  SertissageNormaleInformations.labelHauteurSertissage + 0.05 ; //1.15
          
          double  rouge2Min =  jaune2Max; //1.15
          double  rouge2Max =  SertissageNormaleInformations.labelHauteurSertissage + 0.07 ; //1.17
          
        double[] limites = {rouge1Min, rouge1Max, jaune1Max,vertMax, jaune2Max, rouge2Max};  // Rouge: 1.00 à 1.05, Jaune: 1.05 à 1.08, Vert: 1.08 à 1.12, Jaune: 1.12 à 1.15, Rouge: 1.15 à 2.00
        Color[] couleurs = {
            Color.rgb(255, 0, 0, 0.2),   // Rouge pour 1.00-1.05
            Color.rgb(255, 255, 0, 0.2), // Jaune pour 1.05-1.08
            Color.rgb(0, 255, 0, 0.2),   // Vert pour 1.08-1.12
            Color.rgb(255, 255, 0, 0.2), // Jaune pour 1.12-1.15
            Color.rgb(255, 0, 0, 0.2)    // Rouge pour 1.15-2.00
        };

        double offset = 10;  // Pas de décalage vers le bas

        Node plotArea = chart.lookup(".chart-plot-background");
        if (plotArea == null) return;

        Bounds plotBounds = plotArea.getBoundsInParent();
        double width = plotBounds.getWidth();
        double offsetX = plotBounds.getMinX();

        Pane overlay = new Pane();
        overlay.setMouseTransparent(true);

        // Dessiner les zones colorées
        for (int i = 0; i < limites.length - 1; i++) {
            double y1 = yAxis.getDisplayPosition(limites[i]) + offset;  // Ajouter un offset
            double y2 = yAxis.getDisplayPosition(limites[i + 1]) + offset;  // Ajouter un offset

            if (Double.isNaN(y1) || Double.isNaN(y2)) continue;

            Rectangle rect = new Rectangle(offsetX, Math.min(y1, y2), width, Math.abs(y2 - y1));
            rect.setFill(couleurs[i]);
            overlay.getChildren().add(rect);
        }

        // Ajouter des lignes de seuil
        for (int i = 1; i < limites.length - 1; i++) {
            double y = yAxis.getDisplayPosition(limites[i]) + offset;  // Ajouter un offset
            if (!Double.isNaN(y)) {
                Line line = new Line(offsetX, y, offsetX + width, y);
                line.setStroke(Color.GRAY);
                line.setStrokeWidth(1);
                overlay.getChildren().add(line);
            }
        }

        Node parent = plotArea.getParent();
        if (parent instanceof Pane pane) {
            pane.getChildren().removeIf(n -> "zone-overlay".equals(n.getUserData()));
            overlay.setUserData("zone-overlay");
            pane.getChildren().add(overlay);
        }
    }
    /*********************************************************************************************************************/

  /*  public static void chargerSertissagesParPdekEtPage(VBox root) {
        Task<List<SertissageNormalReponse>> task = new Task<>() {
            @Override
            protected List<SertissageNormalReponse> call() throws Exception {
                String token = AppInformations.token;
                String url = "http://localhost:8281/operations/SertissageNormal/sertissages-par-pdek-et-page"
                           + "?pdekId=" + ResultatSertissageNormal.idPdekSertissageNormalGlobale
                           + "&pageNumber=" + ResultatSertissageNormal.numPageSertissageNormalGlobale ;

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Authorization", "Bearer " + token)
                        .GET()
                        .build();

                HttpClient client = HttpClient.newHttpClient();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(response.body(), new TypeReference<List<SertissageNormalReponse>>() {});
                } else {
                    throw new Exception("Erreur " + response.statusCode() + " : " + response.body());
                }
            }
        };

        task.setOnSucceeded(event -> {
            List<SertissageNormalReponse> donnees = task.getValue();
            StackPane chart = createChartWithZones(donnees);
            root.getChildren().add(chart);
        });

        task.setOnFailed(event -> {
            System.out.println("Impossible de charger les soudures : " + task.getException().getMessage());
        });

        new Thread(task).start();
    }*/

}

