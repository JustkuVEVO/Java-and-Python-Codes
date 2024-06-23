import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class MundialFutbol {
    private JFrame frame;
    private JPanel contentPane;
    private Map<String, Map<String, Integer>> equipos;
    private Map<String, Integer> estadios;
    private List<Partido> partidos;
    private Map<String, List<String>> jugadoresPorEquipo;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MundialFutbol window = new MundialFutbol();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MundialFutbol() {
        equipos = new HashMap<>();
        estadios = new HashMap<>();
        partidos = new ArrayList<>();
        jugadoresPorEquipo = new HashMap<>();

        initEquipos();
        initJugadoresPorEquipo();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Gestión de Mundial de Fútbol");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnEquipos = new JMenu("Gestión de Equipos");
        menuBar.add(mnEquipos);

        JMenuItem mntmAgregarEquipo = new JMenuItem("Agregar Equipo");
        mntmAgregarEquipo.addActionListener(e -> agregarEquipo());
        mnEquipos.add(mntmAgregarEquipo);

        JMenu mnPartidos = new JMenu("Gestión de Partidos");
        menuBar.add(mnPartidos);

        JMenuItem mntmRegistrarPartido = new JMenuItem("Registrar Partido");
        mntmRegistrarPartido.addActionListener(e -> registrarPartido());
        mnPartidos.add(mntmRegistrarPartido);

        JMenu mnEstadios = new JMenu("Gestión de Estadios");
        menuBar.add(mnEstadios);

        JMenuItem mntmAgregarEstadio = new JMenuItem("Agregar Estadio");
        mntmAgregarEstadio.addActionListener(e -> agregarEstadio());
        mnEstadios.add(mntmAgregarEstadio);

        JMenu mnClasificacion = new JMenu("Ver Clasificación");
        menuBar.add(mnClasificacion);

        JMenuItem mntmVerTabla = new JMenuItem("Ver Tabla");
        mntmVerTabla.addActionListener(e -> verClasificacion());
        mnClasificacion.add(mntmVerTabla);

        JMenu mnJugadores = new JMenu("Jugadores");
        menuBar.add(mnJugadores);

        for (String equipo : equipos.keySet()) {
            JMenuItem mntmEquipo = new JMenuItem(equipo);
            mntmEquipo.addActionListener(e -> verJugadores(equipo));
            mnJugadores.add(mntmEquipo);
        }

        contentPane = new JPanel();
        contentPane.setBackground(Color.GREEN);
        contentPane.setLayout(new BorderLayout());
        frame.getContentPane().add(contentPane, BorderLayout.CENTER);
    }

    private void agregarEquipo() {
        limpiarContenido();
        JLabel lblTitulo = new JLabel("Agregar Equipo");
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(0, 2, 10, 10));

        JLabel lblNombre = new JLabel("Nombre del Equipo:");
        panel.add(lblNombre);

        JTextField txtNombre = new JTextField();
        panel.add(txtNombre);
        txtNombre.setColumns(10);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            if (!nombre.isEmpty()) {
                if (equipos.containsKey(nombre)) {
                    JOptionPane.showMessageDialog(frame, "El equipo ya está registrado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                } else {
                    equipos.put(nombre, new HashMap<>(Map.of("PJ", 0, "G", 0, "E", 0, "P", 0, "GF", 0, "GC", 0, "Pts", 0)));
                    JOptionPane.showMessageDialog(frame, "Equipo '" + nombre + "' agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    txtNombre.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "El nombre del equipo no puede estar vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
        contentPane.add(btnAgregar, BorderLayout.SOUTH);
    }

    private void agregarEstadio() {
        limpiarContenido();
        JLabel lblTitulo = new JLabel("Agregar Estadio");
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(0, 2, 10, 10));

        JLabel lblNombre = new JLabel("Nombre del Estadio:");
        panel.add(lblNombre);

        JTextField txtNombre = new JTextField();
        panel.add(txtNombre);
        txtNombre.setColumns(10);

        JLabel lblCapacidad = new JLabel("Capacidad:");
        panel.add(lblCapacidad);

        JTextField txtCapacidad = new JTextField();
        panel.add(txtCapacidad);
        txtCapacidad.setColumns(10);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String capacidadStr = txtCapacidad.getText().trim();
            if (!nombre.isEmpty() && capacidadStr.matches("\\d+")) {
                int capacidad = Integer.parseInt(capacidadStr);
                if (estadios.containsKey(nombre)) {
                    JOptionPane.showMessageDialog(frame, "El estadio ya está registrado.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                } else {
                    estadios.put(nombre, capacidad);
                    JOptionPane.showMessageDialog(frame, "Estadio '" + nombre + "' agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    txtNombre.setText("");
                    txtCapacidad.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "El nombre del estadio no puede estar vacío y la capacidad debe ser un número.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
        contentPane.add(btnAgregar, BorderLayout.SOUTH);
    }

    private void registrarPartido() {
        limpiarContenido();
        JLabel lblTitulo = new JLabel("Registrar Partido");
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(0, 2, 10, 10));

        JLabel lblEquipoLocal = new JLabel("Equipo Local:");
        panel.add(lblEquipoLocal);

        JComboBox<String> cbEquipoLocal = new JComboBox<>(equipos.keySet().toArray(new String[0]));
        panel.add(cbEquipoLocal);

        JLabel lblGolesLocal = new JLabel("Goles Local:");
        panel.add(lblGolesLocal);

        JTextField txtGolesLocal = new JTextField();
        panel.add(txtGolesLocal);
        txtGolesLocal.setColumns(10);

        JLabel lblEquipoVisitante = new JLabel("Equipo Visitante:");
        panel.add(lblEquipoVisitante);

        JComboBox<String> cbEquipoVisitante = new JComboBox<>(equipos.keySet().toArray(new String[0]));
        panel.add(cbEquipoVisitante);

        JLabel lblGolesVisitante = new JLabel("Goles Visitante:");
        panel.add(lblGolesVisitante);

        JTextField txtGolesVisitante = new JTextField();
        panel.add(txtGolesVisitante);
        txtGolesVisitante.setColumns(10);

        JLabel lblEstadio = new JLabel("Estadio:");
        panel.add(lblEstadio);

        JComboBox<String> cbEstadio = new JComboBox<>(estadios.keySet().toArray(new String[0]));
        panel.add(cbEstadio);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> {
            String equipoLocal = (String) cbEquipoLocal.getSelectedItem();
            String equipoVisitante = (String) cbEquipoVisitante.getSelectedItem();
            String golesLocalStr = txtGolesLocal.getText().trim();
            String golesVisitanteStr = txtGolesVisitante.getText().trim();
            String estadio = (String) cbEstadio.getSelectedItem();

            if (equipoLocal != null && equipoVisitante != null && golesLocalStr.matches("\\d+") && golesVisitanteStr.matches("\\d+") && estadio != null) {
                int golesLocal = Integer.parseInt(golesLocalStr);
                int golesVisitante = Integer.parseInt(golesVisitanteStr);
                partidos.add(new Partido(equipoLocal, golesLocal, equipoVisitante, golesVisitante, estadio));
                actualizarEstadisticas(equipoLocal, golesLocal, equipoVisitante, golesVisitante);
                JOptionPane.showMessageDialog(frame, "Partido registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtGolesLocal.setText("");
                txtGolesVisitante.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Debe completar todos los campos correctamente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        });
        contentPane.add(btnRegistrar, BorderLayout.SOUTH);
    }

    private void verClasificacion() {
        limpiarContenido();
        JLabel lblTitulo = new JLabel("Clasificación");
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(0, 1, 10, 10));

        String[] columnas = {"Equipo", "PJ", "G", "E", "P", "GF", "GC", "Pts"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);

        for (Map.Entry<String, Map<String, Integer>> entry : equipos.entrySet()) {
            String equipo = entry.getKey();
            Map<String, Integer> estadisticas = entry.getValue();
            Object[] fila = {equipo, estadisticas.get("PJ"), estadisticas.get("G"), estadisticas.get("E"), estadisticas.get("P"), estadisticas.get("GF"), estadisticas.get("GC"), estadisticas.get("Pts")};
            model.addRow(fila);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
    }

    private void verJugadores(String equipo) {
        limpiarContenido();
        JLabel lblTitulo = new JLabel("Jugadores de " + equipo);
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        contentPane.add(lblTitulo, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(0, 1, 10, 10));

        if (jugadoresPorEquipo.containsKey(equipo)) {
            for (String jugador : jugadoresPorEquipo.get(equipo)) {
                JLabel lblJugador = new JLabel(jugador);
                panel.add(lblJugador);
            }
        } else {
            JLabel lblNoJugadores = new JLabel("No hay jugadores registrados para este equipo.");
            panel.add(lblNoJugadores);
        }
    }

    private void limpiarContenido() {
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();
    }

    private void actualizarEstadisticas(String equipoLocal, int golesLocal, String equipoVisitante, int golesVisitante) {
        equipos.get(equipoLocal).put("PJ", equipos.get(equipoLocal).get("PJ") + 1);
        equipos.get(equipoVisitante).put("PJ", equipos.get(equipoVisitante).get("PJ") + 1);
        equipos.get(equipoLocal).put("GF", equipos.get(equipoLocal).get("GF") + golesLocal);
        equipos.get(equipoLocal).put("GC", equipos.get(equipoLocal).get("GC") + golesVisitante);
        equipos.get(equipoVisitante).put("GF", equipos.get(equipoVisitante).get("GF") + golesVisitante);
        equipos.get(equipoVisitante).put("GC", equipos.get(equipoVisitante).get("GC") + golesLocal);

        if (golesLocal > golesVisitante) {
            equipos.get(equipoLocal).put("G", equipos.get(equipoLocal).get("G") + 1);
            equipos.get(equipoLocal).put("Pts", equipos.get(equipoLocal).get("Pts") + 3);
            equipos.get(equipoVisitante).put("P", equipos.get(equipoVisitante).get("P") + 1);
        } else if (golesLocal < golesVisitante) {
            equipos.get(equipoVisitante).put("G", equipos.get(equipoVisitante).get("G") + 1);
            equipos.get(equipoVisitante).put("Pts", equipos.get(equipoVisitante).get("Pts") + 3);
            equipos.get(equipoLocal).put("P", equipos.get(equipoLocal).get("P") + 1);
        } else {
            equipos.get(equipoLocal).put("E", equipos.get(equipoLocal).get("E") + 1);
            equipos.get(equipoLocal).put("Pts", equipos.get(equipoLocal).get("Pts") + 1);
            equipos.get(equipoVisitante).put("E", equipos.get(equipoVisitante).get("E") + 1);
            equipos.get(equipoVisitante).put("Pts", equipos.get(equipoVisitante).get("Pts") + 1);
        }
    }

    private void initEquipos() {
        equipos.put("Argentina", new HashMap<>(Map.of("PJ", 0, "G", 0, "E", 0, "P", 0, "GF", 0, "GC", 0, "Pts", 0)));
        equipos.put("Brasil", new HashMap<>(Map.of("PJ", 0, "G", 0, "E", 0, "P", 0, "GF", 0, "GC", 0, "Pts", 0)));
        equipos.put("Francia", new HashMap<>(Map.of("PJ", 0, "G", 0, "E", 0, "P", 0, "GF", 0, "GC", 0, "Pts", 0)));
        equipos.put("Alemania", new HashMap<>(Map.of("PJ", 0, "G", 0, "E", 0, "P", 0, "GF", 0, "GC", 0, "Pts", 0)));
    }

    private void initJugadoresPorEquipo() {
        jugadoresPorEquipo.put("Argentina", Arrays.asList("Lionel Messi", "Sergio Agüero", "Ángel Di María"));
        jugadoresPorEquipo.put("Brasil", Arrays.asList("Neymar", "Gabriel Jesus", "Philippe Coutinho"));
        jugadoresPorEquipo.put("Francia", Arrays.asList("Kylian Mbappé", "Antoine Griezmann", "Paul Pogba"));
        jugadoresPorEquipo.put("Alemania", Arrays.asList("Thomas Müller", "Toni Kroos", "Manuel Neuer"));
    }
}