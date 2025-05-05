package Front_java;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import Front_java.Configuration.AppInformations;
import Front_java.Configuration.SoudureInformations;
import Front_java.Configuration.SoudureReponse;
import Front_java.Controller.Dashboard2Controller;
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

public class ChartMoyenneSoudure extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        // Charge les données et affiche le chart une fois terminé
      //  chargerSouduresParPdekEtPage(root);

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Moyenne Soudure avec Zones Colorées");
        primaryStage.show();
    }


    public static StackPane createMoyenneChartWithZones(String numCycle , int moyenne) {
        int min = SoudureInformations.minPelage - 4;
        int maxVert = SoudureInformations.MoyenneVertMax;
        int rouge = SoudureInformations.minPelage;
        int minVert = SoudureInformations.MoyenneVertMin;

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(min, maxVert, 4);

        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        chart.setPrefSize(460, 280);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // Remplir les données depuis la liste
       // for (SoudureReponse s : soudures) {
        series.getData().add(new XYChart.Data<>(numCycle, moyenne));
       // }

        chart.getData().add(series);

        StackPane stackPane = new StackPane(chart);

        chart.lookup(".chart-plot-background").boundsInParentProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(() -> drawZones(stackPane, chart, yAxis));
        });

        return stackPane;
    }


    public static void drawZones(StackPane stackPane, LineChart<String, Number> chart, NumberAxis yAxis) {
    	 int min = SoudureInformations.minPelage -4;
   	     int maxVert = SoudureInformations.MoyenneVertMax;
   	     int rouge = SoudureInformations.minPelage ; 
         int minVert = SoudureInformations.MoyenneVertMin ; 
         
        Node plotBackground = chart.lookup(".chart-plot-background");
        if (plotBackground == null) return;

        Bounds plotBounds = plotBackground.getBoundsInParent();
        double chartWidth = plotBounds.getWidth();
        double chartHeight = plotBounds.getHeight();
        double yAxisPosition = plotBounds.getMinY();
        double horizontalOffset = 32;

        // Positions Y
        double y8 = yAxis.getDisplayPosition(min);
        double y12 = yAxis.getDisplayPosition(rouge);
        double y16 = yAxis.getDisplayPosition(minVert);
        double y62 = yAxis.getDisplayPosition(maxVert);

        if (Double.isNaN(y8) || Double.isNaN(y12) || Double.isNaN(y16) || Double.isNaN(y62)) return;

        // Création des zones
        Rectangle redZone = new Rectangle(horizontalOffset, yAxisPosition + y12, chartWidth, y8 - y12);
        redZone.setFill(Color.rgb(255, 0, 0, 0.2));

        Rectangle yellowZone = new Rectangle(horizontalOffset, yAxisPosition + y16, chartWidth, y12 - y16);
        yellowZone.setFill(Color.rgb(255, 255, 0, 0.2));

        Rectangle greenZone = new Rectangle(horizontalOffset, yAxisPosition + y62, chartWidth, y16 - y62);
        greenZone.setFill(Color.rgb(0, 255, 0, 0.2));

        // Lignes de seuil
        Line redLine = new Line(horizontalOffset, yAxisPosition + y12, horizontalOffset + chartWidth, yAxisPosition + y12);
        redLine.setStroke(Color.RED);
        redLine.setStrokeWidth(1.5);

        Line greenLine = new Line(horizontalOffset, yAxisPosition + y16, horizontalOffset + chartWidth, yAxisPosition + y16);
        greenLine.setStroke(Color.GREEN);
        greenLine.setStrokeWidth(1.5);

        Pane overlay = new Pane(redZone, yellowZone, greenZone, redLine, greenLine);
        overlay.setMouseTransparent(true);

        Node parent = plotBackground.getParent();
        if (parent instanceof Pane pane) {
            pane.getChildren().removeIf(n -> n.getUserData() != null && n.getUserData().equals("zone-overlay"));
            overlay.setUserData("zone-overlay");
            pane.getChildren().add(overlay);
        }
    }
    /*********************************************************************************************************************/

    /*public static void chargerSouduresParPdekEtPage(VBox root) {
        Task<List<SoudureReponse>> task = new Task<>() {
            @Override
            protected List<SoudureReponse> call() throws Exception {
                String token = AppInformations.token;
                String url = "http://localhost:8281/operations/soudure/soudures-par-pdek-et-page"
                           + "?pdekId=" + Dashboard2Controller.idPdekGlobale
                           + "&pageNumber=" + Dashboard2Controller.numPageGlobale ;

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
            StackPane chart = createMoyenneChartWithZones(donnees);
            root.getChildren().add(chart);
        });

        task.setOnFailed(event -> {
            System.out.println("Impossible de charger les soudures : " + task.getException().getMessage());
        });

        new Thread(task).start();
    }
*/
}
