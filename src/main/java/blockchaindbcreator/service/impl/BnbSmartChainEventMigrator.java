package blockchaindbcreator.service.impl;

import blockchaindbcreator.client.BlockchainWebSocketClient;
import blockchaindbcreator.data.bnbsc.entity.BnbSmartChainTransaction;
import blockchaindbcreator.data.bnbsc.repository.BnbScTransactionRepository;
import blockchaindbcreator.service.BlockchainEventMigrator;
import blockchaindbcreator.service.exceptions.MigrationFailedException;
import blockchaindbcreator.service.mapper.TransactionMapper;
import blockchaindbcreator.service.model.BlockchainDataInterval;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Profile("bnbsc")
@Component
@RequiredArgsConstructor
@Slf4j
public class BnbSmartChainEventMigrator implements BlockchainEventMigrator {

    @Qualifier("bnbSmartChainWebSocketClient")
    private final BlockchainWebSocketClient blockchainWebSocketClient;
    private final TransactionMapper transactionMapper;
    private final BnbScTransactionRepository bnbScTransactionRepository;

    @Value("${datasource.bnbsc.hibernate.jdbc-batch-size}")
    private Integer batchSize;
    @Value("${blockchain.bnb-sc.migrator.block-interval-size}")
    private Integer blockIntervalSize;
    private Integer progressCounter = 0;

    @Override
    public void collectOldAndSaveInDatabase() {
        try {
            log.info("Migration for BinanceSmartChain started");
            final var latestBlockNumber = blockchainWebSocketClient.getLatestBlockNumber();
            final var blockIntervals = generateBlockIntervalsFrom(latestBlockNumber);
            blockIntervals.parallelStream()
                    .forEach(interval -> subscribeTransactionsFrom(interval, blockIntervals.size()));

        } catch (Exception exception) {
            throw new MigrationFailedException(exception);
        }
    }

    private List<BlockchainDataInterval> generateBlockIntervalsFrom(BigInteger latestBlock) {
        final var blockIntervals = new LinkedList<BlockchainDataInterval>();
        final var blockNumber = latestBlock.longValue();
        final var intervalSize = blockIntervalSize;
        for (var i = -1L; i < blockNumber; i += intervalSize) {
            final var fromBlockNumber = i + 1;
            final var toBlockNumber = Math.min(i + intervalSize, blockNumber);
            blockIntervals.add(new BlockchainDataInterval(
                    BigInteger.valueOf(fromBlockNumber),
                    BigInteger.valueOf(toBlockNumber)));
        }
        return blockIntervals;
    }

    private void subscribeTransactionsFrom(BlockchainDataInterval interval, Integer numberOfAllBlocks) {
        final List<BnbSmartChainTransaction> transactionList = new ArrayList<>();
        blockchainWebSocketClient.getWeb3j()
                .replayPastTransactionsFlowable(
                        DefaultBlockParameter.valueOf(interval.getFromBlockNumber()),
                        DefaultBlockParameter.valueOf(interval.getToBlockNumber()))
                .subscribe(
                        transaction -> collectAndSaveTransaction(transaction, transactionList),
                        error -> {
                            log.error("Error for blocks {}-{}", interval.getFromBlockNumber(),
                                    interval.getToBlockNumber(), error);
                            throw new MigrationFailedException(error);
                        },
                        () -> {
                            saveTransactions(transactionList);
                            log.info("Progress: {}/{}", progressCounter++, numberOfAllBlocks);
                        }
                );
    }

    private void collectAndSaveTransaction(Transaction transaction, List<BnbSmartChainTransaction> transactionList) {
        final var transactionToSave = transactionMapper.transactionToBnbSmartChainTransaction(transaction);
        transactionList.add(transactionToSave);
        if (shouldSaveTransactions(transactionList)) {
            saveTransactions(transactionList);
        }
    }

    private void saveTransactions(List<BnbSmartChainTransaction> transactionList) {
        bnbScTransactionRepository.saveAll(transactionList);
        transactionList.clear();
    }

    private boolean shouldSaveTransactions(List<BnbSmartChainTransaction> transactionList) {
        return transactionList.size() >= batchSize;
    }

}
