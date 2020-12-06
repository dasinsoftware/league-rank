package za.co.spandigital.leaguerank;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LeagueRankApplicationTest {
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
    void main() throws IOException {
        LeagueRankApplication.main(new String[]{"match-scores.txt"});
        assertEquals("1. Tarantulas , 6 pts\n" +
                              "2. Lions , 5 pts\n" +
                              "3. FC Awesome , 1 pt\n" +
                              "3. Snakes , 1 pt\n" +
                              "4. Grouches , 0 pts\n", bytes.toString());
    }
}