package Modelo;

import java.util.ArrayList;
import java.util.List;

public class FabricaSillas {

    public static List<Silla> crearSillas() {
        List<Silla> sillas = new ArrayList<>();

        // Ejecutiva: 2 filas * 4 => códigos E1A..E1D, E2A..E2D
        for (int fila = 1; fila <= 2; fila++) {
            for (int col = 0; col < 4; col++) {
                String codigo = "E" + fila + (char)('A' + col);
                // posición por columna: A (Ventana), B (Pasillo), C (Pasillo), D (Ventana)
                String ubicacion = (col == 0 || col == 3) ? "Ventana" : "Pasillo";
                sillas.add(new Silla(codigo, "Ejecutiva", ubicacion));
            }
        }

        // Económica: filas 3..9 (7 filas) * 6 columnas A..F
        for (int fila = 3; fila <= 9; fila++) {
            for (int col = 0; col < 6; col++) {
                String codigo = "C" + fila + (char)('A' + col);
                // A Ventana, B Centro, C Pasillo, D Pasillo, E Centro, F Ventana
                String ubicacion;
                if (col == 0 || col == 5) ubicacion = "Ventana";
                else if (col == 1 || col == 4) ubicacion = "Centro";
                else ubicacion = "Pasillo";
                sillas.add(new Silla(codigo, "Económica", ubicacion));
            }
        }

        return sillas;
    }
}
