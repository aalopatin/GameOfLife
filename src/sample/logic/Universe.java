package sample.logic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Universe {

    private int rows;
    private int columns;
    private int[][] currentGeneration;
    private static final int[][] CHEKCED_CELLS_ROW = new int[][]{{-1, -1, -1},{0, 0, 0},{1, 1, 1}};
    private static final int[][] CHEKCED_CELLS_COLUMNS = new int[][]{{-1, 0, 1},{-1, 0, 1},{-1, 0, 1}};
    private static final boolean[][] COUNTED_CELLS = new boolean[][]{{true, true, true},{true, false, true},{true, true, true}};

    public Universe(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        currentGeneration = new int[rows][columns];
        generateRandomField();
    }
    public Universe(int rows, int columns, String patternName) {
        this.rows = rows;
        this.columns = columns;
        currentGeneration = new int[rows][columns];
        setPattern(patternName);
    }

    public boolean isLiveCell(int i, int j) {
        return currentGeneration[i][j] == 1;
    }

    public void nextGeneration(){
        int[][] nextGeneration = new int[rows][columns];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                int countLive = countLiveNeighbours(i, j);
                if (currentGeneration[i][j] == 1) {
                    nextGeneration[i][j] = 1 < countLive && countLive < 4 ? 1 : 0;
                } else {
                    nextGeneration[i][j] = countLive == 3 ? 1 : 0;
                }
            }
        currentGeneration = nextGeneration;
    }

    private void generateRandomField(){
        Random rand = new Random();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++) {
                currentGeneration[i][j] = rand.nextInt(2);
            }
    }

    private void setPattern(String patternName){
        int[][] pattern = getPattern(patternName);
        if (pattern.length == 0) {
            generateRandomField();
        } else {
            for (int i = 0; i < pattern.length; i++) {
                currentGeneration[i] = Arrays.copyOf(pattern[i], currentGeneration[i].length);
            }
        }
    }

    private int countLiveNeighbours(int row, int column){
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int checked_row = row + CHEKCED_CELLS_ROW[i][j];
                int checked_column = column + CHEKCED_CELLS_COLUMNS[i][j];
                if (COUNTED_CELLS[i][j] && isValidCell(checked_row, checked_column)) {
                    if(currentGeneration[checked_row][checked_column] == 1)
                        count++;
                }
            }
        }
        return count;
    }

    private boolean isValidCell(int row, int column) {
        return -1 < row &&
                row < rows &&
                -1 < column &&
                column < columns;

    }

    private int[][] getPattern(String pattern){
        int[][] patternArr = new int[0][0];
        switch (pattern) {
            case "blinker":
                patternArr = new int[][]{
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                        {0, 1, 1, 1, 0},
                        {0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0}
                };
                break;
            case "toad":
                patternArr = new int[][]{
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 1, 1, 0},
                        {0, 1, 1, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0}
                };
                break;
            case "beacon":
                patternArr = new int[][]{
                        {0, 0, 0, 0, 0, 0},
                        {0, 1, 1, 0, 0, 0},
                        {0, 1, 1, 0, 0, 0},
                        {0, 0, 0, 1, 1, 0},
                        {0, 0, 0, 1, 1, 0},
                        {0, 0, 0, 0, 0, 0}
                };
                break;
            case "pulsar":
                patternArr = new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                        {0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                };
                break;
        }
        return patternArr;
    }

}
