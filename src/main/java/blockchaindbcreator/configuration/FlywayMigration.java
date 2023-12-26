package blockchaindbcreator.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class FlywayMigration {

    @Qualifier("bnbSmartChainDataSource")
    private final DataSource bnbSmartChainDataSource;
    @Qualifier("ethereumDataSource")
    private final DataSource ethereumDataSource;

    @PostConstruct
    public void migrateFlyway() {
        migrateEthereum();
        migrateBnbSmartChain();
    }

    private void migrateEthereum() {
        Flyway.configure()
                .dataSource(ethereumDataSource)
                .locations("classpath:db/migration/ethereum")
                .load()
                .migrate();
    }

    private void migrateBnbSmartChain() {
        Flyway.configure()
                .dataSource(bnbSmartChainDataSource)
                .locations("classpath:db/migration/bnb-smart-chain")
                .load()
                .migrate();
    }
}
