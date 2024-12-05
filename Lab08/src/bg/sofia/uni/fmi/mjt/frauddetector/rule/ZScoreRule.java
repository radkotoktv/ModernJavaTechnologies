package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.List;

public class ZScoreRule implements Rule {
    private final double zScoreThreshold;
    private final double weight;

    public ZScoreRule(double zScoreThreshold, double weight) {
        this.zScoreThreshold = zScoreThreshold;
        this.weight = weight;
    }

    private double calculateDispersion(List<Transaction> transactions, double midValue) {
        double dispersion = 0;
        for (int i = 0; i < transactions.size(); i++) {
            dispersion += Math.pow(transactions.get(i).transactionAmount() - midValue, 2);
        }
        return dispersion / transactions.size();
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        double midValue = transactions.stream()
                .reduce(0.0, (res, el) -> res + el.transactionAmount(), (res, el) -> res + el)
                / transactions.size();
        double standardDeviation = Math.sqrt(calculateDispersion(transactions, midValue));

        for (Transaction tr : transactions) {
            double zScore = (tr.transactionAmount() - midValue) / standardDeviation;
            if (zScore > zScoreThreshold) {
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
