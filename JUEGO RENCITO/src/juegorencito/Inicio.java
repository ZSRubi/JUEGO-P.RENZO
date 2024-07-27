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

        // Crear un panel para los botones y centrarlo
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centrar los botones y añadir espaciado

        // Crear botón "Jugar" con borde redondeado
        JButton botonJugar = new JButton("Jugar");
        botonJugar.setFont(new Font("Arial", Font.BOLD, 16));
        botonJugar.setForeground(Color.WHITE);
        botonJugar.setBackground(new Color(0, 153, 76));
        botonJugar.setOpaque(true);
        botonJugar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20))); // Añadir relleno

        // Establecer el tamaño preferido del botón "Jugar"
        botonJugar.setPreferredSize(new Dimension(150, 40)); // Ajusta el tamaño según tus necesidades

        // Crear botón "Regresar" con borde redondeado
        JButton botonRegresar = new JButton("Regresar");
        botonRegresar.setFont(new Font("Arial", Font.BOLD, 16));
        botonRegresar.setForeground(Color.WHITE);
        botonRegresar.setBackground(new Color(153, 0, 0)); // Cambia el color según tus preferencias
        botonRegresar.setOpaque(true);
        botonRegresar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20))); // Añadir relleno

        // Establecer el tamaño preferido del botón "Regresar"
        botonRegresar.setPreferredSize(new Dimension(150, 40)); // Ajusta el tamaño según tus necesidades

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
                // Cerrar el marco actual (o puedes agregar otra acción aquí)
                frame.dispose();
            }
        });

        // Añadir los botones al panel de botones
        buttonPanel.add(botonJugar);
        buttonPanel.add(botonRegresar);

        // Añadir el panel de botones al panel principal en la parte inferior
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
