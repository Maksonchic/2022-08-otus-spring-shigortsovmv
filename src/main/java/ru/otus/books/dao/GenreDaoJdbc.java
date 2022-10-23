package ru.otus.books.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.books.domain.Genre;

import java.util.List;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public Genre getById(long id) {
        return jdbcOperations.queryForObject("select ID as GENRE_ID, GENRE from GENRES where ID = :ID",
                new MapSqlParameterSource().addValue("ID", id),
                new GenreMapper());
    }

    @Override
    public Genre getByGenre(String genre) {
        return jdbcOperations.queryForObject(
                "select ID as GENRE_ID, GENRE from GENRES where lower(GENRE) = lower(:GENRE)",
                new MapSqlParameterSource().addValue("GENRE", genre),
                new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbcOperations.query("select ID as GENRE_ID, GENRE from GENRES",
                new MapSqlParameterSource(),
                new GenreMapper());
    }

    @Override
    public void insert(Genre genre) {
        jdbcOperations.update("insert into GENRES (ID, GENRE) values (" +
                        (genre.id() == 0 ? "((select (MAX(ID)+1) from GENRES)" : ":ID") +
                        ", :GENRE)",
                new MapSqlParameterSource()
                        .addValue("ID", genre.id())
                        .addValue("GENRE", genre.genre()));
    }

    @Override
    public void update(Genre genre) {
        jdbcOperations.update("update GENRES set" +
                        " GENRE = :GENRE" +
                        " where ID = :ID",
                new MapSqlParameterSource()
                        .addValue("ID", genre.id())
                        .addValue("GENRE", genre.genre()));
    }

    @Override
    public void remove(Genre genre) {
        jdbcOperations.update("delete GENRES " +
                        " where ID = :ID and GENRE = :GENRE",
                new MapSqlParameterSource()
                        .addValue("ID", genre.id())
                        .addValue("GENRE", genre.genre()));
    }
}
