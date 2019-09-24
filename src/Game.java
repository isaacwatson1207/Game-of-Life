public class Game {

    public static void main(String[] args) {

        int boardSize = 0;

        try {

            if (args.length != 1) {
                throw new ArrayIndexOutOfBoundsException();
            }

            // Sets the board size to the value of the program argument
            boardSize = Integer.parseInt(args[0]);

            // Considered invalid to have boards of this size or lower
            if (boardSize <= 1) {
                throw new NumberFormatException();
            }

        } catch (ArrayIndexOutOfBoundsException e) {

            System.out.println("Usage: java Game <board size>");

            System.exit(0);
        } catch (NumberFormatException e) {

            System.out.println("The chosen board size must be an integer >= 2.");
        }

        // Initialises a new map using the input board size
        Board board = new Board(boardSize);
    }
}
