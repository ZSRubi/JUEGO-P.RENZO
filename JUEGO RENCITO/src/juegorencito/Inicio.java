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
        frame.setSize(400, 400); // Ajustar el tamaño para acomodar los componentes

        // Crear el panel principal con color de fondo
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(175, 175, 155)); // Color verde ceniza

        // Crear y configurar la etiqueta de texto
        JLabel textLabel = new JLabel("Menu Principal");
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));
        textLabel.setForeground(Color.WHITE); // Asegúrate de que el texto sea visible sobre el fondo
        textLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto horizontalmente

        // Crear un panel para la etiqueta de texto
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setOpaque(false); // Hacer que el panel de título sea transparente
        titlePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0)); // Añadir espacio arriba
        titlePanel.add(textLabel, BorderLayout.NORTH);

        // Crear y configurar el campo de texto para el nombre
        JTextField nameField = new JTextField(10);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setPreferredSize(new Dimension(150, 30));

        // Añadir la etiqueta y el campo de texto al panel de entrada
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setOpaque(false); // Hacer que el panel de entrada sea transparente
        inputPanel.add(new JLabel("Nombre:"));
        inputPanel.add(nameField);

        // Crear un panel contenedor para centrar el inputPanel en el panel principal
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setOpaque(false); // Hacer que el panel central sea transparente
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(inputPanel, gbc);

        // Crear un panel para los botones y centrarlo
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false); // Hacer que el panel de botones sea transparente

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

        // Añadir el listener para el botón "Jugar"
        botonJugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear y mostrar la nueva ventana
                BatallaNavalGUI batallaNaval = new BatallaNavalGUI();
                batallaNaval.setVisible(true);
            }
        });

        // Añadir el botón al panel de botones
        buttonPanel.add(botonJugar);

        // Añadir los paneles al panel principal
        panel.add(titlePanel, BorderLayout.NORTH); // Añadir el título en la parte superior
        panel.add(centerPanel, BorderLayout.CENTER); // Centrar el panel de entrada en el centro
        panel.add(buttonPanel, BorderLayout.SOUTH); // Añadir los botones en la parte inferior

        // Añadir el panel al marco
        frame.add(panel);

        // Centrar el marco en la pantalla
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);

        // Hacer visible el marco
        frame.setVisible(true);
    }
}
