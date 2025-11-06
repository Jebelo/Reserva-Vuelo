package Modelo;

public class Silla {
    private final String codigo;
    private final String clase;     // "Ejecutiva" o "Económica"
    private final String ubicacion; // "Ventana", "Pasillo", "Centro"
    private boolean ocupada;
    private Pasajero pasajero;

    public Silla(String codigo, String clase, String ubicacion) {
        this.codigo = codigo;
        this.clase = clase;
        this.ubicacion = ubicacion;
        this.ocupada = false;
        this.pasajero = null;
    }

    public String getCodigo() { return codigo; }
    public String getClase() { return clase; }
    public String getUbicacion() { return ubicacion; }
    public boolean isOcupada() { return ocupada; }
    public Pasajero getPasajero() { return pasajero; }

    public void ocupar(Pasajero p) {
        this.pasajero = p;
        this.ocupada = true;
    }

    public double getPrecio() {
        return clase.equalsIgnoreCase("Ejecutiva") ? 500000.0 : 250000.0;
    }
}
