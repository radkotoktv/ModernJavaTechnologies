package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class LocationsRuleTest {

    @Test
    public void testApplicableReturnsTrueWhenDistinctLocationsMeetThreshold() {
        LocationsRule rule = new LocationsRule(2, 1.0);

        LocalDateTime date = LocalDateTime.now();
        List<Transaction> transactions = List.of(
                new Transaction("id1", "acc1", 100, date, "Location1", Channel.ONLINE),
                new Transaction("id2", "acc1", 200, date, "Location2", Channel.ATM),
                new Transaction("id3", "acc1", 150, date, "Location1", Channel.BRANCH)
        );

        assertTrue(rule.applicable(transactions));
    }

    @Test
    public void testApplicableReturnsFalseWhenDistinctLocationsDoNotMeetThreshold() {
        LocationsRule rule = new LocationsRule(3, 1.0);

        LocalDateTime date = LocalDateTime.now();
        List<Transaction> transactions = List.of(
                new Transaction("id1", "acc1", 100, date, "Location1", Channel.ONLINE),
                new Transaction("id2", "acc1", 200, date, "Location1", Channel.ATM)
        );

        assertFalse(rule.applicable(transactions));
    }

    @Test
    public void testApplicableReturnsFalseForEmptyTransactionsList() {
        LocationsRule rule = new LocationsRule(1, 1.0);

        List<Transaction> transactions = List.of();

        assertFalse(rule.applicable(transactions));
    }
}
