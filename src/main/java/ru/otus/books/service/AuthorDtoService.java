package ru.otus.books.service;

import ru.otus.books.dto.AuthorDto;
import ru.otus.books.dto.BookDto;

import java.util.List;

public interface AuthorDtoService {
    List<AuthorDto> getAllAuthors();
    List<BookDto> getAuthorBooks(String authorNickName);
    void add(String nickName, String lastName, String firstName, String middleName);
    void removeByNickName(String nickName);
}
