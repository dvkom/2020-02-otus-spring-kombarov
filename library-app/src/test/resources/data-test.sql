insert into authors
    (id, `name`, country, birth_date)
values
    (1, 'Isaac Asimov', 'USA', '1920-04-06'),
    (2, 'Jack London', 'USA', '1876-01-12');
insert into genres
    (id, `name`, description)
values
    (1, 'Science fiction', 'Description'),
    (2, 'Novel', 'Description');
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