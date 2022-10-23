package ru.otus.books.domain;

public record Book(long id, String title, int page_count, Author author, Genre genre) {
}
