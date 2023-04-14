package com.jonas.myp_sb.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = -40356785423868312L;

    //主鍵
    @Id
    private Long id;
    //用戶名
    @Column(name = "user_name")
    private String userName;
    //暱稱
    private String nickName;
    //密碼
    private String password;
    //帳戶狀態 (0正常 1停用)
    private String status;
    //郵件
    private String email;
    //手機
    private String phoneNumber;
    //性別(0男 1女 2未知)
    private String sex;
    //頭像
    private String avatar;
    //用戶類別(0管理員 1普通用戶)
    private String userType;
    //創建人用戶id
    private Long createBy;
    //創建時間
    private Date createTime;
    //更新人
    private Long updateBy;
    //更新時間
    private Date updateTime;
    //刪除標誌(0未刪除 1已刪除)
    private Integer delFlag;

}
