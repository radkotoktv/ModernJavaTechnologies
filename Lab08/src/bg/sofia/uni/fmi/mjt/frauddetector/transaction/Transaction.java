package bg.sofia.uni.fmi.mjt.frauddetector.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Transaction(String transactionID, String accountID, double transactionAmount,
                          LocalDateTime transactionDate, String location, Channel channel) {
    private static final int TRANSACTION_LENGTH = 6;
    private static final int TRANSACTION_ID_INDEX = 0;
    private static final int ACCOUNT_ID_INDEX = 1;
    private static final int TRANSACTION_AMOUNT_INDEX = 2;
    private static final int TRANSACTION_DATE_INDEX = 3;
    private static final int LOCATION_INDEX = 4;
    private static final int CHANNEL_INDEX = 5;
    public Transaction {
        if (transactionID == null       ||
                accountID == null       ||
                transactionDate == null ||
                location == null        ||
                channel == null) {
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
        if (parts.length != TRANSACTION_LENGTH) {
            throw new IllegalArgumentException("Invalid transaction format");
        }
        return new Transaction(parts[TRANSACTION_ID_INDEX],
                parts[ACCOUNT_ID_INDEX],
                Double.parseDouble(parts[TRANSACTION_AMOUNT_INDEX]),
                LocalDateTime.parse(parts[TRANSACTION_DATE_INDEX],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                parts[LOCATION_INDEX],
                Channel.valueOf(parts[CHANNEL_INDEX]));
    }
}
