package com.cryptosearcher.client;

import com.cryptosearcher.client.properties.BnbSmartChainWebSocketClientConfiguration;
import org.springframework.stereotype.Component;

@Component
public class BnbSmartChainWebSocketClient extends BlockchainWebSocketClient {
    protected BnbSmartChainWebSocketClient(BnbSmartChainWebSocketClientConfiguration clientConfiguration) {
        super(clientConfiguration);
    }

}
