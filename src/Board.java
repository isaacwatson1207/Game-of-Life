import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Board extends JFrame implements ActionListener {

    private int boardSize;
    private Cell[][] cells = new Cell[boardSize][boardSize];
    private JButton startButton = new JButton("Start");
    private JButton stopButton = new JButton("Stop");
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private boolean isStopped = true;


    public Board(int boardSize) {

        this.boardSize = boardSize;

        setTitle("Game of Life");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(600,600);
        setLayout(null);
        setVisible(true);

        startButton.setBounds(150, 350, 100, 50);
        startButton.setBackground(Color.MAGENTA);
        startButton.setForeground(Color.white);
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        add(startButton);
        startButton.addActionListener(this);

        stopButton.setBounds(350,350,100,50);
        stopButton.setBackground(Color.DARK_GRAY);
        stopButton.setForeground(Color.white);
        stopButton.setBorderPainted(false);
        stopButton.setOpaque(true);
        add(stopButton);
        stopButton.addActionListener(this);

        addCells();
        linkCells();

        repaint();


    }

    public int getBoardSize() {
        return boardSize;
    }

    public void addCells() {

        cells = new Cell[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {

            for (int j = 0; j < boardSize; j++) {

                cells[i][j] = new Cell(j, i, this);

            }

        }

        linkCells();
    }

    public void linkCells() {

        for (int i = 0; i < boardSize; i++) {

            for (int j = 0; j < boardSize; j++) {

                cells[i][j].addNeighbours(this);

            }

        }

    }

    public Cell getCell(int xcoord, int ycoord) {

        try {

            return cells[ycoord][xcoord];

        } catch (ArrayIndexOutOfBoundsException e) {

            return null;
        }
    }

    public void printBoard() {

        for (int i = 0; i < boardSize; i++) {

            System.out.println();

            for (int j = 0; j < boardSize; j++) {

                 Cell currentCell = cells[i][j];

                 if (!currentCell.isDead()) {
                     System.out.print("■ ");
                 } else {
                     System.out.print("□ ");
                 }
            }
        }
        System.out.println();

        //may not need this method once graphics works
    }

    public void update() {

//        if (isStopped) {
//            executorService.shutdownNow();
//        }

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

//        printBoard(); // may not need this when graphical interface works
    }

    public void reviveCell(int xcoord, int ycoord) {

        cells[ycoord][xcoord].makeLive();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == startButton) {

            isStopped = false;

            for (int i = 0; i < boardSize; i++) {

                for (int j = 0; j < boardSize; j++) {

                    cells[j][i].freezeButton();

                }

            }

            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(this::update, 0, 100, TimeUnit.MILLISECONDS);


//            update();

        } else if (e.getSource() == stopButton) {

            isStopped = true;
            executorService.shutdownNow();

            for (int i = 0; i < boardSize; i++) {

                for (int j = 0; j < boardSize; j++) {

                    cells[j][i].unfreezeButton();

                }

            }

        }

    }
}
