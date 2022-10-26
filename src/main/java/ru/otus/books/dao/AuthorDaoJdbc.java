package ru.otus.books.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.books.domain.Author;

import java.util.List;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final BookDao bookDao;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbcOperations, BookDao bookDao) {
        this.jdbcOperations = jdbcOperations;
        this.bookDao = bookDao;
    }

    @Override
    public Author getById(long id) {
        return jdbcOperations.queryForObject(
                "select ID, NICKNAME, LAST_NAME, FIRST_NAME, MIDDLE_NAME from AUTHORS where ID = :ID",
                new MapSqlParameterSource().addValue("ID", id),
                new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbcOperations.query("select ID, NICKNAME, LAST_NAME, FIRST_NAME, MIDDLE_NAME from AUTHORS",
                new MapSqlParameterSource(),
                new AuthorMapper());
    }

    @Override
    public Author getByNickName(String nickName) {
        try {
            return jdbcOperations.queryForObject(
                    "select ID, NICKNAME, LAST_NAME, FIRST_NAME, MIDDLE_NAME" +
                            " from AUTHORS where lower(NICKNAME) = lower(:NICKNAME)",
                    new MapSqlParameterSource().addValue("NICKNAME", nickName),
                    new AuthorMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void update(Author author) {
        jdbcOperations.update("update AUTHORS set" +
                " NICKNAME = :NICKNAME," +
                " LAST_NAME = :LAST_NAME," +
                " FIRST_NAME = :FIRST_NAME," +
                " MIDDLE_NAME = :MIDDLE_NAME" +
                " where ID = :ID",
                new MapSqlParameterSource()
                        .addValue("NICKNAME", author.nickname())
                        .addValue("LAST_NAME", author.last_name())
                        .addValue("FIRST_NAME", author.first_name())
                        .addValue("MIDDLE_NAME", author.middle_name())
                        .addValue("ID", author.id()));
    }

    @Override
    public void remove(Author author) {
        bookDao.removeByAuthor(author);
        jdbcOperations.update("delete AUTHORS where" +
                        " ID = :ID" +
                        " and NICKNAME = :NICKNAME" +
                        " and LAST_NAME = :LAST_NAME" +
                        " and FIRST_NAME = :FIRST_NAME" +
                        " and MIDDLE_NAME = :MIDDLE_NAME",
                new MapSqlParameterSource()
                        .addValue("ID", author.id())
                        .addValue("NICKNAME", author.nickname())
                        .addValue("LAST_NAME", author.last_name())
                        .addValue("FIRST_NAME", author.first_name())
                        .addValue("MIDDLE_NAME", author.middle_name()));
    }

    @Override
    public void insert(final Author author) {
        jdbcOperations.update(
                "insert into AUTHORS (ID, NICKNAME, LAST_NAME, FIRST_NAME, MIDDLE_NAME) values (" +
                        (author.id() == 0 ? "(select (MAX(ID)+1) from AUTHORS)" : ":ID") +
                        ", :NICKNAME, :LAST_NAME, :FIRST_NAME, :MIDDLE_NAME)",
                new MapSqlParameterSource()
                        .addValue("ID", author.id())
                        .addValue("NICKNAME", author.nickname())
                        .addValue("LAST_NAME", author.last_name())
                        .addValue("FIRST_NAME", author.first_name())
                        .addValue("MIDDLE_NAME", author.middle_name()));
    }
}

