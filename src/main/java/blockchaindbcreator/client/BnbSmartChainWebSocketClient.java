package blockchaindbcreator.client;

import blockchaindbcreator.client.properties.BnbSmartChainWebSocketClientConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile("bnbsc")
@Component
public class BnbSmartChainWebSocketClient extends BlockchainWebSocketClient {
    protected BnbSmartChainWebSocketClient(BnbSmartChainWebSocketClientConfiguration clientConfiguration) {
        super(clientConfiguration);
    }

}
