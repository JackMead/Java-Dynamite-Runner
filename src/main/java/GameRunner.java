import com.softwire.dynamite.bot.Bot;
import com.softwire.dynamite.game.Gamestate;
import com.softwire.dynamite.game.Move;
import com.softwire.dynamite.game.Round;

import java.util.ArrayList;
import java.util.List;

public class GameRunner {
    enum Result {
        PLAYER_ONE_WIN,
        PLAYER_TWO_WIN,
        DRAW
    }

    private final int WINNING_SCORE = 1000;
    private final int DYNAMITE_LIMIT = 100;
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private int playerOneDynamite = 0;
    private int playerTwoDynamite = 0;
    private List<Round> playerOneRounds = new ArrayList<Round>();
    private List<Round> playerTwoRounds = new ArrayList<Round>();
    private int currentDrawStreak = 0;

    public GameResult RunGame(Bot playerOne, Bot playerTwo) {
        resetGame();

        String playerOneName = playerOne.getClass().getSimpleName();
        String playerTwoName = playerTwo.getClass().getSimpleName();

        while (!gameOver()) {
            Move playerOneMove = getMove(playerOne, playerOneRounds);
            Move playerTwoMove = getMove(playerTwo, playerTwoRounds);

            updateRounds(playerOneMove, playerTwoMove);

            if (playerOneMove == null) {
                System.out.println(String.format("%s played an invalid move and lost", playerOneName));
                playerTwoScore = WINNING_SCORE;
                break;
            }

            if (playerTwoMove == null) {
                System.out.println(String.format("%s played an invalid move and lost", playerTwoName));
                playerOneScore = WINNING_SCORE;
                break;
            }

            updateDynamiteUsage(playerOneMove, playerTwoMove);

            if(playerOneDynamite > DYNAMITE_LIMIT){
                System.out.println(String.format("%s exceeded the dynamite limit and lost", playerOneName));
                playerTwoScore = WINNING_SCORE;
                break;
            }

            if(playerTwoDynamite > DYNAMITE_LIMIT){
                System.out.println(String.format("%s exceeded the dynamite limit and lost", playerTwoName));
                playerOneScore = WINNING_SCORE;
                break;
            }

            Result result = resolveRound(playerOneMove, playerTwoMove);
            updatePoints(result);
        }

        return new GameResult(playerOneScore, playerTwoScore, playerOneName, playerTwoName);
    }

    private void resetGame(){
        playerOneScore = 0;
        playerTwoScore = 0;
        playerOneDynamite = 0;
        playerTwoDynamite = 0;
        playerOneRounds = new ArrayList<Round>();
        playerTwoRounds = new ArrayList<Round>();
        currentDrawStreak = 0;
    }

    private boolean gameOver(){
        return playerOneScore >= WINNING_SCORE || playerTwoScore >= WINNING_SCORE || currentDrawStreak >= WINNING_SCORE;
    }

    private void updateRounds(Move playerOneMove, Move playerTwoMove){
        Round playerOneRound = new Round();
        playerOneRound.setP1(playerOneMove);
        playerOneRound.setP2(playerTwoMove);
        playerOneRounds.add(playerOneRound);

        Round playerTwoRound = new Round();
        playerTwoRound.setP1(playerTwoMove);
        playerTwoRound.setP2(playerOneMove);
        playerTwoRounds.add(playerTwoRound);
    }

    private Move getMove(Bot bot, List<Round> rounds) {
        Gamestate gamestate = new Gamestate();
        gamestate.setRounds(rounds);
        return bot.makeMove(gamestate);
    }

    private Result resolveRound(Move playerOneMove, Move playerTwoMove) {
        if (playerOneMove == playerTwoMove) {
            return Result.DRAW;
        }

        if (playerOneMove == Move.D) {
            return playerTwoMove == Move.W ? Result.PLAYER_TWO_WIN : Result.PLAYER_ONE_WIN;
        }

        if (playerOneMove == Move.W) {
            return playerTwoMove == Move.D ? Result.PLAYER_ONE_WIN : Result.PLAYER_TWO_WIN;
        }

        if (playerOneMove == Move.R) {
            boolean p1Wins = playerTwoMove == Move.W || playerTwoMove == Move.S;
            return p1Wins ? Result.PLAYER_ONE_WIN : Result.PLAYER_TWO_WIN;
        }

        if (playerOneMove == Move.S) {
            boolean p1Wins = playerTwoMove == Move.W || playerTwoMove == Move.P;
            return p1Wins ? Result.PLAYER_ONE_WIN : Result.PLAYER_TWO_WIN;
        }

        boolean p1Wins = playerTwoMove == Move.W || playerTwoMove == Move.R;
        return p1Wins ? Result.PLAYER_ONE_WIN : Result.PLAYER_TWO_WIN;
    }

    private void updateDynamiteUsage(Move playerOneMove, Move playerTwoMove){
        if(playerOneMove == Move.D){
            playerOneDynamite++;
        }
        if(playerTwoMove == Move.D){
            playerTwoDynamite++;
        }
    }

    private void updatePoints(Result result){
        int pointsForWinning = currentDrawStreak + 1;
        switch (result){
            case DRAW:
                currentDrawStreak++;
                break;

            case PLAYER_ONE_WIN:
                currentDrawStreak = 0;
                playerOneScore += pointsForWinning;
                break;

            case PLAYER_TWO_WIN:
                currentDrawStreak = 0;
                playerTwoScore += pointsForWinning;
                break;
        }
    }
}
