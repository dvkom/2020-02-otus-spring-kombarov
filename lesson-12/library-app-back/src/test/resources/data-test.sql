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
insert into users
    (id, `name`, password)
values
    (1, 'Bob', '{bcrypt}$2a$10$rOig86WsLHqinIvPQsxGrOckNvOgVJ8n8coVK/rLCtxZ.tlv4qbPu'),
    (2, 'Alice', '{bcrypt}$2a$10$WM9/7EqjyCSVTjHMSPilDuyI4BHUAIR8MhM13f6IDVv.o3wMtsvz.'),
    (3, 'Eve', '{bcrypt}$2a$10$RMU/Ya20Bq2hcwcNC.cAyOsVj21SpG72kBsUY3WaBHZXuJDr.a.7m');
