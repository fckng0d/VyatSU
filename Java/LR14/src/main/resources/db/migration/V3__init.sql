create table client
(
    client_id  bigserial
        primary key,
    birth_date date,
    first_name varchar(255),
    last_name  varchar(255),
    passport   varchar(255)
        constraint uk_c99kgixns845scir9xks4jxhp
            unique,
    patronymic varchar(255)
);

alter table client
    owner to postgres;

create table loan
(
    loan_id                 bigserial
        primary key,
    date_of_give            date,
    date_of_total_repayment date,
    interest_rate            double precision,
    loan_amount             double precision,
    loan_term               integer,
    client_id         bigint
        constraint fk16fy7rykvwh9r0t53pv9qntvk
            references client
            on delete cascade
);

alter table loan
    owner to postgres;


insert into client
values (default, '2003-12-31', 'Даниил', 'Коковихин', 1212121212, 'Николаевич');

insert into loan
values (default, '2022-10-14', null, 5.6, 20000, 12, 1);