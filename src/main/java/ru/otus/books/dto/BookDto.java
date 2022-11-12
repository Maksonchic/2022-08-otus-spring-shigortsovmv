package ru.otus.books.dto;

import lombok.EqualsAndHashCode;
import ru.otus.books.models.Book;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class BookDto {

    private final long id;
    private final String title;
    private final int pageCount;
    private final AuthorDto author;
    private final GenreDto genre;
    private final List<CommentDto> comments;

    public BookDto(long id, String title, int pageCount, AuthorDto author, GenreDto genre, List<CommentDto> comments) {
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
        AuthorDto authorDto = AuthorDto.createDto(book.getAuthor());
        GenreDto genreDto = GenreDto.createDto(book.getGenre());
        List<CommentDto> commentDtos = book.getComments().stream().map(CommentDto::createDto).toList();
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getPageCount(),
                authorDto,
                genreDto,
                commentDtos);
    }

    public static Book createEntity(final BookDto bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getPageCount(),
                AuthorDto.createEntity(bookDto.getAuthor()),
                GenreDto.createEntity(bookDto.getGenre()),
                bookDto.getComments().stream().map(CommentDto::createEntity).toList());
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

    public AuthorDto getAuthor() {
        return author;
    }

    public GenreDto getGenre() {
        return genre;
    }

    public List<CommentDto> getComments() {
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
