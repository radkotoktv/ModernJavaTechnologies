package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class FrequencyRuleTest {

    @Test
    public void testApplicableReturnsFalseWhenTransactionsDoNotMeetThresholdInTimeWindow() {
        FrequencyRule rule = new FrequencyRule(3, ChronoUnit.HOURS.getDuration(), 1.0);

        LocalDateTime date = LocalDateTime.now();
        List<Transaction> transactions = List.of(
                new Transaction("id1", "acc1", 100, date, "Location1", Channel.ONLINE),
                new Transaction("id2", "acc1", 200, date, "Location2", Channel.ATM)
        );

        assertFalse(rule.applicable(transactions));
    }

    @Test
   public  void testApplicableReturnsFalseForEmptyTransactionsList() {
        FrequencyRule rule = new FrequencyRule(1, ChronoUnit.HOURS.getDuration(), 1.0);

        List<Transaction> transactions = List.of();

        assertFalse(rule.applicable(transactions));
    }
}
