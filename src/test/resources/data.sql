insert into AUTHORS (AUTHOR_ID, NICKNAME, LAST_NAME, FIRST_NAME, MIDDLE_NAME) values (1, 'Michael', 'Last', 'Firstov', 'Middleich');
insert into GENRES (GENRE_ID, GENRE) values (1, 'Horror');
insert into BOOKS (BOOK_ID, TITLE, PAGE_COUNT, AUTHOR_ID, GENRE_ID) values (1, 'About us', 322, 1, 1);
insert into BOOKS (BOOK_ID, TITLE, PAGE_COUNT, AUTHOR_ID, GENRE_ID) values (2, 'About you', 228, 1, 1);
insert into COMMENTS (COMMENT_ID, MESSAGE, BOOK_ID) values (1, 'So bad, I should have bought a winrar', 2);
