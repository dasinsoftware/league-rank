package za.co.spandigital.leaguerank;
import java.util.*;
import java.util.stream.Collectors;

public class LeagueRankApplication {

    static List<Match> matches = new ArrayList<>();
    public static String getInput(String newLine){
        return String.join(newLine,
                "Tarantulas 1, FC Awesome 0",
                "Lions 3, Snakes 3",
                "Lions 1, FC Awesome 1",
                "Tarantulas 3, Snakes 1",
                "Lions 4, Grouches 0");
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

        public TeamPointsRank(String team, Integer points) {
            super(team, points);
        }
    }

    public static void main(String[] args) {
        var newLine = System.getProperty("line.separator");
        Arrays.stream(getInput(newLine)
                .split(newLine))
                .forEach(s -> {
                            var line = s.split(",");
                            var teamGoals1 = line[0].trim().split("(?<=\\D)(?=\\d)");
                            var teamGoals2 = line[1].trim().split("(?<=\\D)(?=\\d)");
                            matches.add(new Match(teamGoals1[0], Integer.parseInt(teamGoals1[1]),
                                                  teamGoals2[0], Integer.parseInt(teamGoals2[1])));
                        });

        var tprList = matches.stream()
                .map(Match::getResults)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(teamPoints -> teamPoints.team,
                                               Collectors.summingInt(value -> value.points)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(e -> new TeamPointsRank(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        int r = 0;
        int previousPoints = -1;
        for (TeamPointsRank tpr:tprList){
            if (tpr.points == previousPoints){
                tpr.rank = r;
            }else {
                tpr.rank = ++r;
            }
            previousPoints = tpr.points;
        }

        tprList.stream()
                .sorted(Comparator.comparingInt((TeamPointsRank value) -> value.rank))
                .forEach(teamPointsRank ->
                        System.out.println(teamPointsRank.rank + ". " +
                                           teamPointsRank.team + ", " +
                                           teamPointsRank.points + " pt" +
                                          (teamPointsRank.points==1?"":"s")
                        ));
    }
}
