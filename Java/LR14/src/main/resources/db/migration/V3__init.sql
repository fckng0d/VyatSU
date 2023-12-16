CREATE TABLE User_t
(
    username VARCHAR(30) NOT NULL PRIMARY KEY,
    password VARCHAR NOT NULL
);

alter table User_t
    owner to postgres;

INSERT INTO User_t
VALUES ('admin', '{noop}pass'),
       ('user', '{noop}pass');

CREATE TABLE authorities
(
    auth_id bigserial not null primary key,
    username  varchar(50) NOT NULL,
    authority varchar(50) NOT NULL,

    CONSTRAINT authorities_idx UNIQUE (username, authority),

    CONSTRAINT authorities_ibfk_1
        FOREIGN KEY (username)
            REFERENCES user_t (username)
);

INSERT INTO authorities
VALUES (default, 'admin', 'ROLE_ADMIN'),
       (default, 'user', 'ROLE_USER');

create table client
(
    client_id  bigserial primary key,
    last_name  varchar(50) not null,
    first_name varchar(30) not null,
    patronymic varchar(30),
    birth_date varchar(10)         not null,
    passport   varchar(10)
        constraint uk_c99kgixns845scir9xks4jxhp
            unique,
    username varchar(30) /*unique*/ references User_t(username)
);

alter table client
    owner to postgres;

insert into client
values (default, 'Даниил', 'Коковихин', 'Николаевич', '2003-12-31', 1212121212, 'user');

create table loan
(
    loan_id                 bigserial primary key,
    loan_amount             double precision not null,
    interest_rate           double precision not null,
    loan_term               integer not null,
    date_of_give            date not null,
    date_of_total_repayment date,
    client_id               bigint not null
        constraint fk16fy7rykvwh9r0t53pv9qntvk
            references client
            on delete cascade
);

alter table loan
    owner to postgres;


insert into loan
values (default, 20000, 5.6, 12,'2022-10-14', null,  1),
       (default, 1000, 8.6, 3,'2023-09-11', null,  1),
       (default, 140000, 6.6, 12,'2021-05-24', null,  1);

