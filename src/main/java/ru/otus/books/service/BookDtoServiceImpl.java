package ru.otus.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.dto.AuthorDto;
import ru.otus.books.dto.BookDto;
import ru.otus.books.dto.GenreDto;
import ru.otus.books.models.Book;
import ru.otus.books.models.Comment;
import ru.otus.books.repositories.AuthorRepositoryJpa;
import ru.otus.books.repositories.BookRepositoryJpa;
import ru.otus.books.repositories.GenreRepositoryJpa;

import java.util.ArrayList;

@Service
public class BookDtoServiceImpl implements BookDtoService {

    @Autowired
    BookRepositoryJpa repo;

    @Autowired
    AuthorRepositoryJpa authorRepo;

    @Autowired
    GenreRepositoryJpa genreRepo;

    @Override
    @Transactional(readOnly = true)
    public BookDto getById(long id) {
        return BookDto.createDto(repo.findById(id).orElseThrow(), false);
    }

    @Override
    @Transactional
    public BookDto add(String title, int page_count, String authorNickName, String genre) {
        BookDto bookDto = new BookDto(
                0,
                title,
                page_count,
                AuthorDto.createDto(authorRepo.findByNickName(authorNickName)),
                GenreDto.createDto(genreRepo.findByGenre(genre)),
                new ArrayList<>());
        return BookDto.createDto(repo.save(BookDto.createEntity(bookDto)), true);
    }

    @Override
    @Transactional
    public void removeBookById(long id) {
        repo.remove(repo.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public void addBookComment(long bookId, String commentText) {
        Book book = repo.findById(bookId).orElseThrow();
        book.getComments().add(new Comment(0, commentText));
        repo.save(book);
    }
}
