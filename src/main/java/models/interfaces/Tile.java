package models.interfaces;

public interface Tile {
    boolean isHidden();

    void setHidden(boolean hidden);

    boolean isMine();

    void setMine(boolean mine);

    int getNeighboringMinesCount();

    void setNeighboringMinesCount(int neighboringMines);
}
