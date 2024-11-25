package bg.sofia.uni.fmi.mjt.olympics.comparator;

import bg.sofia.uni.fmi.mjt.olympics.MJTOlympics;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NationMedalComparatorTest {
    @Test
    public void testNationMedalComparatorSameMedals() {
        MJTOlympics olympics = mock(MJTOlympics.class);
        when(olympics.getTotalMedals("Bulgaria")).thenReturn(5);
        when(olympics.getTotalMedals("USA")).thenReturn(5);

        NationMedalComparator nationMedalComparator = new NationMedalComparator(olympics);
        assertEquals("Bulgaria".compareTo("USA"), nationMedalComparator.compare("Bulgaria", "USA"));
    }

    @Test
    public void testNationmedalComparatorLessMedals() {
        MJTOlympics olympics = mock();
        when(olympics.getTotalMedals("Bulgaria")).thenReturn(5);
        when(olympics.getTotalMedals("USA")).thenReturn(10);

        NationMedalComparator nationMedalComparator = new NationMedalComparator(olympics);
        assertEquals(1, nationMedalComparator.compare("Bulgaria", "USA"));
    }

    @Test
    public void testNationMedalComparatorMoreMedals() {
        MJTOlympics olympics = mock();
        when(olympics.getTotalMedals("Bulgaria")).thenReturn(10);
        when(olympics.getTotalMedals("USA")).thenReturn(5);

        NationMedalComparator nationMedalComparator = new NationMedalComparator(olympics);
        assertEquals(-1, nationMedalComparator.compare("Bulgaria", "USA"));
    }
}
