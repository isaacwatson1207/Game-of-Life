import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The map on which the cells are situated.
 */
public class Board extends JFrame implements ActionListener {

    // Attributes of the map
    private int boardSize;
    private Cell[][] cells = new Cell[boardSize][boardSize];

    private JButton startButton = new JButton("Start");
    private JButton stopButton = new JButton("Stop");

    private JButton greenButton = new JButton("Green");
    private JButton redButton = new JButton("Red");

    private JPanel panel = new JPanel();
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private boolean isStopped = true;
    private boolean isStarted = false;




    /**
     *
     * @param boardSize The number of cells to be on the board displayed on the frame
     */
    public Board(int boardSize) {

        this.boardSize = boardSize;

        // Initialises the frame where the content will be displayed
        setTitle("Game of Life");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600, 700);
        setLayout(null);
        setVisible(true);

        // Adds a grey panel to the frame
        panel.setBounds(50,5, 500, 550);
        panel.setBackground(Color.darkGray); // colour change
        panel.setOpaque(true);
        add(panel);

        // Adds the start button to the panel
        startButton.setBounds(100, 350, 100, 50);
        addButton(startButton, Color.magenta, Color.white);

        // Adds the stop button to the panel
        stopButton.setBounds(300, 350, 100, 50);
        addButton(stopButton, Color.yellow, Color.darkGray);

        // Adds the red button to the panel
        redButton.setBounds(100, 450, 100, 50);
        addButton(redButton, Color.red, Color.white);

        // Adds the green button to the panel
        greenButton.setBounds(300, 450, 100, 50);
        addButton(greenButton, Color.green, Color.darkGray);

        // Adds each cell to the map and registers their neighbours
        addCells();
        linkCells();

        repaint();


    }

    /**
     * Adds a button to the panel.
     * @param button The button to be added.
     * @param background The background colour of the button.
     * @param foreground The foreground colour of the button.
     */
    public void addButton(JButton button, Color background, Color foreground) {

        button.setBackground(background);
        button.setForeground(foreground);
        button.setBorderPainted(false);
        button.setOpaque(true);
        panel.add(button);
        button.addActionListener(this);
    }

    public JPanel getPanel() { return panel; }

    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Initialises a new cell for each position on the map.
     */
    public void addCells() {

        cells = new Cell[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {

            for (int j = 0; j < boardSize; j++) {

                cells[i][j] = new Cell(j, i, this);

            }

        }

        linkCells();
    }

    /**
     * Invokes the addNeighbours method for each cell on the map.
     */
    public void linkCells() {

        for (int i = 0; i < boardSize; i++) {

            for (int j = 0; j < boardSize; j++) {

                cells[i][j].addNeighbours(this);

            }

        }

    }

    /**
     *
     * @param xcoord The x-coordinate of the cell to be returned.
     * @param ycoord The y-coordinate of the cell to be returned.
     * @return The cell at the coordinate, null if the coordinate is invalid.
     */
    public Cell getCell(int xcoord, int ycoord) {

        try {

            return cells[ycoord][xcoord];

        } catch (ArrayIndexOutOfBoundsException e) {

            return null;
        }
    }

    /**
     * Checks the conditions of each cell, and then updates the status of each cell accordingly.
     */
    public void update() {

        for (int i = 0; i < boardSize; i++) {

            for (int j = 0; j < boardSize; j++) {

                cells[i][j].checkConditions();

            }
        }

        for (int i = 0; i < boardSize; i++) {

            for (int j = 0; j < boardSize; j++) {

                cells[i][j].updateState();

            }
        }
    }

    @Override
    /**
     * Defines what should happen if one of the buttons in this class is clicked.
     * If paused and start is clicked, then the map is updated every 100 milliseconds.
     * If unpaused and stop is clicked, then the map is paused.
     * If green is clicked, then the value of color in the cell class is set to green.
     * If red is clicked, then the value of color in the cell class is set to red.
     */
    public void actionPerformed(ActionEvent e) {

        // If the start button is clicked
        if (e.getSource() == startButton && !isStarted) {

            isStopped = false;

            // Freezes each cell's button
            for (int i = 0; i < boardSize; i++) {

                for (int j = 0; j < boardSize; j++) {

                    cells[j][i].freezeButton();

                }
            }

            isStarted = true;

            // Invokes the update method every 100 milliseconds
            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(this::update, 0, 100, TimeUnit.MILLISECONDS);

        // If the stop button is clicked
        } else if (e.getSource() == stopButton && !isStopped) {

            isStopped = true;

            // Stops the update executor service
            executorService.shutdownNow();

            isStarted = false;

            // Unfreezes each cell's button
            for (int i = 0; i < boardSize; i++) {

                for (int j = 0; j < boardSize; j++) {

                    cells[j][i].unfreezeButton();

                }

            }

        // If the green button is pressed
        } else if (e.getSource() == greenButton) {
            Cell.setColor(Color.green); //colour change

        // If the red button is pressed
        } else if (e.getSource() == redButton) {
            Cell.setColor(Color.red); //colour change
        }

    }
}
