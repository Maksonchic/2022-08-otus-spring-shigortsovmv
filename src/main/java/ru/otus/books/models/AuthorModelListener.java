package ru.otus.books.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Service;
import ru.otus.books.service.SequenceGenerator;

@Service
public class AuthorModelListener extends AbstractMongoEventListener<Author> {

    @Autowired
    SequenceGenerator sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Author> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGenerator.generateSequence(Author.SEQUENCE_NAME));
        }
    }
}
