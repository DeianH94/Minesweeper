package models.interfaces;

public interface Board {
    Tile[][] getTiles();

    int findAdjacentMinesCount(int row, int col);

    void reduceCountOfHiddenTilesWithoutMines();

    int getTilesWithoutMinesCount();

    void revealAllMines();

    void moveMineToRandomPosition(int row, int col);

    void showTiles(int row, int col);
}
