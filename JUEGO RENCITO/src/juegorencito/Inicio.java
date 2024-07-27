package juegorencito;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inicio {

    public static void main(String[] args) {
        // Crear el marco
        JFrame frame = new JFrame("INICIO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Cargar la imagen del icono
        ImageIcon icon = new ImageIcon("src/main/java/com/mavenproject2/BC.png"); // Reemplaza con la ruta a tu archivo de icono
        // Establecer el icono en el marco
        frame.setIconImage(icon.getImage());

        // Cargar la imagen de fondo
        ImageIcon fondo = new ImageIcon("src/main/java/com/mavenproject2/fondo.png"); // Reemplaza con la ruta a tu archivo de imagen de fondo

        // Crear el panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Crear un JLabel para la imagen de fondo
        JLabel fondoLabel = new JLabel(fondo);
        fondoLabel.setSize(400, 300); // Establecer el tamaño de la imagen
        fondoLabel.setLayout(new BorderLayout()); // Establecer el layout para que la imagen se estire

        // Crear y configurar la etiqueta de texto
        JLabel textLabel = new JLabel("Menu Principal", SwingConstants.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Puedes ajustar el tamaño y estilo de la fuente aquí

        // Añadir la etiqueta al panel en la parte superior
        fondoLabel.add(textLabel, BorderLayout.NORTH);

        // Crear un panel para el botón y centrarlo
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centrar el botón y añadir espaciado

        // Crear botón con borde redondeado
        JButton boton = new JButton("Jugar");
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(0, 153, 76));
        boton.setOpaque(true);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20))); // Añadir relleno

        // Establecer el tamaño preferido del botón
        boton.setPreferredSize(new Dimension(150, 40)); // Ajusta el tamaño según tus necesidades

        // Añadir el listener
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Crear y mostrar la nueva ventana
                BatallaNavalGUI batallaNaval = new BatallaNavalGUI();
                batallaNaval.setVisible(true);
            }
        });

        // Añadir el botón al panel del botón
        buttonPanel.add(boton);

        // Añadir el panel del botón al panel principal en la parte inferior
        fondoLabel.add(buttonPanel, BorderLayout.SOUTH);

        // Añadir el panel al marco
        frame.add(fondoLabel);

        // Hacer visible el marco
        frame.setVisible(true);
    }

    void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}