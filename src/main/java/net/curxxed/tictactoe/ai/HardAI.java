package net.curxxed.tictactoe.ai;

public class HardAI extends AI {

    public HardAI(char aiPlayer, char humanPlayer) {
        super(aiPlayer, humanPlayer);
    }

    @Override
    public int[] getMove(String[][] board) {
        int[] winningMove = findWinningMove(board, aiPlayer);
        if (winningMove != null) return winningMove;
        int[] blockingMove = findWinningMove(board, humanPlayer);
        if (blockingMove != null) return blockingMove;
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == null || board[row][col].isEmpty()) {
                    board[row][col] = String.valueOf(aiPlayer);
                    int score = minimax(board, 0, false);
                    board[row][col] = "";
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{row, col};
                    }
                }
            }
        }
        return bestMove;
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

    private int minimax(String[][] board, int depth, boolean isMaximizing) {
        String result = checkWinner(board);
        if (result != null) {
            if (result.equals(String.valueOf(aiPlayer))) return 10 - depth;
            if (result.equals(String.valueOf(humanPlayer))) return depth - 10;
            return 0;
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == null || board[row][col].isEmpty()) {
                        board[row][col] = String.valueOf(aiPlayer);
                        int eval = minimax(board, depth + 1, false);
                        board[row][col] = "";
                        maxEval = Math.max(maxEval, eval);
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == null || board[row][col].isEmpty()) {
                        board[row][col] = String.valueOf(humanPlayer);
                        int eval = minimax(board, depth + 1, true);
                        board[row][col] = "";
                        minEval = Math.min(minEval, eval);
                    }
                }
            }
            return minEval;
        }
    }

    private String checkWinner(String[][] board) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]))
                return board[i][0];
            if (board[0][i] != null && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]))
                return board[0][i];
        }
        if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]))
            return board[0][0];
        if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]))
            return board[0][2];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == null || board[row][col].isEmpty()) {
                    return null;
                }
            }
        }
        return "Draw";
    }
}
