package blockchaindbcreator.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile("bnbsc")
@Configuration
@ConfigurationProperties(prefix = "client.blockchain.web-socket.bnb-sc")
public class BnbSmartChainWebSocketClientConfiguration extends BlockchainClientConfiguration {

}
