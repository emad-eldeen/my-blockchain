import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("""
                     __  __    ________  ______    ____ _ _____    ____  __    ____  ________ __________  _____    _____   __    __  __
                   _/ /_/ /   / ____/  |/  /   |  / __ ( ) ___/   / __ )/ /   / __ \\/ ____/ //_/ ____/ / / /   |  /  _/ | / /  _/ /_/ /
                  / __/ __/  / __/ / /|_/ / /| | / / / //\\__ \\   / __  / /   / / / / /   / ,< / /   / /_/ / /| |  / //  |/ /  / __/ __/
                 (_  |_  )  / /___/ /  / / ___ |/ /_/ / ___/ /  / /_/ / /___/ /_/ / /___/ /| / /___/ __  / ___ |_/ // /|  /  (_  |_  )\s
                /  _/  _/  /_____/_/  /_/_/  |_/_____/ /____/  /_____/_____/\\____/\\____/_/ |_\\____/_/ /_/_/  |_/___/_/ |_/  /  _/  _/ \s
                /_/ /_/                                                                                                     /_/ /_/   \s
                """);
        BlockChain blockChain = new BlockChain();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        System.out.println("Miners started working!");
        for (int i = 1; i < 5; i++) {
            executor.submit(new Miner(blockChain));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        blockChain.printChain();
    }
}