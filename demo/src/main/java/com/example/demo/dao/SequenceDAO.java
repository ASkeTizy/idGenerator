package com.example.demo.dao;

import com.example.demo.entity.Book;
import com.example.demo.entity.EntityIdentifier;
import com.example.demo.entity.SequenceData;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class SequenceDAO {
    private static final String jdbcUrl = "jdbc:postgresql://localhost:54320/postgres";
    private static final String username = "postgres";
    private static final String password = "12345";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcClient jdbcClient;
    private final Map<String, Long> limitValues = new HashMap<>();
    private final Map<String, Long> currentValues = new HashMap<String, Long>();

    public SequenceDAO() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcClient = JdbcClient.create(dataSource);
        getData();
    }

    public Long generateId(String type) {
        var limit = limitValues.get(type);
        var currentValue = currentValues.get(type);
        currentValue++;
        currentValues.put(type, currentValue);
        if (limit <= currentValue) {
            this.jdbcClient.sql("UPDATE \"LIBRARY\".sequencedata SET ID=:id WHERE role=:role")
                    .param("id",limit + 100L)
                    .param("role",type)
                    .update();
            limitValues.put(type, limit + 100L);
        }
        return currentValues.get(type);
    }

    public void getData() {
        limitValues.putAll(this.jdbcClient.sql("SELECT * FROM \"LIBRARY\".sequencedata")
                .query().listOfRows().stream().collect(Collectors.toMap(s -> (String) s.get("role"),
                        s -> (Long) s.get("id"))));
        currentValues.putAll(limitValues);
    }
}
