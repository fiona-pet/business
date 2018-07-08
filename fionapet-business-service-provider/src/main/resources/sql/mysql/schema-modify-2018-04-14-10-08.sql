drop trigger if exists bj_fiona_pet_business.t_item_type_insert_trigger;
create trigger bj_fiona_pet_business.t_item_type_insert_trigger after INSERT
on bj_fiona_pet_business.t_item_type FOR EACH ROW
begin

set @count = (select count(*) from lf_fiona_pet_business.t_item_type where new.bar_code=bar_code and item_name = new.item_name);

if @count = 0 then
set @Ins = 'sync Instrument';
INSERT INTO `lf_fiona_pet_business`.`t_item_type` (`id`, `item_code`, `item_name`, `cate_no`, `package_unit`, `recipe_unit`, `item_standard`, `item_style`, `bar_code`, `busi_type_id`, `item_bulk`, `input_price`, `dealer_code`, `dealer_name`, `sell_price`, `is_vip_discount`, `vip_sell_price`, `is_sell`, `is_count`, `recipe_price`, `drug_form`, `ware_up_limit`, `ware_down_limit`, `safe_day`, `use_way`, `group_name`, `remark`, `input_code`, `is_scattered`, `location`, `is_can_exchange`, `exchange_reward_point`, `create_user_id`, `create_date`, `update_user_id`, `update_date`, `status`)
VALUES (new.`id`, CONCAT('bj-',new.`item_code`), new.`item_name`, new.`cate_no`, new.`package_unit`, new.`recipe_unit`, new.`item_standard`, new.`item_style`, new.`bar_code`, new.`busi_type_id`, new.`item_bulk`, new.`input_price`, new.`dealer_code`, new.`dealer_name`, new.`sell_price`, new.`is_vip_discount`, new.`vip_sell_price`, new.`is_sell`, new.`is_count`, new.`recipe_price`, new.`drug_form`, new.`ware_up_limit`, new.`ware_down_limit`, new.`safe_day`, new.`use_way`, new.`group_name`, new.`remark`, new.`input_code`, new.`is_scattered`, new.`location`, new.`is_can_exchange`, new.`exchange_reward_point`, new.`create_user_id`, new.`create_date`, new.`update_user_id`, new.`update_date`, new.`status`);
elseif @count > 0 THEN
set @Ins = 'Keep it';
end if;

end;

