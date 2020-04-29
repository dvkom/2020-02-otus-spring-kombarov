insert into authors
    (id, `name`, country, birth_date)
values
    (1, 'Isaac Asimov', 'USA', '1920-04-06'),
    (2, 'Jack London', 'USA', '1876-01-12'),
    (3, 'Clifford Simak', 'USA', '1904-08-03'),
    (4, 'Victor Pelevin', 'Russia', '1962-11-22');

insert into genres
    (id, `name`, description)
values
    (1, 'Science fiction', 'A genre of speculative fiction that typically deals with imaginative and futuristic concepts such as advanced science and technology, space exploration, time travel, parallel universes, and extraterrestrial life. It has been called the "literature of ideas", and often explores the potential consequences of scientific, social, and technological innovations.'),
    (2, 'Novel', 'A relatively long work of narrative fiction, normally written in prose form, and which is typically published as a book.'),
    (3, 'Detective fiction', 'A subgenre of crime fiction and mystery fiction in which an investigator or a detective—either professional, amateur or retired—investigates a crime, often murder. The detective genre began around the same time as speculative fiction and other genre fiction in the mid-nineteenth century and has remained extremely popular, particularly in novels');

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
    (8, 'I recommend...', 1);