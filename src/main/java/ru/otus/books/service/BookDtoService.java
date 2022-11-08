package ru.otus.books.service;

import ru.otus.books.dto.BookDto;

public interface BookDtoService {
    BookDto getById(long id);
    BookDto add(String title, int page_count, String authorNickName, String genre);
    void removeBookById(long id);
    void addBookComment(long bookId, String commentText);
}
