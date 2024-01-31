package fr.btsciel.td2_temperaturefxml;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import jssc.SerialPortException;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class FXMLController implements Initializable {
    @FXML
    public LineChart<String, Number> temperature;
    public NumberAxis yAxis;
    public CategoryAxis xAxis;
    private boolean stop;
    final int TAILLE_DE_FENETRE = 15;
    private Timer timer;
    private SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
    private LiaisonSerie liaisonSerie;
    private CapteurTemperature capteurTemperature = new CapteurTemperature();
    private SimpleDateFormat capteurData=new SimpleDateFormat();
    String port = "COM10";



    public XYChart.Series<String, Number> series1 = new XYChart.Series<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            temperature.setAnimated(false);
            xAxis.setLabel("Temps");
            yAxis.setLabel("Graphique");
            capteurTemperature.configurerConnexionCapteur(port);
        } catch (SerialPortException e) {
            throw new RuntimeException(e);
        }

        temperature.setCursor(Cursor.CROSSHAIR);
        series1.setName("Capteur Z56");
        base_De_Temps();
    }

    public void setLiaisonSerie(LiaisonSerie liaisonSerie) {
        this.liaisonSerie = liaisonSerie;
    }

    @FXML
    public void BoutonON() {

    }
    private void base_De_Temps() {
        TimerTask updateTask = new TimerTask() {
            @Override
            public void run() {
                if (!stop) {
                    Platform.runLater(() -> {
                        try {
                            capteurTemperature.ecrire("IEEE".getBytes(StandardCharsets.US_ASCII));
                            capteurTemperature.ecrire("\r".getBytes(StandardCharsets.US_ASCII));
                            Thread.sleep(1000);
                            series1.getData().add(new XYChart.Data<>(String.valueOf(date.format(new Date())), capteurTemperature.getValeurLue()));
                            if (series1.getData().size() > TAILLE_DE_FENETRE) {
                                series1.getData().remove(0);
                            }
                            capteurTemperature.fermerLiaisonSerieCapteur();
                        } catch (SerialPortException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        };
        timer = new Timer(true);
        timer.scheduleAtFixedRate(updateTask, 0, 5000);
        temperature.getData().add(series1);
    }


}