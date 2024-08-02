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

CREATE TABLE myp.onepiece_card_info (
    id VARCHAR(255) NOT NULL PRIMARY KEY COMMENT '卡號唯一值',
    card_number VARCHAR(255) NOT NULL COMMENT '卡號',
    rarity VARCHAR(255) COMMENT '稀有度',
    type VARCHAR(255) COMMENT '種類',
    card_name VARCHAR(255) COMMENT '卡名',
    image_url VARCHAR(255) COMMENT '圖片連接',
    cost INT COMMENT '費用',
    health INT COMMENT '生命值',
    attribute VARCHAR(255) COMMENT '屬性',
    power INT COMMENT '力量',
    counter INT COMMENT '反擊',
    color VARCHAR(255) COMMENT '顏色',
    feature VARCHAR(255) COMMENT '特徵',
    effect TEXT COMMENT '效果',
    trigger_event TEXT COMMENT '觸發',
    acquisition_info TEXT COMMENT '獲得訊息',
    code VARCHAR(255) COMMENT '分類代號'
) COMMENT='卡片資訊表';

CREATE TABLE myp.onepiece_card_product_info (
    ID INT AUTO_INCREMENT PRIMARY KEY COMMENT '流水號',
    product_name VARCHAR(255) NOT NULL COMMENT '商品名',
    option_code VARCHAR(255) NOT NULL  COMMENT '選項代號',
    product_code VARCHAR(255)  COMMENT '商品代號',
    release_date DATE COMMENT '發售日期',
    price INT COMMENT '價位'
) COMMENT='產品資訊表';

INSERT INTO myp.onepiece_card_product_info (product_name, option_code, product_code, release_date, price) VALUES
  ('スタートデッキ 麦わらの一味【ST-01】', '550001', 'ST-01', '2022-07-08', 990),
  ('スタートデッキ 最悪の世代【ST-02】', '550002', 'ST-02', '2022-07-08', 990),
  ('スタートデッキ 王下七武海【ST-03】', '550003', 'ST-03', '2022-07-08', 990),
  ('スタートデッキ 百獣海賊団【ST-04】', '550004', 'ST-04', '2022-07-08', 990),
  ('ブースターパック ROMANCE DAWN【OP-01】', '550101', 'OP-01', '2022-07-22', 198),
  ('スタートデッキ ONE PIECE FILM edition【ST-05】', '550005', 'ST-05', '2022-08-06', 550),
  ('スタートデッキ 海軍【ST-06】', '550006', 'ST-06', '2022-09-30', 990),
  ('ブースターパック 頂上決戦【OP-02】', '550102', 'OP-02', '2022-11-04', 198),
  ('スタートデッキ ビッグ・マム海賊団【ST-07】', '550007', 'ST-07', '2023-01-21', 990),
  ('ブースターパック 強大な敵【OP-03】', '550103', 'OP-03', '2023-02-11', 198),
  ('スタートデッキ Side モンキー・D・ルフィ【ST-08】', '550008', 'ST-08', '2023-03-25', 550),
  ('スタートデッキ Side ヤマト【ST-09】', '550009', 'ST-09', '2023-03-25', 550),
  ('ブースターパック 謀略の王国【OP-04】', '550104', 'OP-04', '2023-05-27', 220),
  ('アルティメットデッキ “三船長”集結【ST-10】', '550010', 'ST-10', '2023-07-29', 2970),
  ('ブースターパック 新時代の主役【OP-05】', '550105', 'OP-05', '2023-08-26', 220),
  ('スタートデッキ Side ウタ【ST-11】', '550011', 'ST-11', '2023-10-07', 550),
  ('スタートデッキ ゾロ&サンジ【ST-12】', '550012', 'ST-12', '2023-10-28', 1320),
  ('ブースターパック 双璧の覇者【OP-06】', '550106', 'OP-06', '2023-11-25', 220),
  ('アルティメットデッキ 3兄弟の絆【ST-13】', '550013', 'ST-13', '2023-12-23', 3190),
  ('エクストラブースター メモリアルコレクション【EB-01】', '550201', 'EB-01', '2024-01-27', 220),
  ('ブースターパック 500年後の未来【OP-07】', '550107', 'OP-07', '2024-02-24', 220),
  ('スタートデッキ 3D2Y【ST-14】', '550014', 'ST-14', '2024-04-27', 1320),
  ('ブースターパック 二つの伝説【OP-08】', '550108', 'OP-08', '2024-05-25', 220),
  ('スタートデッキ 赤 エドワード・ニューゲート【ST-15】', '550015', 'ST-15', '2024-07-13', 550),
  ('スタートデッキ 緑 ウタ【ST-16】', '550016', 'ST-16', '2024-07-13', 550),
  ('スタートデッキ 青 ドンキホーテ・ドフラミンゴ【ST-17】', '550017', 'ST-17', '2024-07-13', 550),
  ('スタートデッキ 紫 モンキー・D・ルフィ【ST-18】', '550018', 'ST-18', '2024-07-13', 550),
  ('スタートデッキ 黒 スモーカー【ST-19】', '550019', 'ST-19', '2024-07-13', 550),
  ('スタートデッキ 黄 シャーロット・カタクリ【ST-20】', '550020', 'ST-20', '2024-07-13', 550),
  ('プレミアムブースター ONE PIECE CARD THE BEST【PRB-01】', '550301', 'PRB-01', '2024-07-27', 550),
  ('ファミリーデッキセット', '550701', NULL, NULL, NULL),
  ('プロモーションカード', '550901', NULL, NULL, NULL),
  ('限定商品収録カード', '550801', NULL, NULL, NULL);



