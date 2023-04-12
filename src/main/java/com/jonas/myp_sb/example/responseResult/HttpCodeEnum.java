package com.jonas.myp_sb.example.responseResult;

import lombok.Getter;

@Getter
public enum HttpCodeEnum {

    // 成功
    SUCCESS(200,"操作成功"),
    // 登錄
    NEED_LOGIN(401,"需要登錄後操作"),
    NO_OPERATOR_AUTH(403,"無權限操作"),
    COMMENT_NOT_NULL(405,"評論內容不能為空"),
    FILE_TYPE_ERROR(406,"文件格式錯誤"),
    ROLE_NOT_EXIT(407,"角色不存在"),
    SYSTEM_ERROR(500,"系統出現錯誤"),
    USERNAME_EXIST(501,"用戶名已存在"),
    ILLEGAL_PARAMETER(501,"參數格式錯誤!"),
    PHONE_NUMBER_EXIST(502,"手機號已存在"),
    EMAIL_EXIST(503, "郵箱已存在"),
    REQUIRE_USERNAME(504, "必須填寫用戶名"),
    LOGIN_ERROR(505,"用戶名或密碼錯誤"),
    CAN_NOT_DELETE_ADMIN(508, "不能刪除超級管理員");

    private final int code;
    private final String message;

    HttpCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
