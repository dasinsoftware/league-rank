package za.co.spandigital.leaguerank;

import za.co.spandigital.leaguerank.model.Match;
import za.co.spandigital.leaguerank.model.RankedTeam;
import za.co.spandigital.leaguerank.model.TeamPoints;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class LeagueRankApplication {

    public static String getFileInput(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    public static int lastSpacePos(String teamscore){
        for (int i = teamscore.length() - 1; i >= 0; i--) {
            if (!isNumeric(Character.toString(teamscore.charAt(i)))){
                return i;
            }
        }
        return -1;
    }

    public static Match createMatch(String line){
        var teamScores = line.split(",");
        var goals1 = teamScores[0].substring( lastSpacePos(teamScores[0]) + 1);
        var team1  = teamScores[0].substring(0, lastSpacePos(teamScores[0])).trim();
        var goals2 = teamScores[1].substring( lastSpacePos(teamScores[1]) + 1);
        var team2  = teamScores[1].substring(0, lastSpacePos(teamScores[1])).trim();

        return new Match(team1, Integer.parseInt(goals1),
                         team2, Integer.parseInt(goals2));
    }

    public static int getRanking(AtomicInteger ranking,
                                 AtomicInteger previousPoints,
                                 Integer points,
                                 AtomicInteger numTeams){
        int result;
        numTeams.addAndGet(1);
        if (points == previousPoints.get()){
            result = ranking.get();
        }else{
            ranking.addAndGet(1);
            result = numTeams.get();
        }
        previousPoints.set(points);
        return result;
    }

    public static void main(String[] args) throws IOException {

        var aiRanking = new AtomicInteger(0);
        var aiPreviousPoints = new AtomicInteger(-1);
        var numTeams = new AtomicInteger(0);
        Arrays.stream(getFileInput(args[0])
                .split(System.getProperty("line.separator")))
                .map(LeagueRankApplication::createMatch)
                .map(Match::getResults)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(TeamPoints::getTeam,
                         Collectors.summingInt(TeamPoints::getPoints)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(e -> new RankedTeam(e.getKey(),
                                         e.getValue(), getRanking(aiRanking,
                                                                  aiPreviousPoints,
                                                                  e.getValue(),
                                                                  numTeams)))
                .sorted(Comparator.comparingInt(RankedTeam::getRank)
                                  .thenComparing(TeamPoints::getTeam))
                .forEach(rankedTeam ->
                        System.out.println(rankedTeam.getRank() + ". " +
                                           rankedTeam.getTeam() + " , " +
                                           rankedTeam.getPoints() + " pt" +
                                          (rankedTeam.getPoints()==1?"":"s")));
    }
}
