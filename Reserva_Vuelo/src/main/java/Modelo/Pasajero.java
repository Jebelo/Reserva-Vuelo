package Modelo;

public class Pasajero {
    private final String nombre;
    private final String cedula;

    public Pasajero(String nombre, String cedula) {
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pasajero)) return false;
        Pasajero p = (Pasajero) o;
        return cedula != null && cedula.equals(p.cedula);
    }

    @Override
    public int hashCode() {
        return cedula == null ? 0 : cedula.hashCode();
    }
}
