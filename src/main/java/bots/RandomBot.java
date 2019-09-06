package bots;

import com.softwire.dynamite.bot.Bot;
import com.softwire.dynamite.game.Gamestate;
import com.softwire.dynamite.game.Move;

import java.util.Random;

public class RandomBot implements Bot{
    private final Move[] moves = {Move.R, Move.P, Move.S};

    public Move makeMove(Gamestate gamestate) {
        int moveIndex = new Random().nextInt(moves.length);
        return moves[moveIndex];
    }
}
