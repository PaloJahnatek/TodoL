package com.palo.todolist.services.impl;

import com.palo.todolist.domain.Task;
import com.palo.todolist.mapper.TaskRowMapper;
import com.palo.todolist.services.api.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.Instant;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final JdbcTemplate jdbcTemplate;
    private final TaskRowMapper taskRowMapper = new TaskRowMapper();

    //dependency injection cez konštruktor
    @Autowired
    public TaskServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // vraciame všetky tasky tak že ich vytiahneme cez sql
    @Override
    public List<Task> getTasks() {
        final String sql = "SELECT * FROM task";
        return jdbcTemplate.query(sql, taskRowMapper);
    }

    // chceme len jeden task na základe jeho id
    @Override
    public Task get(Integer id) {
        final String sql = "SELECT * FROM task WHERE task.id =" + id;
        try {
            return jdbcTemplate.queryForObject(sql, taskRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    // len jeden task na zákalade jeho usera
    @Override
    public List<Task> getTaskByUserId(Integer userId) {
        final String sql = "SELECT * FROM task WHERE task.userId =" + userId;
        try {
            return jdbcTemplate.query(sql, taskRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    // pridávame task
    @Override
    public Integer add(Task task) {
        final String sql = "INSERT INTO task(userId, name, status, category, description, createdAt) VALUES (?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder(); // keyholder vráti vygenerované id

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, task.getUserId());// z objektu ktorý vstúpil do metódy sa vyberie userId
                // a vloží sa do parameterindex 1 a to sa vloží do sql príkazu
                preparedStatement.setString(2, task.getName());
                preparedStatement.setInt(3, task.getStatus());
                preparedStatement.setString(4, task.getCategory());
                preparedStatement.setString(5, task.getDescription());
                preparedStatement.setTimestamp(6, Timestamp.from(Instant.now()));
                return preparedStatement;
            }
        }, keyHolder);
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue(); // vracia nám integer ako hodnotu
        } else {
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        final String sql = "DELETE FROM task WHERE id = ? ";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void update(Integer id, Task task) {
        final String sql = "UPDATE task SET name = ?, status = ?, category = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sql, task.getName(), task.getStatus(), task.getCategory(), task.getDescription(), id);
    }
}
