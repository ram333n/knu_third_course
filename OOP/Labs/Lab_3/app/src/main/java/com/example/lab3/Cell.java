package com.example.lab3;

public class Cell {
    public int column;
    public int row;
    public boolean topWall = true;
    public boolean leftWall = true;
    public boolean bottomWall = true;
    public boolean rightWall = true;
    public boolean visited = false;

    public Cell(int column, int row) {
        this.column = column;
        this.row = row;
    }
}
