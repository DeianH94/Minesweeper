package models.implementation;

import models.interfaces.Board;
import models.interfaces.Tile;

import java.util.Random;

public class BoardImpl implements Board {
    private Tile[][] tiles;
    private int tilesWithoutMinesCount;

    public BoardImpl(int rows, int cols, int numberOfMines) {
        this.createTiles(rows, cols);
        this.generateMines(rows, cols, numberOfMines);
        this.setTilesWithoutMinesCount(rows, cols, numberOfMines);
    }

    private void createTiles(int rows, int cols) {
        this.tiles = new TileImpl[rows][cols];
        for (int i = 0; i < this.getTiles().length; i++) {
            for (int j = 0; j < this.getTiles()[i].length; j++) {
                this.tiles[i][j] = new TileImpl();
            }
        }
    }

    @Override
    public Tile[][] getTiles() {
        return this.tiles;
    }

    private void generateMines(int rows, int cols, int numberOfMines) {
        int placedMinesCount = 0;
        Random rng = new Random();
        //Places the mines at random coordinates
        while (placedMinesCount < numberOfMines) {
            int rowPosition = rng.nextInt(rows);
            int columnPosition = rng.nextInt(cols);

            if (!this.getTiles()[rowPosition][columnPosition].isMine()) {
                this.getTiles()[rowPosition][columnPosition].setMine(true);
                placedMinesCount++;
            }
        }
    }

    @Override
    public int findAdjacentMinesCount(int row, int col) {
        int adjacentMines = 0;
        for(int i = -1; i <= 1; i++) {
            if((row + i < 0) || (row + i >= this.getTiles().length)) {
                continue;
            }

            for(int j = -1; j <= 1; j++) {
                if((col + j < 0) || (col + j >= this.getTiles()[row].length)) {
                    continue;
                }

                if(this.getTiles()[row + i][col + j].isMine()) {
                    adjacentMines++;
                }
            }
        }

        return adjacentMines;
    }

    private void setTilesWithoutMinesCount(int rows, int cols, int numberOfMines) {
        this.tilesWithoutMinesCount = rows * cols - numberOfMines;
    }

    @Override
    public void reduceCountOfHiddenTilesWithoutMines() {
        this.tilesWithoutMinesCount = this.getTilesWithoutMinesCount() - 1;
    }

    @Override
    public int getTilesWithoutMinesCount() {
        return this.tilesWithoutMinesCount;
    }

    @Override
    public void revealAllMines() {
        for (int i = 0; i < this.getTiles().length; i++) {
            for (int j = 0; j < this.getTiles()[i].length; j++) {
                if (this.getTiles()[i][j].isMine()) {
                    this.getTiles()[i][j].setHidden(false);
                }
            }
        }
    }

    @Override
    public void moveMineToRandomPosition(int row, int col) {
        Random rng = new Random();
        int rowPosition;
        int columnPosition;

        //Chooses new coordinates at random until it finds a tile without a mine
        do {
            rowPosition = rng.nextInt(this.getTiles().length);
            columnPosition = rng.nextInt(this.getTiles()[rowPosition].length);
        } while (this.getTiles()[rowPosition][columnPosition].isMine());

        this.getTiles()[row][col].setMine(false);
        this.getTiles()[rowPosition][columnPosition].setMine(true);
    }

    public void showTiles(int row, int col) {
        //Checks out of bounds
        if (row >= this.getTiles().length
                || row < 0
                || col >= this.getTiles()[row].length
                || col < 0
                || !this.getTiles()[row][col].isHidden()) {
            return;
        }

        this.getTiles()[row][col].setHidden(false);
        this.getTiles()[row][col].setNeighboringMinesCount(this.findAdjacentMinesCount(row, col));
        this.reduceCountOfHiddenTilesWithoutMines();
        if (this.getTiles()[row][col].getNeighboringMinesCount() == 0)  {
            //if there are no mines, recursively show the surrounding mines
            showTiles(row + 1, col);
            showTiles(row + 1, col + 1);
            showTiles(row + 1, col - 1);
            showTiles(row - 1, col);
            showTiles(row - 1, col + 1);
            showTiles(row - 1, col - 1);
            showTiles(row, col + 1);
            showTiles(row, col - 1);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("   ");
        for (int i = 0; i < this.getTiles()[0].length; i++) {
            if (i < 10) {
                builder.append("  ");
            } else {
                builder.append(" ");
            }
            
            builder.append(i);
        }

        builder.append(System.lineSeparator()).append(System.lineSeparator());
        for (int i = 0; i < this.getTiles().length; i++) {
            builder.append(i);
            if (i < 10) {
                builder.append("  ");
            } else {
                builder.append(" ");
            }

            for (int j = 0; j < this.getTiles()[i].length; j++) {
                builder.append("  ").append(this.getTiles()[i][j]);
            }

            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }
}
