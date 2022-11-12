package ru.otus.books.service;

import ru.otus.books.dto.BookDto;
import ru.otus.books.dto.CommentDto;

import java.util.List;

public interface BookDtoService {
    BookDto getById(long id);
    BookDto add(String title, int page_count, String authorNickName, String genre);
    void removeBookById(long id);
    void addBookComment(long bookId, String commentText);
    List<CommentDto> getBookComments(long bookId);
}
