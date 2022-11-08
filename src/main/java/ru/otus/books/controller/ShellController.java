package ru.otus.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.books.service.AuthorDtoService;
import ru.otus.books.service.BookDtoService;
import ru.otus.books.service.CommentDtoService;
import ru.otus.books.service.GenreDtoService;

import java.util.stream.Collectors;

@ShellComponent
public class ShellController {

    @Autowired
    AuthorDtoService authorService;

    @Autowired
    BookDtoService bookService;

    @Autowired
    GenreDtoService genreService;

    @Autowired
    CommentDtoService commentService;

    // get book -id 1
    @ShellMethod(key = "get book -id", group = "books", value = ":id")
    public String getBookById(long id) {
        return bookService.getById(id).toString();
    }

    // get book -author MichAEL
    @ShellMethod(key = "get book -author", group = "books", value = ":authorNickName")
    public String getBookByAuthor(String authorNickName) {
        // convert books list to String json
        return "[" +
                authorService
                        .getAuthorBooks(authorNickName)
                        .stream()
                        .map(a -> a.toString())
                        .collect(Collectors.joining(",")) +
                "\r\n]";
    }

    // add author me l f m
    // add book qweeee 323 Michael Horror
    // add book qweeee 323 me Horror
    // get book -author Michael
    // add book qweeee 323 Michael Horror
    @ShellMethod(key = "add book", group = "books", value = ":title :page_count :nickName_author :genre_name")
    public void addBook(String title, int page_count, String authorNickName, String genre) {
        bookService.add(title, page_count, authorNickName, genre);
    }

    // delete book 2
    @ShellMethod(key = "delete book", group = "books", value = ":id")
    public void removeBookById(long id) {
        bookService.removeBookById(id);
    }

    @ShellMethod(key = "get authors", group = "authors")
    public String getAuthors() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\r\n");
        sb.append(authorService.getAllAuthors().stream()
                .map(a -> a.toString())
                .collect(Collectors.joining(",")));
        sb.append("]");

        return sb.toString();
    }

    // add author me l f m
    @ShellMethod(key = "add author", group = "authors", value = ":nickName :last_name :first_name :middle_name")
    public void addAuthor(String nickName, String lastName, String firstName, String middleName) {
        authorService.add(nickName, lastName, firstName, middleName);
    }

    // delete author Michael
    @ShellMethod(key = "delete author", group = "authors", value = ":nickName")
    public void removeAuthorById(String nickName) {
        authorService.removeByNickName(nickName);
    }

    // add comment 2 "so many words"
    // get book -author Michael
    @ShellMethod(key = "add comment", group = "comments", value = ":bookId :nickName")
    public void addComment(long bookId, String commentText) {
        bookService.addBookComment(bookId, commentText);
    }

    @ShellMethod(key = "get comments", group = "comments", value = ":bookId")
    public String getBookComments(long bookId) {
        return "[\r\n" +
                bookService.getBookComments(bookId).stream()
                        .map(c -> c.toString())
                        .collect(Collectors.joining(",")) +
                "\r\n]";
    }

    @ShellMethod(key = "edit comment", group = "comments", value = ":id :newText")
    public void updateComment(long commentId, String newCommentText) {
        commentService.edit(commentId, newCommentText);
    }

    @ShellMethod(key = "delete comment", group = "comments", value = ":id")
    public void removeComment(long commentId) {
        commentService.removeComment(commentId);
    }

    @ShellMethod(key = "get genres", group = "genres")
    public String getGenres() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\r\n");
        genreService.getAllGenres().forEach((a) -> sb.append(a.toString()).append("\t\r\n"));
        sb.append("]");
        return sb.toString();
    }

    @ShellMethod(key = "add genre", group = "genres", value = ":id :name")
    public void addGenre(String name) {
        genreService.add(name);
    }

    @ShellMethod(key = "delete genre", group = "genres", value = ":genre")
    public void removeGenreById(String genre) {
        genreService.removeGenre(genre);
    }
}
