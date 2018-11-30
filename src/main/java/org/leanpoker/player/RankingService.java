package org.leanpoker.player;

import com.google.gson.Gson;
import org.leanpoker.player.ranking.Ranking;
import org.leanpoker.player.state.CardsItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class RankingService {

    public int getRanking(List<CardsItem> cards) {
        try {
            URL           oracle     = new URL("http://rainman.leanpoker.org/rank");
            URLConnection connection = oracle.openConnection();
            connection.setDoOutput(true);

            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream());
            String cardsJson = "cards=" + new Gson().toJson(cards);
            out.write(cardsJson);
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            StringBuilder input = new StringBuilder();
            String        inputLine;
            while ((inputLine = in.readLine()) != null)
                input.append(inputLine);
            in.close();

            return new Gson().fromJson(input.toString(), Ranking.class).getRank();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

}
