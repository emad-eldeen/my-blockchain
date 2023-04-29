import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BlockChain {
    final private List<Block> chain = new ArrayList<>();
    private static final Pattern STARTS_WITH_ZERO = Pattern.compile("^0+");
    private final List<Transaction> transactions = new ArrayList<>();
    private int numberOfZeros = 0;
    private String latestHash = "0";
    public static final int TARGET_CHAIN_SIZE = 15;
    // the amount of virtual currency awarded to the miner generating a valid block
    public static final double MINING_AWARD = 100.0;


    public BlockChain() {}

    // modification of the number of zeros at the start of the hash
    public enum NModification {
        INCREASE(1),
        DECREASE(-1),
        NO_MODIFICATION(0);
        final int  modification;
        NModification (int modification) {
            this.modification = modification;
        }
    }

    public boolean hashPatternIsValid(String hash) {
        if (numberOfZeros == 0) {
            return (hash.charAt(0) != '0');
        }
        Matcher matcher = STARTS_WITH_ZERO.matcher(hash);
        if (matcher.find()) {
            String zeros = matcher.group();
            return zeros.length() == numberOfZeros;
        }
        return false;
    }

    private synchronized boolean blockPreviousHashIsValid(Block block) {
        return block.getPrevHash().equals(latestHash);
    }

    public synchronized int getSize() {
        return chain.size();
    }

    public synchronized String getLatestHash() {
        return latestHash;
    }

    public synchronized void addBlock(Block block) {
        if (blockPreviousHashIsValid(block)) {
            awardMiningCoins(block.miner);
            setBlockData(block);
            chain.add(block);
            checkProcessingTime(block);
            latestHash = block.getHash();
        }
    }
    public String getName() {
        return "Emad's Blockchain";
    }

    private void setBlockData(Block block) {
        if (transactions.isEmpty()) {
            block.setData("No transactions");
        } else {
            String data = transactions.stream()
                    .map(Transaction::toString)
                    .collect(Collectors.joining("\n"));
            block.setData(data);
        }
    }

    /**
     * checks hash processing time of the block and modifies the number
     * of starting zeros that should be on the next hash
     * @param block
     */
    private void checkProcessingTime(Block block) {
        float MINIMUM_PROCESSING_TIME = 0.2f;
        float MAXIMUM_PROCESSING_TIME = 0.1f;
        if (block.hashProcessingTime < MINIMUM_PROCESSING_TIME) {
            block.setnModification(NModification.INCREASE);
            numberOfZeros++;
        } else if (block.hashProcessingTime > MAXIMUM_PROCESSING_TIME) {
            block.setnModification(NModification.DECREASE);
            numberOfZeros--;
        } // else, default value
    }

    public void printChain() {
        int numberOfZeros = 0;
        for (Block block: chain) {
            System.out.println(block);
            switch (block.nModification) {
                case INCREASE -> System.out.println("N was increased to " + ++numberOfZeros);
                case DECREASE -> System.out.println("N was decreased to " + --numberOfZeros);
                case NO_MODIFICATION -> System.out.println("N was not changed");
            }
            System.out.println();
        }
        System.out.println("End of chain.");
    }

    public synchronized double getEntityBalance(String entity) {
        double totalTo = transactions.stream()
                .filter(tran -> Objects.equals(tran.to(), entity))
                .mapToDouble(Transaction::amount)
                .sum();
        double totalFrom = transactions.stream()
                .filter(tran -> Objects.equals(tran.from(), entity))
                .mapToDouble(Transaction::amount)
                .sum();
        return totalTo - totalFrom;
    }

    private void awardMiningCoins(String miner) {
        transactions.add(new Transaction(getName(), miner, MINING_AWARD));
    }
}