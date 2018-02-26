package api.processing;

import api.models.UnspentOutput;
import api.models.UnspentOutputCollection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UnspentCreation {

    private static final Logger LOGGER = Logger.getLogger(UnspentCreation.class.getName());

    /**
     * Creates a list of UnspentOutput objects for internal data manipulation.
     * @param unspentRoot
     * @return an UnspentOutputCollection object.
     */
    public static UnspentOutputCollection createUnspentObjects(JSONObject unspentRoot) {
        UnspentOutputCollection unspentOutputCollection = null;
        try {
            if (unspentRoot != null && unspentRoot.get("unspent_outputs") != null) {
                JSONArray outputs = (JSONArray) unspentRoot.get("unspent_outputs");
                if (outputs != null) {
                    ArrayList<UnspentOutput> unspentOutputs = new ArrayList<UnspentOutput>();
                    for (int i = 0; i < outputs.length(); i++) {
                        JSONObject unspentJSON = outputs.getJSONObject(i);
                        if (unspentJSON != null) {
                            UnspentOutput unspentOutput = createUnspentObject(unspentJSON);
                            unspentOutputs.add(unspentOutput);
                        }
                    }
                    unspentOutputCollection = new UnspentOutputCollection(unspentOutputs);
                }
            }
        }catch (JSONException e){
            LOGGER.log(Level.SEVERE, e.getMessage(), unspentRoot.toString());
            System.out.println(e);
        }
        return unspentOutputCollection;
    }

    /**
     * Creates a UnspentOutput Object for internal data manipulation.
      * @param unspentJSON
     * @return An UnspentOutput object.
     */
    public static UnspentOutput createUnspentObject(JSONObject unspentJSON){
        UnspentOutput unspentOutput = null;
        if(unspentJSON != null) {
            try {
                String txHash = unspentJSON.getString("tx_hash");
                Long txIndex = unspentJSON.getLong("tx_index");
                Long value = unspentJSON.getLong("value");

                if (txHash != null && txIndex != null && value != null) {
                    unspentOutput = new UnspentOutput(txHash, txIndex, value);
                }
            }catch (JSONException e){
                LOGGER.log(Level.SEVERE, e.getMessage(), unspentJSON.toString());
                System.out.println(e);
            }
        }
        return unspentOutput;
    }

    /**
     * Converts internal Java objects back into JSON after internal manipulation/processing is finished for consumability.
     * @param unspentOutputs
     * @return A JSON response string.
     */
    public static String createUnspentJSON(UnspentOutputCollection unspentOutputs) {
        ObjectMapper mapper = new ObjectMapper();
        String unspentOutputsJSON = null;
        if (unspentOutputs != null){
            try {
                unspentOutputsJSON = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(unspentOutputs);
            } catch (JsonProcessingException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }
        return unspentOutputsJSON;
    }
}

