package blockchaindbcreator.service.impl;

import blockchaindbcreator.service.BlockchainEventListener;
import org.springframework.context.annotation.Profile;


@Profile("bnbsc")
public class BnbSmartChainEventListener implements BlockchainEventListener {

    @Override
    public void subscribeAndSaveInDatabase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
