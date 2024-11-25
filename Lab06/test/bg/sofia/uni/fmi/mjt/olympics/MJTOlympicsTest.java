package bg.sofia.uni.fmi.mjt.olympics;

import bg.sofia.uni.fmi.mjt.olympics.competition.Competition;
import bg.sofia.uni.fmi.mjt.olympics.competition.CompetitionResultFetcher;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MJTOlympicsTest {

    private CompetitionResultFetcher fetcherMock;
    private Competitor competitor1;
    private Competitor competitor2;
    private Competitor competitor3;
    private MJTOlympics olympics;

    @BeforeEach
    void setUp() {
        fetcherMock = mock(CompetitionResultFetcher.class);

        competitor1 = mock(Competitor.class);
        when(competitor1.getNationality()).thenReturn("USA");

        competitor2 = mock(Competitor.class);
        when(competitor2.getNationality()).thenReturn("UK");

        competitor3 = mock(Competitor.class);
        when(competitor3.getNationality()).thenReturn("USA");

        olympics = new MJTOlympics(Set.of(competitor1, competitor2, competitor3), fetcherMock);
    }

//    @Test
//    void testUpdateMedalStatistics() {
//        Competition competition = mock(Competition.class);
//
//        competitor3.addMedal(Medal.GOLD);
//
//        when(competition.competitors()).thenReturn(Set.of(competitor2, competitor3));
//
//        TreeSet<Competitor> ranking = new TreeSet<>();
//        ranking.add(competitor2);
//        ranking.add(competitor3);
//
//        when(fetcherMock.getResult(competition)).thenReturn(ranking);
//
//        olympics.updateMedalStatistics(competition);
//
//        EnumMap<Medal, Integer> usaMedals = olympics.getNationsMedalTable().get("USA");
//
//        assertEquals(1, usaMedals.get(Medal.GOLD), "Expected 1 gold medal for USA");
//
//        EnumMap<Medal, Integer> ukMedals = olympics.getNationsMedalTable().get("UK");
//        assertEquals(1, ukMedals.get(Medal.SILVER), "Expected 1 silver medal for UK");
//    }

    @Test
    void testGetTotalMedals() {
        Competition competition = mock(Competition.class);
        when(competition.competitors()).thenReturn(Set.of(competitor1));

        TreeSet<Competitor> ranking = new TreeSet<>();
        ranking.add(competitor1);

        when(fetcherMock.getResult(competition)).thenReturn(ranking);

        olympics.updateMedalStatistics(competition);
        assertEquals(1, olympics.getTotalMedals("USA"), "Expected 1 medal for USA");
    }

    @Test
    void testUpdateMedalStatisticsThrowsForUnregisteredCompetitor() {
        Competition competition = mock(Competition.class);
        Competitor unregisteredCompetitor = mock(Competitor.class);
        when(competition.competitors()).thenReturn(Set.of(unregisteredCompetitor));

        assertThrows(IllegalArgumentException.class, () -> olympics.updateMedalStatistics(competition));
    }
}
