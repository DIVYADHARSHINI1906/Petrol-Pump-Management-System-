package com.petrolpump.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource(DataSourceProperties properties) {
        String dbUrl = System.getenv("DATABASE_URL");
        if (StringUtils.hasText(dbUrl) && (dbUrl.startsWith("postgres://") || dbUrl.startsWith("postgresql://"))) {
            try {
                // Render and cloud PaaS provide DATABASE_URL as postgres://user:password@host:port/dbname
                // Standard JDBC driver requires jdbc:postgresql://host:port/dbname
                URI uri = new URI(dbUrl.replace("postgres://", "postgresql://"));
                String userInfo = uri.getUserInfo();
                String username = properties.getUsername();
                String password = properties.getPassword();
                if (userInfo != null && userInfo.contains(":")) {
                    String[] parts = userInfo.split(":", 2);
                    username = parts[0];
                    password = parts[1];
                }
                String host = uri.getHost();
                int port = uri.getPort() == -1 ? 5432 : uri.getPort();
                String path = uri.getPath(); // includes leading '/'
                String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + path;

                properties.setUrl(jdbcUrl);
                properties.setUsername(username);
                properties.setPassword(password);
                properties.setDriverClassName("org.postgresql.Driver");
            } catch (Exception ignored) {
                // Fallback to configured properties
            }
        }
        return properties.initializeDataSourceBuilder().build();
    }
}
