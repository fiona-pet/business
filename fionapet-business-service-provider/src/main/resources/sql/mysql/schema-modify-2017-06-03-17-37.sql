update t_medic_medictreat_record set medi_treatment_code=register_no where medi_treatment_code is null;

402881ce5a9d9c75015abca0e9b00619
402881ce5a27f511015a2ac8d5360014
402881ce5a9d9c75015abbc4e55905d3
402881ce5b85766e015b892c32a800cb
402881ce5b9a18af015b9f4860130106
402881ce5c067e9d015c290d150d12ed
402881ce5c067e9d015c2e821fcd1504
402881ce5c3b763b015c4cd0639e05c4


ALTER TABLE fiona_pet_business.t_medic_medictreat_record MODIFY medi_treatment_code VARCHAR(50) NOT NULL;
CREATE UNIQUE INDEX t_medic_medictreat_record_medi_treatment_code_uindex ON fiona_pet_business.t_medic_medictreat_record (medi_treatment_code);


