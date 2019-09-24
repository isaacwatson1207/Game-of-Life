# Game-of-Life

USAGE
- Compile: javac Game.java
- Run: java Game <board size>
  * Running should bring up a window containing the board
  * Note that board size must be greater than or equal to 2
  * Board size is the number of cells per row and per column. E.G. board size = 2 --> 2X2 grid of cells
  
HOW TO INTERACT
- Idea of this application is to add cells onto the board which are either green or red and see how they interact
- Cell neighbours are regarded as the (maximum of) 9 cells surrounding the one in question
- Cells may be added at the beginning by clicking a colour ('Red' or 'Green' button) and then clicking
  the square(s) where you which to place cells of that colour
- Cells may be removed by clicking on the occupied cell again
- Pressing 'Start' will begin the cell interaction
- To add/remove more cells, press 'Stop', and then once finished press start again to continue
- To minimise the screen, press the middle button at the top-left of the window
- To exit, press the left-most button at the top left of
- **NEEDING RESOLUTION**: Do not attempt to maximise the window as this will affect the organisation of the buttons

CELL SURVIVAL RULES
- A cell dies if:
  * It has less than 2 live neighbours (any colour) OR
  * It has more than 3 live neighbours (any colour)
- A cell is revived to a colour if:
  * It has exactly 3 live neighbours AND
  * The majority of these neighbours are of the colour in question
- A cell changes colour if:
  * It has exactly 3 live neighbours AND
  * The majority of these neighbours are of the opposite colour to
    the cell in question
 
 
