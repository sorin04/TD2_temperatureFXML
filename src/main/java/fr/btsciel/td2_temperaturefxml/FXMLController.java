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

    @FXML
    CategoryAxis xAxis;
    @FXML
    NumberAxis yAxis;
    public LineChart<String,Number> LineChart =new LineChart<>(xAxis, yAxis);
    XYChart.Series<String,Number> series1 = new XYChart.Series<>();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LineChart.setAnimated(true);
        LineChart.setCreateSymbols(false);

        for (int i = -6; i < 6; i= 0.1+1) {
            if (if = 0) {
                series1.getData().add(XYChart.Data<Number, Number>(i,Math.sin(i)/i));



            }

        }



    }
}