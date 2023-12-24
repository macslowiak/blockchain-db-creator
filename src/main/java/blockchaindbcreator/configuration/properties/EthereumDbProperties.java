package blockchaindbcreator.configuration.properties;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;


@Profile("ethereum")
@Configuration
public class EthereumDbProperties {

    @Bean
    @ConfigurationProperties("datasource.ethereum.hikari")
    public DataSource ethereumDataSource() {
        return ethereumDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.ethereum")
    public DataSourceProperties ethereumDataSourceProperties() {
        return new DataSourceProperties();
    }
}
