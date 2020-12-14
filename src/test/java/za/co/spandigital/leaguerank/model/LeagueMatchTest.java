package za.co.spandigital.leaguerank.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.spandigital.leaguerank.config.MatchResultConfig;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LeagueMatchTest {

    LeagueMatch leagueMatch_HomeWin;
    LeagueMatch leagueMatch_HomeLoss;
    LeagueMatch leagueMatch_Draw;


    MatchResultConfig mockConfig = mock(MatchResultConfig.class);


    @BeforeEach
    public void beforeAll() {
        leagueMatch_HomeWin = new LeagueMatch("Lions 3, Snakes 2");
        leagueMatch_Draw    = new LeagueMatch("Lions 1, FC Awesome 1");
        leagueMatch_HomeLoss= new LeagueMatch("Taran6tulas 0, Snakes 1");
    }

    @Test
    void getLineSplit() {
        assertArrayEquals(new String[]{"Lions 3", " Snakes 2"}, leagueMatch_HomeWin.getLineSplit());
        assertArrayEquals(new String[]{"Lions 1", " FC Awesome 1"}, leagueMatch_Draw.getLineSplit());
        assertArrayEquals(new String[]{"Taran6tulas 0", " Snakes 1"}, leagueMatch_HomeLoss.getLineSplit());
    }

    @Test
    void getHomeSide() {
        assertEquals("Lions 3", leagueMatch_HomeWin.getHomeSide());
        assertEquals("Lions 1", leagueMatch_Draw.getHomeSide());
        assertEquals("Taran6tulas 0", leagueMatch_HomeLoss.getHomeSide());

    }

    @Test
    void getVisitingSide() {
        assertEquals("Snakes 2", leagueMatch_HomeWin.getVisitingSide());
        assertEquals("FC Awesome 1", leagueMatch_Draw.getVisitingSide());
        assertEquals("Snakes 1", leagueMatch_HomeLoss.getVisitingSide());
    }

    @Test
    void getHomeTeamName() {
        assertEquals("Lions", leagueMatch_HomeWin.getHomeTeamName());
        assertEquals("Lions", leagueMatch_Draw.getHomeTeamName());
        assertEquals("Taran6tulas", leagueMatch_HomeLoss.getHomeTeamName());
    }

    @Test
    void getHomeTeamScore() {
        assertEquals(3, leagueMatch_HomeWin.getHomeTeamScore());
        assertEquals(1, leagueMatch_Draw.getHomeTeamScore());
        assertEquals(0, leagueMatch_HomeLoss.getHomeTeamScore());
    }

    @Test
    void getVisitingTeamName() {
        assertEquals("Snakes", leagueMatch_HomeWin.getVisitingTeamName());
        assertEquals("FC Awesome", leagueMatch_Draw.getVisitingTeamName());
        assertEquals("Snakes", leagueMatch_HomeLoss.getVisitingTeamName());
    }

    @Test
    void getVisitingTeamScore() {
        assertEquals(2, leagueMatch_HomeWin.getVisitingTeamScore());
        assertEquals(1, leagueMatch_Draw.getVisitingTeamScore());
        assertEquals(1, leagueMatch_HomeLoss.getVisitingTeamScore());
    }

    @Test
    void getResults() {
        when(mockConfig.getDraw()).thenReturn(1);
        when(mockConfig.getWin()).thenReturn(3);
        when(mockConfig.getLoss()).thenReturn(0);

        assertEquals(2, leagueMatch_HomeWin.getResults(mockConfig).size());
    }
}