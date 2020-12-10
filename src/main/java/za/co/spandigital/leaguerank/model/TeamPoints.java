package za.co.spandigital.leaguerank.model;

public class TeamPoints{
    public String getTeam() {
        return team;
    }

    public Integer getPoints() {
        return points;
    }

    private String team;
    private Integer points;

    public TeamPoints(String team, Integer points){
        this.team = team;
        this.points = points;
    }
}