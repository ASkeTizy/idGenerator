package com.example.demo.dao;

import com.example.demo.entity.Book;
import com.example.demo.entity.EntityIdentifier;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SequenceDAO {
    private static final String jdbcUrl = "jdbc:postgresql://localhost:54320/postgres";
    private static final String username = "postgres";
    private static final String password = "12345";
    private final Connection connection;
    private final Map<String,Long> limitValues = new HashMap<String,Long>();
    private final Map<String,Long> currentValues = new HashMap<String,Long>();
    public SequenceDAO()  {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(jdbcUrl, username, password);
            getData();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public Long generateId(String type) {
        var limit = limitValues.get(type);
        var currentValue = currentValues.get(type);
        currentValue++;
        currentValues.put(type,currentValue);
        if( limit <= currentValue ) {

            PreparedStatement statement = null;
            try {
                statement = this.connection.prepareStatement("UPDATE \"LIBRARY\".sequencedata SET ID=? WHERE role=?");
                statement.setLong(1, limit + 100L);
                statement.setString(2, type);
                limitValues.put(type,limit+100L);
                statement.execute();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        ;
        return currentValues.get(type);
    }
    public void getData() {
        try {
            var statement = this.connection.prepareStatement("SELECT * FROM \"LIBRARY\".sequencedata");

            ResultSet resultSet = statement.executeQuery();
            List<Book> books = new ArrayList<>();

            while (resultSet.next())
            {
                String role = resultSet.getString("role");
                Long id = resultSet.getLong("id");
                limitValues.put(role,id);
                currentValues.put(role,id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
