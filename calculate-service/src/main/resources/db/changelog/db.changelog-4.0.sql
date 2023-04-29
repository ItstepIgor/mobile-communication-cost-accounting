--liquibase formatted sql

--changeset igor:1

INSERT INTO mobile_operator (id, operator_name)
VALUES (1, 'A1'),
       (2, 'MTS');


--changeset igor:2

INSERT INTO  group_number (id, group_number_name)
VALUES (1, 'Руководители'),
       (2, 'Специалисты'),
       (3, 'Дежурные службы'),
       (4, 'Водители легковых автомобилей'),
       (5, 'Водители грузовых автомобилей'),
       (6, 'Удерживаем все'),
       (7, 'Общая группа');
