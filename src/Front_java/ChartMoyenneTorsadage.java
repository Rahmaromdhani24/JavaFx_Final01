package Front_java;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import Front_java.Configuration.AppInformations;
import Front_java.Configuration.TorsadageInformations;
import Front_java.Configuration.TorsadageReponse;
import Front_java.Torsadage.ResultatTorsadage;
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

public class ChartMoyenneTorsadage extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        // Charge les données et affiche le chart une fois terminé
       // chargerTorsadagesParPdekEtPage(root);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Zone colorée dans LineChart");
        primaryStage.show();
    }

    public static StackPane createMoyenneChartTorsadageWithZones(String numCourant , double moyenne) {

        int pas = extraireNombre(TorsadageInformations.specificationsMesure); 
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis((pas - 2) - 1, (pas + 2) + 1, 0.2);

        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        chart.setPrefSize(460, 300);  // Taille spécifique
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>(numCourant, moyenne));

        chart.getData().add(series);

        StackPane stackPane = new StackPane(chart);

        chart.lookup(".chart-plot-background").boundsInParentProperty().addListener((obs, oldVal, newVal) -> {
            Platform.runLater(() -> drawZones(stackPane, chart, yAxis));
        });

        return stackPane;
    }



    public static void drawZones(StackPane stackPane, LineChart<String, Number> chart, NumberAxis yAxis) {
        Node plotBackground = chart.lookup(".chart-plot-background");
        if (plotBackground == null) return;

        int pas = extraireNombre(TorsadageInformations.specificationsMesure); // ✅ Dynamique

        Bounds plotBounds = plotBackground.getBoundsInParent();
        double chartWidth = plotBounds.getWidth();
        double chartHeight = plotBounds.getHeight();

        double yLowRed    = yAxis.getDisplayPosition(pas - 2); //18
        double yLowYellow = yAxis.getDisplayPosition((pas -2)+ 0.8);//
        double yPas       = yAxis.getDisplayPosition(pas); //
        double yHighYellow= yAxis.getDisplayPosition((pas +2)- 0.8); //
        double yHighRed   = yAxis.getDisplayPosition(pas + 2); //

        double yAxisPosition = plotBounds.getMinY();
        double horizontalOffset = 40;

        Rectangle redZoneBottom = new Rectangle(horizontalOffset, yAxisPosition + yLowRed, chartWidth, yAxisPosition + chartHeight - (yAxisPosition + yLowRed));
        redZoneBottom.setFill(Color.rgb(255, 0, 0, 0.2));

        Rectangle yellowZoneBottom = new Rectangle(horizontalOffset, yAxisPosition + yLowYellow, chartWidth, yLowRed - yLowYellow);
        yellowZoneBottom.setFill(Color.rgb(255, 255, 0, 0.2));

        Rectangle greenZone = new Rectangle(horizontalOffset, yAxisPosition + yHighYellow, chartWidth, yLowYellow - yHighYellow);
        greenZone.setFill(Color.rgb(0, 255, 0, 0.2));

        Rectangle yellowZoneTop = new Rectangle(horizontalOffset, yAxisPosition + yHighRed, chartWidth, yHighYellow - yHighRed);
        yellowZoneTop.setFill(Color.rgb(255, 255, 0, 0.2));

        Rectangle redZoneTop = new Rectangle(horizontalOffset, yAxisPosition, chartWidth, yHighRed - yAxis.getDisplayPosition(yAxis.getUpperBound()));
        redZoneTop.setFill(Color.rgb(255, 0, 0, 0.2));

        Line blackLine = new Line(horizontalOffset, yAxisPosition + yPas, horizontalOffset + chartWidth, yAxisPosition + yPas);
        blackLine.setStroke(Color.BLACK);
        blackLine.setStrokeWidth(1);

        Pane overlay = new Pane(redZoneBottom, yellowZoneBottom, greenZone, yellowZoneTop, redZoneTop, blackLine);
        overlay.setMouseTransparent(true);

        Node parent = plotBackground.getParent();
        if (parent instanceof Pane pane) {
            pane.getChildren().removeIf(n -> n.getUserData() != null && n.getUserData().equals("zone-overlay"));
            overlay.setUserData("zone-overlay");
            pane.getChildren().add(overlay);
        }
    }

    public static int extraireNombre(String texte) {
        // Expression régulière pour capturer le nombre au début
        String regex = "(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texte);
        
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            throw new IllegalArgumentException("Aucun nombre trouvé dans : " + texte);
        }
    }
    /*********************************************************************************************************************/

   /* public static void chargerTorsadagesParPdekEtPage(VBox root) {
        Task<List<TorsadageReponse>> task = new Task<>() {
            @Override
            protected List<TorsadageReponse> call() throws Exception {
                String token = AppInformations.token;
                String url = "http://localhost:8281/operations/torsadage/torsadages-par-pdek-et-page"
                           + "?pdekId=" + ResultatTorsadage.idPdekTorsadageGlobale
                           + "&pageNumber=" + ResultatTorsadage.numPageTorsadageGlobale ;

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .header("Authorization", "Bearer " + token)
                        .GET()
                        .build();

                HttpClient client = HttpClient.newHttpClient();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(response.body(), new TypeReference<List<TorsadageReponse>>() {});
                } else {
                    throw new Exception("Erreur " + response.statusCode() + " : " + response.body());
                }
            }
        };

        task.setOnSucceeded(event -> {
            List<TorsadageReponse> donnees = task.getValue();
            StackPane chart = createMoyenneChartTorsadageWithZones(donnees);
            root.getChildren().add(chart);
        });

        task.setOnFailed(event -> {
            System.out.println("Impossible de charger les soudures : " + task.getException().getMessage());
        });

        new Thread(task).start();
    }*/

}