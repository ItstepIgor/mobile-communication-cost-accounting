--liquibase formatted sql

--changeset igor:1

INSERT INTO mobile_operator (id, operator_name)
VALUES (1, 'A1'),
       (2, 'MTS');