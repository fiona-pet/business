-- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --
-- --- Table structure for v_gest_pet_register  会员宠物挂号视图
-- -- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
drop view if exists v_gest_pet_register;
create view v_gest_pet_register as
select g.id gest_id,g.gest_code, g.gest_name,g.mobile_phone,g.gest_style,
  p.id pet_id, p.pet_name, p.sick_file_code,it.is_count, it.is_vip_discount,
  reg.register_no, reg.item_code,reg.item_name, reg.register_price, it.is_sell,
  reg.id relation_id, reg.operator_man, reg.doctor,
  reg.doctor_id, reg.status, reg.paid_status register_paid_status,reg.paid_time,
  reg.pause_reason, reg.create_user_id, reg.create_date, reg.update_date,reg.update_user_id,
  reg.assistant_doctor_id, reg.assistant_doctor_name
from t_gest g
INNER JOIN t_pet p on g.id = p.gest_id
INNER JOIN t_medic_register_record reg on p.id = reg.pet_id
INNER JOIN t_item_type it on reg.item_code = it.item_code

-- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --
-- --- Table structure for v_settle_accounts_view  待支付视图
-- -- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
drop view if exists v_settle_accounts_view;
create view v_settle_accounts_view as
  --   select uuid() id, sds.gest_id, sdsd.item_code, sdsd.item_name, sdsd.sell_price item_cost,
  --     sdsd.item_num, sdsd.busi_type_id, '直接销售' business_type, sds.id relation_id,
  --     it.is_vip_discount, sdsd.sell_unit item_unit, sdsd.id relation_detail_id
  --   from  t_store_direct_sell sds
  --     INNER JOIN t_store_direct_sell_detail sdsd on sds.id = sdsd.direct_sell_id
  --     LEFT JOIN t_gest g on sds.gest_id=g.id
  --     LEFT JOIN t_item_type it on sdsd.item_code = it.item_code
  --   where sdsd.paid_status <> 'SM00051' or sdsd.paid_status is null
  --
  --   UNION ALL

  select uuid() id,gpr.gest_id, gpr.item_code,gpr.item_name,gpr.register_price item_cost,
         1 item_num, 10 busi_type_id,'挂号费用' business_type, gpr.relation_id, gpr.is_vip_discount, '次' item_unit,
         gpr.relation_id relation_detail_id
  from v_gest_pet_register gpr
    LEFT JOIN t_item_type it on gpr.item_code=it.item_code
  where gpr.register_paid_status is null or gpr.register_paid_status <> 'SM00051'

  UNION ALL

  select uuid() id,p.gest_id, mpd.item_code,mpd.item_name,mpd.item_cost,mpd.item_num,
    it.busi_type_id, '处置处方' business_type, mp.id relation_id,
    it.is_vip_discount, mpd.recipe_unit item_unit,mpd.id relation_detail_id
  from t_medic_prescription_detail mpd
    INNER JOIN t_medic_prescription mp on mp.id = mpd.prescription_id
    INNER JOIN t_medic_medictreat_record mmt on mp.medic_record_code = mmt.medi_treatment_code
    LEFT JOIN t_pet p on mmt.pet_id = p.id
    LEFT JOIN t_gest g on p.gest_id = g.id
    LEFT JOIN t_item_type it on mpd.item_code = it.item_code
  WHERE mpd.paid_status is null or mpd.paid_status <> 'SM00051'

  UNION ALL

  select uuid() id,p.gest_id, mpd.item_code,mpd.item_name,mpd.item_cost,mpd.item_num,
    it.busi_type_id, '住院处置处方' business_type, mp.id relation_id,
    it.is_vip_discount, udd.value_name_cn item_unit,mpd.id relation_detail_id
  from t_in_hospital_prescription_detail mpd
    INNER JOIN t_in_hospital_prescription mp on mp.id = mpd.prescription_id
    INNER JOIN t_in_hospital_record mmt on mp.in_hospital_no = mmt.in_hospital_no
    LEFT JOIN t_user_dict_detail udd on mpd.recipe_unit = udd.dict_detail_code
    LEFT JOIN t_pet p on mmt.pet_id = p.id
    LEFT JOIN t_gest g on p.gest_id = g.id
    LEFT JOIN t_item_type it on mpd.item_code = it.item_code
  WHERE mpd.paid_status is null or mpd.paid_status <> 'SM00051'
;

-- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --
-- --- Table structure for v_gest_bill  会员待支付
-- -- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
drop view if exists v_gest_bill;
create view v_gest_bill as
select uuid() id, gs.gest_id, g.gest_code gest_no, gest_name,g.mobile_phone phone, gs.total from t_gest g
INNER JOIN (select gest_id, sum(item_cost * item_num) total from v_settle_accounts_view
  group by gest_id) gs
  on g.id = gs.gest_id
;

-- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --
-- --- Table structure for v_report_by_person  医生业绩报表
-- -- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
drop view if exists v_report_by_person;
create view v_report_by_person as
SELECT  uuid() id,sum(vrbiip.discount_money*mpd.item_num) total, p.person_name name, "门诊处方" type, substr(mpd.create_date, 1,10) create_date
FROM t_medic_prescription_detail mpd
  JOIN t_persons p ON mpd.create_user_id = p.id
  JOIN v_report_by_item_infact_price vrbiip on mpd.id = vrbiip.relation_detail_id
GROUP BY mpd.create_user_id,substr(mpd.create_date, 1,10)
UNION ALL
SELECT  uuid() id,sum(sdsd.sell_price*sdsd.item_num) total, p.person_name name, case when locate("CEX" ,item_code)>0 then "美容销售" when locate("BC" ,item_code)>0 then "商品销售" end type, substr(sdsd.create_date, 1,10) create_date
FROM t_store_direct_sell_detail sdsd
  JOIN t_persons p ON sdsd.create_user_id = p.id
GROUP BY sdsd.create_user_id,create_date,type

-- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --
-- --- Table structure for v_report_by_item  商品统计报表
-- -- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
drop view if exists v_report_by_item;
create view v_report_by_item as
select uuid() id,mpd.item_code,mpd.item_name,sum(item_num) total_num,avg(item_cost) avg_price,sum(item_num) * avg(item_cost) total,avg(ic.input_price/ic.item_bulk) avg_input_price,avg(ic.input_price/ic.item_bulk)*sum(item_num) total_cost,avg(ic.item_count_num*ic.item_bulk+ic.scattered_count_num) inventory ,  "门诊处方" type,substr(mpd.create_date, 1,7) create_date  from
  t_medic_prescription_detail mpd
  LEFT JOIN t_item_count ic ON ic.item_code = mpd.item_code
group by item_code,item_name,substr(mpd.create_date, 1,7)

-- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --
-- --- Table structure for v_report_by_item_infact_price  商品销售的实际价格
-- -- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
drop view if exists v_report_by_item_infact_price;
create view v_report_by_item_infact_price as
select (1-fsa.dis_count_money/fsa.total_money)*fsad.total_cost discount_money,fsad.relation_detail_id
from t_finance_settle_accounts fsa
  JOIN t_finance_settle_accounts_detail fsad on fsa.id=fsad.settle_accounts_detail_id



