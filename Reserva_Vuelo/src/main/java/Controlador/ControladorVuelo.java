package Controlador;

import Modelo.*;

public class ControladorVuelo {
    private final Vuelo vuelo;
    private String sillaSeleccionada; // puede ser null

    public ControladorVuelo() {
        vuelo = new Vuelo();
        sillaSeleccionada = null;
    }

    public void seleccionarSilla(String codigo) {
        this.sillaSeleccionada = codigo;
    }

    public boolean reservarSilla(Pasajero pasajero, String clase, String ubicacion) {
        // pasa la sillaSeleccionada (puede ser null) al modelo
        boolean ok = vuelo.asignarSilla(pasajero, clase, ubicacion, sillaSeleccionada);
        // limpiar selección para la próxima reserva
        if (ok) sillaSeleccionada = null;
        return ok;
    }

    public Vuelo getVuelo() { return vuelo; }
}
