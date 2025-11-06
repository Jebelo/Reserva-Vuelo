package Vista;

import Controlador.ControladorVuelo;
import Modelo.Pasajero;
import Modelo.Silla;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VistaGrafica extends JFrame {

    private final ControladorVuelo controlador;
    private final JButton[] botones; // 50 botones
    private final JTextField txtNombre;
    private final JTextField txtCedula;
    private final JComboBox<String> cmbClase;
    private final JComboBox<String> cmbUbicacion;
    private final JLabel lblSeleccion;
    private String seleccionCodigo; // código de silla seleccionada

    public VistaGrafica(ControladorVuelo controlador) {
        this.controlador = controlador;
        this.botones = new JButton[50];
        this.seleccionCodigo = null;

        setTitle("Reserva de Vuelo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8,8));

        // Panel izquierdo: formulario
        JPanel panelForm = new JPanel(new GridLayout(8,1,5,5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        panelForm.add(txtCedula);

        panelForm.add(new JLabel("Clase:"));
        cmbClase = new JComboBox<>(new String[]{"Ejecutiva", "Económica"});
        panelForm.add(cmbClase);

        panelForm.add(new JLabel("Preferencia:"));
        cmbUbicacion = new JComboBox<>(new String[]{"Ventana", "Pasillo", "Centro"});
        panelForm.add(cmbUbicacion);

        JButton btnReservar = new JButton("Reservar");
        panelForm.add(btnReservar);

        lblSeleccion = new JLabel("Silla seleccionada: ninguna");
        panelForm.add(lblSeleccion);

        add(panelForm, BorderLayout.WEST);

        // Panel centro: representación del avión (organizado en paneles)
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Ejecutiva (2 filas x 4)
        JPanel panelEjecutiva = new JPanel(new GridLayout(2,4,8,8));
        panelEjecutiva.setBorder(BorderFactory.createTitledBorder("Clase Ejecutiva"));
        // tomar sillas desde vuelo para respetar el orden y códigos
        int idx = 0;
        for (Silla s : controlador.getVuelo().getAvion().getSillas()) {
            if (s.getClase().equalsIgnoreCase("Ejecutiva")) {
                JButton b = crearBotonSilla(s);
                botones[idx++] = b;
                panelEjecutiva.add(b);
            }
        }
        panelCentro.add(panelEjecutiva);

        panelCentro.add(Box.createVerticalStrut(12));

        // Económica (7 filas x 6)
        JPanel panelEconomica = new JPanel(new GridLayout(7,6,6,6));
        panelEconomica.setBorder(BorderFactory.createTitledBorder("Clase Económica"));
        for (Silla s : controlador.getVuelo().getAvion().getSillas()) {
            if (s.getClase().equalsIgnoreCase("Económica")) {
                JButton b = crearBotonSilla(s);
                botones[idx++] = b;
                panelEconomica.add(b);
            }
        }
        panelCentro.add(panelEconomica);

        add(new JScrollPane(panelCentro), BorderLayout.CENTER);

        // acción reservar
        btnReservar.addActionListener((ActionEvent e) -> {
            reservarAccion();
        });

        setVisible(true);
    }

    private JButton crearBotonSilla(Silla silla) {
        JButton b = new JButton(silla.getCodigo() + " (" + silla.getUbicacion().charAt(0) + ")");
        b.setMargin(new Insets(2,2,2,2));
        // color por clase
        if (silla.getClase().equalsIgnoreCase("Ejecutiva")) b.setBackground(new Color(173,216,230));
        else b.setBackground(new Color(144,238,144));

        if (silla.isOcupada()) {
            b.setEnabled(false);
            b.setBackground(Color.RED);
        }

        b.addActionListener(evt -> {
            // marcar selección visualmente
            limpiarSeleccionVisual();
            b.setBackground(Color.YELLOW);
            seleccionCodigo = silla.getCodigo();
            controlador.seleccionarSilla(seleccionCodigo);
            lblSeleccion.setText("Silla seleccionada: " + seleccionCodigo);
        });
        return b;
    }

    private void limpiarSeleccionVisual() {
        // recorrer botones y restablecer color según clase y ocupación
        for (Silla s : controlador.getVuelo().getAvion().getSillas()) {
            // buscar botón correspondiente por texto parcial
            for (JButton btn : botones) {
                if (btn == null) continue;
                if (btn.getText().startsWith(s.getCodigo())) {
                    if (s.isOcupada()) {
                        btn.setBackground(Color.RED);
                        btn.setEnabled(false);
                    } else {
                        if (s.getClase().equalsIgnoreCase("Ejecutiva")) btn.setBackground(new Color(173,216,230));
                        else btn.setBackground(new Color(144,238,144));
                        btn.setEnabled(true);
                    }
                }
            }
        }
    }

    private void reservarAccion() {
        String nombre = txtNombre.getText().trim();
        String cedula = txtCedula.getText().trim();
        String clase = (String) cmbClase.getSelectedItem();
        String ubicacion = (String) cmbUbicacion.getSelectedItem();

        if (nombre.isEmpty() || cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete nombre y cédula.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // crear pasajero
        Pasajero pasajero = new Pasajero(nombre, cedula);

        boolean ok = controlador.reservarSilla(pasajero, clase, ubicacion);
        if (ok) {
            // obtener silla asignada
            Silla s = controlador.getVuelo().buscarSillaAsignada(pasajero);
            // actualizar visualizar botones (poner rojo en la silla)
            limpiarSeleccionVisual();
            // mostrar tiquete
            String mensaje = "🎟️ TIQUETE DE RESERVA\n\n"
                    + "Pasajero: " + pasajero.getNombre() + "\n"
                    + "Cédula: " + pasajero.getCedula() + "\n"
                    + "Clase: " + s.getClase() + "\n"
                    + "Ubicación: " + s.getUbicacion() + "\n"
                    + "Silla: " + s.getCodigo() + "\n"
                    + "Hora salida: 10:00 AM\n"
                    + "Hora llegada: 1:00 PM\n"
                    + String.format("Total a pagar: $%.2f", s.getPrecio());

            JOptionPane.showMessageDialog(this, mensaje, "Tiquete", JOptionPane.INFORMATION_MESSAGE);

            // limpiar campos y selección
            txtNombre.setText("");
            txtCedula.setText("");
            seleccionCodigo = null;
            controlador.seleccionarSilla(null);
            lblSeleccion.setText("Silla seleccionada: ninguna");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo reservar (preferencia/clase no disponible o silla llena).", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }
}
