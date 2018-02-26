import api.models.UnspentOutput;
import api.models.UnspentOutputCollection;
import api.processing.UnspentCreation;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Woolf on 2/25/18.
 */
public class UnspentCreationTests {

    private static String malformedString = null;
    private static JSONObject malformedJSON = null;

    @BeforeClass
    public static void setup() {
        malformedString = "dfds";
        try {
            malformedJSON = new JSONObject("{differentInput:3}");
        }catch (JSONException e){
            System.out.print(e);
        }

    }

    /**
     * These tests tackle those pesky null pointer errors that tend to creep up in Java!
     */
    @Test
    public void createUnspentObjectsNegativeTests() {
        UnspentOutputCollection  nullCollection = UnspentCreation.createUnspentObjects(null);
        UnspentOutputCollection  malformedCollection = UnspentCreation.createUnspentObjects(malformedJSON);
        Assert.assertEquals(nullCollection, null);
        Assert.assertEquals(malformedCollection, null);
    }

    @Test
    public void createUnspentObjectNegativeTests() {
        UnspentOutput nullOutput = UnspentCreation.createUnspentObject(null);
        UnspentOutput malformedOutput = UnspentCreation.createUnspentObject(malformedJSON);
        Assert.assertEquals(nullOutput, null);
        Assert.assertEquals(malformedOutput, null);
    }

    @Test
    public void createUnspentJSONtNegativeTests() {
        UnspentOutput nullOutput = UnspentCreation.createUnspentObject(null);
        Assert.assertEquals(nullOutput, null);
    }


}
