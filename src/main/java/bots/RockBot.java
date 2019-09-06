package bots;

import com.softwire.dynamite.bot.Bot;
import com.softwire.dynamite.game.Gamestate;
import com.softwire.dynamite.game.Move;

public class RockBot implements Bot{
    public Move makeMove(Gamestate gamestate) {
        return Move.R;
    }
}
