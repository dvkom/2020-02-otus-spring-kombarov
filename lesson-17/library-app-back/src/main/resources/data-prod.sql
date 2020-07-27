insert into authors
    (id, name, country)
values
    (1, 'Isaac Asimov', 'USA'),
    (2, 'Jack London', 'USA'),
    (3, 'Clifford Simak', 'USA'),
    (4, 'Victor Pelevin', 'Russia');

insert into genres
    (id, name)
values
    (1, 'Science fiction'),
    (2, 'Novel'),
    (3, 'Detective fiction');

insert into books
    (id, title, page_count, author_id, genre_id)
values
    (1, 'The End of Eternity', '325', 1, 1),
    (2, 'The Sea-Wolf', '425', 2, 2),
    (3, 'White Fang', '288', 2, 2),
    (4, 'Casebook of the Black Widowers', '215', 1, 3),
    (5, 'Way Station', '238', 3, 1),
    (6, 'Destiny Doll', '192', 3, 1),
    (7, 'Chapayev and Void', '448', 4, 2),
    (8, 'The Assassination Bureau', '290', 2, 3);

insert into comments
    (id, text, book_id)
values
    (1, 'It is a good book!', 3),
    (2, 'Not bad!', 8),
    (3, 'O_o', 2),
    (4, 'Some text!', 4),
    (5, 'What a ?', 5),
    (6, 'Where is my mind?', 6),
    (7, 'I am so tired because I wrote all this comments', 7),
    (8, 'I recommend...', 1),
    (9, 'It is a good book!', 3),
    (10, 'Not bad!', 1),
    (11, 'O_o', 1),
    (12, 'Some text!', 2),
    (13, 'What a ?', 3),
    (14, 'Where is my mind?', 4),
    (15, 'I am so tired because I wrote all this comments', 5),
    (16, 'I recommend...', 6);

insert into users
    (id, name, role, password)
values
    (1, 'Bob', 'EDITOR', '{bcrypt}$2a$10$rOig86WsLHqinIvPQsxGrOckNvOgVJ8n8coVK/rLCtxZ.tlv4qbPu'),
    (2, 'Alice', 'USER', '{bcrypt}$2a$10$WM9/7EqjyCSVTjHMSPilDuyI4BHUAIR8MhM13f6IDVv.o3wMtsvz.'),
    (3, 'Eve', 'USER_READ_ONLY', '{bcrypt}$2a$10$RMU/Ya20Bq2hcwcNC.cAyOsVj21SpG72kBsUY3WaBHZXuJDr.a.7m');
alter sequence authors_id_seq restart with 5;
alter sequence genres_id_seq restart with 4;
alter sequence books_id_seq restart with 9;
alter sequence comments_id_seq restart with 17;
alter sequence users_id_seq restart with 4;
