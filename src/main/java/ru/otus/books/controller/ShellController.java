package ru.otus.books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.books.models.Book;
import ru.otus.books.repositories.AuthorRepositoryJpa;
import ru.otus.books.repositories.BookRepositoryJpa;
import ru.otus.books.repositories.CommentRepositoryJpa;
import ru.otus.books.repositories.GenreRepositoryJpa;

import java.util.ArrayList;
import java.util.List;

@ShellComponent
public class ShellController {

    @Autowired
    AuthorRepositoryJpa authorJpa;

    @Autowired
    BookRepositoryJpa bookJpa;

    @Autowired
    CommentRepositoryJpa commentJpa;

    @Autowired
    GenreRepositoryJpa genreJpa;

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

    @ShellMethod(key = "get books", group = "books")
    public String getAllBooks() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\r\n");
        List<Book> all = bookJpa.findAll();
        all.forEach((b) -> sb.append(b.toString()).append("\t\r\n"));
        sb.append("[");
        return sb.toString();
    }

    @ShellMethod(key = "get book -id", group = "books", value = ":id")
    public String getBookById(long id) {
        Book byId = bookJpa.findById(id).orElseThrow();
        return String.valueOf(byId);
    }

    /*
    @ShellMethod(key = "get book -author", group = "books", value = ":authorNickName")
    public String getBookByAuthor(String authorNickName) {
        Author a = authorDao.getByNickName(authorNickName);
        StringBuilder sb = new StringBuilder();
        sb.append("[\r\n");
        bookDao.getByAuthor(a).forEach((b) -> sb.append(b.toString()).append("\t\r\n"));
        sb.append("[");
        return sb.toString();
    }
    */

    /*
    @ShellMethod(key = "delete book", group = "books", value = ":id")
    public void removeBookById(long id) {
        bookDao.remove(bookDao.getById(id));
    }
    */

    /*
    @ShellMethod(key = "get authors", group = "authors")
    public String getAuthors() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\r\n");
        authorDao.getAll().forEach((a) -> sb.append(a.toString()).append("\t\r\n"));
        sb.append("[");
        return sb.toString();
    }
    */

    /*
    @ShellMethod(key = "add author", group = "authors", value = ":nickName :last_name :first_name :middle_name")
    public void addAuthor(String nickName, String lastName, String firstName, String middleName) {
        authorDao.insert(new Author(0, nickName, lastName, firstName, middleName));
    }
    */

    /*
    @ShellMethod(key = "delete author", group = "authors", value = ":id")
    public void removeAuthorById(long id) {
        authorDao.remove(authorDao.getById(id));
    }
    */
}
