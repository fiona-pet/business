delete from t_dealer;
delete from t_member_level;

-- 清理结算单记录
delete from t_gest_paid_record where settle_accounts_id in (select id from t_finance_settle_accounts where settle_code='JS2017042102807');
delete from t_finance_settle_accounts_detail where settle_accounts_detail_id in (select id from t_finance_settle_accounts where settle_code='JS2017042102807');
delete from t_finance_settle_accounts where settle_code='JS2017042102807';

-- 清除结算数据
update t_medic_prescription_detail mpd set mpd.paid_status='SM00051', mpd.paid_time=now() where mpd.paid_status is null and prescription_id in (select id from t_medic_prescription where gest_name = 'sunny') and mpd.id in (select relation_detail_id
                                                                          FROM t_finance_settle_accounts_detail
                                                                            JOIN t_finance_settle_accounts fsa ON settle_accounts_detail_id=fsa.id
                                                                          WHERE fsa.settle_code= 'JS2017051403635')
update t_medic_prescription set paid_status='SM00051' where gest_name = 'sunny';

-- 清除结算数据
update t_medic_prescription_detail set paid_status='SM00051'  where prescription_id in (select id from t_medic_prescription where gest_name = '刑志红');
update t_medic_register_record set paid_status='SM00051' where gest_name = '刑志红';

-- 查询处方明细
SELECT
	mpd.id, mpd.prescription_id,mpd.item_name,`vrbiip`.`discount_money`
FROM
	(
		(
			`t_medic_prescription_detail` `mpd`
			JOIN `t_persons` `p` ON (
				(
					`mpd`.`create_user_id` = `p`.`id`
				)
			)
		)
		JOIN `v_report_by_item_infact_price` `vrbiip` ON (
			(
				`mpd`.`id` = `vrbiip`.`relation_detail_id`
			)
		)
	)
WHERE
	(
		`mpd`.`paid_status` = 'SM00051' and
mpd.create_user_id='402881ce5b85710c015b99b997d40004'
	)
ORDER BY mpd.prescription_id,mpd.group_name

-- 更新 关联关系
update t_finance_settle_accounts_detail fsad set fsad.relation_detail_id=(
  select DISTINCT sdsd.id from t_store_direct_sell_detail sdsd
    JOIN t_store_direct_sell sds on sdsd.direct_sell_id=sds.id
    join t_finance_settle_accounts fsa on sds.create_user_id=fsa.create_user_id and sds.gest_id=fsa.gest_id and substr(sds.create_date, 1,16)=substr(fsa.create_date, 1,16)
    where  sds.total_cost != fsa.should_paid_money
          and fsa.create_date>="2017-04-01 00:00:00" and fsa.create_date<= "2017-04-01 23:59:59"
        and sdsd.item_code=fsad.item_code
)
where fsad.relation_detail_id is null and fsad.relation_id is null

-- 更新统计
update t_finance_settle_accounts_detail fsad set total_cost = (select (1-(fsa.dis_count_money/fsa.total_money))*(total_num*fsad.total_cost) from t_finance_settle_accounts fsa where fsad.settle_accounts_detail_id=fsa.id and total_money!=0)
where fsad.create_date <= "2017-04-23 23:59:59"