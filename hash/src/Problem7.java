import java.util.*;

public class Problem7 {

    // TrieNode definition
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        // Stores top 10 suggestions for this prefix
        List<String> topSuggestions = new ArrayList<>();
        int frequency = 0;
    }

    private TrieNode root = new TrieNode();
    private Map<String, Integer> globalCounts = new HashMap<>();

    public void updateFrequency(String query) {
        int newFreq = globalCounts.getOrDefault(query, 0) + 1;
        globalCounts.put(query, newFreq);

        // Insert/Update query in Trie
        TrieNode current = root;
        for (char c : query.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
            updateNodeSuggestions(current, query);
        }
    }

    private void updateNodeSuggestions(TrieNode node, String query) {
        if (!node.topSuggestions.contains(query)) {
            node.topSuggestions.add(query);
        }
        // Sort based on global frequency and keep top 10
        node.topSuggestions.sort((a, b) -> globalCounts.get(b) - globalCounts.get(a));
        if (node.topSuggestions.size() > 10) {
            node.topSuggestions.remove(10);
        }
    }

    public List<String> search(String prefix) {
        TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) return new ArrayList<>();
            current = current.children.get(c);
        }
        return current.topSuggestions;
    }

    // Main method to test
    public static void main(String[] args) {
        Problem7 autocomplete = new Problem7();

        autocomplete.updateFrequency("apple");
        autocomplete.updateFrequency("app");
        autocomplete.updateFrequency("application");
        autocomplete.updateFrequency("app");
        autocomplete.updateFrequency("apple");

        System.out.println("Suggestions for 'app': " + autocomplete.search("app"));
        System.out.println("Suggestions for 'appl': " + autocomplete.search("appl"));
        System.out.println("Suggestions for 'banana': " + autocomplete.search("banana"));
    }
}