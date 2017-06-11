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
  SELECT
	uuid() AS `id`,
	`gpr`.`gest_id` AS `gest_id`,
	`gpr`.`item_code` AS `item_code`,
	`gpr`.`item_name` AS `item_name`,
	`gpr`.`register_price` AS `item_cost`,
	1 AS `item_num`,
	10 AS `busi_type_id`,
	'挂号费用' AS `business_type`,
	`gpr`.`relation_id` AS `relation_id`,
	`gpr`.`is_vip_discount` AS `is_vip_discount`,
	'次' AS `item_unit`,
	`gpr`.`relation_id` AS `relation_detail_id`,
	`itc`.`cate_name` AS `cate_name`
FROM
	(
		(
			`v_gest_pet_register` `gpr`
			LEFT JOIN `t_item_type` `it` ON (
				(
					`gpr`.`item_code` = `it`.`item_code`
				)
			)
		)
		LEFT JOIN `t_item_cate` `itc` ON (
			(
				`it`.`cate_no` = `itc`.`cate_no`
			)
		)
	)
WHERE
	(
		isnull(
			`gpr`.`register_paid_status`
		)
		OR (
			`gpr`.`register_paid_status` <> 'SM00051'
		)
	)
UNION ALL
	SELECT
		uuid() AS `id`,
		`p`.`gest_id` AS `gest_id`,
		`mpd`.`item_code` AS `item_code`,
		`mpd`.`item_name` AS `item_name`,
		`mpd`.`item_cost` AS `item_cost`,
		`mpd`.`item_num` AS `item_num`,
		`it`.`busi_type_id` AS `busi_type_id`,
		'处置处方' AS `business_type`,
		`mp`.`id` AS `relation_id`,
		`it`.`is_vip_discount` AS `is_vip_discount`,
		`mpd`.`recipe_unit` AS `item_unit`,
		`mpd`.`id` AS `relation_detail_id`,
		`itc`.`cate_name` AS `cate_name`
	FROM
		(
			(
				(
					(
						(
							(
								`t_medic_prescription_detail` `mpd`
								JOIN `t_medic_prescription` `mp` ON (
									(
										`mp`.`id` = `mpd`.`prescription_id`
									)
								)
							)
							JOIN `t_medic_medictreat_record` `mmt` ON (
								(
									`mp`.`medic_record_code` = `mmt`.`medi_treatment_code`
								)
							)
						)
						JOIN `t_pet` `p` ON ((`mmt`.`pet_id` = `p`.`id`))
					)
					JOIN `t_gest` `g` ON ((`p`.`gest_id` = `g`.`id`))
				)
				LEFT JOIN `t_item_type` `it` ON (
					(
						`mpd`.`item_code` = `it`.`item_code`
					)
				)
			)
			LEFT JOIN `t_item_cate` `itc` ON (
				(
					`it`.`cate_no` = `itc`.`cate_no`
				)
			)
		)
	WHERE
		(
			isnull(`mpd`.`paid_status`)
			OR (
				`mpd`.`paid_status` <> 'SM00051'
			)
		)
	UNION ALL
		SELECT
			uuid() AS `id`,
			`p`.`gest_id` AS `gest_id`,
			`mpd`.`item_code` AS `item_code`,
			`mpd`.`item_name` AS `item_name`,
			`mpd`.`item_cost` AS `item_cost`,
			`mpd`.`item_num` AS `item_num`,
			`it`.`busi_type_id` AS `busi_type_id`,
			'住院处置处方' AS `business_type`,
			`mp`.`id` AS `relation_id`,
			`it`.`is_vip_discount` AS `is_vip_discount`,
			`udd`.`value_name_cn` AS `item_unit`,
			`mpd`.`id` AS `relation_detail_id`,
			`itc`.`cate_name` AS `cate_name`
		FROM
			(
				(
					(
						(
							(
								(
									(
										`t_in_hospital_prescription_detail` `mpd`
										JOIN `t_in_hospital_prescription` `mp` ON (
											(
												`mp`.`id` = `mpd`.`prescription_id`
											)
										)
									)
									JOIN `t_in_hospital_record` `mmt` ON (
										(
											`mp`.`in_hospital_no` = `mmt`.`in_hospital_no`
										)
									)
								)
								LEFT JOIN `t_user_dict_detail` `udd` ON (
									(
										`mpd`.`recipe_unit` = `udd`.`dict_detail_code`
									)
								)
							)
							JOIN `t_pet` `p` ON ((`mmt`.`pet_id` = `p`.`id`))
						)
						JOIN `t_gest` `g` ON ((`p`.`gest_id` = `g`.`id`))
					)
					LEFT JOIN `t_item_type` `it` ON (
						(
							`mpd`.`item_code` = `it`.`item_code`
						)
					)
				)
				LEFT JOIN `t_item_cate` `itc` ON (
					(
						`it`.`cate_no` = `itc`.`cate_no`
					)
				)
			)
		WHERE
			(
				isnull(`mpd`.`paid_status`)
				OR (
					`mpd`.`paid_status` <> 'SM00051'
				)
			)
		UNION ALL
			SELECT
				uuid() AS `id`,
				`p`.`gest_id` AS `gest_id`,
				`sd`.`item_code` AS `item_code`,
				`sd`.`item_name` AS `item_name`,
				`sd`.`sell_price` AS `total_cost`,
				`sd`.`input_count` AS `input_count`,
				`it`.`busi_type_id` AS `busi_type_id`,
				'美容服务' AS `business_type`,
				`s`.`id` AS `relation_id`,
				`it`.`is_vip_discount` AS `is_vip_discount`,
				`sd`.`package_unit` AS `item_unit`,
				`sd`.`id` AS `relation_detail_id`,
				`itc`.`cate_name` AS `cate_name`
			FROM
				(
					(
						(
							(
								(
									`t_service_detail` `sd`
									JOIN `t_service` `s` ON (
										(`s`.`id` = `sd`.`service_id`)
									)
								)
								JOIN `t_pet` `p` ON ((`s`.`pet_id` = `p`.`id`))
							)
							JOIN `t_gest` `g` ON ((`p`.`gest_id` = `g`.`id`))
						)
						LEFT JOIN `t_item_type` `it` ON (
							(
								`sd`.`item_code` = `it`.`item_code`
							)
						)
					)
					LEFT JOIN `t_item_cate` `itc` ON (
						(
							`it`.`cate_no` = `itc`.`cate_no`
						)
					)
				)
			WHERE
				(
					isnull(`sd`.`paid_status`)
					OR (
						`sd`.`paid_status` <> 'SM00051'
					)
				)

				UNION ALL
				SELECT
  uuid() AS `id`,
  `p`.`gest_id` AS `gest_id`,
  `frd`.`item_code` AS `item_code`,
  `frd`.`item_name` AS `item_name`,
  `frd`.`sell_price` AS `total_cost`,
  `frd`.`item_num` AS `input_count`,
  `it`.`busi_type_id` AS `busi_type_id`,
  '寄养消费' AS `business_type`,
  `fr`.`id` AS `relation_id`,
  `it`.`is_vip_discount` AS `is_vip_discount`,
  `udd`.`value_name_cn` AS `item_unit`,
  `frd`.`id` AS `relation_detail_id`,
  `itc`.`cate_name` AS `cate_name`
