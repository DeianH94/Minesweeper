import models.implementation.TileImpl;
import models.interfaces.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class TileTests {
    private final String UNREVEALED_TILE_CHARACTER = "-";
    private final String MINE_CHAR = "*";
    private final int MAXIMUM_NUMBER_OF_NEIGHBORING_MINES = 9;

    private Tile testTile;

    @Before
    public void createTile() {
        this.testTile = new TileImpl();
    }

    @Test
    public void hiddenTileShouldReturnDash() {
        this.testTile.setHidden(true);
        Assert.assertEquals("Wrong character",
                UNREVEALED_TILE_CHARACTER,
                this.testTile.toString());
    }

    @Test
    public void revealedTileShouldReturnNeighboringMinesCount() {
        Random rng = new Random();
        int neighboringMinesCount = rng.nextInt(MAXIMUM_NUMBER_OF_NEIGHBORING_MINES);
        this.testTile.setNeighboringMinesCount(neighboringMinesCount);
        this.testTile.setHidden(false);
        Assert.assertEquals("Wrong count",
                String.valueOf(neighboringMinesCount),
                this.testTile.toString());
    }

    @Test
    public void revealedMinesShouldReturnMineCharacter() {
        this.testTile.setMine(true);
        this.testTile.setHidden(false);
        Assert.assertEquals("Wrong character",
                MINE_CHAR,
                this.testTile.toString());
    }
}
