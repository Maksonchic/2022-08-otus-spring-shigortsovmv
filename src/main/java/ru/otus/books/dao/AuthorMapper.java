package ru.otus.books.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.books.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Author(
                rs.getLong("ID"),
                rs.getString("NICKNAME"),
                rs.getString("LAST_NAME"),
                rs.getString("FIRST_NAME"),
                rs.getString("MIDDLE_NAME"));
    }
}
