package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class LocationsRule implements Rule {
    private final int threshold;
    private final double weight;

    public LocationsRule(int threshold, double weight) {
        this.threshold = threshold;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        List<String> locations = new ArrayList<String>();
        for (Transaction tr : transactions) {
            locations.add(tr.location());
        }
        return locations.stream()
                .distinct()
                .toList()
                .size()
                >= threshold;
    }

    @Override
    public double weight() {
        return this.weight;
    }
}
