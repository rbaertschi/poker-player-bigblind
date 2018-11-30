package org.leanpoker.player;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.leanpoker.player.state.CardsItem;
import org.leanpoker.player.state.PlayersItem;
import org.leanpoker.player.state.Request;
import org.leanpoker.player.strategy.RankInitialHand;

import java.util.ArrayList;

public class Player {
    // test comment

    static final String VERSION = "Default Java folding player";

    public static int betRequest(JsonElement request) {
        Request     input     = new Gson().fromJson(request, Request.class);
        PlayersItem ourPlayer = input.getPlayers().get(input.getInAction());

        int newBet = 0;

        ArrayList<CardsItem> allCards = new ArrayList<>();
        allCards.addAll(ourPlayer.getHoleCards());
        allCards.addAll(input.getCommunityCards());

        int minRequiredBet = input.getCurrentBuyIn() - ourPlayer.getBet();
        if (new RankInitialHand(input).getRank() > 0) {
            newBet = Math.max(minRequiredBet, Double.valueOf((double) ourPlayer.getStack() * 0.2 * Math.random()).intValue());
        }

        if (input.getCommunityCards().size() > 0) {
            int ranking = new RankingService().getRanking(allCards);
            if (ranking >= 5) {
                newBet = Math.max(minRequiredBet, Double.valueOf((double) ourPlayer.getStack() * Math.random()).intValue());
            } else if (ranking >= 3) {
                newBet = Math.max(minRequiredBet, Double.valueOf((double) ourPlayer.getStack() * 0.3 * Math.random()).intValue());
            } else if ((double) minRequiredBet / (double) ourPlayer.getBet() < 0.2) {
                newBet = minRequiredBet;
            }
        }


        return newBet;
    }

    private static boolean isPair(PlayersItem ourPlayer) {
        return ourPlayer.getHoleCards().get(0).getRank().equals(ourPlayer.getHoleCards().get(1).getRank());
    }

    public static void showdown(JsonElement game) {
    }
}
