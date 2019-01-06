ALTER TABLE t_item_type ADD COLUMN alias VARCHAR(100) default item_name;
-- UPDATE t_item_count ic SET input_code = (SELECT input_code FROM t_item_type it WHERE it.item_code = ic.item_code);


