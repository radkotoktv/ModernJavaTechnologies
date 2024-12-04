package bg.sofia.uni.fmi.mjt.frauddetector.transaction;

import java.time.LocalDateTime;

public record Transaction(String transactionID, String accountID, double transactionAmount, LocalDateTime transactionDate, String location, Channel channel) {
    public Transaction {
        if (transactionID == null || accountID == null || transactionDate == null || location == null || channel == null) {
            throw new IllegalArgumentException("Transaction fields cannot be null");
        }
        if (transactionID.isBlank() || accountID.isBlank() || location.isBlank()) {
            throw new IllegalArgumentException("Transaction fields cannot be blank");
        }
        if (transactionAmount < 0) {
            throw new IllegalArgumentException("Transaction amount cannot be negative");
        }
    }

    public static Transaction of(String line) {
        String[] parts = line.split(",");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid transaction format");
        }
        return new Transaction(parts[0], parts[1], Double.parseDouble(parts[2]), LocalDateTime.parse(parts[3]), parts[4], Channel.valueOf(parts[5]));
    }

}
