package org.leanpoker.player;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.leanpoker.player.state.PlayersItem;
import org.leanpoker.player.state.Request;

public class Player {
    // test comment

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
        Request     input     = new Gson().fromJson(request, Request.class);
        PlayersItem ourPlayer = input.getPlayers().get(input.getInAction());

        int newBet = 0;

        if (isPair(ourPlayer)) {
            newBet = ourPlayer.getStack();
        }

        return newBet;
    }

    private static boolean isPair(PlayersItem ourPlayer) {
        return ourPlayer.getHoleCards().get(0).getRank().equals(ourPlayer.getHoleCards().get(1).getRank());
    }

    public static void showdown(JsonElement game) {
    }
}
