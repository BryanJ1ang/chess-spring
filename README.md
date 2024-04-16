## Full Stack Java Chess App

Try out the game [here!](https://chess-c486876bf51a.herokuapp.com/) 

## About the project
This is a webapp Chess platform, ported from my previous desktop [Chess application](https://github.com/BryanJ1ang/Java-Chess). It features a single-player mode against a self-programmed computer opponent and real-time online multiplayer gameplay against other users.

Chess is a game with two players, each starting off with 16 pieces on opposite sides of the board. The objective of the game is to checkmate your opponent's king, that is, when one of your pieces threatens to capture your opponent's King on your next turn AND, the opponent has no legal moves to make. You can find a more detailed explanation on the rules [here](https://www.chess.com/learn-how-to-play-chess).

  ![Demo](https://github.com/BryanJ1ang/chess-spring/blob/master/Demo%20GIF.gif)

## Why I chose this project 
I grew up playing Chess with my dad and for as long as I can remember, I've almost always lost. Sadly, I still lose more than I win, playing against him even today, but hopefully, my Chess engine can beat him. 

## Technologies
- Java
- Spring Boot
- Websocket
- React
- JavaScript

## Features
- Singleplayer mode against AI opponent
  - Supports numerous concurrent games, subject to the server's capacity (stress testing not yet conducted).
- Real-time online multiplayer (play against your friend)
  - Note: multiplayer currently only supports 1 single game at a time. Any client that selects multiplayer will be connected to the same game. (Game is refreshed when a single client disconnects)
- Server-side game logic and validation

## Near Future Plans
There's a lot of possibilities for expanding on this project. The top priority is to finish the multiplayer capabilities of the project. The only reason why my multiplayer only supports 1 game at time
currently is that I need to implement the process for creating and joining different games. Aside from that, there are several possibilities for future improvement.

- Implement the process for creating and joining different multiplayer games
- Strengthening the Chess engine
- Incorporating an opening book for users to practice different strategies
  (basically a list of different moves in the initial stages of a Chess game that have been deeply studied)
- User accounts, match history, and skill rating

## Technical Notes

### Architecture: Strategy Design Pattern
The main part of the game logic was constructed using the Strategy Design Pattern, allowing for each piece to have their 
own unique way of moving around the board and collecting their list of all legal moves. Before discovering this concept, 
I had simply included different functions for my pieces within a single file and needless to say, it was tremendous work navigating 
through hunderds of lines of code. Refactoring it, following this design pattern, made my code much more cleaner and way easier to work 
with when I later decided to implement my AI chess engine.

### Chess Engine
On that note, creating the computer opponent was definitely one of the most rewarding aspects of the project. The engine was went through several different iterations
and was evaluated on its ability to solve several ['chess puzzles'](https://lichess.org/study/WiuSw3ga/c9rkZk4L). Initially, it was just a basic minimax algorithm that often times
took several minutes to return. Optimizing it with alpha-beta pruning, as well as cleaning up the other algorithms that the minimax algorithm depended on, increased the efficiency tremendously. 
Tests that once took 3 or 4 minutes to finish only need around 30 seconds. 


## Citations
Chess piece images used in this project were retrieved from [Wikimedia](https://commons.wikimedia.org/wiki/Category:PNG_chess_pieces/Standard_transparent) and are licensed under [Creative Commons Attribution-Share Alike 3.0 Unported](https://creativecommons.org/licenses/by-sa/3.0/deed.en).

Credit goes to Daniel Fern for his Java Tuples library ([JavaTuples](https://www.javatuples.org/index.html)).



