import bg.sofia.uni.fmi.mjt.frauddetector.rule.SmallTransactionsRule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String... args) throws FileNotFoundException {
        String filePath = "dataset.csv";

        try (Reader reader = new FileReader(filePath)) {
            System.out.println("File found");
        } catch (IOException e) {
            System.out.println("File not found");
        }

        SmallTransactionsRule rule = new SmallTransactionsRule(2, 50, 1.0);

        LocalDateTime date = LocalDateTime.now();
        List<Transaction> transactions = List.of(
                new Transaction("id1", "acc1", 40, date, "2024-01-01", Channel.ONLINE),
                new Transaction("id2", "acc1", 30, date, "2024-01-01", Channel.BRANCH),
                new Transaction("id3", "acc1", 100, date, "2024-01-01", Channel.ATM),
                new Transaction("id4", "acc1", 200, date, "2024-01-01", Channel.Online)
        );
        System.out.println(transactions.stream()
                .filter(p -> p.transactionAmount() <= 50)
                .count());

    }

}