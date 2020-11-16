package com.company;

public class PacmanTester {

    public static void main(String[] args) {
        Pacman p = new Pacman("mazes/classic.txt", "mazes/classicOutput.txt");

        p.solveDFS();
        p.writeOutput();

        Pacman a = new Pacman("mazes/tinyMaze.txt", "mazes/tinyMazeOutput.txt");

        a.solveDFS();
        a.writeOutput();

        Pacman s = new Pacman("mazes/mediumMaze.txt", "mazes/mediumMazeOutput.txt");

        s.solveBFS();
        s.writeOutput();

        Pacman o = new Pacman("mazes/unsolvable.txt", "mazes/unsolvableOutput.txt");

        o.solveBFS();
        o.writeOutput();

    }
}
