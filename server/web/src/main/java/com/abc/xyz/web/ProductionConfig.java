package com.abc.xyz.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration
@Profile(Profiles.PRODUCTION)
public class ProductionConfig extends BaseConfig {

    @Override
    @Bean(destroyMethod = "")
    public DataSource dataSource() {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        lookup.setResourceRef(true);
        return lookup.getDataSource("jdbc/BLOGDS");
    }
}