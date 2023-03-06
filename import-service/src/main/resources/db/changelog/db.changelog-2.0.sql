--liquibase formatted sql

--changeset igor:1

CREATE TABLE IF NOT EXISTS call_service
(
    id                    INT8 GENERATED BY DEFAULT AS IDENTITY,
    owner_number          VARCHAR(255)   NOT NULL,
    call_service_name     VARCHAR(255)   NOT NULL,
    call_time             VARCHAR(20)    NOT NULL,
    vat_tax               VARCHAR(40),
    number                INT8           NOT NULL,
    sum                   NUMERIC(19, 4) NOT NULL,
    one_time_call_service BOOLEAN        NOT NULL,
    PRIMARY KEY (id)
);

----changeset igor:2

CREATE TABLE IF NOT EXISTS one_time_call_service
(
    id                         INT8 GENERATED BY DEFAULT AS IDENTITY,
    one_time_call_service_name VARCHAR(255) NOT NULL,
    creation_date              TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);
