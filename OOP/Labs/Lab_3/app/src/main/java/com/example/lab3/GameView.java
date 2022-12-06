package com.example.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class GameView extends View {
    private static final int COLUMNS = 10;
    private static final int ROWS = 17;
    private static final float WALL_THICKNESS = 8;
    private static final int BUTTON_HEIGHT = 165; //hardcoded button height
    private static final Random random = new Random();
    private Cell[][] cells;
    private Paint wallPaint;
    private float cellSize;
    private float hMargin;
    private float wMargin;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(WALL_THICKNESS);

        createMaze();
        generateMaze();
    }

    private void createMaze() {
        cells = new Cell[COLUMNS][ROWS];

        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0 ; j < ROWS; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    private void generateMaze() {
        Stack<Cell> stack = new Stack<>();
        Cell current = cells[0][0];
        Cell next;

        current.visited = true;

        do {
            next = getNeighbour(current);

            if (next != null) {
                removeWall(current, next);
                stack.push(current);
                current = next;
                current.visited = true;
            } else {
                current = stack.pop();
            }
        } while (!stack.empty());
    }

    private Cell getNeighbour(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();

        //left
        if (cell.column > 0) {
            if (!cells[cell.column - 1][cell.row].visited) {
                neighbours.add(cells[cell.column - 1][cell.row]);
            }
        }

        //right
        if (cell.column < COLUMNS - 1) {
            if (!cells[cell.column + 1][cell.row].visited) {
                neighbours.add(cells[cell.column + 1][cell.row]);
            }
        }

        //top
        if (cell.row > 0) {
            if (!cells[cell.column][cell.row - 1].visited) {
                neighbours.add(cells[cell.column][cell.row - 1]);
            }
        }

        //bottom
        if (cell.row < ROWS - 1) {
            if (!cells[cell.column][cell.row + 1].visited) {
                neighbours.add(cells[cell.column][cell.row + 1]);
            }
        }

        Cell result = null;

        if (neighbours.size() > 0) {
            int index = random.nextInt(neighbours.size());
            result = neighbours.get(index);
        }

        return result;
    }

    private void removeWall(Cell current, Cell next) {
        // next higher than current on the same column
        if (current.column == next.column && current.row == next.row + 1) {
            current.topWall = false;
            next.bottomWall = false;
        }

        // next lower than current on the same column
        if (current.column == next.column && current.row == next.row - 1) {
            current.bottomWall = false;
            next.topWall = false;
        }

        // next on the left side of current on the same row
        if (current.column == next.column + 1 && current.row == next.row) {
            current.leftWall = false;
            next.rightWall = false;
        }

        // next on the right side of current on the same row
        if (current.column == next.column - 1 && current.row == next.row) {
            current.rightWall = false;
            next.leftWall = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);

        int width = getWidth();
        int height = getHeight() - BUTTON_HEIGHT;

        if (width / height < COLUMNS / ROWS) {
            cellSize = width / (COLUMNS + 1);
        } else {
            cellSize = height / (ROWS + 1);
        }

        hMargin = (width - COLUMNS * cellSize) / 2;
        wMargin = (height - ROWS * cellSize) / 2;

        canvas.translate(hMargin, wMargin);
        drawMaze(canvas);
    }

    private void drawMaze(Canvas canvas) {
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0 ; j < ROWS; j++) {
                if (cells[i][j].topWall) {
                    canvas.drawLine(
                            i * cellSize,
                            j * cellSize,
                            (i + 1) * cellSize,
                            j * cellSize,
                            wallPaint
                    );
                }

                if (cells[i][j].leftWall) {
                    canvas.drawLine(
                            i * cellSize,
                            j * cellSize,
                            i * cellSize,
                            (j + 1) * cellSize,
                            wallPaint
                    );
                }

                if (cells[i][j].bottomWall) {
                    canvas.drawLine(
                            i * cellSize,
                            (j + 1) * cellSize,
                            (i + 1) * cellSize,
                            (j + 1) * cellSize,
                            wallPaint
                    );
                }

                if (cells[i][j].rightWall) {
                    canvas.drawLine(
                            (i + 1) * cellSize,
                            j * cellSize,
                            (i + 1) * cellSize,
                            (j + 1) * cellSize,
                            wallPaint
                    );
                }
            }
        }
    }
}
