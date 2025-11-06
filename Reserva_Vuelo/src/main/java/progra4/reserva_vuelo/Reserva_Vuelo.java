package progra4.reserva_vuelo;

import Controlador.ControladorVuelo;
import Vista.VistaGrafica;

public class Reserva_Vuelo {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ControladorVuelo controlador = new ControladorVuelo();
            new VistaGrafica(controlador).setVisible(true);
        });
    }
}
