insert into authors
    (id, `name`, country)
values
    (1, 'Isaac Asimov', 'USA'),
    (2, 'Jack London', 'USA');
insert into genres
    (id, `name`)
values
    (1, 'Science fiction'),
    (2, 'Novel');
insert into books
    (id, title, page_count, author_id, genre_id)
values
    (1, 'The End of Eternity', '325', 1, 1),
    (2, 'The Sea-Wolf', '425', 2, 2);
insert into comments
    (id, text, book_id)
values
    (1, 'Text1', 1),
    (2, 'Text2', 2);