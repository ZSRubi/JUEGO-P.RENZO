package juegorencito;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BatallaNavalGUI extends JFrame {
    private static final int GRID_SIZE = 20;
    private static final int[][][] SHIPS = {
        {{0,0}, {0,1}, {0,2}, {1,1}, {2,0}, {2,1}, {2,2}}, // I-shaped ship
        {{0,0}, {0,1}, {0,2}, {1,1}}, // T-shaped inverted
        {{1,0}, {1,1}, {1,2}, {0,1}},
        {{0,0}, {0,1}, {0,2}, {1,0}, {1,1}},
        {{0,0}, {0,1}, {0,2}, {1,2}, {1,1}},
        {{0,1}, {1,0}, {1,1}, {1,2}, {2,1}},
        {{0,1}, {1,0}, {1,1}, {1,2}, {2,1}},
        {{0,0}},{{0,0}},{{0,0}},{{0,0}},{{0,0}}    
    };
    private JButton[][] playerButtons = new JButton[GRID_SIZE][GRID_SIZE];
    private JButton[][] computerButtons = new JButton[GRID_SIZE][GRID_SIZE];
    private char[][] playerBoard = new char[GRID_SIZE][GRID_SIZE];
    private char[][] computerBoard = new char[GRID_SIZE][GRID_SIZE];
    private JTextArea gameLog = new JTextArea(10, 50);
    private JLabel timerLabel = new JLabel("Tiempo restante: 5s");
    private int playerHits = 0;
    private int computerHits = 0;
    private int playerAttempts = 0;
    private int computerAttempts = 0;
    private boolean playerTurn = true;
    private boolean placingShips = true;
    private int shipIndex = 0;
    private int[][] currentShip = SHIPS[0];
    private Timer turnTimer;
    private Timer countdownTimer;
    private final int TURN_TIME_LIMIT = 5000; // 5 seconds per turn
    private int timeRemaining;
    private JButton resetButton;
    private JButton abandonButton;
    private JButton endTurnButton;
    private String playerName; // Variable para el nombre del jugador

    public BatallaNavalGUI() {
        setTitle("Batalla Naval");
        setSize(1200, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Pedir nombre del jugador antes de continuar
        requestPlayerName();

        initializeBoard(playerBoard);
        initializeBoard(computerBoard);
        placeShips(computerBoard);

        JPanel playerPanel = createBoardPanel(playerButtons, true);
        JPanel computerPanel = createBoardPanel(computerButtons, false);

        gameLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameLog);
        scrollPane.setPreferredSize(new Dimension(1200, 150));

        // Cambiar color del texto del temporizador
        timerLabel.setForeground(Color.RED); // Cambiar el color del texto

        // Panel para el log, temporizador y botones
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Añadir espacio alrededor del panel
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
        splitPane.setDividerLocation(600);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add action listeners to buttons
        resetButton.addActionListener(new ResetButtonListener());
        abandonButton.addActionListener(new AbandonButtonListener());
        endTurnButton.addActionListener(new EndTurnButtonListener());

        log("Coloca tus unidades: " + getCurrentShipName());

        // Initialize turn timer
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

        // Initialize countdown timer
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
        int option = JOptionPane.showConfirmDialog(this, panel, "JUEGO RENCITO:Nombre del Jugador", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
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
        setMinimumSize(new Dimension(1200, 800));
    }

    private JPanel createBoardPanel(JButton[][] buttons, boolean isPlayer) {
        JPanel panel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton button = new JButton();
                buttons[row][col] = button;
                button.setPreferredSize(new Dimension(30, 30));
                if (isPlayer) {
                    button.addActionListener(new PlayerBoardClickListener(row, col));
                } else {
                    button.addActionListener(new ComputerButtonClickListener(row, col));
                    button.setEnabled(false);
                }
                panel.add(button);
            }
        }
        return panel;
    }

    private void initializeBoard(char[][] board) {
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
                            board[row + r][col + c] = 'S';
                        } else {
                            board[row + c][col + r] = 'S';
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
                    return false;
                }
            } else {
                if (row + c >= GRID_SIZE || col + r >= GRID_SIZE || board[row + c][col + r] != '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private void log(String message) {
        gameLog.append(message + "\n");
    }

    private String getCurrentShipName() {
        switch (shipIndex) {
            case 0: return "Forma de barco en I";
            case 1: return "Forma de barco en T";
            case 2: return "Forma de barco en T invertida";
            case 3: return "Forma de tanque";
            case 4: return "Forma de tanque";
            case 5: return "Forma de helicoptero";
            case 6: return "Forma de helicoptero";
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
                        playerButtons[row + r][col + c].setBackground(Color.GREEN);
                        playerBoard[row + r][col + c] = 'S';
                    }
                    shipIndex++;
                    if (shipIndex < SHIPS.length) {
                        currentShip = SHIPS[shipIndex];
                        log("Coloca el siguiente barco. Forma: " + getCurrentShipName());
                    } else {
                        placingShips = false;
                        log("Todos los barcos colocados. Tu turno para atacar.");
                        enableComputerBoard();
                        startPlayerTurn();
                    }
                } else {
                    log("No se puede colocar el barco aquí. Intenta otra ubicación.");
                }
            }
        }
    }

    private void enableComputerBoard() {
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
                button.setBackground(Color.RED);
                log("¡Acertaste en una unidad!");
                playerHits++;
                computerBoard[row][col] = 'H';
            } else {
                button.setBackground(Color.BLUE);
                log("Fallaste.");
                computerBoard[row][col] = 'M';
            }
            playerAttempts++;

            if (playerHits == 12) {
                log("¡Has ganado! Todas las unidades del oponente destruidas.");
                disableBoard(computerButtons);
                return;
            }

            playerTurn = false;
            computerTurn();
        }
    }

    private void computerTurn() {
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(GRID_SIZE);
            col = random.nextInt(GRID_SIZE);
        } while (playerBoard[row][col] == 'H' || playerBoard[row][col] == 'M');

        if (playerBoard[row][col] == 'S') {
            playerButtons[row][col].setBackground(Color.RED);
            log("La computadora acertó en una de tus unidades.");
            computerHits++;
            playerBoard[row][col] = 'H';
        } else {
            playerButtons[row][col].setBackground(Color.BLUE);
            log("La computadora falló.");
            playerBoard[row][col] = 'M';
        }
        computerAttempts++;

        if (computerHits == 12) {
            log("La computadora ha ganado. Todas tus unidades han sido destruidas.");
            disableBoard(playerButtons);
            return;
        }

        playerTurn = true;
        startPlayerTurn();
    }

    private void disableBoard(JButton[][] buttons) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }

    private void startPlayerTurn() {
        timeRemaining = TURN_TIME_LIMIT / 1000;
        timerLabel.setText("Tiempo restante: " + timeRemaining + "s");
        turnTimer.restart();
        countdownTimer.restart();
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
            resetGame();
        }
    }

    private class AbandonButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Mostrar un cuadro de diálogo informativo al usuario
            JOptionPane.showMessageDialog(BatallaNavalGUI.this, 
                                          "Has abandonado el juego.", 
                                          "Juego Abandonado", 
                                          JOptionPane.INFORMATION_MESSAGE);
            // Cerrar la ventana de la aplicación
            System.exit(0); // Termina la aplicación
        }
    }
    

    private class EndTurnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (playerTurn) {
                log("Has terminado tu turno. Turno de la computadora.");
                playerTurn = false;
                computerTurn();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BatallaNavalGUI().setVisible(true));
    }
}
