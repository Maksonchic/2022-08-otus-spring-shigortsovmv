package ru.otus.books.dao.utils;

import ru.otus.books.models.Author;
import ru.otus.books.models.Book;
import ru.otus.books.models.Genre;

import java.util.ArrayList;

public abstract class DataCreator {

    public static Book createDataFirstBook() {
        Author aNew = createDataFirstAuthor();
        Genre gNew = createDataFirstGenre();
        return new Book(1, "About us", 322, aNew, gNew, new ArrayList<>());
    }

    public static Author createDataFirstAuthor() {
        return new Author(1, "Michael", "Last", "Firstov", "Middleich");
    }

    public static Genre createDataFirstGenre() {
        return new Genre(1, "Horror2");
    }
}
