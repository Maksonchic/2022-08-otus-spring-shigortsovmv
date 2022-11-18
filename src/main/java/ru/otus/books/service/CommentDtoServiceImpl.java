package ru.otus.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.models.Author;
import ru.otus.books.models.Book;
import ru.otus.books.models.Comment;
import ru.otus.books.repositories.AuthorRepository;
import ru.otus.books.repositories.BookRepository;
import ru.otus.books.repositories.CommentRepository;

@Service
public class CommentDtoServiceImpl implements CommentDtoService {

    @Autowired
    CommentRepository repo;

    @Autowired
    BookRepository bookRepo;

    @Autowired
    AuthorRepository authorRepo;

    @Override
    @Transactional
    public void edit(long commentId, String newCommentText) {
        Comment comment = repo.findById(commentId);
        comment.setMessage(newCommentText);
        repo.save(comment);
    }

    @Override
    @Transactional
    public void removeComment(long commentId) {
        Book book = updateBookCommentInBook(commentId);
        updateBookCommentInAuthor(commentId, book);
        repo.deleteById(commentId);
    }

    private void updateBookCommentInAuthor(long commentId, Book book) {
        Author author = authorRepo.findByNickNameIgnoreCase(book.getAuthor().getNickName());
        for (Book b : author.getBooks()) {
            if (b.getComments().contains(commentId)) {
                b.setComments(book.getComments());
            }
        }
        authorRepo.save(author);
    }

    private Book updateBookCommentInBook(long commentId) {
        Book book = bookRepo.findByComments(commentId);
        book.getComments().removeIf(c -> c.equals(commentId));
        bookRepo.save(book);
        return book;
    }
}
