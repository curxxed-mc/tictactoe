package net.curxxed.tictactoe.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EasyAI extends AI {
    private final Random random = new Random();

    public EasyAI(char aiPlayer, char humanPlayer) {
        super(aiPlayer, humanPlayer);
    }

    @Override
    public int[] getMove(String[][] board) {
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