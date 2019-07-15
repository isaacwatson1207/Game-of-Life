import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JFrame implements ActionListener {

    private int boardSize;
    private Cell[][] cells = new Cell[boardSize][boardSize];
    private JButton startButton = new JButton("Start");
    private JButton stopButton = new JButton("Stopped");

    private boolean isStopped = false;


    public Board(int boardSize) {

        this.boardSize = boardSize;

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(300,300);
        setLayout(null);
        setVisible(true);

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

            for (int i = 0; i < boardSize; i++) {

                for (int j = 0; j < boardSize; j++) {

                    cells[j][i].freezeButton();

                }

            }

        }

    }
}
