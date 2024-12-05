package bg.sofia.uni.fmi.mjt.frauddetector.analyzer;

import bg.sofia.uni.fmi.mjt.frauddetector.rule.Rule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

public class TransactionAnalyzerImpl implements TransactionAnalyzer {
    private final Reader reader;
    private final List<Rule> rules;
    private final List<Transaction> transactions;

    public TransactionAnalyzerImpl(Reader reader, List<Rule> rules) {
        this.reader = reader;
        this.transactions = getAllTransactions();
        this.rules = rules;
        if (rules.stream().reduce(0.0, (res, el) -> res + el.weight(), (res, el) -> res + el) != 1) {
            throw new IllegalArgumentException("Sum weight of rules has to be 1");
        }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> trans = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while ( (line = bufferedReader.readLine() ) != null) {
                trans.add(Transaction.of(line));
            }
        } catch (IOException e) {
            System.out.println("IOException caught!");
        }

        return trans;
    }

    @Override
    public List<Transaction> allTransactions() {
        return transactions;
    }

    @Override
    public List<String> allAccountIDs() {
        List<String> accountIDs = new ArrayList<String>();
        for (Transaction tr : transactions) {
            accountIDs.add(tr.accountID());
        }
        return accountIDs.stream().distinct().toList();
    }

    @Override
    public Map<Channel, Integer> transactionCountByChannel() {
        Map<Channel, Integer> channelTransactions = new HashMap<Channel, Integer>();
        for (Transaction tr : transactions) {
            if (!channelTransactions.containsKey(tr.channel())) {
                channelTransactions.put(tr.channel(), 1);
            } else {
                channelTransactions.put(tr.channel(), channelTransactions.get(tr.channel()) + 1);
            }
        }
        return channelTransactions;
    }

    @Override
    public double amountSpentByUser(String accountID) {
        if (accountID == null || accountID.isEmpty()) {
            throw new IllegalArgumentException("accountID is empty");
        }

        return allTransactionsByUser(accountID).stream()
                .reduce(0.0, (res, el) -> res + el.transactionAmount(), Double::sum);
    }

    @Override
    public List<Transaction> allTransactionsByUser(String accountId) {
        if (accountId == null || accountId.isEmpty()) {
            throw new IllegalArgumentException("Wrong accountId");
        }

        return transactions.stream()
                .filter(transaction -> transaction.accountID().equals(accountId))
                .toList();
    }

    @Override
    public double accountRating(String accountId) {
        if (accountId == null || accountId.isEmpty()) {
            throw new IllegalArgumentException("accountId is null");
        }

        double risk = 0;
        for (Rule rule : rules) {
            if (rule.applicable(allTransactionsByUser(accountId))) {
                risk += rule.weight();
            }
        }
        return risk;
    }

    @Override
    public SortedMap<String, Double> accountsRisk() {
        SortedMap<String, Double> accountRisks = new TreeMap<String, Double>();
        for (String accountId : allAccountIDs()) {
            double risk = accountRating(accountId);
            accountRisks.put(accountId, risk);
        }
        return accountRisks;
    }
}
