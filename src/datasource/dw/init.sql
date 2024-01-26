-- 初始化資料庫
CREATE TABLE myp.demo_test (
   id int NOT NULL,
   data_yr date NOT NULL,
   label text NOT NULL,
   data_update_time timestamp NOT NULL,
   json_data text
);

CREATE TABLE myp.myp_meta_dtl_tbl (
   id int,
   job_nm text,
   col_nm text,
   col_data_type text,
   col_desc text,
   seq_no int
);

CREATE TABLE myp.detail
(
    id int auto_increment primary key,
    tax_juris_cd text,
    c11          text,
    org_cd       text,
    inv_cnt      text,
    ban          text,
    vat_losn     text,
    ban_nm       text,
    ban_addr     text
);
