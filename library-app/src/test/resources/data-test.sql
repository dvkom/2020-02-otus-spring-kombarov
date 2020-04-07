insert into author
    (id, `name`, country, birth_date)
values
    (1, 'Isaac Asimov', 'USA', '1920-04-06'),
    (2, 'Jack London', 'USA', '1876-01-12');
insert into genre
    (id, `name`, description)
values
    (1, 'Science fiction', 'Description'),
    (2, 'Novel', 'Description');
insert into book
    (id, title, page_count, author_id, genre_id)
values
    (1, 'The End of Eternity', '325', 1, 1),
    (2, 'The Sea-Wolf', '425', 2, 2);