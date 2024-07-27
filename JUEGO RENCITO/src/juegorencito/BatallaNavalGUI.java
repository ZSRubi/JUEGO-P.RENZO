package juegorencito;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BatallaNavalGUI extends JFrame {
    private static final int GRID_SIZE = 20; // Tamaño de la cuadrícula del tablero
    private static final int[] SHIP_SIZES = {5, 4, 3, 3, 2, 2, 2, 2, 2}; // Tamaños de los barcos

    // Matrices de botones para los tableros del jugador y la computadora
    private final JButton[][] playerButtons = new JButton[GRID_SIZE][GRID_SIZE];
    private final JButton[][] computerButtons = new JButton[GRID_SIZE][GRID_SIZE];
    
    // Tableros internos del jugador y de la computadora para el juego
    private final char[][] playerBoard = new char[GRID_SIZE][GRID_SIZE];
    private char[][] computerBoard = new char[GRID_SIZE][GRID_SIZE];
    
    // Área de texto para mostrar el registro del juego
    private JTextArea gameLog = new JTextArea();
    
    // Contadores para los intentos y los impactos de cada jugador
    private int playerHits = 0;
    private int computerHits = 0;
    private int playerAttempts = 0;
    private int computerAttempts = 0;
    
    // Variables para controlar el turno del jugador y la colocación de barcos
    private boolean playerTurn = true;
    private boolean placingShips = true;
    private int shipIndex = 0; // Índice del barco actual que se está colocando
    private int cellsToPlace = SHIP_SIZES[0]; // Tamaño del barco actual que se está colocando
    private JButton resetButton;
    private JButton abandonButton;
    private JButton endTurnButton;
    public BatallaNavalGUI() {
        // Configuración de la ventana principal
        setTitle("Batalla Naval");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inicialización de los tableros del jugador y la computadora
        initializeBoard(playerBoard);
        initializeBoard(computerBoard);
        
        // Colocación aleatoria de barcos en el tablero de la computadora
        placeShips(computerBoard);

        // Creación de los paneles de los tableros del jugador y la computadora
        JPanel playerPanel = createBoardPanel(playerButtons, true);
        JPanel computerPanel = createBoardPanel(computerButtons, false);
        // Create buttons
        resetButton = new JButton("Reset");
        abandonButton = new JButton("Abandon");
        endTurnButton = new JButton("End Turn");
        // Configuración del área de texto para el registro del juego
        gameLog.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameLog);

        // Panel dividido para mostrar ambos tableros lado a lado
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playerPanel, computerPanel);
        splitPane.setDividerLocation(600);

        // Agregar el panel dividido y el registro del juego a la ventana principal
        add(splitPane, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        // Add buttons to panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resetButton);
        buttonPanel.add(abandonButton);
        buttonPanel.add(endTurnButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Add action listeners to buttons
        resetButton.addActionListener(new ResetButtonListener());
        abandonButton.addActionListener(new AbandonButtonListener());
        endTurnButton.addActionListener(new EndTurnButtonListener());
        // Mensaje inicial para la colocación de barcos
        log("Coloca tus barcos. Tamaño actual del barco: " + cellsToPlace + " bloques.");
           
    }

    // Método para crear el panel del tablero con botones
    private JPanel createBoardPanel(JButton[][] buttons, boolean isPlayer) {
        JPanel panel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JButton button = new JButton();
                buttons[row][col] = button;
                button.setPreferredSize(new Dimension(30, 30));
                if (isPlayer) {
                    // Asignar el escuchador de eventos para el tablero del jugador
                    button.addActionListener(new PlayerBoardClickListener(row, col));
                } else {
                    // Asignar el escuchador de eventos para el tablero de la computadora
                    button.addActionListener(new ComputerButtonClickListener(row, col));
                    button.setEnabled(false); // Deshabilitar los botones del tablero de la computadora hasta que se terminen de colocar los barcos
                }
                panel.add(button);
            }
        }
        return panel;
    }

    // Método para inicializar el tablero con caracteres '-'
    private void initializeBoard(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = '-';
            }
        }
    }

    // Método para colocar barcos en el tablero de la computadora
    private void placeShips(char[][] board) {
        Random random = new Random();

        for (int size : SHIP_SIZES) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(GRID_SIZE);
                int col = random.nextInt(GRID_SIZE);
                boolean horizontal = random.nextBoolean();

                // Verificar si se puede colocar el barco en la posición aleatoria
                if (canPlaceShip(board, row, col, size, horizontal)) {
                    for (int i = 0; i < size; i++) {
                        if (horizontal) {
                            board[row][col + i] = 'S'; // Marcar la posición del barco en el tablero
                        } else {
                            board[row + i][col] = 'S'; // Marcar la posición del barco en el tablero
                        }
                    }
                    placed = true; // Indicar que el barco ha sido colocado
                }
            }
        }
    }

    // Método para verificar si un barco se puede colocar en una posición específica
    private boolean canPlaceShip(char[][] board, int row, int col, int size, boolean horizontal) {
        if (horizontal) {
            if (col + size > GRID_SIZE) return false; // Verificar que el barco no se salga del tablero
            for (int i = 0; i < size; i++) {
                if (board[row][col + i] != '-') return false; // Verificar que la posición esté libre
            }
        } else {
            if (row + size > GRID_SIZE) return false; // Verificar que el barco no se salga del tablero
            for (int i = 0; i < size; i++) {
                if (board[row + i][col] != '-') return false; // Verificar que la posición esté libre
            }
        }
        return true; // El barco se puede colocar en la posición dada
    }

    // Método para registrar mensajes en el área de texto del juego
    private void log(String message) {
        gameLog.append(message + "\n");
    }

    // Clase para manejar los eventos de clic en el tablero del jugador
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
                if (canPlaceShip(playerBoard, row, col, cellsToPlace, true)) { // Colocar horizontalmente para simplificar
                    for (int i = 0; i < cellsToPlace; i++) {
                        playerButtons[row][col + i].setBackground(Color.GREEN);
                        playerBoard[row][col + i] = 'S'; // Marcar la posición del barco en el tablero del jugador
                    }
                    shipIndex++;
                    if (shipIndex < SHIP_SIZES.length) {
                        cellsToPlace = SHIP_SIZES[shipIndex];
                        log("Coloca el siguiente barco. Tamaño: " + cellsToPlace + " bloques.");
                    } else {
                        placingShips = false; // Cambiar el estado a no estar colocando barcos
                        log("Todos los barcos colocados. Tu turno para atacar.");
                        enableComputerBoard(); // Habilitar el tablero de la computadora para el ataque
                    }
                } else {
                    log("No se puede colocar el barco aquí. Inténtalo de nuevo.");
                }
            }
        }
    }

    // Método para habilitar el tablero de la computadora para el ataque
    private void enableComputerBoard() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                computerButtons[row][col].setEnabled(true);
            }
        }
    }
