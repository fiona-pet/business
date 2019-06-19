ALTER TABLE t_item_type ADD COLUMN item_alias VARCHAR(100);
update t_item_type set item_alias=item_name where item_alias is null;
-- UPDATE t_item_count ic SET input_code = (SELECT input_code FROM t_item_type it WHERE it.item_code = ic.item_code);


