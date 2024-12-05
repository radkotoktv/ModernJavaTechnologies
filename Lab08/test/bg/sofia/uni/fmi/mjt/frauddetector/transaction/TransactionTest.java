package bg.sofia.uni.fmi.mjt.frauddetector.transaction;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {
    @Test
    public void testValidTransactionCreation() {
        LocalDateTime date = LocalDateTime.now();
        Transaction transaction = new Transaction("123456", "account1", 100.0, date, "location", Channel.ONLINE);

        assertEquals("123456", transaction.transactionID());
        assertEquals("account1", transaction.accountID());
        assertEquals(100.0, transaction.transactionAmount());
        assertEquals(date, transaction.transactionDate());
        assertEquals("location", transaction.location());
        assertEquals(Channel.ONLINE, transaction.channel());
    }

    @Test
    public void testTransactionWithNullFieldsThrowsException() {
        LocalDateTime date = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class, () -> new Transaction(null, "account1", 100.0, date, "location", Channel.ONLINE));
        assertThrows(IllegalArgumentException.class, () -> new Transaction("123456", null, 100.0, date, "location", Channel.ONLINE));
        assertThrows(IllegalArgumentException.class, () -> new Transaction("123456", "account1", 100.0, null, "location", Channel.ONLINE));
        assertThrows(IllegalArgumentException.class, () -> new Transaction("123456", "account1", 100.0, date, null, Channel.ONLINE));
        assertThrows(IllegalArgumentException.class, () -> new Transaction("123456", "account1", 100.0, date, "location", null));
    }

    @Test
    public void testTransactionWithBlankFieldsThrowsException() {
        LocalDateTime date = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class, () -> new Transaction("", "account1", 100.0, date, "location", Channel.ONLINE));
        assertThrows(IllegalArgumentException.class, () -> new Transaction("123456", "", 100.0, date, "location", Channel.ONLINE));
        assertThrows(IllegalArgumentException.class, () -> new Transaction("123456", "account1", 100.0, date, "", Channel.ONLINE));
    }

    @Test
    public void testTransactionWithNegativeAmountThrowsException() {
        LocalDateTime date = LocalDateTime.now();
        assertThrows(IllegalArgumentException.class, () -> new Transaction("123456", "account1", -1.0, date, "location", Channel.ONLINE));
    }

    @Test
    public void testValidTransactionOfMethod() {
        String line = "123456,account1,100.0,2023-12-05 15:30:00,location,ONLINE";
        Transaction transaction = Transaction.of(line);

        assertEquals("123456", transaction.transactionID());
        assertEquals("account1", transaction.accountID());
        assertEquals(100.0, transaction.transactionAmount());
        assertEquals(LocalDateTime.parse("2023-12-05 15:30:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                transaction.transactionDate());
        assertEquals("location", transaction.location());
        assertEquals(Channel.ONLINE, transaction.channel());
    }

    @Test
    public void testTransactionOfMethodWithInvalidLineFormatThrowsException() {
        String invalidLine = "123456,account1,100.0,2023-12-05 15:30:00,location"; // Missing channel
        assertThrows(IllegalArgumentException.class, () -> Transaction.of(invalidLine));
    }

    @Test
    public void testTransactionOfMethodWithInvalidAmountThrowsException() {
        String invalidLine = "123456,account1,abc,2023-12-05 15:30:00,location,ONLINE"; // Invalid amount
        assertThrows(NumberFormatException.class, () -> Transaction.of(invalidLine));
    }

    @Test
    public void testTransactionOfMethodWithInvalidDateThrowsException() {
        String invalidLine = "123456,account1,100.0,invalid-date,location,ONLINE"; // Invalid date
        assertThrows(Exception.class, () -> Transaction.of(invalidLine));
    }

    @Test
    public void testTransactionOfMethodWithInvalidChannelThrowsException() {
        String invalidLine = "123456,account1,100.0,2023-12-05 15:30:00,location,INVALID_CHANNEL"; // Invalid channel
        assertThrows(IllegalArgumentException.class, () -> Transaction.of(invalidLine));
    }
}
