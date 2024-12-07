CREATE TABLE IF NOT EXISTS EMPLOYEES
(
    id         bigint generated always as identity,
    email      varchar unique not null,
    password   varchar        not null,
    name       varchar        not null,
    phone      varchar        not null,
    birth_date date           not null,

    constraint employee_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS CLIENTS
(
    id       bigint generated always as identity,
    email    varchar unique not null,
    password varchar        not null,
    name     varchar        not null,
    balance  numeric(10, 2) not null,

    constraint client_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS BOOKS
(
    id               bigint generated always as identity,
    name             varchar        not null,
    genre            varchar        not null,
    age_group        varchar        not null,
    price            numeric(10, 2) not null,
    publication_year DATE           not null,
    author           varchar        not null,
    number_of_pages  integer        not null,
    characteristics  varchar        not null,
    description      varchar        not null,
    language         varchar        not null,

    constraint book_pk PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS ORDERS
(
    id          bigint generated always as identity,
    client_id   bigint         not null,
    employee_id bigint         not null,
    order_date  TIMESTAMP      not null,
    price       numeric(10, 2) not null,

    constraint order_pk PRIMARY KEY (id),
    constraint client_fk FOREIGN KEY (client_id) REFERENCES CLIENTS (id),
    constraint employee_fk FOREIGN KEY (employee_id) REFERENCES EMPLOYEES (id)
);

CREATE TABLE IF NOT EXISTS "BOOK_ITEMS"
(
    id       bigint generated always as identity,
    order_id bigint  not null,
    book_id  bigint  not null,
    quantity integer not null,

    constraint book_item_pk PRIMARY KEY (id),
    constraint order_fk FOREIGN KEY (order_id) REFERENCES ORDERS (id),
    constraint book_fk FOREIGN KEY (book_id) REFERENCES BOOKS (id)
)