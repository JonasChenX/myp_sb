-- 初始化資料庫
CREATE TABLE myp.demo_test (
   id int NOT NULL,
   data_yr date NOT NULL,
   label text NOT NULL,
   data_update_time timestamp NOT NULL,
   json_data text
);

--使用者
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

--權限菜單
CREATE TABLE `sys_menu` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主鍵',
    `menu_name` VARCHAR(64) NOT NULL DEFAULT 'NULL' COMMENT '菜單名稱',
    `path` VARCHAR(200) DEFAULT NULL COMMENT '路由地址',
    `component` VARCHAR(255)  DEFAULT NULL COMMENT '組件地址',
    `visible` CHAR(1) DEFAULT '0' COMMENT '菜單狀態 (0顯示 1隱藏)',
    `status` CHAR(1) DEFAULT '0' COMMENT '菜單狀態 (0正常 1停用)',
    `perms` VARCHAR(100) DEFAULT NULL COMMENT '權限標誌',
    `icon` VARCHAR(100) DEFAULT '#' COMMENT '菜單圖示',
    `create_by` BIGINT(20) DEFAULT NULL COMMENT '創建人用戶id',
    `create_time` DATETIME DEFAULT NULL COMMENT '創建時間',
    `update_by` BIGINT(20) DEFAULT NULL COMMENT '更新人',
    `update_time` DATETIME DEFAULT NULL COMMENT '更新時間',
    `del_flag` INT(11) DEFAULT 0 COMMENT '是否刪除(0未刪除 1已刪除)',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '備註',
    PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='菜單表';

INSERT INTO myp.sys_menu (id, menu_name, path, component, visible, status, perms, icon, create_by, create_time, update_by, update_time, del_flag, remark) VALUES (1, '部門管理', 'dept', 'system/dept/index', '0', '0', 'system:dept:list', '#', null, null, null, null, 0, null);
INSERT INTO myp.sys_menu (id, menu_name, path, component, visible, status, perms, icon, create_by, create_time, update_by, update_time, del_flag, remark) VALUES (2, '測試', 'test', 'system/test/index', '0', '0', 'system:test:list', '#', null, null, null, null, 0, null);

--角色表
CREATE TABLE `sys_role` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主鍵',
    `name` VARCHAR(128) NOT NULL DEFAULT 'NULL' COMMENT '角色名稱',
    `role_key` VARCHAR(100) DEFAULT NULL COMMENT '角色權限字符串',
    `status` CHAR(1) DEFAULT '0' COMMENT '角色狀態 (0正常 1停用)',
    `create_by` BIGINT(20) DEFAULT NULL COMMENT '創建人用戶id',
    `create_time` DATETIME DEFAULT NULL COMMENT '創建時間',
    `update_by` BIGINT(20) DEFAULT NULL COMMENT '更新人',
    `update_time` DATETIME DEFAULT NULL COMMENT '更新時間',
    `del_flag` INT(11) DEFAULT 0 COMMENT '是否刪除(0未刪除 1已刪除)',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '備註',
    PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

INSERT INTO myp.sys_role (id, name, role_key, status, create_by, create_time, update_by, update_time, del_flag, remark) VALUES (1, 'CEO', 'ceo', '0', null, null, null, null, 0, null);

--角色and菜單 表
CREATE TABLE `sys_role_menu` (
    `role_id` BIGINT(200) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `menu_id` BIGINT(200) NOT NULL DEFAULT '0' COMMENT '菜單ID',
    PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

INSERT INTO myp.sys_role_menu (role_id, menu_id) VALUES (1, 1);

--使用者and角色 表
CREATE TABLE `sys_user_role` (
    `user_id` BIGINT(200) NOT NULL AUTO_INCREMENT COMMENT '用戶ID',
    `role_id` BIGINT(200) NOT NULL DEFAULT '0' COMMENT '角色ID',
    PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

INSERT INTO myp.sys_user_role (user_id, role_id) VALUES (1, 1);