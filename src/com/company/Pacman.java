package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public class Pacman {


    private Node[][] maze;

    private String inputFileName;

    private String outputFileName;

    private int height;
    private int width;

    private int startX;
    private int startY;


    private static class Node {

        private char content;
        private int row;
        private int col;
        private boolean visited;
        private Node parent;

        public Node(int x, int y, char c) {
            visited = false;
            content = c;
            parent =  null;
            this.row = x;
            this.col = y;
        }

        public boolean isWall() { return content == 'X'; }
        public boolean isVisited() { return visited; }
    }


    public Pacman(String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        buildGraph();
    }

    private boolean inMaze(int index, int bound){
        return index < bound && index >= 0;
    }

    /** helper method to construct the solution path from S to G
     *  NOTE this path is built in reverse order, starting with the goal G.
     */
    private void backtrack(Node end){

        Node current = end.parent;
        while(current.content != 'S') {
            current.content = '.';
            current = current.parent;
        }
    }


    public void writeOutput() {

        try {
            PrintWriter output = new PrintWriter(new FileWriter(outputFileName));

            output.printf("%d %d\n", height, width);
            for (int i = 0; i < height; ++i) {
                for (int j = 0; j < width; ++j) {
                    output.print(maze[i][j].content);
                }
                output.println();
            }

            output.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        String s = "";
        s += height + " " + width + "\n";
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                s += maze[i][j].content + " ";
            }
            s += "\n";
        }
        return s;
    }

    /* helper method to construct a graph from a given input file;
     */
    private void buildGraph() {

        try {

            BufferedReader input = new BufferedReader(new FileReader(inputFileName));

            String[] heightAndWidth = input.readLine().split(" ");
            this.height = Integer.parseInt(heightAndWidth[0]);
            this.width = Integer.parseInt(heightAndWidth[1]);

            this.maze = new Node[height][width];

            for(int i = 0 ; i < this.height;i++) {
                String line = input.readLine();
                for(int j = 0; j < width;j++) {
                    char c = line.charAt(j);
                    if(c == 'S') {
                        startX = j;
                        startY = i;
                    }
                    maze[i][j] = new Node(i,j,c);
                }
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /** obtains all neighboring nodes that have *not* been
     *  visited yet from a given node when looking at the four
     *  cardinal directions: North, South, West, East (IN THIS ORDER)
     */
    public ArrayList<Node> getNeighbors(Node currentNode) {

        Node north, south, east, west;

        north = maze[currentNode.row-1][currentNode.col];
        south = maze[currentNode.row+1][currentNode.col];
        west = maze[currentNode.row][currentNode.col-1];
        east = maze[currentNode.row][currentNode.col+1];

        ArrayList<Node> nodes = new ArrayList<Node>();
        // 1. Inspect the north neighbor
        if(!north.isWall() && !north.visited) {
            north.visited = true;
            north.parent = currentNode;
            nodes.add(north);
        }
        // 2. Inspect the south neighbor
        if(!south.isWall() && !south.visited) {
            south.visited = true;
            south.parent = currentNode;
            nodes.add(south);
        }
        // 3. Inspect the west neighbor
        if(!west.isWall() && !west.visited) {
            west.visited = true;
            west.parent = currentNode;
            nodes.add(west);
        }
        // 4. Inspect the east neighbor
        if(!east.isWall() && !east.visited) {
            east.visited = true;
            east.parent = currentNode;
            nodes.add(east);
        }
        return nodes;
    }


    public void solveBFS() {

        Queue<Node> bfsQ = new LinkedList<Node>();
        bfsQ.add(maze[startY][startX]);

        Node current;
        while(bfsQ.size() != 0) {
            current = bfsQ.poll();
            ArrayList<Node> neighbors = getNeighbors(current);
            bfsQ.addAll(neighbors);

            if(current.content == 'G') {
                backtrack(current);
                break;
            }

        }

    }


    public void solveDFS() {

        Stack<Node> dfsStack = new Stack<Node>();
        dfsStack.add(maze[startY][startX]);
        Node x;
        while(!dfsStack.empty()) {
            x = dfsStack.pop();
            ArrayList<Node> neighbors = getNeighbors(x);

            dfsStack.addAll(neighbors);

            if(x.content == 'G') {
                backtrack(x);
                break;
            }
        }

    }

}