FROM
  (
      (
          (
              (
                  (
                      `t_foster_record_detail` `frd`
                      JOIN `t_foster_record` `fr` ON (
                      (`fr`.`id` = `frd`.`foster_id`)
                      )
                    )
                  JOIN `t_pet` `p` ON ((`fr`.`pet_id` = `p`.`id`))
                )
              JOIN `t_gest` `g` ON ((`p`.`gest_id` = `g`.`id`))
            )
          LEFT JOIN `t_item_type` `it` ON (
          (
            `frd`.`item_code` = `it`.`item_code`
          )
          )
            LEFT JOIN `t_user_dict_detail` `udd` ON (
          (
            `frd`.`recipe_unit` = `udd`.`dict_detail_code`
          )
          )
        )
      LEFT JOIN `t_item_cate` `itc` ON (
      (
        `it`.`cate_no` = `itc`.`cate_no`
      )
      )
  )
WHERE
  (
    isnull(`frd`.`paid_status`)
    OR (
      `frd`.`paid_status` <> 'SM00051'
    )
  )
;

-- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --
-- --- Table structure for v_gest_bill  会员待支付
-- -- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
drop view if exists v_gest_bill;
create view v_settle_accounts_view_total as select gest_id, sum(item_cost) total from v_settle_accounts_view group by gest_id;
create view v_gest_bill as select id, gest_id, gest_code gest_no, gest_name,mobile_phone phone, total from t_gest JOIN v_settle_accounts_view_total vt on id=vt.gest_id;

-- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --
-- --- Table structure for v_report_by_person  医生业绩报表
-- -- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
DROP VIEW IF EXISTS v_report_by_person;
CREATE VIEW v_report_by_person AS
  SELECT
	uuid() AS `id`,
	sum(`vrbiip`.`discount_money`) AS `total`,
	`p`.`person_name` AS `name`,
	'门诊处方' AS `type`,
	substr(`mpd`.`paid_time`, 1, 10) AS `create_date`
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
		`mpd`.`paid_status` = 'SM00051'
	)
GROUP BY
	`mpd`.`create_user_id`,
	substr(`mpd`.`paid_time`, 1, 10)
UNION ALL
	SELECT
		uuid() AS `id`,
		sum(`fsad`.`total_cost`) AS `total`,
		`p`.`person_name` AS `name`,
		(
			CASE
			WHEN (
				locate('CEX', `fsad`.`item_code`) > 0
			) THEN
				'美容销售'
			WHEN (
				locate('BC', `fsad`.`item_code`) > 0
			) THEN
				'商品销售'
			END
		) AS `type`,
		substr(`sdsd`.`create_date`, 1, 10) AS `create_date`
	FROM
		(
			(
				`t_store_direct_sell_detail` `sdsd`
				JOIN `t_persons` `p` ON (
					(
						`sdsd`.`create_user_id` = `p`.`id`
					)
				)
			)
			JOIN `t_finance_settle_accounts_detail` `fsad` ON (
				(
					`sdsd`.`id` = `fsad`.`relation_detail_id`
				)
			)
		)
	GROUP BY
		`sdsd`.`create_user_id`,
		substr(`sdsd`.`create_date`, 1, 10),
		(
			CASE
			WHEN (
				locate('CEX', `fsad`.`item_code`) > 0
			) THEN
				'美容销售'
			WHEN (
				locate('BC', `fsad`.`item_code`) > 0
			) THEN
				'商品销售'
			END
		)
	UNION ALL
		SELECT
			uuid() AS `id`,
			sum(`fsad`.`total_cost`) AS `total`,
			`p`.`person_name` AS `name`,
			'美容服务' AS `type`,
			substr(`sd`.`paid_time`, 1, 10) AS `create_date`
		FROM
			(
				(
					(
						`t_service_detail` `sd`
						JOIN `t_service` `s` ON (
							(`s`.`id` = `sd`.`service_id`)
						)
					)
					JOIN `t_finance_settle_accounts_detail` `fsad` ON (
						(
							`sd`.`id` = `fsad`.`relation_detail_id`
						)
					)
				)
				JOIN `t_persons` `p` ON (
					(
						`s`.`hairdresser_id` = `p`.`id`
					)
				)
			)
			where sd.item_code not in ('CEX00919','CEX00920','CEX00921','CEX00923','CEX00924','CEX00926','CEX00928','CEX00930','CEX00932','CEX00901','CEX00902')
		GROUP BY
			`s`.`hairdresser_id`,
			`sd`.`paid_time`
		UNION ALL
		select * from v_report_service_by_type
		UNION ALL
			SELECT
				uuid() AS `id`,
				sum(`vrbiip`.`discount_money`) AS `total`,
				`p`.`person_name` AS `name`,
				'住院处方' AS `type`,
				substr(`mpd`.`create_date`, 1, 10) AS `create_date`
			FROM
				(
					(
						`t_in_hospital_prescription_detail` `mpd`
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
			GROUP BY
				`mpd`.`create_user_id`,
				substr(`mpd`.`create_date`, 1, 10);

-- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --
-- --- Table structure for v_report_by_item  商品统计报表
-- -- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
drop view if exists v_report_by_item;
create view v_report_by_item as
select uuid() id,mpd.item_code,mpd.item_name,sum(item_num) total_num,avg(item_cost) avg_price,sum(item_num) * avg(item_cost) total,avg(ic.input_price/ic.item_bulk) avg_input_price,avg(ic.input_price/ic.item_bulk)*sum(item_num) total_cost,avg(ic.item_count_num*ic.item_bulk+ic.scattered_count_num) inventory ,  "门诊处方" type,substr(mpd.create_date, 1,10) create_date  from
  t_medic_prescription_detail mpd
  LEFT JOIN t_item_count ic ON ic.item_code = mpd.item_code
group by item_code,item_name,substr(mpd.create_date, 1,10)

-- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --
-- --- Table structure for v_report_by_item_infact_price  商品销售的实际价格
-- -- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
drop view if exists v_report_by_item_infact_price;
create view v_report_by_item_infact_price as
select (1-fsa.dis_count_money/fsa.total_money)*fsad.total_cost discount_money,fsad.relation_detail_id
from t_finance_settle_accounts fsa
  JOIN t_finance_settle_accounts_detail fsad on fsa.id=fsad.settle_accounts_detail_id

-- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --
-- --- Table structure for v_pet  宠物视图
-- -- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
drop view if EXISTS v_pet;
create view v_pet as
  select p.id pet_id, g.mobile_phone from t_pet p JOIN t_gest g ON p.gest_id=g.id;

-- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --
-- --- Table structure for v_report_service_by_type  美容服务按比例提成
-- -- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---
drop view if exists v_report_service_by_type;
create view v_report_service_by_type as
  SELECT
  uuid() AS `id`,
  sum(`fsad`.`total_cost`)/2 AS `total`,
  `p`.`person_name` AS `name`,
  '洗澡服务1:1' AS `type`,
  substr(`sd`.`paid_time`, 1, 10) AS `create_date`
FROM
  (
      (
          (
              `t_service_detail` `sd`
              JOIN `t_service` `s` ON (
              (`s`.`id` = `sd`.`service_id`)
              )
            )
          JOIN `t_finance_settle_accounts_detail` `fsad` ON (
          (
            `sd`.`id` = `fsad`.`relation_detail_id`
          )
          )
        )
      JOIN `t_persons` `p` ON (
      (
        `s`.`hairdresser_id` = `p`.`id`
      )
      )
  )
where sd.item_code in ('CEX00919','CEX00920','CEX00921','CEX00923','CEX00924','CEX00926','CEX00928','CEX00930','CEX00932')
GROUP BY
  `s`.`hairdresser_id`,
  `sd`.`paid_time`
union ALL
SELECT
  uuid() AS `id`,
  sum(`fsad`.`total_cost`)/2 AS `total`,
  `p`.`person_name` AS `name`,
  '洗澡服务1:1' AS `type`,
  substr(`sd`.`paid_time`, 1, 10) AS `create_date`
FROM
  (
      (
          (
              `t_service_detail` `sd`
              JOIN `t_service` `s` ON (
              (`s`.`id` = `sd`.`service_id`)
              )
            )
          JOIN `t_finance_settle_accounts_detail` `fsad` ON (
          (
            `sd`.`id` = `fsad`.`relation_detail_id`
          )
          )
        )
      JOIN `t_persons` `p` ON (
      (
        `s`.`assistant_id` = `p`.`id`
      )
      )
  )
where sd.item_code in ('CEX00919','CEX00920','CEX00921','CEX00923','CEX00924','CEX00926','CEX00928','CEX00930','CEX00932')
GROUP BY
  `s`.`assistant_id`,
  `sd`.`paid_time`
union ALL
SELECT
  uuid() AS `id`,
  sum(`fsad`.`total_cost`)*2/3 AS `total`,
  `p`.`person_name` AS `name`,
  '美容服务2:1' AS `type`,
  substr(`sd`.`paid_time`, 1, 10) AS `create_date`
FROM
  (
      (
          (
              `t_service_detail` `sd`
              JOIN `t_service` `s` ON (
              (`s`.`id` = `sd`.`service_id`)
              )
            )
          JOIN `t_finance_settle_accounts_detail` `fsad` ON (
          (
            `sd`.`id` = `fsad`.`relation_detail_id`
          )
          )
        )
      JOIN `t_persons` `p` ON (
      (
        `s`.`hairdresser_id` = `p`.`id`
      )
      )
  )
where sd.item_code in ('CEX00901','CEX00902')
GROUP BY
  `s`.`hairdresser_id`,
  `sd`.`paid_time`
union ALL
SELECT
  uuid() AS `id`,
  sum(`fsad`.`total_cost`)/3 AS `total`,
  `p`.`person_name` AS `name`,
  '美容服务2:1' AS `type`,
  substr(`sd`.`paid_time`, 1, 10) AS `create_date`
FROM
  (
      (
          (
              `t_service_detail` `sd`
              JOIN `t_service` `s` ON (
              (`s`.`id` = `sd`.`service_id`)
              )
            )
          JOIN `t_finance_settle_accounts_detail` `fsad` ON (
          (
            `sd`.`id` = `fsad`.`relation_detail_id`
          )
          )
        )
      JOIN `t_persons` `p` ON (
      (
        `s`.`assistant_id` = `p`.`id`
      )
      )
  )
where sd.item_code in ('CEX00901','CEX00902')
GROUP BY
  `s`.`assistant_id`,
  `sd`.`paid_time`



