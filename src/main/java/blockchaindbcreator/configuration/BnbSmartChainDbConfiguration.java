package blockchaindbcreator.configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

@Profile("bnbsc")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "bnbSmartChainEntityManager",
        transactionManagerRef = "bnbSmartChainTransactionManager",
        basePackages = {"blockchaindbcreator.data.bnbsc"}
)
@RequiredArgsConstructor
public class BnbSmartChainDbConfiguration {

    @Qualifier("bnbSmartChainDataSource")
    private final DataSource dataSource;

    @Value("${datasource.bnbsc.hibernate.show-sql}")
    private String showSql;

    @Value("${datasource.bnbsc.hibernate.order-inserts}")
    private String orderInserts;

    @Value("${datasource.bnbsc.hibernate.jdbc-batch-size}")
    private Integer batchSize;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean bnbSmartChainEntityManager(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .properties(Map.of(
                        "hibernate.jdbc.batch_size", batchSize,
                        "hibernate.order_inserts", orderInserts,
                        "hibernate.show_sql", showSql
                ))
                .packages("blockchaindbcreator.data.bnbsc")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager bnbSmartChainTransactionManager(
            @Qualifier("bnbSmartChainEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());

        return transactionManager;
    }

    @PostConstruct
    private void flywayMigration() {
        Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration/bnb-smart-chain")
                .load()
                .migrate();
    }
}


