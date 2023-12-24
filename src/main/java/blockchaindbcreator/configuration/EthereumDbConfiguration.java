package blockchaindbcreator.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
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

@Profile("ethereum")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "ethereumEntityManager",
        transactionManagerRef = "ethereumTransactionManager",
        basePackages = {"blockchaindbcreator.data.ethereum"}
)
public class EthereumDbConfiguration {

    @Bean
    public LocalContainerEntityManagerFactoryBean ethereumEntityManager(
            @Qualifier("ethereumDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
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
}


