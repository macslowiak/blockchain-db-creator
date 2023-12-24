package blockchaindbcreator.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
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

@Profile("bnbsc")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "bnbSmartChainEntityManager",
        transactionManagerRef = "bnbSmartChainTransactionManager",
        basePackages = {"blockchaindbcreator.data.bnbsc"}
)
public class BnbSmartChainDbConfiguration {

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean bnbSmartChainEntityManager(
            @Qualifier("bnbSmartChainDataSource") DataSource dataSource, EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
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
}


