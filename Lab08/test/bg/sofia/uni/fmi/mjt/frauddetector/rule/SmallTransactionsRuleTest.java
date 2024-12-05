package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SmallTransactionsRuleTest {

    @Test
    public void testApplicableReturnsTrueWhenSmallTransactionsExceedThreshold() {
        SmallTransactionsRule rule = new SmallTransactionsRule(2, 50, 1.0);

        LocalDateTime date = LocalDateTime.now();
        List<Transaction> transactions = List.of(
                new Transaction("id1", "acc1", 40, date, "2024-01-01", Channel.ONLINE),
                new Transaction("id2", "acc1", 30, date, "2024-01-01", Channel.BRANCH),
                new Transaction("id3", "acc1", 100, date, "2024-01-01", Channel.ATM),
                new Transaction("id4", "acc1", 200, date, "2024-01-01", Channel.Online)
        );

        assertTrue(rule.applicable(transactions));
    }

    @Test
    public void testApplicableReturnsFalseWhenSmallTransactionsAreBelowThreshold() {
        SmallTransactionsRule rule = new SmallTransactionsRule(2, 50, 1.0);

        LocalDateTime date = LocalDateTime.now();
        List<Transaction> transactions = List.of(
                new Transaction("id1", "acc1", 40, date, "2024-01-01", Channel.ONLINE),
                new Transaction("id2", "acc1", 100, date, "2024-01-01", Channel.BRANCH)
        );

        assertFalse(rule.applicable(transactions));
    }
}
