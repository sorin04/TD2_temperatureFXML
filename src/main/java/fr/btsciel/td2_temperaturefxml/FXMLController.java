package fr.btsciel.td2_temperaturefxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {
    @FXML
    public LineChart<Number, Number> temperature;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;

    private LiaisonSerie liaisonSerie;
    public XYChart.Series<Number, Number> series1 = new XYChart.Series<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        temperature.setAnimated(false);
        yAxis.setLabel("Graphique");
        temperature.setCursor(Cursor.CROSSHAIR);
        series1.setName("Capteur Z56");
        temperature.getData().add(series1);
    }

    public void ajtDonneesTemperature(double valeurX, double valeurY) {
        series1.getData().add(new XYChart.Data<>(valeurX, valeurY));
    }

    public void setLiaisonSerie(LiaisonSerie liaisonSerie) {
        this.liaisonSerie = liaisonSerie;
    }

    @FXML
    public void BoutonON() {
        if (liaisonSerie != null) {
            double temperatureValeur = liaisonSerie.lire();
            System.out.println("Valeur lue : " + temperatureValeur);

            ajtDonneesTemperature(System.currentTimeMillis(), temperatureValeur);
        } else {
            System.out.println("Liaison Serie non initialisée");
        }
    }
}
