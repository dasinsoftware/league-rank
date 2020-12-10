package za.co.spandigital.leaguerank.model;

public class RankedTeam extends TeamPoints {
    public int getRank() {
        return rank;
    }

    private int rank;
    public RankedTeam(String team, Integer points, int rank) {
        super(team, points);
        this.rank = rank;
    }
}
