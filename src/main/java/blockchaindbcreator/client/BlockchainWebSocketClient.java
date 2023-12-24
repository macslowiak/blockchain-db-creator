package blockchaindbcreator.client;

import blockchaindbcreator.client.exceptions.BlockchainClientRegistrationError;
import blockchaindbcreator.client.properties.BlockchainClientConfiguration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.websocket.WebSocketService;


@Slf4j
@Getter
public abstract class BlockchainWebSocketClient {

    private final BlockchainClientConfiguration clientConfiguration;
    private final Web3j web3j;

    protected BlockchainWebSocketClient(BlockchainClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
        this.web3j = buildWeb3j();
    }

    protected Web3j buildWeb3j() {
        try {
            log.info(STR."Registering blockchain client on: \{clientConfiguration.getUrl()}");
            WebSocketService ws = new WebSocketService(clientConfiguration.getUrl(), true);
            ws.connect();
            return Web3j.build(ws);
        } catch (Exception exception) {
            throw new BlockchainClientRegistrationError(clientConfiguration.getUrl(), exception);
        } finally {
            log.info(STR."Blockchain client registered on: \{clientConfiguration.getUrl()}");
        }
    }
}
