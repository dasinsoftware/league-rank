package za.co.spandigital.leaguerank;

import za.co.spandigital.leaguerank.model.Match;
import za.co.spandigital.leaguerank.config.MatchResultConfig;
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

    public static int lastSpacePos(String teamscore){
        for (int i = teamscore.length() - 1; i >= 0; i--) {
            if ((Character.toString(teamscore.charAt(i)).equals(" "))){
                return i;
            }
        }
        return -1;
    }

    public static Match createMatch(String line){
        var teamScores = line.split(",");
        return new Match(teamScores[0].substring(0, lastSpacePos(teamScores[0])).trim(),
                        Integer.parseInt(teamScores[0].substring( lastSpacePos(teamScores[0]) + 1)),
                        teamScores[1].substring(0, lastSpacePos(teamScores[1])).trim(),
                        Integer.parseInt(teamScores[1].substring( lastSpacePos(teamScores[1]) + 1)));
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
        var prevRanking = new AtomicInteger(0);
        var prevPoints = new AtomicInteger(-1);
        var numTeams = new AtomicInteger(0);
        MatchResultConfig matchResultConfig = new MatchResultConfig();
        Arrays.stream(getFileInput(args[0])
                .split(System.getProperty("line.separator")))
                .map(LeagueRankApplication::createMatch)
                .map(match -> match.getResults(matchResultConfig))
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(TeamPoints::getTeam,
                         Collectors.summingInt(TeamPoints::getPoints)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(e -> new RankedTeam(e.getKey(),
                                         e.getValue(), getRanking(prevRanking,
                                                                  prevPoints,
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
