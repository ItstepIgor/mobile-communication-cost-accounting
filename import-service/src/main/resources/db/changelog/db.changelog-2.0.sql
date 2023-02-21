--liquibase formatted sql

--changeset igor:1

CREATE TABLE IF NOT EXISTS call_service
(
    id                INT8 GENERATED BY DEFAULT AS IDENTITY,
    owner_number      VARCHAR(255)   NOT NULL,
    call_service_name VARCHAR(255)   NOT NULL,
    call_time         VARCHAR(20)   NOT NULL,
    vat_tax           VARCHAR(40),
    number            INT8           NOT NULL,
    sum               NUMERIC(19, 4) NOT NULL,
    is_monthly        BOOLEAN        NOT NULL,
    PRIMARY KEY (id)
);