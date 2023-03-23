import java.util.Random;

public class Block {
    int id;
    long timeStamp;
    String prevHash;
    String hash;
    long magicNumber;
    double hashProcessingTime;
    String miner;
    String data;
    BlockChain.NModification nModification = BlockChain.NModification.NO_MODIFICATION;

    public Block() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setMiner(String miner) {
        this.miner = miner;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getId() {
        return id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHashProcessingTime(double setHashProcessingTime) {
        this.hashProcessingTime = setHashProcessingTime;
    }

    public void generateNewMagicNumber() {
        Random random = new Random();
        magicNumber = random.nextLong();
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setnModification(BlockChain.NModification nModification) {
        this.nModification = nModification;
    }

    @Override
    public String toString() {
        return """ 
                Block:
                Created by miner %s
                %s gets %.0f VC
                Id: %d
                Timestamp: %d
                Magic number: %d
                Hash of the previous block:
                %s
                Hash of the block:
                %s
                Block data:
                %s
                Block was generating for %.2f seconds"""
                .formatted(miner, miner, BlockChain.MINING_AWARD, id,
                        timeStamp, magicNumber, prevHash, hash, data, hashProcessingTime);
    }
}