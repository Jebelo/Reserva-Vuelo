package Modelo;

import java.util.List;

public class Vuelo {
    private final Avion avion;

    public Vuelo() {
        avion = new Avion();
    }

    public Avion getAvion() {
        return avion;
    }

    /**
     * Intenta asignar la silla indicada (codigoSilla). Si codigoSilla == null,
     * busca la primera silla libre que cumpla clase+ubicacion.
     * Retorna true si asignó.
     */
    public boolean asignarSilla(Pasajero pasajero, String clase, String ubicacion, String codigoSilla) {
        List<Silla> sillas = avion.getSillas();

        // Si dieron un código específico, probarlo primero
        if (codigoSilla != null && !codigoSilla.isBlank()) {
            Silla s = avion.buscarPorCodigo(codigoSilla);
            if (s != null && !s.isOcupada()
                    && s.getClase().equalsIgnoreCase(clase)
                    && s.getUbicacion().equalsIgnoreCase(ubicacion)) {
                s.ocupar(pasajero);
                return true;
            }
            // si especificaron código pero no se puede, devolvemos false
            return false;
        }

        // Si no dieron código, buscar la primer silla libre que cumpla clase+ubicacion
        for (Silla s : sillas) {
            if (!s.isOcupada()
                    && s.getClase().equalsIgnoreCase(clase)
                    && s.getUbicacion().equalsIgnoreCase(ubicacion)) {
                s.ocupar(pasajero);
                return true;
            }
        }
        // Si no se encontró con la ubicación exacta, intentar cualquier silla de la clase
        for (Silla s : sillas) {
            if (!s.isOcupada() && s.getClase().equalsIgnoreCase(clase)) {
                s.ocupar(pasajero);
                return true;
            }
        }
        return false;
    }

    public Silla buscarSillaAsignada(Pasajero pasajero) {
        for (Silla s : avion.getSillas()) {
            if (s.getPasajero() != null && s.getPasajero().equals(pasajero)) {
                return s;
            }
        }
        return null;
    }
}
