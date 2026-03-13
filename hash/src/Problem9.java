import java.util.*;

public class Problem9 {

    // Inner class for Transaction
    static class Transaction {
        int id;
        int amount;
        String merchant;
        long timestamp; // in minutes

        Transaction(int id, int amount, String merchant, long time) {
            this.id = id;
            this.amount = amount;
            this.merchant = merchant;
            this.timestamp = time;
        }
    }

    // Classic Two-Sum: O(n)
    public void findTwoSum(List<Transaction> transactions, int target) {
        // Map: Amount -> Transaction
        Map<Integer, Transaction> seen = new HashMap<>();

        for (Transaction t : transactions) {
            int complement = target - t.amount;
            if (seen.containsKey(complement)) {
                Transaction other = seen.get(complement);
                System.out.println("Pair Found: ID " + t.id + " and ID " + other.id);
            }
            seen.put(t.amount, t);
        }
    }

    // Duplicate Detection: Same amount, Same merchant
    public void detectDuplicates(List<Transaction> transactions) {
        // Key: amount + merchant string
        Map<String, List<Integer>> tracker = new HashMap<>();

        for (Transaction t : transactions) {
            String key = t.amount + "_" + t.merchant;
            tracker.computeIfAbsent(key, k -> new ArrayList<>()).add(t.id);
        }

        tracker.forEach((key, ids) -> {
            if (ids.size() > 1) {
                System.out.println("Duplicate Alert for " + key + ": IDs " + ids);
            }
        });
    }

    // Main method to test
    public static void main(String[] args) {
        Problem9 analyzer = new Problem9();

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1, 50, "Amazon", 100));
        transactions.add(new Transaction(2, 30, "Flipkart", 105));
        transactions.add(new Transaction(3, 20, "Amazon", 110));
        transactions.add(new Transaction(4, 50, "Amazon", 115)); // duplicate
        transactions.add(new Transaction(5, 70, "Flipkart", 120));

        System.out.println("=== Two-Sum Check (target=100) ===");
        analyzer.findTwoSum(transactions, 100);

        System.out.println("\n=== Duplicate Detection ===");
        analyzer.detectDuplicates(transactions);
    }
}