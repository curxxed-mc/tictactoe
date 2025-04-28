package net.curxxed.tictactoe.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NormalAI extends AI {

    private Random random;

    public NormalAI(char aiPlayer, char humanPlayer) {
        super(aiPlayer, humanPlayer);
        this.random = new Random();
    }

    @Override
    public int[] getMove(String[][] board) {

        if (random.nextDouble() < 0.55) {
            int[] winningMove = findWinningMove(board, aiPlayer);
            if (winningMove != null) return winningMove;

            int[] blockingMove = findWinningMove(board, humanPlayer);
            if (blockingMove != null) return blockingMove;
        }

        return getRandomMove(board);
    }

    private int[] findWinningMove(String[][] board, char player) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == null || board[row][col].isEmpty()) {
                    board[row][col] = String.valueOf(player);
                    boolean wins = checkWin(board, player);
                    board[row][col] = "";
                    if (wins) return new int[]{row, col};
                }
            }
        }
        return null;
    }

    private boolean checkWin(String[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if (equal(board[i][0], board[i][1], board[i][2], player)) return true;
            if (equal(board[0][i], board[1][i], board[2][i], player)) return true;
        }
        return (equal(board[0][0], board[1][1], board[2][2], player)) ||
                (equal(board[0][2], board[1][1], board[2][0], player));
    }

    private boolean equal(String a, String b, String c, char player) {
        return a != null && b != null && c != null &&
                a.equals(String.valueOf(player)) &&
                b.equals(String.valueOf(player)) &&
                c.equals(String.valueOf(player));
    }

    private int[] getRandomMove(String[][] board) {
        List<int[]> emptyCells = new ArrayList<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == null || board[row][col].isEmpty()) {
                    emptyCells.add(new int[]{row, col});
                }
            }
        }
        return emptyCells.get(random.nextInt(emptyCells.size()));
    }
}
