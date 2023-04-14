-- 初始化資料庫
CREATE TABLE myp.demo_test (
   id int NOT NULL,
   data_yr date NOT NULL,
   label text NOT NULL,
   data_update_time timestamp NOT NULL,
   json_data text
);


CREATE TABLE `sys_user` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主鍵',
    `user_name` VARCHAR(64) NOT NULL DEFAULT 'NULL' COMMENT '用戶名',
    `nick_name` VARCHAR(64) NOT NULL DEFAULT 'NULL' COMMENT '暱稱',
    `password` VARCHAR(64) NOT NULL DEFAULT 'NULL' COMMENT '密碼',
    `status` CHAR(1) DEFAULT '0' COMMENT '帳戶狀態 (0正常 1停用)',
    `email` VARCHAR(64) DEFAULT NULL COMMENT '郵件',
    `phone_number` VARCHAR(32) DEFAULT NULL COMMENT '手機',
    `sex` CHAR(1) DEFAULT NULL COMMENT '性別(0男 1女 2未知)',
    `avatar` VARCHAR(128) DEFAULT NULL COMMENT '頭像',
    `user_type` CHAR(1) NOT NULL DEFAULT '1' COMMENT '用戶類別(0管理員 1普通用戶)',
    `create_by` BIGINT(20) DEFAULT NULL COMMENT '創建人用戶id',
    `create_time` DATETIME DEFAULT NULL COMMENT '創建時間',
    `update_by` BIGINT(20) DEFAULT NULL COMMENT '更新人',
    `update_time` DATETIME DEFAULT NULL COMMENT '更新時間',
    `del_flag` INT(11) DEFAULT 0 COMMENT '刪除標誌(0未刪除 1已刪除)',
    PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用戶表';

INSERT INTO myp.sys_user (id, user_name, nick_name, password, status, email, phone_number, sex, avatar, user_type, create_by, create_time, update_by, update_time, del_flag) VALUES (1, 'JonasChen', 'JC', '1111', '1', '1234@gmail.com', '0912345678', '1', null, '0', null, '2023-04-13 11:14:19', null, null, 0);
