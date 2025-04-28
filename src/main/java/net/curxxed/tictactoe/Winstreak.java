package net.curxxed.tictactoe;

public class Winstreak {
    private int easyWins;
    private int normalWins;
    private int hardWins;
    private int randomWins;

    private int easyCurrentStreak = 0;
    private int normalCurrentStreak = 0;
    private int hardCurrentStreak = 0;
    private int randomCurrentStreak = 0;

    private int easyHighestStreak = 0;
    private int normalHighestStreak = 0;
    private int hardHighestStreak = 0;
    private int randomHighestStreak = 0;

    public void incrementEasyWins() {
        easyWins++;
        updateWinStreak("easy");
    }

    public void incrementNormalWins() {
        normalWins++;
        updateWinStreak("normal");
    }

    public void incrementHardWins() {
        hardWins++;
        updateWinStreak("hard");
    }

    public void incrementRandomWins() {
        randomWins++;
        updateWinStreak("random");
    }

    public void resetWinStreak(String mode) {
        switch (mode.toLowerCase()) {
            case "easy":
                easyCurrentStreak = 0;
                break;
            case "normal":
                normalCurrentStreak = 0;
                break;
            case "hard":
                hardCurrentStreak = 0;
                break;
            case "random":
                randomCurrentStreak = 0;
                break;
        }
    }

    private void updateWinStreak(String mode) {
        switch (mode.toLowerCase()) {
            case "easy":
                easyCurrentStreak++;
                if (easyCurrentStreak > easyHighestStreak) {
                    easyHighestStreak = easyCurrentStreak;
                }
                break;
            case "normal":
                normalCurrentStreak++;
                if (normalCurrentStreak > normalHighestStreak) {
                    normalHighestStreak = normalCurrentStreak;
                }
                break;
            case "hard":
                hardCurrentStreak++;
                if (hardCurrentStreak > hardHighestStreak) {
                    hardHighestStreak = hardCurrentStreak;
                }
                break;
            case "random":
                randomCurrentStreak++;
                if (randomCurrentStreak > randomHighestStreak) {
                    randomHighestStreak = randomCurrentStreak;
                }
                break;
        }
    }

    public String toString(String mode) {
        switch (mode.toLowerCase()) {
            case "easy":
                return String.format("Easy Wins: %d, Current Streak: %d, Highest Streak: %d",
                        easyWins, easyCurrentStreak, easyHighestStreak);
            case "normal":
                return String.format("Normal Wins: %d, Current Streak: %d, Highest Streak: %d",
                        normalWins, normalCurrentStreak, normalHighestStreak);
            case "hard":
                return String.format("Hard Wins: %d, Current Streak: %d, Highest Streak: %d",
                        hardWins, hardCurrentStreak, hardHighestStreak);
            case "random":
                return String.format("Random Wins: %d, Current Streak: %d, Highest Streak: %d",
                        randomWins, randomCurrentStreak, randomHighestStreak);
            default:
                return "Invalid mode selected.";
        }
    }
}