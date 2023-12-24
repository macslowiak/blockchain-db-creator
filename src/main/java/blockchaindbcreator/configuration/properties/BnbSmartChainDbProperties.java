package blockchaindbcreator.configuration.properties;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;


@Profile("bnbsc")
@Configuration
public class BnbSmartChainDbProperties {

    @Bean
    @Primary
    @ConfigurationProperties("datasource.bnb-sc.hikari")
    public DataSource bnbSmartChainDataSource() {
        return bnbSmartChainDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "datasource.bnb-sc")
    public DataSourceProperties bnbSmartChainDataSourceProperties() {
        return new DataSourceProperties();
    }
}
