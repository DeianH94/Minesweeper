package models.implementation;

import models.interfaces.Tile;

public class TileImpl implements Tile {
    private boolean isHidden;
    private boolean isMine;
    private int neighboringMines;

    public TileImpl() {
        this.setHidden(true);
        this.setMine(false);
    }

    @Override
    public boolean isHidden() {
        return this.isHidden;
    }

    @Override
    public void setHidden(boolean hidden) {
        this.isHidden = hidden;
    }

    @Override
    public boolean isMine() {
        return this.isMine;
    }

    @Override
    public void setMine(boolean mine) {
        this.isMine = mine;
    }

    @Override
    public int getNeighboringMinesCount() {
        return this.neighboringMines;
    }

    @Override
    public void setNeighboringMinesCount(int neighboringMines) {
        this.neighboringMines = neighboringMines;
    }

    @Override
    public String toString() {
        if (this.isHidden()) {
            return "-";
        } else if (this.isMine()) {
            return "*";
        } else {
            return String.valueOf(this.getNeighboringMinesCount());
        }
    }
}
