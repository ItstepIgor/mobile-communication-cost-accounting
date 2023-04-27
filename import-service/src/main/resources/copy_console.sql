select sum(sum) from calls;
--1805.1743, 1655,8032 - февраль
select sum(sum) from call_service where invoice_date = '2023-02-28';
--9618.4997, 9470.2049 - февраль
select count(*) from calls;
--8316
select count(*) from call_service;
--3439
select count(*) from call_service where one_time_call_service=true;
--true 1801  1982
--false 1638   1457
delete from calls;

select number, mobile_operator from call_service where invoice_date = '2023-03-31' and mobile_operator=2;

update call_service set one_time_call_service=false where mobile_operator=2;

select * from call_service where mobile_operator = 2;

delete from calls where mobile_operator = 2;

delete  from call_service where mobile_operator = 2;
select * from call_service where mobile_operator = 2;

select owner_number, call_service_name, sum_with_nds from call_service where number =333066013;
select * from call_service where number =333066013;
select * from call_service where invoice_date = '2023-03-31' and one_time_call_service = false;
select * from call_service where call_service_name ='Безлимитище+';
select * from calls where short_number ='292131362';







