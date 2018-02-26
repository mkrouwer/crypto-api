package api.connectors;

import api.models.UnspentOutputCollection;
import api.processing.UnspentCreation;
import org.json.*;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Woolf on 2/24/18.
 */
public class BlockchainConnector {

    private String hostName = "https://blockchain.info";
    private static final Logger LOGGER = Logger.getLogger(BlockchainConnector.class.getName());
    /**
     * Retrieves the unspent output from the blockchain.info API and creates internal objects mappings
     * to facilitate real world scenarios where we'd want to re-use and perform additional data processing
     * on the response. Then converts the objects into new JSON objects once all necessary manipulation is complete.
     * @param address
     * @return
     */
    public String getUnspentOutput(String address) {

        try {
            URL url = new URL(hostName + "/unspent?active=" + address);

            LOGGER.log(Level.INFO, url.toString());
            JSONTokener tokener = new JSONTokener(url.openStream());
            JSONObject unspentRoot  = new JSONObject(tokener);

            //I create internal java objects here to facilitate any further functionality we may want to perform on this data in the future.
            UnspentOutputCollection unspentOutput  = UnspentCreation.createUnspentObjects(unspentRoot);
            String unspentJSON = UnspentCreation.createUnspentJSON(unspentOutput);
            return unspentJSON;

        }catch (IOException e){
            if(e.toString().contains("java.io.FileNotFoundException")){
                LOGGER.log(Level.SEVERE, e.getMessage(), address);
                throw new Error("Blockchain Service Unreachable.");
            }
            else{
                LOGGER.log(Level.SEVERE, e.getMessage(), address);
                throw new Error( "Invalid Bitcoin Address.");
            }
        }
    }
}
