package ru.otus.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.dto.AuthorDto;
import ru.otus.books.dto.BookDto;
import ru.otus.books.models.Author;
import ru.otus.books.models.Comment;
import ru.otus.books.repositories.AuthorRepository;
import ru.otus.books.repositories.BookRepository;
import ru.otus.books.repositories.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorDtoServiceImpl implements AuthorDtoService {

    @Autowired
    private AuthorRepository repo;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<AuthorDto> getAllAuthors() {
        return repo.findAll().stream().map(AuthorDto::createDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAuthorBooks(String authorNickName) {
        Author author = repo.findByNickNameIgnoreCase(authorNickName);
        return author.getBooks().stream().map(b -> BookDto.createDto(b, true)).toList();
    }

    @Override
    public void add(String nickName, String lastName, String firstName, String middleName) {
        repo.save(new Author(0, nickName, lastName, firstName, middleName));
    }

    @Override
    @Transactional
    public void removeByNickName(String nickName) {
        Author author = repo.findByNickNameIgnoreCase(nickName);
        List<Comment> comms = new ArrayList<>();
        author.getBooks().forEach(b -> comms.addAll(b.getComments()));
        repo.delete(author);
        bookRepository.deleteAll(author.getBooks());
        commentRepository.deleteAll(comms);
    }
}
