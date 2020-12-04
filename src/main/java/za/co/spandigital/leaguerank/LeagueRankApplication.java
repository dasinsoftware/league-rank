package za.co.spandigital.leaguerank;

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

    public static class Match{
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

    public static class TeamPoints{
         String team;
         Integer points;
         public TeamPoints(String team, Integer points){
             this.team = team;
             this.points = points;
         }
    }

    public static class TeamPointsRank extends TeamPoints{
        int rank;
        public TeamPointsRank(String team, Integer points, int rank) {
            super(team, points);
            this.rank = rank;
        }
    }

    public static Match createMatch(String line){
        var teamScores = line.split(",");
        var teamGoals1 = teamScores[0].trim().split("(?<=\\D)(?=\\d)");
        var teamGoals2 = teamScores[1].trim().split("(?<=\\D)(?=\\d)");
        return new Match(teamGoals1[0], Integer.parseInt(teamGoals1[1]),
                         teamGoals2[0], Integer.parseInt(teamGoals2[1]));
    }

    public static int getRanking(AtomicInteger ranking, AtomicInteger previousPoints, Integer points){
        int result;
        if (points == previousPoints.get()){
            result = ranking.get();
        }else{
            result = ranking.addAndGet(1);
        }
        previousPoints.set(points);
        return result;
    }

    public static void main(String[] args) throws IOException {
        var aiRanking = new AtomicInteger(0);
        var aiPreviousPoints = new AtomicInteger(-1);
        var newLine = System.getProperty("line.separator");
        Arrays.stream(getFileInput(args[0])
                .split(newLine))
                .map(LeagueRankApplication::createMatch)
                .map(Match::getResults)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(teamPoints -> teamPoints.team,
                         Collectors.summingInt(value -> value.points)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(e -> new TeamPointsRank(e.getKey(), e.getValue(),getRanking(aiRanking, aiPreviousPoints, e.getValue()) ))
                .sorted(Comparator.comparingInt((TeamPointsRank value) -> value.rank)
                                  .thenComparing((TeamPointsRank s) -> s.team))
                .forEach(teamPointsRank ->
                        System.out.println(teamPointsRank.rank + ". " +
                                           teamPointsRank.team + ", " +
                                           teamPointsRank.points + " pt" +
                                          (teamPointsRank.points==1?"":"s")));
    }
}
