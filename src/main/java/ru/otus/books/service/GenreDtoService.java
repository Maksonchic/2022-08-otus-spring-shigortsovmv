package ru.otus.books.service;

import ru.otus.books.dto.GenreDto;

import java.util.List;

public interface GenreDtoService {
    List<GenreDto> getAllGenres();
    void add(String name);
}
