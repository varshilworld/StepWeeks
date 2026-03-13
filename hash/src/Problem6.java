import java.util.concurrent.atomic.AtomicLong;

public class Problem6 {

    // Inner class for TokenBucket
    static class TokenBucket {
        private final int maxTokens;
        private final double refillRate; // tokens per second
        private final AtomicLong tokens;
        private final AtomicLong lastRefillTime;

        public TokenBucket(int maxTokens, double refillRate) {
            this.maxTokens = maxTokens;
            this.refillRate = refillRate;
            this.tokens = new AtomicLong(maxTokens);
            this.lastRefillTime = new AtomicLong(System.nanoTime());
        }

        public synchronized boolean allowRequest() {
            refill();
            if (tokens.get() > 0) {
                tokens.decrementAndGet();
                return true;
            }
            return false;
        }

        private void refill() {
            long now = System.nanoTime();
            long elapsed = now - lastRefillTime.get();
            double tokensToAdd = (elapsed / 1e9) * refillRate;
            if (tokensToAdd > 0) {
                long newTokens = Math.min(maxTokens, tokens.get() + (long) tokensToAdd);
                tokens.set(newTokens);
                lastRefillTime.set(now);
            }
        }

        public int getRemainingTokens() {
            refill();
            return tokens.intValue();
        }
    }

    // Main method to test the TokenBucket
    public static void main(String[] args) throws InterruptedException {
        TokenBucket bucket = new TokenBucket(5, 1.0); // 5 tokens max, 1 token/sec refill

        for (int i = 0; i < 7; i++) {
            boolean allowed = bucket.allowRequest();
            System.out.println("Request " + (i + 1) + ": " + (allowed ? "Allowed" : "Denied")
                    + " (Remaining: " + bucket.getRemainingTokens() + ")");
            Thread.sleep(200); // simulate time between requests
        }
    }
}