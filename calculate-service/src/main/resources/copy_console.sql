ALTER SEQUENCE phone_number_id_seq RESTART WITH 1;

select ir.owner_name, pn.number, ir.sum, mb.operator_name
from result ir
         left join phone_number pn on pn.id = ir.number_id
         left join mobile_operator mb on mb.id = pn.mobile_operator_id
where pn.mobile_operator_id = 1;

select ms.monthly_call_service_name, ms.vat_tax, ms.sum, ms.sum_with_nds
from monthly_call_service ms
         left join phone_number pn on pn.id = ms.number_id
where pn.number = 333469936;

select sum(sum)
from calls
where short_number = '293437650'
  and invoice_date = '2023-03-31';

delete
from individual_result;
delete
from result;
delete
from one_time_call_service;
delete
from monthly_call_service;
delete
from phone_number;

SELECT nextVal('group_number_id_seq');

select setval('one_time_call_service_id_seq', (select max(id) from one_time_call_service));

select ot.one_time_call_service_name
from rule_one_time_call_service r
         LEFT JOIN group_rule_one_time_call_service gr on r.id = gr.rule_id
         left outer join group_number gn on gn.id = gr.group_id
         LEFT JOIN phone_number pn on gn.id = pn.group_number_id
         LEFT JOIN one_time_call_service ot on r.one_time_call_service_id = ot.id
where pn.number = 1300943;


SELECT ot.one_time_call_service_name AS oneTimeCallServiceName,
       pn.number                     AS number,
       r.start_payment               AS startPayment,
       r.end_payment                 AS endPayment
FROM rule_one_time_call_service r
         LEFT JOIN group_rule_one_time_call_service gr ON r.id = gr.rule_id
         LEFT JOIN group_number gn ON gn.id = gr.group_id
         LEFT JOIN phone_number pn ON gn.id = pn.group_number_id
         LEFT JOIN one_time_call_service ot ON r.one_time_call_service_id = ot.id
WHERE pn.number = 291300943;

select *
from calls
where mobile_operator = '1'
  and invoice_date = '2023-03-31'
  and short_number = '447865866';

select *
from individual_result
where call_type = 'Платно';

select ms.monthly_call_service_name,
       pn.number,
       gn.group_number_name,
       mcsl.id,
       pn.id,
       pn.mobile_operator_id
from monthly_call_service ms
         left join phone_number pn on pn.id = ms.number_id
         left join group_number gn on gn.id = pn.group_number_id
         left join monthly_call_service_list mcsl on ms.monthly_call_service_name = mcsl.monthly_call_service_name
where gn.id in (1, 2, 4, 5)
  and pn.mobile_operator_id = 1
order by mcsl.id;

select mcsl.monthly_call_service_name, mcsl.id, pnmcsl.number_id
from monthly_call_service_list mcsl
         left join phone_number_monthly_call_service_list pnmcsl on mcsl.id = pnmcsl.monthly_call_service_list_id
         left join phone_number pn on pn.id = pnmcsl.number_id;

select r.owner_name, r.sum, group_number_name, pn.mobile_operator_id
from result r
         left join phone_number pn on r.number_id = pn.id
         left join group_number gn on pn.group_number_id = gn.id
where r.sum > 0 and group_number_id in (1, 2, 4, 5, 6, 3) and pn.mobile_operator_id = 1;