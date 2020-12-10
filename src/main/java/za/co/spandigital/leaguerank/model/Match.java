package za.co.spandigital.leaguerank.model;

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

    private Integer getTeam1Points(){
        return goals1 > goals2 ? 3 : goals1.equals(goals2) ? 1 : 0;
    }
    private Integer getTeam2Points(){
        return goals2 > goals1 ? 3 : goals1.equals(goals2) ? 1 : 0;
    }

    public List<TeamPoints> getResults(){
        return List.of(new TeamPoints(team1, getTeam1Points()), new TeamPoints(team2, getTeam2Points()));
    }
}
