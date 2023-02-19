--liquibase formatted sql

--changeset igor:1

CREATE TABLE calls
(
    id             INT8 GENERATED BY DEFAULT AS IDENTITY,
    owner_number   VARCHAR(255)   NOT NULL,
    call_type      VARCHAR(255)   NOT NULL,
    call_date_time TIMESTAMP      NOT NULL,
    code           VARCHAR(255),
    call_time      TIME           NOT NULL,
    number         VARCHAR(32)    NOT NULL,
    sum            NUMERIC(19, 2) NOT NULL,
    short_number   VARCHAR(32)    NOT NULL,
    day_of_week    INT4           NOT NULL,
    PRIMARY KEY (id)
);
