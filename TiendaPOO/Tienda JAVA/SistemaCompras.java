import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SistemaCompras extends JFrame {
    private Map<String, Map<String, Double>> productos;
    private Map<String, Integer> carrito;

    public SistemaCompras() {
        setTitle("Sistema de Compras");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.YELLOW);

        productos = new HashMap<>();
        carrito = new HashMap<>();

        inicializarProductos();
        crearInterfaz();
    }

    private void inicializarProductos() {
        productos.put("Bebidas", new HashMap<>() {{
            put("Agua", 1.5);
            put("Refresco", 2.0);
            put("Jugo de Naranja", 2.5);
            put("Té Helado", 1.75);
            put("Café", 3.0);
        }});

        productos.put("Comidas", new HashMap<>() {{
            put("Hamburguesa", 5.0);
            put("Pizza", 7.0);
            put("Ensalada César", 4.5);
            put("Sándwich de Pollo", 3.5);
            put("Pasta Alfredo", 6.0);
        }});

        productos.put("Dulces", new HashMap<>() {{
            put("Chocolate", 3.0);
            put("Galletas", 2.5);
            put("Pastelito", 1.75);
            put("Helado de Vainilla", 4.0);
            put("Brownie", 2.0);
        }});

        productos.put("Objetos de Aseo", new HashMap<>() {{
            put("Papel Higiénico", 1.0);
            put("Jabón de Manos", 1.5);
            put("Shampoo", 3.0);
            put("Dentífrico", 2.0);
            put("Toallas de Papel", 1.25);
        }});
    }

    private void crearInterfaz() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel titulo = new JLabel("Bienvenido al Sistema de Compras", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        add(titulo, gbc);

        // Cargar y mostrar la imagen
        ImageIcon imagenLogo = new ImageIcon("OXXO_logo.jpeg");
        Image imagen = imagenLogo.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH);
        JLabel labelImagen = new JLabel(new ImageIcon(imagen));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        add(labelImagen, gbc);

        gbc.gridwidth = 1;

        JButton botonCategorias = new JButton("Categorías");
        botonCategorias.setFont(new Font("Arial", Font.PLAIN, 14));
        botonCategorias.setBackground(Color.RED);
        botonCategorias.setFocusPainted(false);
        botonCategorias.setForeground(Color.BLACK);
        botonCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCategorias();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.EAST;
        add(botonCategorias, gbc);

        JButton botonCarrito = new JButton("Carrito de Compra");
        botonCarrito.setFont(new Font("Arial", Font.PLAIN, 14));
        botonCarrito.setBackground(Color.RED);
        botonCarrito.setFocusPainted(false);
        botonCarrito.setForeground(Color.BLACK);
        botonCarrito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCarrito();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(botonCarrito, gbc);
    }

    private void mostrarCategorias() {
        JDialog ventanaCategorias = new JDialog(this, "Categorías de Productos", true);
        ventanaCategorias.setSize(400, 300);
        ventanaCategorias.getContentPane().setBackground(Color.YELLOW);

        JPanel panelCategorias = new JPanel();
        panelCategorias.setBackground(Color.YELLOW);
        panelCategorias.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int y = 0;
        for (String categoria : productos.keySet()) {
            JButton botonCategoria = new JButton(categoria);
            botonCategoria.setFont(new Font("Arial", Font.PLAIN, 12));
            botonCategoria.setBackground(Color.RED);
            botonCategoria.setFocusPainted(false);
            botonCategoria.setForeground(Color.BLACK);
            botonCategoria.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarProductosCategoria(categoria);
                }
            });
            gbc.gridx = 0;
            gbc.gridy = y++;
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.anchor = GridBagConstraints.CENTER;
            panelCategorias.add(botonCategoria, gbc);
        }

        ventanaCategorias.add(panelCategorias);
        ventanaCategorias.setVisible(true);
    }

    private void mostrarProductosCategoria(String categoria) {
        JDialog ventanaProductos = new JDialog(this, "Productos de " + categoria, true);
        ventanaProductos.setSize(600, 400);
        ventanaProductos.getContentPane().setBackground(Color.YELLOW);

        JPanel panelProductos = new JPanel();
        panelProductos.setBackground(Color.YELLOW);
        panelProductos.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int y = 0;
        for (Map.Entry<String, Double> entry : productos.get(categoria).entrySet()) {
            String producto = entry.getKey();
            Double precio = entry.getValue();

            JPanel frameProducto = new JPanel();
            frameProducto.setBackground(Color.YELLOW);
            frameProducto.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel labelProducto = new JLabel(producto + " - $" + String.format("%.2f", precio));
            labelProducto.setFont(new Font("Arial", Font.PLAIN, 12));
            frameProducto.add(labelProducto);

            JTextField campoCantidad = new JTextField(5);
            frameProducto.add(campoCantidad);

            JButton botonAgregar = new JButton("Agregar al Carrito");
            botonAgregar.setFont(new Font("Arial", Font.PLAIN, 12));
            botonAgregar.setBackground(Color.RED);
            botonAgregar.setFocusPainted(false);
            botonAgregar.setForeground(Color.BLACK);
            botonAgregar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int cantidad = Integer.parseInt(campoCantidad.getText());
                        agregarProductoCarrito(producto, precio, cantidad);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "La cantidad debe ser un número entero.", "Cantidad Inválida", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
            frameProducto.add(botonAgregar);

            gbc.gridx = 0;
            gbc.gridy = y++;
            gbc.insets = new Insets(5, 10, 5, 10);
            gbc.anchor = GridBagConstraints.CENTER;
            panelProductos.add(frameProducto, gbc);
        }

        ventanaProductos.add(panelProductos);
        ventanaProductos.setVisible(true);
    }

    private void agregarProductoCarrito(String producto, Double precio, int cantidad) {
        if (cantidad > 0) {
            carrito.put(producto, carrito.getOrDefault(producto, 0) + cantidad);
            JOptionPane.showMessageDialog(this, "Se han agregado " + cantidad + " " + producto + " al carrito.", "Producto Agregado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que cero.", "Cantidad Inválida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void mostrarCarrito() {
        JDialog ventanaCarrito = new JDialog(this, "Carrito de Compra", true);
        ventanaCarrito.setSize(600, 400);
        ventanaCarrito.getContentPane().setBackground(Color.YELLOW);

        JPanel panelCarrito = new JPanel();
        panelCarrito.setBackground(Color.YELLOW);
        panelCarrito.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        if (carrito.isEmpty()) {
            JLabel labelCarritoVacio = new JLabel("El carrito está vacío");
            labelCarritoVacio.setFont(new Font("Arial", Font.PLAIN, 14));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(10, 0, 10, 0);
            panelCarrito.add(labelCarritoVacio, gbc);
        } else {
            JLabel labelCarrito = new JLabel("Carrito de Compra");
            labelCarrito.setFont(new Font("Arial", Font.BOLD, 16));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(10, 0, 10, 0);
            panelCarrito.add(labelCarrito, gbc);

            int y = 1;
            double total = 0.0;
            for (Map.Entry<String, Integer> entry : carrito.entrySet()) {
                String producto = entry.getKey();
                int cantidad = entry.getValue();
                double precioUnitario = productos.get(obtenerCategoria(producto)).get(producto);
                double subtotal = precioUnitario * cantidad;
                total += subtotal;

                JLabel labelProducto = new JLabel(producto + " x " + cantidad + " - $" + String.format("%.2f", subtotal));
                labelProducto.setFont(new Font("Arial", Font.PLAIN, 12));
                gbc.gridx = 0;
                gbc.gridy = y++;
                gbc.insets = new Insets(5, 10, 5, 10);
                panelCarrito.add(labelProducto, gbc);
            }

            JLabel labelTotal = new JLabel("Total de la Compra: $" + String.format("%.2f", total));
            labelTotal.setFont(new Font("Arial", Font.BOLD, 14));
            gbc.gridx = 0;
            gbc.gridy = y++;
            gbc.insets = new Insets(10, 0, 10, 0);
            panelCarrito.add(labelTotal, gbc);

            JButton botonVaciar = new JButton("Vaciar Carrito");
            botonVaciar.setFont(new Font("Arial", Font.PLAIN, 12));
            botonVaciar.setBackground(Color.RED);
            botonVaciar.setFocusPainted(false);
            botonVaciar.setForeground(Color.BLACK);
            botonVaciar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    vaciarCarrito();
                    ventanaCarrito.dispose();
                }
            });
            gbc.gridx = 0;
            gbc.gridy = y++;
            gbc.insets = new Insets(5, 10, 5, 10);
            panelCarrito.add(botonVaciar, gbc);

            JButton botonConfirmar = new JButton("Confirmar Compra");
            botonConfirmar.setFont(new Font("Arial", Font.PLAIN, 12));
            botonConfirmar.setBackground(Color.RED);
            botonConfirmar.setFocusPainted(false);
            botonConfirmar.setForeground(Color.BLACK);
            botonConfirmar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    confirmarCompra();
                    ventanaCarrito.dispose();
                }
            });
            gbc.gridx = 0;
            gbc.gridy = y;
            gbc.insets = new Insets(5, 10, 5, 10);
            panelCarrito.add(botonConfirmar, gbc);
        }

        ventanaCarrito.add(panelCarrito);
        ventanaCarrito.setVisible(true);
    }

    private void vaciarCarrito() {
        carrito.clear();
    }

    private String obtenerCategoria(String producto) {
        for (Map.Entry<String, Map<String, Double>> entry : productos.entrySet()) {
            if (entry.getValue().containsKey(producto)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void confirmarCompra() {
        carrito.clear();
        JOptionPane.showMessageDialog(this, "¡Gracias por su compra! El carrito ha sido vaciado.", "Compra Confirmada", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SistemaCompras().setVisible(true);
            }
        });
    }
}