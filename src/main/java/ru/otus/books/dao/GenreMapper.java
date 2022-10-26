package ru.otus.books.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.books.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(
                rs.getLong("GENRE_ID"),
                rs.getString("GENRE"));
    }
}
