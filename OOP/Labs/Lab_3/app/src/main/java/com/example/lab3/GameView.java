package com.example.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class GameView extends View {
    private static final int COLUMNS = 15;
    private static final int ROWS = 27;
    private static final float WALL_THICKNESS = 8;
    private static final int BUTTON_HEIGHT = 165; //hardcoded button height
    private static final Random random = new Random();

    private Cell[][] cells;
    private Cell player;
    private Cell exit;
    private List<Cell> path;

    private final Paint wallPaint;
    private final Paint playerPaint;
    private final Paint exitPaint;
    private final Paint pathPaint;

    private float cellSize;
    private float hMargin;
    private float wMargin;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.wallPaint = new Paint();
        this.playerPaint = new Paint();
        this.exitPaint = new Paint();
        this.pathPaint = new Paint();

        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(WALL_THICKNESS);

        playerPaint.setColor(Color.RED);
        exitPaint.setColor(Color.BLUE);
        pathPaint.setColor(Color.WHITE);

        createMaze();
        spawn();
        generateMaze();
    }

    private void createMaze() {
        cells = new Cell[COLUMNS][ROWS];

        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0 ; j < ROWS; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }

        path = new ArrayList<>();
    }

    private void spawn() {
        player = cells[0][0];
        exit = cells[COLUMNS - 1][ROWS - 1];
    }

    private void generateMaze() {
        Stack<Cell> stack = new Stack<>();
        Cell current = cells[0][0];
        Cell next;

        current.visited = true;

        do {
            next = getRandomNeighbour(current);

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

    private Cell getRandomNeighbour(Cell cell) {
        List<Cell> neighbours = getNeighboursForGeneration(cell);

        Cell result = null;

        if (neighbours.size() > 0) {
            int index = random.nextInt(neighbours.size());
            result = neighbours.get(index);
        }

        return result;
    }

    private List<Cell> getNeighboursForGeneration(Cell cell) {
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

        return neighbours;
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
        canvas.drawColor(Color.YELLOW);

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
        drawPath(canvas);
        drawCell(player, canvas, playerPaint);
        drawCell(exit, canvas, exitPaint);
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

    private void drawPath(Canvas canvas) {
        for (Cell cell : path) {
            drawCell(cell, canvas, pathPaint);
        }
    }

    private void drawCell(Cell cell, Canvas canvas, Paint paint) {
        float margin = cellSize / 10;

        canvas.drawRect(
                cell.column * cellSize + margin,
                cell.row * cellSize + margin,
                (cell.column + 1) * cellSize - margin,
                (cell.row + 1) * cellSize - margin,
                paint
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();

            float playerCenterX = hMargin + (player.column + 0.5f) * cellSize;
            float playerCenterY = wMargin + (player.row + 0.5f) * cellSize;

            float dx = x - playerCenterX;
            float dy = y - playerCenterY;

            float absDx = Math.abs(dx);
            float absDy = Math.abs(dy);

            if (absDx > cellSize || absDy > cellSize) {
                if (absDx > absDy) {
                    if (dx > 0) {
                        movePlayer(Direction.RIGHT);
                    } else {
                        movePlayer(Direction.LEFT);
                    }
                } else {
                    if (dy > 0) {
                        movePlayer(Direction.DOWN);
                    } else {
                        movePlayer(Direction.UP);
                    }
                }
            }

            return true;
        }

        return super.onTouchEvent(event);
    }

    private void movePlayer(Direction direction) {
        switch (direction) {
            case UP :
                if (!player.topWall) {
                    player = cells[player.column][player.row - 1];
                }

                break;

            case DOWN :
                if (!player.bottomWall) {
                    player = cells[player.column][player.row + 1];
                }

                break;

            case LEFT :
                if (!player.leftWall) {
                    player = cells[player.column - 1][player.row];
                }

                break;

            case RIGHT :
                if (!player.rightWall) {
                    player = cells[player.column + 1][player.row];
                }

                break;
        }

        checkExit();
        invalidate();
    }

    public void restart() {
        createMaze();
        spawn();
        generateMaze();
        invalidate();
    }

    public void findPath() {
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                cells[i][j].visited = false;
            }
        }

        path.clear();
        explore(player, path);

        invalidate();
    }

    private boolean explore(Cell current, List<Cell> path) {
        if (current.visited) {
            return false;
        }

        if (current == exit) {
            return true;
        }

        path.add(current);
        current.visited = true;

        for (Cell neighbour : getNeighbours(current)) {
            if (explore(neighbour, path)) {
                return true;
            }
        }

        path.remove(path.size() - 1);
        return false;
    }

    private void checkExit() {
        if (player == exit) {
            restart();
        }
    }

    private List<Cell> getNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();

        //left
        if (cell.column > 0) {
            if (!cell.leftWall) {
                neighbours.add(cells[cell.column - 1][cell.row]);
            }
        }

        //right
        if (cell.column < COLUMNS - 1) {
            if (!cell.rightWall) {
                neighbours.add(cells[cell.column + 1][cell.row]);
            }
        }

        //top
        if (cell.row > 0) {
            if (!cell.topWall) {
                neighbours.add(cells[cell.column][cell.row - 1]);
            }
        }

        //bottom
        if (cell.row < ROWS - 1) {
            if (!cell.bottomWall) {
                neighbours.add(cells[cell.column][cell.row + 1]);
            }
        }

        return neighbours;
    }
}
