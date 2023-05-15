--liquibase formatted sql

--changeset igor:1

ALTER TABLE IF EXISTS owner
    ADD CONSTRAINT FK_owner_number FOREIGN KEY (number_id) REFERENCES phone_number;

ALTER TABLE IF EXISTS owner
    ADD CONSTRAINT FK_owner_position FOREIGN KEY (position_id) REFERENCES position;

ALTER TABLE IF EXISTS phone_number
    ADD CONSTRAINT FK_phone_number_owner_group FOREIGN KEY (group_number_id) REFERENCES group_number;

ALTER TABLE IF EXISTS rule_one_time_call_service
    ADD CONSTRAINT FK_rule_one_time_call_service_one_time_call_service FOREIGN KEY (one_time_call_service_id)
        REFERENCES one_time_call_service;

ALTER TABLE IF EXISTS group_rule_one_time_call_service
    ADD CONSTRAINT FK_group_rule_one_time_call_service_group FOREIGN KEY (group_id) REFERENCES group_number;

ALTER TABLE IF EXISTS group_rule_one_time_call_service
    ADD CONSTRAINT FK_group_rule_one_time_call_service_rule_one_time_call_service FOREIGN KEY (rule_id)
        REFERENCES rule_one_time_call_service;

ALTER TABLE IF EXISTS result
    ADD CONSTRAINT FK_result_number FOREIGN KEY (number_id) REFERENCES phone_number;

ALTER TABLE IF EXISTS monthly_call_service
    ADD CONSTRAINT FK_monthly_call_service_number FOREIGN KEY (number_id) REFERENCES phone_number;

ALTER TABLE IF EXISTS individual_result
    ADD CONSTRAINT FK_individual_result_number FOREIGN KEY (number_id) REFERENCES phone_number;

ALTER TABLE IF EXISTS phone_number
    ADD CONSTRAINT FK_phone_number_mobile_operator FOREIGN KEY (mobile_operator_id) REFERENCES mobile_operator;

ALTER TABLE IF EXISTS phone_number_monthly_call_service_list
    ADD CONSTRAINT FK_phone_number_monthly_call_service_list_number FOREIGN KEY (number_id) REFERENCES phone_number;

ALTER TABLE IF EXISTS phone_number_monthly_call_service_list
    ADD CONSTRAINT FK_number_monthly_call_service_list_monthly_call_service_list FOREIGN KEY (monthly_call_service_list_id)
        REFERENCES monthly_call_service_list;

ALTER TABLE IF EXISTS transfer_work_day
    ADD CONSTRAINT FK_transfer_work_day_type_transfer_work_day
        FOREIGN KEY (type_transfer_work_day_id) REFERENCES type_transfer_work_day;