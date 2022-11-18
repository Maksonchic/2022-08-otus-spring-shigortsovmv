package ru.otus.books.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Service;
import ru.otus.books.service.SequenceGenerator;

@Service
public class GenreModelListener extends AbstractMongoEventListener<Genre> {

    @Autowired
    SequenceGenerator sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Genre> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Genre.SEQUENCE_NAME));
        }
    }
}
