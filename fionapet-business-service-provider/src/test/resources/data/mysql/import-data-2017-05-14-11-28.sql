update t_item_type it set cate_no=(select cate_no from t_item_cate itc where it.cate_no=itc.id);
update t_item_type it set cate_no=(select cate_no from t_item_cate itc where it.item_style=itc.cate_name);
update t_item_type set cate_no='ICate01' where cate_no is null;

update t_warehouse_inrecord set warehouse_id='5574bbac-9824-11e6-adb2-4ccc6a1bc949', in_warehouse='医院药房' where in_warehouse_code = 'RK00308';