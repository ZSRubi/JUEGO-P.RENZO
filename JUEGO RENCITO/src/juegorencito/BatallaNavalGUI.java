package juegorencito;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BatallaNavalGUI extends JFrame {
    private static final int GRID_SIZE = 20; // Tamaño del tablero
    private static final int[][][] SHIPS = {
        {{0,0}, {0,1}, {0,2}, {1,1}, {2,0}, {2,1}, {2,2}}, // Barco en forma de I
        {{0,0}, {0,1}, {0,2}, {1,1}}, // Barco en forma de T invertida
        {{1,0}, {1,1}, {1,2}, {0,1}}, // Barco en forma de T
        {{0,0}, {0,1}, {0,2}, {1,0}, {1,1}}, // Barco en forma de tanque
        {{0,0}, {0,1}, {0,2}, {1,2}, {1,1}}, // Barco en forma de tanque
        {{0,1}, {1,0}, {1,1}, {1,2}, {2,1}}, // Barco en forma de helicóptero
        {{0,1}, {1,0}, {1,1}, {1,2}, {2,1}}, // Barco en forma de helicóptero
        {{0,0}},{{0,0}},{{0,0}},{{0,0}},{{0,0}} // Barcos individuales (soldados)
    };

    private JButton[][] playerButtons = new JButton[GRID_SIZE][GRID_SIZE]; // Botones para el tablero del jugador
    private JButton[][] computerButtons = new JButton[GRID_SIZE][GRID_SIZE]; // Botones para el tablero del ordenador
    private char[][] playerBoard = new char[GRID_SIZE][GRID_SIZE]; // Tablero del jugador
    private char[][] computerBoard = new char[GRID_SIZE][GRID_SIZE]; // Tablero del ordenador
    private JTextArea gameLog = new JTextArea(10, 50); // Área de texto para el registro del juego
    private JLabel timerLabel = new JLabel("Tiempo restante: 5s"); // Etiqueta del temporizador
    private int playerHits = 0; // Contador de aciertos del jugador
    private int computerHits = 0; // Contador de aciertos del ordenador
    private int playerAttempts = 0; // Contador de intentos del jugador
    private int computerAttempts = 0; // Contador de intentos del ordenador
    private boolean playerTurn = true; // Indica si es el turno del jugador
    private boolean placingShips = true; // Indica si el jugador está colocando barcos
    private int shipIndex = 0; // Índice del barco actual
    private int[][] currentShip = SHIPS[0]; // Barco actual que se está colocando
    private Timer turnTimer; // Temporizador para el turno del jugador
    private Timer countdownTimer; // Temporizador de cuenta regresiva
    private final int TURN_TIME_LIMIT = 5000; // Tiempo límite por turno (5 segundos)
    private int timeRemaining; // Tiempo restante para el turno
    private JButton resetButton; // Botón para reiniciar el juego
    private JButton abandonButton; // Botón para abandonar el juego
    private JButton endTurnButton; // Botón para terminar el turno
    private String playerName; // Nombre del jugador

    public BatallaNavalGUI() {
        setTitle("Batalla Naval"); // Título de la ventana
        setSize(1200, 800); // Tamaño de la ventana
        setResizable(false); // La ventana no se puede redimensionar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra la aplicación al cerrar la ventana
        setLayout(new BorderLayout()); // Layout principal de la ventana

        // Pedir nombre del jugador antes de continuar
        requestPlayerName();

        // Inicializar tableros y colocar barcos en el tablero del ordenador
        initializeBoard(playerBoard);
        initializeBoard(computerBoard);
        placeShips(computerBoard);

        // Crear paneles para los tableros del jugador y del ordenador
        JPanel playerPanel = createBoardPanel(playerButtons, true);
        JPanel computerPanel = createBoardPanel(computerButtons, false);

        // Configurar área de texto para el registro del juego
        gameLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameLog);
        scrollPane.setPreferredSize(new Dimension(1200, 150));

        // Configurar etiqueta del temporizador
        timerLabel.setForeground(Color.RED); // Cambiar color del texto a rojo

        // Panel para el log, temporizador y botones
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Espacio alrededor del panel
        bottomPanel.add(timerLabel, BorderLayout.WEST);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        resetButton = new JButton("Restaurar");
        abandonButton = new JButton("Abandonar");
        endTurnButton = new JButton("Fin del turno");
        buttonPanel.add(resetButton);
        buttonPanel.add(abandonButton);
        buttonPanel.add(endTurnButton);
        
        // Añadir panel de botones al panel inferior
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Panel principal que contiene los tableros
        JPanel mainPanel = new JPanel(new BorderLayout());
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playerPanel, computerPanel);
        splitPane.setDividerLocation(600); // Posición del divisor
        mainPanel.add(splitPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Agregar escuchadores de eventos a los botones
        resetButton.addActionListener(new ResetButtonListener());
        abandonButton.addActionListener(new AbandonButtonListener());
        endTurnButton.addActionListener(new EndTurnButtonListener());

        // Inicializar temporizador para el turno del jugador
        turnTimer = new Timer(TURN_TIME_LIMIT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (playerTurn) {
                    log("Tiempo de turno agotado. Turno de la computadora.");
                    playerTurn = false;
                    computerTurn();
                }
            }
        });

        // Inicializar temporizador de cuenta regresiva
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    timerLabel.setText("Tiempo restante: " + timeRemaining + "s");
                } else {
                    countdownTimer.stop();
                }
            }
        });

        // Centrar la ventana
        setLocationRelativeTo(null);
    }

    private void requestPlayerName() {
        // Crear un panel principal con BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240)); // Color de fondo suave
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Espacio alrededor del panel
        
        // Crear un panel para la etiqueta y el campo de texto con GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(240, 240, 240)); // Mismo color de fondo para consistencia
    
        // Configurar GridBagConstraints para los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre los componentes
    
        // Crear la etiqueta
        JLabel label = new JLabel("Introduce tu nombre:");
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
    
        // Crear el campo de texto
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
    
        // Añadir la etiqueta y el campo de texto al panel de entrada
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(label, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputPanel.add(textField, gbc);
    
        // Añadir el panel de entrada al panel principal
        panel.add(inputPanel, BorderLayout.CENTER);
    
        // Mostrar el cuadro de diálogo con el panel personalizado
        int option = JOptionPane.showConfirmDialog(this, panel, "JUEGO RENCITO - Nombre del Jugador", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (option == JOptionPane.OK_OPTION) {
            String name = textField.getText().trim();
            if (!name.isEmpty()) {
                playerName = name;
            } else {
                playerName = "Jugador";
            }
    
            // Mostrar mensaje de bienvenida con formato HTML
            String welcomeMessage = String.format(
                "<html><body style='width: 300px; background-color: #d0e0f0;'><h2>¡Bienvenido al juego Rencito, %s!</h2>" +
                "<p>Estamos encantados de tenerte aquí. ¡Prepárate para un emocionante desafío!</p></body></html>", playerName);
        
            JOptionPane.showMessageDialog(this, welcomeMessage, "Bienvenido al juego Rencito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            playerName = "Jugador";
        }
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        setMinimumSize(new Dimension(1200, 800)); // Tamaño mínimo de la ventana
    }

    private JPanel createBoardPanel(JButton[][] buttons, boolean isPlayer) {
        JPanel panel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton button = new JButton();
                buttons[row][col] = button;
                button.setPreferredSize(new Dimension(30, 30));
                if (isPlayer) {
                    button.addActionListener(new PlayerBoardClickListener(row, col)); // Agregar escuchador para el tablero del jugador
                } else {
                    button.addActionListener(new ComputerButtonClickListener(row, col)); // Agregar escuchador para el tablero del ordenador
                    button.setEnabled(false); // Deshabilitar botones del ordenador inicialmente
                }
                panel.add(button);
            }
        }
        return panel;
    }

    private void initializeBoard(char[][] board) {
        // Inicializar el tablero con '-' para indicar casillas vacías
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = '-';
            }
        }
    }

    private void placeShips(char[][] board) {
        Random random = new Random();

        for (int[][] ship : SHIPS) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(GRID_SIZE);
                int col = random.nextInt(GRID_SIZE);
                boolean horizontal = random.nextBoolean();

                if (canPlaceShip(board, row, col, ship, horizontal)) {
                    for (int[] block : ship) {
                        int r = block[0];
                        int c = block[1];
                        if (horizontal) {
                            board[row + r][col + c] = 'S'; // Marcar parte del barco en el tablero
                        } else {
                            board[row + c][col + r] = 'S'; // Marcar parte del barco en el tablero
                        }
                    }
                    placed = true;
                }
            }
        }
    }

    private boolean canPlaceShip(char[][] board, int row, int col, int[][] ship, boolean horizontal) {
        for (int[] block : ship) {
            int r = block[0];
            int c = block[1];
            if (horizontal) {
                if (row + r >= GRID_SIZE || col + c >= GRID_SIZE || board[row + r][col + c] != '-') {
                    return false; // No se puede colocar el barco
                }
            } else {
                if (row + c >= GRID_SIZE || col + r >= GRID_SIZE || board[row + c][col + r] != '-') {
                    return false; // No se puede colocar el barco
                }
            }
        }
        return true; // Se puede colocar el barco
    }

    private void log(String message) {
        gameLog.append(message + "\n"); // Añadir mensaje al registro del juego
    }

    private String getCurrentShipName() {
        // Obtener nombre de la forma del barco actual
        switch (shipIndex) {
            case 0: return "Forma de barco en I";
            case 1: return "Forma de barco en T";
            case 2: return "Forma de barco en T invertida";
            case 3: return "Forma de tanque";
            case 4: return "Forma de tanque";
            case 5: return "Forma de helicóptero";
            case 6: return "Forma de helicóptero";
            case 7: return "Forma de soldado";
            case 8: return "Forma de soldado";
            case 9: return "Forma de soldado";
            case 10: return "Forma de soldado";
            case 11: return "Forma de soldado";
            default: return "";
        }
    }

    private class PlayerBoardClickListener implements ActionListener {
        private int row;
        private int col;

        public PlayerBoardClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (placingShips) {
                if (canPlaceShip(playerBoard, row, col, currentShip, true)) {
                    for (int[] block : currentShip) {
                        int r = block[0];
                        int c = block[1];
                        playerButtons[row + r][col + c].setBackground(Color.GREEN); // Marcar barco en el tablero del jugador
                        playerBoard[row + r][col + c] = 'S';
                    }
                    shipIndex++;
                    if (shipIndex < SHIPS.length) {
                        currentShip = SHIPS[shipIndex];
                        log("Coloca el siguiente barco. Forma: " + getCurrentShipName());
                    } else {
                        placingShips = false;
                        log("Todos los barcos colocados. Tu turno para atacar.");
                        enableComputerBoard(); // Habilitar tablero del ordenador
                        startPlayerTurn(); // Iniciar turno del jugador
                    }
                } else {
                    log("No se puede colocar el barco aquí. Intenta otra ubicación.");
                }
            }
        }
    }

    private void enableComputerBoard() {
        // Habilitar todos los botones del tablero del ordenador
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                computerButtons[row][col].setEnabled(true);
            }
        }
    }

    private class ComputerButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ComputerButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!playerTurn) return;

            JButton button = (JButton) e.getSource();
            if (computerBoard[row][col] == 'S') {
                button.setBackground(Color.RED); // Marcar acierto
                log("¡Acertaste en una unidad!");
                playerHits++;
                computerBoard[row][col] = 'H';
            } else {
                button.setBackground(Color.BLUE); // Marcar fallo
                log("Fallaste.");
                computerBoard[row][col] = 'M';
            }
            playerAttempts++;

            if (playerHits == 12) {
                log("¡Has ganado! Todas las unidades del oponente destruidas.");
                disableBoard(computerButtons); // Deshabilitar tablero del ordenador
                return;
            }

            playerTurn = false;
            computerTurn(); // Realizar turno de la computadora
        }
    }

    private void computerTurn() {
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(GRID_SIZE);
            col = random.nextInt(GRID_SIZE);
        } while (playerBoard[row][col] == 'H' || playerBoard[row][col] == 'M'); // Evitar celdas ya marcadas

        if (playerBoard[row][col] == 'S') {
            playerButtons[row][col].setBackground(Color.RED); // Marcar acierto
            log("La computadora acertó en una de tus unidades.");
            computerHits++;
            playerBoard[row][col] = 'H';
        } else {
            playerButtons[row][col].setBackground(Color.BLUE); // Marcar fallo
            log("La computadora falló.");
            playerBoard[row][col] = 'M';
        }
        computerAttempts++;

        if (computerHits == 12) {
            log("La computadora ha ganado. Todas tus unidades han sido destruidas.");
            disableBoard(playerButtons); // Deshabilitar tablero del jugador
            return;
        }

        playerTurn = true;
        startPlayerTurn(); // Iniciar turno del jugador
    }

    private void disableBoard(JButton[][] buttons) {
        // Deshabilitar todos los botones del tablero
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }

    private void startPlayerTurn() {
        timeRemaining = TURN_TIME_LIMIT / 1000; // Inicializar tiempo restante
        timerLabel.setText("Tiempo restante: " + timeRemaining + "s");
        turnTimer.restart(); // Reiniciar temporizador del turno
        countdownTimer.restart(); // Reiniciar temporizador de cuenta regresiva
    }

    private void resetGame() {
        log("Reiniciando el juego...");
        playerHits = 0;
        computerHits = 0;
        playerAttempts = 0;
        computerAttempts = 0;
        playerTurn = true;
        placingShips = true;
        shipIndex = 0;
        currentShip = SHIPS[0];
        initializeBoard(playerBoard);
        initializeBoard(computerBoard);
        placeShips(computerBoard);
        resetButtons(playerButtons);
        resetButtons(computerButtons);
        log("Coloca tus unidades: " + getCurrentShipName());
    }

    private void resetButtons(JButton[][] buttons) {
        // Restaurar estado inicial de todos los botones
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                buttons[row][col].setEnabled(true);
                buttons[row][col].setBackground(null);
            }
        }
    }

    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame(); // Llamar a la función para reiniciar el juego
        }
    }

    private class AbandonButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Mostrar mensaje informativo y cerrar la aplicación
            JOptionPane.showMessageDialog(BatallaNavalGUI.this, 
                                          "Has abandonado el juego.", 
                                          "Juego Abandonado", 
                                          JOptionPane.INFORMATION_MESSAGE);
            System.exit(0); // Terminar la aplicación
        }
    }

    private class EndTurnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (playerTurn) {
                log("Has terminado tu turno. Turno de la computadora.");
                playerTurn = false;
                computerTurn(); // Realizar turno de la computadora
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BatallaNavalGUI().setVisible(true)); // Iniciar la aplicación
    }
}
