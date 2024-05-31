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

-- worker存放參數
CREATE TABLE myp.worker_model_parameter(
    task_id bigint NOT NULL,
    model_id text NULL,
    parameters text NULL,
    model_type text NULL
)

-- worker存放結果
CREATE TABLE myp.worker_model_result(
    task_id bigint NOT NULL,
    model_type text NULL,
    result_page text NULL,
    taxpayer_list text NULL,
    result_count float NULL,
    parameter_detail text NULL
)

-- worker存放清單
CREATE TABLE myp.worker_task_detail(
    task_id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    job_id text NULL,
    status tinyint NOT NULL,
    type text NOT NULL,
    message text NULL,
    committer text NOT NULL,
    commit_timestamp DATETIME NULL,
    start_timestamp DATETIME NULL,
    end_timestamp DATETIME NULL,
    retry_count smallint NOT NULL,
    last_modified_timestamp DATETIME NULL,
    description text NULL,
    delete_mk text NULL,
    model_id text NULL
)




