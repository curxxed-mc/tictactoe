package net.curxxed.tictactoe;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import net.curxxed.tictactoe.ai.*;

import java.util.Random;

public class GameController {

    @FXML private GridPane grid;
    @FXML private ComboBox<String> aiChoice;
    @FXML private Button restartButton;
    @FXML private Button startButton;
    @FXML private Label statusLabel;
    private AI ai;

    private final Button[][] buttons = new Button[3][3];
    private String[][] board = new String[3][3];
    private boolean isGameStarted = false;
    private String currentPlayer;

    private final Winstreak winstreak = new Winstreak();

    @FXML
    public void initialize() {
        createBoard();
        aiChoice.setOnAction(e -> {
            String diff = aiChoice.getValue();
            statusLabel.setText("Difficulty: " + diff + ". Press Start to begin!");
        });
        aiChoice.getSelectionModel().select("Easy");
        disableAllButtons();
    }

    private void createBoard() {
        grid.getChildren().clear();
        grid.getColumnConstraints().clear();
        grid.getRowConstraints().clear();

        grid.setPrefSize(300, 300);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button btn = new Button();
                btn.setPrefSize(100, 100);
                btn.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
                final int r = row, c = col;
                btn.setOnAction(e -> handleMove(btn, r, c));
                buttons[row][col] = btn;
                board[row][col] = "";
                grid.add(btn, col, row);
            }
        }

        for (int i = 0; i < 3; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setPercentWidth(33.33);
            grid.getColumnConstraints().add(colConstraints);

            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(33.33);
            grid.getRowConstraints().add(rowConstraints);
        }
    }

    private void handleMove(Button btn, int row, int col) {
        if (!isGameStarted || !btn.getText().isEmpty() || currentPlayer.equals("O")) return;

        btn.setText(currentPlayer);
        btn.setDisable(true);
        board[row][col] = currentPlayer;

        Result result = checkGameState(currentPlayer);
        if (result != Result.IN_PROGRESS) {
            handleGameResult(result);
            return;
        }

        currentPlayer = "O";
        statusLabel.setText("AI's turn!");
        makeAIMove();
    }

    private void makeAIMove() {
        String difficulty = aiChoice.getValue();
        int[] aiMove = null;

        if (difficulty != null) {
            switch (difficulty.toLowerCase()) {
                case "easy":
                    aiMove = new EasyAI('O', 'X').getMove(board);
                    break;
                case "normal":
                    aiMove = new NormalAI('O', 'X').getMove(board);
                    break;
                case "hard":
                    aiMove = new HardAI('O', 'X').getMove(board);
                    break;
                case "random":
                    int randomDifficulty = new Random().nextInt(100) + 1;
                    aiMove = new RandomAI('O', 'X', randomDifficulty).getMove(board);
                    break;
                default:
                    break;
            }
        }

        if (aiMove != null) {
            int r = aiMove[0], c = aiMove[1];
            Button aiBtn = buttons[r][c];
            aiBtn.setText("O");
            aiBtn.setDisable(true);
            board[r][c] = "O";

            Result result = checkGameState("O");
            if (result != Result.IN_PROGRESS) {
                handleGameResult(result);
                return;
            }

            currentPlayer = "X";
            statusLabel.setText("Your turn!");
        }
    }

    private Result checkGameState(String player) {
        for (int i = 0; i < 3; i++) {
            if (player.equals(board[i][0]) && player.equals(board[i][1]) && player.equals(board[i][2])) {
                return player.equals("X") ? Result.X_WINS : Result.O_WINS;
            }
            if (player.equals(board[0][i]) && player.equals(board[1][i]) && player.equals(board[2][i])) {
                return player.equals("X") ? Result.X_WINS : Result.O_WINS;
            }
        }

        if ((player.equals(board[0][0]) && player.equals(board[1][1]) && player.equals(board[2][2])) ||
                (player.equals(board[0][2]) && player.equals(board[1][1]) && player.equals(board[2][0]))) {
            return player.equals("X") ? Result.X_WINS : Result.O_WINS;
        }

        if (isBoardFull()) {
            return Result.DRAW;
        }

        return Result.IN_PROGRESS;
    }

    private boolean isBoardFull() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c] == null || board[r][c].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void handleGameResult(Result result) {
        if (result == Result.X_WINS) {
            incrementDifficultyWin();
        } else if (result == Result.O_WINS) {
            winstreak.resetWinStreak(aiChoice.getValue());
        }
        String difficulty = aiChoice.getValue();
        statusLabel.setText(result.getMessage() + " " + winstreak.toString(difficulty));
        disableAllButtons();
    }

    private void incrementDifficultyWin() {
        String difficulty = aiChoice.getValue();
        if (difficulty != null) {
            switch (difficulty.toLowerCase()) {
                case "easy":
                    winstreak.incrementEasyWins();
                    break;
                case "normal":
                    winstreak.incrementNormalWins();
                    break;
                case "hard":
                    winstreak.incrementHardWins();
                    break;
                case "random":
                    winstreak.incrementRandomWins();
                    break;
                default:
                    break;
            }
        }
    }

    private void disableAllButtons() {
        for (Button[] row : buttons) {
            for (Button b : row) {
                b.setDisable(true);
            }
        }
    }

    private void enableAllButtons() {
        for (Button[] row : buttons) {
            for (Button b : row) {
                b.setDisable(false);
            }
        }
    }

    @FXML
    private void onRestart() {
        createBoard();
        board = new String[3][3];
        String difficulty = aiChoice.getValue();
        statusLabel.setText("Game restarted. " + winstreak.toString(difficulty));
        isGameStarted = false;
        startButton.setDisable(false);
        disableAllButtons();
    }

    @FXML
    private void onStart() {
        isGameStarted = true;
        currentPlayer = Math.random() < 0.5 ? "X" : "O";
        statusLabel.setText(currentPlayer.equals("X") ? "X starts! Your move." : "AI's turn!");
        enableAllButtons();
        startButton.setDisable(true);

        if (currentPlayer.equals("O")) {
            makeAIMove();
        }
    }
}