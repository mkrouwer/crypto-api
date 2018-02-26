package api.controllers;

import api.connectors.BlockchainConnector;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Woolf on 2/24/18.
 */

@RestController
public class BlockchainController {

    private static final Logger LOGGER = Logger.getLogger(BlockchainController.class.getName());

    /**
     * Retrieves the unspent output for a transaction from the blockchain.info API
     * @param address
     * @return
     */
    @RequestMapping("/unspent")
    public ResponseEntity unspent(@RequestParam(value="address") String address ){
        BlockchainConnector bc = new BlockchainConnector();
        try {
            String unspentOutputs = bc.getUnspentOutput(address);
            return new ResponseEntity<String>(unspentOutputs, HttpStatus.OK);
        }catch (Error e){
            LOGGER.log(Level.SEVERE, e.getMessage(), address);
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
