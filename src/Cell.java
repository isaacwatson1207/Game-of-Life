import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Cell implements ActionListener {

    private boolean isDead = true;
    private boolean dying = false;
    private boolean reviving = false;
    private List<Cell> neighbours = new ArrayList<>(8);
    private int xcoord;
    private int ycoord;
    private int liveNeighbours;
    private JButton button = new JButton();
    private boolean buttonUsable = true;

    public Cell(int xcoord, int ycoord, Board board) {

        this.xcoord = xcoord;
        this.ycoord = ycoord;
        button.addActionListener(this);

        int n = board.getBoardSize();
        button.setBounds(150 + (xcoord * 300) / n, 10 + (ycoord * 300) / n, 250 / n,250 / n);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        board.add(button);

    }

    public void addNeighbours(Board board) {

        addNeighbour(board.getCell(xcoord - 1, ycoord - 1));
        addNeighbour(board.getCell(xcoord, ycoord - 1));
        addNeighbour(board.getCell(xcoord + 1, ycoord - 1));
        addNeighbour(board.getCell(xcoord - 1, ycoord));
        addNeighbour(board.getCell(xcoord + 1, ycoord));
        addNeighbour(board.getCell(xcoord - 1, ycoord + 1));
        addNeighbour(board.getCell(xcoord, ycoord + 1));
        addNeighbour(board.getCell(xcoord + 1, ycoord + 1));
    }

    public void addNeighbour(Cell cell) {

        if (cell != null && !neighbours.contains(cell)) {
            neighbours.add(cell);
        }
    }

    public void die() {
        dying = true;
    }

    public void revive() {
        reviving = true;
    }

    public void makeLive() {
        isDead = false;
        button.setBackground(Color.black);
    }

    public void makeDead() {
        isDead = true;
        button.setBackground(Color.white);

    } // button colour = white

    public boolean isDead() {
        return isDead;
    }

    public void checkLiveNeighbours() {

        liveNeighbours = 0;

        for (Cell neighbour: neighbours) {

            if (!neighbour.isDead) {

                liveNeighbours++;

            }
        }
    }

    public void checkConditions() {

        checkLiveNeighbours();

        if (liveNeighbours < 2) {
            die();
        } else if (liveNeighbours > 3) {
            die();
        } else if (liveNeighbours == 3) {
            revive();
        }
    }

    public void updateState() {

        if (dying) {
            isDead = true;
            dying = false;
            button.setBackground(Color.white);
            //make button white
        } else if (reviving) {
            isDead = false;
            reviving = false;
            button.setBackground(Color.black);
            //make button black
        }
    }

    public void freezeButton() {

        buttonUsable = false;

    }

    public void unfreezeButton() {

        buttonUsable = true;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button && buttonUsable) {

            if (isDead) {
                makeLive();
            } else {
                makeDead();
            }

        }

    }
}
