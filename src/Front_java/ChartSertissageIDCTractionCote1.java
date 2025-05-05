package Front_java;

import javafx.application.Application;
import javafx.application.Platform;
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

import java.util.Arrays;
import java.util.List;

public class ChartSertissageIDCTractionCote1 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        // Exemple de données
        List<Double> valeurs = Arrays.asList(45.0, 52.0, 63.0, 70.0, 80.0, 90.0, 100.0, 105.0);
        StackPane chartPane = createMoyenneChartWithZones();
        root.getChildren().add(chartPane);

        Scene scene = new Scene(root, 600, 450);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Zones Colorées - Traction Cote 1");
        primaryStage.show();

        // ➤ Attendre l'affichage complet avant de dessiner les zones colorées
        Platform.runLater(() -> {
            LineChart<String, Number> chart = (LineChart<String, Number>) chartPane.getChildren().get(0);
            NumberAxis yAxis = (NumberAxis) chart.getYAxis();
            drawZones(chartPane, chart, yAxis);
        });
    }

    public static StackPane createMoyenneChartWithZones() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(40, 110, 5);  // Limites verticales de 40 à 110 avec un pas de 5

        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        chart.setPrefSize(400, 150);

        StackPane stackPane = new StackPane(chart);

        chart.lookup(".chart-plot-background").boundsInParentProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(() -> drawZones(stackPane, chart, yAxis));
        });

        return stackPane;
    }
    public static void drawZones(StackPane stackPane, LineChart<String, Number> chart, NumberAxis yAxis) {
        // Limites des zones colorées
        double[] limites = {40, 50, 60, 110};  // Rouge: 40 à 50, Jaune: 50 à 60, Vert: 60 à 110
        Color[] couleurs = {
            Color.rgb(255, 0, 0, 0.2),   // Rouge pour 40-50
            Color.rgb(255, 255, 0, 0.2), // Jaune pour 50-60
            Color.rgb(0, 255, 0, 0.2)    // Vert pour 60-110
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
}