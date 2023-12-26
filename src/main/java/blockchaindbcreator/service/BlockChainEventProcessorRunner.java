package blockchaindbcreator.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlockChainEventProcessorRunner {

    private final BlockchainEventMigrator blockchainEventMigrator;
//    private final BlockchainEventListener blockchainEventListener;

    @EventListener(ApplicationReadyEvent.class)
    public void migrateOldData() {
        blockchainEventMigrator.collectOldAndSaveInDatabase();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runListener() {
//    blockchainEventMigrator.collectOldAndSaveInDatabase();
    }
}
