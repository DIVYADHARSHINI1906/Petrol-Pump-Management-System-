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
        String databaseUrl = System.getenv("DATABASE_URL");
        String springDatasourceUrl = System.getenv("SPRING_DATASOURCE_URL");
        String dbHost = System.getenv("DB_HOST");

        // 1. Render / Cloud PaaS PostgreSQL URL (postgres:// or postgresql://)
        if (StringUtils.hasText(databaseUrl) && (databaseUrl.startsWith("postgres://") || databaseUrl.startsWith("postgresql://"))) {
            try {
                URI uri = new URI(databaseUrl.replace("postgres://", "postgresql://"));
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
                String path = uri.getPath();
                String jdbcUrl = "jdbc:postgresql://" + host + ":" + port + path;

                properties.setUrl(jdbcUrl);
                properties.setUsername(username);
                properties.setPassword(password);
                properties.setDriverClassName("org.postgresql.Driver");
            } catch (Exception ignored) {
            }
        } 
        // 2. Explicit JDBC datasource URL
        else if (StringUtils.hasText(springDatasourceUrl)) {
            properties.setUrl(springDatasourceUrl);
        } 
        // 3. MySQL Host specified
        else if (StringUtils.hasText(dbHost) && !dbHost.equalsIgnoreCase("localhost")) {
            String dbPort = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
            String dbName = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "petrol_pump_db";
            properties.setUrl("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
            properties.setDriverClassName("com.mysql.cj.jdbc.Driver");
        } 
        // 4. Safe fallback for cloud deployments without an external database attached yet
        else if (!StringUtils.hasText(properties.getUrl()) || properties.getUrl().contains("localhost:3306")) {
            properties.setUrl("jdbc:h2:mem:petrolpumpdb;DB_CLOSE_DELAY=-1");
            properties.setDriverClassName("org.h2.Driver");
            properties.setUsername("sa");
            properties.setPassword("");
        }

        return properties.initializeDataSourceBuilder().build();
    }
}
