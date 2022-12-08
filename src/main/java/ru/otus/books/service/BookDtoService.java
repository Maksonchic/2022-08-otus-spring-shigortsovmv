package ru.otus.books.service;

import ru.otus.books.dto.BookDto;
import ru.otus.books.models.Comment;

import java.util.List;

public interface BookDtoService {
    BookDto getById(long id);
    BookDto add(String title, int page_count, String authorNickName, String genre);
    void removeBookById(long id);
    void addBookComment(long bookId, String commentText);
    List<Comment> getBookComments(long bookId);
    void removeComment(String commentId);
    void editComment(String commentId, String newCommentText);
}
