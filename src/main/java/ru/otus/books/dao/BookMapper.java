package ru.otus.books.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.books.domain.Author;
import ru.otus.books.domain.Book;
import ru.otus.books.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(
                rs.getLong("ID"),
                rs.getString("TITLE"),
                rs.getInt("PAGE_COUNT"),
                new Author(
                        rs.getLong("AUTHOR_ID"),
                        rs.getString("NICKNAME"),
                        rs.getString("LAST_NAME"),
                        rs.getString("FIRST_NAME"),
                        rs.getString("MIDDLE_NAME")),
                new Genre(
                        rs.getLong("GENRE_ID"),
                        rs.getString("GENRE")));
    }
}
