import java.util.Date;

public class Miner implements Runnable {
    final BlockChain blockChain;

    public Miner(BlockChain blockChain) {
        this.blockChain = blockChain;
    }

    public String getName() {
        return "miner" + Thread.currentThread().getId();
    }

    @Override
    public void run() {
        while (chainIsNotFull()) {
            Block block = new Block();
            block.setMiner(getName());
            findMagicNumber(block);
            blockChain.addBlock(block);
        }
    }

    // keeps generating magic numbers until the reaching a block hash that has a valid pattern
    // according to the chain
    private void findMagicNumber(Block block) {
        String hash;
        long startTime = System.nanoTime();
        double processingTimeInSec;
        do {
            block.setId(blockChain.getSize() + 1);
            block.setTimeStamp(new Date().getTime());
            block.setPrevHash(blockChain.getLatestHash());
            block.generateNewMagicNumber();
            processingTimeInSec = (System.nanoTime() - startTime) / 1e9;
            block.setHashProcessingTime(processingTimeInSec);
            hash = StringUtil.applySha256(block.toString());
            block.setHash(hash);
        } while (!blockChain.hashPatternIsValid(hash)
                && chainIsNotFull());
    }

    private boolean chainIsNotFull() {
        return blockChain.getSize() < BlockChain.TARGET_CHAIN_SIZE;
    }
}