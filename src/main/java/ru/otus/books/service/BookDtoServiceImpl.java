package ru.otus.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.dto.BookDto;
import ru.otus.books.dto.CommentDto;
import ru.otus.books.models.Author;
import ru.otus.books.models.Book;
import ru.otus.books.models.Comment;
import ru.otus.books.repositories.AuthorRepository;
import ru.otus.books.repositories.BookRepository;
import ru.otus.books.repositories.CommentRepository;
import ru.otus.books.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookDtoServiceImpl implements BookDtoService {

    @Autowired
    BookRepository repo;

    @Autowired
    AuthorRepository authorRepo;

    @Autowired
    GenreRepository genreRepo;

    @Autowired
    CommentRepository commentRepo;

    @Override
    @Transactional(readOnly = true)
    public BookDto getById(long id) {
        return BookDto.createDto(repo.findById(id).orElseThrow(), true);
    }

    @Override
    @Transactional
    public BookDto add(String title, int page_count, String authorNickName, String genre) {
        Author author = authorRepo.findByNickNameIgnoreCase(authorNickName);
        Book book = new Book(
                0,
                title,
                page_count,
                author.getId(),
                genreRepo.findByGenreIgnoreCase(genre),
                new ArrayList<>());

        saveBoth(author, book);

        return BookDto.createDto(book);
    }

    @Override
    @Transactional
    public void removeBookById(long id) {
        Book book = repo.findById(id).orElseThrow();
        updateAuthorRemoveBook(book);
        removeCommentsByBook(book);
        repo.delete(book);
    }

    @Override
    @Transactional
    public void addBookComment(long bookId, String commentText) {
        Comment comment = insertComment(commentText);
        insertCommentInBooks(bookId, comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getBookComments(long bookId) {
        Book book = repo.findById(bookId).orElseThrow();
        List<Comment> comments = commentRepo.findByIdIn(book.getComments());
        return CommentDto.createDto(comments);
    }

    private void saveBoth(Author author, Book book) {
        book = repo.save(book);
        author.getBooks().add(book.getId());
        authorRepo.save(author);
    }

    private void removeCommentsByBook(Book book) {
        commentRepo.deleteAllById(book.getComments());
    }

    private void updateAuthorRemoveBook(Book book) {
        Author author = authorRepo.findById(book.getAuthor()).orElseThrow();
        author.getBooks().removeIf(b -> b.equals(book.getId()));
        authorRepo.save(author);
    }

    private Comment insertComment(String commentText) {
        Comment comment = new Comment(0, commentText);
        comment = commentRepo.save(comment);
        return comment;
    }

    private void insertCommentInBooks(long bookId, Comment comment) {
        Book book = repo.findById(bookId).orElseThrow();
        book.getComments().add(comment.getId());
        repo.save(book);
    }
}
