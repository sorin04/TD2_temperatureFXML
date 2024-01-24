module fr.btsciel.td2_temperaturefxml {
    requires javafx.controls;
    requires javafx.fxml;
    requires jssc;
    requires java.desktop;


    opens fr.btsciel.td2_temperaturefxml to javafx.fxml;
    exports fr.btsciel.td2_temperaturefxml;
}