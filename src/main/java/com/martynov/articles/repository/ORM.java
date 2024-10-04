package com.martynov.articles.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class ORM {

    private final JdbcTemplate jdbcTemplate;

    public ORM(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <T> void create(String tableName, String[] columns, Object[] values) {
        String[] placeholders = new String[values.length];
        Arrays.fill(placeholders, "?");
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName,
                String.join(", ", columns),
                String.join(", ", placeholders));
        jdbcTemplate.update(sql, values);
    }


    public <T> T findById(String tableName, String idColumn, Object idValue, Class<T> clazz) {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", tableName, idColumn);
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(clazz);
        return jdbcTemplate.queryForObject(sql, rowMapper, idValue);
    }

    public <T> List<T> findAll(String tableName, Class<T> clazz) {
        String sql = String.format("SELECT * FROM %s", tableName);
        RowMapper<T> rowMapper = new BeanPropertyRowMapper<>(clazz);
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void update(String tableName, String[] columns, Object[] values, String idColumn, Object idValue) {
        StringBuilder setClause = new StringBuilder();
        for (String column : columns) {
            setClause.append(column).append(" = ?, ");
        }
        setClause.delete(setClause.length() - 2, setClause.length());

        String sql = String.format("UPDATE %s SET %s WHERE %s = ?", tableName, setClause.toString(), idColumn);
        jdbcTemplate.update(sql, appendToArray(values, idValue));
    }

    public void delete(String tableName, String idColumn, Object idValue) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", tableName, idColumn);
        jdbcTemplate.update(sql, idValue);
    }

    private Object[] appendToArray(Object[] values, Object newValue) {
        Object[] result = new Object[values.length + 1];
        System.arraycopy(values, 0, result, 0, values.length);
        result[values.length] = newValue;
        return result;
    }
}


