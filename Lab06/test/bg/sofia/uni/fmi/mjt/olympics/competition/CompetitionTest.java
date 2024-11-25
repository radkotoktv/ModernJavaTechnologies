package bg.sofia.uni.fmi.mjt.olympics.competition;

import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CompetitionTest {

    @Test
    void testCompetitionConstructorValidInputs() {
        Competitor competitor = mock(Competitor.class);
        Competition competition = new Competition("Olympics 2024", "100m Sprint", Set.of(competitor));

        assertEquals("Olympics 2024", competition.name());
        assertEquals("100m Sprint", competition.discipline());
        assertEquals(Set.of(competitor), competition.competitors());
    }

    @Test
    void testCompetitionConstructorNullNameThrowsException() {
        Competitor competitor = mock(Competitor.class);
        assertThrows(IllegalArgumentException.class,
                () -> new Competition(null, "100m Sprint", Set.of(competitor)),
                "Expected IllegalArgumentException for null name");
    }

    @Test
    void testCompetitionConstructorBlankNameThrowsException() {
        Competitor competitor = mock(Competitor.class);
        assertThrows(IllegalArgumentException.class,
                () -> new Competition("   ", "100m Sprint", Set.of(competitor)),
                "Expected IllegalArgumentException for blank name");
    }

    @Test
    void testCompetitionConstructorNullDisciplineThrowsException() {
        Competitor competitor = mock(Competitor.class);
        assertThrows(IllegalArgumentException.class,
                () -> new Competition("Olympics 2024", null, Set.of(competitor)),
                "Expected IllegalArgumentException for null discipline");
    }

    @Test
    void testCompetitionConstructorBlankDisciplineThrowsException() {
        Competitor competitor = mock(Competitor.class);
        assertThrows(IllegalArgumentException.class,
                () -> new Competition("Olympics 2024", "   ", Set.of(competitor)),
                "Expected IllegalArgumentException for blank discipline");
    }

    @Test
    void testCompetitionConstructorNullCompetitorsThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Competition("Olympics 2024", "100m Sprint", null),
                "Expected IllegalArgumentException for null competitors");
    }

    @Test
    void testCompetitionConstructorEmptyCompetitorsThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Competition("Olympics 2024", "100m Sprint", Set.of()),
                "Expected IllegalArgumentException for empty competitors");
    }

    @Test
    void testGetCompetitorsReturnsUnmodifiableSet() {
        Competitor competitor = mock(Competitor.class);
        Competition competition = new Competition("Olympics 2024", "100m Sprint", Set.of(competitor));

        Set<Competitor> competitors = competition.competitors();

        assertThrows(UnsupportedOperationException.class,
                () -> competitors.add(mock(Competitor.class)),
                "Expected UnsupportedOperationException for modifying unmodifiable competitors set");
    }

    @Test
    void testEqualsAndHashCodeSameAttributes() {
        Competitor competitor1 = mock(Competitor.class);
        Competitor competitor2 = mock(Competitor.class);

        Competition competition1 = new Competition("Olympics 2024", "100m Sprint", Set.of(competitor1));
        Competition competition2 = new Competition("Olympics 2024", "100m Sprint", Set.of(competitor2));

        assertEquals(competition1, competition2, "Competitions with same name and discipline should be equal");
        assertEquals(competition1.hashCode(), competition2.hashCode(), "Hash codes should match for equal competitions");
    }

    @Test
    void testEqualsAndHashCodeDifferentAttributes() {
        Competitor competitor1 = mock(Competitor.class);
        Competitor competitor2 = mock(Competitor.class);

        Competition competition1 = new Competition("Olympics 2024", "100m Sprint", Set.of(competitor1));
        Competition competition2 = new Competition("Olympics 2024", "200m Sprint", Set.of(competitor2));

        assertNotEquals(competition1, competition2, "Competitions with different disciplines should not be equal");
        assertNotEquals(competition1.hashCode(), competition2.hashCode(), "Hash codes should not match for unequal competitions");
    }
}
