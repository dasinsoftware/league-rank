package za.co.spandigital.leaguerank.config;

import lombok.Data;
import za.co.spandigital.leaguerank.LeagueRankApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
public class MatchResultConfig {
    private int win;
    private int loss;
    private int draw;
    public MatchResultConfig(){
        try (InputStream input = LeagueRankApplication.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find app.properties");
                return;
            }
            prop.load(input);
            setWin(Integer.parseInt(prop.getProperty("win")));
            setDraw(Integer.parseInt(prop.getProperty("draw")));
            setLoss(Integer.parseInt(prop.getProperty("loss")));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
