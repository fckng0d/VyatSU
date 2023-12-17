CREATE TABLE User_t
(
    username  VARCHAR(30) NOT NULL PRIMARY KEY,
    real_name VARCHAR(30) NOT NULL,
    password  VARCHAR     NOT NULL
);

alter table User_t
    owner to postgres;

INSERT INTO User_t
VALUES ('admin1', 'Верховный админ', 'pass'),
       ('admin2', 'Админ2', 'pass'),
       ('admin3', 'Олег', 'pass'),
       ('user1', 'Даниил', 'pass'),
       ('user2', 'Егор', 'pass'),
       ('user3', 'Алексей', 'pass'),
       ('user4', 'Даниил', 'pass'),
       ('user5', 'Елизавета', 'pass'),
       ('user6', 'Александр', 'pass'),
       ('user7', 'Максим', 'pass'),
       ('user8', 'Роман', 'pass'),
       ('user9', 'Алексей', 'pass'),
       ('user10', 'Алексей', 'pass'),
       ('user11', 'Максим', 'pass'),
       ('user12', 'Даниил', 'pass'),
       ('user13', 'Николай', 'pass');

CREATE TABLE authorities
(
    auth_id   bigserial   not null primary key,
    username  varchar(50) NOT NULL,
    authority varchar(50) NOT NULL,

    CONSTRAINT authorities_idx UNIQUE (username, authority),

    CONSTRAINT authorities_ibfk_1
        FOREIGN KEY (username)
            REFERENCES user_t (username)
);

INSERT INTO authorities
VALUES (default, 'admin1', 'ROLE_ADMIN'),
       (default, 'admin2', 'ROLE_ADMIN'),
       (default, 'admin3', 'ROLE_ADMIN'),
       (default, 'user1', 'ROLE_USER'),
       (default, 'user2', 'ROLE_USER'),
       (default, 'user3', 'ROLE_USER'),
       (default, 'user4', 'ROLE_USER'),
       (default, 'user5', 'ROLE_USER'),
       (default, 'user6', 'ROLE_USER'),
       (default, 'user7', 'ROLE_USER'),
       (default, 'user8', 'ROLE_USER'),
       (default, 'user9', 'ROLE_USER'),
       (default, 'user10', 'ROLE_USER'),
       (default, 'user11', 'ROLE_USER'),
       (default, 'user12', 'ROLE_USER'),
       (default, 'user13', 'ROLE_USER');

create table client
(
    client_id  bigserial primary key,
    last_name  varchar(50) not null,
    first_name varchar(30) not null,
    patronymic varchar(30),
    birth_date varchar(10) not null,
    passport   varchar(10)
        constraint uk_c99kgixns845scir9xks4jxhp
            unique,
    username   varchar(30) /*unique*/ references User_t (username)
);

alter table client
    owner to postgres;

insert into client
values (default, 'Коковихин', 'Даниил', 'Николаевич', '2003-12-31', 1212121212, 'user1'),
       (default, 'Русаков', 'Егор', 'Алексеевич', '2004-09-18', 5555566666, 'user2'),
       (default, 'Макаров', 'Алексей', 'Викторович', '1993-03-12', 5838294932, 'user3'),
       (default, 'Бурков', 'Даниил', 'Алексеевич', '2002-10-05', 4589123553, 'user4'),
       (default, 'Петрова', 'Елизавета', 'Симонова', '2004-03-18', 8181200100, 'user5'),
       (default, 'Летягин', 'Александр', 'Петрович', '2003-11-02', 1111122222, 'user6'),
       (default, 'Прозоров', 'Максим', 'Олегович', '2005-03-13', 1103992831, 'user7'),
       (default, 'Лебедев', 'Роман', 'Николаевич', '2005-04-04', 9999328182, 'user8'),
       (default, 'Иванов', 'Алексей', 'Данилович', '2002-10-05', 8543344592, 'user9'),
       (default, 'Корнеплод', 'Виктор', 'Михайлович', '2003-06-10', 2885438385, 'user10'),
       (default, 'Исупов', 'Максим', 'Олегович', '2004-01-05', 8488828052, 'user11'),
       (default, 'Буровой', 'Даниил', 'Александрович', '2002-12-01', 4821434234, 'user12'),
       (default, 'Петров', 'Николай', 'Михайлович', '2000-05-15', 5554333455, 'user13');

create table loan
(
    loan_id                 bigserial primary key,
    loan_amount             double precision not null,
    interest_rate           double precision not null,
    loan_term               integer          not null,
    date_of_give            date             not null,
    date_of_total_repayment date,
    client_id               bigint           not null
        constraint fk16fy7rykvwh9r0t53pv9qntvk
            references client
            on delete cascade
);

alter table loan
    owner to postgres;


insert into loan
values (default, 20000, 5.6, 12, '2022-10-14', null, 1),
       (default, 1000, 8.6, 3, '2023-09-11', null, 1),
       (default, 140000, 6.6, 12, '2022-01-01', null, 1),
       (default, 1500, 7.6, 3, '2023-04-21', null, 1),
       (default, 43444, 7.2, 12, '2022-01-12', null, 1),
       (default, 7250, 5.6, 6, '2021-08-11', null, 1),
       (default, 56700, 6.4, 24, '2023-03-17', null, 1),
       (default, 43200, 5.6, 6, '2023-01-05', null, 1),
       (default, 5000, 7.6, 6, '2023-01-12', null, 2),
       (default, 17944, 5.8, 12, '2022-03-18', null, 2),
       (default, 10000, 4.8, 3, '2023-02-21', null, 3),
       (default, 32400, 5.8, 12, '2020-10-10', null, 3),
       (default, 54000, 7.8, 24, '2022-02-11', null, 3),
       (default, 13000, 5.4, 12, '2020-12-02', null, 3),
       (default, 12300, 4.9, 6, '2021-09-05', null, 4),
       (default, 17944, 5.8, 12, '2022-04-04', null, 4),
       (default, 2100000, 6.7, 24, '2022-03-14', null, 4);

