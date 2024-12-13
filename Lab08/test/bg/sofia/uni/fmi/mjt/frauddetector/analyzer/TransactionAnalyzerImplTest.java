package bg.sofia.uni.fmi.mjt.frauddetector.analyzer;

import bg.sofia.uni.fmi.mjt.frauddetector.rule.FrequencyRule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.Rule;
import bg.sofia.uni.fmi.mjt.frauddetector.rule.SmallTransactionsRule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionAnalyzerImplTest {
    @Test
    public void testRuleWeightSumLessThanOne() {
        Reader reader = mock(Reader.class);

        SmallTransactionsRule smallTransactionsRule = mock(SmallTransactionsRule.class);
        when(smallTransactionsRule.weight()).thenReturn(0.2);

        FrequencyRule frequencyRule = mock(FrequencyRule.class);
        when(frequencyRule.weight()).thenReturn(0.5);

        List<Rule> rules = new ArrayList<Rule>();
        rules.add(smallTransactionsRule);
        rules.add(frequencyRule);

        assertThrows(IllegalArgumentException.class, () -> new TransactionAnalyzerImpl(reader, rules));
    }

    @Test
    public void testRuleWeightSumMoreThanOne() {
        Reader reader = mock(Reader.class);

        SmallTransactionsRule smallTransactionsRule = mock(SmallTransactionsRule.class);
        when(smallTransactionsRule.weight()).thenReturn(0.7);

        FrequencyRule frequencyRule = mock(FrequencyRule.class);
        when(frequencyRule.weight()).thenReturn(0.6);

        List<Rule> rules = new ArrayList<Rule>();
        rules.add(smallTransactionsRule);
        rules.add(frequencyRule);

        assertThrows(IllegalArgumentException.class, () -> new TransactionAnalyzerImpl(reader, rules));
    }

    @Test
    public void testGetAllTransactions() {
        String transactionsInput = """
TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel
TX000001,AC00128,14.09,2023-04-11 16:29:14,San Diego,ATM
TX000002,AC00455,376.24,2023-06-27 16:44:19,Houston,ATM
TX000003,AC00019,126.29,2023-07-10 18:16:08,Mesa,Online
        """;

        SmallTransactionsRule smallTransactionsRule = mock(SmallTransactionsRule.class);
        when(smallTransactionsRule.weight()).thenReturn(0.4);

        FrequencyRule frequencyRule = mock(FrequencyRule.class);
        when(frequencyRule.weight()).thenReturn(0.6);

        List<Rule> rules = new ArrayList<Rule>();
        rules.add(smallTransactionsRule);
        rules.add(frequencyRule);

        TransactionAnalyzerImpl analyzer = new TransactionAnalyzerImpl(new StringReader(transactionsInput), rules);

        List<Transaction> transactions = analyzer.allTransactions();
        assertEquals(3, transactions.size());
    }

    @Test
    public void testNoTransactionsAllAccountIds() {
        ArrayList<Rule> rules = mock(ArrayList.class);
        Reader reader = mock(Reader.class);
        TransactionAnalyzerImpl transactionAnalyzer = mock(TransactionAnalyzerImpl.class);
        when(transactionAnalyzer.allTransactions()).thenReturn(null);
        assertTrue(transactionAnalyzer.allAccountIDs().isEmpty());
    }

    @Test
    void testTransactionCountByChannel() {
        String transactionsInput = """
TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel
TX000001,AC00128,14.09,2023-04-11 16:29:14,San Diego,ATM
TX000002,AC00455,376.24,2023-06-27 16:44:19,Houston,ATM
TX000003,AC00019,126.29,2023-07-10 18:16:08,Mesa,Online
        """;

        SmallTransactionsRule smallTransactionsRule = mock(SmallTransactionsRule.class);
        when(smallTransactionsRule.weight()).thenReturn(0.4);

        FrequencyRule frequencyRule = mock(FrequencyRule.class);
        when(frequencyRule.weight()).thenReturn(0.6);

        List<Rule> rules = new ArrayList<Rule>();
        rules.add(smallTransactionsRule);
        rules.add(frequencyRule);

        TransactionAnalyzerImpl analyzer = new TransactionAnalyzerImpl(new StringReader(transactionsInput), rules);

        Map<Channel, Integer> countByChannel = analyzer.transactionCountByChannel();

        assertEquals(1, countByChannel.get(Channel.Online));
        assertEquals(2, countByChannel.get(Channel.ATM));
    }

    @Test
    void testAccountsRisk() {
        String transactionsInput = """
TransactionID,AccountID,TransactionAmount,TransactionDate,Location,Channel
TX000001,AC00128,14.09,2023-04-11 16:29:14,San Diego,ATM
TX000002,AC00455,376.24,2023-06-27 16:44:19,Houston,ATM
TX000003,AC00019,126.29,2023-07-10 18:16:08,Mesa,Online
        """;

        Rule mockRule = mock(Rule.class);
        when(mockRule.weight()).thenReturn(1.0);
        when(mockRule.applicable(anyList())).thenReturn(true);

        TransactionAnalyzerImpl analyzer = new TransactionAnalyzerImpl(new StringReader(transactionsInput), List.of(mockRule));

        SortedMap<String, Double> risks = analyzer.accountsRisk();

        assertEquals(1.0, risks.get("AC00128"));
        assertEquals(1.0, risks.get("AC00455"));
    }
}
