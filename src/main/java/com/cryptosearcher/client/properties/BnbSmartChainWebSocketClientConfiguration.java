package com.cryptosearcher.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client.blockchain.web-socket.bnb-sc")
public class BnbSmartChainWebSocketClientConfiguration extends BlockchainClientConfiguration {

}
