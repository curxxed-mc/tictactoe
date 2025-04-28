package net.curxxed.tictactoe.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAI extends AI {
    private int difficulty;
    private final Random random;

    public RandomAI(char aiPlayer, char humanPlayer, int difficulty) {
        super(aiPlayer, humanPlayer);
        setDifficulty(difficulty);
        this.random = new Random();
    }

    public void setDifficulty(int difficulty) {
        Random random = new Random();
        this.difficulty = random.nextInt(100) + 1;
    }

    @Override
    public int[] getMove(String[][] board) {
        double randomness = 1.0 - (difficulty / 100.0);

        if (random.nextDouble() < randomness) {
            return makeRandomMove(board);
        }

        if (difficulty < 50) {
            return makeMediumMove(board);
        } else {
            return makeHardMove(board);
        }
    }

    private int[] makeRandomMove(String[][] board) {
        List<int[]> emptyCells = getEmptyCells(board);
        return emptyCells.get(random.nextInt(emptyCells.size()));
    }

    private int[] makeMediumMove(String[][] board) {
        int[] winningMove = findWinningMove(board, aiPlayer);
        if (winningMove != null) {
            return winningMove;
        }

        int[] blockingMove = findWinningMove(board, humanPlayer);
        if (blockingMove != null) {
            return blockingMove;
        }
        return makeRandomMove(board);
    }

    private int[] makeHardMove(String[][] board) {
        int[] winningMove = findWinningMove(board, aiPlayer);
        if (winningMove != null) {
            return winningMove;
        }

        int[] blockingMove = findWinningMove(board, humanPlayer);
        if (blockingMove != null) {
            return blockingMove;
        }
        if (board[1][1] == null || board[1][1].isEmpty()) {
            return new int[]{1, 1};
        }

        int[][] corners = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        for (int[] corner : corners) {
            if (board[corner[0]][corner[1]] == null || board[corner[0]][corner[1]].isEmpty()) {
                return corner;
            }
        }

        return makeRandomMove(board);
    }

    private List<int[]> getEmptyCells(String[][] board) {
        List<int[]> emptyCells = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == null || board[row][col].isEmpty()) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }
        return emptyCells;
    }

    private int[] findWinningMove(String[][] board, char player) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == null || board[row][col].isEmpty()) {
                    board[row][col] = String.valueOf(player);
                    if (checkWin(board, player)) {
                        board[row][col] = "";
                        return new int[]{row, col};
                    }
                    board[row][col] = "";
                }
            }
        }
        return null;
    }

    private boolean checkWin(String[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != null && !board[i][0].isEmpty() &&
                    board[i][1] != null && !board[i][1].isEmpty() &&
                    board[i][2] != null && !board[i][2].isEmpty() &&
                    player == board[i][0].charAt(0) && player == board[i][1].charAt(0) && player == board[i][2].charAt(0)) {
                return true;
            }
            if (board[0][i] != null && !board[0][i].isEmpty() &&
                    board[1][i] != null && !board[1][i].isEmpty() &&
                    board[2][i] != null && !board[2][i].isEmpty() &&
                    player == board[0][i].charAt(0) && player == board[1][i].charAt(0) && player == board[2][i].charAt(0)) {
                return true;
            }
        }
        return (board[0][0] != null && !board[0][0].isEmpty() &&
                board[1][1] != null && !board[1][1].isEmpty() &&
                board[2][2] != null && !board[2][2].isEmpty() &&
                player == board[0][0].charAt(0) && player == board[1][1].charAt(0) && player == board[2][2].charAt(0)) ||
                (board[0][2] != null && !board[0][2].isEmpty() &&
                        board[1][1] != null && !board[1][1].isEmpty() &&
                        board[2][0] != null && !board[2][0].isEmpty() &&
                        player == board[0][2].charAt(0) && player == board[1][1].charAt(0) && player == board[2][0].charAt(0));
    }
}