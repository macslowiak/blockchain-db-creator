package blockchaindbcreator.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

@Profile("ethereum")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "ethereumEntityManager",
        transactionManagerRef = "ethereumTransactionManager",
        basePackages = {"blockchaindbcreator.data.ethereum"}
)
@RequiredArgsConstructor
public class EthereumDbConfiguration {

    @Qualifier("bnbSmartChainDataSource")
    private final DataSource dataSource;

    @Value("${datasource.ethereum.hibernate.show-sql}")
    private String showSql;

    @Value("${datasource.ethereum.hibernate.order-inserts}")
    private String orderInserts;

    @Value("${datasource.ethereum.hibernate.jdbc-batch-size}")
    private Integer batchSize;

    @Bean
    public LocalContainerEntityManagerFactoryBean ethereumEntityManager(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .properties(Map.of(
                        "hibernate.jdbc.batch_size", batchSize,
                        "hibernate.order_inserts", orderInserts,
                        "hibernate.show_sql", showSql
                ))
                .packages("blockchaindbcreator.data.ethereum")
                .build();
    }

    @Bean
    public PlatformTransactionManager ethereumTransactionManager(
            @Qualifier("ethereumEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

        return transactionManager;
    }

    @PostConstruct
    private void flywayMigration() {
        Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration/ethereum")
                .load()
                .migrate();
    }
}
