package net.curxxed.tictactoe.ai;

public abstract class AI {
    protected char aiPlayer; // AI's symbol ('O')
    protected char humanPlayer; // Human's symbol ('X')

    public AI(char aiPlayer, char humanPlayer) {
        this.aiPlayer = aiPlayer;
        this.humanPlayer = humanPlayer;
    }

    // Abstract method to get the AI's move
    public abstract int[] getMove(String[][] board);
}