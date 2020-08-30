import models.implementation.BoardImpl;
import models.interfaces.Board;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardTests {
    private final int BEGINNER_DIFFICULTY_BOARD_SIZE = 9;
    private final int INTERMEDIATE_DIFFICULTY_BOARD_SIZE = 16;
    private final int ADVANCED_DIFFICULTY_BOARD_SIZE = 24;
    private final int BEGINNER_DIFFICULTY_MINES = 10;
    private final int INTERMEDIATE_DIFFICULTY_MINES = 40;
    private final int ADVANCED_DIFFICULTY_MINES = 99;

    private Board board;

    @Before
    public void createBeginnerBoard() {
        this.board = new BoardImpl(BEGINNER_DIFFICULTY_BOARD_SIZE,
                BEGINNER_DIFFICULTY_BOARD_SIZE,
                BEGINNER_DIFFICULTY_MINES);
    }

    @Test
    public void checkTileRowMatrixLength() {
        Assert.assertEquals(BEGINNER_DIFFICULTY_BOARD_SIZE, board.getTiles().length);
    }

    @Test
    public void checkTileColumnMatrixLength() {
        Assert.assertEquals(BEGINNER_DIFFICULTY_BOARD_SIZE, board.getTiles()[0].length);
    }

    @Test
    public void checkNumberOfCreatedMines() {
        int mineCount = 0;
        for (int i = 0; i < this.board.getTiles().length; i++) {
            for (int j = 0; j < this.board.getTiles()[i].length; j++) {
                if (this.board.getTiles()[i][j].isMine()) {
                    mineCount++;
                }
            }
        }

        Assert.assertEquals("Wrong number of generated mines",
                BEGINNER_DIFFICULTY_MINES,
                mineCount);
    }
}
