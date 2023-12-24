package blockchaindbcreator.client.exceptions;

public class BlockchainClientRegistrationError extends RuntimeException{

    private static final String CANNOT_CONNET_TO_BLOCKCHAIN = STR."Cannot connect to blockchain: %s";

    public BlockchainClientRegistrationError(String url, Throwable cause) {
        super(String.format(CANNOT_CONNET_TO_BLOCKCHAIN, url), cause);
    }

}
