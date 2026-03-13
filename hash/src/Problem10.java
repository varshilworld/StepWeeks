import java.util.*;

public class Problem10 {

    // L1: Fast In-Memory LRU Cache
    private final int L1_CAPACITY = 10000;
    private LinkedHashMap<String, String> l1Cache = new LinkedHashMap<>(L1_CAPACITY, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
            return size() > L1_CAPACITY;
        }
    };

    // L2: Larger SSD-backed Cache (simulated here)
    private Map<String, String> l2Cache = new HashMap<>();

    // Access tracking for Promotion Logic
    private Map<String, Integer> accessCounts = new HashMap<>();
    private final int PROMOTION_THRESHOLD = 5;

    public String getVideo(String videoId) {
        // 1. Try L1
        if (l1Cache.containsKey(videoId)) {
            System.out.println("L1 HIT (0.5ms)");
            return l1Cache.get(videoId);
        }

        // 2. Try L2
        if (l2Cache.containsKey(videoId)) {
            System.out.println("L2 HIT (5ms)");
            trackAccess(videoId);
            return l2Cache.get(videoId);
        }

        // 3. Try L3 (Simulated Database)
        System.out.println("L3 MISS - Fetching from DB (150ms)");
        String data = "VideoData_" + videoId;

        // Add to L2 initially
        l2Cache.put(videoId, data);
        trackAccess(videoId);
        return data;
    }

    private void trackAccess(String videoId) {
        int count = accessCounts.getOrDefault(videoId, 0) + 1;
        accessCounts.put(videoId, count);

        // Promotion Logic
        if (count >= PROMOTION_THRESHOLD && !l1Cache.containsKey(videoId)) {
            System.out.println("Promoting " + videoId + " to L1");
            l1Cache.put(videoId, l2Cache.get(videoId));
        }
    }

    // Main method to test
    public static void main(String[] args) {
        Problem10 cacheSystem = new Problem10();

        // Simulate repeated access
        for (int i = 0; i < 7; i++) {
            String video = cacheSystem.getVideo("VID123");
            System.out.println("Access " + (i + 1) + ": " + video);
        }

        // Access another video
        cacheSystem.getVideo("VID999");
    }
}