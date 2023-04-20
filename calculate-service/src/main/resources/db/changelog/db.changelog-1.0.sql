--liquibase formatted sql

--changeset igor:1

CREATE TABLE IF NOT EXISTS owner
(
    id          INT8 GENERATED BY DEFAULT AS IDENTITY,
    owner_name  VARCHAR(255) NOT NULL,
    number_id   INT8         NOT NULL,
    position_id INT8         NOT NULL,
    PRIMARY KEY (id)
);

--changeset igor:2

CREATE TABLE IF NOT EXISTS position
(
    id            INT8 GENERATED BY DEFAULT AS IDENTITY,
    position_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

--changeset igor:3

CREATE TABLE IF NOT EXISTS one_time_call_service
(
    id                         INT8 GENERATED BY DEFAULT AS IDENTITY,
    one_time_call_service_name VARCHAR(255) NOT NULL,
    creation_date              TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);

--changeset igor:4

CREATE TABLE IF NOT EXISTS rule_one_time_call_service
(
    id                       INT8 GENERATED BY DEFAULT AS IDENTITY,
    rule_name                VARCHAR(255) NOT NULL,
    one_time_call_service_id INT8         NOT NULL,
    start_payment            TIME         NOT NULL,
    end_payment              TIME         NOT NULL,
    PRIMARY KEY (id)
);

--changeset igor:5

CREATE TABLE IF NOT EXISTS number_rule_one_time_call_service
(
    rule_id   INT8 NOT NULL,
    number_id INT8 NOT NULL,
    PRIMARY KEY (rule_id, number_id)
);

--changeset igor:6

CREATE TABLE IF NOT EXISTS phone_number
(
    id                 INT8 GENERATED BY DEFAULT AS IDENTITY,
    number             INT8      NOT NULL,
    group_number_id    INT8      NOT NULL,
    mobile_operator_id INT8      NOT NULL,
    creation_date      TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);

--changeset igor:7

CREATE TABLE IF NOT EXISTS group_number
(
    id                INT8 GENERATED BY DEFAULT AS IDENTITY,
    group_number_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

--changeset igor:8

CREATE TABLE IF NOT EXISTS monthly_call_service
(
    id                        INT8 GENERATED BY DEFAULT AS IDENTITY,
    monthly_call_service_name VARCHAR(255) NOT NULL,
    creation_date             TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
);

--changeset igor:9

CREATE TABLE IF NOT EXISTS monthly_call_service_cost
(
    id                      INT8 GENERATED BY DEFAULT AS IDENTITY,
    monthly_call_service_id INT8           NOT NULL,
    date_sum_start          DATE           NOT NULL,
    sum                     NUMERIC(19, 4) NOT NULL,
    PRIMARY KEY (id)
);

--changeset igor:10

CREATE TABLE IF NOT EXISTS number_monthly_call_service
(
    monthly_call_service_id INT8 NOT NULL,
    number_id               INT8 NOT NULL,
    PRIMARY KEY (number_id, monthly_call_service_id)
);

--changeset igor:11

CREATE TABLE IF NOT EXISTS result
(
    id         INT8 GENERATED BY DEFAULT AS IDENTITY,
    owner_name VARCHAR(255)   NOT NULL,
    number_id  INT8           NOT NULL,
    sum        NUMERIC(19, 2) NOT NULL,
    PRIMARY KEY (id)
);

--changeset igor:12

CREATE TABLE IF NOT EXISTS individual_result
(
    id         INT8 GENERATED BY DEFAULT AS IDENTITY,
    owner_name VARCHAR(255)   NOT NULL,
    number_id  INT8           NOT NULL,
    call_date  TIMESTAMP      NOT NULL,
    sum        NUMERIC(19, 2) NOT NULL,
    PRIMARY KEY (id)
);

--changeset igor:13

CREATE TABLE IF NOT EXISTS calls
(
    id              INT8 GENERATED BY DEFAULT AS IDENTITY,
    owner_number    VARCHAR(255)   NOT NULL,
    call_service    VARCHAR(255)   NOT NULL,
    call_date_time  TIMESTAMP      NOT NULL,
    code            VARCHAR(40),
    call_time       VARCHAR(20)    NOT NULL,
    number          VARCHAR(32)    NOT NULL,
    sum             NUMERIC(19, 4) NOT NULL,
    short_number    VARCHAR(40)    NOT NULL,
    day_of_week     INT4           NOT NULL,
    mobile_operator VARCHAR(10)    NOT NULL,
    PRIMARY KEY (id)
);

--changeset igor:14

CREATE TABLE IF NOT EXISTS mobile_operator
(
    id            INT8 GENERATED BY DEFAULT AS IDENTITY,
    operator_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
