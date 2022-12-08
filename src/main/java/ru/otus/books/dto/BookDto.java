package ru.otus.books.dto;

import lombok.EqualsAndHashCode;
import ru.otus.books.models.Book;
import ru.otus.books.models.Comment;

import java.util.List;

@EqualsAndHashCode
public class BookDto {

    private final long id;
    private final String title;
    private final int pageCount;
    private final Long author;
    private final GenreDto genre;
    private final List<Comment> comments;

    public BookDto(long id, String title, int pageCount, Long author, GenreDto genre, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.pageCount = pageCount;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }

    public static BookDto createDto(final Book book) {
        return BookDto.createDto(book, false);
    }

    public static BookDto createDto(final Book book, boolean isAuthorContains) {
        if (!isAuthorContains) {
            book.setAuthor(null);
        }
        GenreDto genreDto = GenreDto.createDto(book.getGenre());
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getPageCount(),
                book.getAuthor(),
                genreDto,
                book.getComments());
    }

    public static Book createEntity(final BookDto bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getPageCount(),
                bookDto.getAuthor(),
                GenreDto.createEntity(bookDto.getGenre()),
                bookDto.getComments());
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPageCount() {
        return pageCount;
    }

    public Long getAuthor() {
        return author;
    }

    public GenreDto getGenre() {
        return genre;
    }

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "\r\n{" +
                "\"id\":" + id +
                ",\"title\":\"" + title + "\"" +
                ",\"pageCount\":" + pageCount +
                ",\"author\":" + (author == null ? "null" : author) +
                ",\"genre\":" + genre +
                ",\"comments\":" + comments +
                "}";
    }
}
