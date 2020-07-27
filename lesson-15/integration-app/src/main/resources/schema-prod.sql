create table books (
    id bigint generated by default as identity,
    title varchar(255) not null,
    page_count integer,
    author varchar(255),
    genre varchar(255),
    primary key (id),
    constraint unique_book unique (title, author)
);