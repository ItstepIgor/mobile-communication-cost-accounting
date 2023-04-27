delete
from phone_number;

ALTER SEQUENCE phone_number_id_seq RESTART WITH 1;

delete
from calls
where mobile_operator = '2';

select *
from phone_number
where mobile_operator_id = 2;

select ir.owner_name, pn.number, ir.sum, mb.operator_name
from result ir
         left join phone_number pn on pn.id = ir.number_id
         left join mobile_operator mb on mb.id = pn.mobile_operator_id
where pn.mobile_operator_id = 1;

select * from monthly_call_service;

select ms.monthly_call_service_name, ms.vat_tax, ms.sum, ms.sum_with_nds
from monthly_call_service ms
         left join phone_number pn on pn.id = ms.number_id
where pn.number = 333469936;

select sum(sum) from calls where short_number = '333469936' and invoice_date = '2023-03-31';

delete from result;
delete from one_time_call_service;
delete from monthly_call_service;
delete from phone_number;