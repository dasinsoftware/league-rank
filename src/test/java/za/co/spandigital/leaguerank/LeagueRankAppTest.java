package za.co.spandigital.leaguerank;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class LeagueRankAppTest {
    private PrintStream console;
    private ByteArrayOutputStream bytes;

    @BeforeEach
    public void setUp() {
        bytes   = new ByteArrayOutputStream();
        console = System.out;
        System.setOut(new PrintStream(bytes));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(console);
    }

    @Test
    void run() {
        var app = new LeagueRankApp();
        app.run(new String[]{"-f=match-scores.txt"});
        assertEquals("1. Taran6tulas , 6 pts\n" +
                "2. Lions , 5 pts\n" +
                "3. FC Awesome , 1 pt\n" +
                "3. Snakes , 1 pt\n" +
                "5. Grouches , 0 pts\n", bytes.toString());

    }

    @Test
    void run_without_filename_option() {
        var app = new LeagueRankApp();
        app.run(new String[]{"match-scores.txt"});
        assertEquals("usage: LeagueRankEx [-f <arg>]\n" +
                " -f,--filename <arg>   file name to load data from\n", bytes.toString());

    }

    @Test
    void getRanking() {
        var app = new LeagueRankApp();
        assertEquals(6,app.getRanking(new AtomicInteger(2),
                                               new AtomicInteger(5), 3,
                                               new AtomicInteger(5)));
    }
}