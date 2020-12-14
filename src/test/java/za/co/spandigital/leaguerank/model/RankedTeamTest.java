package za.co.spandigital.leaguerank.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class RankedTeamTest {

    RankedTeam rankedTeam_MoreThanOnePoint;
    RankedTeam rankedTeam_ZeroPoints;
    RankedTeam rankedTeam_OnePoint;

    @BeforeEach
    public void beforeEach() {
        rankedTeam_MoreThanOnePoint = new RankedTeam("Lions", 2, 1);
        rankedTeam_ZeroPoints = new RankedTeam("Lions", 0, 10);
        rankedTeam_OnePoint = new RankedTeam("Lions", 1, 6);
    }

    @Test
    void testToString() {
        assertEquals("1. Lions , 2 pts", rankedTeam_MoreThanOnePoint.toString());
        assertEquals("10. Lions , 0 pts", rankedTeam_ZeroPoints.toString());
        assertEquals("6. Lions , 1 pt", rankedTeam_OnePoint.toString());

    }
}