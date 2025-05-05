package Front_java;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ChartWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Création des axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(30); // Plage des valeurs sur l'axe Y

        // Masquer les axes
        xAxis.setOpacity(0); // Masquer l'axe X
        yAxis.setOpacity(0); // Masquer l'axe Y

        // Création du StackedBarChart
        StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<>(xAxis, yAxis);
        stackedBarChart.setTitle("Histogramme Empilé");

        // Création des séries avec intervalles définis
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Rouge");
        series1.getData().add(new XYChart.Data<>("A", 10)); // Zone rouge de 0 à 10

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Jaune");
        series2.getData().add(new XYChart.Data<>("A", 10)); // Zone jaune de 10 à 20

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("Vert");
        series3.getData().add(new XYChart.Data<>("A", 10)); // Zone verte de 20 à 30

        // Ajout des séries au graphique
        stackedBarChart.getData().addAll(series1, series2, series3);

        // Créer un Pane pour superposer le point sur le graphique
        Pane overlayPane = new Pane();

        // Ajouter un point au milieu de l'axe X, dans la zone appropriée
        Platform.runLater(() -> {
            addPointToChart(stackedBarChart, xAxis, yAxis, overlayPane, 22); // Exemple de point pour la zone rouge (0-10)
        });

        // Créer le StackPane pour empiler le graphique et ajouter les éléments
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(stackedBarChart, overlayPane);

        // Création de la scène
        Scene scene = new Scene(stackPane, 600, 400);

        // Affichage de la scène
        primaryStage.setTitle("Histogramme Empilé avec Point Noir");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Méthode pour ajouter un point à la chart à la position donnée
    private void addPointToChart(StackedBarChart<String, Number> stackedBarChart, CategoryAxis xAxis, NumberAxis yAxis, Pane overlayPane, double yValue) {
        // Calculer la position Y en fonction de la valeur
        double yPosition = getYPositionForPoint(yValue, yAxis);

        // Position X centrée sur la catégorie "A"
        double xPosition = xAxis.getDisplayPosition("A");

        // Créer un point noir
        Circle point = new Circle(5);  // Rayon du point = 5
        point.setFill(Color.BLACK);  // Couleur noire

        // Placer le point à la position calculée
        point.setCenterX(xPosition);
        point.setCenterY(yPosition);

        // Ajouter le point au Pane
        overlayPane.getChildren().add(point);
    }

    // Méthode pour obtenir la position Y en fonction de la valeur (en tenant compte des intervalles)
    private double getYPositionForPoint(double value, NumberAxis yAxis) {
        double cumulativeHeight = 0;

        // Zone rouge de 0 à 10
        if (value >= 0 && value <= 10) {
            cumulativeHeight = value;  // Zone rouge
        }
        // Zone jaune de 10 à 20
        else if (value > 10 && value <= 20) {
            cumulativeHeight = 10 + (value - 10);  // Zone jaune
        }
        // Zone verte de 20 à 30
        else if (value > 20 && value <= 30) {
            cumulativeHeight = 20 + (value - 20);  // Zone verte
        }

        // Retourner la position Y calculée pour la valeur sur l'axe Y
        return yAxis.getDisplayPosition(cumulativeHeight);
    }

    public static void main(String[] args) {
        launch(args);
    }


}
