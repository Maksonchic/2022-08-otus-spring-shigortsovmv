package ru.otus.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.books.models.Author;
import ru.otus.books.models.Book;
import ru.otus.books.models.Comment;
import ru.otus.books.models.Genre;
import ru.otus.books.repositories.AuthorRepositoryJpa;
import ru.otus.books.repositories.BookRepositoryJpa;
import ru.otus.books.repositories.CommentRepositoryJpa;
import ru.otus.books.repositories.GenreRepositoryJpa;

import java.util.ArrayList;

@ShellComponent
public class ShellController {

    @Autowired
    AuthorRepositoryJpa authorJpa;

    @Autowired
    BookRepositoryJpa bookJpa;

    @Autowired
    GenreRepositoryJpa genreJpa;

    @Autowired
    CommentRepositoryJpa commentJpa;

    // get book -id 1
    @ShellMethod(key = "get book -id", group = "books", value = ":id")
    public String getBookById(long id) {
        return bookJpa.findById(id).orElseThrow().toString();
    }

    // get book -author MichAEL
    @ShellMethod(key = "get book -author", group = "books", value = ":authorNickName")
    public String getBookByAuthor(String authorNickName) {
        Author a = authorJpa.findByNickName(authorNickName);
        StringBuilder sb = new StringBuilder();
        sb.append("[\r\n");
        a.getBooks().forEach((b) -> sb.append(b).append("\t\r\n"));
        sb.append("[");
        return sb.toString();
    }

    // add author me l f m
    // add book qweeee 323 me Horror
    // get book -author me
    // add book qweeee 323 Michael Horror
    @ShellMethod(key = "add book", group = "books", value = ":title :page_count :nickName_author :genre_name")
    public void addBook(String title, int page_count, String authorNickName, String genre) {
        bookJpa.save(new Book(
                0,
                title,
                page_count,
                authorJpa.findByNickName(authorNickName),
                genreJpa.findByGenre(genre),
                new ArrayList<>()));
    }

    @ShellMethod(key = "delete book", group = "books", value = ":id")
    public void removeBookById(long id) {
        bookJpa.remove(bookJpa.findById(id).orElseThrow());
    }

    @ShellMethod(key = "get authors", group = "authors")
    public String getAuthors() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\r\n");
        authorJpa.findAll().forEach((a) -> sb.append(a.toString()).append("\t\r\n"));
        sb.append("[");
        return sb.toString();
    }

    // add author me l f m
    @ShellMethod(key = "add author", group = "authors", value = ":nickName :last_name :first_name :middle_name")
    public void addAuthor(String nickName, String lastName, String firstName, String middleName) {
        authorJpa.save(new Author(0, nickName, lastName, firstName, middleName));
    }

    @ShellMethod(key = "delete author", group = "authors", value = ":nickName")
    public void removeAuthorById(String nickName) {
        authorJpa.remove(authorJpa.findByNickName(nickName));
    }

    @ShellMethod(key = "add comment", group = "comments", value = ":bookId :nickName")
    public void addComment(long bookId, String commentText) {
        Book book = bookJpa.findById(bookId).orElseThrow();
        book.getComments().add(new Comment(0, commentText));
        bookJpa.save(book);
    }

    @ShellMethod(key = "get comments", group = "comments", value = ":bookId")
    public String getBookComments(long bookId) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\r\n");
        bookJpa.findById(bookId).orElseThrow().getComments().forEach((a) -> sb.append(a.toString()).append("\t\r\n"));
        sb.append("[");
        return sb.toString();
    }

    @ShellMethod(key = "edit comment", group = "comments", value = ":id :newText")
    public void updateComment(long commentId, String newCommentText) {
        Comment comment = commentJpa.findById(commentId);
        comment.setMessage(newCommentText);
        commentJpa.save(comment);
    }

    @ShellMethod(key = "delete comment", group = "comments", value = ":id")
    public void removeComment(long commentId) {
        commentJpa.removeById(commentId);
    }

    @ShellMethod(key = "get genres", group = "genres")
    public String getGenres() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\r\n");
        genreJpa.findAll().forEach((a) -> sb.append(a.toString()).append("\t\r\n"));
        sb.append("[");
        return sb.toString();
    }

    @ShellMethod(key = "add genre", group = "genres", value = ":id :name")
    public void addGenre(int genreId, String name) {
        genreJpa.save(new Genre(genreId, name));
    }

    @ShellMethod(key = "delete genre", group = "genres", value = ":id")
    public void removeGenreById(int id) {
        genreJpa.remove(genreJpa.findById(id));
    }
}
