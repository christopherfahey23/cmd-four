# Four in a Row

## To Run
From `bin`, run `java Four`.

## Gameplay
Make rows, columns, or diagonal groups of four by dropping pieces into 
the columns. Play against the computer.

## The Algorithm
The computer looks one move ahead. For each possible move, it 
scans the resulting board for groups of 2 and 3 that can be connected 
into winning groups of four (as well as groups of 4). 
It assigns a score to each possible board and plays the move that 
produces the highest-scoring board. (Inspired by https://www.kaggle.com/alexisbcook/one-step-lookahead.)
