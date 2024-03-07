--liquibase formatted sql

--changeset anikolotov:1 context:test
--comment: add test data

insert into categories (id, name) values ('4c928a17-6e80-49b3-8d07-e568ca8d5a56', 'test-cat-1');
insert into categories (id, name) values ('a3b9df9e-24eb-4db0-b95a-4e1c14687bba', 'test-cat-2');
insert into categories (id, name) values ('7fe7c871-6d8c-4183-8d00-4fa1838e3b4c', 'test-cat-3');

insert into products (id, name, description, price, category_id)
    values (gen_random_uuid(), 'test-prod-1', 'abacaba', 1000, '4c928a17-6e80-49b3-8d07-e568ca8d5a56');
insert into products (id, name, description, price, category_id)
    values (gen_random_uuid(), 'test-prod-2', 'test', 10000, '4c928a17-6e80-49b3-8d07-e568ca8d5a56');
insert into products (id, name, description, price, category_id)
    values (gen_random_uuid(), 'test-prod-3', 'some desc', 5000, '4c928a17-6e80-49b3-8d07-e568ca8d5a56');


insert into products (id, name, description, price, category_id)
    values (gen_random_uuid(), 'test-prod-1', 'abacaba', 3000, 'a3b9df9e-24eb-4db0-b95a-4e1c14687bba');
insert into products (id, name, description, price, category_id)
    values (gen_random_uuid(), 'test-prod-2', 'test', 2000, 'a3b9df9e-24eb-4db0-b95a-4e1c14687bba');
insert into products (id, name, description, price, category_id)
    values (gen_random_uuid(), 'test-prod-3', 'some desc', 1500, 'a3b9df9e-24eb-4db0-b95a-4e1c14687bba');



insert into products (id, name, description, price, category_id)
    values (gen_random_uuid(), 'test-prod-1', 'test', 500, '7fe7c871-6d8c-4183-8d00-4fa1838e3b4c');
