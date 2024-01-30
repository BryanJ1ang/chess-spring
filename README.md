## Full Stack Java Chess App

Try out the game [here!](https://chess-c486876bf51a.herokuapp.com/) See if you can beat my Chess engine.

## About the project
This is a Chess webapp, featuring singleplayer against an computer opponent and real-time online multiplayer against another person.

Chess is a game featuring two players, both starting off with 16 pieces on opposite sides of the board. The objective of the game is to checkmate your opponent's king, that is, when one of your pieces threatens to capture your opponent's King on your next term AND, the opponent has no legal moves. You can find a more detailed explanation on the rules [here](https://www.chess.com/learn-how-to-play-chess). 

## Why I chose this project 
I grew up playing Chess with my dad and for as long as I can remember, I've almost always lost. Sadly, I still lose more than I win, playing against him even today, but hopefully, my Chess engine can beat him. 

## Features
- Singleplayer mode against AI opponent
  - Supports as many concurrent games as the server can handle (haven't stress tested it)
- Real-time online multiplayer (play againt your friend)
  - Note: multiplayer currently only supports 1 single game at a time. Any client that selects multiplayer will be connected to the same game. (Game is refreshed when a single client disconnects)
- Server-side game logic and validation

## Technologies
- Java
- Spring Boot
- Websocket
- React
- JavaScript

## Technical Notes

### Strategy Design Pattern
The main part of the game logic was constructed using the Strategy Design Pattern, allowing for each piece to have their 
own unique way of moving around the board and collecting their list of all legal moves. Before discovering this was a thing, 
I had simply included different functions for my pieces within a single file and needless to say, it was tremendous work navigating 
through hunderds of lines of code. Refactoring it, following this design pattern, made my code much more cleaner and way easier to work 
with when I later decided to implement my AI chess engine.

### Chess Engine
On that note, creating the computer opponent was definitely one of the most rewarding aspects of the project. The engine was created went through several different iterations
and was evaluated on its ability to solve several ['chess puzzles'](https://lichess.org/study/WiuSw3ga/c9rkZk4L). Initially, it was just a basic minimax algorithm that often times
took several minutes to return. Optimizing it with alpha-beta pruning, as well as cleaning up the other algorithms that the minimax algorithm depended on, increased the efficiency tremendously. 
Tests that once took 3 or 4 minutes to finish now within 30s. 


## Citations
Chess piece images used in this project are were retrieved from [Wikimedia](https://commons.wikimedia.org/wiki/Category:PNG_chess_pieces/Standard_transparent) and are licensed under [Creative Commons Attribution-Share Alike 3.0 Uported](https://creativecommons.org/licenses/by-sa/3.0/deed.en).

Credit goes to Daniel Fern for his Java Tuples library ([JavaTuples](https://www.javatuples.org/index.html)).



