package api.models;


/**
 * Creates a UnspentOutput object for internal data manipulation and retrieval.
 */
public class UnspentOutput {

    private String transactionHash;
    private long transactionIndex;
    private long value;

    public UnspentOutput(String transactionHash, long transactionIndex, long value){
        this.transactionHash = transactionHash;
        this.transactionIndex = transactionIndex;
        this.value = value;
    }


    /**
     *
     * @return The transaction hash.
     */
    public String getTransactionHash(){
        return transactionHash;
    }


    /**
     *
     * @return The transaction index.
     */
    public long getTransactionIndex(){
        return transactionIndex;
    }


    /**
     *
     * @return The unspent value;
     */
    public long getValue(){
        return value;
    }

}
