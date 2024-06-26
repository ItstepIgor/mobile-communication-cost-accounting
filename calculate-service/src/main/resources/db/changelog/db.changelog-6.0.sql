--liquibase formatted sql

--changeset igor:1

UPDATE phone_number set group_number_id = 1 WHERE id = 63;
UPDATE phone_number set group_number_id = 1 WHERE id = 349;
UPDATE phone_number set group_number_id = 6 WHERE id = 1514;
UPDATE phone_number set group_number_id = 3 WHERE id = 237;
UPDATE phone_number set group_number_id = 3 WHERE id = 1094;
UPDATE phone_number set group_number_id = 3 WHERE id = 1515;
UPDATE phone_number set group_number_id = 3 WHERE id = 1516;
UPDATE phone_number set group_number_id = 3 WHERE id = 1517;
UPDATE phone_number set group_number_id = 3 WHERE id = 1518;
UPDATE phone_number set group_number_id = 3 WHERE id = 1519;
UPDATE phone_number set group_number_id = 3 WHERE id = 1520;
UPDATE phone_number set group_number_id = 3 WHERE id = 1521;
UPDATE phone_number set group_number_id = 3 WHERE id = 1522;
UPDATE phone_number set group_number_id = 3 WHERE id = 1523;
UPDATE phone_number set group_number_id = 3 WHERE id = 1524;
UPDATE phone_number set group_number_id = 3 WHERE id = 1525;
UPDATE phone_number set group_number_id = 3 WHERE id = 1526;
UPDATE phone_number set group_number_id = 3 WHERE id = 1527;
UPDATE phone_number set group_number_id = 3 WHERE id = 1528;
UPDATE phone_number set group_number_id = 3 WHERE id = 1529;
UPDATE phone_number set group_number_id = 4 WHERE id = 1291;
UPDATE phone_number set group_number_id = 6 WHERE id = 1373;

--changeset igor:2

DELETE FROM phone_number WHERE id = 334;
DELETE FROM phone_number WHERE id = 274;
DELETE FROM phone_number WHERE id = 285;
DELETE FROM phone_number WHERE id = 291;
DELETE FROM phone_number WHERE id = 498;
DELETE FROM phone_number_monthly_call_service_list WHERE number_id = 1373 AND monthly_call_service_list_id = 36;
DELETE FROM phone_number_monthly_call_service_list WHERE number_id = 1094 AND monthly_call_service_list_id = 49;
DELETE FROM phone_number_monthly_call_service_list WHERE number_id = 237 AND monthly_call_service_list_id = 49;
DELETE FROM phone_number_monthly_call_service_list WHERE number_id = 238 AND monthly_call_service_list_id = 42;
DELETE FROM phone_number_monthly_call_service_list WHERE number_id = 303 AND monthly_call_service_list_id = 42;
DELETE FROM phone_number_monthly_call_service_list WHERE number_id = 303 AND monthly_call_service_list_id = 8;
DELETE FROM phone_number_monthly_call_service_list WHERE number_id = 1468 AND monthly_call_service_list_id = 43;
DELETE FROM phone_number_monthly_call_service_list WHERE number_id = 1468 AND monthly_call_service_list_id = 8;

--changeset igor:3

INSERT INTO phone_number_monthly_call_service_list (number_id, monthly_call_service_list_id)
VALUES (383, 11),
       (63, 7),
       (34, 11),
       (238, 15),
       (238, 53),
       (303, 205),
       (303, 210),
       (349, 8),
       (349, 42),
       (1468, 15),
       (1468, 53);