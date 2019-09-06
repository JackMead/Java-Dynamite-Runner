package bots;

import com.softwire.dynamite.bot.Bot;
import com.softwire.dynamite.game.Gamestate;
import com.softwire.dynamite.game.Move;

import java.util.Random;

public class RandomDynamiteBot implements Bot{
    private final Move[] moves = {Move.R, Move.P, Move.S};
    private int dynamitesUsed = 0;

    public Move makeMove(Gamestate gamestate) {

        if(shouldThrowDynamite()){
            dynamitesUsed++;
            return Move.D;
        }

        int moveIndex = new Random().nextInt(moves.length);
        return moves[moveIndex];
    }

    private boolean shouldThrowDynamite(){
        // throw dynamites 10% of the time until used up
        return dynamitesUsed < 100 && new Random().nextInt(10) == 0;
    }
}
