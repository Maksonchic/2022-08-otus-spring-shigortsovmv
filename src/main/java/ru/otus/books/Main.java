package ru.otus.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("ru.otus.books.repositories")
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class);
	}
}
