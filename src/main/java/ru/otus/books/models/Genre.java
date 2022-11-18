package ru.otus.books.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "GENRES")
public class Genre {

    @Transient
    public static final String SEQUENCE_NAME = "genres_sequence";

    @Id
    private long id;
    private String genre;
}
