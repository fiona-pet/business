ALTER TABLE fiona_pet_business.t_warehouse_inrecord ADD COLUMN paid_status VARCHAR(6);
update t_pet set pet_breed='未绝育' where pet_breed='6847b614-5787-11e6-9b57-ec4aad85924d';
update t_pet set pet_breed='已绝育' where pet_breed='6847d8b0-5787-11e6-9b57-ec4aad85924d';
