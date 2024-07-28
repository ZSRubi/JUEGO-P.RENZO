package juegorencito;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inicio {

    public static void main(String[] args) {
        // Crear el marco
        JFrame frame = new JFrame("INICIO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400); // Aumentar el tamaño para acomodar la imagen

        // Cargar la imagen del icono
        ImageIcon icon = new ImageIcon("icons/avion.png"); // Ruta a tu imagen de icono
        frame.setIconImage(icon.getImage());

        // Cargar la imagen de fondo
        ImageIcon fondo = new ImageIcon("src/main/java/com/mavenproject2/fondo.png"); // Reemplaza con la ruta a tu archivo de imagen de fondo

        // Crear el panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Crear un JLabel para la imagen de fondo
        JLabel fondoLabel = new JLabel(fondo);
        fondoLabel.setSize(400, 400); // Ajustar el tamaño del fondo
        fondoLabel.setLayout(new BorderLayout());

        // Crear y configurar la etiqueta de texto
        JLabel textLabel = new JLabel("Menu Principal", SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Crear un panel para la etiqueta de texto y el campo de texto
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(textLabel, BorderLayout.NORTH);

        // Crear y configurar el campo de texto para el nombre
        JTextField nameField = new JTextField(10);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setPreferredSize(new Dimension(150, 30));

        // Añadir la etiqueta y el campo de texto al panel de título
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.add(new JLabel("Nombre:"));
        inputPanel.add(nameField);

        // Crear un JLabel para la imagen del icono
        ImageIcon iconImage = new ImageIcon("icons/avion.png"); // Ruta a tu imagen
        JLabel imageLabel = new JLabel(iconImage);

        // Crear un panel para la imagen del icono
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        imagePanel.add(imageLabel);

        // Añadir el panel de entrada y el panel de imagen al panel de título
        titlePanel.add(inputPanel, BorderLayout.CENTER);
        titlePanel.add(imagePanel, BorderLayout.SOUTH);

        // Añadir el panel de título al panel de fondo en la parte central
        fondoLabel.add(titlePanel, BorderLayout.CENTER);

        // Crear un panel para los botones y centrarlo
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Crear botón "Jugar" con borde redondeado
        JButton botonJugar = new JButton("Jugar");
        botonJugar.setFont(new Font("Arial", Font.BOLD, 16));
        botonJugar.setForeground(Color.WHITE);
        botonJugar.setBackground(new Color(0, 153, 76));
        botonJugar.setOpaque(true);
        botonJugar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        // Establecer el tamaño preferido del botón "Jugar"
        botonJugar.setPreferredSize(new Dimension(150, 40));

        // Crear botón "Regresar" con borde redondeado
        JButton botonRegresar = new JButton("Regresar");
        botonRegresar.setFont(new Font("Arial", Font.BOLD, 16));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.setBackground(new Color(153, 0, 0));
        botonRegresar.setOpaque(true);
        botonRegresar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));

        // Establecer el tamaño preferido del botón "Regresar"
        botonRegresar.setPreferredSize(new Dimension(150, 40));

        // Añadir el listener para el botón "Jugar"
        botonJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear y mostrar la nueva ventana
                BatallaNavalGUI batallaNaval = new BatallaNavalGUI();
                batallaNaval.setVisible(true);
            }
        });

        // Añadir el listener para el botón "Regresar"
        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar el marco actual
                frame.dispose();
            }
        });

        // Añadir los botones al panel de botones
        buttonPanel.add(botonJugar);
        buttonPanel.add(botonRegresar);

        // Añadir el panel de botones al panel de fondo en la parte inferior
        fondoLabel.add(buttonPanel, BorderLayout.SOUTH);

        // Añadir el panel al marco
        frame.add(fondoLabel);

        // Centrar el marco en la pantalla
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // Hacer visible el marco
        frame.setVisible(true);
    }

    void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
