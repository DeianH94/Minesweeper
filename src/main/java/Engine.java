import models.implementation.BoardImpl;
import models.interfaces.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Engine {
    private final int BEGINNER_DIFFICULTY_BOARD_SIZE = 9;
    private final int INTERMEDIATE_DIFFICULTY_BOARD_SIZE = 16;
    private final int ADVANCED_DIFFICULTY_BOARD_SIZE = 24;
    private final int BEGINNER_DIFFICULTY_MINES = 10;
    private final int INTERMEDIATE_DIFFICULTY_MINES = 40;
    private final int ADVANCED_DIFFICULTY_MINES = 99;

    private Board board;

    public void run() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        boolean isGameOver = false;
        boolean isGameWon = false;
        this.setDifficulty(reader);

        boolean isFirstMove = true;
        //Main loop
        while (!isGameOver) {
            System.out.println("Current Status of Board : " + System.lineSeparator());
            System.out.println(this.getBoard());
            System.out.println("Enter your move, (row, column) -> ");
            try {
                int[] coordinates = Arrays.stream(reader.readLine().split("\\s+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                int row = coordinates[0];
                int col = coordinates[1];

                validateUserCoordinates(row, col);

                if (isFirstMove && this.getBoard().getTiles()[row][col].isMine()) {
                    this.getBoard().moveMineToRandomPosition(row, col);
                }

                this.getBoard().showTiles(row, col);
                if (this.getBoard().getTiles()[row][col].isMine()) {
                    isGameOver = true;
                    this.getBoard().revealAllMines();
                } else if (this.getBoard().getTilesWithoutMinesCount() == 0) {
                        isGameWon = true;
                        isGameOver = true;
                }

                isFirstMove = false;
            } catch (IndexOutOfBoundsException | IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }

        System.out.println(this.getBoard());
        if (isGameWon) {
            System.out.println("Game won!");
        } else {
            System.out.println("You lost!");
        }
    }

    private void validateUserCoordinates(int row, int col) {
        if (row < 0 || row >= this.getBoard().getTiles().length) {
            throw new IndexOutOfBoundsException("Invalid row value");
        }

        if (col < 0 || col >= this.getBoard().getTiles()[row].length) {
            throw new IndexOutOfBoundsException("Invalid column value");
        }

        if (!this.getBoard().getTiles()[row][col].isHidden()) {
            throw new IllegalArgumentException("This tile is already revealed");
        }
    }

    private void setDifficulty(BufferedReader reader) throws IOException {
        System.out.println("Enter the Difficulty Level");
        System.out.printf("Press 0 for BEGINNER (%s * %s Cells and %s Mines)" + System.lineSeparator(),
                BEGINNER_DIFFICULTY_BOARD_SIZE,
                BEGINNER_DIFFICULTY_BOARD_SIZE,
                BEGINNER_DIFFICULTY_MINES);
        System.out.printf("Press 1 for INTERMEDIATE (%s * %s Cells and %s Mines)" + System.lineSeparator(),
                INTERMEDIATE_DIFFICULTY_BOARD_SIZE,
                INTERMEDIATE_DIFFICULTY_BOARD_SIZE,
                INTERMEDIATE_DIFFICULTY_MINES);
        System.out.printf("Press 2 for ADVANCED (%s * %s Cells and %s Mines)" + System.lineSeparator(),
                ADVANCED_DIFFICULTY_BOARD_SIZE,
                ADVANCED_DIFFICULTY_BOARD_SIZE,
                ADVANCED_DIFFICULTY_MINES);

        boolean validDifficultyLevel = false;
        while (!validDifficultyLevel) {
            try {
                int difficulty = Integer.parseInt(reader.readLine());
                if (difficulty < 0 || difficulty > 2) {
                    throw new IllegalArgumentException("Invalid number");
                }

                validDifficultyLevel = true;
                this.createBoard(difficulty);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a number between 0 and 2");
            } catch (IllegalArgumentException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private void createBoard(int difficulty) {
        switch (difficulty) {
            case 0:
                this.board = new BoardImpl(BEGINNER_DIFFICULTY_BOARD_SIZE,
                        BEGINNER_DIFFICULTY_BOARD_SIZE,
                        BEGINNER_DIFFICULTY_MINES);
                break;
            case 1:
                this.board = new BoardImpl(INTERMEDIATE_DIFFICULTY_BOARD_SIZE,
                        INTERMEDIATE_DIFFICULTY_BOARD_SIZE,
                        INTERMEDIATE_DIFFICULTY_MINES);
                break;
            case 2:
                this.board = new BoardImpl(ADVANCED_DIFFICULTY_BOARD_SIZE,
                        ADVANCED_DIFFICULTY_BOARD_SIZE,
                        ADVANCED_DIFFICULTY_MINES);
                break;
        }
    }

    public Board getBoard() {
        return this.board;
    }
}
