package za.co.spandigital.leaguerank.model;

public class RankedTeam extends TeamPoints {

    private final int rank;

    public RankedTeam(String team, Integer points, int rank) {
        super(team, points);
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public String toString(){
        return getRank() + ". " +
                getTeam() + " , " +
                getPoints() + " pt" +
                (getPoints()==1?"":"s");
    }
}
