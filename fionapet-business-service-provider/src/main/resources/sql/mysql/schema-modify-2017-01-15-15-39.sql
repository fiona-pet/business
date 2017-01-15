ALTER TABLE t_item_count ADD COLUMN input_code VARCHAR(50);
UPDATE t_item_count ic SET input_code = (SELECT input_code FROM t_item_type it WHERE it.item_code = ic.item_code);


