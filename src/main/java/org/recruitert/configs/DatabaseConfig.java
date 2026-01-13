package org.recruitert.configs;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(
    name = "app.embedded-postgres.enabled",
    havingValue = "true"
)

public class DatabaseConfig {
    @Bean(destroyMethod = "stop")
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("db")
            .withUsername("postgres")
            .withPassword("postgres");
    }

    @Bean
    public DataSource dataSource(PostgreSQLContainer<?> container) {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }
}
