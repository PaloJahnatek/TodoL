package com.palo.todolist.mapper;

import com.palo.todolist.domain.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setNickname((resultSet.getString("nickname")));
        user.setEmail(resultSet.getString("email"));
        user.setAge(resultSet.getInt("age"));
        user.setPassword((resultSet.getString("password")));
        return user;

    }
}
