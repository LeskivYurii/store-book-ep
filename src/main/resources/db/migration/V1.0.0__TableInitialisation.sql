CREATE TABLE IF NOT EXISTS USERS
(
    id         bigint generated always as identity,
    email      varchar unique not null,
    role       varchar        not null,
    password   varchar        not null,
    name       varchar        not null,
    is_active  boolean        not null,
    phone      varchar,
    birth_date date,
    balance    numeric(10, 2),
    created_at timestamp with time zone,
    updated_at timestamp with time zone,

    constraint user_pk PRIMARY KEY (id)
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
    quantity         integer        not null,
    characteristics  varchar        not null,
    description      varchar        not null,
    language         varchar        not null,
    created_at       timestamp with time zone,
    updated_at       timestamp with time zone,

    constraint book_pk PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS ORDERS
(
    id          bigint generated always as identity,
    client_id   bigint         not null,
    employee_id bigint         not null,
    status      varchar        not null,
    price       numeric(10, 2) not null,
    created_at  timestamp with time zone,
    updated_at  timestamp with time zone,

    constraint order_pk PRIMARY KEY (id),
    constraint client_fk FOREIGN KEY (client_id) REFERENCES USERS (id),
    constraint employee_fk FOREIGN KEY (employee_id) REFERENCES USERS (id)
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
);

CREATE TABLE IF NOT EXISTS CART_ITEM
(
    id bigint generated always as identity,
    client_id bigint not null,
    book_id bigint not null,
    quantity int not null,

    constraint cart_item_pk primary key (id),
    constraint user_cart_item_fk FOREIGN KEY (client_id) REFERENCES USERS(id),
    constraint book_cart_item_fk FOREIGN KEY (book_id) REFERENCES BOOKS(id)
)