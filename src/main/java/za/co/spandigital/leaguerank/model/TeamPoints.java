package za.co.spandigital.leaguerank.model;

public class TeamPoints{

    private final String team;
    private final Integer points;

    public TeamPoints(String team, Integer points){
        this.team = team;
        this.points = points;
    }

    public String getTeam() {
        return team;
    }

    public Integer getPoints() {
        return points;
    }
}