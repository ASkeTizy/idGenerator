package com.example.demo.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

public class DriverManager {
    @Autowired
    private static Environment env;

    @Bean(name ="PostgresSQL")
    @Primary
    public static DataSource PostgreSQLDataSource()
    {

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("cdata.jdbc.postgresql.PostgreSQLDriver");
        dataSourceBuilder.url("jdbc:postgresql:User=postgres;Password=12345;Database=postgres;Server=127.0.0.1;Port=54320;");
        return dataSourceBuilder.build();
    }

    //@Override
    public void setEnvironment( final Environment environment) {
        env=environment;
    }
}
