package com.example.lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {
    private static final int COLUMNS = 7;
    private static final int ROWS = 14;
    private static final float WALL_THICKNESS = 8;
    private static final int BUTTON_HEIGHT = 165; //hardcoded button height
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
    }

    private void createMaze() {
        cells = new Cell[COLUMNS][ROWS];

        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0 ; j < ROWS; j++) {
                cells[i][j] = new Cell(i, j);
            }
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
