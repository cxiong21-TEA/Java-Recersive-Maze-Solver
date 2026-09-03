# Java Recursive Maze Solver

A Java maze-solving project that uses recursion, inheritance, abstract classes, and object-oriented programming concepts to navigate a maze and collect reachable gold coins.

## Overview

This project implements a `MazeSolver` class that extends an abstract `Maze` class.

The solver reads maze data from an input file, stores the maze in a 2D grid, validates positions, tracks visited cells, finds a valid path to a target, and recursively counts reachable gold coins.

## Features

- Recursive maze traversal
- Pathfinding through a 2D grid
- Gold-coin collection
- Position validation
- Start and target position tracking
- Visited-cell tracking
- Successful path marking
- File-based maze input
- Object-oriented design
- Method overriding

## Technologies and Concepts

- Java
- Recursion
- Inheritance
- Abstract classes
- Method overriding
- Object-Oriented Programming
- 2D arrays
- File input
- Exception handling

## Maze Solver

`MazeSolver.java` contains the main implementation.

The class extends the abstract `Maze` class and overrides required methods for:

- Accessing and modifying the maze grid
- Getting maze size
- Managing start and target positions
- Validating positions
- Detecting gold coins
- Checking available cells
- Marking visited cells
- Marking successful paths
- Traversing the maze
- Collecting reachable gold coins

The traversal algorithm recursively explores four directions:

- Down
- Right
- Up
- Left

When a path reaches the target, the successful route is marked as part of the final path.

## Gold Coin Collection

The project also includes a recursive method that explores reachable maze cells and counts gold coins.

Visited cells are tracked to prevent the recursive search from repeatedly visiting the same locations.

## Position Class

`Position.java` represents a location in the maze using a row and column.

It includes:

- Row and column storage
- Getter methods
- Position comparison
- `equals()` override
- `toString()` override

## Project Files

```text
Java-Recursive-Maze-Solver/
├── README.md
├── MazeSolver.java
└── Position.java
