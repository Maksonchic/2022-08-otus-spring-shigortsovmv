package ru.otus.books.controller;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.books.dao.AuthorDao;
import ru.otus.books.dao.BookDao;
import ru.otus.books.dao.GenreDao;
import ru.otus.books.domain.Author;
import ru.otus.books.domain.Book;

import java.util.List;

@ShellComponent
public class ShellController {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public ShellController(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @ShellMethod(key = "add book")
    public void addBook(String title, int page_count, String authorNickName, String genre) {
        bookDao.insert(new Book(
                0,
                title,
                page_count,
                authorDao.getByNickName(authorNickName),
                genreDao.getByGenre(genre)));
    }

    @ShellMethod(key = "get books")
    public String getAllBooks() {
        StringBuilder sb = new StringBuilder();
        sb.append("[\r\n");
        bookDao.getAll().forEach((b) -> sb.append(b.toString()).append("\t\r\n"));
        sb.append("[");
        return sb.toString();
    }

    @ShellMethod(key = "b")
    public String getBookById(long id) {
        Book byId = bookDao.getById(id);
        return String.valueOf(byId);
    }

    @ShellMethod(key = "c")
    public String getByAuthor(long authorId) {
        Author a = authorDao.getById(authorId);
        List<Book> q = bookDao.getByAuthor(a);
        return String.valueOf(q);
    }

    @ShellMethod(key = "d")
    public String getAuthByNick(String nickName) {
        Author a = authorDao.getByNickName(nickName);
        List<Book> q = bookDao.getByAuthor(a);
        return String.valueOf(q);
    }
}
