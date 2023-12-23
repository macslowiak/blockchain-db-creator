package com.cryptosearcher.service.data.impl;

import com.cryptosearcher.client.BlockchainWebSocketClient;
import com.cryptosearcher.service.data.BlockchainEventMigrator;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.Transaction;

@Component
@RequiredArgsConstructor
@Slf4j
public class BnbSmartChainEventMigrator implements BlockchainEventMigrator {

    @Qualifier("bnbSmartChainWebSocketClient")
    private final BlockchainWebSocketClient blockchainWebSocketClient;

    private List<Transaction> transactionList = new LinkedList<>();

    @Override
    @Bean
    public void collectOldAndSaveInDatabase() {
        try {
            final var subscription = blockchainWebSocketClient.getWeb3j()
                .replayPastTransactionsFlowable(
                    DefaultBlockParameter.valueOf(BigInteger.valueOf(14051976)),
                    DefaultBlockParameter.valueOf(BigInteger.valueOf(14051977)))
                .subscribe(event -> {
                    log.info("Event received");
                    addTransaction(event);
                }, error -> {
                    log.info("Error: ", error);
                    throw new RuntimeException(error);
                }, () -> log.info("Completed"));
            TimeUnit.SECONDS.sleep(10);
            subscription.dispose();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        } finally {
            log.info("Transaction list size: {}", transactionList);
        }
    }

    private void addTransaction(Transaction transaction) {
        transactionList.add(transaction);
    }

}
