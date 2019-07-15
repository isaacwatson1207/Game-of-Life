public class Game {

    public static void main(String[] args) {

        int boardSize = 0;

        try {

            boardSize = Integer.parseInt(args[0]);

        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {

            System.out.println("Please enter a number for the board size as the program argument.");
            System.exit(0);
        }

        Board board = new Board(boardSize);
//        board.printBoard();

//        board.reviveCell(2,1);
//        board.reviveCell(0,1);
//        board.reviveCell(1,0);
//        board.reviveCell(1,1);
//        board.printBoard();
//        board.reviveCell(10,11);
//        board.reviveCell(11,10);
//        board.reviveCell(10,10);
//        board.reviveCell(11,11);

        /*
        1. buttons clicked on the board where the user wishes to revive cells
        2. user presses start
        3. buttons on board are made uninteractive
        4. board is updated in a loop until the stop button is pressed
        NOTE for testing could make the loop stop at 100 turns for testing to prevent a crash
         */


        for (int i = 0; i < 100; i++) { // change this to be while stop button not pressed
            board.update();
        }
    }
}
