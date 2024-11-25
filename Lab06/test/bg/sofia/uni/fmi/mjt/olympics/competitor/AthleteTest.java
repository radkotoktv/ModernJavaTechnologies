package bg.sofia.uni.fmi.mjt.olympics.competitor;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

class AthleteTest {

    @Test
    void testAthleteConstructorValidInputs() {
        Athlete athlete = new Athlete("123", "Usain Bolt", "Jamaica");

        assertEquals("123", athlete.getIdentifier());
        assertEquals("Usain Bolt", athlete.getName());
        assertEquals("Jamaica", athlete.getNationality());
        assertTrue(athlete.getMedals().isEmpty());
    }

    @Test
    void testAthleteConstructorNullIdentifierThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Athlete(null, "Usain Bolt", "Jamaica"),
                "Expected IllegalArgumentException for null identifier");
    }

    @Test
    void testAthleteConstructorEmptyNameThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Athlete("123", "", "Jamaica"),
                "Expected IllegalArgumentException for empty name");
    }

    @Test
    void testAthleteConstructorNullNationalityThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Athlete("123", "Usain Bolt", null),
                "Expected IllegalArgumentException for null nationality");
    }

    @Test
    void testAddMedalValidMedal() {
        Athlete athlete = new Athlete("123", "Michael Phelps", "USA");

        athlete.addMedal(Medal.GOLD);
        athlete.addMedal(Medal.SILVER);

        assertEquals(Set.of(Medal.GOLD, Medal.SILVER), athlete.getMedals());
    }

    @Test
    void testAddMedalNullThrowsException() {
        Athlete athlete = new Athlete("123", "Simone Biles", "USA");

        assertThrows(IllegalArgumentException.class,
                () -> athlete.addMedal(null),
                "Expected IllegalArgumentException for null medal");
    }

    @Test
    void testGetMedalsReturnsUnmodifiableSet() {
        Athlete athlete = new Athlete("123", "Usain Bolt", "Jamaica");
        athlete.addMedal(Medal.BRONZE);

        Set<Medal> medals = athlete.getMedals();

        assertThrows(UnsupportedOperationException.class,
                () -> medals.add(Medal.GOLD),
                "Expected UnsupportedOperationException for modifying unmodifiable medals set");
    }

    @Test
    void testEqualsAndHashCodeSameAttributes() {
        Athlete athlete1 = new Athlete("123", "Carl Lewis", "USA");
        Athlete athlete2 = new Athlete("123", "Carl Lewis", "USA");

        assertEquals(athlete1, athlete2, "Athletes with same attributes should be equal");
        assertEquals(athlete1.hashCode(), athlete2.hashCode(), "Hash codes should match for equal athletes");
    }

    @Test
    void testEqualsAndHashCodeDifferentAttributes() {
        Athlete athlete1 = new Athlete("123", "Carl Lewis", "USA");
        Athlete athlete2 = new Athlete("124", "Carl Lewis", "Canada");

        assertNotEquals(athlete1, athlete2);
        assertNotEquals(athlete1.hashCode(), athlete2.hashCode());
    }
}
