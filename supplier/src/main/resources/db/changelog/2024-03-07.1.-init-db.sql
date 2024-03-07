--liquibase formatted sql

--changeset anikolotov:1
--comment: init db changeset

create table categories
(
    id uuid primary key,
    name varchar not null unique
);

create table products
(
    id uuid primary key,
    name varchar not null,
    description text,
    price bigint not null,
    category_id uuid references categories(id)
);