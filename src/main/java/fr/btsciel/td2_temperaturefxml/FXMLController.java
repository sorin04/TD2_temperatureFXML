package fr.btsciel.td2_temperaturefxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.awt.image.MemoryImageSource;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {
    public LineChart<Number, Number>courbe;
    public XYChart.Series series1 = new XYChart.Series<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courbe.setAnimated(true);
        courbe.setCreateSymbols(false);
        for (double i = -6; i < 6; i = 0.1+i) {
            if (i!= 0) {
                series1.getData().add(new XYChart.Data<Number, Number>(i,Math.sin(i)/i));
            }
        }
        courbe.getData().add(series1);
    }
}