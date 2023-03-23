import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockChain blockChain = new BlockChain();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (int i = 1; i < 5; i++) {
            executor.submit(new Miner(blockChain));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        blockChain.printChain();
    }
}