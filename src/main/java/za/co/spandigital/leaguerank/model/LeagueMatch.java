package za.co.spandigital.leaguerank.model;

import za.co.spandigital.leaguerank.config.MatchResultConfig;

import java.util.List;

public class LeagueMatch {
    String matchLine;

    public LeagueMatch(String matchLine){
        this.matchLine = matchLine;
    }

    public String[] getLineSplit(){
        return matchLine.split(",");
    }

    public String getHomeSide(){
        return getLineSplit()[0].trim();
    }

    public String getVisitingSide(){
        return getLineSplit()[1].trim();
    }

    public String getHomeTeamName(){
        return getHomeSide().substring(0, lastSpacePos(getHomeSide())).trim();
    }

    public int getHomeTeamScore(){
        return Integer.parseInt(getHomeSide().substring(lastSpacePos(getHomeSide()) + 1));
    }

    public String getVisitingTeamName(){
        return getVisitingSide().substring(0, lastSpacePos(getVisitingSide())).trim();
    }

    public int getVisitingTeamScore(){
        return Integer.parseInt(getVisitingSide().substring(lastSpacePos(getVisitingSide()) + 1));
    }

    private int lastSpacePos(String teamScore){
        for (int i = teamScore.length() - 1; i >= 0; i--) {
            if ((Character.toString(teamScore.charAt(i)).equals(" "))){
                return i;
            }
        }
        return -1;
    }

    private Integer getHomeTeamPoints(MatchResultConfig matchResultConfig){
        return  getHomeTeamScore() > getVisitingTeamScore() ?  matchResultConfig.getWin() :
                getHomeTeamScore() == getVisitingTeamScore() ? matchResultConfig.getDraw():
                                                               matchResultConfig.getLoss();
    }

    private Integer getVistingTeamPoints(MatchResultConfig matchResultConfig){
        return  getVisitingTeamScore() > getHomeTeamScore() ?  matchResultConfig.getWin() :
                getVisitingTeamScore() == getHomeTeamScore() ? matchResultConfig.getDraw() :
                                                               matchResultConfig.getLoss();
    }

    public List<TeamPoints> getResults(MatchResultConfig matchResultConfig){
        return List.of(new TeamPoints(getHomeTeamName(), getHomeTeamPoints(matchResultConfig)),
                       new TeamPoints(getVisitingTeamName(), getVistingTeamPoints(matchResultConfig)));
    }
}
