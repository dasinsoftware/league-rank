package za.co.spandigital.leaguerank.model;

import za.co.spandigital.leaguerank.config.MatchResultConfig;

import java.util.List;

public class Match{
    String team1;
    Integer goals1;
    String team2;
    Integer goals2;


    public Match(String team1, Integer goals1, String team2, Integer goals2 ){
        this.team1 = team1;
        this.goals1 = goals1;
        this.team2 = team2;
        this.goals2 = goals2;
    }

    private Integer getTeam1Points(MatchResultConfig matchResultConfig){
        return  goals1 > goals2 ?       matchResultConfig.getWin() :
                goals1.equals(goals2) ? matchResultConfig.getDraw():
                                        matchResultConfig.getLoss();
    }

    private Integer getTeam2Points(MatchResultConfig matchResultConfig){
        return  goals2 > goals1 ?       matchResultConfig.getWin() :
                goals1.equals(goals2) ? matchResultConfig.getDraw() :
                                        matchResultConfig.getLoss();
    }

    public List<TeamPoints> getResults(MatchResultConfig matchResultConfig){
        return List.of(new TeamPoints(team1, getTeam1Points(matchResultConfig)),
                       new TeamPoints(team2, getTeam2Points(matchResultConfig)));
    }
}
