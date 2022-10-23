package ru.otus.books.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.books.domain.Author;
import ru.otus.books.domain.Book;

import java.util.HashMap;
import java.util.List;

import static java.util.Objects.isNull;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Book> getAll() {
        return jdbcOperations.query(
                "select b.ID, TITLE, PAGE_COUNT, b.AUTHOR_ID, NICKNAME, LAST_NAME, FIRST_NAME, MIDDLE_NAME," +
                        "   b.GENRE_ID, g.GENRE" +
                        " from BOOKS b, AUTHORS a, GENRES g" +
                        " where a.ID = b.AUTHOR_ID" +
                        "   and b.GENRE_ID = g.ID",
                new MapSqlParameterSource(),
                new BookMapper());
    }

    @Override
    public int count() {
        Integer res = this.jdbcOperations.queryForObject("select count(1) from BOOKS",
                        new HashMap<>(),
                        Integer.class);
        return isNull(res) ? 0 : res;
    }

    @Override
    public void insert(final Book book) {
        jdbcOperations.update("insert into BOOKS (ID, TITLE, PAGE_COUNT, AUTHOR_ID, GENRE_ID) values (" +
                (book.id() == 0 ? "(select (MAX(ID)+1) from BOOKS)" : ":ID") +
                ", :TITLE, :PAGE_COUNT, :AUTHOR_ID, :GENRE_ID)",
                new MapSqlParameterSource()
                        .addValue("ID", book.id())
                        .addValue("TITLE", book.title())
                        .addValue("PAGE_COUNT", book.page_count())
                        .addValue("AUTHOR_ID", book.author().id())
                        .addValue("GENRE_ID", book.genre().id()));
    }

    @Override
    public Book getById(long id) {
        return this.jdbcOperations.queryForObject(
                "select b.ID, TITLE, PAGE_COUNT, b.AUTHOR_ID, NICKNAME, LAST_NAME, FIRST_NAME, MIDDLE_NAME," +
                        "   b.GENRE_ID, g.GENRE" +
                        " from BOOKS b, AUTHORS a, GENRES g" +
                        " where b.ID = :ID" +
                        "   and a.ID = b.AUTHOR_ID" +
                        "   and b.GENRE_ID = g.ID",
                new MapSqlParameterSource().addValue("ID", id),
                new BookMapper());
    }

    @Override
    public void remove(Book book) {
        jdbcOperations.update("delete BOOKS where" +
                        " ID = :ID" +
                        " and TITLE = :TITLE" +
                        " and PAGE_COUNT = :PAGE_COUNT" +
                        " and AUTHOR_ID = :AUTHOR_ID" +
                        " and GENRE_ID = :GENRE_ID",
                new MapSqlParameterSource()
                        .addValue("ID", book.id())
                        .addValue("TITLE", book.title())
                        .addValue("PAGE_COUNT", book.page_count())
                        .addValue("AUTHOR_ID", book.author().id())
                        .addValue("GENRE_ID", book.genre().id()));
    }

    @Override
    public void update(Book book) {
        jdbcOperations.update("update BOOKS set " +
                        "TITLE = :TITLE," +
                        "PAGE_COUNT = :PAGE_COUNT," +
                        "AUTHOR_ID = :AUTHOR_ID," +
                        "GENRE_ID = :GENRE_ID" +
                        " where ID = :ID",
                new MapSqlParameterSource()
                        .addValue("ID", book.id())
                        .addValue("TITLE", book.title())
                        .addValue("PAGE_COUNT", book.page_count())
                        .addValue("AUTHOR_ID", book.author().id())
                        .addValue("GENRE_ID", book.genre().id()));
    }

    @Override
    public void removeByAuthor(Author author) {
        jdbcOperations.update("delete BOOKS where AUTHOR_ID = :AUTHOR_ID",
                new MapSqlParameterSource().addValue("AUTHOR_ID", author.id()));
    }

    @Override
    public List<Book> getByAuthor(final Author author) {
        return jdbcOperations.query(
                "select b.ID, TITLE, PAGE_COUNT, b.AUTHOR_ID, NICKNAME, LAST_NAME, FIRST_NAME, MIDDLE_NAME," +
                        "   b.GENRE_ID, g.GENRE" +
                        " from BOOKS b, AUTHORS a, GENRES g" +
                        " where b.AUTHOR_ID = :AUTHOR_ID" +
                        "   and a.ID = b.AUTHOR_ID" +
                        "   and b.GENRE_ID = g.ID",
                new MapSqlParameterSource().addValue("AUTHOR_ID", author.id()),
                new BookMapper());
    }
}

