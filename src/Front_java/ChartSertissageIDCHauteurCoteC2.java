package Front_java;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ChartSertissageIDCHauteurCoteC2 extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        // Titre principal
        Label title = new Label("Graphique de Sertissage IDC - Hauteur");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Légendes "Côté 1" et "Côté 2" avec couleurs
        HBox legendBox = new HBox(20);
        legendBox.setPadding(new Insets(10, 0, 10, 0));

       
        Scene scene = new Scene(root, 650, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Zones Colorées - Soudure");
        primaryStage.show();
    }

    private Label createLegend(String text, Color color) {
        Label square = new Label("  ");
        square.setStyle("-fx-background-color: " + toHex(color) + "; -fx-border-color: black;");
        square.setMinSize(10, 10);
        Label label = new Label(text);
        label.setTextFill(color);
        HBox hBox = new HBox(5, square, label);
        return new Label(null, hBox);
    }

    public static StackPane createMoyenneChartWithZones(double y1a, double y1b, double y1c, double y1d    ) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(10.5, 11.35, 0.05);

        // Crée un graphique de ligne pour les deux séries
        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setLegendVisible(true); // Afficher la légende
        chart.setAnimated(false);
        chart.setPrefSize(470, 300); // Ajustez la taille du graphique

        // Série Côté 1 (rouge)
        XYChart.Series<String, Number> serie1 = new XYChart.Series<>();
        serie1.setName("Côté 1"); // Légende pour Côté 1
        serie1.getData().add(new XYChart.Data<>("Éch1 (C1)", y1a));  // Éch1 pour Côté 1
        serie1.getData().add(new XYChart.Data<>("Éch2 (C1)", y1b));  // Éch2 pour Côté 1
        serie1.getData().add(new XYChart.Data<>("Éch3 (C1)", y1c));  // Éch3 pour Côté 1
        serie1.getData().add(new XYChart.Data<>("ÉchFin (C1)", y1d));  // ÉchFin pour Côté 1

        // Série Côté 2 (bleu) avec les mêmes catégories x (répétées)
       
        // Ajouter les séries au graphique
        chart.getData().addAll(serie1);

        // Label du graphique
        Label chartTitle = new Label("");
        chartTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        chartTitle.setPadding(new Insets(0, 0, 5, 0));

        // Conteneur pour le titre et le graphique
        VBox container = new VBox(5, chartTitle, chart);
        container.setMaxWidth(chart.getPrefWidth());

        StackPane stackPane = new StackPane(container);

        // Dessiner les zones colorées sur le graphique
        chart.lookup(".chart-plot-background").boundsInParentProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(() -> drawZones(stackPane, chart, yAxis));
        });

        Platform.runLater(() -> {
            setSeriesColor(chart, 0, Color.RED);  // Couleur de la série 1 (Côté 1)
            setSeriesColor(chart, 1, Color.BLUE); // Couleur de la série 2 (Côté 2)
        });

        return stackPane;
    }




    public static void drawZones(StackPane stackPane, LineChart<String, Number> chart, NumberAxis yAxis) {
        double[] limites = {10.5, 10.85, 10.88, 10.97, 11.0, 11.35};
        Color[] couleurs = {
                Color.rgb(255, 0, 0, 0.2),
                Color.rgb(255, 255, 0, 0.2),
                Color.rgb(0, 255, 0, 0.2),
                Color.rgb(255, 255, 0, 0.2),
                Color.rgb(255, 0, 0, 0.2)
        };

        double offset = 10;

        Node plotArea = chart.lookup(".chart-plot-background");
        if (plotArea == null) return;

        Bounds plotBounds = plotArea.getBoundsInParent();
        double width = plotBounds.getWidth();
        double offsetX = plotBounds.getMinX();

        Pane overlay = new Pane();
        overlay.setMouseTransparent(true);

        for (int i = 0; i < limites.length - 1; i++) {
            double y1 = yAxis.getDisplayPosition(limites[i]) + offset;
            double y2 = yAxis.getDisplayPosition(limites[i + 1]) + offset;

            if (Double.isNaN(y1) || Double.isNaN(y2)) continue;

            Rectangle rect = new Rectangle(offsetX, Math.min(y1, y2), width, Math.abs(y2 - y1));
            rect.setFill(couleurs[i]);
            overlay.getChildren().add(rect);
        }

        for (int i = 1; i < limites.length - 1; i++) {
            double y = yAxis.getDisplayPosition(limites[i]) + offset;
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

    private static void setSeriesColor(LineChart<String, Number> chart, int seriesIndex, Color color) {
        Node line = chart.lookup(".default-color" + seriesIndex + ".chart-series-line");
        if (line != null) {
            line.setStyle("-fx-stroke: " + toRgbString(color) + ";");
        }

        for (Node node : chart.lookupAll(".default-color" + seriesIndex + ".chart-line-symbol")) {
            node.setStyle("-fx-background-color: " + toRgbString(color) + ", white;");
        }
    }

    private static String toRgbString(Color c) {
        return String.format("rgba(%d, %d, %d, %f)",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255),
                c.getOpacity());
    }

    private static String toHex(Color c) {
        return String.format("#%02X%02X%02X",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255));
    }
}
