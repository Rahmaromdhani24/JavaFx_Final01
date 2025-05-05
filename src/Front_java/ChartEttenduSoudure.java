package Front_java;

import Front_java.Configuration.SoudureInformations;
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

public class ChartEttenduSoudure extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        // Charge les données et affiche le chart une fois terminé
      //  chargerSouduresParPdekEtPage(root);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Zone colorée dans LineChart");
        primaryStage.show();
    }

    public static StackPane createEtenduChartWithZones(String numCycle  , int r) {
    	
        double pas = 5.66;
        int etendu =SoudureInformations.EtenduValueMax ; 
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(0, etendu+1, pas);  // Intervalle de y de 0 à 2.3 avec un pas de 0.3

        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        chart.setPrefSize(460, 200);
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Remplir les données depuis la liste
        series.getData().add(new XYChart.Data<>(numCycle, r));
     

        chart.getData().add(series);

        StackPane stackPane = new StackPane(chart);

        chart.lookup(".chart-plot-background").boundsInParentProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(() -> drawZones(stackPane, chart, yAxis));
        });

        return stackPane;
    }

    public static void drawZones(StackPane stackPane, LineChart<String, Number> chart, NumberAxis yAxis) {
    	int etendu =SoudureInformations.EtenduValueMax ;  
        Node plotBackground = chart.lookup(".chart-plot-background");
        if (plotBackground == null) return;

        Bounds plotBounds = plotBackground.getBoundsInParent();
        double chartWidth = plotBounds.getWidth(); // La largeur du graphique
        double chartHeight = plotBounds.getHeight(); // La hauteur du graphique

        // Position Y des zones en fonction de l'axe Y
        double y03 = yAxis.getDisplayPosition(0.3);
        double y06 = yAxis.getDisplayPosition(0.6);
        double y09 = yAxis.getDisplayPosition(0.9);
        double y12 = yAxis.getDisplayPosition(1.2);
        double y15 = yAxis.getDisplayPosition(1.5);
        double y18 = yAxis.getDisplayPosition(1.8);
        double y21 = yAxis.getDisplayPosition(2.1);
        double y24 = yAxis.getDisplayPosition(2.4);

        // S'assurer que les positions sont valides
        if (Double.isNaN(y03) || Double.isNaN(y06) || Double.isNaN(y09)
                || Double.isNaN(y12) || Double.isNaN(y15) || Double.isNaN(y18)
                || Double.isNaN(y21) || Double.isNaN(y24)) {
            return;
        }

        // Calculer la position relative à la zone de la courbe (en prenant en compte la hauteur totale du graphique)
        double yAxisPosition = plotBounds.getMinY();

        // Décalage horizontal en pixels (ajuster la valeur pour déplacer plus ou moins à droite)
        double horizontalOffset = 40; // Décalage de 40 pixels vers la droite

        // Créer les rectangles de chaque zone avec un décalage horizontal
        Rectangle zone1 = new Rectangle(horizontalOffset, yAxisPosition + y03, chartWidth, y06 - y03);
        zone1.setFill(Color.rgb(255, 0, 0, 0.2));

        Rectangle zone2 = new Rectangle(horizontalOffset, yAxisPosition + y06, chartWidth, y09 - y06);
        zone2.setFill(Color.rgb(255, 255, 0, 0.2));

        Rectangle zone3 = new Rectangle(horizontalOffset, yAxisPosition + y09, chartWidth, y12 - y09);
        zone3.setFill(Color.rgb(0, 255, 0, 0.2));

        Rectangle zone4 = new Rectangle(horizontalOffset, yAxisPosition + y12, chartWidth, y15 - y12);
        zone4.setFill(Color.rgb(255, 255, 0, 0.2));

        Rectangle zone5 = new Rectangle(horizontalOffset, yAxisPosition + y15, chartWidth, y18 - y15);
        zone5.setFill(Color.rgb(255, 0, 0, 0.2));

        Rectangle zone6 = new Rectangle(horizontalOffset, yAxisPosition + y18, chartWidth, y21 - y18);
        zone6.setFill(Color.rgb(255, 255, 0, 0.2));

        Rectangle zone7 = new Rectangle(horizontalOffset, yAxisPosition + y21, chartWidth, y24 - y21);
        zone7.setFill(Color.rgb(0, 255, 0, 0.2));

        // Créer la ligne rouge à y = etendu max
        Line redLine = new Line(horizontalOffset, yAxisPosition + yAxis.getDisplayPosition(etendu), horizontalOffset + chartWidth, yAxisPosition + yAxis.getDisplayPosition(etendu));
        redLine.setStroke(Color.RED);
        redLine.setStrokeWidth(2);

        // Empiler les rectangles dans une Pane transparente
        Pane overlay = new Pane(zone1, zone2, zone3, zone4, zone5, zone6, zone7, redLine);
        overlay.setMouseTransparent(true);

        // Ajouter la pane au graphique
        Node parent = plotBackground.getParent();
        if (parent instanceof Pane pane) {
            pane.getChildren().removeIf(n -> n.getUserData() != null && n.getUserData().equals("zone-overlay"));
            overlay.setUserData("zone-overlay");
            pane.getChildren().add(overlay);
        }
    }
/***************************************************************************************************************************/
  /*  public static void chargerSouduresParPdekEtPage(VBox root) {
        Task<List<SoudureReponse>> task = new Task<>() {
            @Override
            protected List<SoudureReponse> call() throws Exception {
                String token = AppInformations.token;
                String url = "http://localhost:8281/operations/soudure/soudures-par-pdek-et-page"
                           + "?pdekId=" + SoudureResult.getPdekId()
                           + "&pageNumber=" + SoudureResult.getPageNumber();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Authorization", "Bearer " + token)
                        .GET()
                        .build();

                HttpClient client = HttpClient.newHttpClient();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(response.body(), new TypeReference<List<SoudureReponse>>() {});
                } else {
                    throw new Exception("Erreur " + response.statusCode() + " : " + response.body());
                }
            }
        };

        task.setOnSucceeded(event -> {
            List<SoudureReponse> donnees = task.getValue();
            StackPane chart = createEtenduChartWithZones(donnees);
            root.getChildren().add(chart);
        });

        task.setOnFailed(event -> {
            System.out.println("Impossible de charger les soudures : " + task.getException().getMessage());
        });

        new Thread(task).start();
    }*/
}
