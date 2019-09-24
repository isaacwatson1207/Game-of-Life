import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Individual members of the population
 */
public class Cell implements ActionListener {


    // Colours (placing here makes it easier to change in future)

    private static final Color DEFAULT = Color.gray;
    private static final Color RED = Color.red;
    private static final Color GREEN = Color.green;

    // Attributes of each cell
    private static Color color = GREEN;
    private Color myColor = null;
    private boolean isDead = true;
    private boolean dying = false;
    private boolean reviving = false;
    private List<Cell> neighbours = new ArrayList<>(8);
    private int xcoord;
    private int ycoord;
    private int liveNeighbours;
    private JButton button = new JButton();
    private boolean buttonUsable = true;

    /**
     *
     * @param xcoord The x-coordinate of the cell on the map.
     * @param ycoord The y-coordinate of the cell on the map.
     * @param board The map on which the cell is situated.
     */
    public Cell(int xcoord, int ycoord, Board board) {

        this.xcoord = xcoord;
        this.ycoord = ycoord;
        button.addActionListener(this);

        int n = board.getBoardSize();
        button.setBounds(100 + (xcoord * 300) / n, 10 + (ycoord * 300) / n, 250 / n,250 / n);
        button.setBackground(DEFAULT);
        button.setForeground(DEFAULT);
        button.setOpaque(true);
        button.setBorderPainted(false);
        board.getPanel().add(button);

    }

    /**
     * Stores a list referencing each neighbouring cell on the map.
     * @param board The map on which the cell is situated.
     */
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

    /**
     * Adds a neighbour to the list provided it is not already added and it is an actual cell.
     * @param cell The neighbour to be added.
     */
    public void addNeighbour(Cell cell) {

        if (cell != null && !neighbours.contains(cell)) {
            neighbours.add(cell);
        }
    }

    /**
     * Confirms that the cell will die at the end of this turn.
     */
    public void die() {
        dying = true;
    }

    /**
     * Confirms that the cell will be revived at the end of this turn.
     * @param color The colour that the cell will be revived as.
     */
    public void revive(Color color) {
        reviving = true;
        myColor = color;
    }

    /**
     * Immediately makes a cell alive again as the current value of color.
     * Invoked when a user is editing the board.
     */
    public void makeLive() {
        isDead = false;
        button.setBackground(color);
        myColor = color;
    }

    /**
     * Immediately kills a cell.
     * Invoked when a user is editing the board.
     */
    public void makeDead() {
        isDead = true;
        button.setBackground(DEFAULT);
        myColor = null;

    }

    public boolean isDead() {
        return isDead;
    }

    /**
     * Updates the value of how many live neighbours the cell has.
     */
    public void checkLiveNeighbours() {

        liveNeighbours = 0;

        for (Cell neighbour: neighbours) {

            if (!neighbour.isDead) {

                liveNeighbours++;

            }
        }
    }

    /**
     *
     * @param neighbourColor The colour to be checked.
     * @return The number of neighbours which are of the specified colour.
     */
    public int checkNeighboursOfColor(Color neighbourColor) {

        int numOfColor = 0;

        for (Cell neighbour: neighbours) {

            if (neighbour.myColor == neighbourColor) {

                numOfColor++;

            }

        }

        return numOfColor;

    }

    /**
     * Updates a cell's status according to the rules of the map.
     */
    public void checkConditions() {

        checkLiveNeighbours();

        if (liveNeighbours < 2) {
            die();
        } else if (liveNeighbours > 3) {
            die();
        } else if (liveNeighbours == 3) {

            if (myColor != null) {

                if (checkNeighboursOfColor(myColor) < 2) {

                    if (myColor == GREEN) {
                        revive(RED);
                    } else {
                        revive(GREEN);
                    }
                }
            } else if (checkNeighboursOfColor(GREEN) < 2) {
                revive(RED);
            } else {
                revive(GREEN);
            }
        }
    }

    /**
     * Makes a dying cell die or a reviving cell alive as the color it was revived as.
     */
    public void updateState() {

        if (dying) {
            isDead = true;
            dying = false;
            button.setBackground(DEFAULT);
            myColor = null;
            //make button white
        } else if (reviving) {
            isDead = false;
            reviving = false;
            button.setBackground(myColor);
            //make button black
        }
    }

    /**
     * Makes the cell unclickable on the displayed map.
     */
    public void freezeButton() {

        buttonUsable = false;
    }

    /**
     * Makes the cell clickable on the displayed map.
     */
    public void unfreezeButton() {

        buttonUsable = true;

    }

    @Override
    /**
     * Defines the reaction to if the button representing the cell is clicked.
     * Button presses only trigger status changes if the button is unfrozen.
     * If the cell is dead, it is made alive.
     * If the cell is alive, it is made dead.
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button && buttonUsable) {

            if (isDead) {
                makeLive();
            } else {
                makeDead();
            }

        }

    }

    /**
     * Sets the value of the static color attribute.
     * @param color1 The new value of color.
     */
    public static void setColor(Color color1) {
        color = color1;
    }
}
