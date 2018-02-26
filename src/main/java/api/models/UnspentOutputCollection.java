package api.models;

import java.util.ArrayList;
import java.util.List;


/**
 * An object to manage the collection of UnspentOutput objects. This would be the place
 * for implementing sorting, searching, etc. methods on the collection of UnspentOutput
 * objects.
 */
public class UnspentOutputCollection {

    private ArrayList<UnspentOutput> unspentOutputs;

    public UnspentOutputCollection(ArrayList<UnspentOutput> unspentOutputs){
        this.unspentOutputs = unspentOutputs;
    }

    /**
     * Retrieves the full, un-manipulated list of UnspentOutput objects.
     * @return A List of UnspentOutput objects.
     */
    public List<UnspentOutput> getUnspentOutputs(){
        return unspentOutputs;
    }
}
