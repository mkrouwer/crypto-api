/**
 * Created by Woolf on 2/25/18.
 */
import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BlockchainConnectorTests {

    private static final Logger LOGGER = Logger.getLogger(BlockchainConnectorTests.class.getName());
    private String sampleAddress = "373hb5N8FcQaN3R7x3zcfCdwrro7N6VYyB";
    private String badAddress = "badAddress";
    private String sampleHash = "250bebf954a8f86bdbf682dacd60b743ebf94d561692c2196590b4f0cccf8091";
    private Integer sampleIndex = 314834005;
    private Integer sampleValue = 245583;

    @BeforeClass
    public static void setup() {
        RestAssured.port = Integer.valueOf(8080);
        String baseHost = "http://localhost";
        RestAssured.baseURI = baseHost;

    }

    @Test
    public void testBlockchainUnspentAPIConnectivity() {
        given().when().get("https://blockchain.info/unspent?active=" + sampleAddress).then().statusCode(200);
    }

    @Test
    public void testInternalUnspentAPIPostiveConnectivity() {
        given().when().get(RestAssured.baseURI + "/unspent?address=" + sampleAddress).then().statusCode(200);
    }

    @Test
    public void testInternalUnspentAPINegativeConnectivity() {
        given().when().get(RestAssured.baseURI + "/unspent?address=" + badAddress).then().statusCode(400);
    }

    @Test
    public void unspentAPINegativeSmokeTest() {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RestAssured.baseURI + "/unspent?address=" + badAddress);
        Assert.assertEquals(response.getBody().print(), "Invalid Bitcoin Address.");
    }

    @Test
    public void unspentAPIPostiveSmokeTest() {
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(RestAssured.baseURI + "/unspent?address=" + sampleAddress);
        LOGGER.log(Level.INFO, RestAssured.baseURI + "/unspent?address=" + sampleAddress);
            try {
                JSONObject body =  new JSONObject(response.body().print());
                JSONArray unspentOutputs = (JSONArray) body.get("unspentOutputs");
                if(unspentOutputs != null) {
                    JSONObject sampleOutput = (JSONObject) unspentOutputs.get(0);
                    if(sampleOutput != null) {
                        String transactionHash = sampleOutput.getString("transactionHash");
                        Integer transactionIndex = sampleOutput.getInt("transactionIndex");
                        Integer value = sampleOutput.getInt("value");
                        Assert.assertEquals(transactionHash, sampleHash);
                        Assert.assertEquals(transactionIndex, sampleIndex);
                        Assert.assertEquals(value, sampleValue);
                    }
                }
            }catch (JSONException e){
                LOGGER.log(Level.SEVERE, e.getMessage());
                System.out.println(e);
            }

    }


}
