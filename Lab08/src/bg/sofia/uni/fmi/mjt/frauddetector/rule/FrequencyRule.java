package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.List;

public class FrequencyRule implements Rule {
    private final int transactionCountThreshold;
    private final TemporalAmount timeWindow;
    private final double weight;

    public FrequencyRule(int transactionCountThreshold, TemporalAmount timeWindow, double weight) {
        this.transactionCountThreshold = transactionCountThreshold;
        this.timeWindow = timeWindow;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        LocalDateTime firstDate = transactions.getFirst().transactionDate();
        for (Transaction tr : transactions) {
            LocalDateTime offsetDate = tr.transactionDate();
            boolean appl = transactions.stream()
                    .filter(p -> p.transactionDate().isBefore(offsetDate.plus(timeWindow)))
                    .toList()
                    .size()
                    >= transactionCountThreshold;
            if (appl) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double weight() {
        return this.weight;
    }
}
