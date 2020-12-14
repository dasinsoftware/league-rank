package za.co.spandigital.leaguerank;

import org.apache.commons.cli.*;
import za.co.spandigital.leaguerank.config.MatchResultConfig;
import za.co.spandigital.leaguerank.model.LeagueMatch;
import za.co.spandigital.leaguerank.model.RankedTeam;
import za.co.spandigital.leaguerank.model.TeamPoints;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * LeagueRankApp is a console application which computes
 * league standings from match scores.
 *
 * The application takes
 * a file of results of matches as its single argument.
 *
 */
public class LeagueRankApp {

    /**
     * Runs the application
     *
     * @param args an array of String arguments to be parsed
     */
    public void run(String[] args) {

        CommandLine line = parseArguments(args);

        if (line.hasOption("filename")) {

            String fileName = line.getOptionValue("filename");

            calculateAndPrintTable(readData(fileName));

        } else {
            printAppHelp();
        }
    }

    /**
     * Parses application arguments
     *
     * @param args application arguments
     * @return <code>CommandLine</code> which represents a list of application
     * arguments.
     */
    private CommandLine parseArguments(String[] args) {

        Options options = getOptions();
        CommandLine line = null;

        CommandLineParser parser = new DefaultParser();

        try {
            line = parser.parse(options, args);

        } catch (ParseException ex) {

            System.err.println("Failed to parse command line arguments");
            System.err.println(ex.toString());
            printAppHelp();

            System.exit(1);
        }

        return line;
    }

    /**
     * Generates application command line options
     *
     * @return application <code>Options</code>
     */
    private Options getOptions() {

        var options = new Options();

        options.addOption("f", "filename", true, "file name to load data from");
        return options;
    }

    /**
     * Prints application help
     */
    private void printAppHelp() {

        Options options = getOptions();

        var formatter = new HelpFormatter();
        formatter.printHelp("LeagueRankEx", options, true);
    }


    /**
     * Reads application data from a file
     *
     * @param fileName file of application data
     * @return String of lines
     */
    private String readData(String fileName) {
        String lines = null;
        try {
            lines =  Files.readString(Path.of(fileName));
        } catch (IOException ex) {
            System.err.println("Failed to read file");
            System.err.println(ex.toString());
            System.exit(1);
        }
        return lines;
    }

    /**
     * Calculates and prints league standings
     *
     * @param data input data
     */
    private void calculateAndPrintTable(String data) {

        var prevRanking = new AtomicInteger(0);
        var prevPoints = new AtomicInteger(-1);
        var numTeams = new AtomicInteger(0);
        MatchResultConfig matchResultConfig = new MatchResultConfig();
        Arrays.stream(data
                .split(System.getProperty("line.separator")))
                .map(LeagueMatch::new)
                .map(leagueMatch -> leagueMatch.getResults(matchResultConfig))
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(TeamPoints::getTeam,
                        Collectors.summingInt(TeamPoints::getPoints)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(e -> new RankedTeam(e.getKey(), e.getValue(), getRanking(prevRanking, prevPoints, e.getValue(), numTeams)))
                .sorted(Comparator.comparingInt(RankedTeam::getRank)
                                  .thenComparing(RankedTeam::getTeam))
                .forEach(System.out::println);
    }

    public int getRanking(AtomicInteger previousranking,
                          AtomicInteger previousPoints,
                          Integer points,
                          AtomicInteger numTeams){
        int result;
        numTeams.addAndGet(1);
        if (points == previousPoints.get()){
            result = previousranking.get();
        }else{
            previousranking.addAndGet(1);
            result = numTeams.get();
        }
        previousPoints.set(points);
        return result;
    }
}
