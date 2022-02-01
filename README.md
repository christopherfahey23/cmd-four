# Command-4

## To Run
From `bin`, run `java CmdFour`.

## Gameplay
Make rows, columns, or diagonal groups of four by dropping pieces into 
the columns. Play against the computer.

## The Algorithm
The computer looks one move ahead. For each possible move, it 
scans the resulting board for groups of 2 and 3 that can be connected 
into winning groups of four (as well as groups of 4). 
It assigns a score to each possible board and plays the move that 
produces the highest-scoring board. (Inspired by https://www.kaggle.com/alexisbcook/one-step-lookahead.)

Though the AI could be improved if it looked more than one turn ahead, 
I have not yet been able to beat it. 
However, since the user plays first, it is always possible for the user to win with 
perfect gameplay as long the user's first move is in column 4. 
(More info [here](https://en.wikipedia.org/wiki/Connect_Four#Mathematical_solution).)