private class ResetButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Reset the game
        playerHits = 0;
        computerHits = 0;
        playerAttempts = 0;
        computerAttempts = 0;
        placingShips = true;
        shipIndex = 0;
        cellsToPlace = SHIP_SIZES[0];
        log("Juego reseteado. Coloca tus barcos. Tamaño actual del barco: " + cellsToPlace + " bloques.");
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                playerBoard[i][j] = '-';
                computerBoard[i][j] = '-';
                playerButtons[i][j].setBackground(null);
                computerButtons[i][j].setBackground(null);
                playerButtons[i][j].setText("");
                computerButtons[i][j].setText("");
                computerButtons[i][j].setEnabled(false);
            }
        }
        gameLog.setText(""); // Reset the game log
    }
}

private class AbandonButtonListener implements ActionListener {

    private Inicio inicio;
    public AbandonButtonListener() {
        inicio = new Inicio(); // Crear la instancia de Inicio aquí
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // Cerrar la ventana actual
        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor((JButton) e.getSource());
        currentFrame.dispose();

        // Mostrar la ventana de inicio
        inicio.setVisible(true);
    }
}

    private class EndTurnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // End the player's turn and let the computer play
            playerTurn = false;
            computerTurn();
        }
    }
    // Clase para manejar los eventos de clic en el tablero de la computadora
    private class ComputerButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ComputerButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (playerTurn) {
                playerAttempts++;
                if (computerBoard[row][col] == 'S') {
                    computerButtons[row][col].setText("X");
                    computerButtons[row][col].setBackground(Color.RED);
                    computerBoard[row][col] = 'X'; // Marcar el impacto en el tablero de la computadora
                    playerHits++;
                    log("¡Impacto en (" + row + ", " + col + ")!");
                    if (playerHits == SHIP_SIZES.length) {
                        log("¡Felicidades! Has hundido todos los barcos de la computadora en " + playerAttempts + " intentos.");
                        disableComputerBoard(); // Terminar el juego si todos los barcos han sido hundidos
                    }
                } else if (computerBoard[row][col] == '-') {
                    computerButtons[row][col].setText("O");
                    computerButtons[row][col].setBackground(Color.BLUE);
                    computerBoard[row][col] = 'O'; // Marcar el fallo en el tablero de la computadora
                    log("Agua en (" + row + ", " + col + ").");
                } else {
                    log("Ya has atacado esta posición (" + row + ", " + col + ").");
                }
                playerTurn = false; // Cambiar el turno a la computadora
                computerTurn(); // Realizar el turno de la computadora
            }
        }
    }

    // Método para deshabilitar el tablero de la computadora cuando termina el juego
    private void disableComputerBoard() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                computerButtons[row][col].setEnabled(false);
            }
        }
    }

    // Método para manejar el turno de la computadora
    private void computerTurn() {
        Random random = new Random();
        boolean hit = false;
        while (!hit) {
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);

            if (playerBoard[row][col] == 'S') {
                playerButtons[row][col].setText("X");
                playerButtons[row][col].setBackground(Color.RED);
                playerBoard[row][col] = 'X'; // Marcar el impacto en el tablero del jugador
                computerHits++;
                log("La computadora impactó en (" + row + ", " + col + ")!");
                if (computerHits == SHIP_SIZES.length) {
                    log("La computadora ha hundido todos tus barcos. Juego terminado.");
                    disableComputerBoard(); // Terminar el juego si todos los barcos del jugador han sido hundidos
                }
                hit = true;
            } else if (playerBoard[row][col] == '-') {
                playerButtons[row][col].setText("O");
                playerButtons[row][col].setBackground(Color.BLUE);
                playerBoard[row][col] = 'O'; // Marcar el fallo en el tablero del jugador
                log("La computadora falló en (" + row + ", " + col + ").");
                hit = true;
            }
        }
        playerTurn = true; // Cambiar el turno al jugador
        log("Tu turno.");
        
    }

    // Método principal para ejecutar la aplicación GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BatallaNavalGUI game = new BatallaNavalGUI();
            game.setVisible(true);
        });
    }
}
