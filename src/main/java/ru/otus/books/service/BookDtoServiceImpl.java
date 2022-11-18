package ru.otus.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.dto.AuthorDto;
import ru.otus.books.dto.BookDto;
import ru.otus.books.dto.CommentDto;
import ru.otus.books.dto.GenreDto;
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
        return BookDto.createDto(repo.findById(id), true);
    }

    @Override
    @Transactional
    public BookDto add(String title, int page_count, String authorNickName, String genre) {
        BookDto bookDto = new BookDto(
                0,
                title,
                page_count,
                AuthorDto.createDto(authorRepo.findByNickNameIgnoreCase(authorNickName)),
                GenreDto.createDto(genreRepo.findByGenreIgnoreCase(genre)),
                new ArrayList<>());

        Book entity = BookDto.createEntity(bookDto);
        entity.getAuthor().setBooks(null);
        repo.save(entity);

        Author author = authorRepo.findByNickNameIgnoreCase(authorNickName);
        entity.setAuthor(null);
        author.getBooks().add(entity);
        authorRepo.save(author);

        return bookDto;
    }

    @Override
    @Transactional
    public void removeBookById(long id) {
        Book book = repo.findById(id);
        updateAuthorRemoveBook(book);
        removeCommentsByBook(book);
        repo.delete(book);
    }

    private void removeCommentsByBook(Book book) {
        commentRepo.deleteAllById(book.getComments());
    }

    private void updateAuthorRemoveBook(Book book) {
        Author author = authorRepo.findByNickNameIgnoreCase(book.getAuthor().getNickName());
        author.getBooks().removeIf(b -> b.getId() == book.getId());
        authorRepo.save(author);
    }

    @Override
    @Transactional
    public void addBookComment(long bookId, String commentText) {
        Comment comment = insertComment(commentText);
        Book book = insertCommentInBooks(bookId, comment);
        updateAuthor(book);
    }

    private Comment insertComment(String commentText) {
        Comment comment = new Comment(0, commentText);
        comment = commentRepo.save(comment);
        return comment;
    }

    private Book insertCommentInBooks(long bookId, Comment comment) {
        Book book = repo.findById(bookId);
        book.getComments().add(comment.getId());
        return repo.save(book);
    }

    private void updateAuthor(Book book) {
        Author author = authorRepo.findByNickNameIgnoreCase(book.getAuthor().getNickName());
        for (Book b : author.getBooks()) {
            if (b.getId() == book.getId()) {
                b.setComments(book.getComments());
                break;
            }
        }
        authorRepo.save(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getBookComments(long bookId) {
        Book book = repo.findById(bookId);
        List<Comment> comments = commentRepo.findByIdIn(book.getComments());
        return CommentDto.createDto(comments);
    }
}
