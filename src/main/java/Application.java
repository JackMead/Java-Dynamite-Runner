import bots.RandomBot;
import bots.RandomDynamiteBot;
import bots.RockBot;
import bots.YourBot;
import com.softwire.dynamite.bot.Bot;

public class Application {
    private GameRunner gameRunner = new GameRunner();

    public static void main(String[] args) {
        new Application().Run();
    }

    public void Run() {
        RunGameBetween(new YourBot(), new RockBot());

        RunGameBetween(new YourBot(), new RandomBot());

        RunGameBetween(new YourBot(), new RandomDynamiteBot());
    }

    private void RunGameBetween(Bot botOne, Bot botTwo){
        printGameResult(gameRunner.RunGame(botOne, botTwo));
        System.out.println();
    }

    private void printGameResult(GameResult gameResult) {
        String resultString = String.format("%s: %d vs %s: %d",
                gameResult.getPlayerOneName(),
                gameResult.getPlayerOneScore(),
                gameResult.getPlayerTwoName(),
                gameResult.getPlayerTwoScore());
        System.out.println(resultString);

        if (gameResult.getPlayerOneScore() == gameResult.getPlayerTwoScore()) {
            System.out.println("*** Draw ***");
            return;
        }

        String winnerName = gameResult.getPlayerOneScore() > gameResult.getPlayerTwoScore()
                ? gameResult.getPlayerOneName()
                : gameResult.getPlayerTwoName();
        System.out.println(String.format("*** Winner: %s ***", winnerName));

    }
}
