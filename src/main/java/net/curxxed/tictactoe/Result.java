package net.curxxed.tictactoe;

public enum Result {
    X_WINS("X wins!"),
    O_WINS("O wins!"),
    DRAW("It's a draw!"),
    IN_PROGRESS("Game in progress...");

    private final String resultMessage;

    Result(String message) {
        this.resultMessage = message;
    }

    public String getMessage() {
        return resultMessage;
    }
}