--liquibase formatted sql

--changeset igor:1

CREATE OR REPLACE FUNCTION date_now()
       RETURNS trigger
       LANGUAGE plpgsql AS
$func$
BEGIN
       NEW.creation_date := now();
       RETURN NEW;
END
$func$;

--changeset igor:2

CREATE TRIGGER one_time_call_service_table_before_insert
       BEFORE INSERT ON one_time_call_service
       FOR EACH ROW
       WHEN (NEW.creation_date IS NULL)
EXECUTE FUNCTION date_now();

--changeset igor:3

CREATE TRIGGER phone_number_table_before_insert
       BEFORE INSERT ON phone_number
       FOR EACH ROW
       WHEN (NEW.creation_date IS NULL)
EXECUTE FUNCTION date_now();

--changeset igor:4

CREATE TRIGGER monthly_call_service_table_before_insert
       BEFORE INSERT ON monthly_call_service
       FOR EACH ROW
       WHEN (NEW.creation_date IS NULL)
EXECUTE FUNCTION date_now();

--changeset igor:5

CREATE TRIGGER result_table_before_insert
       BEFORE INSERT ON result
       FOR EACH ROW
       WHEN (NEW.creation_date IS NULL)
EXECUTE FUNCTION date_now();

--changeset igor:6

CREATE TRIGGER individual_result_table_before_insert
       BEFORE INSERT ON individual_result
       FOR EACH ROW
       WHEN (NEW.creation_date IS NULL)
EXECUTE FUNCTION date_now();

--changeset igor:7

CREATE TRIGGER monthly_call_service_list_table_before_insert
       BEFORE INSERT ON monthly_call_service_list
       FOR EACH ROW
       WHEN (NEW.creation_date IS NULL)
EXECUTE FUNCTION date_now();