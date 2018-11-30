package org.leanpoker.player.strategy;

import org.leanpoker.player.state.CardsItem;
import org.leanpoker.player.state.Request;

import java.util.Arrays;
import java.util.List;

public class RankInitialHand {

    private final int             openCommunityCards;
    private       List<CardsItem> playerHand;

    public RankInitialHand(Request input) {
        openCommunityCards = input.getCommunityCards().size();
        this.playerHand = input.getPlayers().get(input.getInAction()).getHoleCards();
    }

    public int getRank() {
        String card1 = playerHand.get(0).getRank();
        String card2 = playerHand.get(1).getRank();

        return openCommunityCards > 0 ? 0 : getRank(card1, card2);
    }

    public int getRank(String card1, String card2) {

        String rank  = "23456789TJQKA";
        String combi = rank.indexOf(card1) > rank.indexOf(card2) ? card1 + card2 : card2 + card1;

        boolean isTop = Arrays.asList(new String[]{"AA", "KK", "QQ", "JJ", "TT", "99", "88", "77", "AK", "AQ", "AJ", "AT"}).contains(combi);
        isTop |= equalColor() && Arrays.asList(new String[]{"AQ", "KQ"}).contains(combi);
        if (isTop) {
            return 1;
        }
        return 0;
    }

    public boolean equalColor() {
        return playerHand.get(0).getSuit().equals(playerHand.get(0).getSuit());
    }
}
