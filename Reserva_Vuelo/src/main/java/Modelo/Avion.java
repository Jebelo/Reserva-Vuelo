package Modelo;

import java.util.List;

public class Avion {
    private final List<Silla> sillas;

    public Avion() {
        this.sillas = FabricaSillas.crearSillas();
    }

    public List<Silla> getSillas() { return sillas; }

    public Silla buscarPorCodigo(String codigo) {
        if (codigo == null) return null;
        for (Silla s : sillas) {
            if (s.getCodigo().equalsIgnoreCase(codigo)) return s;
        }
        return null;
    }
}
