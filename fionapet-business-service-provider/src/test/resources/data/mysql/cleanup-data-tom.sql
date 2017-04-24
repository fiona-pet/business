delete from t_dealer;
delete from t_member_level;

-- 清理结算单记录
delete from t_gest_paid_record where settle_accounts_id in (select id from t_finance_settle_accounts where settle_code='JS2017042102807');
delete from t_finance_settle_accounts_detail where settle_accounts_detail_id in (select id from t_finance_settle_accounts where settle_code='JS2017042102807');
delete from t_finance_settle_accounts where settle_code='JS2017042102807';

-- 清除结算数据
update t_medic_prescription_detail set paid_status='SM00051'  where prescription_id in (select id from t_medic_prescription where gest_name = '刑志红');
update t_medic_register_record set paid_status='SM00051' where gest_name = '刑志红';