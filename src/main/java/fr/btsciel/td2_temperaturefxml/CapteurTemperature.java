package fr.btsciel.td2_temperaturefxml;

import jssc.SerialPortEvent;
import jssc.SerialPortException;

public class CapteurTemperature extends LiaisonSerie {
    private final int ARRET = 1;
    private final int PARITE = 0;
    private final int DONNEES = 8;
    private final int VITESSE = 9600;
    private int LONGUEUR_TRAME;
    private float valeurMesuree;

    public CapteurTemperature() {
    }

    public void fermerConnexionCapteur() {
        super.fermerPort();
    }

    public void configurerConnexionCapteur(String portDeTravail) throws SerialPortException {
        super.initCom(portDeTravail);
        super.configurerParametres(VITESSE, DONNEES, PARITE, ARRET);
        super.serialPort.openPort();
    }

    public float decodageTrameCapteur(byte[] byteArray) {
        StringBuilder sb = new StringBuilder();
        boolean premierOctet = false;
        for (byte b : byteArray) {
            if (!premierOctet) {
                sb.append((b & 0xff)).append(".");
            } else {
                sb.append((b & 0xff));
            }
            premierOctet = true;
        }
        valeurMesuree = Float.parseFloat(sb.toString());
        return valeurMesuree;
    }

    public byte[] lireTrameCapteurModeNormal() {
        return super.lireTrame(LONGUEUR_TRAME);
    }

    public float conversionTrameCapteur(byte[] byteArray) {
        valeurMesuree = BigEndian.fromArray(byteArray);
        return valeurMesuree;
    }

    public byte[] lireTrameCapteurModeIEEE() {
        return super.lireTrame(LONGUEUR_TRAME);
    }

    public float getValeurMesuree() {
        return valeurMesuree;
    }

    @Override
    public void serialEvent(SerialPortEvent spe) {
        super.serialEvent(spe);
        LONGUEUR_TRAME = spe.getEventValue();
        byte[] OCTETS_DEBUT_IEEE = lireTrameCapteurModeIEEE();

        System.out.printf("""
                --- Réception ---
                %s Celsius
                %s Kelvin
                %n""", decodageTrameCapteur(OCTETS_DEBUT_IEEE), decodageTrameCapteur(OCTETS_DEBUT_IEEE) + 273.15f);
    }
}